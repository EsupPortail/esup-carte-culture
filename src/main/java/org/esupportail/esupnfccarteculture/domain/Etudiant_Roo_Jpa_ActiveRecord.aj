// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.esupportail.esupnfccarteculture.domain;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.esupportail.esupnfccarteculture.domain.Etudiant;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Etudiant_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Etudiant.entityManager;
    
    public static final List<String> Etudiant.fieldNames4OrderClauseFilter = java.util.Arrays.asList("log", "eppn", "csn", "civilite", "nom", "prenom", "email", "coupons", "nbRecharge", "filiere", "niveauEtudes", "affiliation", "etablissement", "dateNaissance", "dateRecharge", "dateInscription");
    
    public static final EntityManager Etudiant.entityManager() {
        EntityManager em = new Etudiant().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Etudiant.countEtudiants() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Etudiant o", Long.class).getSingleResult();
    }
    
    public static List<Etudiant> Etudiant.findAllEtudiants() {
        return entityManager().createQuery("SELECT o FROM Etudiant o", Etudiant.class).getResultList();
    }
    
    public static List<Etudiant> Etudiant.findAllEtudiants(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Etudiant o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Etudiant.class).getResultList();
    }
    
    public static Etudiant Etudiant.findEtudiant(Long id) {
        if (id == null) return null;
        return entityManager().find(Etudiant.class, id);
    }
    
    public static List<Etudiant> Etudiant.findEtudiantEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Etudiant o", Etudiant.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    public static List<Etudiant> Etudiant.findEtudiantEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Etudiant o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Etudiant.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Etudiant.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Etudiant.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Etudiant attached = Etudiant.findEtudiant(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Etudiant.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Etudiant.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Etudiant Etudiant.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Etudiant merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}