package accounting;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class show_result extends index
{	private JFrame jf;
	private JPanel jp;
	private JButton ok;
	private JLabel fname,lname,address,amount,items;
	private JTextField fnameval,lnameval;
	private JTextArea addressval;
	private JScrollPane scroll;
	private JTable table;
	
	
	public show_result(ResultSet rs)
	{	jf=new JFrame();
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jf.setTitle("Customer's Transaction Details");
		jf.setVisible(true);
		jf.setResizable(false);
		
		jp=new JPanel();
		jp.setLayout(new GridBagLayout());
		
		fname=new JLabel("Customer's first name :");
		lname=new JLabel("Customer's last name :");
		address=new JLabel("Customer's address :");
		fnameval=new JTextField();
		fnameval.setEditable(false);
		lnameval=new JTextField();
		lnameval.setEditable(false);
		addressval=new JTextArea(5,20);
		addressval.setEditable(false);
		addressval.setLineWrap(true);
		addressval.setWrapStyleWord(true);
		amount=new JLabel("Amount (in Rs.)");
		items=new JLabel("Gift items");
				
		String[] head={"","Amount(in Rs.)","Items"};
				
		ok=new JButton("OK");
		try
		{	fnameval.setText(rs.getString("first_name"));
			lnameval.setText(rs.getString("last_name"));
			addressval.setText(rs.getString("address"));
			Object[][] data={	{"Sell",rs.getDouble("sell_amount"),rs.getString("sell")},
					{"Purchase",rs.getDouble("purchase_amount"),rs.getString("purchase")},
					{"Net",rs.getDouble("sell_amount")-rs.getDouble("purchase_amount")," "}
						};
			table=new JTable(data,head);
			table.setEnabled(false);
			table.getColumnModel().getColumn(2).setCellRenderer(new TableCellLongTextRenderer());
			}
		catch(SQLException e)
		{	JOptionPane.showMessageDialog(new JOptionPane(),"Something went wrong.\n\nCannot Connect to Server.\n\n"
				+ "Please make sure xampp is working properly.\n\n","Error",JOptionPane.ERROR_MESSAGE);
			System.exit(0);
			}
		
		jp.add(fname,gridval(0,0,1,1,GridBagConstraints.EAST,GridBagConstraints.NONE));
		jp.add(fnameval,gridval(1,0,2,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH));
		jp.add(lname,gridval(0,1,1,1,GridBagConstraints.EAST,GridBagConstraints.NONE));
		jp.add(lnameval,gridval(1,1,2,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH));
		jp.add(address,gridval(0,2,1,1,GridBagConstraints.NORTHEAST,GridBagConstraints.NONE));
		jp.add(addressval,gridval(1,2,2,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH));
		jp.add(amount,gridval(1,3,1,1,GridBagConstraints.CENTER,GridBagConstraints.NONE));
		jp.add(items,gridval(2,3,1,1,GridBagConstraints.CENTER,GridBagConstraints.NONE));
		jp.add(table,gridval(0,4,3,3,GridBagConstraints.CENTER,GridBagConstraints.BOTH));
		jp.add(ok,gridval(2,7,1,1,GridBagConstraints.EAST,GridBagConstraints.NONE));
		
		jf.add(jp);
		ok.requestFocusInWindow();		
		
		main.center(jf);
		jf.pack();
		
		Handler h=new Handler();
		ok.addActionListener(h);
		ok.addKeyListener(h);
		}
	
	public class TableCellLongTextRenderer extends JTextArea implements TableCellRenderer
	{	public Component getTableCellRendererComponent(JTable t,Object val,boolean isSelected,boolean hasFocus,int row,int column)
		{	this.setText((String)val);
			this.setWrapStyleWord(true);
			this.setLineWrap(true);
			setSize(table.getColumnModel().getColumn(column).getWidth(),getPreferredSize().height);
			if(table.getRowHeight(row)!=getPreferredSize().height)
			{	table.setRowHeight(row,getPreferredSize().height);
				}
			return this;
			}		
		}
	
	public class Handler extends KeyAdapter implements ActionListener
	{	public void actionPerformed(ActionEvent e)
		{	if(e.getSource()==ok)
				jf.dispose();
			}
		public void keyTyped(KeyEvent e)
		{	if(e.getKeyChar()==KeyEvent.VK_ENTER)
				jf.dispose();
			}
		}
	}
