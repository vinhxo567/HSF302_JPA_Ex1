package com.hsf302.Chapter6.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên không được để trống")
    @Size(min = 2, max = 50, message = "Tên phải từ 2 đến 50 ký tự")
    @Column(nullable = false, length = 50)
    private String name;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @NotNull(message = "Tuổi không được để trống")
    @Min(value = 18, message = "Tuổi phải từ 18 trở lên")
    @Column(nullable = false)
    private Integer age;

    @NotNull(message = "Chuyên ngành không được để trống")
    @ManyToOne
    @JoinColumn(name = "major_id", nullable = false)
    private Major major;

    @NotNull(message = "Điểm GPA không được để trống")
    @Min(value = 0, message = "GPA tối thiểu là 0.0")
    @Max(value = 10, message = "GPA tối đa là 10.0")
    @Column(nullable = false)
    private Double gpa;

    // Constructors
    public Student() {
    }

    public Student(String name, String email, Integer age, Major major, Double gpa) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.major = major;
        this.gpa = gpa;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public Major getMajor() { return major; }
    public void setMajor(Major major) { this.major = major; }

    public Double getGpa() { return gpa; }
    public void setGpa(Double gpa) { this.gpa = gpa; }
}
