package com.foxminded.obotezatu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDao implements Dao<Course> {

	public static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/foxminded", "postgres", "1");
		} catch (Exception e) {
			System.out.println(e);
		}
		return connection;
	}

	@Override
	public void insert(Course course) {
		try (Connection connection = getConnection();
				PreparedStatement ps = connection
						.prepareStatement("insert into courses(course_id, name, description) values(?,?,?)")) {
			ps.setString(1, course.getCourseId());
			ps.setString(2, course.getName());
			ps.setString(3, course.getDescription());
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	@Override
	public Course getRecordById(String courseId) {
		Course course = new Course();
		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement("select * from courses where course_id=?")) {
			ps.setString(1, courseId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					course.setCourseId(courseId);
					course.setName(rs.getString("name"));
					course.setDescription(rs.getString("description"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return course;
	}

	@Override
	public void update(Course course) {
		try (Connection connection = getConnection();
				PreparedStatement ps = connection
						.prepareStatement("update courses set course_id=?, name=?, description =? ")) {
			ps.setString(1, course.getCourseId());
			ps.setString(2, course.getName());
			ps.setString(3, course.getDescription());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Course course) {
		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement("delete from courses where course_id=?")) {
			ps.setString(1, course.getCourseId());
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	@Override
	public List<Course> getAll() {
		List<Course> courses = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement("SELECT * FROM courses");
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Course course = new Course();
				course.setCourseId(rs.getString("course_id"));
				course.setName(rs.getString("name"));
				course.setDescription(rs.getString("description"));
				courses.add(course);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return courses;
	}
}
