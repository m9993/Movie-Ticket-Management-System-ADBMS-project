import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import oracle.jdbc.driver.*;
import oracle.jdbc.OracleTypes;

public class AddMovie extends JFrame implements ActionListener
{
	String userId;
	JLabel textLabel,idLabel,nameLabel,relLabel,dayLabel,time1Label,slotLabel,time2Label,theaterLabel;
	JButton slotBtn,addBtn,add1Btn,add2Btn,backBtn,logoutBtn;
	JPanel panel;
	JTextField idText,nameTF,relTF,dayTF,time1TF,date2TF,time2TF;
	JComboBox combo,movieCombo,dateCombo,slotCombo,dayCombo,relCombo,theaterCombo;
	String movieName,movieId,day,date2,time1,time2,relD,slot,movID,mCode,theaterTxt,thId;
	int cap;
	String theaterId[]=new String[10];
	String tName[]=new String[10];
	String item[]=new String[10];
	String capacity[]=new String[10];
	int totalTheater=0,id=0,totalTime;
	
	public AddMovie(String userId)
	{
		super("");
		
		this.userId = userId;
		this.setSize(1300,700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		Font tLFont=new Font("Cambria",  Font.ITALIC+Font.BOLD, 40);
		Font labelFont=new Font("Arial",  Font.ITALIC+Font.BOLD, 25);
		Font btnFont  =new Font("Arial",  Font.ITALIC, 25);
		Font tfFont=new Font("Arial",  Font.ITALIC, 20);
		Font comboFont=new Font("Arial",  Font.ITALIC, 20);
		panel = new JPanel();
		panel.setLayout(null);
		
		
		
		textLabel=new JLabel("Add Movie");
		textLabel.setBounds(500,30,400,40);
		textLabel.setFont(tLFont);
		panel.add(textLabel);
		
		
		logoutBtn = new JButton("Logout");
		logoutBtn.setBounds(1130, 30, 120, 40);
		logoutBtn.setFont(btnFont);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		
		
		idLabel=new JLabel("Movie ID");
		idLabel.setBounds(300,100,150,40);
		idLabel.setFont(labelFont);
		panel.add(idLabel);
		
		//GET Movie ID 
		
		String sql = "{call CREATE_MID (?)}";     
        Connection con=null;//for connection
		CallableStatement stmt = null;
		ResultSet rs = null;//to get row by row result from DB
		System.out.println(sql);
        try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");//load driver
			System.out.println("driver loaded");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","system");
			System.out.println("connection done 1");//connection with database established
			stmt = con.prepareCall(sql);//create statement
			System.out.println("statement created 1");
			stmt.registerOutParameter(1, java.sql.Types.VARCHAR);//getting result
			stmt.execute();
			//rs = (ResultSet)stmt.getObject(1);
			System.out.println("results received 1");
			movID=stmt.getString(1);
			movID = "M" + (Integer.parseInt(movID.substring(1,movID.length()))+1);
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

                if(stmt!=null)
					stmt.close();

                if(con!=null)
					con.close();
            }
            catch(Exception ex){}
        }
		
		//
		
		idText=new JTextField(movID);
		idText.setBounds(500, 100, 250, 40);
		idText.setFont(tfFont);
		panel.add(idText);
		idText.setEnabled(false);
		
		
		nameLabel=new JLabel("Movie Name");
		nameLabel.setBounds(300,160,150,40);
		nameLabel.setFont(labelFont);
		panel.add(nameLabel);
		
		nameTF=new JTextField();
		nameTF.setBounds(500, 160, 250, 40);
		nameTF.setFont(tfFont);
		panel.add(nameTF);
		nameTF.setVisible(true);
		
		relLabel=new JLabel("Release Year");
		relLabel.setBounds(300,220,180,40);
		relLabel.setFont(labelFont);
		panel.add(relLabel);
		
		/*relTF=new JTextField();
		relTF.setBounds(500, 220, 250, 40);
		relTF.setFont(tfFont);
		panel.add(relTF);*/
		//relTF.setVisible(true);
		
		
		int []relY=new int[200];
		String []relYear=new String[200];
		relY[0]=1900;
		
		for(int i=1;i<200;i++)
		{
			relY[i]=relY[i-1]+1;
		}
		for(int i=0;i<200;i++)
		{
			relYear[i]=Integer.toString(relY[i]);
		}
		
		relCombo= new JComboBox(relYear);
		relCombo.setBounds(500,220,250,40);
		relCombo.setFont(comboFont);
		relCombo.addActionListener(this);
		panel.add(relCombo);
		
		addBtn=new JButton("Create");
		addBtn.setBounds(800, 220, 150,40);
		addBtn.setFont(btnFont);
		addBtn.addActionListener(this);
		panel.add(addBtn);
		
