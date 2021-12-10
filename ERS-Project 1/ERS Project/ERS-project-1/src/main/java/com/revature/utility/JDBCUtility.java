package com.revature.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.postgresql.Driver;

public class JDBCUtility {
	
	public static Connection getConnection() throws SQLException {
		String url = System.getenv("db_url");
		String username = System.getenv("username");
		String password = System.getenv("password");

		Driver postgresDriver = new Driver();		
		DriverManager.registerDriver(postgresDriver);

		Connection con = DriverManager.getConnection(url, username, password);
		
		return con;
	}
	
}

//jdbc:postgresql://{host}[:{port}]/[{database}]

//jdbc:postgresql://localhost:5432/postgres