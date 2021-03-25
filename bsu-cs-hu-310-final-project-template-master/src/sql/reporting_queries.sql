/* Put your final project reporting queries here */
USE cs_hu_310_final_project;

-- 1. Calculate the GPA for a student given a student_id (ussing student_id=1)
 SELECT 
	students.first_name, students.last_name, count(class_registrations.class_section_id) as number_of_classes,
   sum(convert_to_grade_point(grades.letter_grade)) as total_grade_points_earned, avg(convert_to_grade_point(grades.letter_grade)) as GPA
   from class_registrations
	join students on students.student_id = class_registrations.student_id
	join grades on grades.grade_id = class_registrations.grade_id
   where students.student_id = 1
   group by students.student_id;
                                                                                                             
-- 2. Calculate the GPA for each student (across all classes and all terms)
     SELECT 
      students.first_name, students.last_name, count(class_registrations.class_section_id) as number_of_classes,
      sum(convert_to_grade_point(grades.letter_grade)) as total_grade_points_earned, 
      avg(convert_to_grade_point(grades.letter_grade)) as GPA
      from class_registrations
      join students on students.student_id = class_registrations.student_id
      join grades on grades.grade_id = class_registrations.grade_id
      group by students.student_id;
                                                                           
 -- 3. Calculate the avg GPA for each class
 SELECT 
 classes.code, classes.name, count(class_registrations.class_section_id) as number_of_grades,
 sum(convert_to_grade_point(grades.letter_grade)) as total_grade_points, 
 avg(convert_to_grade_point(grades.letter_grade)) as AVG_GPA
 from class_sections
 right join class_registrations on class_sections.class_section_id = class_registrations.class_section_id
 join grades on class_registrations.grade_id = grades.grade_id
 join classes on class_sections.class_id = classes.class_id
 group by classes.class_id;
                            
-- 4. Calculate the avg GPA for each class and term
    SELECT 
    classes.code, classes.name, terms.name as term,
    count(class_registrations.class_section_id) as number_of_grades,
    sum(convert_to_grade_point(grades.letter_grade)) as total_grade_points,
    avg(convert_to_grade_point(grades.letter_grade)) as AVG_GPA
    from classes
    right join class_sections on class_sections.class_id = classes.class_id
    left join class_registrations on class_registrations.class_section_id = class_sections.class_section_id 
    left join grades on grades.grade_id = class_registrations.grade_id
    left join terms on class_sections.term_id = terms.term_id
    group by class_sections.class_section_id
    order by AVG_GPA DESC;
                               
-- 5. List all the classes being taught by an instructor (use instructor_id=1)
SELECT
	instructors.first_name, instructors.last_name, academic_titles.title, 
    classes.code, classes.name as class_name, terms.name as term
from class_sections
right join instructors on instructors.instructor_id = class_sections.instructor_id
right join classes on classes.class_id = class_sections.class_id
join academic_titles on academic_titles.academic_title_id = instructors.academic_title_id
right join terms on terms.term_id = class_sections.term_id
where instructors.instructor_id = 1;
                               
-- 6. List all classes with terms & instructor
select
	classes.code, classes.name, terms.name as term, instructors.first_name,
    instructors.last_name
from class_sections
right join instructors on instructors.instructor_id = class_sections.instructor_id
right join classes on classes.class_id = class_sections.class_id
join academic_titles on academic_titles.academic_title_id = instructors.academic_title_id
right join terms on terms.term_id = class_sections.term_id
order by classes.code
                          
-- 7. Calculate the remaining space left in a class
select 
	classes.code, classes.name, terms.name as term, 
    count(class_registrations.class_section_id) as enrolled_students, 
    classes.maximum_students - count(class_registrations.class_section_id) as space_remaining
    from class_sections
join classes on classes.class_id = class_sections.class_id
join terms on terms.term_id = class_sections.term_id
join class_registrations on class_registrations.class_section_id = class_sections.class_section_id
group by class_registrations.class_section_id;
                                                                                                    

                                                                                                              
                                                                                                              
