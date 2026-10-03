DROP TABLE IF EXISTS work_time;
DROP TABLE IF EXISTS worker;

CREATE TABLE worker (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	employee_no VARCHAR(20) NOT NULL UNIQUE,
	name VARCHAR(100) NOT NULL,
	email VARCHAR(255) NOT NULL UNIQUE,
	password VARCHAR(255) NOT NULL,
	role VARCHAR(20) NOT NULL
);

CREATE TABLE work_time (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	worker_id BIGINT NOT NULL,
	check_date DATE NOT NULL,
	check_in DATETIME,
	check_out DATETIME,
	/* break_minutes INT NOT NULL DEFAULT 60 */
	
	CONSTRAINT fk_work_time_worker
		FOREIGN KEY (worker_id)
		REFERENCES worker(id),
		
	CONSTRAINT uq_worker_check_date
		UNIQUE (worker_id, check_date)
);
	