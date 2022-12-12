package com.reportcard.project.dtos;

import java.util.List;

public class CourseResponseDto {
	private Integer id;
	private String professorName;
	private SubjectResponseDto subject;
	private String calendarYearName;
	private List<StudentResponseDto> students;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProfessorName() {
		return professorName;
	}
	public void setProfessorName(String professorName) {
		this.professorName = professorName;
	}
	public SubjectResponseDto getSubject() {
		return subject;
	}
	public void setSubject(SubjectResponseDto subject) {
		this.subject = subject;
	}
	public String getCalendarYearName() {
		return calendarYearName;
	}
	public void setCalendarYearName(String calendarYearName) {
		this.calendarYearName = calendarYearName;
	}
	public List<StudentResponseDto> getStudents() {
		return students;
	}
	public void setStudents(List<StudentResponseDto> students) {
		this.students = students;
	}
	
}
