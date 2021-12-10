--Delete users table-- 
DROP TABLE IF EXISTS users;

---Create Users Table-
CREATE TABLE users (
	user_id SERIAL PRIMARY KEY,
	first_name VARCHAR(50) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	username VARCHAR(50) NOT NULL,
	password VARCHAR(20) NOT NULL,
	user_role VARCHAR(50) NOT NULL
);

--INSERT USERS--

INSERT INTO users (
	first_name, last_name, username, password, user_role
	)
VALUES
	('Mina', 'Jackson', 'mjackson','smile123','finmanager'),
	('Apollo', 'Deeds', 'adeeds45','pswd222','employee'),
	('Macy', 'Gray', 'mgray2021','mypassword', 'employee');

--View Users of Users Table--
	
SELECT *
FROM users;

--Select paricular User--

SELECT *
FROM users
WHERE username = 'mjackson'
AND password = 'smile123';

--Delete Assignments Table--

DROP TABLE IF EXISTS reimtickets;

--Create ReimTickets Table--

CREATE TABLE reimtickets (
 reimticket_id SERIAL PRIMARY KEY,
 reimticket_type VARCHAR(50) NOT NULL,
 reimticket_image BYTEA,
 reimamount INTEGER,
 resolver_id INTEGER, 
 reimauthor_id INTEGER NOT NULL,
 CONSTRAINT resolver_fk FOREIGN KEY (resolver_id) REFERENCES users(user_id),
 CONSTRAINT reimauthor_fk FOREIGN KEY (reimauthor_id) REFERENCES users(user_id)
 
 );
--Update reimtickets--

UPDATE reimtickets
SET 
reimamount = 100,
resolver_id = 1
WHERE reimticket_id = 1;

--INSERT reimtickets--

INSERT INTO reimtickets (
	reimticket_type,
	reimauthor_id	
)
VALUES
	('Lodging', 2),
	('Travel', 3),
	('Food', 2),
	('Other', 3);

--View Reimtickets Table --

SELECT *
FROM reimtickets;

--Select certain Employee's Riemtickets--

SELECT *
FROM reimtickets
WHERE reimauthor_id = 2;






