package com.foxminded.domain;

import java.util.Scanner;

import com.foxminded.dao.DataBase;
import com.foxminded.dao.DataSource;

public class Main {

	public static void main(String[] args) {
		DataBase dataBasePostgres = new DataBase(
				new DataSource("jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "1"));
		System.out.println("Creating database \"foxy\"...");
		dataBasePostgres.createDataBase();
		System.out.println("Done");
		System.out.println("Creating tables and generate data...");
		DataSource dataSource = new DataSource("jdbc:postgresql://127.0.0.1:5432/foxy", "local", "1");
		DataBase dataBaseFoxy = new DataBase(dataSource);
		dataBaseFoxy.createDataBaseTables();
		System.out.println("Done.");
		try (Scanner scanner = new Scanner(System.in)) {
			int option = -1;
			while (option != 0) {
				printMenuOptions();
				option = scanner.nextInt();
				Menu menu = new Menu(dataSource);
				switch (option) {
				case 1:
					menu.getGroupsWithLessCount(scanner);
					break;
				case 2:
					menu.getRelation(scanner);
					break;
				case 3:
					menu.addNewStudent(scanner);
					break;
				case 4:
					menu.deleteStudent(scanner);
					break;
				case 5:
					menu.addStudentToCourse(scanner);
					break;
				case 6:
					menu.removeStudentFromCourse(scanner);
					break;
				case 0:
					System.out.println("Exit.");
					break;
				default:
					System.out.println("Incorrect option.");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("Deleting database \"foxy\"...");
			dataBasePostgres.deleteDataBase();
			System.out.println("Done");
		}
	}

	private static void printMenuOptions() {
		System.out.println("\n\nSelect: \n" 
				+ "1 - Find all groups with less or equals student count.\n"
				+ "2 - Find all students related to course with given name.\n" 
				+ "3 - Add new student.\n"
				+ "4 - Delete student by STUDENT_ID.\n" 
				+ "5 - Add a student to the course (from a list).\n"
				+ "6 - Remove the student from one of his or her courses.\n" 
				+ "0 - for exit.\n");
		System.out.print("Option?: ");
	}
}
