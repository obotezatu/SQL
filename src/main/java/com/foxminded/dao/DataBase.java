package com.foxminded.dao;

import static java.lang.System.lineSeparator;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.foxminded.domain.Course;
import com.foxminded.domain.Group;
import com.foxminded.domain.Student;

public class DataBase {

	private String[] courses = { "Math", "Biology", "Accounting", "Agriculture", "Computer Science", "Economics",
			"History", "Management", "Medicine", "Psychology" };
	private String[] groups = generateGroupName();
	private String[] firstNames = { "Li", "Edison", "Dung", "Keren", "Amina", "Juana", "Kelly", "Lan", "Margareta",
			"Micheline", "Susan", "Kimberli", "Maira", "Teresia", "Florentino", "Danny", "Tyisha", "Abdul", "Tamisha",
			"Vivian" };
	private String[] lastNames = { "Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson",
			"Moore", "Taylor", "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia",
			"Martinez", "Robinson" };
	
	private DataSource dataSource;
	private CourseDao courseDao = new CourseDao(dataSource);
	private GroupDao groupDao;
	private StudentDao studentDao;
	private StudentCourseDao  studentCourseDao;

	public DataBase(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void createDataBase() {
		executeSQL("createUser.sql");
		executeSQL("createDB.sql");
	}

	public void deleteDataBase() {
		executeSQL("dropDataBase.sql");
	}

	public void createDataBaseTables() {
		executeSQL("schema.sql");
		insertCourses();
		insertGroups();
		insertStudents(lastNames, firstNames);
		insertStudentCourse();
	}

	private String[] generateGroupName() {
		String[] groupNames = new String[10];
		for (int i = 0; i < 10; i++) {
			groupNames[i] = String.format("%2s-%2s", randomAlphabetic(2).toUpperCase(), randomNumeric(2));
		}
		return groupNames;
	}

	private void insertCourses() {
		Stream.of(courses).map(courseName -> {
			Course course = new Course();
			course.setCourseName(courseName);
			course.setDescription("description");
			return course;
		}).forEach(course -> {
			try {
				courseDao.insert(course);
			} catch (DaoException e) {
				e.printStackTrace();
			}
		});
	}

	private void insertGroups() {
		Stream.of(groups).map(groupName -> {
			Group group = new Group();
			group.setGroupName(groupName);
			return group;
		}).forEach(group -> {
			try {
				groupDao.insert(group);
			} catch (DaoException e) {
				e.printStackTrace();
			}
		});
	}

	private void insertStudents(String[] lastNames, String[] firstNames) {
		Random random = new Random();
		try {
			List<Group> groups = groupDao.getAll();
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

	private void insertStudentCourse() {
		Random random = new Random();
		try {
			List<Student> students = studentDao.getAll();
			List<Course> courses = courseDao.getAll();
			for (int i = 0; i < students.size(); i++) {
				int studentPosition = i;
				int limit = random.nextInt(3) + 1;
				Set<Integer> coursePosition = new HashSet<>();
				for (int j = 0; j < limit; j++) {
					coursePosition.add(random.nextInt(courses.size()));
				}
				coursePosition.forEach(position -> {
					try {
						studentCourseDao.insert(students.get(studentPosition), courses.get(position));
					} catch (DaoException e) {
						e.printStackTrace();
					}
				});
			}
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	private void executeSQL(String fileName) {
		Connection connection = dataSource.getConnection();
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
		String sqlQuery = new String();
		try {
			sqlQuery = Files.lines(getResourceFile(fileName)).collect( Collectors.joining( " " ));//forEach(line -> sqlQuery.append(line).append(lineSeparator()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sqlQuery;
	}
}
