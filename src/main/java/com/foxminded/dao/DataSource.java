package com.foxminded.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataSource {

	private static Connection getConnection(String dataBase, String user, String password) {
		Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/" + dataBase, user, password);
		} catch (Exception e) {
			System.out.println(e);
		}
		return connection;
	}
}
