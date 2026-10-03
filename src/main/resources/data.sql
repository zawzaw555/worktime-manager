INSERT INTO common_login (
	user_id,
	password,
	role
) VALUES 
('admin','$2a$10$SRtXMWCwTwVFK0ZbKXAnWuxYl2QytRFbmDJ/iGdyfVqWDM.jjHase','ADMIN'),
('worker','$2a$10$1fADdXvbMtU2gx0nOzzEWexyw9x/zK9pMwGLwnA53MmQSZz.6GLv6','WORKER');

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
    '$2a$10$gf29fjgX/lg2IZL5iDRWPubik1n/aAKQCeYmcwX/7fcbO89.YMKIm'
),
(
    'worker02',
    '田中太郎',
    'tanaka@example.com',
    '$2a$10$gf29fjgX/lg2IZL5iDRWPubik1n/aAKQCeYmcwX/7fcbO89.YMKIm'
),
(
    'worker03',
    '佐藤太郎',
    'satou@example.com',
    '$2a$10$gf29fjgX/lg2IZL5iDRWPubik1n/aAKQCeYmcwX/7fcbO89.YMKIm'
);