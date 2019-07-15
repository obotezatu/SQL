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
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		DataBase dataBase = new DataBase();
		Connection connection = dataBase.getConnection("postgres", "postgres", "1");
		//dataBase.executeSQL(connection, "createUser.sql");
		//dataBase.executeSQL(connection, "createDB.sql");
		
		//connection = dataBase.getConnection("foxy", "local", "1");
		//dataBase.executeSQL(connection, "schema.sql");
		dataBase.setGROUPS();
		String[] groups = dataBase.getGROUPS();
		Arrays.stream(groups).forEach(group->System.out.print(group + ", "));
		System.out.println();
		String[] courses = dataBase.getCOURSES();
		Arrays.stream(courses).forEach(course->System.out.print(course + ", "));

		/*String text = null;
		StudentDao studentDao = new StudentDao(getConnection());
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Select: \n " 
					+ "1 - find if any of the groups has less than 10 students.\n "
					+ "2 - find name of course and related students.\n "
					+ "3 - delete all students from group with name \"SR-01\".\n " 
					+ "4 - display all students");

			text = scanner.nextLine();
			switch (text) {
			case "1":
				print(studentDao.getGroupLessTen());
				break;
			case "2":
				System.out.println("Course name?");
				text = scanner.nextLine();
				print(studentDao.getRelationStudentsCourses(text));
				break;
			case "3":
				studentDao.deleteStudentsFromGroup("SR-01");
				print(studentDao.getAll());
				break;
			case "4":
				print(studentDao.getAll());
				break;
			default:
				break;
			}
		}*/
		dataBase.executeSQL(connection, "dropDataBase.sql");
	}

	public static void print(List<Student> students) {
		students.stream().forEach(
				student -> System.out.println(String.format("%8s | %10s | %-10s | %-10s | %5s", student.getStudentId(),
						student.getGroupId(), student.getFirstName(), student.getLastName(), student.getCourse())));
	}

	public static void print(Map<String, Integer> studentsInGroup) {
		studentsInGroup.forEach((k, v) -> System.out.println(k + " - " + v));
	}
}
