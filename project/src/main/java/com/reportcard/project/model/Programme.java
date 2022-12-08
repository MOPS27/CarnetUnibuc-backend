package com.reportcard.project.model;

import javax.persistence.*;

import javax.validation.constraints.Min;

@Entity
@Table(name="programmes")
public class Programme {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;
	
	@Column(nullable = false, length = 100, unique = true)
	public String name;
	
	@Min(value = 0)
	public Integer numberOfYears;
}
