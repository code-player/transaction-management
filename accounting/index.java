package accounting;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class index extends connect
{	private static  JLabel user;
	private static JButton logout;
	public static JFrame jf;
	private static JPanel jp,cus_sell;
	
	public static void set_index(String str)
	{	jf=new JFrame();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setTitle("Customer's Transaction Management System");
		jf.setResizable(false);
		jf.setVisible(true);
		jf.setLayout(new GridBagLayout());
				
		jp=new JPanel();
		jp.setLayout(new GridBagLayout());
		user=new JLabel(str);
		user.setText(user.getText().toUpperCase());
		user.setForeground(Color.BLUE);
		user.setFont(new Font("Serif",Font.BOLD+Font.ITALIC,18));
		logout=new JButton("Logout");
		logout.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		jf.add(user,gridval(0,0,1,1,GridBagConstraints.CENTER,GridBagConstraints.NONE));
		jf.add(logout,gridval(2,0,1,1,GridBagConstraints.CENTER,GridBagConstraints.NONE));
		//jp.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		
		add_customer cus=new add_customer();
		add_purchase pur=new add_purchase();
		add_sell sell=new add_sell();
		get_tot gt=new get_tot();
		
	//	jf.add(jp,gridval(0,0,3,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH));
		jf.pack();
		main.center(jf);
		
		Handler h= new Handler();
		logout.addActionListener(h);
		logout.addKeyListener(h);
		
		}
	
	public static GridBagConstraints gridval(int x,int y,int w,int h,int anchor,int fill)
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
	
	private static class Handler extends KeyAdapter implements ActionListener
	{	public void actionPerformed(ActionEvent e)
		{	if(e.getSource()==logout)
			{	login lg=new login();
				jf.dispose();
				}
			jf.pack();
			main.center(jf);
			}
		public void keyTyped(KeyEvent e)
		{	if(e.getKeyChar()==KeyEvent.VK_ENTER)
			{	if(e.getSource()==logout)	
				{	login lg=new login();
					jf.dispose();
					}
				}
			jf.pack();
			main.center(jf);
			}
		}
	
	}
