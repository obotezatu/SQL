package com.foxminded.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.foxminded.bean.Course;

public class CourseDao implements Dao<Course> {

	@Override
	public void insert(Course course, Connection connection) {
		final String query = "INSERT INTO courses(course_name, course_description) VALUES(?,?)";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, course.getCourseName());
			preparedStatement.setString(2, course.getDescription());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Course getById(int courseId, Connection connection) {
		final String query = "SELECT * FROM courses WHERE course_id=?";
		Course course = new Course();
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, courseId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					course.setCourseId(courseId);
					course.setCourseName(resultSet.getString("course_name"));
					course.setDescription(resultSet.getString("course_description"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return course;
	}

	@Override
	public void update(Course course, Connection connection) {
		final String query = "UPDATE courses SET course_id=?, course_name=?, course_description =? ";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, course.getCourseId());
			preparedStatement.setString(2, course.getCourseName());
			preparedStatement.setString(3, course.getDescription());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Course course, Connection connection) {
		final String query = "DELETE FROM courses WHERE course_id=?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, course.getCourseId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Course> getAll(Connection connection) {
		final String query = "SELECT * FROM courses";
		List<Course> courses = new ArrayList<>();
		try (PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				Course course = new Course();
				course.setCourseId(resultSet.getInt("course_id"));
				course.setCourseName(resultSet.getString("course_name"));
				course.setDescription(resultSet.getString("course_description"));
				courses.add(course);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return courses;
	}
}
