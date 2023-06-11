package com.guardjo.ticketmanager.batch.repository;

import com.guardjo.ticketmanager.batch.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
