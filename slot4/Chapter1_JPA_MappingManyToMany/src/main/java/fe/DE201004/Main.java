package fe.DE201004;

import fe.DE201004.dao.DepartmentDAO;
import fe.DE201004.dao.EmployeeDAO;
import fe.DE201004.dao.ProjectDAO;
import fe.DE201004.pojo.Department;
import fe.DE201004.pojo.Employee;
import fe.DE201004.pojo.Gender;
import fe.DE201004.pojo.Project;
import fe.DE201004.util.JPAUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        EmployeeDAO employeeDAO = new EmployeeDAO();
        ProjectDAO projectDAO = new ProjectDAO();

        System.out.println("--- BẮT ĐẦU TODO 5.7: DEMO MANY-TO-MANY ---");

        // 1. Tạo Department (bắt buộc vì Employee yêu cầu department_id không null)
        Department dept = new Department();
        dept.setName("IT Department");
        departmentDAO.save(dept);
        System.out.println("Đã tạo phòng ban: " + dept.getName());

        // 2. Tạo 3 Employee
        Employee e1 = new Employee("nv1@gmail.com", "Nguyen Van A", Gender.MALE, new BigDecimal("1000"), LocalDate.now());
        e1.setDepartment(dept);
        Employee e2 = new Employee("nv2@gmail.com", "Tran Thi B", Gender.FEMALE, new BigDecimal("1200"), LocalDate.now());
        e2.setDepartment(dept);
        Employee e3 = new Employee("nv3@gmail.com", "Le Van C", Gender.MALE, new BigDecimal("1100"), LocalDate.now());
        e3.setDepartment(dept);

        employeeDAO.save(e1);
        employeeDAO.save(e2);
        employeeDAO.save(e3);
        System.out.println("Đã tạo 3 nhân viên: NV1, NV2, NV3");

        // 3. Tạo 2 Project
        Project pA = new Project("PROJ-A", "Project A", new BigDecimal("50000"), LocalDate.now(), null);
        Project pB = new Project("PROJ-B", "Project B", new BigDecimal("80000"), LocalDate.now(), null);

        projectDAO.save(pA);
        projectDAO.save(pB);
        System.out.println("Đã tạo 2 dự án: Project A, Project B");

        // 4. Phân công chéo
        // NV1 tham gia Project A+B
        employeeDAO.assignEmployeeToProject(e1.getId(), pA.getId());
        employeeDAO.assignEmployeeToProject(e1.getId(), pB.getId());

        // NV2 tham gia Project B
        employeeDAO.assignEmployeeToProject(e2.getId(), pB.getId());

        // NV3 tham gia Project A
        employeeDAO.assignEmployeeToProject(e3.getId(), pA.getId());
        
        System.out.println("Đã phân công nhân viên vào dự án xong.\n");

        // 5. In ra danh sách project của từng nhân viên
        System.out.println("--- DANH SÁCH PROJECT CỦA TỪNG NHÂN VIÊN ---");
        
        printEmployeeProjects(employeeDAO, e1.getId());
        printEmployeeProjects(employeeDAO, e2.getId());
        printEmployeeProjects(employeeDAO, e3.getId());

        System.out.println("--- KẾT THÚC TODO 5.7 ---\n");

        System.out.println("--- BẮT ĐẦU TODO 5.8: THỐNG KÊ PROJECT ---");
        List<Object[]> stats = projectDAO.getProjectStatistics();
        for (Object[] row : stats) {
            String projectName = (String) row[0];
            Long employeeCount = (Long) row[1];
            BigDecimal totalSalary = (BigDecimal) row[2];
            System.out.println("Dự án: " + projectName + " | Số NV active: " + employeeCount + " | Tổng lương: " + totalSalary);
        }
        System.out.println("--- KẾT THÚC TODO 5.8 ---\n");

        System.out.println("--- BẮT ĐẦU TODO 5.9: GỠ NHÂN VIÊN KHỎI DỰ ÁN ---");
        System.out.println("Thực hiện gỡ NV1 khỏi Project A...");
        employeeDAO.unassignEmployeeFromProject(e1.getId(), pA.getId());
        
        System.out.println("Danh sách dự án của NV1 sau khi gỡ:");
        printEmployeeProjects(employeeDAO, e1.getId());
        System.out.println("--- KẾT THÚC TODO 5.9 ---\n");

        System.out.println("--- BẮT ĐẦU TODO 5.10: TÌM NHÂN VIÊN THAM GIA > 1 PROJECT ---");
        System.out.println("Phân công thêm NV2 vào Project A để test...");
        employeeDAO.assignEmployeeToProject(e2.getId(), pA.getId());
        
        List<Employee> busyEmployees = employeeDAO.findEmployeesInMultipleProjects();
        System.out.println("Có " + busyEmployees.size() + " nhân viên tham gia > 1 dự án:");
        for (Employee e : busyEmployees) {
            System.out.println("- " + e.getFullName() + " (Email: " + e.getEmail() + ")");
        }
        System.out.println("--- KẾT THÚC TODO 5.10 ---\n");

        JPAUtil.close();
    }

    private static void printEmployeeProjects(EmployeeDAO employeeDAO, Long empId) {
        Employee emp = employeeDAO.findByIdWithProjects(empId);
        String projectNames = emp.getProjects().stream()
                .map(Project::getProjectName)
                .collect(Collectors.joining(", "));
        System.out.println("- " + emp.getFullName() + " tham gia " + emp.getProjects().size() + " dự án: [" + projectNames + "]");
    }
}