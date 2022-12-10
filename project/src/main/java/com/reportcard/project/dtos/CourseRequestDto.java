package com.reportcard.project.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class CourseRequestDto {

	@NotBlank
	@Size(max = 100)
	private String name;
	
	@NotBlank
	@Positive
	private Integer creditCount;

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
