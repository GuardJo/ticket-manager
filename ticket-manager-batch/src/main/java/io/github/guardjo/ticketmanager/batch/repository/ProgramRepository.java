package io.github.guardjo.ticketmanager.batch.repository;

import io.github.guardjo.ticketmanager.batch.domain.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
}
