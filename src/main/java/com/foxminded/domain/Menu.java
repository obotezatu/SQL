package com.foxminded.domain;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import com.foxminded.dao.CourseDao;
import com.foxminded.dao.DaoException;
import com.foxminded.dao.DataSource;
import com.foxminded.dao.GroupDao;
import com.foxminded.dao.MenuDao;
import com.foxminded.dao.StudentCourseDao;
import com.foxminded.dao.StudentDao;

public class Menu {

	private DataSource dataSource;

	public Menu(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void getGroupsWithLessCount(Scanner scanner) {
		System.out.println("Find all groups with less or equals student count ");
		System.out.println("**************************************************\n");
		System.out.print("Input count: ");
		int count = scanner.nextInt();
		System.out.println("------------------------");
		MenuDao menuDao = new MenuDao(dataSource);
		try {
			Map<String, Integer> studentsInGroup = menuDao.getGroupLessCount(count);
			studentsInGroup.forEach((k, v) -> System.out.println(k + " - " + v + " students;"));
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	public void getRelation(Scanner scanner) {
		System.out.println("Find all students related to course with given name ");
		System.out.println("**************************************************\n");
		try {
			List<Course> courses = new CourseDao(dataSource).getAll();
			System.out.println(String.format("%4s | %-10s | ", "Id", "Name"));
			System.out.println("------------------");
			courses.stream().forEach(course -> System.out
					.print(String.format("%4d | %-10s | \n", course.getCourseId(), course.getCourseName())));
			System.out.println("\nInput course name: ");
			String courseName = scanner.next();
			MenuDao menuDao = new MenuDao(dataSource);
			List<Student> students = menuDao.getRelationStudentsCourses(courseName);
			System.out.println(String.format("|%4s | %-10s | %-10s |\n********************************** ", "N ",
					"FIRST NAME", "LAST NAME"));
			AtomicInteger count = new AtomicInteger(1);
			students.stream()
					.forEach(student -> System.out
							.println(String.format("|%4d. | %-10s | %-10s |\n--------------------------------- ",
									count.getAndIncrement(), student.getFirstName(), student.getLastName())));
		} catch (IndexOutOfBoundsException e) {
			System.out.println("\nSuch course not exist!\n");
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	public void addNewStudent(Scanner scanner) {
		System.out.println("	Add new student ");
		System.out.println("**************************************************");
		try {
			Student student = new Student();
			System.out.print("\nFirst name: ");
			String firstName = scanner.next();
			student.setFirstName(firstName);
			System.out.print("\nLast name: ");
			String lastName = scanner.next();
			student.setLastName(lastName);
			List<Group> groups = new GroupDao(dataSource).getAll();
			System.out.print("\nGroups: \n");
			groups.forEach(group -> System.out.println(group.getGroupId() + "->\"" + group.getGroupName() + "\"; "));
			System.out.print("\nSelect group Id: ");
			int id = scanner.nextInt();
			student.setGroupId(id);
			StudentDao studentDao = new StudentDao(dataSource);
			studentDao.insert(student);
			student = studentDao.getByName(student.getFirstName(), student.getLastName());
			joinStudentCourse(student, scanner);
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	public void deleteStudent(Scanner scanner) {
		System.out.println("	Delete student by STUDENT_ID ");
		System.out.println("**************************************************");
		try {
			System.out.print("\nInput sudent Id: ");
			int id = scanner.nextInt();
			MenuDao menuDao = new MenuDao(dataSource);
			Student student = new StudentDao(dataSource).getById(id);
			menuDao.deleteStudentById(id);
			System.out.println("\nStudent <" + student.getFirstName() + " " + student.getLastName() + "> was deleted.");
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	public void addStudentToCourse(Scanner scanner) {
		System.out.println("	Add a student to the course  ");
		System.out.println("**************************************************");
		try {
			System.out.print("\nInput sudent Id: ");
			int id = scanner.nextInt();
			Student student = new StudentDao(dataSource).getById(id);
			List<Course> studentCourses = new StudentCourseDao(dataSource).getCoursesByStudent(student);
			System.out.print("<" + student.getFirstName() + " " + student.getLastName() + "> is already enrolled at: ");
			studentCourses.forEach(studentCourse -> System.out.print("\"" + studentCourse.getCourseName() + "\", "));
			joinStudentCourse(student, scanner);
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	public void removeStudentFromCourse(Scanner scanner) {
		System.out.println("	Remove the student from course  ");
		System.out.println("**************************************************");
		try {
			System.out.print("\nInput sudent Id: ");
			int studentId = scanner.nextInt();
			Student student = new StudentDao(dataSource).getById(studentId);
			StudentCourseDao studentCourseDao = new StudentCourseDao(dataSource);
			List<Course> studentCourses = studentCourseDao.getCoursesByStudent(student);
			System.out.print("<" + student.getFirstName() + " " + student.getLastName() + "> is already enrolled at: ");
			studentCourses.forEach(studentCourse -> System.out
					.print(studentCourse.getCourseId() + "->" + "\"" + studentCourse.getCourseName() + "\", "));
			System.out.print("\nSelect course Id: ");
			int courseId = scanner.nextInt();
			Course course = new CourseDao(dataSource).getById(courseId);
			studentCourseDao.delete(student.getStudentId(), courseId);
			System.out.println("\nStudent <" + student.getFirstName() + " " + student.getLastName()
					+ "> was removed from " + course.getCourseName());
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	private void joinStudentCourse(Student student, Scanner scanner) {
		try {
			CourseDao courseDao = new CourseDao(dataSource);
			List<Course> courses = courseDao.getAll();
			System.out.print("\n\nCourses: \n");
			courses.forEach(
					course -> System.out.println(course.getCourseId() + "->\"" + course.getCourseName() + "\"; "));
			System.out.print("\nSelect course Id: ");
			int id = scanner.nextInt();
			new StudentCourseDao(dataSource).insert(student, courseDao.getById(id));
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
}
