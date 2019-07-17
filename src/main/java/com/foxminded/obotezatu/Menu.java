package com.foxminded.obotezatu;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.foxminded.dao.CourseDao;
import com.foxminded.dao.Dao;
import com.foxminded.dao.GroupDao;
import com.foxminded.dao.MenuDao;

public class Menu {

	public  void getGroupsWithLessCount(Connection connection) {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Find all groups with less or equals student count ");
			System.out.println("**************************************************\n");
			System.out.print("Input count: ");
			int count = scanner.nextInt();
			System.out.println("------------------------");
			MenuDao menuDao = new MenuDao();
			Map<String, Integer> studentsInGroup =  menuDao.getGroupLessCount(count, connection);
			studentsInGroup.forEach((k, v) -> System.out.println(k + " - " + v));
		}
	}

	public void getRelation(Connection connection) {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Find all students related to course with given name ");
			System.out.println("**************************************************\n");
			List<Course> courses = new CourseDao().getAll(connection);
			System.out.println(String.format("%4s | %10s | ", "Id", "Name"));
			System.out.println("------------------");
			courses.stream().limit(courses.size()/2).forEach(course->System.out.print(String.format("%4d | %10s | ", course.getCourseId(), course.getCourseName())));
			System.out.println();
			courses.stream().skip(courses.size()/2).forEach(course->System.out.print(String.format("%4d | %10s | ", course.getCourseId(), course.getCourseName())));
			System.out.println("\nInput course name: ");
			String text = scanner.nextLine();
			System.out.println("\n------------------------");
			MenuDao menuDao = new MenuDao();
			List<Student> students =  menuDao.getRelationStudentsCourses(text, connection);
			List<Course> courses = new GroupDao();
			students.stream().forEach(student -> System.out.println(String.format("%4d | %-10s | %-10s",
					student.getStudentId(), student.getGroupId(), student.getFirstName(), student.getLastName(),student.getGroupId())));
		}
	}

}
