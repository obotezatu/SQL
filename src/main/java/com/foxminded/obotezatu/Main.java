package com.foxminded.obotezatu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class Main {

	public static void main(String[] args) {
			Dao<Student> studentDao = new StudentDao(getConnection());
			List<Student> students = studentDao.getAll();
			for(Student student: students){
				System.out.println(String.format("%8s | %10s | %-10s | %-10s", student.getStudentId(), student.getGroupId(), student.getFirstName(), student.getLastName()));
			}
			System.out.println("\n\n----------------------------------------------\n\n");
			StudentDaoAdditional studentDaoAdditional = new StudentDaoAdditional(getConnection());
			List<Student> studentsSdd = studentDaoAdditional.getRelationStudentsCourses("P-1");
			for(Student student: studentsSdd){
				System.out.println(String.format("%8s | %10s | %-10s | %-10s", student.getStudentId(), student.getGroupId(), student.getFirstName(), student.getLastName()));
			}
	}
	
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
}
