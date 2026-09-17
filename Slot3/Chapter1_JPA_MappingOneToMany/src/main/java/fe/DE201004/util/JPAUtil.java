package fe.DE201004.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    // Tên hsf302FU phải khớp với persistence-unit name trong file persistence.xml
    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("hsf302FU");

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }

    public static void close() {
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }
}
