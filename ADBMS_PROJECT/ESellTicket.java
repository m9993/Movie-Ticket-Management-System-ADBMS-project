import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

public class ESellTicket extends JFrame implements ActionListener
{
	JLabel seatLabel,sellLabel,nameLabel,dateLabel,timeLabel,asLabel,sLabel,customerLabel,thLabel;
	JButton thBtn,addEmployeeBtn, viewEmployeeBtn, logoutBtn,sdBtn,timeBtn,ssBtn,refreshBtn,backBtn,sellBtn;
	JPanel panel;
	JTextField seatTF;
	JComboBox thCombo,movieCombo,dateCombo,timeCombo,customerCombo;
	String userId;
	String movId[]=new String[100];
	String dates[]=new String[100];
	String times[]=new String[100];
	int totalMovie=0,id=0,totalDate=0,totalTime=0,seats,totalCustomer=0,as,totalTh=0,id1=0;
	String movieName[]= new String[10000];
	String customerId[]= new String[10000];
	String theaterName[]=new String[10];
	String theaterID[]=new String[10];
	public ESellTicket(String userId)
	{
		super("");
		
		this.userId = userId;
		this.setSize(1300,700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Font labelFont=new Font("Arial",  Font.ITALIC+Font.BOLD, 25);
		Font slabelFont=new Font("Arial",  Font.ITALIC, 15);
		Font btnFont  =new Font("Arial",  Font.ITALIC, 25);
		Font comboFont=new Font("Arial",  Font.ITALIC, 20);
		panel = new JPanel();
		panel.setLayout(null);
		
		//label 
		
		Font sellFont=new Font("Cambria",  Font.ITALIC+Font.BOLD, 40);
		
		sellLabel=new JLabel("Sell Ticket");
		sellLabel.setBounds(550,50,300,60);
		sellLabel.setFont(sellFont);
		panel.add(sellLabel);
		
		customerLabel=new JLabel("Customer ID");
		customerLabel.setBounds(300,150,150,40);
		customerLabel.setFont(labelFont);
		panel.add(customerLabel);
		
		nameLabel=new JLabel("Movies");
		nameLabel.setBounds(300,220,150,40);
		nameLabel.setFont(labelFont);
		panel.add(nameLabel);
		
		dateLabel=new JLabel("Day");
		dateLabel.setBounds(300,290,150,40);
		dateLabel.setFont(labelFont);
		panel.add(dateLabel);
		dateLabel.setVisible(false);
		
		thLabel=new JLabel("Theater");
		thLabel.setBounds(300,360,150,40);
		thLabel.setFont(labelFont);
		panel.add(thLabel);
		thLabel.setVisible(false);
		
		timeLabel=new JLabel("Times ");
		timeLabel.setBounds(300,430,150,40);
		timeLabel.setFont(labelFont);
		panel.add(timeLabel);
		timeLabel.setVisible(false);
		
		seatLabel=new JLabel("Seats  ");
		seatLabel.setBounds(300,500,200,40);
		seatLabel.setFont(labelFont);
		panel.add(seatLabel);
		seatLabel.setVisible(false);
		
		seatTF=new JTextField("");
		seatTF.setBounds(500,500,300,40);
		seatTF.setFont(comboFont);
		panel.add(seatTF);
		seatTF.setVisible(false);
		
		asLabel=new JLabel("Available seats  ");
		asLabel.setBounds(820,500,120,40);
		asLabel.setFont(slabelFont);
		asLabel.setForeground(Color.RED);
		panel.add(asLabel);
		asLabel.setVisible(false);
		
		sLabel=new JLabel(" "+seats);
		sLabel.setBounds(950,500,50,40);
		sLabel.setFont(slabelFont);
		sLabel.setForeground(Color.RED);
		panel.add(sLabel);
		sLabel.setVisible(false);
		
		//getcoustomer id
		
		String itemc[] = new String[10];
		
		
		
		String query = "SELECT CUSTOMERID FROM CUSTOMER";     
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
			
			int j=0;
			while(rs.next())
			{
               itemc[j]=rs.getString("CUSTOMERID");
			   System.out.println(itemc[j]);
			   totalCustomer++;
			   j++;
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
		
		String cust[]=new String[totalCustomer];
		
		for(int j=0;j<totalCustomer;j++)
		{
			customerId[j]=itemc[j];
			cust[j]=itemc[j];
		}
		
		customerCombo= new JComboBox(cust);
		customerCombo.setBounds(500,150,300,40);
		customerCombo.setFont(comboFont);
		customerCombo.addActionListener(this);
		panel.add(customerCombo);
		
		//get movie name 
		
		String item[] = new String[100];
		
		
		
		String query1 = "SELECT movieName,mId FROM movie";     
        Connection con1=null;//for connection
        Statement st1 = null;//for query execution
		ResultSet rs1 = null;//to get row by row result from DB
		System.out.println(query1);
        try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");//load driver
			System.out.println("driver loaded1");
			con1 = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","system");
			System.out.println("connection done1");//connection with database established
			st1 = con1.createStatement();//create statement
			System.out.println("statement created1");
			rs1 = st1.executeQuery(query1);//getting result
			System.out.println("results received1");
			
			int i=0;
			while(rs1.next())
			{
               item[i]=rs1.getString("movieName");
			   movId[i]=rs1.getString("mId");
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
                if(rs1!=null)
					rs1.close();

                if(st1!=null)
					st1.close();

                if(con1!=null)
					con1.close();
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
		movieCombo.setBounds(500,220,300,40);
		movieCombo.setFont(comboFont);
		movieCombo.addActionListener(this);
		panel.add(movieCombo);
		
		//movie name got;
		
		//date
		
		dateCombo= new JComboBox(dates);
		dateCombo.setBounds(500,290,300,40);
		dateCombo.setFont(comboFont);
		dateCombo.setEditable(true);
		dateCombo.addActionListener(this);
		panel.add(dateCombo);
		dateCombo.setVisible(false);
		
		thCombo= new JComboBox(theaterName);
		thCombo.setBounds(500,360,300,40);
		thCombo.setFont(comboFont);
		thCombo.setEditable(true);
		thCombo.addActionListener(this);
		panel.add(thCombo);
		thCombo.setVisible(false);
		
		//time 
		
		timeCombo= new JComboBox(times);
		timeCombo.setBounds(500,430,300,40);
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
		
		
		sdBtn=new JButton("Show Day");
		sdBtn.setBounds(850,220,180,40);
		sdBtn.setFont(btnFont);
		sdBtn.addActionListener(this);
		panel.add(sdBtn);
		
		thBtn=new JButton("Show Theater");
		thBtn.setBounds(850,290,180,40);
		thBtn.setFont(btnFont);
		thBtn.addActionListener(this);
		panel.add(thBtn);
		thBtn.setVisible(false);
		
		timeBtn=new JButton("Show time");
		timeBtn.setBounds(850,360,180,40);
		timeBtn.setFont(btnFont);
		timeBtn.addActionListener(this);
		panel.add(timeBtn);
		timeBtn.setVisible(false);
		
		ssBtn=new JButton("Show Seat");
		ssBtn.setBounds(850,430,180,40);
		ssBtn.setFont(btnFont);
		ssBtn.addActionListener(this);
		panel.add(ssBtn);
		ssBtn.setVisible(false);
		
		refreshBtn=new JButton("Refresh");
		refreshBtn.setBounds(300,570,150,40);
		refreshBtn.setFont(btnFont);
		refreshBtn.addActionListener(this);
		panel.add(refreshBtn);
		refreshBtn.setVisible(false);
		
		backBtn=new JButton("Back");
		backBtn.setBounds(500,570,150,40);
		backBtn.setFont(btnFont);
		backBtn.addActionListener(this);
		panel.add(backBtn);
		
		sellBtn=new JButton("Sell");
		sellBtn.setBounds(700,570,150,40);
		sellBtn.setFont(btnFont);
		sellBtn.addActionListener(this);
		panel.add(sellBtn);
		sellBtn.setVisible(false);
		
			
		this.add(panel);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String text = ae.getActionCommand();
		
		if(text.equals(logoutBtn.getText()))
		{
			UserLogin al=new UserLogin();
			al.setVisible(true);
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
			String query =  "SELECT distinct day FROM mtime WHERE mId='"+movId[id]+"'";      
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
			thBtn.setVisible(true);
			refreshBtn.setVisible(true);
			timeLabel.setVisible(false);
			timeCombo.setVisible(false);
			ssBtn.setVisible(false);
			asLabel.setVisible(false);
			sLabel.setVisible(false);
			
		}
		else if(text.equals(thBtn.getText()))
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
			String itemt[]=new String[100];
			String itemth[]=new String[100];
			String query =  "SELECT theaterid,theatername  FROM theater  WHERE theaterid in (select theaterid from mtime where mId='"+movId[id]+"' and day = '"+mDate+"')";      
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
					itemt[i]=rs.getString("theaterid");
					itemth[i]=rs.getString("theatername");
					System.out.println(itemth[i]);
					totalTh++;
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
			
			thCombo.removeAllItems();
			
			for(int i=0;i<totalTh;i++)
			{
				theaterID[i]=itemt[i];
				theaterName[i]=itemth[i];
				System.out.println(theaterName[i]);
				thCombo.insertItemAt(theaterName[i], i);
			}
			thCombo.setSelectedIndex(0);
			thLabel.setVisible(true);
			thCombo.setVisible(true);
			dateLabel.setVisible(true);
			dateCombo.setVisible(true);
			timeBtn.setVisible(true);
			refreshBtn.setVisible(true);
			timeLabel.setVisible(false);
			timeCombo.setVisible(false);
			ssBtn.setVisible(false);
			asLabel.setVisible(false);
			sLabel.setVisible(false);
			thCombo.setVisible(true);
			thBtn.setEnabled(false);
			
		}
		else if(text.equals(timeBtn.getText()))
		{
			//get movie id
			
			
			String mName=movieCombo.getSelectedItem().toString();
			String mDate=dateCombo.getSelectedItem().toString();
			String mth=thCombo.getSelectedItem().toString();
			
			for(int i=0;i<totalMovie;i++)
			{
				if(mName.equals(movieName[i]))
				{
					id=i;
					break;
				}
			}
			for(int i=0;i<totalTh;i++)
			{
				if(mth.equals(theaterName[i]))
				{
					id1=i;
					break;
				}
			}
			String itemt[]=new String[5];
			String query =  "SELECT mslot FROM mtime WHERE mId='"+movId[id]+"' and day='"+mDate+"' AND THEATERID ='"+theaterID[id1]+"'";      
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
			String mth=thCombo.getSelectedItem().toString();
			
			for(int i=0;i<totalMovie;i++)
			{
				if(mName.equals(movieName[i]))
				{
					id=i;
					break;
				}
			}
			for(int i=0;i<totalTh;i++)
			{
				if(mth.equals(theaterName[i]))
				{
					id1=i;
					break;
				}
			}
			String query =  "SELECT AVAILSEAT FROM mtime WHERE mId='"+movId[id]+"' and day='"+mDate+"' AND THEATERID ='"+theaterID[id1]+"' AND MSLOT="+mTime+"";
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
				st = con.prepareCall(query);//create statement
				System.out.println("statement created");
				rs=st.executeQuery(query);//getting result
				System.out.println("results received");
				

			
				int i=0;
				while(rs.next())
				{
					seats=rs.getInt("availseat");
					System.out.println(seats);
					
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
			
			
			sLabel.setText(""+seats);
			asLabel.setVisible(true);
			sLabel.setVisible(true);
			seatLabel.setVisible(true);
			seatTF.setVisible(true);
			sellBtn.setVisible(true);

			
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
			seatLabel.setVisible(false);
			seatTF.setVisible(false);
			sellBtn.setVisible(false);
			refreshBtn.setVisible(false);
			
		}
		else if(text.equals(backBtn.getText()))
		{
			EmployeeHome eh= new EmployeeHome(userId);
			eh.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(sellBtn.getText()))
		{
			String cId=customerCombo.getSelectedItem().toString();
			String mName=movieCombo.getSelectedItem().toString();
			String mDate=dateCombo.getSelectedItem().toString();
			int mTime=Integer.parseInt(timeCombo.getSelectedItem().toString());
			String mth=thCombo.getSelectedItem().toString();
			String p=seatTF.getText();
			int bs=Integer.parseInt(p);
			
			for(int i=0;i<totalMovie;i++)
			{
				if(mName.equals(movieName[i]))
				{
					id=i;
					break;
				}
			}
			for(int i=0;i<totalTh;i++)
			{
				if(mth.equals(theaterName[i]))
				{
					id1=i;
					break;
				}
			}
			String query =  "SELECT AVAILSEAT FROM mtime WHERE mId='"+movId[id]+"' and day='"+mDate+"' AND THEATERID ='"+theaterID[id1]+"' AND MSLOT="+mTime+"";      
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
				CallableStatement stmt = null;
				System.out.println("statement created");
				rs = st.executeQuery(query);//getting result
				System.out.println("results received");
			
				int i=0;
				while(rs.next())
				{
					as=rs.getInt("availseat");
					System.out.println(as);
					
				}
				if(bs<as)
				{
					String sql="{call SELL_TICKET (? , ? ,? , ?, ? , ? , ? ,?)}";
					try
					{
						stmt = con.prepareCall(sql);
						stmt.setString(1,cId);
						stmt.setString(2,userId);
						stmt.setString(3,movId[id]);
						stmt.setString(4,mDate);
						stmt.setString(5,theaterID[id1]);
						stmt.setInt(6,mTime);
						stmt.setInt(7,bs);
						stmt.setInt(8,(as-bs));
						stmt.execute();
						/*st.execute(query1);
						System.out.println(query1);
						st.execute(query3);
						System.out.println(query3);*/
						System.out.println("Ticket Sold");
						
						JOptionPane.showMessageDialog(this,"Ticket Sold");
						
						dateLabel.setVisible(false);
						dateCombo.setVisible(false);
						timeBtn.setVisible(false);
						timeLabel.setVisible(false);
						timeCombo.setVisible(false);
						ssBtn.setVisible(false);
						asLabel.setVisible(false);
						sLabel.setVisible(false);
						seatLabel.setVisible(false);
						seatTF.setVisible(false);
						sellBtn.setVisible(false);
						refreshBtn.setVisible(false);
					}
					catch(Exception ex)
					{System.out.println("Exception : " +ex.getMessage());}
				}
				else
					JOptionPane.showMessageDialog(this,"Not enough Ticket");
			
			}
			catch(Exception ex)
			{
				System.out.println("Exception : " +ex.getMessage());
				JOptionPane.showMessageDialog(this,"TInvalid Input");
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
	
	
}