package com.reportcard.project.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CourseRequestDto {

	@NotBlank
	@Size(max = 100)
	private String professorName;
	
	@NotBlank
	@Size(max = 100)
	private String calendarYearName;

	private int subjectId;

	public String getProfessorName() {
		return professorName;
	}

	public void setProfessorName(String professorName) {
		this.professorName = professorName;
	}

	public String getCalendarYearName() {
		return calendarYearName;
	}

	public void setCalendarYearName(String calendarYearName) {
		this.calendarYearName = calendarYearName;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	
	
}
