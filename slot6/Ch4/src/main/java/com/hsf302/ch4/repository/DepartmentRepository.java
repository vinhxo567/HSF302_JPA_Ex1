package com.hsf302.ch4.repository;

import com.hsf302.ch4.pojo.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findByStudentsIsEmpty();
    
    Optional<Department> findByCode(String code); // TODO 16a

    @Query("SELECT d FROM Department d LEFT JOIN FETCH d.students WHERE d.code = :code")
    Optional<Department> findByCodeWithStudents(@Param("code") String code); // TODO 16b

    @Query("SELECT d.name AS departmentName, COUNT(s) AS studentCount " +
           "FROM Department d LEFT JOIN d.students s " +
           "GROUP BY d.id, d.name " +
           "ORDER BY d.name")
    List<com.hsf302.ch4.dto.DepartmentStudentCount> countStudentsByDepartment();

    @Query("SELECT new com.hsf302.ch4.dto.DepartmentStudentDTO(d.name, COUNT(s)) " +
           "FROM Department d LEFT JOIN d.students s " +
           "GROUP BY d.id, d.name")
    List<com.hsf302.ch4.dto.DepartmentStudentDTO> countStudentsByDepartmentDTO();

    @Query(value = "SELECT d.name AS departmentName, COUNT(s.id) AS studentCount " +
                   "FROM departments d LEFT JOIN students s ON s.department_id = d.id " +
                   "GROUP BY d.id, d.name",
           nativeQuery = true)
    List<com.hsf302.ch4.dto.DepartmentStudentCount> countStudentsByDepartmentNative();
}
