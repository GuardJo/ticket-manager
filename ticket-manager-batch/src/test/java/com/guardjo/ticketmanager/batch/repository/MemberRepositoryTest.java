package com.guardjo.ticketmanager.batch.repository;

import com.guardjo.ticketmanager.batch.config.JpaConfig;
import com.guardjo.ticketmanager.batch.domain.Member;
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
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    private final static long TEST_DATA_SIZE = 4L;

    @DisplayName("Member 객체 신규 저장 테스트")
    @Test
    void testCreateMember() {
        Member expected = Member.builder()
                .name("tester")
                .status("test")
                .phoneNumber("11-11-11")
                .email("test@mail.com")
                .build();

        Member actual = memberRepository.save(expected);

        assertThat(actual)
                .hasFieldOrPropertyWithValue("name", expected.getName())
                .hasFieldOrPropertyWithValue("status", expected.getStatus())
                .hasFieldOrPropertyWithValue("phoneNumber", expected.getPhoneNumber())
                .hasFieldOrPropertyWithValue("email", expected.getEmail());

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("특정 Member 객체 조회 테스트")
    @Test
    void testFindMember() {
        Long memberId = 1L;
        String memberName = "tester"; // data.sql 참고

        Member actual = memberRepository.findById(memberId).orElseThrow();

        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo(memberName);
    }

    @DisplayName("Member 전체 조회 테스트")
    @Test
    void testFindAllMembers() {
        List<Member> members = memberRepository.findAll();

        assertThat(members.size()).isEqualTo(TEST_DATA_SIZE);
    }

    @DisplayName("특정 Member 객체 수정 테스트")
    @Test
    void testUpdateMember() {
        String newName = "newTester";
        Long memberId = 1L;

        Member actual = memberRepository.findById(memberId).orElseThrow();
        actual.setName(newName);
        Member updateMember = memberRepository.saveAndFlush(actual);

        assertThat(updateMember.getName()).isEqualTo(newName);
    }

    @DisplayName("특정 Member 객체 삭제 테스트")
    @Test
    void testDeleteMember() {
        Long memberId = 1L;

        memberRepository.deleteById(memberId);

        assertThat(memberRepository.count()).isEqualTo(TEST_DATA_SIZE - 1);
    }
}