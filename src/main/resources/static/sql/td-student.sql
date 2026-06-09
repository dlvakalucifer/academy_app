create table storage_document
(
    id         bigint generated always as identity,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    email      varchar(255) not null,

    constraint pk_student primary key (id),
    constraint email_student unique (email)
);