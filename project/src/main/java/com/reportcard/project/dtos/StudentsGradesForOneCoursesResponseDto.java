package com.reportcard.project.dtos;

public class StudentsGradesForOneCoursesResponseDto {

	private Integer grade;
	private String studentLastName;
	private String sutdentFirstName;
	private String studentEmail;
	private Integer groupNumber;
	
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public String getStudentLastName() {
		return studentLastName;
	}
	public void setStudentLastName(String studentLastName) {
		this.studentLastName = studentLastName;
	}
	public String getSutdentFirstName() {
		return sutdentFirstName;
	}
	public void setSutdentFirstName(String sutdentFirstName) {
		this.sutdentFirstName = sutdentFirstName;
	}
	public String getStudentEmail() {
		return studentEmail;
	}
	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}
	public Integer getGroupNumber() {
		return groupNumber;
	}
	public void setGroupNumber(Integer groupNumber) {
		this.groupNumber = groupNumber;
	}
	
}
