package org.esupportail.esupnfccarteculture.repository;

import org.esupportail.esupnfccarteculture.entity.Salle;
import org.esupportail.esupnfccarteculture.entity.TypeSalle;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class SalleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("nom", "lieu", "typeSalle", "tarif");

    public Salle findSalle(Long id) {
        if (id == null) return null;
        return entityManager().find(Salle.class, id);
    }

    public List<Salle> findAllSalles(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Salle o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Salle.class).getResultList();
    }

    public final EntityManager entityManager() {
        EntityManager em = entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public long countSalles() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Salle o", Long.class).getSingleResult();
    }

    public List<Salle> findSalleEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Salle o", Salle.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public List<Salle> findSalleEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Salle o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Salle.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public Long countFindSallesByNomEquals(String nom) {
        if (nom == null || nom.length() == 0) throw new IllegalArgumentException("The nom argument is required");
        EntityManager em = entityManager;
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM Salle AS o WHERE o.nom = :nom", Long.class);
        q.setParameter("nom", nom);
        return ((Long) q.getSingleResult());
    }

    public Long countFindSallesByTypeSalle(String typeSalle) {
        if (typeSalle == null || typeSalle.length() == 0) throw new IllegalArgumentException("The typeSalle argument is required");
        EntityManager em = entityManager;
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM Salle AS o WHERE o.typeSalle = :typeSalle", Long.class);
        q.setParameter("typeSalle", typeSalle);
        return ((Long) q.getSingleResult());
    }

    public TypedQuery<Salle> findSallesByNomEquals(String nom) {
        if (nom == null || nom.length() == 0) throw new IllegalArgumentException("The nom argument is required");
        EntityManager em = entityManager;
        TypedQuery<Salle> q = em.createQuery("SELECT o FROM Salle AS o WHERE o.nom = :nom", Salle.class);
        q.setParameter("nom", nom);
        return q;
    }

    public TypedQuery<Salle> findSallesByNomEquals(String nom, String sortFieldName, String sortOrder) {
        if (nom == null || nom.length() == 0) throw new IllegalArgumentException("The nom argument is required");
        EntityManager em = entityManager;
        StringBuilder queryBuilder = new StringBuilder("SELECT o FROM Salle AS o WHERE o.nom = :nom");
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            queryBuilder.append(" ORDER BY ").append(sortFieldName);
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                queryBuilder.append(" ").append(sortOrder);
            }
        }
        TypedQuery<Salle> q = em.createQuery(queryBuilder.toString(), Salle.class);
        q.setParameter("nom", nom);
        return q;
    }

    public TypedQuery<Salle> findSallesByTypeSalle(String typeSalle) {
        if (typeSalle == null || typeSalle.length() == 0) throw new IllegalArgumentException("The typeSalle argument is required");
        EntityManager em = entityManager;
        TypedQuery<Salle> q = em.createQuery("SELECT o FROM Salle AS o WHERE o.typeSalle = :typeSalle", Salle.class);
        q.setParameter("typeSalle", typeSalle);
        return q;
    }

    public TypedQuery<Salle> findSallesByTypeSalle(String typeSalle, String sortFieldName, String sortOrder) {
        if (typeSalle == null || typeSalle.length() == 0) throw new IllegalArgumentException("The typeSalle argument is required");
        EntityManager em = entityManager;
        StringBuilder queryBuilder = new StringBuilder("SELECT o FROM Salle AS o WHERE o.typeSalle = :typeSalle");
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            queryBuilder.append(" ORDER BY ").append(sortFieldName);
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                queryBuilder.append(" ").append(sortOrder);
            }
        }
        TypedQuery<Salle> q = em.createQuery(queryBuilder.toString(), Salle.class);
        q.setParameter("typeSalle", typeSalle);
        return q;
    }

    public List<Salle> findAllSalles() {
        return entityManager().createQuery("SELECT o FROM Salle o ORDER BY o.nom", Salle.class).getResultList();
    }

    public Long countFindSallesByTypeSalle(TypeSalle typeSalle) {
        if (typeSalle == null) throw new IllegalArgumentException("The typeSalle argument is required");
        EntityManager em = entityManager;
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM Salle AS o WHERE o.typeSalle = :typeSalle", Long.class);
        q.setParameter("typeSalle", typeSalle.getNom());
        return ((Long) q.getSingleResult());
    }

    public TypedQuery<Salle> findSallesByTypeSalle(TypeSalle typeSalle) {
        if (typeSalle == null) throw new IllegalArgumentException("The typeSalle argument is required");
        EntityManager em = entityManager;
        TypedQuery<Salle> q = em.createQuery("SELECT o FROM Salle AS o WHERE o.typeSalle = :typeSalle", Salle.class);
        q.setParameter("typeSalle", typeSalle.getNom());
        return q;
    }

    public TypedQuery<Salle> findSalles() {
        EntityManager em = entityManager;
        TypedQuery<Salle> q = em.createQuery("SELECT o FROM Salle AS o WHERE o.typeSalle != 'inscription' AND o.typeSalle != 'joker'", Salle.class);
        return q;
    }

    @Transactional
    public void persist(Salle salle) {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(salle);
    }

    @Transactional
    public void remove(Long id) {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Salle attached = findSalle(id);
            this.entityManager.remove(attached);
        }
    }

    @Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

    @Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

    @Transactional
    public Salle merge(Salle salle) {
        if (this.entityManager == null) this.entityManager = entityManager();
        Salle merged = entityManager.merge(salle);
        this.entityManager.flush();
        return merged;
    }


}
