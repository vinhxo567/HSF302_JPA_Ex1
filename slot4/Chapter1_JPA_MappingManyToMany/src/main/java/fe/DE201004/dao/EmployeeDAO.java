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
}
