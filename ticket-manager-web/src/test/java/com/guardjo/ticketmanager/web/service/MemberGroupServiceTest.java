package com.guardjo.ticketmanager.web.service;

import com.guardjo.ticketmanager.web.data.UserGroupSimpleData;
import com.guardjo.ticketmanager.web.util.TestDataGenerator;
import io.github.guardjo.ticketmanager.common.domain.MemberGroup;
import io.github.guardjo.ticketmanager.common.repository.MemberGroupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class MemberGroupServiceTest {
    @Mock
    private MemberGroupRepository memberGroupRepository;

    @InjectMocks
    private MemberGroupService memberGroupService;

    @DisplayName("사용자 그룹 전체 목록 반환 테스트")
    @Test
    void testFindAllUserGroupSimpleDataList() {
        List<MemberGroup> expectedEntity = List.of(TestDataGenerator.memberGroup());
        List<UserGroupSimpleData> expected = expectedEntity.stream()
                .map(UserGroupSimpleData::from)
                .toList();

        given(memberGroupRepository.findAll()).willReturn(expectedEntity);

        List<UserGroupSimpleData> actual = memberGroupService.findAllUserGroupSimpleDataList();

        assertThat(actual).isEqualTo(expected);

        then(memberGroupRepository).should().findAll();
    }

    @DisplayName("사용자 그룹 단일 조회 테스트")
    @ParameterizedTest
    @MethodSource("findEntityTestData")
    void testFindMemberGroup(Optional<MemberGroup> memberGroup) {
        long groupId = memberGroup.isPresent() ? memberGroup.get().getId() : 1L;

        given(memberGroupRepository.findById(eq(groupId))).willReturn(memberGroup);

        MemberGroup actual = memberGroupService.findMemberGroup(groupId);

        if (memberGroup.isPresent()) {
            assertThat(actual).isEqualTo(memberGroup.get());
        } else {
            assertThat(actual).isNull();
        }

        then(memberGroupRepository).should().findById(eq(groupId));
    }

    static Stream<Arguments> findEntityTestData() {
        return Stream.of(
                Arguments.of(Optional.of(TestDataGenerator.memberGroup())),
                Arguments.of(Optional.empty())
        );
    }
}