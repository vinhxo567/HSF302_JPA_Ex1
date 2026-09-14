package fu.DE201004.dao;

import fu.DE201004.pojo.Employee;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

public class EmployeeDAO {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("hsf301PU");
    public void save(Employee e) {
        // Truoc dong nay: e dang o trang thai NEW/TRANSIENT
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(e); // -> e chuyen sang MANAGED, se duoc INSERT khi commit
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close(); // sau dong nay, e (neu con giu tham chieu) la DETACHED
        }
    }
