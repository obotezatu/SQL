package com.foxminded.obotezatu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoAdditional {

	private Connection connection;

	public StudentDaoAdditional(Connection connection) {
		this.connection = connection;
	}

	public List<Student> getRelationStudentsCourses(String course_id) {
		List<Student> students = new ArrayList<>();
		try (// Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(
						"SELECT students.student_id, students.group_id, students.first_name, students.last_name, courses.name "
								+ "FROM students " + "inner join courses_students as cs "
								+ "ON students.student_id = cs.student_id " + "inner join courses	"
								+ "ON courses.course_id = cs.course_id	where cs.course_id like ?")) {
			ps.setString(1, course_id);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Student student = new Student();
					student.setStudentId(rs.getString("student_id"));
					student.setGroupId(rs.getString("group_id"));
					student.setFirstName(rs.getString("first_name"));
					student.setLastName(rs.getString("last_name"));
					students.add(student);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return students;
	}
}
