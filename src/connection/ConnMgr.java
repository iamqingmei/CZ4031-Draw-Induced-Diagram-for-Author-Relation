package connection;

import java.sql.*;
import java.util.*;

public class ConnMgr{
	Connection connection = null;
	private static ConnMgr connMgr = new ConnMgr();
	private ConnMgr(){
		//-----------------------set up connection------------------------
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your PostgreSQL JDBC Driver? "
					+ "Include in your library path!");
			e.printStackTrace();
			return;
		}

		System.out.println("PostgreSQL JDBC Driver Registered!");

		try {
			this.connection = DriverManager.getConnection(
					"jdbc:postgresql://127.0.0.1:5432/4031Project2", "postgres",
					"123456");
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}

		if (this.connection != null) {
			System.out.println("Connected to the database!");
		} else {
			System.out.println("Failed to make connection!");
		}
		// ----------------------end connection set up------------------------
	}

	public static ConnMgr getInstance() {
    	return connMgr;
	}

	public String getAuthorName(int authorid){
		String result = null;
		try{
			Statement st = this.connection.createStatement();
			ResultSet rs = st.executeQuery("SELECT name FROM author WHERE authorid = " + authorid);
		
			if (rs.next()){
				result = rs.getString(1);
			} 
			rs.close();
			st.close();
		}
		catch (SQLException e){
			e.printStackTrace();
			return null;
		}
		return result;
	}

	public String getPublicationTitle(int pubid){
		String result = null;
		try{
			Statement st = this.connection.createStatement();
			ResultSet rs = st.executeQuery("SELECT title FROM publication WHERE pubid = " + pubid);
		
			if (rs.next()){
				result = rs.getString(1);
			} 
			rs.close();
			st.close();
		}
		catch (SQLException e){
			e.printStackTrace();
			return null;
		}
		return result;
	}

	public int authorCount(){
		int result = -1;
		try{
			Statement st = this.connection.createStatement();
			ResultSet rs = st.executeQuery("SELECT count(*) FROM author");
		
			if (rs.next()){
				result = rs.getInt(1);
			} 
			rs.close();
			st.close();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return result;
	}

	public Set<Integer> getPubidSetOfAuthor(int authorid){
		Set<Integer> result = new HashSet<Integer>();
		try{
			Statement st = this.connection.createStatement();
			ResultSet rs = st.executeQuery("SELECT pubid FROM authored WHERE authorid = " + authorid);
		
			while (rs.next()){
				result.add(rs.getInt(1));
			} 
			rs.close();
			st.close();
		}
		catch (SQLException e){
			e.printStackTrace();
			return null;
		}
		return result;
	}

	public ArrayList<Integer> getCollaborator(int authorid){
		ArrayList<Integer> result = new ArrayList<Integer>();
		try{
			Statement st = this.connection.createStatement();
			ResultSet rs = st.executeQuery("SELECT DISTINCT A2.authorid FROM Authored A1, Authored A2 WHERE A1.pubid = A2.pubid AND A1.authorid != A2.authorid AND A1.authorid = " + authorid);
			while (rs.next()){
				result.add(rs.getInt(1));
			} 
			rs.close();
			st.close();
		}
		catch (SQLException e){
			e.printStackTrace();
			return null;
		}
		return result;
	}
}