		dayLabel=new JLabel("Day");
		dayLabel.setBounds(300,290,150,40);
		dayLabel.setFont(labelFont);
		panel.add(dayLabel);
		dayLabel.setVisible(false);
		
		/*dayTF=new JTextField();
		dayTF.setBounds(500, 290, 250, 40);
		dayTF.setFont(tfFont);
		panel.add(dayTF);
		dayTF.setVisible(false);*/
		
		String []dayList= new String[]{"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
		
		dayCombo= new JComboBox(dayList);
		dayCombo.setBounds(500,290,250,40);
		dayCombo.setFont(comboFont);
		dayCombo.addActionListener(this);
		panel.add(dayCombo);
		dayCombo.setVisible(false);
		
		
		
		
		theaterLabel=new JLabel("Theater");
		theaterLabel.setBounds(300,360,150,40);
		theaterLabel.setFont(labelFont);
		panel.add(theaterLabel);
		theaterLabel.setVisible(false);
		
		
		//THEATER LIST 
		
		
		String thQuery = "SELECT THEATERID , THEATERNAME , CAPACITY FROM THEATER";     
        Connection conn=null;//for connection
        Statement stm = null;//for query execution
		ResultSet rst = null;//to get row by row result from DB
		System.out.println(thQuery);
        try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");//load driver
			System.out.println("driver loaded 2");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","system");
			System.out.println("connection done 2");//connection with database established
			stm = conn.createStatement();//create statement
			System.out.println("statement created 2");
			rst = stm.executeQuery(thQuery);//getting result
			System.out.println("results received 2");
			
			int i=0;
			while(rst.next())
			{
               item[i]=rst.getString("theaterName");
			   theaterId[i]=rst.getString("theaterId");
			   capacity[i]=rst.getString("capacity");
			   System.out.println(theaterId[i]);
			   totalTheater++;
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
                if(rst!=null)
					rs.close();

                if(stm!=null)
					stm.close();

                if(conn!=null)
					conn.close();
            }
            catch(Exception ex){}
        }
		
		
		String []theaterName= new String[totalTheater];
		for(int i=0;i<totalTheater;i++)
		{
			theaterName[i]=item[i];
			tName[i]=item[i];
		}
		

		theaterCombo=new JComboBox(theaterName);
		theaterCombo.setBounds(500, 360, 250, 40);
		theaterCombo.setFont(comboFont);
		theaterCombo.addActionListener(this);
		panel.add(theaterCombo);
		theaterCombo.setVisible(false);
		
		slotBtn=new JButton("Slot");
		slotBtn.setBounds(800, 360, 150,40);
		slotBtn.setFont(btnFont);
		slotBtn.addActionListener(this);
		panel.add(slotBtn);
		slotBtn.setVisible(false);
		
		slotLabel=new JLabel("Slot");
		slotLabel.setBounds(300,420,150,40);
		slotLabel.setFont(labelFont);
		panel.add(slotLabel);
		slotLabel.setVisible(false);
		
		
		
		slotCombo=new JComboBox();
		slotCombo.setBounds(500, 420, 250, 40);
		slotCombo.setFont(comboFont);
		panel.add(slotCombo);
		slotCombo.setVisible(false);
		
		add1Btn=new JButton("ADD");
		add1Btn.setBounds(600, 500, 150,40);
		add1Btn.setFont(btnFont);
		add1Btn.addActionListener(this);
		panel.add(add1Btn);
		add1Btn.setVisible(false);
		
		
		
		
		backBtn=new JButton("Back");
		backBtn.setBounds(400, 500, 150,40);
		backBtn.setFont(btnFont);
		backBtn.addActionListener(this);
		panel.add(backBtn);
		backBtn.setVisible(true);
		
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
		
