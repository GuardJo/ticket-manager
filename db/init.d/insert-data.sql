insert into program (program_id, name, count, expiration_period)
values (1, 'PT 10회권', 10, null);
insert into program (program_id, name, count, expiration_period)
values (2, 'PT 20회권', 20, null);
insert into program (program_id, name, count, expiration_period)
values (3, 'PT 30회권', 30, null);
insert into program (program_id, name, count, expiration_period)
values (4, '수영 3개월권', null, 90);
insert into program (program_id, name, count, expiration_period)
values (5, '헬스장 1개월권', null, 30);

insert into member (member_id, name, status, phone_number, email)
values (1, 'tester', 'ACTIVE', '010-1010-1011', 'test@mail.com');
insert into member (member_id, name, status, phone_number, email)
values (2, 'tester2', 'INACTIVE', '010-1010-1012', 'test2@mail.com');
insert into member (member_id, name, status, phone_number, email)
values (3, 'tester3', 'ACTIVE', '010-1010-1013', 'test3@mail.com');
insert into member (member_id, name, status, phone_number, email)
values (4, 'tester4', 'ACTIVE', '010-1010-1014', 'test4@mail.com');