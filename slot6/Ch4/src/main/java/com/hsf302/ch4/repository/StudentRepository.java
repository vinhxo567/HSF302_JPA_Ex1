package com.hsf302.ch4.repository;

import com.hsf302.ch4.pojo.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.hsf302.ch4.pojo.Gender;
import org.springframework.data.jpa.repository.Query;

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
}
