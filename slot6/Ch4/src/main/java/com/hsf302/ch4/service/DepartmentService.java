package com.hsf302.ch4.service;

import com.hsf302.ch4.pojo.Department;
import java.util.List;

public interface DepartmentService {
    long count();                                   // TODO 6
    boolean existsById(Long id);                    // TODO 6
    List<Department> getEmptyDepartments();         // TODO 11c
    
    java.util.Optional<Department> findByCode(String code);   // TODO 16a
    Department getWithStudents(String code);                  // TODO 16b

    List<com.hsf302.ch4.dto.DepartmentStudentCount> countStudentsByDepartment();
    List<com.hsf302.ch4.dto.DepartmentStudentDTO> countStudentsByDepartmentDTO();
    List<com.hsf302.ch4.dto.DepartmentStudentCount> countStudentsByDepartmentNative();
}
