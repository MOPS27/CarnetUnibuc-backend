package com.reportcard.project.services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reportcard.project.dtos.ProgrammeRequestDto;
import com.reportcard.project.dtos.ProgrammeResponseDto;
import com.reportcard.project.exceptions.DuplicateItemException;
import com.reportcard.project.exceptions.NotFoundException;
import com.reportcard.project.model.Programme;
import com.reportcard.project.repositories.ProgrammeRepository;


import javax.validation.Validator;

@Service
public class ProgrammeService {

	@Autowired
	ProgrammeRepository programmeRepository;

	@Autowired
    private Validator validator;
	
	ModelMapper modelMapper = new ModelMapper();
	
	public List<ProgrammeResponseDto> getAll() {
		List<ProgrammeResponseDto> returnValue = new ArrayList<>();

		
		List<Programme> programms = programmeRepository.findAll();
		
		for (Programme entity : programms) {
			returnValue.add(modelMapper.map(entity, ProgrammeResponseDto.class));
		}

		return returnValue;
	}
	
	public ProgrammeResponseDto create(ProgrammeRequestDto request) throws DuplicateItemException {
		
		//validator.validate(request);
		
		Programme programme = modelMapper.map(request, Programme.class);
		
		validateProgrammeNameUnique(request.getName());
		
		var createdProgramme = programmeRepository.save(programme);
		
		return modelMapper.map(createdProgramme, ProgrammeResponseDto.class);
	}
	
	private void validateProgrammeNameUnique(String name) throws DuplicateItemException {
		
		var all = programmeRepository.findAll();
		
		var any = all.stream()
				.anyMatch(x -> x.getName().equals(name));
		
		if (any) {
			throw new DuplicateItemException("Programul de studiu", "denumirea", name);
		}
	}
	
	public void delete(Integer id) throws NotFoundException {
		var programme = programmeRepository.findById(id);
		
		if(programme.isEmpty()) {
			throw new NotFoundException("Programul de studiu", "id", id.toString());
		}
		
		programmeRepository.delete(programme.get());
	}
}
