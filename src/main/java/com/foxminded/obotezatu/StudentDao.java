package com.foxminded.obotezatu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentDao implements Dao<Student> {

	public static Connection getConnection() {
		Connection connection= null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/foxminded", "postgres", "1");
		} catch (Exception e) {
			System.out.println(e);
		}
		return connection;
	}

	@Override
	public void insert(Student student) {
		try {
			Connection connection= getConnection();
			PreparedStatement ps = connection.prepareStatement("insert into students(student_id,group_id,first_name,last_name) values(?,?,?,?)");
			ps.setString(1, student.getStudentId());
			ps.setString(2, student.getGroupId());
			ps.setString(3, student.getFirstName());
			ps.setString(4, student.getLastName());
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void update(Student student) {
		try {
			Connection connection= getConnection();
			PreparedStatement ps = connection.prepareStatement("update students set group_id=?,first_name=?,last_name=? where student_id=?");
			ps.setString(1, student.getGroupId());
			ps.setString(2, student.getFirstName());
			ps.setString(3, student.getLastName());
			ps.setString(4, student.getStudentId());
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public Student getById(int id) {
		Student student = new Student();
		return student;
	}

	@Override
	public void delete(Student student) {
		try {
			Connection connection= getConnection();
			PreparedStatement ps = connection.prepareStatement("delete from students where id=?");
			ps.setString(1, student.getStudentId());
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	@Override
	public List<Student> getAll() {
		List<Student> students = new ArrayList<>();

		try {
			Connection connection = getConnection();
			String catalog = connection.getCatalog();
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM students");
			ResultSet rs;
			rs= ps.executeQuery();
			while (rs.next()) {
				Student student = new Student();
				student.setStudentId(rs.getString("student_id"));
				student.setGroupId(rs.getString("group_id"));
				student.setFirstName(rs.getString("first_name"));
				student.setLastName(rs.getString("last_name"));
				students.add(student);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return students;
	}
}
