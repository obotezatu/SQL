package com.foxminded.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.foxminded.obotezatu.Group;
import com.foxminded.obotezatu.Student;

public class StudentDao {
	
	public void insert(Student student, Connection connection) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
						"insert into students(group_id,first_name,last_name) values(?,?,?)")) {
			preparedStatement.setInt(1, student.getGroupId());
			preparedStatement.setString(2, student.getFirstName());
			preparedStatement.setString(3, student.getLastName());
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public Student getRecordByName(String firstName, String lastName, Connection connection) {
		Student student = new Student();
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"SELECT * "
				+ "FROM students "
				+ "WHERE first_name "
				+ "LIKE ? AND last_name LIKE ?")) {
			preparedStatement.setString(1, firstName);
			preparedStatement.setString(2, lastName);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					student.setStudentId(resultSet.getInt("student_id"));
					student.setGroupId(resultSet.getInt("group_id"));
					student.setFirstName(resultSet.getString("first_name"));
					student.setLastName(resultSet.getString("last_name"));
				}
			} 
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}
	
	public List<Student> getAll(Connection connection) {
		List<Student> students = new ArrayList<>();
		try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM students");
				ResultSet resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				Student student = new Student();
				student.setStudentId(resultSet.getInt("student_id"));
				student.setGroupId(resultSet.getInt("group_id"));
				student.setFirstName(resultSet.getString("first_name"));
				student.setLastName(resultSet.getString("last_name"));
				students.add(student);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return students;
	}

	

	public void deleteStudentsFromGroup(String groupName, Connection connection) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"DELETE FROM students USING groups"
				+ "WHERE students.group_id = groups.group_id AND groups.name = ?")) {
			preparedStatement.setString(1, groupName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
