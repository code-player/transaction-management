package accounting;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class get_tot extends index 
{	private JPanel get_details;
	private JTextField fnamefield,lnamefield;
	private JTextArea addressfield;
	private JScrollPane addrscroll;
	private JLabel fname,lname,address,error;
	private JButton reset,get_det;
	
	public get_tot()
	{	get_details=new JPanel();
		get_details.setLayout(new GridBagLayout());
		
		fname=new JLabel("Customer's first name :");
		lname=new JLabel("Customer's last name :");
		fnamefield=new JTextField(20);
		lnamefield=new JTextField(20);
		address=new JLabel("Customer's address :");
		addressfield=new JTextArea(5,20);
		addressfield.setLineWrap(true);
		addressfield.setWrapStyleWord(true);
		addrscroll=new JScrollPane(addressfield);
		addrscroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		address.setVisible(false);
		addrscroll.setVisible(false);
		reset=new JButton("Reset");
		get_det=new JButton("Get Details");
		error=new JLabel("");
		error.setVisible(false);
		error.setForeground(Color.RED);
				
		get_details.add(error,gridval(0,0,6,1,GridBagConstraints.CENTER,GridBagConstraints.NONE));
		get_details.add(fname,gridval(0,1,1,1,GridBagConstraints.EAST,GridBagConstraints.NONE));
		get_details.add(fnamefield,gridval(1,1,2,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH));
		get_details.add(lname,gridval(3,1,1,1,GridBagConstraints.EAST,GridBagConstraints.NONE));
		get_details.add(lnamefield,gridval(4,1,2,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH));
		get_details.add(address,gridval(1,2,1,1,GridBagConstraints.NORTHEAST,GridBagConstraints.CENTER));
		get_details.add(addrscroll,gridval(2,2,2,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH));
		get_details.add(reset,gridval(4,3,1,1,GridBagConstraints.EAST,GridBagConstraints.NONE));
		get_details.add(get_det,gridval(5,3,1,1,GridBagConstraints.EAST,GridBagConstraints.BOTH));
		
		get_details.setBorder(BorderFactory.createTitledBorder(BorderFactory.createDashedBorder(null),"Get Total Transaction Details"));
		
		jf.add(get_details,gridval(0,2,3,1,GridBagConstraints.CENTER,GridBagConstraints.NONE));
		
		Handler h=new Handler();
		
		reset.addActionListener(h);
		get_det.addActionListener(h);
		reset.addKeyListener(h);
		get_det.addKeyListener(h);
		if(address.isVisible()==false)
			lnamefield.addKeyListener(h);
		
		jf.pack();
		main.center(jf);

		}
	
	public void get_details()
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
		else if(address.isVisible()&&addressfield.getText().length()==0)
		{	error.setText("Please enter the address of customer.");
			error.setVisible(true);
			addressfield.requestFocusInWindow();
			}
		else
		{	try
			{	if(address.isVisible())
				{	st=con.prepareStatement("select * from customers where first_name=? and last_name=? and address=?");
					st.setString(1,fnamefield.getText());
					st.setString(2,lnamefield.getText());
					st.setString(3,addressfield.getText());
					}
				else
				{	st=con.prepareStatement("select * from customers where first_name=? and last_name=?");
					st.setString(1,fnamefield.getText());
					st.setString(2,lnamefield.getText());
					}
				rs=st.executeQuery();
				if(!rs.next())
				{	error.setText("Customer doesn't exists, first add this customer.");
					error.setVisible(true);
					}
				else if(!rs.next())
				{	rs.last();
					fnamefield.setText(null);
					lnamefield.setText(null);
					fnamefield.setEditable(true);
					lnamefield.setEditable(true);
					addressfield.setText(null);
					error.setText(null);
					error.setVisible(false);
					address.setVisible(false);
					addrscroll.setVisible(false);
					fnamefield.requestFocusInWindow();	
					show_result res=new show_result(rs);
					}
				else
				{	error.setText("Enter customer's address too.");
					error.setVisible(true);
					fnamefield.setEditable(false);
					lnamefield.setEditable(false);
					address.setVisible(true);
					addrscroll.setVisible(true);
					addressfield.requestFocusInWindow();
					}
				
				}
			catch(SQLException e)
			{	JOptionPane.showMessageDialog(new JOptionPane(),"Something went wrong.\n\nCannot Connect to Server.\n\n"
					+ "Please make sure xampp is working properly.\n\n","Error",JOptionPane.ERROR_MESSAGE);
				System.exit(0);
				}
			
			}
		
		}
	
	public class Handler extends KeyAdapter implements ActionListener 
	{	public void actionPerformed(ActionEvent e)
		{	if(e.getSource()==reset)
			{	fnamefield.setText(null);
				lnamefield.setText(null);
				fnamefield.setEditable(true);
				lnamefield.setEditable(true);
				addressfield.setText(null);
				address.setVisible(false);
				addrscroll.setVisible(false);
				error.setText(null);
				error.setVisible(false);
				fnamefield.requestFocusInWindow();
				}
			else if(e.getSource()==get_det||e.getSource()==lnamefield)
			{	get_details();
				}
			jf.pack();
			main.center(jf);
			}
		
		public void keyTyped(KeyEvent e)
		{	if(e.getKeyChar()==KeyEvent.VK_ENTER)
			{	if(e.getSource()==reset)
				{	fnamefield.setText(null);
					lnamefield.setText(null);
					fnamefield.setEditable(true);
					lnamefield.setEditable(true);
					addressfield.setText(null);
					address.setVisible(false);
					addrscroll.setVisible(false);
					error.setText(null);
					error.setVisible(false);
					fnamefield.requestFocusInWindow();
					}
				else if(e.getSource()==get_det||e.getSource()==lnamefield)
				{	get_details();
					}
				}
			jf.pack();
			main.center(jf);
			}
		}	
	}
		
