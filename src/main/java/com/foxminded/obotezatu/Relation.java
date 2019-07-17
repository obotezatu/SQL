package com.foxminded.obotezatu;

public class Relation {
	
	private int studentId;
	private String studentLastName;
	private String studentFirstName;
	private String groupName;
	private String courseName;
	
	public int getStudentId() {
		return studentId;
	}
	public String getStudentLastName() {
		return studentLastName;
	}
	public String getStudentFirstName() {
		return studentFirstName;
	}
	public String getGroupName() {
		return groupName;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public void setStudentLastName(String studentLastName) {
		this.studentLastName = studentLastName;
	}
	public void setStudentFirstName(String studentFirstName) {
		this.studentFirstName = studentFirstName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}	
}
