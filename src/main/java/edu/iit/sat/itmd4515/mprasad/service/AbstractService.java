package edu.iit.sat.itmd4515.mprasad.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Logger;

/**
 * Abstract Service providing common CRUD operations.
 * Author: Monisha
 * @param <T>
 */
public abstract class AbstractService<T> {

    /**
     *
     */
    @PersistenceContext(name = "itmd4515PU")
    protected EntityManager em;

    /**
     *
     */
    protected Class<T> entityClass;

    private static final Logger LOG = Logger.getLogger(AbstractService.class.getName());

    /**
     *
     * @param entityClass
     */
    protected AbstractService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    // CRUD Operations

    /**
     *
     * @param entity
     */
    public void create(T entity) {
        em.persist(entity);
    }

    /**
     *
     * @param id
     * @return
     */
    public T read(Long id) {
        return em.find(entityClass, id);
    }

    /**
     *
     * @param entity
     */
    public void update(T entity) {
        em.merge(entity);
    }

    /**
     *
     * @param entity
     */
    public void delete(T entity) {
        em.remove(em.merge(entity));
    }

    /**
     *
     * @param namedQueryName
     * @return a list
     */
    public List<T> readAll(String namedQueryName) {
        return em.createNamedQuery(namedQueryName, entityClass).getResultList();
    }

    // Generic find by field method

    /**
     *
     * @param fieldName
     * @param value
     * @return
     */
    public T findByField(String fieldName, Object value) {
        String jpql = String.format("SELECT e FROM %s e WHERE e.%s = :value", entityClass.getSimpleName(), fieldName);
        TypedQuery<T> query = em.createQuery(jpql, entityClass);
        query.setParameter("value", value);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            LOG.warning("No result found for " + entityClass.getSimpleName() + " with " + fieldName + " = " + value);
            return null;
        }
    }
}
