INSERT INTO worker (
    employee_no,
    name,
    email,
    password,
    role
) VALUES (
    'EMP001',
    '山田太郎',
    'yamada@example.com',
    'password',
    'USER'
);

INSERT INTO work_time (
    worker_id,
    check_date,
    check_in,
    check_out
) VALUES (
    1,
    '2026-07-30',
    '2026-07-30 09:00:00',
    '2026-07-30 18:00:00'
);