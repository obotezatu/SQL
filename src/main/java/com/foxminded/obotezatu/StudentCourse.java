package com.foxminded.obotezatu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentCourse {

	public void insert(Student student, Course course, Connection connection) {
		try (PreparedStatement preparedStatement = connection.prepareStatement("insert into courses_students(course_id, student_id) values(?,?)")) {
			preparedStatement.setInt(1, course.getCourseId());
			preparedStatement.setInt(2, student.getStudentId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
}
