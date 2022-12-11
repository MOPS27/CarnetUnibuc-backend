package com.reportcard.project.dtos;

import java.util.ArrayList;
import java.util.List;

import com.reportcard.project.dtos.StudentResponseDto;

public class CourseResponseDto {
	private Integer id;
	private String professorName;
	private String subjectName;
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
	public List<StudentResponseDto> getStudents() {
		return students;
	}
	public void setStudents(List<StudentResponseDto> students) {
		this.students = students;
	}
	
}
