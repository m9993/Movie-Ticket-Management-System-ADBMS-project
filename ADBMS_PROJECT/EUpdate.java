import java.lang.*;import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.util.*;

public class EUpdate extends JFrame implements ActionListener
{
	JLabel titleLabel,userIdLabel, uNameLabel, phnLabel, salaryLabel, roleLabel;
	JTextField idText,nameText,phoneTF1,phoneTF2,roleText,salaryText;
	JButton logoutBtn, backBtn,updateBtn;
	JPanel panel;
	String userId,uName,phnNo,role,salary;
	
	public EUpdate(String userId)
	{
		super("Empolyee Info");
		
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
		uNameLabel.setBounds(300, 220, 220, 40);
		uNameLabel.setFont(labelFont);
		panel.add(uNameLabel);
		
		
		phnLabel = new JLabel("Phone no    ");
		phnLabel.setBounds(300, 290, 200, 40);
		phnLabel.setFont(labelFont);
		panel.add(phnLabel);
		
		roleLabel = new JLabel("Role  ");
		roleLabel.setBounds(300, 360, 200, 40);
		roleLabel.setFont(labelFont);
		panel.add(roleLabel);
		
		salaryLabel = new JLabel("Salary  ");
		salaryLabel.setBounds(300, 430, 200, 40);
		salaryLabel.setFont(labelFont);
		panel.add(salaryLabel);
		
		
		//get data
		

		String query = "SELECT * FROM EMPLOYEE WHERE EMPID ='"+userId+"'";     
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
                Name = rs.getString("EMPNAME");
				phnno = rs.getString("PHONENO");
				r = rs.getString("role");
				s = rs.getString("salary");
				flag=true;
				uName=Name;
				phnNo=phnno;
				role=r;
				salary=s;
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
		
	
		
		phoneTF1 = new JTextField("+880");
		phoneTF1.setBounds(600, 290, 50, 40);
		phoneTF1.setFont(tfFont);
		phoneTF1.setEnabled(false);
		phoneTF1.setForeground(Color.BLACK);
		panel.add(phoneTF1);
		
		phoneTF2 = new JTextField();
		phoneTF2.setBounds(650, 290, 200, 40);
		phoneTF2.setFont(tfFont);
		phoneTF2.setText(phnNo.substring(4,14));
		panel.add(phoneTF2);
		
		roleText = new JTextField(role);
		roleText.setBounds(600, 360, 250, 40);
		roleText.setFont(tfFont);
		panel.add(roleText);
		roleText.setEnabled(false);
		
		salaryText = new JTextField(salary);
		salaryText.setBounds(600, 430, 250, 40);
		salaryText.setFont(tfFont);
		panel.add(salaryText);
		salaryText.setEnabled(false);
		
		
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
			EInfo c= new EInfo(userId);
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
		
		String eName = nameText.getText();
		String phnNo = phoneTF1.getText()+phoneTF2.getText();
		String role = roleText.getText();
		String salary=salaryText.getText();
		String query = "UPDATE employee SET empname='"+eName+"', PHONENO = '"+phnNo+"', role = '"+role+"', salary = '"+salary+"' WHERE EMPID='"+userId+"'";	
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
			st.close();
			con.close();
			JOptionPane.showMessageDialog(this, "Success !!!");
			
			EInfo ei=new EInfo(userId);
			ei.setVisible(true);
			this.setVisible(false);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(this, "Oops !!!");
		}
	}
}
