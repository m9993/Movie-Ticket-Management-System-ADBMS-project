import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.util.*;

public class Signup extends JFrame implements ActionListener
{
	JLabel titleLabel,userIdLabel, uNameLabel, phnLabel, passLabel, emailIdLabel;
	JTextField userIdTF, uNameTF, passTF, phoneTF1, phoneTF2, emailTF;
	JPasswordField passPF;
	JButton autoPassBtn, submitBtn, backBtn;
	JPanel panel;
	
	public Signup()
	{
		super("User Signup");
		
		this.setSize(1300,700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setLayout(null);
		
		Font titleFont = new Font("Cambria",Font.ITALIC+Font.BOLD, 40);
		Font labelFont=new Font("Arial",  Font.ITALIC, 25);
		Font btnFont  =new Font("Arial",  Font.ITALIC, 25);
		
		titleLabel = new JLabel("New User Registration ");
		titleLabel.setBounds(450,50,500,60);
		titleLabel.setFont(titleFont);
		panel.add(titleLabel);
		
		//LABEL FIELD//
		
		userIdLabel = new JLabel("User ID : ");
		userIdLabel.setBounds(300, 150, 150, 40);
		userIdLabel.setFont(labelFont);
		panel.add(userIdLabel);
		
		uNameLabel = new JLabel("User Name : ");
		uNameLabel.setBounds(300, 210, 150, 40);
		uNameLabel.setFont(labelFont);
		panel.add(uNameLabel);
		
		passLabel = new JLabel("Password : ");
		passLabel.setBounds(300, 270, 150, 40);
		passLabel.setFont(labelFont);
		panel.add(passLabel);
		
		phnLabel = new JLabel("Phone no. : ");
		phnLabel.setBounds(300, 330, 150, 40);
		phnLabel.setFont(labelFont);
		panel.add(phnLabel);
		
		emailIdLabel = new JLabel("Email ID : ");
		emailIdLabel.setBounds(300, 390, 150, 40);
		emailIdLabel.setFont(labelFont);
		panel.add(emailIdLabel);
		
		//TextFeild//
		
		Font tfFont=new Font("Arial",  Font.ITALIC, 20);
		
		userIdTF = new JTextField();
		userIdTF.setBounds(550, 150, 250, 40);
		userIdTF.setFont(tfFont);
		panel.add(userIdTF);
		
		uNameTF = new JTextField();
		uNameTF.setBounds(550, 210, 250, 40);
		uNameTF.setFont(tfFont);
		panel.add(uNameTF);
		
		passTF = new JTextField();
		passTF.setBounds(550, 270, 250, 40);
		passTF.setFont(tfFont);
		passTF.setEnabled(false);
		panel.add(passTF);
		
		phoneTF1 = new JTextField("+880");
		phoneTF1.setBounds(550, 330, 50, 40);
		phoneTF1.setFont(tfFont);
		phoneTF1.setEnabled(false);
		phoneTF1.setForeground(Color.BLACK);
		panel.add(phoneTF1);
		
		phoneTF2 = new JTextField();
		phoneTF2.setBounds(600, 330, 200, 40);
		phoneTF2.setFont(tfFont);
		panel.add(phoneTF2);
		
		emailTF = new JTextField();
		emailTF.setBounds(550, 390, 250, 40);
		emailTF.setFont(tfFont);
		panel.add(emailTF);
		
		//BUTTON FIELD//
		
		autoPassBtn = new JButton("Generate");
		autoPassBtn.setBounds(850, 270, 150, 40);
		autoPassBtn.setFont(btnFont);		
		autoPassBtn.addActionListener(this);
		panel.add(autoPassBtn);
		
		submitBtn = new JButton("Submit");
		submitBtn.setBounds(600, 500, 150,40);
		submitBtn.setFont(btnFont);
		submitBtn.addActionListener(this);
		panel.add(submitBtn);
		
		backBtn = new JButton("Back");
		backBtn.setBounds(400, 500, 150,40);
		backBtn.setFont(btnFont);
		backBtn.addActionListener(this);
		panel.add(backBtn);
		
		
		
		this.add(panel);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String text = ae.getActionCommand();
		
		if(text.equals(backBtn.getText()))
		{
			UserLogin lg= new UserLogin();
			lg.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(autoPassBtn.getText()))
		{
			Random r = new Random();
			passTF.setText(r.nextInt(89999999)+10000000+"");
			autoPassBtn.setEnabled(false);
		}
		else if(text.equals(submitBtn.getText()))
		{
			insertIntoDB();
		}
		else{}
	}
	
	public void insertIntoDB()
	{
		String newId = userIdTF.getText();
		String newPass = passTF.getText();
		String uName = uNameTF.getText();
		String phnNo = phoneTF1.getText()+phoneTF2.getText();
	    String emaiId = emailTF.getText();
		
		
		String query1 = "INSERT INTO CUSTOMER VALUES ('"+newId+"','"+uName+"','"+ newId+"','"+emaiId+"')";
		String query2 = "INSERT INTO LOGIN VALUES ('"+newId+"','"+newPass+"',2)";
		System.out.println(query1);
		System.out.println(query2);
        
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","system");
			Statement stm = con.createStatement();
			stm.execute(query1);
			stm.execute(query2);
			stm.close();
			con.close();
			JOptionPane.showMessageDialog(this, "Success!");
			UserLogin lg= new UserLogin();
			lg.setVisible(true);
			this.setVisible(false);
		}
        catch(Exception ex)
		{
			System.out.println("Exception : " +ex.getMessage());
			JOptionPane.showMessageDialog(this, "Oops !!!");
        }
    }
}
