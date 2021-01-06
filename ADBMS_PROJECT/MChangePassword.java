import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

public class MChangePassword extends JFrame implements ActionListener
{
	JLabel oldPassLabel, newPassLabel;
	JPasswordField oldPassPF, newPassPF;
	JButton changeBtn, backBtn, logoutBtn;
	JPanel panel;
	String userId;
	
	public MChangePassword(String userId)
	{
		super("");
		
		this.userId = userId;
		this.setSize(1300,700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setLayout(null);
		
		Font titleFont = new Font("Cambria",Font.ITALIC+Font.BOLD, 40);
		Font labelFont=new Font("Arial",  Font.ITALIC, 25);
		Font btnFont  =new Font("Arial",  Font.ITALIC, 25);
		
		//BUTTON 
		
		logoutBtn = new JButton("Logout");
		logoutBtn.setBounds(1130, 30, 120, 40);
		logoutBtn.setFont(btnFont);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		
		changeBtn = new JButton("Change");
		changeBtn.setBounds(600, 400, 150,40);
		changeBtn.setFont(btnFont);
		changeBtn.addActionListener(this);
		panel.add(changeBtn);
		
		backBtn = new JButton("Back");
		backBtn.setBounds(400, 400, 150,40);
		backBtn.setFont(btnFont);
		backBtn.addActionListener(this);
		panel.add(backBtn);
		
		//label 
		
		oldPassLabel = new JLabel("Old Password : ");
		oldPassLabel.setBounds(300, 210, 200, 40);
		oldPassLabel.setFont(labelFont);
		panel.add(oldPassLabel);
		
		newPassLabel = new JLabel("New Password : ");
		newPassLabel.setBounds(300, 270, 200, 40);
		newPassLabel.setFont(labelFont);
		panel.add(newPassLabel);
		
		//Text field
		
		Font tfFont=new Font("Arial",  Font.ITALIC, 20);
		
		oldPassPF = new JPasswordField();
		oldPassPF.setBounds(600, 210, 250, 40);
		oldPassPF.setFont(tfFont);
		panel.add(oldPassPF);
		
		newPassPF = new JPasswordField();
		newPassPF.setBounds(600, 270, 250, 40);
		newPassPF.setFont(tfFont);
		panel.add(newPassPF);
		
		this.add(panel);
	}
	
	
	public void actionPerformed(ActionEvent ae)
	{
		String text = ae.getActionCommand();
		
		if(text.equals(logoutBtn.getText()))
		{
			UserLogin lg = new UserLogin();
			lg.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(backBtn.getText()))
		{
			Customer c = new Customer(userId);
			c.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(changeBtn.getText()))
		{
			checkLogin();
		}
		else{}
	}
	
	public void checkLogin()
	{
		String query = "SELECT password FROM login WHERE loginID='"+userId+"'";     
        Connection con=null;//for connection
        Statement st = null;//for query execution
		ResultSet rs = null;//to get row by row result from DB
		System.out.println(query);
        try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");//load driver
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
				String password = rs.getString("password");
				
				if(password.equals(oldPassPF.getText()))
				{
					flag=true;
					System.out.println("flag true");
				}
			}
			if(flag)
			{
				String query0 = "UPDATE LOGIN SET  password = '"+newPassPF.getText()+"' WHERE loginID='"+userId+"'";		
				try
				{
					Class.forName("oracle.jdbc.driver.OracleDriver");//load driver
					con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","system");
					st = con.createStatement();//create statement
					st.executeUpdate(query0);
					System.out.println("Query 0 done");
					st.close();
					con.close();
					JOptionPane.showMessageDialog(this, "Success !!!");
					UserLogin ul=new UserLogin();
					ul.setVisible(true);
					this.setVisible(false);
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
			
			else if(!flag)
			{
				JOptionPane.showMessageDialog(this,"Invalid old Password"); 
				oldPassPF.setText("");
				newPassPF.setText("");
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