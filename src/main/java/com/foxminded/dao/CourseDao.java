package com.foxminded.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.foxminded.domain.Course;

public class CourseDao implements Dao<Course> {

	DataSource dataSource;

	public CourseDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void insert(Course course) throws DaoException {
		final String QUERY = "INSERT INTO courses(course_name, course_description) VALUES(?,?)";
		try (Connection connection = getDataSource().getConnection();
				PreparedStatement statement = connection.prepareStatement(QUERY)) {
			statement.setString(1, course.getCourseName());
			statement.setString(2, course.getDescription());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Cannot insert course.", e);
		}
	}

	@Override
	public Course getById(int courseId) throws DaoException {
		final String QUERY = "SELECT * FROM courses WHERE course_id=?";
		Course course = new Course();
		try (Connection connection = getDataSource().getConnection();
				PreparedStatement statement = connection.prepareStatement(QUERY)) {
			statement.setInt(1, courseId);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					course.setCourseId(courseId);
					course.setCourseName(resultSet.getString("course_name"));
					course.setDescription(resultSet.getString("course_description"));
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Cannot get course by Id.", e);
		}
		return course;
	}

	@Override
	public void update(Course course) throws DaoException {
		final String QUERY = "UPDATE courses SET course_id=?, course_name=?, course_description =? ";
		try (Connection connection = getDataSource().getConnection();
				PreparedStatement statement = connection.prepareStatement(QUERY)) {
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
		final String QUERY = "DELETE FROM courses WHERE course_id=?";
		try (Connection connection = getDataSource().getConnection();
				PreparedStatement statement = connection.prepareStatement(QUERY)) {
			statement.setInt(1, course.getCourseId());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Cannot delete course.", e);
		}
	}

	@Override
	public List<Course> getAll() throws DaoException {
		final String QUERY = "SELECT * FROM courses";
		List<Course> courses = new ArrayList<>();
		try (Connection connection = getDataSource().getConnection();
				PreparedStatement statement = connection.prepareStatement(QUERY);
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				Course course = new Course();
				course.setCourseId(resultSet.getInt("course_id"));
				course.setCourseName(resultSet.getString("course_name"));
				course.setDescription(resultSet.getString("course_description"));
				courses.add(course);
			}
		} catch (SQLException e) {
			throw new DaoException("Cannot get all course.", e);
		}
		return courses;
	}
}
