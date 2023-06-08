create table user
(
    user_id       bigint       not null auto_increment primary key,
    name          varchar(100) not null,
    status        varchar(50)  not null,
    phone_number  varchar(50),
    email         varchar(100),
    create_time   timestamp    not null default CURRENT_TIMESTAMP,
    modified_time timestamp    not null default CURRENT_TIMESTAMP
);

create table program
(
    program_id        bigint       not null auto_increment primary key,
    name              varchar(100) not null,
    count             int                   default null,
    expiration_period int                   default null,
    create_time       timestamp    not null default CURRENT_TIMESTAMP,
    modified_time     timestamp    not null default CURRENT_TIMESTAMP
);

create table ticket
(
    ticket_id       bigint    not null auto_increment primary key,
    user_id         bigint    not null,
    program_id      bigint    not null,
    remaining_count int                default 0,
    started_time    timestamp not null,
    expired_time    timestamp not null,
    created_time    timestamp not null default CURRENT_TIMESTAMP,
    modified_time   timestamp not null default CURRENT_TIMESTAMP
);

create table reservation
(
    reservation_id bigint    not null auto_increment primary key,
    user_id        bigint    not null,
    ticket_id      bigint    not null,
    used_count     int                default 0,
    started_time   timestamp not null,
    finished_time  timestamp not null,
    created_time   timestamp not null default CURRENT_TIMESTAMP,
    modified_time  timestamp not null default CURRENT_TIMESTAMP
);