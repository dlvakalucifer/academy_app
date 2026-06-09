create table storage_document
(
    id           bigint generated always as identity,
    student_id   bigint       not null,
    object_key   varchar(255) not null,
    file_name    varchar(255) not null,
    content_type varchar(255),
    file_size    bigint       not null,

    constraint pk_document primary key (id),
    constraint fk_document_student foreign key (student_id) references student (id)
);