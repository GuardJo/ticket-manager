package com.guardjo.ticketmanager.web.service;

import com.guardjo.ticketmanager.web.data.UserGroupSimpleData;
import com.guardjo.ticketmanager.web.util.TestDataGenerator;
import io.github.guardjo.ticketmanager.common.domain.MemberGroup;
import io.github.guardjo.ticketmanager.common.repository.MemberGroupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
}