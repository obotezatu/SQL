package com.foxminded.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.foxminded.domain.Student;

public class StudentDao {
 
	private static final String INSERT = "INSERT INTO students(group_id,first_name,last_name) VALUES(?,?,?) RETURNING student_id ";
	private static final String GET_BY_ID = "SELECT * FROM students WHERE student_id = ?";
	private static final String GET_ALL = "SELECT * FROM students";
	private static final String DELETE_STUDENTS_FROM_GROUP = "DELETE FROM students USING groups WHERE students.group_id = groups.group_id AND groups.name = ?";
	private static final String DELETE_STUDENT_BY_ID = "DELETE FROM students WHERE student_id=?";
	private static final String RELATION_STUENTS_COURSES = "SELECT groups.group_id, students.student_id, students.last_name, students.first_name "
			+ "FROM courses  INNER JOIN courses_students as cs  " 
			+ "ON courses.course_id = cs.course_id  "
			+ "INNER JOIN students  " 
			+ "ON cs.student_id = students.student_id  " 
			+ "INNER JOIN groups  "
			+ "ON students.group_id = groups.group_id  " 
			+ "WHERE courses.course_name LIKE ? "
			+ "ORDER BY groups.group_name";
	
	private DataSource dataSource;

	public StudentDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void insert(Student student) throws DaoException {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(INSERT)) {
			statement.setInt(1, student.getGroupId());
			statement.setString(2, student.getFirstName());
			statement.setString(3, student.getLastName());
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next() != false) {
				student.setStudentId(resultSet.getInt("student_id"));
			}
		} catch (SQLException e) {
			throw new DaoException("Cannot insert student", e);
		}
	}

	public Student getById(int studentId) throws DaoException {
		Student student = new Student();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
			statement.setInt(1, studentId);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next() != false) {
					student = getStudent(resultSet);
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Cannot get student by ID.", e);
		}
		return student;
	}

	public List<Student> getAll() throws DaoException {
		List<Student> students = new ArrayList<>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(GET_ALL);
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				students.add(getStudent(resultSet));
			}
		} catch (SQLException e) {
			throw new DaoException("Cannot get all students.", e);
		}
		return students;
	}

	public void deleteStudentsFromGroup(String groupName) throws DaoException {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_STUDENTS_FROM_GROUP)) {
			statement.setString(1, groupName);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Cannot delete students from group.", e);
		}
	}

	private Student getStudent(ResultSet resultSet) throws SQLException {
		Student student = new Student();
		student.setStudentId(resultSet.getInt("student_id"));
		student.setGroupId(resultSet.getInt("group_id"));
		student.setFirstName(resultSet.getString("first_name"));
		student.setLastName(resultSet.getString("last_name"));
		return student;
	}
	
	public void deleteStudentById(int studentId) throws DaoException {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_STUDENT_BY_ID)) {
			statement.setInt(1, studentId);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Cannot delete student by Id.", e);
		}
	}
	
	public List<Student> getStudentsByCourseName(String courseName) throws DaoException {
		List<Student> students = new ArrayList<>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(RELATION_STUENTS_COURSES)) {
			statement.setString(1, courseName + "%");
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Student student = new Student();
					student.setStudentId(resultSet.getInt("student_id"));
					student.setGroupId(resultSet.getInt("group_id"));
					student.setFirstName(resultSet.getString("first_name"));
					student.setLastName(resultSet.getString("last_name"));
					students.add(student);
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Cannot get relation course->students.", e);
		}
		return students;
	}
}
