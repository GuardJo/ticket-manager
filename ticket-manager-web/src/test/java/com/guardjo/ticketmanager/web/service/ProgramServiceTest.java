package com.guardjo.ticketmanager.web.service;

import com.guardjo.ticketmanager.web.data.ProgramSimpleData;
import com.guardjo.ticketmanager.web.util.TestDataGenerator;
import io.github.guardjo.ticketmanager.common.domain.Program;
import io.github.guardjo.ticketmanager.common.repository.ProgramRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ProgramServiceTest {
    @Mock
    private ProgramRepository programRepository;

    @InjectMocks
    private ProgramService programService;

    @DisplayName("프로그램명 전체 목록 반환 테스트")
    @Test
    void testGetProgramSimpleDataList() {
        List<Program> expectedEntity = List.of(TestDataGenerator.program());
        List<ProgramSimpleData> expected = expectedEntity.stream()
                .map(ProgramSimpleData::from)
                .toList();

        given(programRepository.findAll()).willReturn(expectedEntity);

        List<ProgramSimpleData> actual = programService.getProgramSimpleDataList();

        assertThat(actual).isEqualTo(expected);

        then(programRepository).should().findAll();
    }

    @DisplayName("특정 프로그램 데이터 반환 테스트")
    @Test
    void testGetProgram() {
        long programId = 1L;
        Program expected = TestDataGenerator.program();

        given(programRepository.findById(eq(programId))).willReturn(Optional.of(expected));

        Program actual = programService.getProgram(programId);

        assertThat(actual).isEqualTo(expected);

        then(programRepository).should().findById(eq(programId));
    }
}