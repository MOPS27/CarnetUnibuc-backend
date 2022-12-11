package com.reportcard.project.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;

// MATERII
@Entity
@Table(name="subjects")
public class Subject {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false, length = 100, unique = true)
	private String name;
	
	@Min(value = 0)
	private Integer creditCount;

	@OneToMany(mappedBy = "subject")
	private List<Course> courses;
	
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

	public Integer getCreditCount() {
		return creditCount;
	}

	public void setCreditCount(Integer creditCount) {
		this.creditCount = creditCount;
	}
	
	public List<Course> getCourses() {
		return this.courses;
	}
	
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	
}
