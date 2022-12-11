package com.reportcard.project;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.reportcard.project.dtos.GroupRequestDto;
import com.reportcard.project.dtos.GroupResponseDto;
import com.reportcard.project.dtos.StudentRequestDto;
import com.reportcard.project.dtos.StudentResponseDto;
import com.reportcard.project.exceptions.DuplicateItemException;
import com.reportcard.project.model.Group;
import com.reportcard.project.model.Student;
import com.reportcard.project.repositories.GroupRepository;
import com.reportcard.project.repositories.StudentRepository;
import com.reportcard.project.services.StudentService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class StudentTest {

	@Mock
	private StudentRepository studentRepositoryMock;
	
	@Mock
	private GroupRepository groupRepositoryMock;
	
	@InjectMocks
	private StudentService studentService;

	
	
	@Test
	void student_get() {
		
		List<Student> students = new ArrayList<Student>();
		students.add(new Student() {{ 
			setId(1);
			setLastName("Studentul x");
			setFirstName("y");
			setEmail("studentxy@gmail.com");
			setGroup(new Group() {{
					setGroupCode(123);
					}});
			}});
		students.add(new Student() {{ 
			setId(1);
			setLastName("Studentul z");
			setFirstName("t");
			setEmail("studentzt@gmail.com");
			setGroup(new Group() {{
					setGroupCode(124);
					}});
			}});
		
		when(studentRepositoryMock.findAll()).thenReturn(students);
		
		List<StudentResponseDto> response = studentService.getAll();
		
		assertNotNull(response);
		assertEquals(2, response.size());
		
		List<StudentResponseDto> expected = students.stream()
				.map(p -> new StudentResponseDto() {{ 
					setId(p.getId());
					setLastName(p.getLastName());
					setFirstName(p.getFirstName());
					setEmail(p.getEmail());
					setGroup(new ModelMapper().map(p.getGroup(), GroupResponseDto.class));
				}})
				.collect(Collectors.toList());
		
		assertThat(response)
			.usingRecursiveComparison()
			.isEqualTo(expected);
	}
	
	@Test
	void student_create() throws DuplicateItemException {
		
		var s1 = new StudentRequestDto() {{
			setLastName("Studentul x");
			setFirstName("y");
			setEmail("x@gmail.com");
			setGroup(new GroupRequestDto() {{
					setGroupCode(125);
					}});
		}};
		var s2 = new Student() {{
			setId(1);
			setLastName("Studentul x");
			setFirstName("y");
			setEmail("x@gmail.com");
			setGroup(new Group() {{
					setGroupCode(125);
					}});
		}};
		var request = new ArrayList<StudentRequestDto>();
		request.add(s1);
		
		
		ArrayList<Student> savedStudents = new ArrayList<Student>();
		savedStudents.add(s2);
		
		var expected = new ArrayList<StudentResponseDto>();
		
		var savedGroup = new Group();
		List<Group> groups = new ArrayList<Group>();
		when(groupRepositoryMock.findAll()).thenReturn(groups);
		when(groupRepositoryMock.save(any(Group.class))).thenReturn((Group) savedGroup);
		
		
		for(Student savedStudent : savedStudents) {
		when(studentRepositoryMock.save(any(Student.class))).thenReturn(savedStudent);
		
		var expectedStudent = new StudentResponseDto() {{
			setId(savedStudent.getId());
			setLastName(savedStudent.getLastName());
			setFirstName(savedStudent.getFirstName());
			setEmail(savedStudent.getEmail());
			setGroup(new ModelMapper().map(savedStudent.getGroup(), GroupResponseDto.class));
		}};
		expected.add(expectedStudent);
		}

		var response = studentService.create(request); 
		
		verify(studentRepositoryMock).save(any(Student.class));
		
		
		assertThat(response)
			.usingRecursiveComparison()
			.isEqualTo(expected);
	}
	

	
	
}
