package com.hsf302.Chapter6.service;

import com.hsf302.Chapter6.entity.Student;
import com.hsf302.Chapter6.entity.Major;
import com.hsf302.Chapter6.dto.StudentForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface StudentService {
    Page<Student> findAll(String keyword, Pageable pageable);
    Optional<Student> findById(Long id);
    Student create(StudentForm form);
    boolean update(Long id, StudentForm form);
    boolean delete(Long id);
    boolean isEmailTaken(String email, Long excludeId);
    List<Major> getMajors();
}
