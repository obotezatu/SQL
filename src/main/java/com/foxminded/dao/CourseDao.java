package com.foxminded.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.foxminded.domain.Course;

public class CourseDao implements Dao<Course> {

	private static final String INSERT = "INSERT INTO courses(course_name, course_description) VALUES(?,?)";
	private static final String GET_BY_ID = "SELECT * FROM courses WHERE course_id=?";
	private static final String UPDATE = "UPDATE courses SET course_id=?, course_name=?, course_description =? ";
	private static final String DELETE = "DELETE FROM courses WHERE course_id=?";
	private static final String GET_ALL = "SELECT * FROM courses";

	private DataSource dataSource;

	public CourseDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void insert(Course course) throws DaoException {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(INSERT)) {
			statement.setString(1, course.getCourseName());
			statement.setString(2, course.getDescription());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Cannot insert course.", e);
		}
	}

	@Override
	public Course getById(int courseId) throws DaoException {
		Course course = new Course();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
			statement.setInt(1, courseId);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next() != false) {
					course = setCourseInfo(resultSet);
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Cannot get course by Id.", e);
		}
		return course;
	}

	@Override
	public void update(Course course) throws DaoException {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE)) {
			statement.setInt(1, course.getCourseId());
			statement.setString(2, course.getCourseName());
			statement.setString(3, course.getDescription());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Cannot update course.", e);
		}
	}

	@Override
	public void delete(Course course) throws DaoException {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE)) {
			statement.setInt(1, course.getCourseId());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Cannot delete course.", e);
		}
	}

	@Override
	public List<Course> getAll() throws DaoException {
		List<Course> courses = new ArrayList<>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(GET_ALL);
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				courses.add(setCourseInfo(resultSet));
			}
		} catch (SQLException e) {
			throw new DaoException("Cannot get all course.", e);
		}
		return courses;
	}

	private Course setCourseInfo(ResultSet resultSet) throws SQLException {
		Course course = new Course();
		course.setCourseId(resultSet.getInt("course_id"));
		course.setCourseName(resultSet.getString("course_name"));
		course.setDescription(resultSet.getString("course_description"));
		return course;
	}
}
