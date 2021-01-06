import java.lang.*;import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.util.*;

public class MyTicket extends JFrame implements ActionListener
{
	JLabel titleLabel,movieNameLabel,mLabel[],dateLabel,dLabel[],timeLabel,tLabel[],seatLabel,sLabel[];
	JButton logoutBtn, backBtn;
	JPanel panel;
	String userId,uName,phnNo,emaiId;
	String movieName[] = new String[5];
	String date[] = new String[5];
	String time[]=new String[5];
	int seat[]=new int[5];
	
	public MyTicket(String userId)
	{
		super("");
		
		this.userId=userId;
		this.setSize(1300,700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setLayout(null);
		
		Font titleFont = new Font("Cambria",Font.ITALIC+Font.BOLD, 40);
		Font labelFont=new Font("Arial",  Font.BOLD, 25);
		Font btnFont  =new Font("Arial",  Font.ITALIC, 25);
		
		titleLabel = new JLabel("My Ticket ");
		titleLabel.setBounds(550,50,500,60);
		titleLabel.setFont(titleFont);
		panel.add(titleLabel);
		
		//LABEL FIELD//
		
		movieNameLabel = new JLabel("");
		movieNameLabel.setBounds(200, 150, 150, 40);
		movieNameLabel.setFont(labelFont);
		panel.add(movieNameLabel);
		
		dateLabel = new JLabel("");
		dateLabel.setBounds(500, 150, 150, 40);
		dateLabel.setFont(labelFont);
		panel.add(dateLabel);
		
		timeLabel = new JLabel("");
		timeLabel.setBounds(720, 150, 150, 40);
		timeLabel.setFont(labelFont);
		panel.add(timeLabel);
		
		seatLabel = new JLabel("");
		seatLabel.setBounds(950, 150, 150, 40);
		seatLabel.setFont(labelFont);
		panel.add(seatLabel);
		
		//get data
		

		String query = "SELECT * FROM (SELECT * FROM TICKET_VIEW WHERE CUSTOMERID='"+userId+"' ) WHERE ROWNUM<5";     
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
			String m[] = new String[5];
			String d[] = new String[5];
			String t[]=new String[5];
			int s[]=new int[5];
			int i=0;
			while(rs.next())
			{
                m[i] = rs.getString("MCODE");
                d[i] = rs.getString("EMPID");
				s[i]= rs.getInt("seat");
				flag=true;
				movieName[i]=m[i];
				date[i]=d[i];
				time[i]=t[i];
				seat[i]=s[i];
				System.out.println(movieName[i]);
				System.out.println(date[i]);
				System.out.println(time[i]);
				System.out.println(seat[i]);
				i++;
			}
			if(flag)
			{
				
				
				
			}
			if(!flag)
			{
				
				JOptionPane.showMessageDialog(this,"No data Found!"); 
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
		mLabel=new JLabel[5];
		dLabel=new JLabel[5];
		tLabel=new JLabel[5];
		sLabel=new JLabel[5];
		
		int y=220;
		
		Font rsltFont  =new Font("Arial",  Font.ITALIC, 25);
		
		for(int j=0;j<5;j++)
		{
			if(movieName[j]!=null)
			{
				int x=200;
			
				mLabel[j]=new JLabel(""+movieName[j]);
				mLabel[j].setBounds(x,y,150,40);
				mLabel[j].setFont(rsltFont);
				panel.add(mLabel[j]);
				
				x=x+300;
				
				dLabel[j]=new JLabel(""+date[j]);
				dLabel[j].setBounds(x,y,150,40);
				dLabel[j].setFont(rsltFont);
				panel.add(dLabel[j]);
				
				x=x+220;
				
				tLabel[j]=new JLabel(""+time[j]);
				tLabel[j].setBounds(x,y,150,40);
				tLabel[j].setFont(rsltFont);
				panel.add(tLabel[j]);
				
				x=x+230;
				
				sLabel[j]=new JLabel(""+seat[j]);
				sLabel[j].setBounds(x,y,150,40);
				sLabel[j].setFont(rsltFont);
				panel.add(sLabel[j]);
				y=y+50;
			}
		}
		
		
		
		//BUTTON FIELD//
		
		logoutBtn = new JButton("Logout");
		logoutBtn.setBounds(1130, 30, 120, 40);
		logoutBtn.setFont(btnFont);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		backBtn = new JButton("Back");
		backBtn.setBounds(550, 500, 150,40);
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
			Customer c= new Customer(userId);
			c.setVisible(true);
			this.setVisible(false);
		}
		
		else if(text.equals(logoutBtn.getText()))
		{
			UserLogin ul=new UserLogin();
			ul.setVisible(true);
			this.setVisible(false);
		}
		
		else{}
	}
	
}
