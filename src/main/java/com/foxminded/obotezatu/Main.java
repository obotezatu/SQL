package com.foxminded.obotezatu;

import java.util.List;

public class Main {

	public static void main(String[] args) {
			StudentDao studentDao = new StudentDao();
			List<Student> students = studentDao.getAll();
			for(Student student: students){
				System.out.println(student);
			}

	}

}
