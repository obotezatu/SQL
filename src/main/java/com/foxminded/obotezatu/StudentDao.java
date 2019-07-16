package com.foxminded.obotezatu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentDao {

	private Connection connection;

	public StudentDao(Connection connection) {
		this.connection = connection;
	}
	
	public List<Student> getAll() {
		List<Student> students = new ArrayList<>();
		try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM students");
				ResultSet resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				Student student = new Student();
				student.setStudentId(resultSet.getString("student_id"));
				student.setGroupId(resultSet.getString("group_id"));
				student.setFirstName(resultSet.getString("first_name"));
				student.setLastName(resultSet.getString("last_name"));
				students.add(student);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return students;
	}

	public List<Student> getRelationStudentsCourses(String courseName) {
		List<Student> students = new ArrayList<>();
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"SELECT students.student_id, students.group_id, students.first_name, students.last_name, courses.name "
						+ "FROM students "
						+ "INNER JOIN courses_students as cs "
						+ "ON students.student_id = cs.student_id "
						+ "INNER JOIN courses "
						+ "ON courses.course_id = cs.course_id	WHERE courses.name LIKE ?")) {
			preparedStatement.setString(1, courseName+"%");
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					Student student = new Student();
					student.setStudentId(resultSet.getString("student_id"));
					student.setGroupId(resultSet.getString("group_id"));
					student.setFirstName(resultSet.getString("first_name"));
					student.setLastName(resultSet.getString("last_name"));
					//student.setCourse(resultSet.getString("name"));
					students.add(student);
				}
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return students;
	}

	public void deleteStudentsFromGroup(String groupName) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"DELETE FROM students USING groups"
				+ "WHERE students.group_id = groups.group_id AND groups.name = ?")) {
			preparedStatement.setString(1, groupName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Map<String,Integer> getGroupLessTen() {
		Map<String,Integer> studentsInGroup = new HashMap<>();
		try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT  group_id, COUNT (group_id) FROM public.students GROUP BY group_id HAVING COUNT(group_id) < 10");
				ResultSet resultSet = preparedStatement.executeQuery()){
			while(resultSet.next()) {
				studentsInGroup.put(resultSet.getString("group_id"), resultSet.getInt("count"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return studentsInGroup;
	}
}
