package accounting;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

import accounting.add_purchase.mHandler;

public class add_sell extends index
{	private JPanel cus_sell;
	private JButton add,reset;
	private JTextField fnamefield,lnamefield,amountfield;
	private JTextArea addressfield;
	private JLabel fname,lname,amount,address,extra,error,change;
	public JList extrasell;
	private static DefaultListModel mlist;
	private JScrollPane scroll,addrscroll;

	public add_sell()
	{	mlist=new DefaultListModel();
		try
		{	st=con.prepareStatement("select name from `items` where 1");
			rs=st.executeQuery();
			while(rs.next())
				mlist.addElement(rs.getString("name"));
			}
		catch(SQLException e)
		{	JOptionPane.showMessageDialog(new JOptionPane(),"Something went wrong.\n\nCannot Connect to Server.\n\n"
				+ "Please make sure xampp is working properly.\n\n","Error",JOptionPane.ERROR_MESSAGE);
			System.exit(0);
			}
		
		
		cus_sell=new JPanel();
		cus_sell.setLayout(new GridBagLayout());
		fname=new JLabel("Customer's first name :");
		lname=new JLabel("Customer's last name :");
		fnamefield=new JTextField(20);
		lnamefield=new JTextField(20);
		amount=new JLabel("Amount (in Rs.) :");
		amountfield=new JTextField(20);
		extra=new JLabel("Gift Items on sale :");
		extrasell=new JList(mlist);
		extrasell.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		extrasell.setVisibleRowCount(4);
		extrasell.setSelectedIndex(0);
		scroll=new JScrollPane(extrasell);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		change=new JLabel("Edit Items List");
		address=new JLabel("Customer's address :");
		addressfield=new JTextArea(5,20);
		addressfield.setLineWrap(true);
		addressfield.setWrapStyleWord(true);
		addrscroll=new JScrollPane(addressfield);
		addrscroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		address.setVisible(false);
		addrscroll.setVisible(false);
		add=new JButton("Add Details");
		reset=new JButton("Reset");
		error=new JLabel("");
		error.setVisible(false);
		error.setForeground(Color.RED);
		
		cus_sell.add(error,gridval(0,0,3,1,GridBagConstraints.CENTER,GridBagConstraints.NONE));
		cus_sell.add(fname,gridval(0,1,1,1,GridBagConstraints.EAST,GridBagConstraints.NONE));
		cus_sell.add(fnamefield,gridval(1,1,2,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH));
		cus_sell.add(lname,gridval(0,2,1,1,GridBagConstraints.EAST,GridBagConstraints.NONE));
		cus_sell.add(lnamefield,gridval(1,2,2,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH));
		cus_sell.add(extra,gridval(0,3,1,1,GridBagConstraints.NORTHEAST,GridBagConstraints.NONE));
		cus_sell.add(scroll,gridval(1,3,2,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH));
		cus_sell.add(change,gridval(2,4,1,1,GridBagConstraints.EAST,GridBagConstraints.NONE));
		cus_sell.add(amount,gridval(0,5,1,1,GridBagConstraints.EAST,GridBagConstraints.NONE));
		cus_sell.add(amountfield,gridval(1,5,2,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH));
		cus_sell.add(address,gridval(0,6,1,1,GridBagConstraints.NORTHEAST,GridBagConstraints.NONE));
		cus_sell.add(addrscroll,gridval(1,6,2,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH));
		cus_sell.add(reset,gridval(1,7,1,1,GridBagConstraints.EAST,GridBagConstraints.NONE));
		cus_sell.add(add,gridval(2,7,1,1,GridBagConstraints.EAST,GridBagConstraints.BOTH));
		cus_sell.setBorder(BorderFactory.createTitledBorder(BorderFactory.createDashedBorder(null),"Add New Sell Details"));
		
		jf.add(cus_sell,gridval(2,1,1,1,GridBagConstraints.WEST,GridBagConstraints.BOTH));
		
		Handler h=new Handler();
		
		reset.addActionListener(h);
		reset.addKeyListener(h);
		add.addActionListener(h);
		add.addKeyListener(h);
		if(address.isVisible()==false)
			amountfield.addKeyListener(h);
		
		mHandler mh=new mHandler();
		change.addMouseListener(mh);
				
		jf.pack();
		main.center(jf);
		}
	
	public static void refresh(ResultSet r)
	{	mlist.clear();
		try
		{	while(r.next())
				mlist.addElement(r.getString("name"));
			}
		catch(SQLException e)
		{	JOptionPane.showMessageDialog(new JOptionPane(),"Something went wrong.\n\nCannot Connect to Server.\n\n"
				+ "Please make sure xampp is working properly.\n\n","Error",JOptionPane.ERROR_MESSAGE);
			System.exit(0);
			}
		main.center(jf);
		jf.pack();
		}
	
