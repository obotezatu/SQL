package com.foxminded.obotezatu;

import static java.lang.System.lineSeparator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

import org.apache.commons.lang3.RandomStringUtils;

import com.foxminded.dao.DataBase;

public class Main {

	public static void main(String[] args) {
		/*
		 * DataBase dataBase = new DataBase(); Connection connection =
		 * getConnection("postgres", "postgres", "1");
		 * dataBase.createDataBase(connection);
		 */
		Connection connection = getConnection("foxy", "local", "1");
		//dataBase.createDataBaseTables(connection);

		String text = null;
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Select: \n "
					+ "1 - Find all groups with less or equals student count.\n "
					+ "2 - Find all students related to course with given name.\n "
					+ "3 - Add new student.\n " 
					+ "4 - Delete student by STUDENT_ID.\n"
					+ "5 - Add a student to the course (from a list).\n"
					+ "6 - Remove the student from one of his or her courses.\n");

			text = scanner.nextLine();
			Menu menu = new Menu();
			switch (text) {
			case "1":
				menu.getGroupsWithLessCount(connection);
				break;
			case "2":
				menu.getRelation(connection);
				break;
			/*
			 * case "3": studentDao.deleteStudentsFromGroup("SR-01");
			 * print(studentDao.getAll()); break; case "4": print(studentDao.getAll());
			 * break; default: break;
			 */
			}
		}

		connection = getConnection("postgres", "postgres", "1");
		// dataBase.deleteDataBase(connection);
	}

	private static Connection getConnection(String dataBase, String user, String password) {
		Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/" + dataBase, user, password);
		} catch (Exception e) {
			System.out.println(e);
		}
		return connection;
	}

	public static void print(List<Student> students) {
		students.stream().forEach(student -> System.out.println(String.format("%4d | %4d | %-10s | %-10s",
				student.getStudentId(), student.getGroupId(), student.getFirstName(), student.getLastName())));
	}

	public static void print(Map<String, Integer> studentsInGroup) {
		studentsInGroup.forEach((k, v) -> System.out.println(k + " - " + v));
	}
}
