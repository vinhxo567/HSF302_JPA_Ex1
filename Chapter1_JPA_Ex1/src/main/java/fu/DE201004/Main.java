package fu.DE201004;
import fu.DE201004.dao.EmployeeDAO;
import fu.DE201004.pojo.Employee;
import fu.DE201004.pojo.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();

        Employee emp = new Employee("Nguyen Van A", "a@fpt.edu.vn",
                new BigDecimal("15000000"), Gender.MALE, LocalDate.of(2022, 3, 1));

        dao.save(emp);

        System.out.println("Da tao: " + emp);

        Employee found = dao.findById(emp.getId());

        System.out.println("Doc lai: " + found);


        found.setSalary(new BigDecimal("17000000"));

        Employee updated = dao.update(found);

        System.out.println("Sau update: " + updated);


        Employee reChecked = dao.findById(emp.getId());
        System.out.println("Kiem tra lai sau update: " + reChecked);

        dao.delete(emp.getId());

        Employee afterDelete = dao.findById(emp.getId());
        System.out.println("Sau khi xoa, tim lai: " + afterDelete); // ky vong: null

        Employee dup1 = new Employee("User 1", "trung@fpt.edu.vn",
                new BigDecimal("10000000"), Gender.FEMALE, LocalDate.now());
        Employee dup2 = new Employee("User 2", "trung@fpt.edu.vn", // trung email
                new BigDecimal("11000000"), Gender.MALE, LocalDate.now());

        dao.save(dup1);
        try {
            dao.save(dup2); // ky vong: nem exception vi vi pham UNIQUE
            System.out.println("LOI: khong thay exception nhu ky vong!");
        } catch (RuntimeException ex) {
            System.out.println("Da bat duoc loi trung email nhu ky vong: "
                    + ex.getClass().getSimpleName());
        }
    }
}
