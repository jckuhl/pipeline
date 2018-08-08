package com.revature.Connection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.revature.Servlets.FrontController;

public class JDBCConnection {
	final static Logger log = Logger.getLogger(JDBCConnection.class);
	
	public static Connection getConnection() {
		
		InputStream inp = null;
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			Connection conn = null;
			
			String currentDir = new File(".").getAbsolutePath();
			
			Properties props = new Properties();
			inp = new FileInputStream("/Users/jonathankuhl/Documents/workspace-sts-3.9.5.RELEASE/project1/src/main/resources/project1.properties");
			props.load(inp);
			
			String endpoint = props.getProperty("jdbc.url");
			String username = props.getProperty("jdbc.username");
			String password = props.getProperty("jdbc.password");
			
			conn = DriverManager.getConnection(endpoint, username, password);
			
			return conn;
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} finally {
			try {
				inp.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

}

