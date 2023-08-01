create table member
(
    member_id     bigint       not null auto_increment primary key,
    name          varchar(100) not null,
    status        varchar(50)  not null,
    phone_number  varchar(50),
    email         varchar(100),
    created_time  timestamp    not null default CURRENT_TIMESTAMP,
    modified_time timestamp    not null default CURRENT_TIMESTAMP
);

create table program
(
    program_id        bigint       not null auto_increment primary key,
    name              varchar(100) not null,
    count             int                   default null,
    expiration_period int                   default null,
    created_time      timestamp    not null default CURRENT_TIMESTAMP,
    modified_time     timestamp    not null default CURRENT_TIMESTAMP
);

create table ticket
(
    ticket_id       bigint       not null auto_increment primary key,
    member_id       bigint       not null,
    program_id      bigint       not null,
    remaining_count int                   default 0,
    status          varchar(255) null,
    started_time    timestamp    not null,
    expired_time    timestamp    not null,
    created_time    timestamp    not null default CURRENT_TIMESTAMP,
    modified_time   timestamp    not null default CURRENT_TIMESTAMP,
    constraint FKd5t3axsm3pj3mt41qhpkmw9yj
        foreign key (member_id) references member (member_id),
    constraint FKliq3sqxtuknpgr7rsmufx5p12
        foreign key (program_id) references program (program_id)
);

create table reservation
(
    reservation_id bigint    not null auto_increment primary key,
    member_id      bigint    not null,
    ticket_id      bigint    not null,
    used_count     int                default 0,
    started_time   timestamp not null,
    finished_time  timestamp not null,
    created_time   timestamp not null default CURRENT_TIMESTAMP,
    modified_time  timestamp not null default CURRENT_TIMESTAMP,
    constraint FK68999qe28ym9eqqlowybh9nvn
        foreign key (member_id) references member (member_id),
    constraint FKkn72g30lgqj1a31doyr5qb6ln
        foreign key (ticket_id) references ticket (ticket_id)
);

create table member_group
(
    group_id      bigint auto_increment
        primary key,
    group_name    varchar(255) not null,
    created_time  timestamp    not null default CURRENT_TIMESTAMP,
    modified_time timestamp    not null default CURRENT_TIMESTAMP
);

create table free_ticket
(
    free_ticket_id bigint auto_increment
        primary key,
    status         varchar(255) null,
    group_id       bigint       not null,
    ticket_id      bigint       not null,
    created_time   timestamp    not null default CURRENT_TIMESTAMP,
    modified_time  timestamp    not null default CURRENT_TIMESTAMP,
    constraint FKfyu8ksipghxojntd5ol2y0t9g
        foreign key (ticket_id) references ticket (ticket_id),
    constraint FKoaumxnmeoevb7ckl3hsn424s5
        foreign key (group_id) references member_group (group_id)
);

-- auto-generated definition
create table member_group_member
(
    member_group_member_id bigint auto_increment
        primary key,
    group_id               bigint null,
    member_id              bigint null,
    constraint FKh1c85sw916m4xvj80kwun910
        foreign key (group_id) references member_group (group_id),
    constraint FKl3uv5gmqeu3hoi3goc08xq32o
        foreign key (member_id) references member (member_id)
);

-- auto-generated definition
create table notification
(
    notification_id     bigint auto_increment
        primary key,
    created_time        datetime(6)  null,
    modified_time       datetime(6)  null,
    content             varchar(200) not null,
    kakao_uuid          varchar(255) not null,
    notification_status varchar(255) not null,
    reservation_id      bigint       null,
    constraint FKs24nj4175mp37khlffo484eok
        foreign key (reservation_id) references reservation (reservation_id)
);

create table reservation_history
(
    reservation_history_id       bigint auto_increment
        primary key,
    history_date                 date      not null,
    total_new_reservation_count  int                default 0 not null,
    total_reservation_used_count int                default 0 not null,
    created_time                 timestamp not null default CURRENT_TIMESTAMP,
    modified_time                timestamp not null default CURRENT_TIMESTAMP
)
    comment '이용 현황';

create index reservation_history_reservation_history_id_history_date_index
    on reservation_history (reservation_history_id, history_date);


