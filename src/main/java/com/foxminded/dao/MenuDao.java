package com.foxminded.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.foxminded.obotezatu.Group;
import com.foxminded.obotezatu.Student;

public class MenuDao {

	public Map<String, Integer> getGroupLessCount(int count, Connection connection) {
		Map<String, Integer> studentsInGroup = new HashMap<>();
		try (PreparedStatement preparedStatement = connection
				.prepareStatement("SELECT  groups.group_name, COUNT (students.group_id) AS count " + "FROM students	"
						+ "RIGHT JOIN groups ON students.group_id = groups.group_id " + "GROUP BY groups.group_name	"
						+ "HAVING COUNT(students.group_id) <= " + String.valueOf(count) + "ORDER BY count");
				ResultSet resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				studentsInGroup.put(resultSet.getString("group_name"), resultSet.getInt("count"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return studentsInGroup;
	}

	public List<Student> getRelationStudentsCourses(String courseName, Connection connection) {
		List<Student> students = new ArrayList<>();
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"SELECT groups.group_name, students.student_id, students.last_name, students.first_name, courses.course_name "
				+ "FROM courses  INNER JOIN courses_students as cs  "
				+ "ON courses.course_id = cs.course_id  "
				+ "INNER JOIN students  "
				+ "ON cs.student_id = students.student_id  "
				+ "INNER JOIN groups  "
				+ "ON students.group_id = groups.group_id  "
				+ "WHERE courses.course_name LIKE ? "
				+ "ORDER BY groups.group_name")) {
			preparedStatement.setString(1, courseName + "%");
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					Student student = new Student();
					//student.setStudentId(resultSet.getInt("student_id"));
					//student.setGroupId(resultSet.getInt("group_id"));
					student.setFirstName(resultSet.getString("first_name"));
					student.setLastName(resultSet.getString("last_name"));
					//students.add(student);
					Group group = new Group();
					group.set
				}
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return students;
	}
}
