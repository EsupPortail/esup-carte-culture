package org.esupportail.esupnfccarteculture.repository;

import org.esupportail.esupnfccarteculture.entity.Gestionnaire;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class GestionnaireRepository {

   public final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("eppn", "nom", "prenom", "salles");

    @PersistenceContext
    private EntityManager entityManager;

   public final EntityManager entityManager() {
        EntityManager em = entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

   public long countGestionnaires() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Gestionnaire o", Long.class).getSingleResult();
    }

   public List<Gestionnaire> findAllGestionnaires() {
        return entityManager().createQuery("SELECT o FROM Gestionnaire o", Gestionnaire.class).getResultList();
    }

   public List<Gestionnaire> findAllGestionnaires(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Gestionnaire o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Gestionnaire.class).getResultList();
    }

   public Gestionnaire findGestionnaire(Long id) {
        if (id == null) return null;
        return entityManager().find(Gestionnaire.class, id);
    }

   public List<Gestionnaire> findGestionnaireEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Gestionnaire o", Gestionnaire.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

   public List<Gestionnaire> findGestionnaireEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Gestionnaire o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Gestionnaire.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

   public Long countFindGestionnairesByEppnEquals(String eppn) {
        if (eppn == null || eppn.length() == 0) throw new IllegalArgumentException("The eppn argument is required");
        EntityManager em = entityManager;
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM Gestionnaire AS o WHERE o.eppn = :eppn", Long.class);
        q.setParameter("eppn", eppn);
        return ((Long) q.getSingleResult());
    }

   public TypedQuery<Gestionnaire> findGestionnairesByEppnEquals(String eppn) {
        if (eppn == null || eppn.length() == 0) throw new IllegalArgumentException("The eppn argument is required");
        EntityManager em = entityManager;
        TypedQuery<Gestionnaire> q = em.createQuery("SELECT o FROM Gestionnaire AS o WHERE o.eppn = :eppn", Gestionnaire.class);
        q.setParameter("eppn", eppn);
        return q;
    }

   public TypedQuery<Gestionnaire> findGestionnairesByEppnEquals(String eppn, String sortFieldName, String sortOrder) {
        if (eppn == null || eppn.length() == 0) throw new IllegalArgumentException("The eppn argument is required");
        EntityManager em = entityManager;
        StringBuilder queryBuilder = new StringBuilder("SELECT o FROM Gestionnaire AS o WHERE o.eppn = :eppn");
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            queryBuilder.append(" ORDER BY ").append(sortFieldName);
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                queryBuilder.append(" ").append(sortOrder);
            }
        }
        TypedQuery<Gestionnaire> q = em.createQuery(queryBuilder.toString(), Gestionnaire.class);
        q.setParameter("eppn", eppn);
        return q;
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
    public Gestionnaire merge(Gestionnaire gestionnaire) {
        if (this.entityManager == null) this.entityManager = entityManager();
        Gestionnaire merged = entityManager.merge(gestionnaire);
        this.entityManager.flush();
        return merged;
    }

    @Transactional
    public void persist(Gestionnaire gestionnaire) {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(gestionnaire);
    }

   @Transactional
   public void remove(Long id) {
       if (this.entityManager == null) this.entityManager = entityManager();
       Gestionnaire attached = findGestionnaire(id);
       attached.getSalles().clear();
       this.entityManager.remove(attached);
   }

}
