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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.RandomStringUtils;

public class DataBase {

	private final String[] COURSES = { "Math", "Biology", "Accounting", "Agriculture", "Computer Science", "Economics",
			"History", "Management", "Medicine", "Psychology" };
	private final String[] GROUPS = setGroups();
	private final String[] FIRSTNAMES = { "Li", "Edison", "Dung", "Keren", "Amina", "Juana", "Kelly", "Lan",
			"Margareta", "Micheline", "Susan", "Kimberli", "Maira", "Teresia", "Florentino", "Danny", "Tyisha", "Abdul",
			"Tamisha", "Vivian" };
	private final String[] LASTNAMES = { "Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson",
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
		executeSQL(connection, "schema.sql");
		insertCourses(connection);
		insertGroups(connection);
		insertStudents(LASTNAMES, FIRSTNAMES, connection);
		insertStudentCourse(connection);
	}

	private void insertCourses(Connection connection) {
		Arrays.stream(COURSES).map(courseName -> {
			Course course = new Course();
			course.setCourseName(courseName);
			course.setDescription("description");
			return course;
		}).forEach(course -> new CourseDao().insert(course, connection));
		System.out.println("insertCourses was successfull.");
	}

	private void insertGroups(Connection connection) {
		Arrays.stream(GROUPS).map(groupName -> {
			Group group = new Group();
			group.setGroupName(groupName);
			return group;
		}).forEach(group -> new GroupDao().insert(group, connection));
		System.out.println("insertGroups was successfull.");
	}

	private void insertStudents(String[] lastNames, String[] firstNames, Connection connection) {
		Random random = new Random();
		List<Group> groups = new GroupDao().getAll(connection);
		StudentDao studentDao = new StudentDao();
		int i;
		for (i = 0; i < 200; i++) {
			Student student = new Student();
			student.setFirstName(firstNames[random.nextInt(20)]);
			student.setLastName(lastNames[random.nextInt(20)]);
			student.setGroupId(groups.get(random.nextInt(10)).getGroupId());
			studentDao.insert(student, connection);
		}
		System.out.println("insertStudents was successfull." + i + "students.");
	}

	private void insertStudentCourse(Connection connection) {
		Random random = new Random();
		List<Student> students = new StudentDao().getAll(connection);
		List<Course> courses = new CourseDao().getAll(connection);
		for (int i = 0; i < students.size(); i++) {
			int limit = random.nextInt(3) + 1;
			for (int j = 0; j < limit; j++) {
				int coursPosition = random.nextInt(courses.size());
				Student stud = students.get(i);
				Course cour =  courses.get(coursPosition);
				new StudentCourse().insert(students.get(i), courses.get(coursPosition), connection);
			}
		}
		System.out.println("insertStudentsCourses was successfull.");
	}

	public void executeSQL(Connection connection, String fileName) {
		try (Statement statement = connection.createStatement()) {
			statement.execute(readSql(fileName));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * public Connection getConnection(String dataBase, String user, String
	 * password) { Connection connection = null; try {
	 * Class.forName("org.postgresql.Driver"); connection =
	 * DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/" + dataBase,
	 * user, password); } catch (Exception e) { System.out.println(e); } return
	 * connection; }
	 */
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
