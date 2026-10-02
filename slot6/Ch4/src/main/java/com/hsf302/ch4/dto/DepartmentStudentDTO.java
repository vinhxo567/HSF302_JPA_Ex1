package com.hsf302.ch4.dto;

public class DepartmentStudentDTO {
    private String departmentName;
    private Long studentCount;

    public DepartmentStudentDTO(String departmentName, Long studentCount) {
        this.departmentName = departmentName;
        this.studentCount = studentCount;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public Long getStudentCount() {
        return studentCount;
    }

    @Override
    public String toString() {
        return "DepartmentStudentDTO{" +
                "departmentName='" + departmentName + '\'' +
                ", studentCount=" + studentCount +
                '}';
    }
}
