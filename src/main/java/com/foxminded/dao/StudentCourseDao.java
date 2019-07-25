package com.foxminded.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.foxminded.domain.Course;
import com.foxminded.domain.Relation;
import com.foxminded.domain.Student;

public class StudentCourseDao {

	DataSource dataSource = new DataSource();

	public void insert(Student student, Course course) throws DaoException {
		try (Connection connection = dataSource.getConnectionFoxy();
				PreparedStatement statement = connection
						.prepareStatement("INSERT INTO courses_students(course_id, student_id) VALUES(?,?)")) {
			statement.setInt(1, course.getCourseId());
			statement.setInt(2, student.getStudentId());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Cannot insert relation student->courses.", e);
		}
	}

	public void delete(int studentId, int courseId) throws DaoException {
		try (Connection connection = dataSource.getConnectionFoxy();
				PreparedStatement statement = connection
						.prepareStatement("DELETE FROM courses_students where student_id=? AND course_id =?")) {
			statement.setInt(1, studentId);
			statement.setInt(2, courseId);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Cannot insert group.", e);
		}
	}

	public List<Relation> getCoursesByStudent(Student student) throws DaoException {
		List<Relation> relations = new ArrayList<>();
		try (Connection connection = dataSource.getConnectionFoxy();
				PreparedStatement statement = connection.prepareStatement(
						"SELECT groups.group_name, students.student_id, students.last_name, students.first_name, courses.course_name, courses.course_id "
								+ "FROM courses  INNER JOIN courses_students as cs  "
								+ "ON courses.course_id = cs.course_id  " + "INNER JOIN students  "
								+ "ON cs.student_id = students.student_id  " + "INNER JOIN groups  "
								+ "ON students.group_id = groups.group_id  " + "WHERE students.student_id = ? "
								+ "ORDER BY groups.group_name")) {
			statement.setInt(1, student.getStudentId());
			try (ResultSet resultSet = statement.executeQuery()) {
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
			throw new DaoException("Cannot insert group.", e);
		}
		return relations;
	}
}
