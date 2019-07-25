package com.foxminded.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.foxminded.domain.Relation;
import com.foxminded.domain.Student;

public class MenuDao {

	DataSource dataSource = new DataSource();

	public Map<String, Integer> getGroupLessCount(int count) throws DaoException {
		Map<String, Integer> studentsInGroup = new HashMap<>();
		try (Connection connection = dataSource.getConnectionFoxy();
				PreparedStatement statement = connection
						.prepareStatement("SELECT  groups.group_name, COUNT (students.group_id) AS count "
								+ "FROM students	" + "RIGHT JOIN groups ON students.group_id = groups.group_id "
								+ "GROUP BY groups.group_name	" + "HAVING COUNT(students.group_id) <= "
								+ String.valueOf(count) + "ORDER BY count");
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
		try (Connection connection = dataSource.getConnectionFoxy();
				PreparedStatement statement = connection.prepareStatement(
						"SELECT groups.group_id, students.student_id, students.last_name, students.first_name, courses.course_name "
								+ "FROM courses  INNER JOIN courses_students as cs  "
								+ "ON courses.course_id = cs.course_id  " + "INNER JOIN students  "
								+ "ON cs.student_id = students.student_id  " + "INNER JOIN groups  "
								+ "ON students.group_id = groups.group_id  " + "WHERE courses.course_name LIKE ? "
								+ "ORDER BY groups.group_name")) {
			statement.setString(1, courseName + "%");
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					//Relation relation = new Relation();
					Student student = new Student();
					student.setStudentId(resultSet.getInt("student_id"));
					student.setGroupId(resultSet.getInt("group_id"));
					student.setFirstName(resultSet.getString("first_name"));
					student.setLastName(resultSet.getString("last_name"));
					//relation.setCourseName(resultSet.getString("course_name"));
					students.add(student);
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Cannot get relation course->students.", e);
		}
		return students;
	}

	public void deleteStudentById(int studentId) throws DaoException {
		try (Connection connection = dataSource.getConnectionFoxy();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM students WHERE student_id=?")) {
			statement.setInt(1, studentId);
		} catch (SQLException e) {
			throw new DaoException("Cannot delete student by Id.", e);
		}
	}
}
