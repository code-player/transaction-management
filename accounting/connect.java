package accounting;
import java.sql.*;
import javax.swing.*;

public class connect
{	public PreparedStatement st;
	public ResultSet rs;
	public Connection con;
	
	public connect()
	{	try
		{	Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost/java","root","password");
			}
		catch(Exception e)
		{	JOptionPane.showMessageDialog(new JOptionPane(),"Something went wrong.\n\nCannot Connect to Server.\n\n"
				+ "Please make sure xampp is already started.\n\n","Error",JOptionPane.ERROR_MESSAGE);
			System.exit(0);
			}
		}
	}
