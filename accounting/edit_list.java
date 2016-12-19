package accounting;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class edit_list extends index
{	private JFrame j;
	private JPanel jp,jp1,top;
	private JList list;
	private JLabel add,del,adderr,delerr;
	private JTextField addval;
	private DefaultListModel mlist;
	private JRadioButton but1,but2;
	private ButtonGroup buts;
	private JButton addthis,cancel1,delete,cancel2;
	private JScrollPane scroll;
	
	public edit_list()
	{	j=new JFrame();
		j.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		j.setResizable(false);
		j.setVisible(true);
		j.setTitle("Edit Items List");
		j.setLayout(new GridBagLayout());
		
		try
		{	mlist=new DefaultListModel();
			st=con.prepareStatement("select * from items where 1");
			rs=st.executeQuery();
			while(rs.next())
			{	mlist.addElement(rs.getString("name"));
				}
			}
		catch(SQLException e)
		{	JOptionPane.showMessageDialog(new JOptionPane(),"Something went wrong.\n\nCannot Connect to Server.\n\n"
				+ "Please make sure xampp is working properly.\n\n","Error",JOptionPane.ERROR_MESSAGE);
			System.exit(0);
			}
		
		top=new JPanel();
		top.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		top.setLayout(new FlowLayout(FlowLayout.LEFT));
		but1=new JRadioButton("Add New Item");
		but1.setSelected(true);
		but2=new JRadioButton("Delete Items");
		buts=new ButtonGroup();
		buts.add(but1);
		buts.add(but2);
		top.add(but1);
		top.add(but2);
		
		jp=new JPanel();
		jp.setLayout(new GridBagLayout());
		adderr=new JLabel("");
		adderr.setVisible(false);
		adderr.setForeground(Color.RED);
		add=new JLabel("Enter Item's Name :");
		addval=new JTextField(17);
		addthis=new JButton("Add Item");
		cancel1=new JButton("Close");
		jp.add(adderr,gridval(0,0,3,1,GridBagConstraints.CENTER,GridBagConstraints.NONE));
		jp.add(add,gridval(0,1,1,1,GridBagConstraints.WEST,GridBagConstraints.NONE));
		jp.add(addval,gridval(1,1,2,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH));
		jp.add(addthis,gridval(1,2,1,1,GridBagConstraints.CENTER,GridBagConstraints.NONE));
		jp.add(cancel1,gridval(2,2,1,1,GridBagConstraints.CENTER,GridBagConstraints.NONE));
		jp.setVisible(true);
		
		jp1=new JPanel();
		jp1.setLayout(new GridBagLayout());
		delerr=new JLabel("");
		delerr.setVisible(false);
		delerr.setForeground(Color.RED);
		del=new JLabel("Select items to delete :");
		list=new JList(mlist);
		list.setFixedCellWidth(200);
		list.setVisibleRowCount(4);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setSelectedIndex(0);
		scroll=new JScrollPane(list);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		delete=new JButton("Delete Item(s)");
		cancel2=new JButton("Close");
		jp1.add(delerr,gridval(0,0,3,1,GridBagConstraints.CENTER,GridBagConstraints.NONE));
		jp1.add(del,gridval(0,1,1,1,GridBagConstraints.NORTHEAST,GridBagConstraints.NONE));
		jp1.add(scroll,gridval(1,1,2,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH));
		jp1.add(delete,gridval(1,2,1,1,GridBagConstraints.CENTER,GridBagConstraints.NONE));
		jp1.add(cancel2,gridval(2,2,2,1,GridBagConstraints.CENTER,GridBagConstraints.NONE));
		jp1.setVisible(false);
		
		j.add(top,gridval(0,0,2,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH));
		j.add(jp,gridval(0,1,3,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH));
		j.add(jp1,gridval(0,2,3,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH));
		
		Handler h=new Handler();
		but1.addActionListener(h);
		but2.addActionListener(h);
		but1.addKeyListener(h);
		but2.addKeyListener(h);
		
		addthis.addActionListener(h);
		cancel1.addActionListener(h);
		addval.addKeyListener(h);
		addthis.addKeyListener(h);
		cancel1.addKeyListener(h);
		
		delete.addActionListener(h);
		delete.addKeyListener(h);
		cancel2.addActionListener(h);
		cancel2.addKeyListener(h);
				
		main.center(j);
		j.pack();
		
		}
		
		public int additem(String str)
		{	try
			{	st=con.prepareStatement("select * from items where name=?");
				st.setString(1,str);
				rs=st.executeQuery();
				if(rs.next())
				{	adderr.setText("Item already exists.");
					adderr.setVisible(true);
					addval.requestFocusInWindow();
					return 0;
					}
				st=con.prepareStatement("insert into items set name=?");
				st.setString(1,str);
				st.executeUpdate();
				mlist.clear();
				st=con.prepareStatement("select * from items where 1");
				rs=st.executeQuery();
				add_purchase.refresh(rs);
				rs.beforeFirst();
				add_sell.refresh(rs);
				rs.beforeFirst();
				while(rs.next())
					mlist.addElement(rs.getString("name"));
				}
			catch(SQLException e)
			{	JOptionPane.showMessageDialog(new JOptionPane(),"Something went wrong.\n\nCannot Connect to Server.\n\n"
					+ "Please make sure xampp is working properly.\n\n","Error",JOptionPane.ERROR_MESSAGE);
				System.exit(0);
				}
			return 1;
			}
		
		public void delitem(Object[] str)
		{	try
			{	for(Object o:str)	
				{	st=con.prepareStatement("delete from items where name=?");
					st.setString(1,(String)o);
					st.executeUpdate();
					}
				mlist.clear();
				st=con.prepareStatement("select * from items where 1");
				rs=st.executeQuery();
				add_purchase.refresh(rs);
				rs.beforeFirst();
				add_sell.refresh(rs);
				rs.beforeFirst();
				while(rs.next())
					mlist.addElement(rs.getString("name"));
				}
			catch(SQLException e)
			{	JOptionPane.showMessageDialog(new JOptionPane(),"Something went wrong.\n\nCannot Connect to Server.\n\n"
					+ "Please make sure xampp is working properly.\n\n","Error",JOptionPane.ERROR_MESSAGE);
				System.exit(0);
				}
			}
	
		public class Handler extends KeyAdapter implements ActionListener
		{	public void actionPerformed(ActionEvent e)
			{	if(e.getSource()==but1)
				{	jp.setVisible(true);
					jp1.setVisible(false);
					list.setSelectedIndex(0);
					addval.requestFocusInWindow();
					addval.setText(null);
					delerr.setText(null);
					delerr.setVisible(false);
					adderr.setText(null);
					adderr.setVisible(false);
					}
				else if(e.getSource()==but2)
				{	jp1.setVisible(true);
					jp.setVisible(false);
					list.setSelectedIndex(0);
					addval.setText(null);
					delerr.setText(null);
					delerr.setVisible(false);
					adderr.setText(null);
					adderr.setVisible(false);
					}
				
				if(but1.isSelected()==true)
				{	if(e.getSource()==cancel1)
					{	j.dispose();
						}
					else if(e.getSource()==addthis)
					{	if(addval.getText().length()==0)
						{	adderr.setText("Enter item's name first.");
							adderr.setVisible(true);
							}
						else
						{	if(additem(addval.getText())==1)
							{	adderr.setText("Item added successfully.");
								adderr.setVisible(true);
								}
							}
						}
					}
				else if(but2.isSelected()==true)
				{	if(e.getSource()==cancel2)
					{	j.dispose();
						}
					else if(e.getSource()==delete)
					{	if(list.isSelectionEmpty())
						{	delerr.setText("Select items first.");
							delerr.setVisible(true);
							}
						else
						{	delitem(list.getSelectedValues());
							delerr.setText("Item deleted successfully.");
							delerr.setVisible(true);
							}
						}
					}
					
				main.center(j);
				j.pack();
				}
			
			public void keyTyped(KeyEvent e)
			{	if(e.getKeyChar()==KeyEvent.VK_ENTER)
				{	if(e.getSource()==but1)
					{	but1.setSelected(true);
						jp.setVisible(true);
						jp1.setVisible(false);
						list.setSelectedIndex(0);
						addval.requestFocusInWindow();
						addval.setText(null);
						delerr.setText(null);
						delerr.setVisible(false);
						adderr.setText(null);
						adderr.setVisible(false);
						}
					else if(e.getSource()==but2)
					{	but2.setSelected(true);
						jp1.setVisible(true);
						jp.setVisible(false);
						list.setSelectedIndex(0);
						addval.setText(null);
						delerr.setText(null);
						delerr.setVisible(false);
						adderr.setText(null);
						adderr.setVisible(false);
						}
					
					if(but1.isSelected()==true)
					{	if(e.getSource()==cancel1)
						{	j.dispose();
							}
						else if(e.getSource()==addthis||e.getSource()==addval)
						{	if(addval.getText().length()==0)
							{	adderr.setText("Enter item's name first.");
								adderr.setVisible(true);
								}
							else
							{	if(additem(addval.getText())==1)
								{	adderr.setText("Item added successfully.");
									adderr.setVisible(true);
									}
								}
							}
						}
					else if(but2.isSelected()==true)
					{	if(e.getSource()==cancel2)
						{	j.dispose();
							}
						else if(e.getSource()==delete)
						{	if(list.isSelectionEmpty())
							{	delerr.setText("Select items first.");
								delerr.setVisible(true);
								}
							else
							{	delitem(list.getSelectedValues());
								delerr.setText("Item deleted successfully.");
								delerr.setVisible(true);
								}
							}
						}
					}
				main.center(j);
				j.pack();
				
				}
			
			}
		
	}
