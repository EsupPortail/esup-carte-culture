package org.esupportail.esupnfccarteculture.repository;

import org.esupportail.esupnfccarteculture.entity.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class RoleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("eppn", "role");

    public Long countFindRolesByEppnEquals(String eppn) {
        if (eppn == null || eppn.length() == 0) throw new IllegalArgumentException("The eppn argument is required");
        EntityManager em = entityManager;
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM Role AS o WHERE o.eppn = :eppn", Long.class);
        q.setParameter("eppn", eppn);
        return ((Long) q.getSingleResult());
    }

    public TypedQuery<Role> findRolesByEppnEquals(String eppn) {
        if (eppn == null || eppn.length() == 0) throw new IllegalArgumentException("The eppn argument is required");
        EntityManager em = entityManager;
        TypedQuery<Role> q = em.createQuery("SELECT o FROM Role AS o WHERE o.eppn = :eppn", Role.class);
        q.setParameter("eppn", eppn);
        return q;
    }

    public TypedQuery<Role> findRolesByEppnEquals(String eppn, String sortFieldName, String sortOrder) {
        if (eppn == null || eppn.length() == 0) throw new IllegalArgumentException("The eppn argument is required");
        EntityManager em = entityManager;
        StringBuilder queryBuilder = new StringBuilder("SELECT o FROM Role AS o WHERE o.eppn = :eppn");
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            queryBuilder.append(" ORDER BY ").append(sortFieldName);
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                queryBuilder.append(" ").append(sortOrder);
            }
        }
        TypedQuery<Role> q = em.createQuery(queryBuilder.toString(), Role.class);
        q.setParameter("eppn", eppn);
        return q;
    }

    public List<Role> findAllRoles() {
        return entityManager().createQuery("SELECT o FROM Role o", Role.class).getResultList();
    }

    public EntityManager entityManager() {
        EntityManager em = entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public long countRoles() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Role o", Long.class).getSingleResult();
    }

    public List<Role> findAllRoles(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Role o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Role.class).getResultList();
    }

    public Role findRole(Long id) {
        if (id == null) return null;
        return entityManager().find(Role.class, id);
    }

    public List<Role> findRoleEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Role o", Role.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public List<Role> findRoleEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Role o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Role.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }


    @Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

    @Transactional
    public Role merge(Role role) {
        if (this.entityManager == null) this.entityManager = entityManager();
        Role merged = entityManager.merge(role);
        this.entityManager.flush();
        return merged;
    }

    @Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

    @Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

    @Transactional
    public void remove(Long id) {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Role attached = findRole(id);
            this.entityManager.remove(attached);
        }
    }

}