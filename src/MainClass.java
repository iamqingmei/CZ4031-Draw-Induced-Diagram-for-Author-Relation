import java.sql.*;
import java.util.*;
import java.awt.*;
import java.awt.geom.*;


import javax.swing.*;

import graph.JGraphAdaptor;

public class MainClass {

	public static void main(String[] argv) {

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
		Connection connection = null;

		try {
			connection = DriverManager.getConnection(
					"jdbc:postgresql://127.0.0.1:5432/4031Project2", "postgres",
					"123456");
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}

		if (connection != null) {
			System.out.println("Connected to the database!");
		} else {
			System.out.println("Failed to make connection!");
		}

		// ----------------------end connection set up------------------------
		int totalAuthorNumber = authorCount(connection);
		int k = 0;
		ArrayList<Integer> authoridList = new ArrayList<Integer>();
		HashMap<Integer,Set<Integer>> authorPubMap = new HashMap<Integer,Set<Integer>>();

		// System.out.println("the first author name is: " + getAuthorName(connection,1));
		// System.out.println("the first publication title is: " + getPublicationTitle(connection,1));
		System.out.println("There are " + totalAuthorNumber + " authors.");
		

		System.out.println("Please input the number of authors:");
		Scanner sc = new Scanner(System.in);
		k = sc.nextInt();
		int authorid = 0;
		for (int i=0;i<k;i++){
			System.out.println("Please input the authorid: (from 1 to " + totalAuthorNumber +")");
			authorid = sc.nextInt();
			authoridList.add(authorid);
			authorPubMap.put(authorid,getPubidSetOfAuthor(connection,authorid));
		}

		System.out.println("The " + k + " authors published the following publications: ");
		// Get a set of the entries
		Set set = authorPubMap.entrySet();
		// Get an iterator
		Iterator it = set.iterator();
		// Display elements
		while(it.hasNext()) {
			Map.Entry me = (Map.Entry)it.next();
			System.out.print(me.getKey() + ": ");
			System.out.println(me.getValue());
		}

		for (int i=0;i<k;i++){
			for (int j=i+1;j<k;j++){
				Set<Integer> iPubSet = authorPubMap.get(authoridList.get(i));
				Set<Integer> jPubSet = authorPubMap.get(authoridList.get(j));
				Set<Integer> intersection = new HashSet<Integer>(iPubSet);
				intersection.retainAll(jPubSet);
				if (!intersection.isEmpty()){
					System.out.println("author " + authoridList.get(i) + " and author " + authoridList.get(j) +" is connected!");
				}
				else{
					System.out.println("author " + authoridList.get(i) + " and author " + authoridList.get(j) +" is not connected :( !");
				}
			}
		}

		sc.close();

		JGraphAdaptor graph = new JGraphAdaptor();
		graph.init();

		JFrame frame = new JFrame();
        frame.getContentPane().add(graph);
        frame.setTitle("JGraphT Adapter to JGraph Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
		


	}

	public static String getAuthorName(Connection conn, int authorid){
		String result = null;
		try{
			Statement st = conn.createStatement();
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

	public static String getPublicationTitle(Connection conn, int pubid){
		String result = null;
		try{
			Statement st = conn.createStatement();
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

	public static int authorCount(Connection conn){
		int result = -1;
		try{
			Statement st = conn.createStatement();
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

	public static Set<Integer> getPubidSetOfAuthor(Connection conn, int authorid){
		Set<Integer> result = new HashSet<Integer>();
		try{
			Statement st = conn.createStatement();
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

}