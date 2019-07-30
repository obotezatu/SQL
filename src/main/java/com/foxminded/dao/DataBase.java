package com.foxminded.dao;

import static java.lang.System.lineSeparator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.postgresql.util.PSQLException;

import com.foxminded.domain.Course;
import com.foxminded.domain.Group;
import com.foxminded.domain.Student;

public class DataBase {

	private final String[] COURSES = { "Math", "Biology", "Accounting", "Agriculture", "Computer Science", "Economics",
			"History", "Management", "Medicine", "Psychology" };
	private final String[] GROUPS = generateGroups();
	private final String[] FIRSTNAMES = { "Li", "Edison", "Dung", "Keren", "Amina", "Juana", "Kelly", "Lan",
			"Margareta", "Micheline", "Susan", "Kimberli", "Maira", "Teresia", "Florentino", "Danny", "Tyisha", "Abdul",
			"Tamisha", "Vivian" };
	private final String[] LASTNAMES = { "Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson",
			"Moore", "Taylor", "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia",
			"Martinez", "Robinson" };
	private DataSource dataSource;

	public DataBase(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void createDataBase() {
		executeSQL(getDataSource().getConnection(), "createUser.sql");
		executeSQL(getDataSource().getConnection(), "createDB.sql");
	}

	public void deleteDataBase() {
		executeSQL(getDataSource().getConnection(), "dropDataBase.sql");
	}

	public void createDataBaseTables() {
		executeSQL(getDataSource().getConnection(), "schema.sql");
		insertCourses();
		insertGroups();
		insertStudents(LASTNAMES, FIRSTNAMES);
		try {
			insertStudentCourse();
		} catch (PSQLException e) {
			e.printStackTrace();
		}
	}

	private String[] generateGroups() {
		String[] groups = new String[10];
		for (int i = 0; i < 10; i++) {
			StringBuilder group = new StringBuilder();
			groups[i] = group.append(RandomStringUtils.randomAlphabetic(2).toUpperCase()).append("-")
					.append(RandomStringUtils.randomNumeric(2)).toString();
		}
		return groups;
	}

	private void insertCourses() {
		Arrays.stream(COURSES).map(courseName -> {
			Course course = new Course();
			course.setCourseName(courseName);
			course.setDescription("description");
			return course;
		}).forEach(course -> {
			try {
				new CourseDao(getDataSource()).insert(course);
			} catch (DaoException e) {
				e.printStackTrace();
			}
		});
	}

	private void insertGroups() {
		Arrays.stream(GROUPS).map(groupName -> {
			Group group = new Group();
			group.setGroupName(groupName);
			return group;
		}).forEach(group -> {
			try {
				new GroupDao(getDataSource()).insert(group);
			} catch (DaoException e) {
				e.printStackTrace();
			}
		});
	}

	private void insertStudents(String[] lastNames, String[] firstNames) {
		Random random = new Random();
		try {
			List<Group> groups = new GroupDao(getDataSource()).getAll();
			StudentDao studentDao = new StudentDao(getDataSource());
			for (int i = 0; i < 200; i++) {
				Student student = new Student();
				student.setFirstName(firstNames[random.nextInt(20)]);
				student.setLastName(lastNames[random.nextInt(20)]);
				student.setGroupId(groups.get(random.nextInt(10)).getGroupId());
				studentDao.insert(student);
			}
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	private void insertStudentCourse() throws PSQLException {
		Random random = new Random();
		try {
			List<Student> students = new StudentDao(getDataSource()).getAll();
			List<Course> courses = new CourseDao(getDataSource()).getAll();
			for (int i = 0; i < students.size(); i++) {
				int studentPosition = i;
				int limit = random.nextInt(3) + 1;
				Set<Integer> coursePosition = new HashSet<>();
				for (int j = 0; j < limit; j++) {
					coursePosition.add(random.nextInt(courses.size()));
				}
				coursePosition.forEach(position -> {
					try {
						new StudentCourseDao(getDataSource()).insert(students.get(studentPosition),
								courses.get(position));
					} catch (DaoException e) {
						e.printStackTrace();
					}
				});
			}
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	private void executeSQL(Connection connection, String fileName) {
		try (Statement statement = connection.createStatement()) {
			statement.execute(readSql(fileName));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
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
