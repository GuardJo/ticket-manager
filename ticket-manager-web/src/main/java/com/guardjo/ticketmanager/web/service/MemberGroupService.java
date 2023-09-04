package com.guardjo.ticketmanager.web.service;

import com.guardjo.ticketmanager.web.data.UserGroupSimpleData;
import io.github.guardjo.ticketmanager.common.domain.MemberGroup;
import io.github.guardjo.ticketmanager.common.repository.MemberGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MemberGroupService {
    private final MemberGroupRepository memberGroupRepository;

    /**
     * 현재 저장되어 있는 사용자 그룹 전체 목록을 반환한다.
     *
     * @return UserGroupSimpleData 목록
     */
    @Transactional(readOnly = true)
    public List<UserGroupSimpleData> findAllUserGroupSimpleDataList() {
        log.info("Find All UserGroupSimpleData List");

        return memberGroupRepository.findAll().stream()
                .map(UserGroupSimpleData::from)
                .toList();
    }

    public MemberGroup findMemberGroup(long groupId) {
        log.info("Find MemberGroup Entity, groupId = {}", groupId);

        return memberGroupRepository.findById(groupId).orElse(null);
    }
}
