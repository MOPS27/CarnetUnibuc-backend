package com.reportcard.project.dtos;

public class CourseResponseDto {

	private int id;
	private String name;
	private Integer creditCount;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCreditCount() {
		return creditCount;
	}
	public void setCreditCount(Integer creditCount) {
		this.creditCount = creditCount;
	}
	
	
}
