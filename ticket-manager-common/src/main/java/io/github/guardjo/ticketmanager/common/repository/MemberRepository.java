package io.github.guardjo.ticketmanager.common.repository;

import io.github.guardjo.ticketmanager.common.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