	public void addsell()
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
		else if(amount.getText().length()==0)
		{	error.setText("Please enter the amount of purchase.");
			error.setVisible(true);
			amount.setText("");
			amount.requestFocusInWindow();
			}
		else if(address.isVisible()&&addressfield.getText().length()==0)
		{	error.setText("Please enter the address of customer.");
			error.setVisible(true);
			addressfield.requestFocusInWindow();
			}
		else
		{	try
			{	double amt=Double.parseDouble(amountfield.getText());
				if(address.isVisible())
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
					String s=new String(),s1=new String();
					s=rs.getString("sell");
					s1=rs.getString("purchase");
					Object[] sel=extrasell.getSelectedValues();
					for(Object i:sel)
					{	if(s==null)
							s=""+i;
						else
							s+=", "+i;
						}
					amt+=rs.getDouble("sell_amount");
					String[] arr,arr1;
					
					if(s!=null)	
					{	arr=s.split(", ");
						}
					else	
					{	arr=null;
						}
					if(s1!=null)
					{	arr1=s1.split(", ");
						}
					else 
					{	arr1=null;
						}
										
					if(arr!=null&&arr1!=null)
					{	s="";
						s1="";
						
						for(int i=0;i<arr.length;i++)
						{	for(int j=0;j<arr1.length;j++)
							{	if(arr[i].equals((String)arr1[j]))
								{	arr1[j]="";
									arr[i]="";
									break;
									}
								}
							}
						for(String a:arr)
						{	if(a!="")
							{	if(s==""||s==null)
									s=a;
								else
									s+=", "+a;
								}
							}
						for(String a:arr1)
						{	if(a!="")
							{	if(s1==""||s1==null)
									s1=a;
								else
									s1+=", "+a;
								}
							}
						}
					if(address.isVisible())
					{	st=con.prepareStatement("update customers set purchase=?,sell=?,sell_amount=? where first_name=? and last_name=? and address=?");
						st.setString(1,s1);
						st.setString(2,s);
						st.setDouble(3,amt);
						st.setString(4,fnamefield.getText());
						st.setString(5,lnamefield.getText());
						st.setString(6,addressfield.getText());
						}
					else
					{	st=con.prepareStatement("update customers set purchase=?,sell=?,sell_amount=? where first_name=? and last_name=?");
						st.setString(1,s1);
						st.setString(2,s);
						st.setDouble(3,amt);
						st.setString(4,fnamefield.getText());
						st.setString(5,lnamefield.getText());
						}
					System.out.println(st);
					st.executeUpdate();
					error.setText("Details added successfully.");
					error.setVisible(true);
					fnamefield.setText(null);
					lnamefield.setText(null);
					extrasell.setSelectedIndex(0);
					fnamefield.setEditable(true);
					lnamefield.setEditable(true);
					address.setVisible(false);
					addressfield.setText("");
					addrscroll.setVisible(false);
					amountfield.setText(null);
					extrasell.setEnabled(true);
					amountfield.setEditable(true);
					fnamefield.requestFocusInWindow();
					}
				else
				{	error.setText("Enter customer's address too.");
					error.setVisible(true);
					fnamefield.setEditable(false);
					lnamefield.setEditable(false);
					extrasell.setEnabled(false);
					amountfield.setEditable(false);
					address.setVisible(true);
					addrscroll.setVisible(true);
					addressfield.requestFocusInWindow();
					}
				}
			catch(NumberFormatException e)
			{	error.setText("Enter a valid amount of purchase.");
				error.setVisible(true);
				amountfield.setText("");
				amountfield.requestFocusInWindow();
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
				amountfield.setText(null);
				extrasell.setSelectedIndex(0);
				fnamefield.setEditable(true);
				lnamefield.setEditable(true);
				amountfield.setEditable(true);
				extrasell.setEnabled(true);
				addressfield.setText(null);
				address.setVisible(false);
				addrscroll.setVisible(false);
				}
			else if(e.getSource()==add)
			{	addsell();
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
					amountfield.setText(null);
					extrasell.setSelectedIndex(0);
					fnamefield.setEditable(true);
					lnamefield.setEditable(true);
					amountfield.setEditable(true);
					extrasell.setEnabled(true);
					addressfield.setText(null);
					address.setVisible(false);
					addrscroll.setVisible(false);
					}
				else if(e.getSource()==add||e.getSource()==amountfield) 
				{	addsell();
					}
				}
			jf.pack();
			main.center(jf);
			}
		}	
	
	public class mHandler extends MouseAdapter
	{	public void mouseClicked(MouseEvent e)
		{	if(e.getSource()==change)
			{	edit_list ed=new edit_list();
				}	
			}
		
		public void mouseEntered(MouseEvent e)
		{	if(e.getSource()==change)
			{	Font f=change.getFont();
				f.getStyle();
				Font nf=f.deriveFont(Font.BOLD+Font.ITALIC);
				change.setFont(nf);
				}
			}
		
		public void mouseExited(MouseEvent e)
		{	if(e.getSource()==change)
			{	change.setBorder(null);
				Font f=change.getFont();
				f.getStyle();
				Font nf=f.deriveFont(Font.BOLD);
				change.setFont(nf);
				}
			}
		}
	
	}
