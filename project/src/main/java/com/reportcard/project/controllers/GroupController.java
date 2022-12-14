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

import com.reportcard.project.dtos.GroupRequestDto;
import com.reportcard.project.dtos.GroupResponseDto;
import com.reportcard.project.exceptions.DuplicateItemException;
import com.reportcard.project.services.GroupService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/groups")
public class GroupController {

	@Autowired
	private GroupService groupService;
	
	@GetMapping
	public List<GroupResponseDto> getAll() {
		return groupService.getAll();
	}
	
	@PostMapping
	public ResponseEntity<GroupResponseDto> create(@Valid @RequestBody GroupRequestDto request) 
	throws URISyntaxException, DuplicateItemException {

		var response = groupService.create(request);
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(new URI(String.format("/groups/%s", response.getId())));
		
		return new ResponseEntity<GroupResponseDto>(response, responseHeaders, HttpStatus.CREATED);
	}
}
