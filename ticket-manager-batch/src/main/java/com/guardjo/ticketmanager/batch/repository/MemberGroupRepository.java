package com.guardjo.ticketmanager.batch.repository;

import com.guardjo.ticketmanager.batch.domain.MemberGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberGroupRepository extends JpaRepository<MemberGroup, Long> {
}
