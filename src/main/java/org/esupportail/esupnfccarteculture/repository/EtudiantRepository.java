package org.esupportail.esupnfccarteculture.repository;

import org.esupportail.esupnfccarteculture.entity.Etudiant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class EtudiantRepository {

    public final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("log", "eppn", "csn", "civilite", "nom", "prenom", "email", "coupons", "nbRecharge", "filiere", "niveauEtudes", "affiliation", "etablissement", "dateNaissance", "dateRecharge", "dateInscription");

    protected final static Logger log = LoggerFactory.getLogger(Etudiant.class);

    @PersistenceContext
    private EntityManager entityManager;

    public Long countFindEtudiantsByEppnLike(String eppn) {
        if (eppn == null || eppn.length() == 0) throw new IllegalArgumentException("The eppn argument is required");
        eppn = eppn.replace('*', '%');
        if (eppn.charAt(0) != '%') {
            eppn = "%" + eppn;
        }
        if (eppn.charAt(eppn.length() - 1) != '%') {
            eppn = eppn + "%";
        }
        EntityManager em = entityManager;
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM Etudiant AS o WHERE LOWER(o.eppn) LIKE LOWER(:eppn)", Long.class);
        q.setParameter("eppn", eppn);
        return ((Long) q.getSingleResult());
    }

    public Long countFindEtudiantsByCsnEquals(String csn) {
        if (csn == null || csn.length() == 0) throw new IllegalArgumentException("The csn argument is required");
        EntityManager em = entityManager;
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM Etudiant AS o WHERE o.csn = :csn", Long.class);
        q.setParameter("csn", csn);
        return ((Long) q.getSingleResult());
    }

    public TypedQuery<Etudiant> findEtudiantsByEppnEquals(String eppn, String sortFieldName, String sortOrder) {
        if (eppn == null || eppn.length() == 0) throw new IllegalArgumentException("The eppn argument is required");
        EntityManager em = entityManager;
        StringBuilder queryBuilder = new StringBuilder("SELECT o FROM Etudiant AS o WHERE o.eppn = :eppn");
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            queryBuilder.append(" ORDER BY ").append(sortFieldName);
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                queryBuilder.append(" ").append(sortOrder);
            }
        }
        TypedQuery<Etudiant> q = em.createQuery(queryBuilder.toString(), Etudiant.class);
        q.setParameter("eppn", eppn);
        return q;
    }

    public TypedQuery<Etudiant> findEtudiantsByEppnLike(String eppn) {
        if (eppn == null || eppn.length() == 0) throw new IllegalArgumentException("The eppn argument is required");
        eppn = eppn.replace('*', '%');
        if (eppn.charAt(0) != '%') {
            eppn = "%" + eppn;
        }
        if (eppn.charAt(eppn.length() - 1) != '%') {
            eppn = eppn + "%";
        }
        EntityManager em = entityManager;
        TypedQuery<Etudiant> q = em.createQuery("SELECT o FROM Etudiant AS o WHERE LOWER(o.eppn) LIKE LOWER(:eppn)", Etudiant.class);
        q.setParameter("eppn", eppn);
        return q;
    }

    public TypedQuery<Etudiant> findEtudiantsByNomEqualsAndPrenomEqualsAndDateNaissanceEquals(String nom, String prenom, Date dateNaissance) {
        if (nom == null || nom.length() == 0) throw new IllegalArgumentException("The nom argument is required");
        if (prenom == null || prenom.length() == 0) throw new IllegalArgumentException("The prenom argument is required");
        if (dateNaissance == null) throw new IllegalArgumentException("The dateNaissance argument is required");
        EntityManager em = entityManager;
        TypedQuery<Etudiant> q = em.createQuery("SELECT o FROM Etudiant AS o WHERE lower(o.nom) = lower(:nom)  AND lower(o.prenom) = lower(:prenom)  AND o.dateNaissance = :dateNaissance", Etudiant.class);
        q.setParameter("nom", nom);
        q.setParameter("prenom", prenom);
        q.setParameter("dateNaissance", dateNaissance);
        return q;
    }

    public Long countFindEtudiantsByEppnEquals(String eppn) {
        if (eppn == null || eppn.length() == 0) throw new IllegalArgumentException("The eppn argument is required");
        EntityManager em = entityManager;
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM Etudiant AS o WHERE o.eppn = :eppn", Long.class);
        q.setParameter("eppn", eppn);
        return ((Long) q.getSingleResult());
    }

    public TypedQuery<Etudiant> findEtudiantsByNomEquals(String nom, String sortFieldName, String sortOrder) {
        if (nom == null || nom.length() == 0) throw new IllegalArgumentException("The nom argument is required");
        EntityManager em = entityManager;
        StringBuilder queryBuilder = new StringBuilder("SELECT o FROM Etudiant AS o WHERE o.nom = :nom");
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            queryBuilder.append(" ORDER BY ").append(sortFieldName);
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                queryBuilder.append(" ").append(sortOrder);
            }
        }
        TypedQuery<Etudiant> q = em.createQuery(queryBuilder.toString(), Etudiant.class);
        q.setParameter("nom", nom);
        return q;
    }

    public Long countFindEtudiantsByEtablissementEquals(String etablissement) {
        if (etablissement == null || etablissement.length() == 0) throw new IllegalArgumentException("The etablissement argument is required");
        EntityManager em = entityManager;
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM Etudiant AS o WHERE o.etablissement = :etablissement", Long.class);
        q.setParameter("etablissement", etablissement);
        return ((Long) q.getSingleResult());
    }

    public Long countFindEtudiantsByNomEquals(String nom) {
        if (nom == null || nom.length() == 0) throw new IllegalArgumentException("The nom argument is required");
        EntityManager em = entityManager;
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM Etudiant AS o WHERE o.nom = :nom", Long.class);
        q.setParameter("nom", nom);
        return ((Long) q.getSingleResult());
    }

    public TypedQuery<Etudiant> findEtudiantsByNomEqualsAndPrenomEqualsAndDateNaissanceEquals(String nom, String prenom, Date dateNaissance, String sortFieldName, String sortOrder) {
        if (nom == null || nom.length() == 0) throw new IllegalArgumentException("The nom argument is required");
        if (prenom == null || prenom.length() == 0) throw new IllegalArgumentException("The prenom argument is required");
        if (dateNaissance == null) throw new IllegalArgumentException("The dateNaissance argument is required");
        EntityManager em = entityManager;
        StringBuilder queryBuilder = new StringBuilder("SELECT o FROM Etudiant AS o WHERE o.nom = :nom  AND o.prenom = :prenom  AND o.dateNaissance = :dateNaissance");
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            queryBuilder.append(" ORDER BY ").append(sortFieldName);
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                queryBuilder.append(" ").append(sortOrder);
            }
        }
        TypedQuery<Etudiant> q = em.createQuery(queryBuilder.toString(), Etudiant.class);
        q.setParameter("nom", nom);
        q.setParameter("prenom", prenom);
        q.setParameter("dateNaissance", dateNaissance);
        return q;
    }

    public Long countFindEtudiantsByNomEqualsAndPrenomEqualsAndDateNaissanceEquals(String nom, String prenom, Date dateNaissance) {
        if (nom == null || nom.length() == 0) throw new IllegalArgumentException("The nom argument is required");
        if (prenom == null || prenom.length() == 0) throw new IllegalArgumentException("The prenom argument is required");
        if (dateNaissance == null) throw new IllegalArgumentException("The dateNaissance argument is required");
        EntityManager em = entityManager;
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM Etudiant AS o WHERE lower(o.nom) = lower(:nom)  AND lower(o.prenom) = lower(:prenom) AND o.dateNaissance = :dateNaissance", Long.class);
        q.setParameter("nom", nom);
        q.setParameter("prenom", prenom);
        q.setParameter("dateNaissance", dateNaissance);
        return ((Long) q.getSingleResult());
    }

    public TypedQuery<Etudiant> findEtudiantsByEppnEquals(String eppn) {
        if (eppn == null || eppn.length() == 0) throw new IllegalArgumentException("The eppn argument is required");
        EntityManager em = entityManager;
        TypedQuery<Etudiant> q = em.createQuery("SELECT o FROM Etudiant AS o WHERE o.eppn = :eppn", Etudiant.class);
        q.setParameter("eppn", eppn);
        return q;
    }

    public TypedQuery<Etudiant> findEtudiantsByEtablissementEquals(String etablissement) {
        if (etablissement == null || etablissement.length() == 0) throw new IllegalArgumentException("The etablissement argument is required");
        EntityManager em = entityManager;
        TypedQuery<Etudiant> q = em.createQuery("SELECT o FROM Etudiant AS o WHERE o.etablissement = :etablissement", Etudiant.class);
        q.setParameter("etablissement", etablissement);
        return q;
    }

    public TypedQuery<Etudiant> findEtudiantsByNomEquals(String nom) {
        if (nom == null || nom.length() == 0) throw new IllegalArgumentException("The nom argument is required");
        EntityManager em = entityManager;
        TypedQuery<Etudiant> q = em.createQuery("SELECT o FROM Etudiant AS o WHERE o.nom = :nom", Etudiant.class);
        q.setParameter("nom", nom);
        return q;
    }

    public TypedQuery<Etudiant> findEtudiantsByNomLike(String nom) {
        if (nom == null || nom.length() == 0) throw new IllegalArgumentException("The nom argument is required");
        nom = nom.replace('*', '%');
        if (nom.charAt(0) != '%') {
            nom = "%" + nom;
        }
        if (nom.charAt(nom.length() - 1) != '%') {
            nom = nom + "%";
        }
        EntityManager em = entityManager;
        TypedQuery<Etudiant> q = em.createQuery("SELECT o FROM Etudiant AS o WHERE LOWER(o.nom) LIKE LOWER(:nom)", Etudiant.class);
        q.setParameter("nom", nom);
        return q;
    }

    public Long countFindEtudiantsByNomLike(String nom) {
        if (nom == null || nom.length() == 0) throw new IllegalArgumentException("The nom argument is required");
        nom = nom.replace('*', '%');
        if (nom.charAt(0) != '%') {
            nom = "%" + nom;
        }
        if (nom.charAt(nom.length() - 1) != '%') {
            nom = nom + "%";
        }
        EntityManager em = entityManager;
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM Etudiant AS o WHERE LOWER(o.nom) LIKE LOWER(:nom)", Long.class);
        q.setParameter("nom", nom);
        return ((Long) q.getSingleResult());
    }

    public TypedQuery<Etudiant> findEtudiantsByNomLike(String nom, String sortFieldName, String sortOrder) {
        if (nom == null || nom.length() == 0) throw new IllegalArgumentException("The nom argument is required");
        nom = nom.replace('*', '%');
        if (nom.charAt(0) != '%') {
            nom = "%" + nom;
        }
        if (nom.charAt(nom.length() - 1) != '%') {
            nom = nom + "%";
        }
        EntityManager em = entityManager;
        StringBuilder queryBuilder = new StringBuilder("SELECT o FROM Etudiant AS o WHERE LOWER(o.nom) LIKE LOWER(:nom)");
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            queryBuilder.append(" ORDER BY ").append(sortFieldName);
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                queryBuilder.append(" ").append(sortOrder);
            }
        }
        TypedQuery<Etudiant> q = em.createQuery(queryBuilder.toString(), Etudiant.class);
        q.setParameter("nom", nom);
        return q;
    }

    public TypedQuery<Etudiant> findEtudiantsByCsnEquals(String csn) {
        if (csn == null || csn.length() == 0) throw new IllegalArgumentException("The csn argument is required");
        EntityManager em = entityManager;
        TypedQuery<Etudiant> q = em.createQuery("SELECT o FROM Etudiant AS o WHERE o.csn = :csn", Etudiant.class);
        q.setParameter("csn", csn);
        return q;
    }

    public TypedQuery<Etudiant> findEtudiantsByCsnEquals(String csn, String sortFieldName, String sortOrder) {
        if (csn == null || csn.length() == 0) throw new IllegalArgumentException("The csn argument is required");
        EntityManager em = entityManager;
        StringBuilder queryBuilder = new StringBuilder("SELECT o FROM Etudiant AS o WHERE o.csn = :csn");
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            queryBuilder.append(" ORDER BY ").append(sortFieldName);
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                queryBuilder.append(" ").append(sortOrder);
            }
        }
        TypedQuery<Etudiant> q = em.createQuery(queryBuilder.toString(), Etudiant.class);
        q.setParameter("csn", csn);
        return q;
    }

    public TypedQuery<Etudiant> findEtudiantsByEppnLike(String eppn, String sortFieldName, String sortOrder) {
        if (eppn == null || eppn.length() == 0) throw new IllegalArgumentException("The eppn argument is required");
        eppn = eppn.replace('*', '%');
        if (eppn.charAt(0) != '%') {
            eppn = "%" + eppn;
        }
        if (eppn.charAt(eppn.length() - 1) != '%') {
            eppn = eppn + "%";
        }
        EntityManager em = entityManager;
        StringBuilder queryBuilder = new StringBuilder("SELECT o FROM Etudiant AS o WHERE LOWER(o.eppn) LIKE LOWER(:eppn)");
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            queryBuilder.append(" ORDER BY ").append(sortFieldName);
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                queryBuilder.append(" ").append(sortOrder);
            }
        }
        TypedQuery<Etudiant> q = em.createQuery(queryBuilder.toString(), Etudiant.class);
        q.setParameter("eppn", eppn);
        return q;
    }

    public TypedQuery<Etudiant> findEtudiantsByEtablissementEquals(String etablissement, String sortFieldName, String sortOrder) {
        if (etablissement == null || etablissement.length() == 0) throw new IllegalArgumentException("The etablissement argument is required");
        EntityManager em = entityManager;
        StringBuilder queryBuilder = new StringBuilder("SELECT o FROM Etudiant AS o WHERE o.etablissement = :etablissement");
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            queryBuilder.append(" ORDER BY ").append(sortFieldName);
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                queryBuilder.append(" ").append(sortOrder);
            }
        }
        TypedQuery<Etudiant> q = em.createQuery(queryBuilder.toString(), Etudiant.class);
        q.setParameter("etablissement", etablissement);
        return q;
    }

    public List<Etudiant> findEtudiantEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Etudiant o", Etudiant.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public final EntityManager entityManager() {
        EntityManager em = entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public long countEtudiants() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Etudiant o", Long.class).getSingleResult();
    }

    public List<Etudiant> findAllEtudiants() {
        return entityManager().createQuery("SELECT o FROM Etudiant o", Etudiant.class).getResultList();
    }

    public List<Etudiant> findAllEtudiants(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Etudiant o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Etudiant.class).getResultList();
    }

    public Etudiant findEtudiant(Long id) {
        if (id == null) return null;
        return entityManager().find(Etudiant.class, id);
    }

    public List<Etudiant> findEtudiantEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
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
    public Etudiant merge(Etudiant etudiant) {
        if (this.entityManager == null) this.entityManager = entityManager();
        Etudiant merged = this.entityManager.merge(etudiant);
        this.entityManager.flush();
        return merged;
    }

    @Transactional
    public void persist(Etudiant etudiant) {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(etudiant);
    }

    @Transactional
    public void remove(long id) {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Etudiant attached = findEtudiant(id);
            this.entityManager.remove(attached);
        }
    }

    @Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

    @Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

    public TypedQuery<Etudiant> findEtablissements(Integer annee, String dateFilter, String searchString) {

        EntityManager em = entityManager;
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Etudiant> query = criteriaBuilder.createQuery(Etudiant.class);
        Root<Etudiant> etudiantRoot = query.from(Etudiant.class);
        final List<Predicate> predicates = new ArrayList<Predicate>();

        if(dateFilter != null && dateFilter != ""){
            try {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
                Date dateBeginFilter = format.parse(dateFilter);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateBeginFilter);
                calendar.add(Calendar.DATE, 1);
                Date dateEndFilter = calendar.getTime();
                predicates.add(criteriaBuilder.between(etudiantRoot.<Date>get("dateInscription"), dateBeginFilter, dateEndFilter));
            } catch (ParseException e) {
                log.warn("format de date incorrect : " + dateFilter, e);
            }
        }else{
            dateFilter = "";
            Date now = new Date();
            if(annee==null) {
                SimpleDateFormat anneeDf = new SimpleDateFormat("yyyy");
                annee = Integer.valueOf(anneeDf.format(now));
                if(now.getMonth() >= 0 && now.getMonth() <8) annee--;
            }
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
            try {
                Date dateBegin = format.parse("01/09/"+annee);
                Date dateEnd = format.parse("31/08/"+(annee+1));
                predicates.add(criteriaBuilder.between(etudiantRoot.<Date>get("dateInscription"), dateBegin, dateEnd));
            } catch (ParseException e) {
                log.error("Problème date de debut ou de fin", e);
            }
        }

        if(searchString != null && searchString!=""){
            Expression<Boolean> fullTestSearchExpression = criteriaBuilder.function("fts", Boolean.class, criteriaBuilder.literal(searchString));
            predicates.add(criteriaBuilder.isTrue(fullTestSearchExpression));
        }else{
            searchString="";
        }

        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        query.select(etudiantRoot.get("etablissement")).groupBy(etudiantRoot.get("etablissement"));
        return em.createQuery(query);
    }

    public List<String> findAnnees() {
        EntityManager em = entityManager;
        String sql = "SELECT CAST(DATE_PART('year', date_inscription) AS INTEGER) AS year FROM etudiant GROUP BY year ORDER BY year DESC";
        return em.createNativeQuery(sql).getResultList();
    }

    public TypedQuery<Etudiant> findEtudiants(Integer annee, String dateFilter, String etablissementFilter, String searchString, Integer page, Integer size, String sortFieldName, String sortOrder) {
        int sizeNo = size == null ? 10 : size.intValue();
        final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;

        if(sortFieldName==null){
            sortFieldName = "dateRecharge";
            sortOrder = "desc";
        }

        EntityManager em = entityManager;
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Etudiant> query = criteriaBuilder.createQuery(Etudiant.class);
        Root<Etudiant> etudiantRoot = query.from(Etudiant.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        List<Order> orders = new ArrayList<Order>();

        if(dateFilter != null && dateFilter != ""){
            try {
                DateFormat formatFilter = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
                Date dateBeginFilter = formatFilter.parse(dateFilter);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateBeginFilter);
                calendar.add(Calendar.DATE, 1);
                Date dateEndFilter = calendar.getTime();
                predicates.add(criteriaBuilder.between(etudiantRoot.<Date>get("dateRecharge"), dateBeginFilter, dateEndFilter));
            } catch (ParseException e) {
                log.warn("format de date incorrect : " + dateFilter, e);
            }
        }else{
            dateFilter = "";
            Date now = new Date();
            if(annee==null) {
                SimpleDateFormat anneeDf = new SimpleDateFormat("yyyy");
                annee = Integer.valueOf(anneeDf.format(now));
                if(now.getMonth() >= 0 && now.getMonth() <8) annee--;
            }
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
            try {
                Date dateBegin = format.parse("01/09/"+annee);
                Date dateEnd = format.parse("31/08/"+(annee+1));
                predicates.add(criteriaBuilder.or(criteriaBuilder.between(etudiantRoot.<Date>get("dateInscription"), dateBegin, dateEnd), criteriaBuilder.between(etudiantRoot.<Date>get("dateRecharge"), dateBegin, dateEnd)));
            } catch (ParseException e) {
                log.error("Problème date de debut ou de fin", e);
            }
        }

        if(searchString != null && searchString!=""){
            Expression<Boolean> fullTestSearchExpression = criteriaBuilder.function("fts", Boolean.class, criteriaBuilder.literal(searchString));
            Expression<Double> fullTestSearchRanking = criteriaBuilder.function("ts_rank", Double.class, criteriaBuilder.literal(searchString));
            predicates.add(criteriaBuilder.isTrue(fullTestSearchExpression));
            if(sortOrder.equals("asc")) {
                orders.add(criteriaBuilder.asc(fullTestSearchRanking));
            } else {
                orders.add(criteriaBuilder.desc(fullTestSearchRanking));
            }
        }else{
            searchString="";
        }

        if(etablissementFilter != null && etablissementFilter != ""){
            predicates.add(criteriaBuilder.equal(etudiantRoot.<String>get("etablissement"), etablissementFilter));
        }else {
            etablissementFilter = "";
        }
        if(sortOrder.equals("asc")) {
            orders.add(criteriaBuilder.asc(etudiantRoot.get(sortFieldName)));
        } else {
            orders.add(criteriaBuilder.desc(etudiantRoot.get(sortFieldName)));
        }

        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        query.orderBy(orders);
        query.select(etudiantRoot);
        return em.createQuery(query).setFirstResult(firstResult).setMaxResults(sizeNo);
    }

    public long countFindEtudiants(Integer annee, String dateFilter, String etablissementFilter, String searchString, Integer page, Integer size) {

        EntityManager em = entityManager;
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Etudiant> etudiantRoot = query.from(Etudiant.class);
        final List<Predicate> predicates = new ArrayList<Predicate>();

        if(dateFilter != null && dateFilter != ""){
            try {
                DateFormat formatFilter = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
                Date dateBeginFilter = formatFilter.parse(dateFilter);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateBeginFilter);
                calendar.add(Calendar.DATE, 1);
                Date dateEndFilter = calendar.getTime();
                predicates.add(criteriaBuilder.or(criteriaBuilder.between(etudiantRoot.<Date>get("dateRecharge"), dateBeginFilter, dateEndFilter), criteriaBuilder.between(etudiantRoot.<Date>get("dateInscription"), dateBeginFilter, dateEndFilter)));
            } catch (ParseException e) {
                log.warn("format de date incorrect : " + dateFilter, e);
            }
        }else {
            dateFilter = "";
            Date now = new Date();
            if(annee==null) {
                SimpleDateFormat anneeDf = new SimpleDateFormat("yyyy");
                annee = Integer.valueOf(anneeDf.format(now));
                if(now.getMonth() >= 0 && now.getMonth() <8) annee--;
            }
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
            try {
                Date dateBegin = format.parse("01/09/"+annee);
                Date dateEnd = format.parse("31/08/"+(annee+1));
                predicates.add(criteriaBuilder.between(etudiantRoot.<Date>get("dateRecharge"), dateBegin, dateEnd));
            } catch (ParseException e) {
                log.error("Problème date de debut ou de fin", e);
            }
        }

        if(searchString != null && searchString!=""){
            Expression<Boolean> fullTestSearchExpression = criteriaBuilder.function("fts", Boolean.class, criteriaBuilder.literal(searchString));
            predicates.add(criteriaBuilder.isTrue(fullTestSearchExpression));
        }else{
            searchString="";
        }

        if(etablissementFilter != null && etablissementFilter != ""){
            predicates.add(criteriaBuilder.equal(etudiantRoot.<String>get("etablissement"), etablissementFilter));
        }else {
            etablissementFilter = "";
        }

        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        query.select(criteriaBuilder.count(etudiantRoot));
        return em.createQuery(query).getSingleResult();
    }

}
