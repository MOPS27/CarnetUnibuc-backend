package com.reportcard.project.services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reportcard.project.dtos.ProgrammeResponseDto;
import com.reportcard.project.model.Programme;
import com.reportcard.project.repositories.ProgrammeRepository;

@Service
public class ProgrammeService {

	@Autowired
	ProgrammeRepository programmeRepository;
	
	
	public List<ProgrammeResponseDto> getAll() {
		List<ProgrammeResponseDto> returnValue = new ArrayList<>();

		
		List<Programme> programms = programmeRepository.findAll();
		
		for (Programme entity : programms) {
			returnValue.add(new ModelMapper().map(entity, ProgrammeResponseDto.class));
		}

		return returnValue;
	}
}
