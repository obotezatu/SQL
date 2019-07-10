--SQL query should find if any of the groups has less than 10 students.

SELECT  group_id, COUNT (group_id)
	FROM public.students
	GROUP BY group_id
	HAVING
		COUNT(group_id) < 10;
		
--SQL query should delete all students from group with name "SR-01"

DELETE FROM students 
	USING groups
	WHERE students.group_id = groups.group_id AND groups.name LIKE 'SR-01'	;
	
--SQL query that finds name of course and related students.
	
SELECT students.student_id, students.group_id, students.first_name, students.last_name, courses.name
	FROM students
	inner join courses_students as cs
	ON students.student_id = cs.student_id
	inner join courses 
	ON courses.course_id = cs.course_id
	where courses.name LIKE 'Java%';
	
