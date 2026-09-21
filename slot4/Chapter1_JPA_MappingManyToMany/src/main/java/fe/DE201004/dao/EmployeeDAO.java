package fe.DE201004.dao;

import fe.DE201004.pojo.Employee;
import fe.DE201004.pojo.Project;
import fe.DE201004.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class EmployeeDAO {

    public void save(Employee employee) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(employee);
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) tx.rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }

    public Employee findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Employee.class, id);
        } finally {
            em.close();
        }
    }

    public Employee findByIdWithProjects(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT e FROM Employee e LEFT JOIN FETCH e.projects WHERE e.id = :id", Employee.class)
                     .setParameter("id", id)
                     .getSingleResult();
        } finally {
            em.close();
        }
    }

    public List<Employee> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Employee update(Employee employee) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        Employee merged = null;
        try {
            tx.begin();
            // TODO 2.5: update() dùng em.merge() và gán lại kết quả
            merged = em.merge(employee);
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) tx.rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return merged;
    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Employee employee = em.find(Employee.class, id);
            if (employee != null) {
                em.remove(employee);
            }
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) tx.rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }

    // TODO 5.6: Viết method assignEmployeeToProject(Long employeeId, Long projectId)
    public void assignEmployeeToProject(Long employeeId, Long projectId) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            // find cả 2 entity trong 1 transaction
            Employee employee = em.find(Employee.class, employeeId);
            Project project = em.find(Project.class, projectId);
            
            if (employee != null && project != null) {
                // gọi helper method để đồng bộ hóa 2 chiều
                employee.assignToProject(project);
            }
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) tx.rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }

    // TODO 5.9: Helper DAO method để gỡ nhân viên khỏi project
    public void unassignEmployeeFromProject(Long employeeId, Long projectId) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Employee employee = em.find(Employee.class, employeeId);
            Project project = em.find(Project.class, projectId);
            
            if (employee != null && project != null) {
                employee.unassignFromProject(project);
            }
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) tx.rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }

    // TODO 5.10: Tìm các Employee active tham gia nhiều hơn 1 project cùng lúc
    public List<Employee> findEmployeesInMultipleProjects() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT e FROM Employee e WHERE e.active = true AND SIZE(e.projects) > 1";
            return em.createQuery(jpql, Employee.class).getResultList();
        } finally {
            em.close();
        }
    }

    // TODO 5.11: Deactivate nhân viên thay vì xóa hẳn
    /*
     * Giải thích: Khi nhân viên nghỉ việc (deactivate), ta KHÔNG NÊN tự động gỡ họ khỏi tất cả các project
     * bằng CascadeType.REMOVE hay gỡ quan hệ trong bảng trung gian.
     * Lý do:
     * - Cần giữ nguyên dữ liệu trong bảng trung gian employee_project để phục vụ mục đích tra cứu lịch sử 
     *   (biết ai đã từng tham gia dự án nào).
     * - Không dùng CascadeType.REMOVE ở quan hệ ManyToMany vì nếu thiết lập không cẩn thận, việc xóa Employee 
     *   sẽ kéo theo việc xóa luôn các Project đang liên kết, gây ảnh hưởng đến dữ liệu dự án và các Employee khác.
     * Cách xử lý phù hợp: Chỉ update cột active = false (soft delete) và giữ nguyên liên kết N-N. 
     * Các query nghiệp vụ (như báo cáo ở TODO 5.8, 5.10) sẽ thêm điều kiện WHERE active = true để lọc dữ liệu hợp lệ.
     */
    public void deactivateEmployee(Long employeeId) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Employee employee = em.find(Employee.class, employeeId);
            if (employee != null) {
                employee.setActive(false);
            }
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) tx.rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }
}
