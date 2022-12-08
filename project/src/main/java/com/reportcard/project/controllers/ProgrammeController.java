package com.reportcard.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.reportcard.project.dtos.ProgrammeResponseDto;
import com.reportcard.project.services.ProgrammeService;

@RestController
@RequestMapping("/programmes")
public class ProgrammeController {

	private final ProgrammeService programmeService;
	
	@Autowired
	public ProgrammeController(ProgrammeService programmeService) {
		this.programmeService = programmeService;
	}
	
	@GetMapping(path = "/get")
	public @ResponseBody List<ProgrammeResponseDto> getAll() {
		return programmeService.getAll();
	}
}
