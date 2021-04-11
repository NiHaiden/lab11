package db;


import model.Address;
import model.Customer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public class AddressRepository implements AutoCloseable {
    private final static AddressRepository INSTANCE = new AddressRepository();

    private AddressRepository() {}

    public static AddressRepository getINSTANCE() {
        return INSTANCE;
    }

    public Optional<Address> getAddressById(Integer id) {
        EntityManager em = JPAUtil.getEMF("jpa-test-unit").createEntityManager();

        try {
            return Optional.ofNullable(em.find(Address.class, id));
        } finally {
            em.close();
        }
    }

    public List<Address> findAll() {
        EntityManager entityManager = JPAUtil.getEMF("jpa-test-unit").createEntityManager();

        try {
            return entityManager.createQuery("select a from Address a").getResultList();
        } finally {
            entityManager.close();
        }
    }

    public boolean persistAddress(Address address) {
        EntityManager entityManager = JPAUtil.getEMF("jpa-test-unit").createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        try {
            tx.begin();
            entityManager.persist(address);
            tx.commit();
            return true;
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }

            return false;
        } finally {
            entityManager.close();
        }
    }


    @Override
    public void close() throws Exception {
        JPAUtil.close();
    }
}
