package com.reportcard.project;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
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
import org.springframework.boot.test.context.SpringBootTest;

import com.reportcard.project.dtos.SubjectRequestDto;
import com.reportcard.project.dtos.SubjectResponseDto;
import com.reportcard.project.exceptions.DuplicateItemException;
import com.reportcard.project.model.Subject;
import com.reportcard.project.repositories.SubjectRepository;
import com.reportcard.project.services.SubjectService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class SubjectTests {

	@Mock
	private SubjectRepository subjectRepositoryMock;
	
	@InjectMocks
	private SubjectService subjectService;

	
	@Test
	void subject_get() {
		
		List<Subject> subjects = new ArrayList<Subject>();
		subjects.add(new Subject() {{ 
			setId(1);
			setName("Materia x");
			setCreditCount(3); 
			}});
		subjects.add(new Subject() {{ 
			setId(2);
			setName("Materia y");
			setCreditCount(4); 
			}});
		
		when(subjectRepositoryMock.findAll()).thenReturn(subjects);
		
		List<SubjectResponseDto> response = subjectService.getAll();
		
		assertNotNull(response);
		assertEquals(2, response.size());
		
		List<SubjectResponseDto> expected = subjects.stream()
				.map(p -> new SubjectResponseDto() {{ 
					setId(p.getId());
					setName(p.getName());
					setCreditCount(p.getCreditCount());
				}})
				.collect(Collectors.toList());
		
		assertThat(response)
			.usingRecursiveComparison()
			.isEqualTo(expected);
	}
	
	@Test
	void subject_create() throws DuplicateItemException {
		
		var request = new SubjectRequestDto() {{
			setName("name");
			setCreditCount(3);
		}};
		
		var savedSubject = new Subject() {{
			setId(1);
			setName("name");
			setCreditCount(3);
		}};
		
		when(subjectRepositoryMock.save(any(Subject.class))).thenReturn(savedSubject);
		
		var response = subjectService.create(request);
		
		verify(subjectRepositoryMock).save(any(Subject.class));
		
		var expected = new SubjectResponseDto() {{
			setId(savedSubject.getId());
			setName(savedSubject.getName());
			setCreditCount(savedSubject.getCreditCount());
		}};
		
		assertThat(response)
			.usingRecursiveComparison()
			.isEqualTo(expected);
	}
	
	@Test
	void subject_create_throwsDuplicate_whenNameDuplicate() throws DuplicateItemException {
		
		var request = new SubjectRequestDto() {{
			setName("Materia x");
			setCreditCount(3);
		}};
		
		var subjects = new ArrayList<Subject>();
		subjects.add(new Subject(){{
			setId(1);
			setName("Materia x");
			setCreditCount(3);
		}});
		
		when(subjectRepositoryMock.findAll()).thenReturn(subjects);
		
		var exception = assertThrows(DuplicateItemException.class, () -> { 
			subjectService.create(request);
		});
		
		var expectedMessage = String.format("%s cu %s = %s deja exista", 
				"Materia", 
				"denumirea", 
				request.getName());
		var actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
		
		verify(subjectRepositoryMock, never()).save(any(Subject.class));
		
	}
}
