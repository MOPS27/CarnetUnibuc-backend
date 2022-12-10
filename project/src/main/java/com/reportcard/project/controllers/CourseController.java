package com.reportcard.project.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reportcard.project.dtos.CourseRequestDto;
import com.reportcard.project.dtos.CourseResponseDto;
import com.reportcard.project.exceptions.NotFoundException;
import com.reportcard.project.services.CourseService;

@CrossOrigin
@RestController
@RequestMapping("/courses")
public class CourseController {

	@Autowired
	private CourseService courseService;
	
	@GetMapping
	public List<CourseResponseDto> getAll() {
		return courseService.getAll();
	}
	
	@PostMapping
	public ResponseEntity<CourseResponseDto> create(@Valid @RequestBody CourseRequestDto request) 
	throws URISyntaxException, NotFoundException {

		var response = courseService.create(request);
		
		var responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(new URI(String.format("/courses/%s", response.getId())));
		
		return new ResponseEntity<CourseResponseDto>(response, responseHeaders, HttpStatus.CREATED);
	}
}
