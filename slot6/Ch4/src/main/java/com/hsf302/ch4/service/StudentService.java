package com.hsf302.ch4.service;

import com.hsf302.ch4.pojo.Student;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.hsf302.ch4.pojo.Gender;
import org.springframework.data.domain.Page;

public interface StudentService {
    long count();                                   // TODO 6
    Optional<Student> findById(Long id);            // TODO 6
    List<Student> findAllOrderByGpaDesc();                              // TODO 7a
    Page<Student> findPage(int pageIndex, int size, String sortField);  // TODO 7b
    
    Optional<Student> findByStudentCode(String studentCode);   // TODO 8a
    boolean isEmailExisted(String email);                      // TODO 8b
    long countActive();                                        // TODO 8c
    
    List<Student> searchByName(String keyword);                                      // TODO 9
    List<Student> findByEmailDomain(String domain);                                  // TODO 9
    List<Student> findWithoutEmail();                                                // TODO 9

    List<Student> getStudentsInGpaRange(double minGpa, double maxGpa);      // TODO 10a
    List<Student> getActiveStudentsByGender(Gender gender);                 // TODO 10b
    List<Student> getStudentsBornAfter(LocalDate date);                     // TODO 10c
    
    List<Student> getStudentsByDepartment(String deptCode);                 // TODO 11a
    List<Student> getTop3Students();                                        // TODO 11b
    
    List<Student> getStudentsByDeptAndMinGpa(String deptCode, double minGpa);     // TODO 12
    
    List<Student> getStudentsByDeptAndMinGpaNamed(String deptCode, double minGpa);  // TODO 13
    
    int deactivateLowGpaStudents(double threshold);                                 // TODO 14
}
