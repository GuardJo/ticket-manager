package com.guardjo.ticketmanager.web.service;

import com.guardjo.ticketmanager.web.data.ProgramSimpleData;
import io.github.guardjo.ticketmanager.common.domain.Program;
import io.github.guardjo.ticketmanager.common.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ProgramService {
    private final ProgramRepository programRepository;

    /**
     * 현재 서비스 중인 프로그램명 전체 목록 정보 조회
     *
     * @return ProgramSimpleData List
     */
    @Transactional(readOnly = true)
    public List<ProgramSimpleData> getProgramSimpleDataList() {
        log.info("Find ProgramSimpleData List");

        return programRepository.findAll().stream()
                .map(ProgramSimpleData::from)
                .toList();
    }

    /**
     * 주어진 식별자에 대한 Program 데이터 조회
     *
     * @param programId Program에 대한 식별자
     * @return Program Entity
     */
    @Transactional(readOnly = true)
    public Program getProgram(long programId) {
        log.info("Find Program, programId = {}", programId);

        return programRepository.findById(programId).orElse(null);
    }
}
