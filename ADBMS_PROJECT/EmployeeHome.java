import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

public class EmployeeHome extends JFrame implements ActionListener
{
	JLabel welcomeLabel,nowLabel,nameLabel,dateLabel,timeLabel,asLabel,sLabel;
	JButton addEmployeeBtn, viewEmployeeBtn, backBtn, logoutBtn,goBtn,sdBtn,timeBtn,ssBtn,refreshBtn;
	JPanel panel;
	JComboBox combo,movieCombo,dateCombo,timeCombo;
	String userId;
	String movId[]=new String[10];
	String dates[]=new String[5];
	String times[]=new String[5];
	int totalMovie=0,id=0,totalDate=0,totalTime=0,seats;
	String movieName[]= new String[200];
	public EmployeeHome(String userId)
	{
		super("EmployeeHome");
		
		this.userId = userId;
		this.setSize(1300,700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Font labelFont=new Font("Arial",  Font.ITALIC+Font.BOLD, 25);
		Font btnFont  =new Font("Arial",  Font.ITALIC, 25);
		Font comboFont=new Font("Arial",  Font.ITALIC, 20);
		panel = new JPanel();
		panel.setLayout(null);
		
		//label 
		
		
		welcomeLabel = new JLabel("Welcome, "+userId);
		welcomeLabel.setBounds(850,30,260,40);
		welcomeLabel.setFont(labelFont);
		panel.add(welcomeLabel);
		
		Font nowFont=new Font("Cambria",  Font.ITALIC+Font.BOLD, 40);
		nowLabel=new JLabel("Now Showing");
		nowLabel.setBounds(500,100,300,60);
		nowLabel.setFont(nowFont);
		panel.add(nowLabel);
		
		nameLabel=new JLabel("Movies");
		nameLabel.setBounds(300,200,150,40);
		nameLabel.setFont(labelFont);
		panel.add(nameLabel);
		
		dateLabel=new JLabel("Date");
		dateLabel.setBounds(300,270,150,40);
		dateLabel.setFont(labelFont);
		panel.add(dateLabel);
		dateLabel.setVisible(false);
		
		timeLabel=new JLabel("Times ");
		timeLabel.setBounds(300,340,150,40);
		timeLabel.setFont(labelFont);
		panel.add(timeLabel);
		timeLabel.setVisible(false);
		
		asLabel=new JLabel("Available seats  ");
		asLabel.setBounds(450,420,200,40);
		asLabel.setFont(labelFont);
		panel.add(asLabel);
		asLabel.setVisible(false);
		
		sLabel=new JLabel(" "+seats);
		sLabel.setBounds(750,420,200,40);
		sLabel.setFont(labelFont);
		panel.add(sLabel);
		sLabel.setVisible(false);
		
		//get movie name 
		
		String item[] = new String[100];
		
		
		
		String query = "SELECT movieName,mId FROM movie";     
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
			
			int i=0;
			while(rs.next())
			{
               item[i]=rs.getString("movieName");
			   movId[i]=rs.getString("mId");
			   System.out.println(movId[i]);
			   totalMovie++;
			   i++;
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
		
		String moName[]= new String[totalMovie];
		
		for(int i=0;i<totalMovie;i++)
		{
			moName[i]=item[i];
			movieName[i]=item[i];
		}
		
		movieCombo= new JComboBox(moName);
		movieCombo.setBounds(500,200,300,40);
		movieCombo.setFont(comboFont);
		movieCombo.addActionListener(this);
		panel.add(movieCombo);
		
		//movie name got;
		
		//date
		
		dateCombo= new JComboBox(dates);
		dateCombo.setBounds(500,270,300,40);
		dateCombo.setFont(comboFont);
		dateCombo.setEditable(true);
		dateCombo.addActionListener(this);
		panel.add(dateCombo);
		dateCombo.setVisible(false);
		
		//time 
		
		timeCombo= new JComboBox(times);
		timeCombo.setBounds(500,340,300,40);
		timeCombo.setFont(comboFont);
		timeCombo.setEditable(true);
		timeCombo.addActionListener(this);
		panel.add(timeCombo);
		timeCombo.setVisible(false);
		
		//button
		
		logoutBtn = new JButton("Logout");
		logoutBtn.setBounds(1130, 30, 120, 40);
		logoutBtn.setFont(btnFont);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		
		
		String items[] = new String[]{"","Sell Ticket","Change password","My Info","Delete Account"};
		combo= new JComboBox(items);
		combo.setBounds(150,30,170,40);
		combo.setFont(comboFont);
		combo.addActionListener(this);
		panel.add(combo);
		
		
		goBtn=new JButton("Go");
		goBtn.setBounds(330,30,80,40);
		goBtn.setFont(btnFont);
		goBtn.addActionListener(this);
		panel.add(goBtn);
		
		sdBtn=new JButton("Show Day");
		sdBtn.setBounds(850,200,180,40);
		sdBtn.setFont(btnFont);
		sdBtn.addActionListener(this);
		panel.add(sdBtn);
		
		timeBtn=new JButton("Show Time");
		timeBtn.setBounds(850,270,180,40);
		timeBtn.setFont(btnFont);
		timeBtn.addActionListener(this);
		panel.add(timeBtn);
		timeBtn.setVisible(false);
		
		ssBtn=new JButton("Show Seat");
		ssBtn.setBounds(850,340,180,40);
		ssBtn.setFont(btnFont);
		ssBtn.addActionListener(this);
		panel.add(ssBtn);
		ssBtn.setVisible(false);
		
		refreshBtn=new JButton("Refresh");
		refreshBtn.setBounds(550,500,180,40);
		refreshBtn.setFont(btnFont);
		refreshBtn.addActionListener(this);
		panel.add(refreshBtn);
		refreshBtn.setVisible(false);
		
			
		this.add(panel);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String text = ae.getActionCommand();
		if(text.equals(goBtn.getText()))
		{
			String comboText = combo.getSelectedItem().toString();
			if (comboText.equals("Sell Ticket"))
			{
				ESellTicket es=new ESellTicket(userId);
				es.setVisible(true);
				this.setVisible(false);
			
			}
			
			else if (comboText.equals("Change password"))
			{
				EChangePassword ec=new EChangePassword(userId);
				ec.setVisible(true);
				this.setVisible(false);
			
			}
			else if (comboText.equals("My Info"))
			{
				EInfo ei=new EInfo(userId);
				ei.setVisible(true);
				this.setVisible(false);
			
			}
			else if (comboText.equals("Delete Account"))
			{
				EDelete ed=new EDelete(userId);
				ed.setVisible(true);
				this.setVisible(false);
			
			}
			
		}
		else if(text.equals(logoutBtn.getText()))
		{
			UserLogin ul=new UserLogin();
			ul.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(sdBtn.getText()))
		{
			//get movie id
			
			
			String mName=movieCombo.getSelectedItem().toString();
			
			for(int i=0;i<totalMovie;i++)
			{
				if(mName.equals(movieName[i]))
				{
					id=i;
					break;
				}
			}
			String itemd[]=new String[5];
			String query =  "SELECT day FROM mtime WHERE mId='"+movId[id]+"'";      
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
			
				int i=0;
				while(rs.next())
				{
					itemd[i]=rs.getString("day");
					System.out.println(itemd[i]);
					totalDate++;
					i++;
				}
				sdBtn.setEnabled(false);
				movieCombo.setEnabled(false);
			
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
			
			dateCombo.removeAllItems();
		
			for(int i=0;i<5;i++)
			{
				dates[i]=itemd[i];
				System.out.println(dates[i]);
				dateCombo.insertItemAt(dates[i], i);
			}
			dateCombo.setSelectedIndex(0);
			
			dateLabel.setVisible(true);
			dateCombo.setVisible(true);
			timeBtn.setVisible(true);
			refreshBtn.setVisible(true);
			timeLabel.setVisible(false);
			timeCombo.setVisible(false);
			ssBtn.setVisible(false);
			asLabel.setVisible(false);
			sLabel.setVisible(false);
			
		}
		else if(text.equals(timeBtn.getText()))
		{
			//get movie id
			
			String mName=movieCombo.getSelectedItem().toString();
			String mDate=dateCombo.getSelectedItem().toString();
			
			for(int i=0;i<totalMovie;i++)
			{
				if(mName.equals(movieName[i]))
				{
					id=i;
					break;
				}
			}
			String itemt[]=new String[5];
			String query =  "SELECT mslot FROM mtime WHERE mId='"+movId[id]+"' and day='"+mDate+"'";      
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
			
				int i=0;
				while(rs.next())
				{
					itemt[i]=rs.getString("mslot");
					System.out.println(itemt[i]);
					totalTime++;
					i++;
				}
				timeBtn.setEnabled(false);
				dateCombo.setEnabled(false);
			
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
			
			timeCombo.removeAllItems();
		
			for(int i=0;i<3;i++)
			{
				times[i]=itemt[i];
				System.out.println(times[i]);
				timeCombo.insertItemAt(times[i], i);
			}
			timeCombo.setSelectedIndex(0);
			
			timeLabel.setVisible(true);
			timeCombo.setVisible(true);
			ssBtn.setVisible(true);
			
		}
		else if(text.equals(ssBtn.getText()))
		{
			//get available seat
			
			
			String mName=movieCombo.getSelectedItem().toString();
			String mDate=dateCombo.getSelectedItem().toString();
			String mTime=timeCombo.getSelectedItem().toString();
			
			for(int i=0;i<totalMovie;i++)
			{
				if(mName.equals(movieName[i]))
				{
					id=i;
					break;
				}
			}
			String query =  "SELECT availseat FROM mtime WHERE mId='"+movId[id]+"' and day='"+mDate+"' and mslot='"+mTime+"'";      
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
			
				int i=0;
				while(rs.next())
				{
					seats=rs.getInt("availseat");
					System.out.println(seats);
					
				}
				ssBtn.setEnabled(false);
				timeCombo.setEnabled(false);
				timeCombo.setEnabled(false);
				
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
			
			
			sLabel.setText(""+seats);
			asLabel.setVisible(true);
			sLabel.setVisible(true);

			
		}
		else if(text.equals(refreshBtn.getText()))
		{
			dateLabel.setVisible(false);
			dateCombo.setVisible(false);
			timeBtn.setVisible(false);
			timeLabel.setVisible(false);
			timeCombo.setVisible(false);
			ssBtn.setVisible(false);
			asLabel.setVisible(false);
			sLabel.setVisible(false);
			refreshBtn.setVisible(false);
			movieCombo.setEnabled(true);
			dateCombo.setEnabled(true);
			timeBtn.setEnabled(true);
			timeCombo.setEnabled(true);
			dateCombo.setEnabled(true);
			sdBtn.setEnabled(true);
			ssBtn.setEnabled(true);
			
		}
		
		
	}
	
	
}