package com.foxminded.obotezatu;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.foxminded.dao.CourseDao;
import com.foxminded.dao.Dao;
import com.foxminded.dao.GroupDao;
import com.foxminded.dao.MenuDao;
import com.foxminded.dao.StudentCourse;
import com.foxminded.dao.StudentDao;

public class Menu {

	public void getGroupsWithLessCount(Connection connection) {
		System.out.println("Find all groups with less or equals student count ");
		System.out.println("**************************************************\n");
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("Input count: ");
			int count = scanner.nextInt();
			System.out.println("------------------------");
			MenuDao menuDao = new MenuDao();
			Map<String, Integer> studentsInGroup = menuDao.getGroupLessCount(count, connection);
			studentsInGroup.forEach((k, v) -> System.out.println(k + " - " + v + " students;"));
		}
	}

	public void getRelation(Connection connection) {
		System.out.println("Find all students related to course with given name ");
		System.out.println("**************************************************\n");
		try (Scanner scanner = new Scanner(System.in)) {
			List<Course> courses = new CourseDao().getAll(connection);
			System.out.println(String.format("%4s | %10s | ", "Id", "Name"));
			System.out.println("------------------");
			courses.stream().limit(courses.size() / 2).forEach(course -> System.out
					.print(String.format("%4d | %10s | ", course.getCourseId(), course.getCourseName())));
			System.out.println();
			courses.stream().skip(courses.size() / 2).forEach(course -> System.out
					.print(String.format("%4d | %10s | ", course.getCourseId(), course.getCourseName())));
			System.out.println("\nInput course name: ");
			String text = scanner.nextLine();
			MenuDao menuDao = new MenuDao();
			List<Relation> relations = menuDao.getRelationStudentsCourses(text, connection);
			System.out.println("\n****************< " + relations.get(0).getCourseName() + " >****************\n");
			System.out.println(
					String.format("|%4s | %-10s | %-10s | %-10s |\n********************************************* ",
							"ID", "FIRST NAME", "LAST NAME", "GROUP"));
			relations.stream()
					.forEach(relation -> System.out.println(String.format(
							"|%4d | %-10s | %-10s | %-10s |\n--------------------------------------------- ",
							relation.getStudentId(), relation.getStudentFirstName(), relation.getStudentLastName(),
							relation.getGroupName())));
		} catch (IndexOutOfBoundsException e) {
			System.out.println("\nSuch group not exist!\n");
		}
	}
	
	public void addNewStudent(Connection connection) {
		System.out.println("	Add new student ");
		System.out.println("**************************************************");
		try (Scanner scanner = new Scanner(System.in)) {
			Student student = new Student();
			System.out.print("\nFirst name: ");
			String text = scanner.nextLine();
			student.setFirstName(text);
			System.out.print("\nLast name: ");
			text = scanner.nextLine();
			student.setLastName(text);
			List<Group> groups = new GroupDao().getAll(connection);
			System.out.print("\nGroups: \n");
			groups.forEach(group->System.out.println(group.getGroupId()+ "->\"" + group.getGroupName() + "\"; "));
			System.out.print("\nSelect group Id: ");
			int id = scanner.nextInt();
			student.setGroupId(id);
			StudentDao studentDao = new StudentDao();
			studentDao.insert(student, connection);
			student = studentDao.getRecordByName(student.getFirstName(), student.getLastName(), connection);
			CourseDao courseDao = new CourseDao();
			List<Course> courses = courseDao.getAll(connection);
			System.out.print("\nCourses: \n");
			courses.forEach(course->System.out.println(course.getCourseId() + "->\""+ course.getCourseName() + "\"; "));
			System.out.print("\nSelect course Id: ");
			id = scanner.nextInt();
			new StudentCourse().insert(student, courseDao.getRecordById(id, connection), connection);
			System.out.println("\nStudent <" + student.getFirstName() + " " + student.getLastName() + "> - added.");
		}
	}
}
