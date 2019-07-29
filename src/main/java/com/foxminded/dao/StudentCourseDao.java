package com.foxminded.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.foxminded.domain.Course;
import com.foxminded.domain.Student;

public class StudentCourseDao {

	DataSource dataSource = new DataSource();

	public void insert(Student student, Course course) throws DaoException {
		final String QUERY = "INSERT INTO courses_students(course_id, student_id) "
				+ "SELECT ?,? "
				+ "WHERE NOT EXISTS ("
				+ "SELECT 1 FROM courses_students "
				+ "WHERE course_id=? AND student_id = ?)";
		try (Connection connection = dataSource.getConnectionFoxy();
				PreparedStatement statement = connection.prepareStatement(QUERY)) {
			statement.setInt(1, course.getCourseId());
			statement.setInt(2, student.getStudentId());
			statement.setInt(3, course.getCourseId());
			statement.setInt(4, student.getStudentId());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Cannot insert relation student->courses.", e);
		}
	}

	public void delete(int studentId, int courseId) throws DaoException {
		final String QUERY = "DELETE FROM courses_students WHERE student_id=? AND course_id =?";
		try (Connection connection = dataSource.getConnectionFoxy();
				PreparedStatement statement = connection.prepareStatement(QUERY)) {
			statement.setInt(1, studentId);
			statement.setInt(2, courseId);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Cannot delete relation student->courses.", e);
		}
	}

	public List<Course> getCoursesByStudent(Student student) throws DaoException {
		final String QUERY = "SELECT  courses.course_name, courses.course_id "
				+ "FROM courses  INNER JOIN courses_students as cs  " 
				+ "ON courses.course_id = cs.course_id "
				+ "INNER JOIN students  " 
				+ "ON cs.student_id = students.student_id " 
				+ "INNER JOIN groups  "
				+ "ON students.group_id = groups.group_id " 
				+ "WHERE students.student_id = ? "
				+ "ORDER BY groups.group_name";
		List<Course> courses = new ArrayList<>();
		try (Connection connection = dataSource.getConnectionFoxy();
				PreparedStatement statement = connection.prepareStatement(QUERY)) {
			statement.setInt(1, student.getStudentId());
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Course course = new Course();
					course.setCourseName(resultSet.getString("course_name"));
					course.setCourseId(resultSet.getInt("course_id"));
					courses.add(course);
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Cannot get relation student->courses.", e);
		}
		return courses;
	}
}
