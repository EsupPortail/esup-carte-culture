package org.esupportail.esupnfccarteculture.repository;

import org.esupportail.esupnfccarteculture.entity.TagLogGest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TagLogGestRepository {

    public final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("eppn", "salle", "date", "eppnInit");

    @PersistenceContext
    transient EntityManager entityManager;


    public List<TagLogGest> findTagLogGestEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM TagLogGest o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, TagLogGest.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public final EntityManager entityManager() {
        EntityManager em = entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public long countTagLogGests() {
        return entityManager().createQuery("SELECT COUNT(o) FROM TagLogGest o", Long.class).getSingleResult();
    }

    public List<TagLogGest> findAllTagLogGests() {
        return entityManager().createQuery("SELECT o FROM TagLogGest o", TagLogGest.class).getResultList();
    }

    public List<TagLogGest> findAllTagLogGests(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM TagLogGest o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, TagLogGest.class).getResultList();
    }

    public TagLogGest findTagLogGest(Long id) {
        if (id == null) return null;
        return entityManager().find(TagLogGest.class, id);
    }

    public List<TagLogGest> findTagLogGestEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM TagLogGest o", TagLogGest.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }


    @Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

    @Transactional
    public TagLogGest merge(TagLogGest tagLogGest) {
        if (this.entityManager == null) this.entityManager = entityManager();
        TagLogGest merged = this.entityManager.merge(tagLogGest);
        this.entityManager.flush();
        return merged;
    }

    @Transactional
    public void persist(TagLogGest tagLogGest) {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(tagLogGest);
    }

    @Transactional
    public void remove(Long id) {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            TagLogGest attached = findTagLogGest(id);
            this.entityManager.remove(attached);
        }
    }

    @Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
}
