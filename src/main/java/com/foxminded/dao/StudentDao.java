package com.foxminded.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.foxminded.domain.Student;

public class StudentDao {

	DataSource dataSource = new DataSource();

	public void insert(Student student) throws DaoException {
		try (Connection connection = dataSource.getConnectionFoxy();
				PreparedStatement statement = connection
						.prepareStatement("insert into students(group_id,first_name,last_name) values(?,?,?)")) {
			statement.setInt(1, student.getGroupId());
			statement.setString(2, student.getFirstName());
			statement.setString(3, student.getLastName());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Cannot insert student", e);
		}
	}

	public Student getByName(String firstName, String lastName) throws DaoException {
		Student student = new Student();
		try (Connection connection = dataSource.getConnectionFoxy();
				PreparedStatement statement = connection.prepareStatement(
						"SELECT * " + "FROM students " + "WHERE first_name " + "LIKE ? AND last_name LIKE ?")) {
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					student.setStudentId(resultSet.getInt("student_id"));
					student.setGroupId(resultSet.getInt("group_id"));
					student.setFirstName(resultSet.getString("first_name"));
					student.setLastName(resultSet.getString("last_name"));
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Cannot get student by name.", e);
		}
		return student;
	}

	public Student getById(int studentId) throws DaoException {
		Student student = new Student();
		try (Connection connection = dataSource.getConnectionFoxy();
				PreparedStatement statement = connection
						.prepareStatement("SELECT * " + "FROM students " + "WHERE student_id = ?")) {
			statement.setInt(1, studentId);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					student.setStudentId(resultSet.getInt("student_id"));
					student.setGroupId(resultSet.getInt("group_id"));
					student.setFirstName(resultSet.getString("first_name"));
					student.setLastName(resultSet.getString("last_name"));
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Cannot get student by ID.", e);
		}
		return student;
	}

	public List<Student> getAll() throws DaoException {
		List<Student> students = new ArrayList<>();
		try (Connection connection = dataSource.getConnectionFoxy();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM students");
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				Student student = new Student();
				student.setStudentId(resultSet.getInt("student_id"));
				student.setGroupId(resultSet.getInt("group_id"));
				student.setFirstName(resultSet.getString("first_name"));
				student.setLastName(resultSet.getString("last_name"));
				students.add(student);
			}
		} catch (SQLException e) {
			throw new DaoException("Cannot get all students.", e);
		}
		return students;
	}

	public void deleteStudentsFromGroup(String groupName) throws DaoException {
		try (Connection connection = dataSource.getConnectionFoxy();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM students USING groups"
						+ "WHERE students.group_id = groups.group_id AND groups.name = ?")) {
			statement.setString(1, groupName);
		} catch (SQLException e) {
			throw new DaoException("Cannot delete students from group.", e);
		}
	}
}
