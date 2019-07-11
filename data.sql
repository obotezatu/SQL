-- INSERT groups
INSERT INTO public.groups(name) VALUES ('SR-01');
INSERT INTO public.groups(name) VALUES ('IT-01');
INSERT INTO public.groups(name) VALUES ('OP-03');

--INSERT courses
INSERT INTO public.courses(name, description) VALUES ('Java Fundamentals', 'This Java Fundamentals training course covers the basics of programming in Java.');
INSERT INTO public.courses(name, description) VALUES ('Java Advanced', 'It discusses advanced topics, including object creation,\nconcurrency, serialization, reflection and many more. It will guide you through your journey to Java mastery!');
INSERT INTO public.courses(name, description) VALUES ('Python Fundamental', 'Welcome to an introduction to Python and Programming.');
INSERT INTO public.courses(name, description) VALUES ('JavaScript Fundamental', 'Welcome to an introduction to JavaScript.');

--INSERT students
INSERT INTO public.students(group_id, first_name, last_name) VALUES (1, 'Igor', 'Krutoi');
INSERT INTO public.students(group_id, first_name, last_name) VALUES (1, 'Bon', 'Jovi');
INSERT INTO public.students(group_id, first_name, last_name) VALUES (1, 'Jhon', 'Doe');
INSERT INTO public.students(group_id, first_name, last_name) VALUES (1, 'Igor', 'Ivanov');
INSERT INTO public.students(group_id, first_name, last_name) VALUES (1, 'Sidor', 'Sidorov');
INSERT INTO public.students(group_id, first_name, last_name) VALUES (1, 'Piotr', 'Petrov');
INSERT INTO public.students(group_id, first_name, last_name) VALUES (1, 'Ivan', 'Ivanov');
INSERT INTO public.students(group_id, first_name, last_name) VALUES (1, 'Dan', 'Danilov');
INSERT INTO public.students(group_id, first_name, last_name) VALUES (1, 'Vladimir', 'Vladimirov');
INSERT INTO public.students(group_id, first_name, last_name) VALUES (1, 'Dmitriy', 'Dmitrov');
INSERT INTO public.students(group_id, first_name, last_name) VALUES (1, 'Vasilii', 'Vasilyev');
INSERT INTO public.students(group_id, first_name, last_name) VALUES (2, 'Paul', 'McCartney');
INSERT INTO public.students(group_id, first_name, last_name) VALUES (2, 'Ala', 'Pugachyova');
INSERT INTO public.students(group_id, first_name, last_name) VALUES (3, 'Alexandr', 'Socolov');
INSERT INTO public.students(group_id, first_name, last_name) VALUES (3, 'Evgenii', 'Petuhov');
INSERT INTO public.students(group_id, first_name, last_name) VALUES (3, 'Vadim', 'Kurochkin');

--INSERT groups_students
INSERT INTO public.courses_students(course_id, student_id)VALUES (2, 1);
INSERT INTO public.courses_students(course_id, student_id)VALUES (1, 2);
INSERT INTO public.courses_students(course_id, student_id)VALUES (4, 2);
INSERT INTO public.courses_students(course_id, student_id)VALUES (3, 3);
INSERT INTO public.courses_students(course_id, student_id)VALUES (4, 3);
INSERT INTO public.courses_students(course_id, student_id)VALUES (3, 4);
INSERT INTO public.courses_students(course_id, student_id)VALUES (4, 5);
INSERT INTO public.courses_students(course_id, student_id)VALUES (1, 5);
INSERT INTO public.courses_students(course_id, student_id)VALUES (3, 5);
INSERT INTO public.courses_students(course_id, student_id)VALUES (3, 6);
INSERT INTO public.courses_students(course_id, student_id)VALUES (1, 6);
INSERT INTO public.courses_students(course_id, student_id)VALUES (2, 7);
INSERT INTO public.courses_students(course_id, student_id)VALUES (3, 8);
INSERT INTO public.courses_students(course_id, student_id)VALUES (3, 9);
INSERT INTO public.courses_students(course_id, student_id)VALUES (1, 10);
INSERT INTO public.courses_students(course_id, student_id)VALUES (2, 10);
INSERT INTO public.courses_students(course_id, student_id)VALUES (3, 11);
INSERT INTO public.courses_students(course_id, student_id)VALUES (4, 11);
INSERT INTO public.courses_students(course_id, student_id)VALUES (1, 12);
INSERT INTO public.courses_students(course_id, student_id)VALUES (3, 12);
INSERT INTO public.courses_students(course_id, student_id)VALUES (3, 13);
INSERT INTO public.courses_students(course_id, student_id)VALUES (2, 13);
INSERT INTO public.courses_students(course_id, student_id)VALUES (4, 13);
INSERT INTO public.courses_students(course_id, student_id)VALUES (3, 14);
INSERT INTO public.courses_students(course_id, student_id)VALUES (1, 15);
INSERT INTO public.courses_students(course_id, student_id)VALUES (2, 15);
INSERT INTO public.courses_students(course_id, student_id)VALUES (4, 16);
INSERT INTO public.courses_students(course_id, student_id)VALUES (3, 16);