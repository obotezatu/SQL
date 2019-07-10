-- \i 'D:/Books/java/Java 2018 USM/Tasks/SQL/schema.sql'

CREATE DATABASE fox
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

\c fox

CREATE TABLE courses
(
    course_id varchar(50) PRIMARY KEY NOT NULL,
    name varchar(150) UNIQUE NOT NULL,
    description varchar(500)
);

CREATE TABLE groups
(
    group_id varchar(50) PRIMARY KEY NOT NULL,
    name varchar(150) UNIQUE NOT NULL
);

CREATE TABLE students
(
    student_id varchar(50) PRIMARY KEY NOT NULL,
    group_id varchar(50)  NOT NULL,
    first_name varchar(150)NOT NULL,
    last_name varchar(150) NOT NULL,
    CONSTRAINT students_group_id_fkey FOREIGN KEY (group_id)
        REFERENCES public.groups (group_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE courses_students
(
    course_id varchar(50)  NOT NULL,
    student_id varchar(50) NOT NULL,
    CONSTRAINT courses_students_pkey PRIMARY KEY (course_id, student_id),
    CONSTRAINT courses_students_course_id FOREIGN KEY (course_id)
        REFERENCES public.courses (course_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT courses_students_student_id FOREIGN KEY (student_id)
        REFERENCES public.students (student_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)