package com.foxminded.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataSource {

	private String url;
	private String user;
	private String password;

	public DataSource(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
	}

	public Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
}
