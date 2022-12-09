package com.reportcard.project.dtos;

public class ProgrammeResponseDto {
	private Integer id;
	private String name;
	private Integer numberOfYears;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNumberOfYears() {
		return numberOfYears;
	}
	public void setNumberOfYears(Integer numberOfYears) {
		this.numberOfYears = numberOfYears;
	}
	
}
