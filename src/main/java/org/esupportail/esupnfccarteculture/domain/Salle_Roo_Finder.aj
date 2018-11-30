// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.esupportail.esupnfccarteculture.domain;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.esupportail.esupnfccarteculture.domain.Salle;

privileged aspect Salle_Roo_Finder {
    
    public static Long Salle.countFindSallesByNomEquals(String nom) {
        if (nom == null || nom.length() == 0) throw new IllegalArgumentException("The nom argument is required");
        EntityManager em = Salle.entityManager();
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM Salle AS o WHERE o.nom = :nom", Long.class);
        q.setParameter("nom", nom);
        return ((Long) q.getSingleResult());
    }
    
    public static Long Salle.countFindSallesByTypeSalle(String typeSalle) {
        if (typeSalle == null || typeSalle.length() == 0) throw new IllegalArgumentException("The typeSalle argument is required");
        EntityManager em = Salle.entityManager();
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM Salle AS o WHERE o.typeSalle = :typeSalle", Long.class);
        q.setParameter("typeSalle", typeSalle);
        return ((Long) q.getSingleResult());
    }
    
    public static TypedQuery<Salle> Salle.findSallesByNomEquals(String nom) {
        if (nom == null || nom.length() == 0) throw new IllegalArgumentException("The nom argument is required");
        EntityManager em = Salle.entityManager();
        TypedQuery<Salle> q = em.createQuery("SELECT o FROM Salle AS o WHERE o.nom = :nom", Salle.class);
        q.setParameter("nom", nom);
        return q;
    }
    
    public static TypedQuery<Salle> Salle.findSallesByNomEquals(String nom, String sortFieldName, String sortOrder) {
        if (nom == null || nom.length() == 0) throw new IllegalArgumentException("The nom argument is required");
        EntityManager em = Salle.entityManager();
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
    
    public static TypedQuery<Salle> Salle.findSallesByTypeSalle(String typeSalle) {
        if (typeSalle == null || typeSalle.length() == 0) throw new IllegalArgumentException("The typeSalle argument is required");
        EntityManager em = Salle.entityManager();
        TypedQuery<Salle> q = em.createQuery("SELECT o FROM Salle AS o WHERE o.typeSalle = :typeSalle", Salle.class);
        q.setParameter("typeSalle", typeSalle);
        return q;
    }
    
    public static TypedQuery<Salle> Salle.findSallesByTypeSalle(String typeSalle, String sortFieldName, String sortOrder) {
        if (typeSalle == null || typeSalle.length() == 0) throw new IllegalArgumentException("The typeSalle argument is required");
        EntityManager em = Salle.entityManager();
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
    
}