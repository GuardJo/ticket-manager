insert into program (name, count, expiration_period)
values ('PT 10회권', 10, null);
insert into program (name, count, expiration_period)
values ('PT 20회권', 20, null);
insert into program (name, count, expiration_period)
values ('PT 30회권', 30, null);
insert into program (name, count, expiration_period)
values ('수영 3개월권', null, 90);
insert into program (name, count, expiration_period)
values ('헬스장 1개월권', null, 30);

insert into member (name, status, phone_number, email)
values ('tester', 'ACTIVE', '010-1010-1011', 'test@mail.com');
insert into member (name, status, phone_number, email)
values ('tester2', 'INACTIVE', '010-1010-1012', 'test2@mail.com');
insert into member (name, status, phone_number, email)
values ('tester3', 'ACTIVE', '010-1010-1013', 'test3@mail.com');
insert into member (name, status, phone_number, email)
values ('tester4', 'ACTIVE', '010-1010-1014', 'test4@mail.com');

insert into ticket (member_id, program_id, remaining_count, status, started_time, expired_time)
values (1, 1, 10, 'READY', '2023-05-01', '2024-05-01');
insert into ticket (member_id, program_id, remaining_count, status, started_time, expired_time)
values (2, 1, 10, 'READY', '2023-05-01', '2024-05-01');
insert into ticket (member_id, program_id, remaining_count, status, started_time, expired_time)
values (3, 1, 10, 'READY', '2023-05-01', '2024-05-01');
insert into ticket (member_id, program_id, remaining_count, status, started_time, expired_time)
values (4, 4, 90, 'READY', '2023-05-01', '2024-05-01');

insert into reservation (member_id, ticket_id, used_count, started_time, finished_time)
values (1, 1, 0, '2023-05-01', '2023-05-02');
insert into reservation (member_id, ticket_id, used_count, started_time, finished_time)
values (2, 2, 0, '2023-05-01', '2023-05-02');
insert into reservation (member_id, ticket_id, used_count, started_time, finished_time)
values (3, 3, 0, '2023-05-01', '2023-05-02');
insert into reservation (member_id, ticket_id, used_count, started_time, finished_time)
values (4, 4, 0, '2023-05-01', '2023-05-02');

insert into member_group (group_name)
values ('test group');
insert into member_group (group_name)
values ('test group2');

insert into member_group_member (member_id, group_id)
values (1, 1);
insert into member_group_member (member_id, group_id)
VALUES (2, 1);
insert into member_group_member (member_id, group_id)
values (3, 2);
insert into member_group_member (member_id, group_id)
VALUES (4, 2);

insert into free_ticket (status, group_id, ticket_id)
VALUES ('NOT_RECEIVE', 1, 1);

insert into notification (content, kakao_uuid, notification_status, reservation_id)
values ('Test Notification1', 'kakao-test-uuid1', 'NOT_SEND', 1);
insert into notification (content, kakao_uuid, notification_status, reservation_id)
values ('Test Notification2', 'kakao-test-uuid2', 'NOT_SEND', 2);
insert into notification (content, kakao_uuid, notification_status, reservation_id)
values ('Test Notification3', 'kakao-test-uuid3', 'NOT_SEND', 3);
insert into notification (content, kakao_uuid, notification_status, reservation_id)
values ('Test Notification4', 'kakao-test-uuid4', 'NOT_SEND', 4);