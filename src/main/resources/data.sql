INSERT INTO common_login (
	user_id,
	password,
	role
) VALUES 
('admin','admin123','ADMIN'),
('worker','worker123','WORKER');

INSERT INTO worker (
    worker_no,
    name,
    email,
    password
) VALUES 
(
    'worker01',
    '山田太郎',
    'yamada@example.com',
    'password'
),
(
    'worker02',
    '田中太郎',
    'tanaka@example.com',
    'password'
),
(
    'worker03',
    '佐藤太郎',
    'satou@example.com',
    'password'
);