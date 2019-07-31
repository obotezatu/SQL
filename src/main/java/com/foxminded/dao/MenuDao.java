package com.foxminded.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.foxminded.domain.Student;

public class MenuDao {

	private static final String GROUP_LESS_COUNT = "SELECT  groups.group_name, COUNT (students.group_id) AS count " 
			+ "FROM students "
			+ "RIGHT JOIN groups ON students.group_id = groups.group_id " 
			+ "GROUP BY groups.group_name "
			+ "HAVING COUNT(students.group_id) <= ";
	private static final String RELATION_STUENTS_COURSES = "SELECT groups.group_id, students.student_id, students.last_name, students.first_name "
			+ "FROM courses  INNER JOIN courses_students as cs  " 
			+ "ON courses.course_id = cs.course_id  "
			+ "INNER JOIN students  " 
			+ "ON cs.student_id = students.student_id  " 
			+ "INNER JOIN groups  "
			+ "ON students.group_id = groups.group_id  " 
			+ "WHERE courses.course_name LIKE ? "
			+ "ORDER BY groups.group_name";
	private static final String DELETE_STUDENT_BY_ID = "DELETE FROM students WHERE student_id=?";
	private DataSource dataSource;

	public MenuDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Map<String, Integer> getGroupLessCount(int count) throws DaoException {
		Map<String, Integer> studentsInGroup = new HashMap<>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(GROUP_LESS_COUNT + String.valueOf(count));
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				studentsInGroup.put(resultSet.getString("group_name"), resultSet.getInt("count"));
			}
		} catch (SQLException e) {
			throw new DaoException("Cannot get groups.", e);
		}
		return studentsInGroup;
	}

	public List<Student> getRelationStudentsCourses(String courseName) throws DaoException {
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

	public void deleteStudentById(int studentId) throws DaoException {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_STUDENT_BY_ID)) {
			statement.setInt(1, studentId);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Cannot delete student by Id.", e);
		}
	}
}
