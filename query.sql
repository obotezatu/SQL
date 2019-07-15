--SQL query should find if any of the groups has less than 10 students.

	SELECT  groups.name, COUNT (students.group_id) AS count
	FROM groups
	LEFT JOIN students
	ON groups.group_id = students.group_id
	GROUP BY groups.name
	HAVING
		COUNT(students.group_id) < 10;
		
--SQL query should delete all students from group with name "SR-01"

DELETE FROM students 
	USING groups
	WHERE students.group_id = groups.group_id AND groups.name = 'SR-01';
	
--SQL query that finds name of course and related students.
	
SELECT courses.name as course, students.last_name, students.first_name
	FROM courses
	INNER JOIN courses_students as cs
	ON courses.course_id = cs.course_id
	INNER JOIN students
	ON cs.student_id = students.student_id
	ORDER BY course,students.last_name
	
