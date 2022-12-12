package com.reportcard.project.dtos;

public class SubjectsGradesResponseDto {

	private SubjectResponseDto subject;
	private Integer grade;
	
	public SubjectResponseDto getSubject() {
		return subject;
	}
	public void setSubject(SubjectResponseDto subject) {
		this.subject = subject;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
}
