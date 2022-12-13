package com.reportcard.project.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reportcard.project.dtos.CourseResponseDto;
import com.reportcard.project.dtos.SubjectRequestDto;
import com.reportcard.project.dtos.SubjectResponseDto;
import com.reportcard.project.exceptions.DuplicateItemException;
import com.reportcard.project.exceptions.NotFoundException;
import com.reportcard.project.services.CourseService;
import com.reportcard.project.services.SubjectService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/subjects")
public class SubjectController {

	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private CourseService courseService;
	
	@GetMapping
	public List<SubjectResponseDto> getAll() {
		return subjectService.getAll();
	}
	
	@GetMapping("/{id}/courses")
	public List<CourseResponseDto> getBySubject(@PathVariable int id) throws NotFoundException {
		return courseService.getBySubject(id);
	}
	
	@PostMapping
	public ResponseEntity<SubjectResponseDto> create(@Valid @RequestBody SubjectRequestDto request) throws URISyntaxException, DuplicateItemException{
		var response = subjectService.create(request);

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(new URI(String.format("/subjects/%s", response.getId())));

		return new ResponseEntity<SubjectResponseDto>(response, responseHeaders, HttpStatus.CREATED);
	}
	
	
}
