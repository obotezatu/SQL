package com.foxminded.obotezatu;

import java.util.List;

public class Main {

	public static void main(String[] args) {
			Dao<Student> studentDao = new StudentDao();
			List<Student> students = studentDao.getAll();
			for(Student student: students){
				System.out.println(String.format("%8s | %10s | %-10s | %-10s", student.getStudentId(), student.getGroupId(), student.getFirstName(), student.getLastName()));
			}
			
	}
	
	public void print(Object object) {
		
	}

}