		else if(text.equals(logoutBtn.getText()))
		{
			AdminLogin al = new AdminLogin();
			al.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(addBtn.getText()))
		{
			movieId=idText.getText().toUpperCase();
			movieName=nameTF.getText().toUpperCase();
			relD=relCombo.getSelectedItem().toString();
			int releaseDate=Integer.parseInt(relD);
			String query1 = "INSERT INTO movie VALUES ('"+movieId+"','"+movieName+"',"+ releaseDate+")";			
			System.out.println(query1);
			
			
			try
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","system");
				Statement stm = con.createStatement();
				stm.execute(query1);
				stm.close();
				con.close();
				dayLabel.setVisible(true);
				dayCombo.setVisible(true);
				slotLabel.setVisible(true);
				theaterLabel.setVisible(true);
				theaterCombo.setVisible(true);
				slotCombo.setVisible(true);
				add1Btn.setVisible(true);
				idText.setEnabled(false);
				nameTF.setEnabled(false);
				relCombo.setEnabled(false);
				addBtn.setEnabled(false);
				backBtn.setEnabled(false);
				slotBtn.setVisible(true);
				
			}
			catch(Exception ex)
			{
				
				JOptionPane.showMessageDialog(this, ex.getMessage());
				
			}
		}
		else if(text.equals(slotBtn.getText()))
		{
			//get movie id
			
			
			String sDay=dayCombo.getSelectedItem().toString().toUpperCase();
			String sTheater=theaterCombo.getSelectedItem().toString();
			
			for(int i=0;i<totalTheater;i++)
			{
				if(sTheater.equals(tName[i]))
				{
					id=i;
					break;
				}
			}
			String theaterTxt=theaterId[id];
			String itemt[]=new String[5];
			String sql = "{call getMSlot (?, ? ,? )}";
			Connection con=null;//for connection
			Statement st = null;//for query execution
			ResultSet rs = null;//to get row by row result from DB
			CallableStatement stmt = null;
			System.out.println(sql);
			
			try
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");//load driver
				System.out.println("driver loaded");
				con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","system");
				System.out.println("connection done");//connection with database established
				//st = con.createStatement();//create statement
				System.out.println("statement created");
				//rs = st.executeQuery(query);//getting result
				stmt = con.prepareCall(sql);
				String dayTxt= sDay ;
				
				stmt.setString(1, dayTxt);
				stmt.setString(2,theaterTxt);
				stmt.registerOutParameter(3, OracleTypes.CURSOR);
				stmt.execute();
				rs = (ResultSet)stmt.getObject(3);
				System.out.println("results received");
			
				int i=0;
				while(rs.next())
				{
					//itemt[i]=rs.getString("mslot");
					itemt[i]=rs.getString("mslot");
					System.out.println(itemt[i]);
					totalTime++;
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
					if(stmt!=null)
						stmt.close();
				}
				catch(Exception ex){}
			}
			
			slotCombo.removeAllItems();
			String []times=new String[3];
			for(int i=0;i<3;i++)
			{
				times[i]=itemt[i];
				System.out.println(times[i]);
				slotCombo.insertItemAt(times[i], i);
			}
			slotCombo.setSelectedIndex(0);
			
			
			
		}
		else if(text.equals(add1Btn.getText()))
		{
			String sTheater=theaterCombo.getSelectedItem().toString();
			
			for(int i=0;i<totalTheater;i++)
			{
				if(sTheater.equals(tName[i]))
				{
					id=i;
					break;
				}
			}
			String theaterTxt=theaterId[id];
			
			String sql = "{call CREATE_MCODE (? , ? ,?)}";     
			Connection con=null;//for connection
			CallableStatement stmt = null;
			ResultSet rs = null;//to get row by row result from DB
			System.out.println(sql);
			try
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");//load driver
				System.out.println("driver loaded");
				con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","system");
				System.out.println("connection done 4");//connection with database established
				stmt = con.prepareCall(sql);//create statement				
				System.out.println(theaterTxt);
				System.out.println("statement created 4");
				stmt.setString(1,theaterTxt);
				stmt.registerOutParameter(2, java.sql.Types.VARCHAR);//getting result
				stmt.registerOutParameter(3, java.sql.Types.INTEGER);//getting result
				stmt.execute();
				//rs = (ResultSet)stmt.getObject(1);
				System.out.println("results received 4");
				mCode=stmt.getString(2);
				System.out.println(mCode);
				cap=stmt.getInt(3);
				
				mCode = "MC" + (Integer.parseInt(mCode.substring(2,mCode.length()))+1);
				System.out.println(mCode);
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
	
					if(stmt!=null)
						stmt.close();

					if(con!=null)
						con.close();
				}
				catch(Exception ex){}
			}
			
			
			//
			movieId=idText.getText().toUpperCase();
			day=dayCombo.getSelectedItem().toString();
			slot=slotCombo.getSelectedItem().toString();
			String query1 = "INSERT INTO MTIME VALUES('"+mCode+"' , '"+day.toUpperCase()+"',"+slot+" ,'"+movieId+"' , '"+theaterTxt+"', "+cap+") ";			
			System.out.println(query1);
			
			
			try
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection co = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","system");
				Statement stm = co.createStatement();
				stm.execute(query1);
				stm.close();
				co.close();
				
				JOptionPane.showMessageDialog(this, "Success !!!");
				slotCombo.removeAllItems();
				
				
			}
			catch(SQLException ex)
			{
				if(ex.getErrorCode()== 21000)
				{
					JOptionPane.showMessageDialog( this,"MOVIE LIMIT REACHED");
					backBtn.setEnabled(true);
				}
				else 
				{
					JOptionPane.showMessageDialog(null, ex.getErrorCode());
					System.out.println("Exception : " +ex.getMessage());
				}
			}
			catch(NullPointerException e)
			{
				JOptionPane.showMessageDialog(this, "Invalid Input !!!");
				
			}
			catch (ClassNotFoundException e)
			{
				
			}
			finally
			{
				
			}
		}
	}
	
}