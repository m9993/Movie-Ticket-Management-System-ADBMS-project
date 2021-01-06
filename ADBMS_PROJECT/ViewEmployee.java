import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

public class ViewEmployee extends JFrame implements ActionListener
{
	JLabel userLabel, eNameLabel, phoneLabel, roleLabel, salaryLabel;
	JTextField userTF, phoneTF1, phoneTF2, eNameTF, roleTF, salaryTF;
	JButton refreshBtn, loadBtn, updateBtn, delBtn, backBtn, logoutBtn;
	JPanel panel;
	String userId;
	
	public ViewEmployee(String userId)
	{
		super("Sample Management System - View Employee");
		
		this.setSize(1300,700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.userId = userId;
		
		
		Font labelFont=new Font("Arial",  Font.ITALIC+Font.BOLD, 25);
		Font btnFont  =new Font("Arial",  Font.ITALIC, 25);
		
		panel = new JPanel();
		panel.setLayout(null);
		
		logoutBtn = new JButton("Logout");
		logoutBtn.setBounds(1130, 30, 120, 40);
		logoutBtn.setFont(btnFont);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		//LABEL FIELD//
		
		userLabel = new JLabel("User ID : ");
		userLabel.setBounds(300, 150, 150, 40);
		userLabel.setFont(labelFont);
		panel.add(userLabel);
		
		eNameLabel = new JLabel("Name : ");
		eNameLabel.setBounds(300, 210, 150, 40);
		eNameLabel.setFont(labelFont);
		panel.add(eNameLabel);
		eNameLabel.setVisible(false);
		
		roleLabel = new JLabel("Role : ");
		roleLabel.setBounds(300, 270, 150, 40);
		roleLabel.setFont(labelFont);
		panel.add(roleLabel);
		roleLabel.setVisible(false);
		
		phoneLabel = new JLabel("Phone no : ");
		phoneLabel.setBounds(300, 330, 150, 40);
		phoneLabel.setFont(labelFont);
		panel.add(phoneLabel);
		phoneLabel.setVisible(false);
		
		salaryLabel = new JLabel("Salary : ");
		salaryLabel.setBounds(300, 390, 150, 40);
		salaryLabel.setFont(labelFont);
		panel.add(salaryLabel);
		salaryLabel.setVisible(false);
		
		
		//TextFeild//
		
		Font tfFont=new Font("Arial",  Font.ITALIC, 20);
		
		userTF = new JTextField();
		userTF.setBounds(550, 150, 250, 40);
		userTF.setFont(tfFont);
		panel.add(userTF);

		
		eNameTF = new JTextField();
		eNameTF.setBounds(550, 210, 250, 40);
		eNameTF.setFont(tfFont);
		panel.add(eNameTF);
		eNameTF.setVisible(false);
		
		roleTF = new JTextField();
		roleTF.setBounds(550, 270, 250, 40);
		roleTF.setFont(tfFont);
		panel.add(roleTF);
		roleTF.setVisible(false);
		
		
		phoneTF1 = new JTextField("+880");
		phoneTF1.setBounds(550, 330, 50, 40);
		phoneTF1.setFont(tfFont);
		phoneTF1.setEnabled(false);
		phoneTF1.setForeground(Color.BLACK);
		panel.add(phoneTF1);
		phoneTF1.setVisible(false);
		
		phoneTF2 = new JTextField();
		phoneTF2.setBounds(600, 330, 200, 40);
		phoneTF2.setFont(tfFont);
		panel.add(phoneTF2);
		phoneTF2.setVisible(false);
		

		salaryTF = new JTextField();
		salaryTF.setBounds(550, 390, 250, 40);
		salaryTF.setFont(tfFont);
		panel.add(salaryTF);
		salaryTF.setVisible(false);
		
		//BUTTON
		
		backBtn = new JButton("Back");
		backBtn.setBounds(400, 250, 150,40);
		backBtn.setFont(btnFont);
		backBtn.addActionListener(this);
		panel.add(backBtn);
		
		loadBtn = new JButton("Load");
		loadBtn.setBounds(600, 250, 150,40);
		loadBtn.setFont(btnFont);
		loadBtn.addActionListener(this);
		panel.add(loadBtn);
		
		refreshBtn = new JButton("Refresh");
		refreshBtn.setBounds(300, 500, 150, 40);
		refreshBtn.setFont(btnFont);
		refreshBtn.addActionListener(this);
		panel.add(refreshBtn);
		refreshBtn.setVisible(false);
		
		updateBtn = new JButton("Update");
		updateBtn.setBounds(490, 500, 120, 40);
		updateBtn.setFont(btnFont);
		updateBtn.addActionListener(this);
		panel.add(updateBtn);
		updateBtn.setVisible(false);
		
		delBtn = new JButton("Delete");
		delBtn.setBounds(650, 500, 120, 40);
		delBtn.setFont(btnFont);
		delBtn.addActionListener(this);
		panel.add(delBtn);
		delBtn.setVisible(false);
		
		this.add(panel);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String text = ae.getActionCommand();
		
		if(text.equals(backBtn.getText()))
		{
			ManagerHome me = new ManagerHome(userId);
			me.setVisible(true);
			this.setVisible(false);
		}
		if(text.equals(refreshBtn.getText()))
		{
			userTF.setEnabled(true);
			loadBtn.setVisible(true);
			backBtn.setVisible(true);
			eNameLabel.setVisible(false);
			eNameTF.setVisible(false);
			phoneLabel.setVisible(false);
			phoneTF1.setVisible(false);
			phoneTF2.setVisible(false);
			roleLabel.setVisible(false);
			roleTF.setVisible(false);
			salaryLabel.setVisible(false);
			salaryTF.setVisible(false);
			refreshBtn.setVisible(false);
			updateBtn.setVisible(false);
			delBtn.setVisible(false);
			userTF.setText("");
		}
		else if(text.equals(logoutBtn.getText()))
		{
			UserLogin lg = new UserLogin();
			lg.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(loadBtn.getText()))
		{
			loadFromDB();			
		}
		else if(text.equals(updateBtn.getText()))
		{
			updateInDB();
		}
		else if(text.equals(delBtn.getText()))
		{
			deleteFromDB();
		}
		else{}
	}
	
	public void loadFromDB()
	{
		String loadId = userTF.getText();
		String query = "select empid, empname, role, salary,phoneNumber from employee where EMPID='"+loadId+"'";     
        Connection con=null;//for connection
        Statement st = null;//for query execution
		ResultSet rs = null;//to get row by row result from DB
		System.out.println(query);
        try
		{
			
			System.out.println("driver loaded");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","system");
			System.out.println("connection done");//connection with database established
			st = con.createStatement();//create statement
			System.out.println("statement created");
			rs = st.executeQuery(query);//getting result
			System.out.println("results received");
			
			boolean flag = false;
			String eName = null;
			String phnNo = null;
			String role = null;
			String salary=null;			
			while(rs.next())
			{
                eName = rs.getString("empname");
				phnNo = rs.getString("phoneNumber");
				role = rs.getString("role");
				salary = rs.getString("salary");
				flag=true;
				break;
			}
			if(flag)
			{
				
				userTF.setEnabled(false);
				loadBtn.setVisible(false);
				backBtn.setVisible(false);
				eNameLabel.setVisible(true);
				eNameTF.setVisible(true);
				phoneLabel.setVisible(true);
				phoneTF1.setVisible(true);
				phoneTF2.setVisible(true);
				roleLabel.setVisible(true);
				roleTF.setVisible(true);
				salaryLabel.setVisible(true);
				salaryTF.setVisible(true);
				refreshBtn.setVisible(true);
				updateBtn.setVisible(true);
				delBtn.setVisible(true);
				
				
				eNameTF.setText(eName);
				System.out.println(""+eName);
				phoneTF1.setText("+880");
				phoneTF2.setText(phnNo.substring(4,14));
				System.out.println(""+phnNo);
				roleTF.setText(role);
				System.out.println(""+role);
				salaryTF.setText(salary);
				System.out.println(""+salary);
				userTF.setEnabled(false);
				updateBtn.setEnabled(true);
				delBtn.setEnabled(true);
			}
			if(!flag)
			{
				eNameTF.setText("");
				phoneTF1.setText("");
				phoneTF2.setText("");
				roleTF.setText("");
				salaryTF.setText("");
				JOptionPane.showMessageDialog(this,"Invalid ID"); 
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
	
	public void updateInDB()
	{
		String newId = userTF.getText();
		String eName = eNameTF.getText();
		String phnNo = phoneTF1.getText()+phoneTF2.getText();
		String role = roleTF.getText();
		String salary=salaryTF.getText();
		String query = "UPDATE EMPLOYEE SET EMPNAME='"+eName+"', phoneNumber = '"+phnNo+"', role = '"+role+"', salary = '"+salary+"' WHERE EMPID='"+newId+"'";	
        Connection con=null;//for connection
        Statement st = null;//for query execution
		System.out.println(query);
        try
		{
	
			
			System.out.println("driver loaded");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","system");
			st = con.createStatement();//create statement
			st.executeUpdate(query);
			st.close();
			con.close();
			JOptionPane.showMessageDialog(this, "Success !!!");
			
			userTF.setEnabled(true);
			loadBtn.setVisible(true);
			backBtn.setVisible(true);
			eNameLabel.setVisible(false);
			eNameTF.setVisible(false);
			phoneLabel.setVisible(false);
			phoneTF1.setVisible(false);
			phoneTF2.setVisible(false);
			roleLabel.setVisible(false);
			roleTF.setVisible(false);
			salaryLabel.setVisible(false);
			salaryTF.setVisible(false);
			refreshBtn.setVisible(false);
			updateBtn.setVisible(false);
			delBtn.setVisible(false);
			userTF.setText("");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(this, "Oops !!!");
		}
	}
	
	
	public void deleteFromDB()
	{
		String newId = userTF.getText();
		String query1 = "DELETE from employeeinfo WHERE employeeId='"+newId+"';";
		String query2 = "DELETE from adminlogin WHERE adminId='"+newId+"';";
		System.out.println(query1);
		System.out.println(query2);
        try
		{
			
			System.out.println("driver loaded");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","system");
			Statement stm = con.createStatement();
			stm.execute(query1);
			stm.execute(query2);
			stm.close();
			con.close();
			JOptionPane.showMessageDialog(this, "Success !!!");
			
			userTF.setEnabled(true);
			loadBtn.setVisible(true);
			backBtn.setVisible(true);
			eNameLabel.setVisible(false);
			eNameTF.setVisible(false);
			phoneLabel.setVisible(false);
			phoneTF1.setVisible(false);
			phoneTF2.setVisible(false);
			roleLabel.setVisible(false);
			roleTF.setVisible(false);
			salaryLabel.setVisible(false);
			salaryTF.setVisible(false);
			refreshBtn.setVisible(false);
			updateBtn.setVisible(false);
			delBtn.setVisible(false);
			userTF.setText("");
		}
        catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this, "Oops !!!");
        }
	}
}