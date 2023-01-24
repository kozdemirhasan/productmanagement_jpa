package de.kozdemir.myproductmanagement;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Optional;

public class ProductRepository {

    public static EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }


    public void save(Product product) {

        if (product.getId() > 0L) {
            // UPDATE
        } else {
            insert(product);
        }
    }

    private void insert(Product product) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(product);
        em.getTransaction().commit();
    }

    public Optional<Product> find(long id) {
        EntityManager em = getEntityManager();
        Product product = em.find(Product.class, id);
        if (product != null) {
            em.detach(product);
            return Optional.of(product);
        }
        return Optional.empty();
    }

    public List<Product> find() {
        EntityManager em = getEntityManager();

        List<Product> products = em.createQuery("SELECT p FROM Product p").getResultList();
        return products;
    }

    public List<Product> find(String str) {
        EntityManager em = getEntityManager();
        List<Product> products = em.createQuery("SELECT p FROM Product p WHERE p.name LIKE ?1 OR p.description LIKE ?1")
                .setParameter(1, "%" + str + "%")
                .getResultList();
        return products;
    }

    public void delete(long id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Product product = em.find(Product.class, id);
        em.remove(product);
        em.getTransaction().commit();
    }

    public void delete(Product product) {
        delete(product.getId());
    }
}



