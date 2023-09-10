package com.guardjo.ticketmanager.web.data;

import io.github.guardjo.ticketmanager.common.domain.Program;

public record ProgramSimpleData(
        Long programId,
        String programName
) {
    public static ProgramSimpleData of(Long programId, String programName) {
        return new ProgramSimpleData(programId, programName);
    }

    public static ProgramSimpleData from(Program program) {
        return ProgramSimpleData.of(program.getId(), program.getName());
    }
}
