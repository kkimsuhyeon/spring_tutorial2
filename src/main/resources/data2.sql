INSERT INTO member (email, password, nickname, memo, created_at)
VALUES ('test1@test.com', '1234', 'test1', 'I am test.', now()),
       ('test2@test.com', '1234', 'test2', '!!!!', now()),
       ('test3@test.com', '1234', 'test3', '????', now()),
       ('test4@test.com', '1234', 'test4', '@@@@', now()),
       ('test5@test.com', '1234', 'test5', '####', now())
;

INSERT INTO member_role (member_id, role)
VALUES (1, 'MANAGER'),
       (1, 'USER'),
       (2, 'MANAGER'),
       (3, 'USER')
;

INSERT INTO article (title, content, hashtag, status, member_id, created_by, modified_by, created_at, modified_at)
VALUES ('title1', 'content1', '#123', 'PRIVATE', 1, 'Kamilah', 'Murial', '2021-05-30 23:53:32',
        '2021-03-10 08:48:50'),
       ('title2', 'content2', '#234', 'PUBLIC', 1, 'Kamilah', 'Murial', '2021-05-30 23:53:52', '2021-03-10 08:48:50'),
       ('title3', 'content3', '#345', 'PRIVATE', 1, 'Kamilah', 'Murial', '2021-05-30 23:53:51', '2021-03-10 08:48:50'),
       ('title94', 'content4', '#456', 'PUBLIC', 2, 'Kamilah', 'Murial', '2021-05-30 23:53:50', '2021-03-10 08:48:50'),
       ('title04', 'content4', '#456', 'PUBLIC', 2, 'Kamilah', 'Murial', '2021-05-30 23:53:49', '2021-03-10 08:48:50'),
       ('title84', 'content4', '#456', 'PUBLIC', 3, 'Kamilah', 'Murial', '2021-05-30 23:53:48', '2021-03-10 08:48:50'),
       ('title74', 'content4', '#456', 'PUBLIC', 4, 'Kamilah', 'Murial', '2021-05-30 23:53:47', '2021-03-10 08:48:50'),
       ('title64', 'content4', '#456', 'PUBLIC', 5, 'Kamilah', 'Murial', '2021-05-30 23:53:46', '2021-03-10 08:48:50'),
       ('title54', 'content4', '#456', 'PUBLIC', 5, 'Kamilah', 'Murial', '2021-05-30 23:53:45', '2021-03-10 08:48:50'),
       ('title44', 'content4', '#456', 'PUBLIC', 4, 'Kamilah', 'Murial', '2021-05-30 23:53:44', '2021-03-10 08:48:50'),
       ('title34', 'content4', '#456', 'PUBLIC', 3, 'Kamilah', 'Murial', '2021-05-30 23:53:43', '2021-03-10 08:48:50'),
       ('title24', 'content4', '#456', 'PUBLIC', 1, 'Kamilah', 'Murial', '2021-05-30 23:53:42', '2021-03-10 08:48:50'),
       ('title14', 'content4', '#456', 'PUBLIC', 1, 'Kamilah', 'Murial', '2021-05-30 23:53:41', '2021-03-10 08:48:50')
;

INSERT INTO article_comment (article_id, content, created_at, modified_at, created_by, modified_by)
VALUES (1, 'content1', '2021-03-02 22:40:04', '2021-04-27 15:38:09', 'Lind', 'Orv'),
       (1, 'content2', '2021-03-02 22:40:04', '2021-04-27 15:38:09', 'Lind', 'Orv'),
       (1, 'content3', '2021-03-02 22:40:04', '2021-04-27 15:38:09', 'Lind', 'Orv'),
       (2, 'content4', '2021-03-02 22:40:04', '2021-04-27 15:38:09', 'Lind', 'Orv'),
       (3, 'content5', '2021-03-02 22:40:04', '2021-04-27 15:38:09', 'Lind', 'Orv'),
       (2, 'content6', '2021-03-02 22:40:04', '2021-04-27 15:38:09', 'Lind', 'Orv'),
       (3, 'content7', '2021-03-02 22:40:04', '2021-04-27 15:38:09', 'Lind', 'Orv'),
       (4, 'content8', '2021-03-02 22:40:04', '2021-04-27 15:38:09', 'Lind', 'Orv'),
       (4, 'content9', '2021-03-02 22:40:04', '2021-04-27 15:38:09', 'Lind', 'Orv');