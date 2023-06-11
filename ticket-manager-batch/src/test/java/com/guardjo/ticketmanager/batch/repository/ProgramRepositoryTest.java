package com.guardjo.ticketmanager.batch.repository;

import com.guardjo.ticketmanager.batch.config.JpaConfig;
import com.guardjo.ticketmanager.batch.domain.Program;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@Import(JpaConfig.class)
@DataJpaTest
class ProgramRepositoryTest {
    @Autowired
    private ProgramRepository programRepository;

    private final static long TEST_DATA_SIZE = 5L;

    @DisplayName("신규 Program 저장 테스트")
    @Test
    void testCreateProgram() {
        Program newProgram = Program.builder()
                .name("New Program")
                .count(60)
                .build();

        Program actual = programRepository.save(newProgram);

        assertThat(actual)
                .hasFieldOrPropertyWithValue("name", newProgram.getName())
                .hasFieldOrPropertyWithValue("count", newProgram.getCount());
    }

    @DisplayName("특정 Program 객체 조회 테스트")
    @Test
    void testFindProgram() {
        Long programId = 1L;
        String programName = "PT 10회권"; // data.sql 참고

        Program actual = programRepository.findById(programId).orElseThrow();

        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo(programName);
    }

    @DisplayName("전체 Prgoram 객체 조회 테스트")
    @Test
    void testFindAllPrograms() {
        List<Program> programList = programRepository.findAll();

        assertThat(programList.size()).isEqualTo(TEST_DATA_SIZE);
    }

    @DisplayName("특정 Program 객체 수정 테스트")
    @Test
    void testUpdateProgram() {
        Program program = programRepository.findById(1L).orElseThrow();
        program.setCount(program.getCount() - 1);

        programRepository.flush();

        Program updateProgram = programRepository.findById(1L).orElseThrow();

        assertThat(updateProgram.getCount()).isEqualTo(program.getCount());
    }

    @DisplayName("특정 Program 객체 삭제 테스트")
    @Test
    void testDeleteProgram() {
        programRepository.deleteById(1L);

        assertThat(programRepository.count()).isEqualTo(TEST_DATA_SIZE - 1);
    }
}