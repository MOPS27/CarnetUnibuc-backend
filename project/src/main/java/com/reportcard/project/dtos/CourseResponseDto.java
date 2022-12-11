package com.reportcard.project.dtos;

public class CourseResponseDto {
	private Integer id;
	private String professorName;
	private String subjectName;
	private String calendarYearName;
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
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getCalendarYearName() {
		return calendarYearName;
	}
	public void setCalendarYearName(String calendarYearName) {
		this.calendarYearName = calendarYearName;
	}
	
	
}
