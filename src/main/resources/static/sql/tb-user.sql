create table academy_user
(
    id            bigint generated always as identity,
    username      varchar(255) not null,
    password_hash varchar(255) not null,
    enabled       boolean      not null default true,
    role          varchar(50)  not null,

    constraint pk_user
        primary key (id),

    constraint uq_user_username
        unique (username)
);