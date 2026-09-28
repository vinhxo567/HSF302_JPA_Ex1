package com.hsf302.ch4.service;

import com.hsf302.ch4.pojo.Student;
import com.hsf302.ch4.repository.StudentRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.hsf302.ch4.pojo.Gender;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)          // mặc định: mọi method chỉ ĐỌC
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public long count() {
        return studentRepository.count();
    }

    @Override
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public List<Student> findAllOrderByGpaDesc() {
        return studentRepository.findAll(Sort.by(Sort.Direction.DESC, "gpa"));
    }

    @Override
    public Page<Student> findPage(int pageIndex, int size, String sortField) {
        if (pageIndex < 0 || size <= 0) {
            throw new IllegalArgumentException("pageIndex phải >= 0 và size phải > 0");
        }
        Pageable pageable = PageRequest.of(pageIndex, size, Sort.by(sortField).ascending());
        return studentRepository.findAll(pageable);
    }

    @Override
    public Optional<Student> findByStudentCode(String studentCode) {
        return studentRepository.findByStudentCode(studentCode);
    }

    @Override
    public boolean isEmailExisted(String email) {
        return studentRepository.existsByEmail(email);
    }

    @Override
    public long countActive() {
        return studentRepository.countByActiveTrue();
    }

    @Override
    public List<Student> searchByName(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return List.of();
        }
        return studentRepository.findByFullNameContainingIgnoreCase(keyword.trim());
    }

    @Override
    public List<Student> findByEmailDomain(String domain) {
        String suffix = domain.startsWith("@") ? domain : "@" + domain;
        return studentRepository.findByEmailEndingWith(suffix);
    }

    @Override
    public List<Student> findWithoutEmail() {
        return studentRepository.findByEmailIsNull();
    }

    @Override
    public List<Student> getStudentsInGpaRange(double minGpa, double maxGpa) {
        return studentRepository.findByGpaBetween(minGpa, maxGpa);
    }

    @Override
    public List<Student> getActiveStudentsByGender(Gender gender) {
        return studentRepository.findByGenderAndActiveTrue(gender);
    }

    @Override
    public List<Student> getStudentsBornAfter(LocalDate date) {
        return studentRepository.findByDobAfter(date);
    }

    @Override
    public List<Student> getStudentsByDepartment(String deptCode) {
        return studentRepository.findByDepartment_Code(deptCode);
    }

    @Override
    public List<Student> getTop3Students() {
        return studentRepository.findTop3ByOrderByGpaDesc();
    }

    @Override
    public List<Student> getStudentsByDeptAndMinGpa(String deptCode, double minGpa) {
        return studentRepository.findByDeptAndMinGpa_Positional(deptCode, minGpa);
    }

    @Override
    public List<Student> getStudentsByDeptAndMinGpaNamed(String deptCode, double minGpa) {
        return studentRepository.findByDeptAndMinGpa_Named(deptCode, minGpa);
    }
}
