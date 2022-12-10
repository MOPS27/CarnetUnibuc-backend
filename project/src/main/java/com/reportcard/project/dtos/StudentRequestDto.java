package com.reportcard.project.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class StudentRequestDto {

	@NotBlank
	@Size(max = 100)
	private String lastName;
	
	@NotBlank
	@Size(max = 100)
	private String firstName;
	
	@NotBlank
	@Size(max = 100)
	private String email;

    private GroupRequestDto group;

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public GroupRequestDto getGroup() {
		return group;
	}

	public void setGroup(GroupRequestDto group) {
		this.group = group;
	}

	
}
