package com.reportcard.project.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reportcard.project.dtos.CourseRequestDto;
import com.reportcard.project.dtos.CourseResponseDto;
import com.reportcard.project.exceptions.DuplicateItemException;
import com.reportcard.project.services.CourseService;

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
	public ResponseEntity<CourseResponseDto>  create(@Valid @RequestBody CourseRequestDto request) throws URISyntaxException, DuplicateItemException{
		var response = courseService.create(request);

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(new URI(String.format("/courses/%s", response.getId())));

		return new ResponseEntity<CourseResponseDto>(response, responseHeaders, HttpStatus.CREATED);
	}
	
	
	@PutMapping(path = "/update/{objectId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<CourseResponseDto> updateCourse(@PathVariable String objectId,
			@RequestBody CourseResponseDto courseDto) {
		return courseService.getAll();
	}
	
	@DeleteMapping(path = "/delete/{objectId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<CourseResponseDto> deleteCourse(@PathVariable String objectId) {
		return courseService.getAll();
	}
	
}
