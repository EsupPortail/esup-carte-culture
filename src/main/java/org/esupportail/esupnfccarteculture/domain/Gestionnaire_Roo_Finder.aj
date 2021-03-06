// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.esupportail.esupnfccarteculture.domain;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.esupportail.esupnfccarteculture.domain.Gestionnaire;

privileged aspect Gestionnaire_Roo_Finder {
    
    public static Long Gestionnaire.countFindGestionnairesByEppnEquals(String eppn) {
        if (eppn == null || eppn.length() == 0) throw new IllegalArgumentException("The eppn argument is required");
        EntityManager em = Gestionnaire.entityManager();
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM Gestionnaire AS o WHERE o.eppn = :eppn", Long.class);
        q.setParameter("eppn", eppn);
        return ((Long) q.getSingleResult());
    }
    
    public static TypedQuery<Gestionnaire> Gestionnaire.findGestionnairesByEppnEquals(String eppn) {
        if (eppn == null || eppn.length() == 0) throw new IllegalArgumentException("The eppn argument is required");
        EntityManager em = Gestionnaire.entityManager();
        TypedQuery<Gestionnaire> q = em.createQuery("SELECT o FROM Gestionnaire AS o WHERE o.eppn = :eppn", Gestionnaire.class);
        q.setParameter("eppn", eppn);
        return q;
    }
    
    public static TypedQuery<Gestionnaire> Gestionnaire.findGestionnairesByEppnEquals(String eppn, String sortFieldName, String sortOrder) {
        if (eppn == null || eppn.length() == 0) throw new IllegalArgumentException("The eppn argument is required");
        EntityManager em = Gestionnaire.entityManager();
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
    
}
