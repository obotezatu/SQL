package com.foxminded.obotezatu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDao implements Dao<Course> {

	@Override
	public void insert(Course course, Connection connection) {
		try (PreparedStatement preparedStatement = connection
						.prepareStatement("insert into courses(course_name, course_description) values(?,?)")) {
			preparedStatement.setString(1, course.getCourseName());
			preparedStatement.setString(2, course.getDescription());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	@Override
	public Course getRecordById(int courseId, Connection connection) {
		Course course = new Course();
		try ( PreparedStatement preparedStatement = connection.prepareStatement("select * from courses where course_id=?")) {
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
		try (PreparedStatement preparedStatement = connection
						.prepareStatement("update courses set course_id=?, course_name=?, course_description =? ")) {
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
		try (PreparedStatement preparedStatement = connection.prepareStatement("delete from courses where course_id=?")) {
			preparedStatement.setInt(1, course.getCourseId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	@Override
	public List<Course> getAll(Connection connection) {
		List<Course> courses = new ArrayList<>();
		try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM courses");
				ResultSet resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				Course course = new Course();
				course.setCourseId(resultSet.getInt("course_id"));
				course.setCourseName(resultSet.getString("course_name"));
				course.setDescription(resultSet.getString("course_description"));
				courses.add(course);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return courses;
	}
}
