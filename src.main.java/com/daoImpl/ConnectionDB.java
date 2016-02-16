package com.daoImpl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class ConnectionDB {
	private static Connection connection = null;

	static {
		 try {
	        Class.forName("com.mysql.jdbc.Driver");

	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        String url = "jdbc:mysql://localhost:3306/practice07_db";
	        String user = "root";
	        String password = "toor";

	        
	        try {
	            connection = DriverManager.getConnection(url, user, password);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	}
	
	public static Connection getConnection(){
		return connection;
	}
	

}
