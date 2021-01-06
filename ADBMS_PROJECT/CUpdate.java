import java.lang.*;import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.util.*;

public class CUpdate extends JFrame implements ActionListener
{
	JLabel titleLabel,userIdLabel, uNameLabel, phnLabel, passLabel, emailIdLabel;
	JTextField idText,nameText,phoneText,emailText;
	JButton logoutBtn, backBtn,updateBtn;
	JPanel panel;
	String userId,uName,phnNo,emaiId;
	
	public CUpdate(String userId)
	{
		super("Customer info");
		
		this.userId=userId;
		this.setSize(1300,700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setLayout(null);
		
		Font titleFont = new Font("Cambria",Font.ITALIC+Font.BOLD, 40);
		Font labelFont=new Font("Arial",  Font.ITALIC, 25);
		Font btnFont  =new Font("Arial",  Font.ITALIC, 25);
		
		titleLabel = new JLabel("My Info ");
		titleLabel.setBounds(550,50,500,60);
		titleLabel.setFont(titleFont);
		panel.add(titleLabel);
		
		//LABEL FIELD//
		
		userIdLabel = new JLabel("User ID  ");
		userIdLabel.setBounds(350, 150, 150, 40);
		userIdLabel.setFont(labelFont);
		panel.add(userIdLabel);
		
		uNameLabel = new JLabel("User Name  ");
		uNameLabel.setBounds(350, 220, 150, 40);
		uNameLabel.setFont(labelFont);
		panel.add(uNameLabel);
		
		
		/*phnLabel = new JLabel("Phone no    ");
		phnLabel.setBounds(350, 290, 150, 40);
		phnLabel.setFont(labelFont);
		panel.add(phnLabel);*/
		
		emailIdLabel = new JLabel("Email ID  ");
		emailIdLabel.setBounds(350, 290, 150, 40);
		emailIdLabel.setFont(labelFont);
		panel.add(emailIdLabel);
		
		
		//get data
		

		String query = "SELECT * FROM CUSTOMER WHERE CUSTOMERID ='"+userId+"'";     
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
			String Name = null;
			
			String emailid = null;			
			while(rs.next())
			{
                Name = rs.getString("CUSTOMERNAME");
				//phnno = rs.getString("phoneNumber");
				emailid = rs.getString("EMAIL");
				flag=true;
				uName=Name;
				
				emaiId=emailid;
				break;
			}
			if(flag)
			{
				
				
				
			}
			if(!flag)
			{
				
				JOptionPane.showMessageDialog(this,"Something Wrong!"); 
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
		
		//result field//
		
		Font tfFont=new Font("Arial",  Font.ITALIC, 20);
		
		idText = new JTextField(userId);
		idText.setBounds(600, 150, 250, 40);
		idText.setFont(tfFont);
		panel.add(idText);
		idText.setEnabled(false);
		
		nameText = new JTextField(uName);
		nameText.setBounds(600, 220, 250, 40);
		nameText.setFont(tfFont);
		panel.add(nameText);
		
	
		
		/*phoneText = new JTextField(phnNo);
		phoneText.setBounds(600, 290, 250, 40);
		phoneText.setFont(tfFont);
		panel.add(phoneText);*/
		
		emailText = new JTextField(emaiId);
		emailText.setBounds(600, 290, 250, 40);
		emailText.setFont(tfFont);
		panel.add(emailText);
		
		//BUTTON FIELD//
		
		logoutBtn = new JButton("Logout");
		logoutBtn.setBounds(1130, 30, 120, 40);
		logoutBtn.setFont(btnFont);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		backBtn = new JButton("Back");
		backBtn.setBounds(400, 500, 150,40);
		backBtn.setFont(btnFont);
		backBtn.addActionListener(this);
		panel.add(backBtn);
		
		updateBtn = new JButton("Update");
		updateBtn.setBounds(600, 500, 150,40);
		updateBtn.setFont(btnFont);
		updateBtn.addActionListener(this);
		panel.add(updateBtn);
		
		
		
		this.add(panel);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String text = ae.getActionCommand();
		
		if(text.equals(backBtn.getText()))
		{
			CInfo c= new CInfo(userId);
			c.setVisible(true);
			this.setVisible(false);
		}
		
		else if(text.equals(logoutBtn.getText()))
		{
			UserLogin ul=new UserLogin();
			ul.setVisible(true);
			this.setVisible(false);
		}
		
		else if (text.equals(updateBtn.getText()))
		{
			updateInDB();
		}
	}
	
	
	public void updateInDB()
	{
		String newId = idText.getText();
		String cName = nameText.getText();
		//String phnNo = phoneText.getText();
		String email = emailText.getText();

		String query = "UPDATE CUSTOMER SET CUSTOMERNAME='"+cName+"',  EMAIL = '"+email+"' WHERE CUSTOMERID='"+newId+"'";	
        Connection con=null;//for connection
        Statement st = null;//for query execution
		System.out.println(query);
        try
		{
	
			Class.forName("oracle.jdbc.driver.OracleDriver");//load driver
			System.out.println("driver loaded");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","system");
			st = con.createStatement();//create statement
			st.executeUpdate(query);
			System.out.println(query);
			st.close();
			con.close();
			JOptionPane.showMessageDialog(this, "Success !!!");
			
			CInfo c=new CInfo(userId);
			c.setVisible(true);
			this.setVisible(false);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(this, "Oops !!!");
		}
	}
	
	
}
