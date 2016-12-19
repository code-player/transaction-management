package accounting;
import javax.swing.*;
import java.sql.*;
import java.util.Arrays;
import java.awt.*;
import java.awt.event.*;

public class login extends connect
{	private JButton login,exit;
	private JLabel top,user,pass,wronguser,wrongpass;
	private JTextField userfield;
	private JPasswordField passfield;
	private JFrame jf;
	
	public login()
	{	jf=new JFrame();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setTitle("Login Window");
		jf.setResizable(false);
		jf.setVisible(true);
		jf.setLayout(new GridBagLayout());
		
		
		JPanel ptop=new JPanel();
		top=new JLabel("Login with your username and password.");
		top.setForeground(Color.BLUE);
		top.setFont(new Font("Serif",Font.BOLD+Font.ITALIC,14));
		ptop.add(top,BorderLayout.NORTH);
		user=new JLabel("Username :");
		user.setForeground(Color.ORANGE);
		wronguser=new JLabel("");
		wronguser.setVisible(false);
		wronguser.setForeground(Color.RED);
		pass=new JLabel("Password :");
		pass.setForeground(Color.ORANGE);
		wrongpass=new JLabel("");
		wrongpass.setVisible(false);
		wrongpass.setForeground(Color.RED);
		userfield=new JTextField(18);
		passfield=new JPasswordField(18);
		login=new JButton("Login");
		exit=new JButton("Exit");
		
		jf.add(ptop,gridval(0,0,3,2,GridBagConstraints.NORTHWEST,GridBagConstraints.NONE));
		jf.add(user,gridval(0,2,1,1,GridBagConstraints.EAST,GridBagConstraints.NONE));
		jf.add(userfield,gridval(1,2,2,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH));
		jf.add(wronguser,gridval(1,3,2,1,GridBagConstraints.NORTHWEST,GridBagConstraints.NONE));
		jf.add(pass,gridval(0,4,1,1,GridBagConstraints.EAST,GridBagConstraints.NONE));
		jf.add(passfield,gridval(1,4,2,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH));
		jf.add(wrongpass,gridval(1,5,2,1,GridBagConstraints.NORTHWEST,GridBagConstraints.NONE));
		jf.add(login,gridval(1,6,1,1,GridBagConstraints.SOUTHEAST,GridBagConstraints.NONE));
		jf.add(exit,gridval(2,6,1,1,GridBagConstraints.SOUTHWEST,GridBagConstraints.NONE));
		userfield.requestFocusInWindow();
		jf.pack();
		main.center(jf);
		
		Handler h=new Handler();
		login.addActionListener(h);
		exit.addActionListener(h);
		login.addKeyListener(h);
		exit.addKeyListener(h);
		passfield.addKeyListener(h);
		}
	
	private GridBagConstraints gridval(int x,int y,int w,int h,int anchor,int fill)
	{	GridBagConstraints c=new GridBagConstraints();
		c.gridx=x;
		c.gridy=y;
		c.gridwidth=w;
		c.gridheight=h;
		c.insets=new Insets(5,5,5,5);
		c.anchor=anchor;
		c.fill=fill;
		return c;
		}
	
	public void validate()
	{	if(userfield.getText().isEmpty())
		{	wronguser.setText("Please enter username.");
			wronguser.setVisible(true);
			userfield.requestFocusInWindow();
			passfield.setText(null);
			wrongpass.setVisible(false);
			}
		else if(passfield.getPassword().length==0)
		{	wrongpass.setText("Please enter password.");
			wrongpass.setVisible(true);
			passfield.requestFocusInWindow();
			passfield.setText(null);
			wronguser.setVisible(false);
			}
		else
		{	try
			{	st=con.prepareStatement("select username,password from `users` where username=?");
				st.setString(1,userfield.getText());
				rs=st.executeQuery();
				if(rs.next())
				{	char[] pass=new char[rs.getString(2).length()];
					for(int i=0;i<rs.getString(2).length();i++)
						pass[i]=rs.getString(2).charAt(i);
					if(Arrays.equals(pass,passfield.getPassword()))
					{	index.set_index(userfield.getText());
						jf.dispose();
						}
					else
					{	wrongpass.setText("Invalid password.");
						wrongpass.setVisible(true);
						wronguser.setVisible(false);
						passfield.setText(null);
						passfield.requestFocusInWindow();
						}
					}
				else
				{	wronguser.setText("Invalid username or password.");
					wronguser.setVisible(true);
					wrongpass.setVisible(false);
					userfield.setText(null);
					passfield.setText(null);
					userfield.requestFocusInWindow();
					}
				}
			catch(SQLException e)
			{	JOptionPane.showMessageDialog(new JOptionPane(),"Something went wrong.\n\nCannot Connect to Server.\n\n"
					+ "Please make sure xampp is working properly.\n\n","Error",JOptionPane.ERROR_MESSAGE);
				System.exit(0);
				}
			}
		jf.pack();
		main.center(jf);
		}	
	
	public class Handler extends KeyAdapter implements ActionListener
	{	public void actionPerformed(ActionEvent e)
		{	if(e.getSource()==exit)
				System.exit(0);
			else if(e.getSource()==login)
				validate();
			}
		
		public void keyTyped(KeyEvent e)
		{	if(e.getKeyChar()==KeyEvent.VK_ENTER)
			{	if(e.getSource()==exit)
					System.exit(0);
				else if(e.getSource()==login||e.getSource()==passfield)
					validate();
				}
			}
		}	
	}
