package fe.DE201004.dao;

import fe.DE201004.pojo.Project;
import fe.DE201004.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class ProjectDAO {

    public void save(Project project) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(project);
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) tx.rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }

    // TODO 5.8: Viết JPQL đếm số nhân viên active tham gia mỗi project và tính tổng salary
    public List<Object[]> getProjectStatistics() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT p.projectName, COUNT(e), SUM(e.salary) " +
                          "FROM Project p JOIN p.employees e " +
                          "WHERE e.active = true " +
                          "GROUP BY p.projectName";
            return em.createQuery(jpql, Object[].class).getResultList();
        } finally {
            em.close();
        }
    }
}
