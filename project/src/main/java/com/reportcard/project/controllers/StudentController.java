package com.reportcard.project.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reportcard.project.dtos.StudentRequestDto;
import com.reportcard.project.dtos.StudentResponseDto;
import com.reportcard.project.services.StudentService;

@CrossOrigin
@RestController
@RequestMapping("/students")
public class StudentController {

	@Autowired
	private StudentService studentService;
	
	@GetMapping
	public List<StudentResponseDto> getAll() {
		return studentService.getAll();
	}
	
//	@PostMapping
//	public ResponseEntity<StudentResponseDto> create(@Valid @RequestBody StudentRequestDto request) 
//	throws URISyntaxException, DuplicateItemException {
//
//		var response = studentService.create(request);
//		
//		HttpHeaders responseHeaders = new HttpHeaders();
//		responseHeaders.setLocation(new URI(String.format("/students/%s", response.getId())));
//		
//		return new ResponseEntity<StudentResponseDto>(response, responseHeaders, HttpStatus.CREATED);
//	}
	// import a list of students from a JSON file
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<StudentResponseDto> create(@Valid @RequestBody List<StudentRequestDto> request){
		studentService.create(request);
		return studentService.getAll();
	}
}