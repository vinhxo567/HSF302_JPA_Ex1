package com.hsf302.Chapter6.service.impl;

import com.hsf302.Chapter6.entity.Student;
import com.hsf302.Chapter6.entity.Major;
import com.hsf302.Chapter6.dto.StudentForm;
import com.hsf302.Chapter6.repository.StudentRepository;
import com.hsf302.Chapter6.repository.MajorRepository;
import com.hsf302.Chapter6.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final MajorRepository majorRepository;

    public StudentServiceImpl(StudentRepository studentRepository, MajorRepository majorRepository) {
        this.studentRepository = studentRepository;
        this.majorRepository = majorRepository;
    }

    @Override
    public Page<Student> findAll(String keyword, Pageable pageable) {
        if (keyword != null && !keyword.isBlank()) {
            return studentRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword.trim(), keyword.trim(), pageable);
        }
        return studentRepository.findAll(pageable);
    }

    @Override
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    @Transactional
    public Student create(StudentForm form) {
        Major major = majorRepository.findById(form.getMajorId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy chuyên ngành"));
        Student student = new Student(form.getName(), form.getEmail(), form.getAge(), major, form.getGpa());
        return studentRepository.save(student);
    }

    @Override
    @Transactional
    public boolean update(Long id, StudentForm form) {
        return studentRepository.findById(id).map(existing -> {
            Major major = majorRepository.findById(form.getMajorId())
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy chuyên ngành"));
            existing.setName(form.getName());
            existing.setEmail(form.getEmail());
            existing.setAge(form.getAge());
            existing.setMajor(major);
            existing.setGpa(form.getGpa());
            return true;
        }).orElse(false);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        if (!studentRepository.existsById(id)) return false;
        studentRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean isEmailTaken(String email, Long excludeId) {
        if (email == null || email.isBlank()) return false;
        return excludeId == null
                ? studentRepository.existsByEmailIgnoreCase(email.trim())
                : studentRepository.existsByEmailIgnoreCaseAndIdNot(email.trim(), excludeId);
    }

    @Override
    public List<Major> getMajors() {
        return majorRepository.findAll();
    }
}
