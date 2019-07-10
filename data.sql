-- INSERT groups
INSERT INTO public.groups(
	group_id, name)
	VALUES ('SR_01', 'SR-01');

INSERT INTO public.groups(
	group_id, name)
	VALUES ('IT_01', 'IT-01');
	
INSERT INTO public.groups(
	group_id, name)
	VALUES ('OP_03', 'OP-03');
	
--INSERT courses
INSERT INTO public.courses(
	course_id, name, description)
	VALUES ('J-1','Java Fundamentals', 'This Java Fundamentals training course covers the basics of programming in Java.');

INSERT INTO public.courses(
	course_id, name, description)
	VALUES ('J-2','Java Advanced', 'It discusses advanced topics, including object creation,\nconcurrency, serialization, reflection and many more. It will guide you through your journey to Java mastery!');
	
INSERT INTO public.courses(
	course_id, name, description)
	VALUES ('P-1','Python Fundamental', 'Welcome to an introduction to Python and Programming.');
	
INSERT INTO public.courses(
	course_id, name, description)
	VALUES ('JS-1','JavaScript Fundamental', 'Welcome to an introduction to JavaScript.');

--INSERT students
INSERT INTO public.students(
	student_id, group_id, first_name, last_name)
	VALUES ('00003', 'SR_01', 'Igor', 'Krutoi');
INSERT INTO public.courses_students(
	course_id, student_id)
	VALUES ('J-2', '00003');

INSERT INTO public.students(
	student_id, group_id, first_name, last_name)
	VALUES ('00002', 'SR_01', 'Bon', 'Jovi');
	INSERT INTO public.courses_students(
	course_id, student_id)
	VALUES ('J-1', '00002');
	INSERT INTO public.courses_students(
	course_id, student_id)
	VALUES ('JS-1', '00002');
	
INSERT INTO public.students(
	student_id, group_id, first_name, last_name)
	VALUES ('00001', 'SR_01', 'Jhon', 'Doe');
	INSERT INTO public.courses_students(
	course_id, student_id)
	VALUES ('P-1', '00001');
	INSERT INTO public.courses_students(
	course_id, student_id)
	VALUES ('JS-1', '00001');
	
INSERT INTO public.students(
	student_id, group_id, first_name, last_name)
	VALUES ('00006', 'SR_01', 'Igor', 'Ivanov');
	INSERT INTO public.courses_students(
	course_id, student_id)
	VALUES ('P-1', '00006');
	
INSERT INTO public.students(
	student_id, group_id, first_name, last_name)
		VALUES ('00007', 'SR_01', 'Sidor', 'Sidorov');
	INSERT INTO public.courses_students(
	course_id, student_id)
		VALUES ('JS-1', '00007');
	INSERT INTO public.courses_students(
	course_id, student_id)
		VALUES ('J-1', '00007');
	INSERT INTO public.courses_students(
	course_id, student_id)
		VALUES ('P-1', '00007');

INSERT INTO public.students(
	student_id, group_id, first_name, last_name)
	VALUES ('00009', 'SR_01', 'Piotr', 'Petrov');
	INSERT INTO public.courses_students(
	course_id, student_id)
	VALUES ('P-1', '00009');
	INSERT INTO public.courses_students(
	course_id, student_id)
	VALUES ('J-1', '00009');
	
INSERT INTO public.students(
	student_id, group_id, first_name, last_name)
	VALUES ('00010', 'SR_01', 'Ivan', 'Ivanov');
INSERT INTO public.courses_students(
	course_id, student_id)
	VALUES ('J-2', '00010');
	
INSERT INTO public.students(
	student_id, group_id, first_name, last_name)
	VALUES ('00011', 'SR_01', 'Dan', 'Danilov');
	INSERT INTO public.courses_students(
	course_id, student_id)
	VALUES ('P-1', '00011');

INSERT INTO public.students(
	student_id, group_id, first_name, last_name)
	VALUES ('00012', 'SR_01', 'Vladimir', 'Vladimirov');
	INSERT INTO public.courses_students(
	course_id, student_id)
	VALUES ('P-1', '00012');
	
INSERT INTO public.students(
	student_id, group_id, first_name, last_name)
	VALUES ('00013', 'SR_01', 'Dmitriy', 'Dmitrov');
	INSERT INTO public.courses_students(
	course_id, student_id)
	VALUES ('J-1', '00013');
	INSERT INTO public.courses_students(
	course_id, student_id)
	VALUES ('J-2', '00013');
	
INSERT INTO public.students(
	student_id, group_id, first_name, last_name)
	VALUES ('00014', 'SR_01', 'Vasilii', 'Vasilyev');
	INSERT INTO public.courses_students(
	course_id, student_id)
	VALUES ('P-1', '00014');
	INSERT INTO public.courses_students(
	course_id, student_id)
	VALUES ('JS-1', '00014');
	
INSERT INTO public.students(
	student_id, group_id, first_name, last_name)
	VALUES ('00004', 'IT_01', 'Paul', 'McCartney');
	INSERT INTO public.courses_students(
	course_id, student_id)
	VALUES ('J-1', '00004');
	INSERT INTO public.courses_students(
	course_id, student_id)
	VALUES ('P-1', '00004');

INSERT INTO public.students(
	student_id, group_id, first_name, last_name)
	VALUES ('00005', 'IT_01', 'Ala', 'Pugachyova');
	INSERT INTO public.courses_students(
	course_id, student_id)
	VALUES ('P-1', '00005');
	INSERT INTO public.courses_students(
	course_id, student_id)
	VALUES ('J-2', '00005');
	INSERT INTO public.courses_students(
	course_id, student_id)
	VALUES ('JS-1', '00005');
	
INSERT INTO public.students(
	student_id, group_id, first_name, last_name)
	VALUES ('00015', 'OP_03', 'Alexandr', 'Socolov');
	INSERT INTO public.courses_students(
	course_id, student_id)
	VALUES ('P-1', '00015');

INSERT INTO public.students(
	student_id, group_id, first_name, last_name)
	VALUES ('00016', 'OP_03', 'Evgenii', 'Petuhov');
	INSERT INTO public.courses_students(
	course_id, student_id)
	VALUES ('J-1', '00016');
	INSERT INTO public.courses_students(
	course_id, student_id)
	VALUES ('J-2', '00016');

INSERT INTO public.students(
	student_id, group_id, first_name, last_name)
	VALUES ('00017', 'OP_03', 'Vadim', 'Kurochkin');
	INSERT INTO public.courses_students(
	course_id, student_id)
	VALUES ('JS-1', '00017');
	INSERT INTO public.courses_students(
	course_id, student_id)
	VALUES ('P-1', '00017');