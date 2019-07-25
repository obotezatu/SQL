package com.foxminded.domain;

public class Relation {

	private int studentId;
	private String studentLastName;
	private String studentFirstName;
	private int groupId;
	private String groupName;
	private int courseId;
	private String courseName;

	public int getStudentId() {
		return studentId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
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
