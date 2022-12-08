package com.reportcard.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.reportcard.project.dtos.ProgrammeResponseDto;
import com.reportcard.project.services.ProgrammeService;

@RestController
@RequestMapping("/programmes")
public class ProgrammeController {

	
	@Autowired
	private ProgrammeService programmeService;
	
	@GetMapping(path = "/get")
	public List<ProgrammeResponseDto> getAll() {
		return programmeService.getAll();
	}
}
