package db;


import model.Customer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public class CustomerRepository implements AutoCloseable {

    private final static CustomerRepository INSTANCE = new CustomerRepository();

    private CustomerRepository() {
    }

    public static CustomerRepository getINSTANCE() {
        return INSTANCE;
    }

    public Optional<Customer> getCustomerById(Integer id) {
        EntityManager em = JPAUtil.getEMF("jpa-test-unit").createEntityManager();

        try {
            return Optional.ofNullable(em.find(Customer.class, id));
        } finally {
            em.close();
        }
    }


    public List<Customer> findAll() {
        EntityManager entityManager = JPAUtil.getEMF("jpa-test-unit").createEntityManager();

        try {
            return entityManager.createQuery("select c from Customer c").getResultList();
        } finally {
            entityManager.close();
        }
    }

    public boolean persistCustomer(Customer customer) {
        EntityManager entityManager = JPAUtil.getEMF("jpa-test-unit").createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        try {
            tx.begin();
           // entityManager.persist(customer.getAddress());
            entityManager.persist(customer);
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
