--SQL query should find if any of the groups has less than 10 students.

SELECT  groups.name, COUNT (students.group_id)
	FROM students
	INNER JOIN groups
	ON students.group_id = groups.group_id
	GROUP BY groups.name
	HAVING
		COUNT(students.group_id) < 10;
		
--SQL query should delete all students from group with name "SR-01"

DELETE FROM students 
	USING groups
	WHERE students.group_id = groups.group_id AND groups.name = 1;
	
--SQL query that finds name of course and related students.
	
SELECT students.first_name, students.last_name, groups.name as group, courses.name as course
	FROM students
	INNER JOIN groups
	ON students.group_id = groups.group_id
	INNER JOIN courses_students AS cs
	ON students.student_id = cs.student_id
	INNER JOIN courses 
	ON courses.course_id = cs.course_id
	WHERE courses.name LIKE 'Java Fun%';
	
