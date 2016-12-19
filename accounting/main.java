package accounting;
import java.awt.*;
import javax.swing.*;

public class main 
{	public static void main(String[] args)
	{	connect c=new connect();
		login log=new login();
		}
	
	public static void center(JFrame j)
	{	Toolkit tk=Toolkit.getDefaultToolkit();
		Dimension d=tk.getScreenSize();
		j.setLocation((d.width-j.getWidth())/2,(d.height-j.getHeight())/2);
		}
	}
