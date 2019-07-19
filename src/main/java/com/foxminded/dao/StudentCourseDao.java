package com.foxminded.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.foxminded.bean.Course;
import com.foxminded.bean.Relation;
import com.foxminded.bean.Student;

public class StudentCourseDao {

	public void insert(Student student, Course course, Connection connection) {
		try (PreparedStatement preparedStatement = connection
				.prepareStatement("INSERT INTO courses_students(course_id, student_id) VALUES(?,?)")) {
			preparedStatement.setInt(1, course.getCourseId());
			preparedStatement.setInt(2, student.getStudentId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(int studentId, int courseId, Connection connection) {
		try (PreparedStatement preparedStatement = connection
				.prepareStatement("DELETE FROM courses_students where student_id=? AND course_id =?")) {
			preparedStatement.setInt(1, studentId);
			preparedStatement.setInt(2, courseId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Relation> getCoursesByStudent(Student student, Connection connection) {
		List<Relation> relations = new ArrayList<>();
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"SELECT groups.group_name, students.student_id, students.last_name, students.first_name, courses.course_name, courses.course_id "
						+ "FROM courses  INNER JOIN courses_students as cs  " 
						+ "ON courses.course_id = cs.course_id  "
						+ "INNER JOIN students  " + "ON cs.student_id = students.student_id  " 
						+ "INNER JOIN groups  "
						+ "ON students.group_id = groups.group_id  " 
						+ "WHERE students.student_id = ? "
						+ "ORDER BY groups.group_name")) {
			preparedStatement.setInt(1, student.getStudentId());
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					Relation relation = new Relation();
					relation.setStudentId(resultSet.getInt("student_id"));
					relation.setGroupName(resultSet.getString("group_name"));
					relation.setStudentFirstName(resultSet.getString("first_name"));
					relation.setStudentLastName(resultSet.getString("last_name"));
					relation.setCourseName(resultSet.getString("course_name"));
					relation.setCourseId(resultSet.getInt("course_id"));
					relations.add(relation);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return relations;
	}
}
