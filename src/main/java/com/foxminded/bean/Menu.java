package com.foxminded.bean;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.foxminded.dao.CourseDao;
import com.foxminded.dao.GroupDao;
import com.foxminded.dao.MenuDao;
import com.foxminded.dao.StudentCourseDao;
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
			groups.forEach(group -> System.out.println(group.getGroupId() + "->\"" + group.getGroupName() + "\"; "));
			System.out.print("\nSelect group Id: ");
			int id = scanner.nextInt();
			student.setGroupId(id);
			StudentDao studentDao = new StudentDao();
			studentDao.insert(student, connection);
			student = studentDao.getRecordByName(student.getFirstName(), student.getLastName(), connection);
			joinStudentCourse(student, connection);
		}
	}

	public void deleteStudent(Connection connection) {
		System.out.println("	Delete student by STUDENT_ID ");
		System.out.println("**************************************************");
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("\nInput sudent Id: ");
			int id = scanner.nextInt();
			MenuDao menuDao = new MenuDao();
			Student student = new StudentDao().getRecordById(id, connection);
			menuDao.deleteStudentById(id, connection);
			System.out.println("\nStudent <" + student.getFirstName() + " " + student.getLastName() + "> was deleted.");
		}
	}

	public void addStudentToCourse(Connection connection) {
		System.out.println("	Add a student to the course  ");
		System.out.println("**************************************************");
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("\nInput sudent Id: ");
			int id = scanner.nextInt();
			Student student = new StudentDao().getRecordById(id, connection);
			List<Relation> studentCourses = new StudentCourseDao().getCoursesByStudent(student, connection);
			System.out.print("<" + student.getFirstName() + " " + student.getLastName() + "> is already enrolled at: ");
			studentCourses.forEach(studentCourse -> System.out.print("\"" + studentCourse.getCourseName() + "\", "));
			joinStudentCourse(student, connection);
		}
	}

	public void removeStudentFromCourse(Connection connection) {
		System.out.println("	Remove the student from course  ");
		System.out.println("**************************************************");
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("\nInput sudent Id: ");
			int id = scanner.nextInt();
			Student student = new StudentDao().getRecordById(id, connection);
			StudentCourseDao studentCourseDao = new StudentCourseDao();
			List<Relation> studentCourses = studentCourseDao.getCoursesByStudent(student, connection);
			System.out.print("<" + student.getFirstName() + " " + student.getLastName() + "> is already enrolled at: ");
			studentCourses.forEach(studentCourse -> System.out
					.print(studentCourse.getCourseId() + "->" + "\"" + studentCourse.getCourseName() + "\", "));
			System.out.print("\nSelect course Id: ");
			id = scanner.nextInt();
			Course course = new CourseDao().getById(id, connection);
			studentCourseDao.delete(student.getStudentId(), id, connection);
			System.out.println("\nStudent <" + student.getFirstName() + " " + student.getLastName()
					+ "> was removed from " + course.getCourseName());
		}
	}

	private void joinStudentCourse(Student student, Connection connection) {
		CourseDao courseDao = new CourseDao();
		List<Course> courses = courseDao.getAll(connection);
		System.out.print("\n\nCourses: \n");
		courses.forEach(course -> System.out.println(course.getCourseId() + "->\"" + course.getCourseName() + "\"; "));
		System.out.print("\nSelect course Id: ");
		try (Scanner scanner = new Scanner(System.in)) {
			int id = scanner.nextInt();
			new StudentCourseDao().insert(student, courseDao.getById(id, connection), connection);
		}
		System.out.println("\nStudent <" + student.getFirstName() + " " + student.getLastName() + "> - added.");
	}
}
