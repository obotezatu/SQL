package com.foxminded.obotezatu;

import static java.lang.System.lineSeparator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.RandomStringUtils;

public class DataBase {

	private final String[] COURSES = { "Math", "Biology", "Accounting", "Agriculture", "Computer Science", "Economics",
			"History", "Management", "Medicine", "Psychology" };
	private final String[] GROUPS = setGroups();
	private final String[] FIRSTNAME = { "Li", "Edison", "Dung", "Keren", "Amina", "Juana", "Kelly", "Lan", "Margareta",
			"Micheline", "Susan", "Kimberli", "Maira", "Teresia", "Florentino", "Danny", "Tyisha", "Abdul", "Tamisha",
			"Vivian" };
	private final String[] LASTNAME = { "Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson",
			"Moore", "Taylor", "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia",
			"Martinez", "Robinson" };

	public String[] setGroups() {
		String[] groups = new String[10];
		for (int i = 0; i < 10; i++) {
			StringBuilder group = new StringBuilder();
			groups[i] = group.append(RandomStringUtils.randomAlphabetic(2).toUpperCase()).append("-")
					.append(RandomStringUtils.randomNumeric(2)).toString();
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
	}

	public void deleteDataBase(Connection connection) {
		executeSQL(connection, "dropDataBase.sql");
	}

	public void createDataBaseTables(Connection connection) {
		// executeSQL(connection, "schema.sql");
		insertCourses(connection);
		insertGroups(connection);
	}

	private void insertCourses(Connection connection) {
		Arrays.stream(COURSES).map(this::generateCourse).forEach(course -> new CourseDao().insert(course, connection));
		;
	}

	private void insertGroups(Connection connection) {
		Arrays.stream(GROUPS).map(name -> {
			Group group = new Group();
			group.setGroupName(name);
			return group;
		}).forEach(group -> new GroupDao().insert(group, connection));
	}

	private Course generateCourse(String name) {
		Course course = new Course();
		course.setCourseName(name);
		course.setDescription(RandomStringUtils.randomAlphabetic(20));
		return course;
	}

	public void executeSQL(Connection connection, String fileName) {
		try (Statement statement = connection.createStatement()) {
			statement.execute(readSql(fileName));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

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
