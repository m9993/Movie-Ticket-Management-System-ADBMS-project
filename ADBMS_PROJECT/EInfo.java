import java.lang.*;import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.util.*;

public class EInfo extends JFrame implements ActionListener
{
	JLabel titleLabel,userIdLabel, uNameLabel, phnLabel, passLabel,salaryLabel, RoleLabel,roleLabel,sLabel,nameLabel,idLabel,phoneLabel;
	JButton logoutBtn, backBtn,changeBtn;
	JPanel panel;
	String userId,uName,phnNo,role,salary;
	
	public EInfo(String userId)
	{
		super("Employee info");
		
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
		
		userIdLabel = new JLabel("Employee ID  ");
		userIdLabel.setBounds(300, 150, 200, 40);
		userIdLabel.setFont(labelFont);
		panel.add(userIdLabel);
		
		uNameLabel = new JLabel("Employee Name  ");
		uNameLabel.setBounds(300, 220, 250, 40);
		uNameLabel.setFont(labelFont);
		panel.add(uNameLabel);
		
		
		/*phnLabel = new JLabel("Phone no    ");
		phnLabel.setBounds(300, 290, 200, 40);
		phnLabel.setFont(labelFont);
		panel.add(phnLabel);*/
		
		RoleLabel = new JLabel("Role  ");
		RoleLabel.setBounds(300, 290, 200, 40);
		RoleLabel.setFont(labelFont);
		panel.add(RoleLabel);
		
		salaryLabel = new JLabel("Salary  ");
		salaryLabel.setBounds(300, 360, 200, 40);
		salaryLabel.setFont(labelFont);
		panel.add(salaryLabel);
		//get data
		

		String query = "SELECT * FROM EMPLOYEE_VIEW";     
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
			String phnno = null;
			String r = null;			
			String s = null;			
			while(rs.next())
			{
                if(userId.equals(rs.getString("LOGINID")))
				{
					Name = rs.getString("EMPNAME");
					phnno = rs.getString("phoneNumber");
					r = rs.getString("ROLE");
					s=rs.getString("SALARY");
					flag=true;
					uName=Name;
					//phnNo=phnno;
					salary=s;
					role=r;
					break;
				}
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
		
		idLabel = new JLabel(userId);
		idLabel.setBounds(600, 150, 250, 40);
		idLabel.setFont(tfFont);
		panel.add(idLabel);
		
		nameLabel = new JLabel(uName);
		nameLabel.setBounds(600, 220, 300, 40);
		nameLabel.setFont(tfFont);
		panel.add(nameLabel);
		
	
		
		/*phoneLabel = new JLabel(phnNo);
		phoneLabel.setBounds(600, 290, 250, 40);
		phoneLabel.setFont(tfFont);
		panel.add(phoneLabel);*/
		
		roleLabel = new JLabel(role);
		roleLabel.setBounds(600, 290, 250, 40);
		roleLabel.setFont(tfFont);
		panel.add(roleLabel);
		
		sLabel = new JLabel(salary);
		sLabel.setBounds(600, 360, 250, 40);
		sLabel.setFont(tfFont);
		panel.add(sLabel);
		
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
		
		changeBtn = new JButton("Change");
		changeBtn.setBounds(600, 500, 150,40);
		changeBtn.setFont(btnFont);
		changeBtn.addActionListener(this);
		panel.add(changeBtn);
		
		
		
		this.add(panel);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String text = ae.getActionCommand();
		
		if(text.equals(backBtn.getText()))
		{
			EmployeeHome eh= new EmployeeHome(userId);
			eh.setVisible(true);
			this.setVisible(false);
		}
		
		else if(text.equals(changeBtn.getText()))
		{
			EUpdate cu= new EUpdate(userId);
			cu.setVisible(true);
			this.setVisible(false);
		}
		
		else if(text.equals(logoutBtn.getText()))
		{
			UserLogin al=new UserLogin();
			al.setVisible(true);
			this.setVisible(false);
		}
		
		else{}
	}
	
}
