package accounting;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class add_customer extends index
{	private JPanel newcus;
	private JButton addcus,reset;
	private JTextField fnamefield,lnamefield;
	private JTextArea addrfield;
	private JLabel fname,lname,address,error;
	private JScrollPane scroll;
	
	public add_customer()
	{	newcus=new JPanel();
		newcus.setLayout(new GridBagLayout());
		fname=new JLabel("Customer's first name :");
		lname=new JLabel("Customer's last name :");
		fnamefield=new JTextField(20);
		lnamefield=new JTextField(20);
		address=new JLabel("Customer's address :");
		addrfield=new JTextArea(5,20); 
		addrfield.setLineWrap(true);
		addrfield.setWrapStyleWord(true);
		scroll=new JScrollPane(addrfield);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		addcus=new JButton("Add new customer");
		reset=new JButton("Reset");
		error=new JLabel("");
		error.setVisible(false);
		error.setForeground(Color.RED);
		
		newcus.add(error,gridval(0,0,3,1,GridBagConstraints.CENTER,GridBagConstraints.NONE));
		newcus.add(fname,gridval(0,1,1,1,GridBagConstraints.EAST,GridBagConstraints.NONE));
		newcus.add(fnamefield,gridval(1,1,2,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH));
		newcus.add(lname,gridval(0,2,1,1,GridBagConstraints.EAST,GridBagConstraints.NONE));
		newcus.add(lnamefield,gridval(1,2,2,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH));
		newcus.add(address,gridval(0,3,1,1,GridBagConstraints.NORTHEAST,GridBagConstraints.NONE));
		newcus.add(scroll,gridval(1,3,2,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH));
		newcus.add(reset,gridval(1,4,1,1,GridBagConstraints.EAST,GridBagConstraints.NONE));
		newcus.add(addcus,gridval(2,4,1,1,GridBagConstraints.EAST,GridBagConstraints.NONE));
		newcus.setBorder(BorderFactory.createTitledBorder(BorderFactory.createDashedBorder(null),"Add New Customer"));
		
		jf.add(newcus,gridval(0,1,1,1,GridBagConstraints.NORTHWEST,GridBagConstraints.VERTICAL));
		
		Handler h=new Handler();
		
		reset.addActionListener(h);
		reset.addKeyListener(h);
		addcus.addActionListener(h);
		addcus.addKeyListener(h);	
		
		jf.pack();
		main.center(jf);
		}
	
	private void addcust()
	{	if(fnamefield.getText().length()==0)
		{	error.setText("Please enter first name.");
			if(lnamefield.getText().length()==0)
			error.setText("Please enter first name and last name.");
			error.setVisible(true);
			fnamefield.requestFocusInWindow();
			}
		else if(lnamefield.getText().length()==0)
		{	error.setText("Please enter last name.");
			error.setVisible(true);
			lnamefield.requestFocusInWindow();
			}
		else if(addrfield.getText().length()==0)
		{	error.setText("Please enter customer's address.");
			error.setVisible(true);
			addrfield.requestFocusInWindow();
			}
		else
		{	try
			{	st=con.prepareStatement("select * from customers where first_name=? and last_name=? and address=?");
				st.setString(1,fnamefield.getText());
				st.setString(2,lnamefield.getText());
				st.setString(3,addrfield.getText());
				rs=st.executeQuery();
				if(rs.next())
				{	error.setText("Customer already exists.");
					error.setVisible(true);
					}
				else
				{	st=con.prepareStatement("insert into customers (first_name,last_name,address) values (?,?,?)");
					st.setString(1,fnamefield.getText());
					st.setString(2,lnamefield.getText());
					st.setString(3,addrfield.getText());
					st.execute();
					error.setText("Customer added successfully.");
					error.setVisible(true);
					fnamefield.setText(null);
					lnamefield.setText(null);
					addrfield.setText(null);
					fnamefield.requestFocusInWindow();
					}
				}
			catch(SQLException e)
			{	JOptionPane.showMessageDialog(new JOptionPane(),"Something went wrong.\n\nCannot Connect to Server.\n\n"
					+ "Please make sure xampp is working properly.\n\n","Error",JOptionPane.ERROR_MESSAGE);
				System.exit(0);
				}
			}
		}
	
	private class Handler extends KeyAdapter implements ActionListener
	{	public void actionPerformed(ActionEvent e)
		{	if(e.getSource()==reset)
			{	error.setVisible(false);
				error.setText("");
				fnamefield.setText(null);
				lnamefield.setText(null);
				addrfield.setText(null);
				}
			else if(e.getSource()==addcus)
			{	addcust();
				}
			jf.pack();
			main.center(jf);
			}
		public void keyTyped(KeyEvent e)
		{	if(e.getKeyChar()==KeyEvent.VK_ENTER)
			{	if(e.getSource()==reset)
				{	error.setVisible(false);
					error.setText("");
					fnamefield.setText(null);
					lnamefield.setText(null);
					addrfield.setText(null);
					}
				else if(e.getSource()==addcus) 
				{	addcust();
					}
				jf.pack();
				main.center(jf);
				}
			}
		}
	}
