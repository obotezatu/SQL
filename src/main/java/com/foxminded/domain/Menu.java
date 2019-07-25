package com.foxminded.domain;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import com.foxminded.dao.CourseDao;
import com.foxminded.dao.DaoException;
import com.foxminded.dao.GroupDao;
import com.foxminded.dao.MenuDao;
import com.foxminded.dao.StudentCourseDao;
import com.foxminded.dao.StudentDao;

public class Menu {

	public void getGroupsWithLessCount(int count) {
		
			MenuDao menuDao = new MenuDao();
			try {
			Map<String, Integer> studentsInGroup = menuDao.getGroupLessCount(count);
			studentsInGroup.forEach((k, v) -> System.out.println(k + " - " + v + " students;"));
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	public void getRelation(Scanner scanner) {
		try {
			List<Course> courses = new CourseDao().getAll();
			System.out.println(String.format("%4s | %-10s | ", "Id", "Name"));
			System.out.println("------------------");
			courses.stream().forEach(course -> System.out
					.print(String.format("%4d | %10s | \n", course.getCourseId(), course.getCourseName())));
			System.out.println("\nInput course name: ");
			String courseName = scanner.next();
			MenuDao menuDao = new MenuDao();
			List<Student> students = menuDao.getRelationStudentsCourses(courseName);
			//System.out.println("\n****************< " + courses.get + " >****************\n");
			System.out.println(
					String.format("|%4s | %-10s | %-10s |\n*********************************** ",
							"N", "FIRST NAME", "LAST NAME"));
			AtomicInteger count = new AtomicInteger(1);
			students.stream()
					.forEach(student -> System.out.println(String.format(
							"|%4d | %-10s | %-10s |\n----------------------------------- ",
							count.getAndIncrement(), student.getFirstName(), student.getLastName())));
		} catch (IndexOutOfBoundsException e) {
			System.out.println("\nSuch group not exist!\n");
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	public void addNewStudent(Scanner scanner) {
		try {
			Student student = new Student();
			System.out.print("\nFirst name: ");
			String firstName = scanner.next();
			student.setFirstName(firstName);
			System.out.print("\nLast name: ");
			String lastName = scanner.next();
			student.setLastName(lastName);
			List<Group> groups = new GroupDao().getAll();
			System.out.print("\nGroups: \n");
			groups.forEach(group -> System.out.println(group.getGroupId() + "->\"" + group.getGroupName() + "\"; "));
			System.out.print("\nSelect group Id: ");
			int id = scanner.nextInt();
			student.setGroupId(id);
			StudentDao studentDao = new StudentDao();
			studentDao.insert(student);
			student = studentDao.getByName(student.getFirstName(), student.getLastName());
			joinStudentCourse(student, scanner);
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	public void deleteStudent(int id) {
		try {
			MenuDao menuDao = new MenuDao();
			Student student = new StudentDao().getById(id);
			menuDao.deleteStudentById(id);
			System.out.println("\nStudent <" + student.getFirstName() + " " + student.getLastName() + "> was deleted.");
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	public void addStudentToCourse(Scanner scanner) {
		try{
			System.out.print("\nInput sudent Id: ");
			int id = scanner.nextInt();
			Student student = new StudentDao().getById(id);
			List<Relation> studentCourses = new StudentCourseDao().getCoursesByStudent(student);
			System.out.print("<" + student.getFirstName() + " " + student.getLastName() + "> is already enrolled at: ");
			studentCourses.forEach(studentCourse -> System.out.print("\"" + studentCourse.getCourseName() + "\", "));
			joinStudentCourse(student, scanner);
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	public void removeStudentFromCourse(int studentId, int courseId) {
		try {
			Student student = new StudentDao().getById(studentId);
			StudentCourseDao studentCourseDao = new StudentCourseDao();
			List<Relation> studentCourses = studentCourseDao.getCoursesByStudent(student);
			System.out.print("<" + student.getFirstName() + " " + student.getLastName() + "> is already enrolled at: ");
			studentCourses.forEach(studentCourse -> System.out
					.print(studentCourse.getCourseId() + "->" + "\"" + studentCourse.getCourseName() + "\", "));
			Course course = new CourseDao().getById(courseId);
			studentCourseDao.delete(student.getStudentId(), courseId);
			System.out.println("\nStudent <" + student.getFirstName() + " " + student.getLastName()
					+ "> was removed from " + course.getCourseName());
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	private void joinStudentCourse(Student student, Scanner scanner) {
		try {
			CourseDao courseDao = new CourseDao();
			List<Course> courses = courseDao.getAll();
			System.out.print("\n\nCourses: \n");
			courses.forEach(
					course -> System.out.println(course.getCourseId() + "->\"" + course.getCourseName() + "\"; "));
			System.out.print("\nSelect course Id: ");
			int id = scanner.nextInt();
			new StudentCourseDao().insert(student, courseDao.getById(id));
		} catch (DaoException e) {
			e.printStackTrace();
		}
		System.out.println("\nStudent <" + student.getFirstName() + " " + student.getLastName() + "> - added.");
	}
}
