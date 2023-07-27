insert into user_tb
(id, email, password, username, role, hire_date, profile_image, created_date, updated_date)
values (1, 'test1@test.com', '$2a$10$fIlcyUbp4JxhQa2xUQIh5emZpfbycmqTAkN4im4df7Kj4bzxzKDmm', 'ceo', 'USER', date('2019-03-17'), '/image/default.png', date('2023-03-17'),
        date('2023-03-17')),
       (2, 'test2@test.com', '$2a$10$fIlcyUbp4JxhQa2xUQIh5emZpfbycmqTAkN4im4df7Kj4bzxzKDmm', 'cfo', 'USER', date('2019-03-17'), '/image/default.png', date('2023-03-17'),
        date('2023-03-17')),
       (3, 'test3@test.com', '$2a$10$fIlcyUbp4JxhQa2xUQIh5emZpfbycmqTAkN4im4df7Kj4bzxzKDmm', 'cto', 'USER', date('2023-03-17'), '/image/default.png', date('2023-03-17'),
        date('2023-03-17')),
       (4, 'test4@test.com', '$2a$10$fIlcyUbp4JxhQa2xUQIh5emZpfbycmqTAkN4im4df7Kj4bzxzKDmm', 'manager', 'USER', date('2023-03-17'), '/image/default.png',
        date('2023-03-17'), date('2023-03-17')),
       (5, 'test5@test.com', '$2a$10$fIlcyUbp4JxhQa2xUQIh5emZpfbycmqTAkN4im4df7Kj4bzxzKDmm', 'senior', 'USER', date('2023-03-17'), '/image/default.png', date('2023-03-17'),
        date('2023-03-17')),
       (6, 'test6@test.com', '$2a$10$fIlcyUbp4JxhQa2xUQIh5emZpfbycmqTAkN4im4df7Kj4bzxzKDmm', 'junior', 'USER', date('2023-03-17'), '/image/default.png', date('2023-03-17'),
        date('2023-03-17'));

insert into vacation_tb
(id, user_id, created_date, start_date, end_date, reason, status, approval_date)
values (1, 1, date('2023-07-27'), date('2023-07-27'), date('2023-07-27'), '휴가', 'READY', null),
       (2, 2, date('2023-07-27'), date('2023-08-01'), date('2023-08-01'), '병가', 'READY', null),
       (3, 3, date('2023-07-27'), date('2023-08-06'), date('2023-08-06'), '휴가', 'READY', null),
       (4, 4, date('2023-07-27'), date('2023-08-08'), date('2023-08-08'), '휴가', 'READY', null),
       (5, 5, date('2023-07-27'), date('2023-07-30'), date('2023-07-30'), '병가', 'READY', null),
       (6, 6, date('2023-07-27'), date('2023-08-07'), date('2023-08-07'), '휴가', 'READY', null);

insert into vacation_info_tb
(id, user_id, remain_vacation, used_vacation)
values (1, 1, 5, 2),
       (2, 2, 6, 3),
       (3, 3, 7, 4),
       (4, 4, 8, 5),
       (5, 5, 9, 6),
       (6, 6, 10, 7);

insert into on_duty_tb
(id, user_id, created_date, updated_date, duty_date, status, approval_date)
values (1, 1, date('2023-07-27'), date('2023-07-27'), date('2023-07-27'), 'PENDING', null),
       (2, 2, date('2023-07-27'), date('2023-08-01'), date('2023-08-01'), 'PENDING', null),
       (3, 3, date('2023-07-27'), date('2023-08-06'), date('2023-08-06'), 'PENDING', null),
       (4, 4, date('2023-07-27'), date('2023-08-08'), date('2023-08-08'), 'PENDING', null),
       (5, 5, date('2023-07-27'), date('2023-07-30'), date('2023-07-30'), 'PENDING', null),
       (6, 6, date('2023-07-27'), date('2023-08-07'), date('2023-08-07'), 'PENDING', null);

insert into log_tb
(id, request_ip, sign_in_date, user_id)
values (1, '127.0.0.1', date('2023-07-26'), 1),
       (2, '127.0.0.1', date('2023-07-27'), 1),
       (3, '127.0.0.1', date('2023-07-28'), 1),
       (4, '127.0.0.2', date('2023-07-26'), 2),
       (5, '127.0.0.3', date('2023-07-27'), 3),
       (6, '127.0.0.4', date('2023-07-27'), 4);

insert into sign_up_tb
(id, email, password, username, hire_date, created_date)
values (1, 'test1@test.com', 'test1234', 'ceo', date('2023-07-27'), date('2023-07-27')),
       (2, 'test2@test.com', 'test1234', 'cfo', date('2023-08-01'), date('2023-08-01')),
       (3, 'test3@test.com', 'test1234', 'cto', date('2023-08-06'), date('2023-08-06')),
       (4, 'test4@test.com', 'test1234', 'manager', date('2023-08-08'), date('2023-08-08')),
       (5, 'test5@test.com', 'test1234', 'senior', date('2023-07-30'), date('2023-07-30')),
       (6, 'test6@test.com', 'test1234', 'junior', date('2023-08-07'), date('2023-08-07'));
