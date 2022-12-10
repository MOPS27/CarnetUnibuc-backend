package com.reportcard.project.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class ProgrammeRequestDto {

	@NotBlank
	@Size(max = 100)
	private String name;

	@Positive
	private Integer numberOfYears;

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
