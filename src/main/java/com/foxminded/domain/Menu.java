package com.foxminded.domain;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import com.foxminded.dao.CourseDao;
import com.foxminded.dao.DaoException;
import com.foxminded.dao.DataSource;
import com.foxminded.dao.GroupDao;
import com.foxminded.dao.StudentCourseDao;
import com.foxminded.dao.StudentDao;

public class Menu {

	private CourseDao courseDao;
	private GroupDao groupDao;
	private StudentDao studentDao;
	private StudentCourseDao studentCourseDao;

	public Menu(DataSource dataSource) {
		courseDao = new CourseDao(dataSource);
		studentDao = new StudentDao(dataSource);
		groupDao = new GroupDao(dataSource);
		studentCourseDao = new StudentCourseDao(dataSource);
	}

	public void getGroupsWithLessCount(Scanner scanner) {
		System.out.println("Find all groups with less or equals student count ");
		System.out.println("**************************************************\n");
		System.out.print("Input count: ");
		int count = scanner.nextInt();
		System.out.println("------------------------");
		try {
			Map<String, Integer> studentsInGroup = groupDao.getGroupLessCount(count);
			studentsInGroup.forEach((k, v) -> System.out.println(k + " - " + v + " students;"));
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	public void getRelation(Scanner scanner) {
		System.out.println("Find all students related to course with given name ");
		System.out.println("**************************************************\n");
		try {
			List<Course> courses = courseDao.getAll();
			System.out.println(String.format("%4s | %-10s | ", "Id", "Name"));
			System.out.println("------------------");
			courses.stream().forEach(course -> System.out
					.println(String.format("%4d | %-10s | ", course.getCourseId(), course.getCourseName())));
			System.out.println("Input course name: ");
			String courseName = scanner.next();
			List<Student> students = studentDao.getStudentsByCourseName(courseName);
			System.out.println(String.format("|%4s | %-10s | %-10s |\n********************************** ", "N ",
					"FIRST NAME", "LAST NAME"));
			AtomicInteger count = new AtomicInteger(1);
			students.forEach(student -> System.out
					.println(String.format("|%4d. | %-10s | %-10s |\n--------------------------------- ",
							count.getAndIncrement(), student.getFirstName(), student.getLastName())));
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	public void addNewStudent(Scanner scanner) {
		System.out.println("	Add new student ");
		System.out.println("**************************************************");
		try {
			Student student = new Student();
			System.out.println("First name: ");
			String firstName = scanner.next();
			student.setFirstName(firstName);
			System.out.println("Last name: ");
			String lastName = scanner.next();
			student.setLastName(lastName);
			List<Group> groups = groupDao.getAll();
			System.out.println("Groups:");
			groups.forEach(group -> System.out.println(group.getGroupId() + "->\"" + group.getGroupName() + "\"; "));
			System.out.println("Select group Id: ");
			int id = scanner.nextInt();
			student.setGroupId(id);
			studentDao.insert(student);
			joinStudentCourse(student, scanner);
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	public void deleteStudent(Scanner scanner) {
		System.out.println("	Delete student by STUDENT_ID ");
		System.out.println("**************************************************");
		try {
			System.out.println("Input student Id: ");
			int id = scanner.nextInt();
			Student student = studentDao.getById(id);
			studentDao.deleteStudentById(id);
			System.out.println("Student <" + student.getFirstName() + " " + student.getLastName() + "> was deleted.");
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	public void addStudentToCourse(Scanner scanner) {
		System.out.println("	Add a student to the course  ");
		System.out.println("**************************************************");
		try {
			System.out.println("Input sudent Id: ");
			int id = scanner.nextInt();
			Student student = studentDao.getById(id);
			List<Course> studentCourses = studentCourseDao.getCoursesByStudent(student);
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
			System.out.println("Input sudent Id: ");
			int studentId = scanner.nextInt();
			Student student = studentDao.getById(studentId);
			List<Course> studentCourses = studentCourseDao.getCoursesByStudent(student);
			System.out
					.println("<" + student.getFirstName() + " " + student.getLastName() + "> is already enrolled at: ");
			studentCourses.forEach(studentCourse -> System.out
					.println(studentCourse.getCourseId() + "->" + "\"" + studentCourse.getCourseName() + "\", "));
			System.out.println("Select course Id: ");
			int courseId = scanner.nextInt();
			Course course = courseDao.getById(courseId);
			studentCourseDao.delete(student.getStudentId(), courseId);
			System.out.println("Student <" + student.getFirstName() + " " + student.getLastName()
					+ "> was removed from " + course.getCourseName());
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	private void joinStudentCourse(Student student, Scanner scanner) {
		try {
			List<Course> courses = courseDao.getAll();
			System.out.println("Courses: ");
			courses.forEach(
					course -> System.out.println(course.getCourseId() + "->\"" + course.getCourseName() + "\"; "));
			System.out.println("Select course Id: ");
			int id = scanner.nextInt();
			studentCourseDao.insert(student, courseDao.getById(id));
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
}
