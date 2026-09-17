package fe.DE201004;

import fe.DE201004.dao.DepartmentDAO;
import fe.DE201004.pojo.Department;
import fe.DE201004.pojo.Employee;
import fe.DE201004.pojo.Gender;
import fe.DE201004.util.JPAUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DepartmentDAO departmentDAO = new DepartmentDAO();


        System.out.println("--- BẮT ĐẦU TODO 2.8: TÁI HIỆN N+1 QUERY PROBLEM ---");
  
        List<Department> departments = departmentDAO.findAll();
        
        System.out.println("Đã lấy xong danh sách Departments (1 câu lệnh SELECT).");
        System.out.println("Bắt đầu lặp qua danh sách để lấy thông tin Employees (Sẽ sinh ra N câu SELECT):");

        for (Department dept : departments) {
            System.out.println("Phòng ban: " + dept.getName());
   
            for (Employee e : dept.getEmployees()) {
                System.out.println(" - " + e.getFullName());
            }
        }
        System.out.println("--- KẾT THÚC TODO 2.8 ---");

        JPAUtil.close();
    }
}