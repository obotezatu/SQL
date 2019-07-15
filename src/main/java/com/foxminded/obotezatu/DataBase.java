package com.foxminded.obotezatu;

import static java.lang.System.lineSeparator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.commons.lang3.RandomStringUtils;

public class DataBase {
	
	private final String[] COURSES = {"Math", "Biology", "Accounting","Agriculture", "Computer Science", "Economics", "History", "Management", "Medicine", "Psychology"};
<<<<<<< HEAD
	private final String[] GROUPS = setGroups(); 
	private final String[] FIRSTNAME = {"Li","Edison","Dung","Keren","Amina","Juana","Kelly","Lan","Margareta","Micheline","Susan","Kimberli","Maira","Teresia","Florentino","Danny","Tyisha","Abdul","Tamisha","Vivian"};
	private final String[] LASTNAME = {"Smith", "Johnson",  "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor", "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia", "Martinez", "Robinson"};
	
	public String[] setGroups() {
		String[] groups = new String[10];
			for (int i=0; i<10; i++) {
				StringBuilder group = new StringBuilder();
				groups[i] = group.append(RandomStringUtils.randomAlphabetic(2).toUpperCase()).append("-").append(RandomStringUtils.randomNumeric(2)).toString();
			}
			return groups;
	}
	
	public String[] getGroups() {
		return GROUPS;
	}
	
	public String[] getCourses() {
		return COURSES;
	}
	
	public void createDataBase(Connection connection) {
		  executeSQL(connection, "createUser.sql");
		  executeSQL(connection, "createDB.sql");
		  executeSQL(connection, "schema.sql"); 		 
	}
	
	public void deleteDataBase(Connection connection) {
		executeSQL(connection, "dropDataBase.sql");
	}
	
	private void executeSQL(Connection connection, String fileName) {
=======
	private final String[] GROUPS = new String[10]; 
	
	public void setGROUPS() {
			for (int i=0; i<10; i++) {
				StringBuilder group = new StringBuilder();
				this.GROUPS[i] = group.append(RandomStringUtils.randomAlphabetic(2).toUpperCase()).append("-").append(RandomStringUtils.randomNumeric(2)).toString();
			}
	}
	
	public String[] getGROUPS() {
		return GROUPS;
	}
	
	public String[] getCOURSES() {
		return COURSES;
	}
	
	public void executeSQL(Connection connection, String fileName) {
>>>>>>> 618fb7cb96c6f12517c2654599bbe43c1117c49f
		try (Statement statement = connection.createStatement()) {
			statement.execute(readSql(fileName));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

<<<<<<< HEAD
=======
	public Connection getConnection(String dataBase, String user, String password) {
		Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/" + dataBase, user, password);
		} catch (Exception e) {
			System.out.println(e);
		}
		return connection;
	}

>>>>>>> 618fb7cb96c6f12517c2654599bbe43c1117c49f
	private Path getResourceFile(String resourceFileName) {
		Path path = null;
		try {
			path = Paths.get(getClass().getClassLoader().getResource(resourceFileName).toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return path;
	}

	private String readSql(String fileName) {
		StringBuilder sqlQuery = new StringBuilder();
		try {
			Files.lines(getResourceFile(fileName)).forEach(line -> sqlQuery.append(line).append(lineSeparator()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sqlQuery.toString();
	}
}
