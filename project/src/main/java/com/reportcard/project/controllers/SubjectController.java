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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reportcard.project.dtos.SubjectRequestDto;
import com.reportcard.project.dtos.SubjectResponseDto;
import com.reportcard.project.exceptions.DuplicateItemException;
import com.reportcard.project.services.SubjectService;

@CrossOrigin
@RestController
@RequestMapping("/subjects")
public class SubjectController {

	@Autowired
	private SubjectService subjectService;
	
	@GetMapping
	public List<SubjectResponseDto> getAll() {
		return subjectService.getAll();
	}
	
	@PostMapping
	public ResponseEntity<SubjectResponseDto> create(@Valid @RequestBody SubjectRequestDto request) throws URISyntaxException, DuplicateItemException{
		var response = subjectService.create(request);

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(new URI(String.format("/subjects/%s", response.getId())));

		return new ResponseEntity<SubjectResponseDto>(response, responseHeaders, HttpStatus.CREATED);
	}
	
	
	@PutMapping(path = "/update/{objectId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<SubjectResponseDto> updateSubject(@PathVariable String objectId,
			@RequestBody SubjectResponseDto subjectDto) {
		return subjectService.getAll();
	}
	
	@DeleteMapping(path = "/delete/{objectId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<SubjectResponseDto> deleteSubject(@PathVariable String objectId) {
		return subjectService.getAll();
	}
	
}
