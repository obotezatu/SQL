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

	private static final String INSERT = "INSERT INTO courses_students(course_id, student_id) VALUES (?, ?) "
			+ "ON CONFLICT ON CONSTRAINT courses_students_course_id_student_id_key "
			+ "DO NOTHING ";
	private static final String DELETE = "DELETE FROM courses_students WHERE student_id=? AND course_id =?";
	private static final String COURSE_BY_STUDENT = "SELECT  courses.course_name, courses.course_id "
			+ "FROM courses  INNER JOIN courses_students as cs  " 
			+ "ON courses.course_id = cs.course_id "
			+ "INNER JOIN students  " 
			+ "ON cs.student_id = students.student_id " 
			+ "INNER JOIN groups  "
			+ "ON students.group_id = groups.group_id " 
			+ "WHERE students.student_id = ? "
			+ "ORDER BY groups.group_name";
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

	public StudentCourseDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void insert(Student student, Course course) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(INSERT)) {
			statement.setInt(1, course.getCourseId());
			statement.setInt(2, student.getStudentId());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(int studentId, int courseId) throws DaoException {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE)) {
			statement.setInt(1, studentId);
			statement.setInt(2, courseId);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Cannot delete relation student->courses.", e);
		}
	}

	public List<Course> getCoursesByStudent(Student student) throws DaoException {
		List<Course> courses = new ArrayList<>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(COURSE_BY_STUDENT)) {
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
}
