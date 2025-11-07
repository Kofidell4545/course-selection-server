package com.courseallocation.course_allocation.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.courseallocation.course_allocation.model.Course;
import com.courseallocation.course_allocation.model.Semester;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCourseCode(String courseCode);
    List<Course> findByDepartment(String department);
    List<Course> findByInstructor(String instructor);
    List<Course> findBySemester(Semester semester);
    List<Course> findBySemesterId(Long semesterId);
    List<Course> findByCourseNameContainingIgnoreCase(String courseName);
    List<Course> findByDepartmentAndLevelAndSemesterId(String department, Integer level, Long semesterId);
    boolean existsByCourseCode(String courseCode);
}

