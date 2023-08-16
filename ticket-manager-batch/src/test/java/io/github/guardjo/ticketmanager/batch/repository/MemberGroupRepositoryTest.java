package io.github.guardjo.ticketmanager.batch.repository;

import io.github.guardjo.ticketmanager.batch.config.JpaConfig;
import io.github.guardjo.ticketmanager.batch.domain.Member;
import io.github.guardjo.ticketmanager.batch.domain.MemberGroup;
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
class MemberGroupRepositoryTest {
    @Autowired
    private MemberGroupRepository memberGroupRepository;

    private final static long TEST_DATA_SIZE = 2L;

    @DisplayName("신규 MemberGroup 저장 테스트")
    @Test
    void testCreateMemberGroup() {
        MemberGroup expected = MemberGroup.builder()
                .members(List.of())
                .groupName("testGroup")
                .build();

        MemberGroup actual = memberGroupRepository.save(expected);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("특정 MemberGroup 조회 테스트")
    @Test
    void testFindMemberGroup() {
        long groupId = 1L;

        // data.sql 참고
        String groupName = "test group";
        List<Long> expectedMemberIds = List.of(1L, 2L);

        MemberGroup memberGroup = memberGroupRepository.findById(groupId).orElseThrow();
        List<Long> actualMemberIds = memberGroup.getMembers().stream()
                .map(Member::getId)
                .toList();

        assertThat(memberGroup.getGroupName()).isEqualTo(groupName);
        assertThat(actualMemberIds).isEqualTo(expectedMemberIds);
    }

    @DisplayName("전체 MemberGroup 조회 테스트")
    @Test
    void testFindAllMemberGroups() {
        List<MemberGroup> memberGroups = memberGroupRepository.findAll();

        assertThat(memberGroups.size()).isEqualTo(TEST_DATA_SIZE);
    }

    @DisplayName("특정 MemberGroup 제거 테스트")
    @Test
    void testDeleteMemberGroup() {
        long groupId = 1L;

        memberGroupRepository.deleteById(groupId);

        assertThat(memberGroupRepository.count()).isEqualTo(TEST_DATA_SIZE - 1);
    }
}