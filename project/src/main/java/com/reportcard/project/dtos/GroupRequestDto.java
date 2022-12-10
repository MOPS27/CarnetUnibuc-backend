package com.reportcard.project.dtos;

import javax.validation.constraints.Positive;

public class GroupRequestDto {

	@Positive
	private Integer groupCode;

	public Integer getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(Integer groupCode) {
		this.groupCode = groupCode;
	}
	
	
}
