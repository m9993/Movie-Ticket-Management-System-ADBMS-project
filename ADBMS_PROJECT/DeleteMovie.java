import java.lang.*;import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.util.*;

public class DeleteMovie extends JFrame implements ActionListener
{
	JLabel textLabel,nameLabel;
	JButton backBtn,dltBtn;
	JComboBox movieCombo;
	JPanel panel;
	String userId;
	int id;
	
	String movId[]=new String[10];
	String movieName[]= new String[200];
	int totalMovie=0;
	
	
	public DeleteMovie(String userId)
	{
		super("Delete Movie");
		
		this.setSize(1300,700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.userId = userId;
		
		panel = new JPanel();
		panel.setLayout(null);
		
		
		Font labelFont=new Font("Arial",  Font.ITALIC+Font.BOLD, 35);
		Font btnFont  =new Font("Arial",  Font.ITALIC, 25);
		Font comboFont=new Font("Arial",  Font.ITALIC, 20);
		
		textLabel=new JLabel("Delete Movie");
		textLabel.setBounds(500,100,700,60);
		textLabel.setFont(labelFont);
		panel.add(textLabel);
		
		nameLabel=new JLabel("Movies");
		nameLabel.setBounds(300,200,150,40);
		nameLabel.setFont(labelFont);
		panel.add(nameLabel);
		
		//get movie name 
		
		String item[] = new String[10];
		
		
		
		String query = "SELECT * FROM  MOVIE_VIEW";     
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
               item[i]=rs.getString("MOVIENAME");
			   movId[i]=rs.getString("MID");
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
		
		
		String mName[]= new String[totalMovie];
		for(int i=0;i<totalMovie;i++)
		{
			movieName[i]=item[i];
			mName[i]=item[i];
		}
		
		movieCombo= new JComboBox(mName);
		movieCombo.setBounds(500,200,300,40);
		movieCombo.setFont(comboFont);
		movieCombo.addActionListener(this);
		panel.add(movieCombo);
		
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
			ManagerHome mh= new ManagerHome(userId);
			mh.setVisible(true);
			this.setVisible(false);
		}
		
		else if(text.equals(dltBtn.getText()))
		{
			String mName=movieCombo.getSelectedItem().toString();
			
			for(int i=0;i<totalMovie;i++)
			{
				if(mName.equals(movieName[i]))
				{
					id=i;
					break;
				}
			}
			deleteFromDB(id);
		}
		
		else{}
	}
	
	public void deleteFromDB(int id)
	{

		String sql="{call DELETE_MOVIE( ? )}";	
		Connection con=null;//for connection
		CallableStatement stmt = null;
		ResultSet rs = null;//to get row by row result from DB		
		System.out.println(sql);
        try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");//load driver
			System.out.println("driver loaded");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","system");
			System.out.println("connection done ");//connection with database established
			stmt = con.prepareCall(sql);//create statement				
			System.out.println("statement created ");
			stmt.setString(1,movId[id]);
			stmt.execute();
			System.out.println("results received ");
			//stm.close();
			//con.close();
			JOptionPane.showMessageDialog(this, "Success !!!");
			
			ManagerHome mh =new ManagerHome(userId);
			mh.setVisible(true);
			this.setVisible(false);
			
			
		}
        catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this, "Oops !!!");
			System.out.println("Exception : " +ex.getMessage());
        }
	}
}