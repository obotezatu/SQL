package com.foxminded.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataSource {

	public Connection getConnectionPostgres() {
		Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "1");
		} catch (Exception e) {
			System.out.println(e);
		}
		return connection;
	}

	public Connection getConnectionFoxy() {
		Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/foxy", "local", "1");
		} catch (Exception e) {
			System.out.println(e);
		}
		return connection;
	}
}
