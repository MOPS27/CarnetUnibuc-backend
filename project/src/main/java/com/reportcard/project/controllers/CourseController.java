package com.reportcard.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reportcard.project.dtos.CourseResponseDto;
import com.reportcard.project.services.CourseService;

@RestController
@RequestMapping("/courses")
public class CourseController {

	@Autowired
	private CourseService CourseService;
	
	@GetMapping(path = "/get")
	public List<CourseResponseDto> getAll() {
		return CourseService.getAll();
	}
	
}
