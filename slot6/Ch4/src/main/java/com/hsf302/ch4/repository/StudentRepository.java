package com.hsf302.ch4.repository;

import com.hsf302.ch4.pojo.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.hsf302.ch4.pojo.Gender;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;

public interface StudentRepository extends JpaRepository<Student, Long>,
                                           JpaSpecificationExecutor<Student> {
    
    Optional<Student> findByStudentCode(String studentCode);
    boolean existsByEmail(String email);
    long countByActiveTrue();

    List<Student> findByFullNameContainingIgnoreCase(String keyword);           // TODO 9
    List<Student> findByEmailEndingWith(String suffix);
    List<Student> findByEmailIsNull();

    List<Student> findByGpaBetween(double min, double max);                 // TODO 10
    List<Student> findByGenderAndActiveTrue(Gender gender);
    List<Student> findByDobAfter(LocalDate date);

    List<Student> findByDepartment_Code(String deptCode);                   // TODO 11
    List<Student> findTop3ByOrderByGpaDesc();                               // TODO 11

    @Query("SELECT s FROM Student s WHERE s.department.code = ?1 AND s.gpa >= ?2")
    List<Student> findByDeptAndMinGpa_Positional(String deptCode, double minGpa); // TODO 12

    @Query("SELECT s FROM Student s WHERE s.department.code = :dCode AND s.gpa >= :mGpa")
    List<Student> findByDeptAndMinGpa_Named(
            @Param("dCode") String deptCode,
            @Param("mGpa") double minGpa
    );                                                                      // TODO 13

    @Modifying
    @Query("UPDATE Student s SET s.active = false WHERE s.gpa < :minGpa")
    int deactivateStudentsWithGpaLessThan(@Param("minGpa") double minGpa);  // TODO 14
    @Query("SELECT s FROM Student s WHERE s.gpa > (SELECT AVG(s2.gpa) FROM Student s2) ORDER BY s.gpa DESC")
    List<Student> findAboveAverageGpa();

    @Query(value = "SELECT TOP (:n) s.* " +
                   "FROM students s JOIN departments d ON s.department_id = d.id " +
                   "WHERE d.code = :code " +
                   "ORDER BY s.gpa DESC",
           nativeQuery = true)
    List<Student> findTopNByDepartmentNative(@Param("code") String code, @Param("n") int n); // TODO 17

    @Query("SELECT s.studentCode AS studentCode, s.fullName AS fullName, " +
           "       s.gpa AS gpa, d.name AS departmentName " +
           "FROM Student s JOIN s.department d " +
           "WHERE s.active = true " +
           "ORDER BY s.fullName")
    List<com.hsf302.ch4.dto.StudentSummary> findActiveSummaries(); // TODO 18

    @Query("SELECT s FROM Student s WHERE s.department.code = :code AND s.active = true")
    org.springframework.data.domain.Page<Student> findActiveByDepartment(@Param("code") String code, org.springframework.data.domain.Pageable pageable); // TODO 19

    // ===== Exercise 2 — Part C =====
    List<Student> findByCourses_CodeOrderByFullNameAsc(String courseCode);                  // TODO 9
    long countByCourses_Code(String courseCode);                                             // TODO 9, 20
    List<Student> findByCourses_CodeAndActiveTrueOrderByFullNameAsc(String courseCode);      // TODO 9
    List<Student> findByCoursesIsEmptyOrderByFullNameAsc();                                  // TODO 11, 24
    boolean existsByStudentCodeAndCourses_Code(String studentCode, String courseCode);       // TODO 11
}
