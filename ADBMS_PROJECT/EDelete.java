import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.util.*;

public class EDelete extends JFrame implements ActionListener
{
	JLabel textLabel;
	JButton backBtn,dltBtn;
	JPanel panel;
	String userId;
	
	public EDelete(String userId)
	{
		super("");
		
		this.setSize(1300,700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.userId = userId;
		
		panel = new JPanel();
		panel.setLayout(null);
		
		
		Font labelFont=new Font("Arial",  Font.ITALIC+Font.BOLD, 35);
		Font btnFont  =new Font("Arial",  Font.ITALIC, 25);
		
		textLabel=new JLabel("Do You Want to Delete Your Account?");
		textLabel.setBounds(300,200,700,60);
		textLabel.setFont(labelFont);
		panel.add(textLabel);
		
		backBtn = new JButton("Back");
		backBtn.setBounds(400, 350, 150,40);
		backBtn.setFont(btnFont);
		backBtn.addActionListener(this);
		panel.add(backBtn);
		
		dltBtn = new JButton("Delete");
		dltBtn.setBounds(650, 350, 150,40);
		dltBtn.setFont(btnFont);
		dltBtn.addActionListener(this);
		panel.add(dltBtn);
		
		this.add(panel);
		
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String text = ae.getActionCommand();
		
		if(text.equals(backBtn.getText()))
		{
			Customer c= new Customer(userId);
			c.setVisible(true);
			this.setVisible(false);
		}
		
		else if(text.equals(dltBtn.getText()))
		{
			deleteFromDB();
		}
		
		else{}
	}
	
	public void deleteFromDB()
	{

		String query1 = "DELETE from employee WHERE LOGINID='"+userId+"'";
		String query2 = "DELETE from LOGIN WHERE LOGINID='"+userId+"'";
		System.out.println(query1);
		System.out.println(query2);
        try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");//load driver
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","system");
			Statement stm = con.createStatement();
			stm.execute(query1);
			stm.execute(query2);
			stm.close();
			con.close();
			JOptionPane.showMessageDialog(this, "Success !!!");
			
			AdminLogin al =new AdminLogin();
			al.setVisible(true);
			this.setVisible(false);
			
			
		}
        catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this, "Oops !!!");
			System.out.println("Exception : " +ex.getMessage());
        }
	}
}