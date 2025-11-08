package com.courseallocation.course_allocation.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.courseallocation.course_allocation.dto.CourseResponse;
import com.courseallocation.course_allocation.dto.EnrollmentResponse;
import com.courseallocation.course_allocation.dto.StudentCourseSummary;
import com.courseallocation.course_allocation.model.Course;
import com.courseallocation.course_allocation.model.Enrollment;
import com.courseallocation.course_allocation.model.Student;
import com.courseallocation.course_allocation.repository.CourseRepository;
import com.courseallocation.course_allocation.repository.EnrollmentRepository;
import com.courseallocation.course_allocation.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseSelectionService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final CourseService courseService;

    // Business rules: Maximum limits per semester
    private static final int MAX_CREDITS_PER_SEMESTER = 21;
    private static final int MAX_COURSES_PER_SEMESTER = 7;

    public List<CourseResponse> getAvailableCoursesForStudent(Long studentId, Long semesterId) {
        // Fetch student details
        Student currentStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

        return courseService.getAvailableCoursesForStudent(
                currentStudent.getDepartment(),
                currentStudent.getYear(),
                semesterId
        );
    }

    public EnrollmentResponse selectCourse(Long studentId, Long courseId) {
        // Validate student and course existence
        Student currentStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

        Course selectedCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));

        // Validate department match
        if (!selectedCourse.getDepartment().equals(currentStudent.getDepartment())) {
            throw new RuntimeException("Course is not available for your department");
        }

        // Validate year level match
        if (!selectedCourse.getLevel().equals(currentStudent.getYear())) {
            throw new RuntimeException("Course is not available for your year level");
        }

        // Check for duplicate enrollment
        if (enrollmentRepository.existsByStudentAndCourse(currentStudent, selectedCourse)) {
            throw new RuntimeException("You are already enrolled in this course");
        }

        // Check course capacity
        if (selectedCourse.getCurrentEnrollment() >= selectedCourse.getMaxCapacity()) {
            throw new RuntimeException("Course is full. Cannot enroll more students");
        }

        // Check credit and course limits
        List<Enrollment> existingEnrollments = enrollmentRepository.findByStudentId(studentId);
        int accumulatedCredits = existingEnrollments.stream()
                .mapToInt(e -> e.getCourse().getCredits())
                .sum();

        if (accumulatedCredits + selectedCourse.getCredits() > MAX_CREDITS_PER_SEMESTER) {
            throw new RuntimeException("Credit limit exceeded. Maximum " + MAX_CREDITS_PER_SEMESTER + 
                    " credits allowed per semester. You currently have " + accumulatedCredits + " credits");
        }

        if (existingEnrollments.size() >= MAX_COURSES_PER_SEMESTER) {
            throw new RuntimeException("Course limit exceeded. Maximum " + MAX_COURSES_PER_SEMESTER + 
                    " courses allowed per semester");
        }

        // Create new enrollment
        Enrollment newEnrollment = new Enrollment();
        newEnrollment.setStudent(currentStudent);
        newEnrollment.setCourse(selectedCourse);
        newEnrollment.setStatus("ENROLLED");

        Enrollment savedEnrollment = enrollmentRepository.save(newEnrollment);

        // Update course enrollment count
        selectedCourse.setCurrentEnrollment(selectedCourse.getCurrentEnrollment() + 1);
        courseRepository.save(selectedCourse);

        return mapToResponse(savedEnrollment);
    }

    public void dropCourse(Long studentId, Long enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found with id: " + enrollmentId));

        if (!enrollment.getStudent().getId().equals(studentId)) {
            throw new RuntimeException("You are not authorized to drop this course");
        }

        Course course = enrollment.getCourse();
        if (course.getCurrentEnrollment() > 0) {
            course.setCurrentEnrollment(course.getCurrentEnrollment() - 1);
            courseRepository.save(course);
        }

        enrollmentRepository.deleteById(enrollmentId);
    }

    public StudentCourseSummary getStudentCourseSummary(Long studentId, Long semesterId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        
        int totalCredits = enrollments.stream()
                .mapToInt(e -> e.getCourse().getCredits())
                .sum();

        List<EnrollmentResponse> enrolledCourses = enrollments.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return new StudentCourseSummary(
                student.getId(),
                student.getFirstName() + " " + student.getLastName(),
                student.getDepartment(),
                student.getYear(),
                enrollments.size(),
                totalCredits,
                MAX_CREDITS_PER_SEMESTER,
                MAX_COURSES_PER_SEMESTER,
                enrolledCourses
        );
    }

    private EnrollmentResponse mapToResponse(Enrollment enrollment) {
        return new EnrollmentResponse(
                enrollment.getId(),
                enrollment.getStudent().getId(),
                enrollment.getStudent().getFirstName() + " " + enrollment.getStudent().getLastName(),
                enrollment.getStudent().getStudentId(),
                enrollment.getCourse().getId(),
                enrollment.getCourse().getCourseCode(),
                enrollment.getCourse().getCourseName(),
                enrollment.getStatus(),
                enrollment.getCreatedAt(),
                enrollment.getUpdatedAt()
        );
    }
}

