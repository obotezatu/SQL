package com.foxminded.obotezatu;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBase {
	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/foxminded", "postgres", "1");
		} catch (Exception e) {
			System.out.println(e);
		}
		return con;
	}

}
