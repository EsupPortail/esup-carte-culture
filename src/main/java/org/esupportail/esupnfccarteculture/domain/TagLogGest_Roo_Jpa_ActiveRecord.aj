// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.esupportail.esupnfccarteculture.domain;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.esupportail.esupnfccarteculture.domain.TagLogGest;
import org.springframework.transaction.annotation.Transactional;

privileged aspect TagLogGest_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager TagLogGest.entityManager;
    
    public static final List<String> TagLogGest.fieldNames4OrderClauseFilter = java.util.Arrays.asList("eppn", "salle", "date", "eppnInit");
    
    public static final EntityManager TagLogGest.entityManager() {
        EntityManager em = new TagLogGest().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long TagLogGest.countTagLogGests() {
        return entityManager().createQuery("SELECT COUNT(o) FROM TagLogGest o", Long.class).getSingleResult();
    }
    
    public static List<TagLogGest> TagLogGest.findAllTagLogGests() {
        return entityManager().createQuery("SELECT o FROM TagLogGest o", TagLogGest.class).getResultList();
    }
    
    public static List<TagLogGest> TagLogGest.findAllTagLogGests(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM TagLogGest o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, TagLogGest.class).getResultList();
    }
    
    public static TagLogGest TagLogGest.findTagLogGest(Long id) {
        if (id == null) return null;
        return entityManager().find(TagLogGest.class, id);
    }
    
    public static List<TagLogGest> TagLogGest.findTagLogGestEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM TagLogGest o", TagLogGest.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    public static List<TagLogGest> TagLogGest.findTagLogGestEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM TagLogGest o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, TagLogGest.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void TagLogGest.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void TagLogGest.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            TagLogGest attached = TagLogGest.findTagLogGest(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void TagLogGest.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void TagLogGest.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public TagLogGest TagLogGest.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        TagLogGest merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
