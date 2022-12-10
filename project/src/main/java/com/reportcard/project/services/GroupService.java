package com.reportcard.project.services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reportcard.project.dtos.GroupRequestDto;
import com.reportcard.project.dtos.GroupResponseDto;
import com.reportcard.project.exceptions.DuplicateItemException;
import com.reportcard.project.model.Group;
import com.reportcard.project.repositories.GroupRepository;

@Service
public class GroupService {

	@Autowired
	GroupRepository groupRepository;

	
	ModelMapper modelMapper = new ModelMapper();
	
	public List<GroupResponseDto> getAll() {
		List<GroupResponseDto> returnValue = new ArrayList<>();

		
		List<Group> programms = groupRepository.findAll();
		
		for (Group entity : programms) {
			returnValue.add(modelMapper.map(entity, GroupResponseDto.class));
		}

		return returnValue;
	}
	
	public GroupResponseDto create(GroupRequestDto request)  {
		
		//validator.validate(request);
		System.out.println(request.getGroupCode());
		
		Group group = modelMapper.map(request, Group.class);
		
		var entity = validateUnique(request.getGroupCode());
			if(entity== null){
				var createdGroup = groupRepository.save(group);
				return modelMapper.map(createdGroup, GroupResponseDto.class);
			}
		return modelMapper.map(entity, GroupResponseDto.class);
			
		
		//var createdGroup = groupRepository.save(group);
		
		//return modelMapper.map(createdGroup, GroupResponseDto.class);
	}
	
	private Group validateUnique(Integer groupCode) //throws DuplicateItemException 
	{
		
		var all = groupRepository.findAll();
		for(Group group: all) {
			if(group.getGroupCode() == groupCode) {
				return group;
			}
		}
		
		return null;
//		var any = all.stream()
//				.anyMatch(x -> x.getGroupCode() == groupCode);
//		
//		if (any) {
//			throw new DuplicateItemException("Grupa ", "numarul ", groupCode.toString());
//		}
	}
}
