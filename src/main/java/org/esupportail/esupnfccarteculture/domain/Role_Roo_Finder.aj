// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.esupportail.esupnfccarteculture.domain;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.esupportail.esupnfccarteculture.domain.Role;

privileged aspect Role_Roo_Finder {
    
    public static Long Role.countFindRolesByEppnEquals(String eppn) {
        if (eppn == null || eppn.length() == 0) throw new IllegalArgumentException("The eppn argument is required");
        EntityManager em = Role.entityManager();
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM Role AS o WHERE o.eppn = :eppn", Long.class);
        q.setParameter("eppn", eppn);
        return ((Long) q.getSingleResult());
    }
    
    public static TypedQuery<Role> Role.findRolesByEppnEquals(String eppn) {
        if (eppn == null || eppn.length() == 0) throw new IllegalArgumentException("The eppn argument is required");
        EntityManager em = Role.entityManager();
        TypedQuery<Role> q = em.createQuery("SELECT o FROM Role AS o WHERE o.eppn = :eppn", Role.class);
        q.setParameter("eppn", eppn);
        return q;
    }
    
    public static TypedQuery<Role> Role.findRolesByEppnEquals(String eppn, String sortFieldName, String sortOrder) {
        if (eppn == null || eppn.length() == 0) throw new IllegalArgumentException("The eppn argument is required");
        EntityManager em = Role.entityManager();
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
    
}
