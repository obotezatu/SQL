package com.foxminded.domain;

import java.util.Scanner;

import com.foxminded.dao.DataBase;

public class Main {

	public static void main(String[] args) {
		DataBase dataBase = new DataBase();
		System.out.println("Creating database ...");
		dataBase.createDataBase();
		System.out.println("Done");
		System.out.println("Creating tables and generate data...");
		dataBase.createDataBaseTables();
		System.out.println("Done.");
		try (Scanner scanner = new Scanner(System.in)) {
			int option = -1;
			while (option != 0) {
				System.out.println("\n\nSelect: \n" + "1 - Find all groups with less or equals student count.\n"
						+ "2 - Find all students related to course with given name.\n" + "3 - Add new student.\n"
						+ "4 - Delete student by STUDENT_ID.\n" + "5 - Add a student to the course (from a list).\n"
						+ "6 - Remove the student from one of his or her courses.\n" + "0 - for exit.\n");
				System.out.print("Option?: ");
				option = scanner.nextInt();
				Menu menu = new Menu();
				switch (option) {
				case 1:
					System.out.println("Find all groups with less or equals student count ");
					System.out.println("**************************************************\n");
					System.out.print("Input count: ");
					int count = scanner.nextInt();
					System.out.println("------------------------");
					menu.getGroupsWithLessCount(count);
					break;
				case 2:
					System.out.println("Find all students related to course with given name ");
					System.out.println("**************************************************\n");
					menu.getRelation(scanner);
					break;
				case 3:
					System.out.println("	Add new student ");
					System.out.println("**************************************************");
					menu.addNewStudent(scanner);
					break;
				case 4:
					System.out.println("	Delete student by STUDENT_ID ");
					System.out.println("**************************************************");
					System.out.print("\nInput sudent Id: ");
					int id = scanner.nextInt();
					menu.deleteStudent(id);
					break;
				case 5:
					System.out.println("	Add a student to the course  ");
					System.out.println("**************************************************");
					menu.addStudentToCourse(scanner);
					break;
				case 6:
					System.out.println("	Remove the student from course  ");
					System.out.println("**************************************************");
					System.out.print("\nInput sudent Id: ");
					int studentId = scanner.nextInt();
					System.out.print("\nSelect course Id: ");
					int courseId = scanner.nextInt();
					menu.removeStudentFromCourse(studentId, courseId);
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
			dataBase.deleteDataBase();
		}
	}
}
