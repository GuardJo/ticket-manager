package io.github.guardjo.ticketmanager.common.repository;

import io.github.guardjo.ticketmanager.common.domain.MemberGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberGroupRepository extends JpaRepository<MemberGroup, Long> {
}
