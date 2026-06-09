insert into academy_user
(username,
 password_hash,
 enabled,
 role)
values ('admin',
        '$2a$10$dummyHashReplaceLater',
        true,
        'ADMIN');

insert into academy_user
(username,
 password_hash,
 enabled,
 role)
values ('lecturer',
        '$2a$10$dummyHashReplaceLater',
        true,
        'LECTURER');

insert into academy_user
(username,
 password_hash,
 enabled,
 role)
values ('student',
        '$2a$10$dummyHashReplaceLater',
        true,
        'STUDENT');