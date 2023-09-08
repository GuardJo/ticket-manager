package com.guardjo.ticketmanager.web.data;

import io.github.guardjo.ticketmanager.common.domain.MemberGroup;

public record UserGroupSimpleData(
        long groupId,
        String groupName
) {
    public static UserGroupSimpleData create(long groupId, String groupName) {
        return new UserGroupSimpleData(groupId, groupName);
    }

    public static UserGroupSimpleData from(MemberGroup group) {
        return UserGroupSimpleData.create(group.getId(), group.getGroupName());
    }
}
