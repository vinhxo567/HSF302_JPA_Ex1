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
}
