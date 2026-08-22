DROP TABLE IF EXISTS break_time;
DROP TABLE IF EXISTS work_time;
DROP TABLE IF EXISTS worker;
DROP TABLE IF EXISTS common_login;

CREATE TABLE common_login (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	user_id VARCHAR(50) NOT NULL UNIQUE,
	password VARCHAR(255) NOT NULL,
	role varchar(20) NOT NULL
);

CREATE TABLE worker (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	worker_no VARCHAR(20) NOT NULL UNIQUE,
	name VARCHAR(100) NOT NULL,
	email VARCHAR(255) NOT NULL UNIQUE,
	password VARCHAR(255) NOT NULL
);

CREATE TABLE work_time (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	worker_id BIGINT NOT NULL,
	check_date DATE NOT NULL,
	check_in DATETIME,
	check_out DATETIME,
	
	CONSTRAINT fk_work_time_worker
		FOREIGN KEY (worker_id)
		REFERENCES worker(id)
);

CREATE TABLE break_time (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	work_time_id BIGINT NOT NULL,
	break_start DATETIME NOT NULL,
	break_end DATETIME NULL,
	
	CONSTRAINT fk_break_work_time
		FOREIGN KEY (work_time_id)
		REFERENCES work_time(id)
);
	
