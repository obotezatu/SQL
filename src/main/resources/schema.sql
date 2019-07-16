CREATE TABLE courses(
    course_id serial PRIMARY KEY NOT NULL,
    course_name varchar(150) UNIQUE NOT NULL,
    course_description varchar(500)
);

CREATE TABLE groups(
    group_id serial PRIMARY KEY NOT NULL,
    group_name varchar(150) UNIQUE NOT NULL
);

CREATE TABLE students(
    student_id serial PRIMARY KEY NOT NULL,
    group_id integer  NOT NULL,
    first_name varchar(150)NOT NULL,
    last_name varchar(150) NOT NULL,
    CONSTRAINT students_group_id_fkey FOREIGN KEY (group_id)
        REFERENCES public.groups (group_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE courses_students(
    course_id integer  NOT NULL,
    student_id integer NOT NULL,
    UNIQUE (course_id, student_id),
    CONSTRAINT courses_students_course_id FOREIGN KEY (course_id)
        REFERENCES public.courses (course_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT courses_students_student_id FOREIGN KEY (student_id)
        REFERENCES public.students (student_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)

