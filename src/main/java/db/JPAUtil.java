package db;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

    private static EntityManagerFactory emf;

    public static EntityManagerFactory getEMF(String persistenceUnit) {
        if(emf == null) {
            emf = Persistence.createEntityManagerFactory(persistenceUnit);
        }
        return emf;
    }

    public static void close() {
        if (emf.isOpen()) {
            emf.close();
            emf = null;
        }
    }

}
