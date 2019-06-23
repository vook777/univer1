CREATE TABLE students 
(
	id serial primary key,
	student_card_number VARCHAR (200),
	first_name VARCHAR (200),
	last_name VARCHAR (200),
	group_id int
);

CREATE TABLE auditoriums 
(
	id serial primary key,
	name VARCHAR (200),
	capacity int
);

CREATE TABLE courses 
(
	id serial primary key,
	name VARCHAR (200),
	number_of_weeks int,
	description text
);

CREATE TABLE teachers 
(
	id serial primary key,
	first_name VARCHAR (200),
	last_name VARCHAR (200),
	faculty_id int
);

CREATE TABLE groups 
(
	id serial primary key,
	group_name VARCHAR (200),
	faculty_id int
);

CREATE TABLE faculties 
(
	id serial primary key,
	faculty_name VARCHAR (200),
);

CREATE TABLE lectures 
(
	id serial primary key,
	course_id int,
	auditorium_id int,
	teacher_id int,
	group_id int,
	time time
);
