package com.reportcard.project.dtos;

public class StudentResponseDto {

	private Integer id;
	private String lastName;
	private String firstName;
	private String email;
    private GroupResponseDto group;

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

	public GroupResponseDto getGroup() {
		return group;
	}

	public void setGroup(GroupResponseDto group) {
		this.group = group;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
