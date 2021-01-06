import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

public class AdminLogin extends JFrame implements ActionListener
{
	JLabel title, userLabel, passLabel, label1;
	JTextField userTF;
	JPasswordField passPF;
	JButton loginBtn, backBtn;
	JPanel panel;
	
	public AdminLogin()
	{
		super("Admin Login Window");
		this.setSize(1300,700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setLayout(null);
		
		Font titleFont = new Font("Cambria", Font.ITALIC + Font.BOLD, 35);
		
		title = new JLabel("Admin Login");
		title.setBounds(550, 100, 400, 60);
		title.setFont(titleFont);
		panel.add(title);
		
		Font labelFont=new Font("Arial",  Font.ITALIC+Font.BOLD, 25);
		Font btnFont  =new Font("Arial",  Font.ITALIC, 25);
		
		Font tfFont=new Font("Arial",  Font.ITALIC, 20);
		
		userLabel = new JLabel("Admin ID    : ");
		userLabel.setBounds(350, 250, 150, 40);
		userLabel.setFont(labelFont);
		panel.add(userLabel);
		
		userTF = new JTextField();
		userTF.setBounds(550, 250, 250, 40);
		userTF.setFont(tfFont);
		panel.add(userTF);
		
		passLabel = new JLabel("Password  : ");
		passLabel.setBounds(350, 300, 150, 40);
		passLabel.setFont(labelFont);
		panel.add(passLabel);
		
		passPF = new JPasswordField();
		passPF.setBounds(550, 300, 250, 40);
		passPF.setFont(tfFont);
		panel.add(passPF);
		
		loginBtn = new JButton("Login");
		loginBtn.setBounds(580, 380, 150, 40);
		loginBtn.setFont(btnFont);
		loginBtn.addActionListener(this);
		panel.add(loginBtn);
		
		
		backBtn = new JButton("Back");
		backBtn.setBounds(400, 380, 150, 40);
		backBtn.setFont(btnFont);
		backBtn.addActionListener(this);
		panel.add(backBtn);
		
	    
		
		this.add(panel);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String text = ae.getActionCommand();
		
		if(text.equals(loginBtn.getText()))
		{
			checkLogin();
		}
		else if(text.equals(backBtn.getText()))
		{
			UserLogin ul = new UserLogin();
			ul.setVisible(true);
			this.setVisible(false);
		}
		else{}
	}
	
	public void checkLogin()
	{
		String query = "SELECT `adminId`, `password`, `status` FROM `adminlogin`;";     
        Connection con=null;//for connection
        Statement st = null;//for query execution
		ResultSet rs = null;//to get row by row result from DB
		System.out.println(query);
        try
		{
			Class.forName("com.mysql.jdbc.Driver");//load driver
			System.out.println("driver loaded");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","system");
			System.out.println("connection done");//connection with database established
			st = con.createStatement();//create statement
			System.out.println("statement created");
			rs = st.executeQuery(query);//getting result
			System.out.println("results received");
			
			boolean flag = false;			
			while(rs.next())
			{
                String adminId = rs.getString("adminId");
				String password = rs.getString("password");
				int status = rs.getInt("status");
				
				if(adminId.equals(userTF.getText()) && password.equals(passPF.getText()))
				{
					flag=true;
					if(status==0)
					{
						ManagerHome mh = new ManagerHome(adminId);
						mh.setVisible(true);
						this.setVisible(false);
					}
					else if(status==1)
					{
						EmployeeHome eh = new EmployeeHome(adminId);
						eh.setVisible(true);
						this.setVisible(false);
					}
					else{}
				}
			}
			if(!flag)
			{
				JOptionPane.showMessageDialog(this,"Invalid ID or Password"); 
				userTF.setText("");
				passPF.setText("");
			}
		}
        catch(Exception ex)
		{
			System.out.println("Exception : " +ex.getMessage());
        }
        finally
		{
            try
			{
                if(rs!=null)
					rs.close();

                if(st!=null)
					st.close();

                if(con!=null)
					con.close();
            }
            catch(Exception ex){}
        }
	}
}