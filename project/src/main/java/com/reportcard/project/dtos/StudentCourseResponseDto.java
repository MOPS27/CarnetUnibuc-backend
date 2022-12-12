package com.reportcard.project.dtos;

public class StudentCourseResponseDto {

	private Integer id;
	private StudentResponseDto student;
	private CourseResponseDto course;
	private Integer grade;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public StudentResponseDto getStudent() {
		return student;
	}
	public void setStudent(StudentResponseDto student) {
		this.student = student;
	}
	public CourseResponseDto getCourse() {
		return course;
	}
	public void setCourse(CourseResponseDto course) {
		this.course = course;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
}
