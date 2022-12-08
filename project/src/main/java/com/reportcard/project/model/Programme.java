package com.reportcard.project.model;

import javax.persistence.*;

import javax.validation.constraints.Min;

@Entity
@Table(name="programmes")
public class Programme {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false, length = 100, unique = true)
	private String name;
	
	@Min(value = 0)
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
