package com.foxminded.bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

import com.foxminded.dao.DataBase;

public class Main {

	public static void main(String[] args) {
		DataBase dataBase = new DataBase();
		Connection connection = getConnection("postgres", "postgres", "1");
		dataBase.createDataBase(connection);
		connection = getConnection("foxy", "local", "1");
		dataBase.createDataBaseTables(connection);
		try {
			Scanner scanner = new Scanner(System.in);
			String option = "";
			while (!option.equals("0")) {
				System.out.println("\n\nSelect: \n" + "1 - Find all groups with less or equals student count.\n"
						+ "2 - Find all students related to course with given name.\n" + "3 - Add new student.\n"
						+ "4 - Delete student by STUDENT_ID.\n" + "5 - Add a student to the course (from a list).\n"
						+ "6 - Remove the student from one of his or her courses.\n" + "0 - for exit.\n");
				// boolean hasNextInt = scanner.hasNextInt();

				option = scanner.next();

				System.out.println("option: " + option);
				Menu menu = new Menu();

				switch (option) {
				case "1":
					menu.getGroupsWithLessCount(connection);
					break;
				case "2":
					menu.getRelation(connection);
					break;
				case "3":
					menu.addNewStudent(connection);
					break;
				case "4":
					menu.deleteStudent(connection);
					break;
				case "5":
					menu.addStudentToCourse(connection);
					break;
				case "6":
					menu.removeStudentFromCourse(connection);
					break;
				case "0":
					System.out.println("Exit.");
					break;
				default:
					System.out.println("Incorrect option.");
					break;
				}

				// scanner.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		connection = getConnection("postgres", "postgres", "1");
		dataBase.deleteDataBase(connection);
	}

	/*
	 * private static Connection getConnection(String dataBase, String user, String
	 * password) { Connection connection = null; try {
	 * Class.forName("org.postgresql.Driver"); connection =
	 * DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/" + dataBase,
	 * user, password); } catch (Exception e) { System.out.println(e); } return
	 * connection; }
	 */
}
