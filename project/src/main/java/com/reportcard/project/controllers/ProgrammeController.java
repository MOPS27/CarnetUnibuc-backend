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

import com.reportcard.project.dtos.ProgrammeRequestDto;
import com.reportcard.project.dtos.ProgrammeResponseDto;
import com.reportcard.project.exceptions.DuplicateItemException;
import com.reportcard.project.services.ProgrammeService;

@CrossOrigin
@RestController
@RequestMapping("/programmes")
public class ProgrammeController {

	@Autowired
	private ProgrammeService programmeService;
	
	@GetMapping
	public List<ProgrammeResponseDto> getAll() {
		return programmeService.getAll();
	}
	
	@PostMapping
	public ResponseEntity<ProgrammeResponseDto> create(@Valid @RequestBody ProgrammeRequestDto request) 
	throws URISyntaxException, DuplicateItemException {

		var response = programmeService.create(request);
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(new URI(String.format("/programmes/%s", response.getId())));
		
		return new ResponseEntity<ProgrammeResponseDto>(response, responseHeaders, HttpStatus.CREATED);
	}
}
