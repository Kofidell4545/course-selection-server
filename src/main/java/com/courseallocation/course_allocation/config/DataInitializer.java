package com.courseallocation.course_allocation.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.courseallocation.course_allocation.model.Course;
import com.courseallocation.course_allocation.model.Semester;
import com.courseallocation.course_allocation.model.Student;
import com.courseallocation.course_allocation.repository.CourseRepository;
import com.courseallocation.course_allocation.repository.SemesterRepository;
import com.courseallocation.course_allocation.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final SemesterRepository semesterRepository;
    private final CourseRepository courseRepository;

    @Override
    public void run(String... args) throws Exception {
        if (semesterRepository.count() == 0) {
            seedData();
        }
    }

    private void seedData() {
        // Initialize Fall 2024 semester
        Semester fallSemester = new Semester();
        fallSemester.setSemesterCode("FALL2024");
        fallSemester.setName("Fall 2024");
        fallSemester.setStartDate(LocalDate.of(2024, 9, 1));
        fallSemester.setEndDate(LocalDate.of(2024, 12, 15));
        fallSemester.setIsActive(true);
        fallSemester = semesterRepository.save(fallSemester);

        // Create sample students
        Student kwameAsante = new Student();
        kwameAsante.setStudentId("STU001");
        kwameAsante.setFirstName("Kwame");
        kwameAsante.setLastName("Asante");
        kwameAsante.setEmail("kwame.asante@university.edu");
        kwameAsante.setDepartment("Computer Science");
        kwameAsante.setYear(2);
        kwameAsante.setPin("1234");
        studentRepository.save(kwameAsante);

        Student amaMensah = new Student();
        amaMensah.setStudentId("STU002");
        amaMensah.setFirstName("Ama");
        amaMensah.setLastName("Mensah");
        amaMensah.setEmail("ama.mensah@university.edu");
        amaMensah.setDepartment("Computer Science");
        amaMensah.setYear(3);
        amaMensah.setPin("5678");
        studentRepository.save(amaMensah);

        Student kofiOsei = new Student();
        kofiOsei.setStudentId("STU003");
        kofiOsei.setFirstName("Kofi");
        kofiOsei.setLastName("Osei");
        kofiOsei.setEmail("kofi.osei@university.edu");
        kofiOsei.setDepartment("Mathematics");
        kofiOsei.setYear(1);
        kofiOsei.setPin("9012");
        studentRepository.save(kofiOsei);

        Student akosuaBoateng = new Student();
        akosuaBoateng.setStudentId("STU004");
        akosuaBoateng.setFirstName("Akosua");
        akosuaBoateng.setLastName("Boateng");
        akosuaBoateng.setEmail("akosua.boateng@university.edu");
        akosuaBoateng.setDepartment("Computer Science");
        akosuaBoateng.setYear(2);
        akosuaBoateng.setPin("3456");
        studentRepository.save(akosuaBoateng);

        // Create sample courses
        Course dataStructures = new Course();
        dataStructures.setCourseCode("CS201");
        dataStructures.setCourseName("Data Structures and Algorithms");
        dataStructures.setDepartment("Computer Science");
        dataStructures.setLevel(2);
        dataStructures.setCredits(3);
        dataStructures.setMaxCapacity(30);
        dataStructures.setDescription("Introduction to data structures and algorithm analysis");
        dataStructures.setInstructor("Dr. Sarah Johnson");
        dataStructures.setSemester(fallSemester);
        courseRepository.save(dataStructures);

        Course databaseSystems = new Course();
        databaseSystems.setCourseCode("CS301");
        databaseSystems.setCourseName("Database Systems");
        databaseSystems.setDepartment("Computer Science");
        databaseSystems.setLevel(3);
        databaseSystems.setCredits(3);
        databaseSystems.setMaxCapacity(25);
        databaseSystems.setDescription("Fundamentals of database design and SQL");
        databaseSystems.setInstructor("Dr. Michael Brown");
        databaseSystems.setSemester(fallSemester);
        courseRepository.save(databaseSystems);

        Course oopCourse = new Course();
        oopCourse.setCourseCode("CS202");
        oopCourse.setCourseName("Object-Oriented Programming");
        oopCourse.setDepartment("Computer Science");
        oopCourse.setLevel(2);
        oopCourse.setCredits(4);
        oopCourse.setMaxCapacity(35);
        oopCourse.setDescription("Advanced OOP concepts and design patterns");
        oopCourse.setInstructor("Dr. Emily Davis");
        oopCourse.setSemester(fallSemester);
        courseRepository.save(oopCourse);

        Course calculusCourse = new Course();
        calculusCourse.setCourseCode("MATH101");
        calculusCourse.setCourseName("Calculus I");
        calculusCourse.setDepartment("Mathematics");
        calculusCourse.setLevel(1);
        calculusCourse.setCredits(4);
        calculusCourse.setMaxCapacity(40);
        calculusCourse.setDescription("Introduction to differential and integral calculus");
        calculusCourse.setInstructor("Dr. Robert Wilson");
        calculusCourse.setSemester(fallSemester);
        courseRepository.save(calculusCourse);

        Course softwareEngineering = new Course();
        softwareEngineering.setCourseCode("CS401");
        softwareEngineering.setCourseName("Software Engineering");
        softwareEngineering.setDepartment("Computer Science");
        softwareEngineering.setLevel(4);
        softwareEngineering.setCredits(3);
        softwareEngineering.setMaxCapacity(20);
        softwareEngineering.setDescription("Software development lifecycle and methodologies");
        softwareEngineering.setInstructor("Dr. David Martinez");
        softwareEngineering.setSemester(fallSemester);
        courseRepository.save(softwareEngineering);

        Course operatingSystems = new Course();
        operatingSystems.setCourseCode("CS302");
        operatingSystems.setCourseName("Operating Systems");
        operatingSystems.setDepartment("Computer Science");
        operatingSystems.setLevel(3);
        operatingSystems.setCredits(3);
        operatingSystems.setMaxCapacity(28);
        operatingSystems.setDescription("OS concepts, processes, memory management");
        operatingSystems.setInstructor("Dr. Lisa Anderson");
        operatingSystems.setSemester(fallSemester);
        courseRepository.save(operatingSystems);
    }
}

