package org.esupportail.esupnfccarteculture.repository;

import org.esupportail.esupnfccarteculture.entity.Etudiant;
import org.esupportail.esupnfccarteculture.entity.Salle;
import org.esupportail.esupnfccarteculture.entity.TagLog;
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
public class TagLogRepository {

    protected final static Logger log = LoggerFactory.getLogger(TagLogRepository.class);

    public final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("log", "etudiant", "salle", "date", "tarif", "eppnInit");

    @PersistenceContext
    private EntityManager entityManager;

    public Long countFindTagLogsByEtudiant(Etudiant etudiant) {
        if (etudiant == null) throw new IllegalArgumentException("The etudiant argument is required");
        EntityManager em = entityManager;
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM TagLog AS o WHERE o.etudiant = :etudiant", Long.class);
        q.setParameter("etudiant", etudiant);
        return ((Long) q.getSingleResult());
    }

    public Long countFindTagLogsByDateBetween(Date minDate, Date maxDate) {
        if (minDate == null) throw new IllegalArgumentException("The minDate argument is required");
        if (maxDate == null) throw new IllegalArgumentException("The maxDate argument is required");
        EntityManager em = entityManager;
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM TagLog AS o WHERE o.date BETWEEN :minDate AND :maxDate", Long.class);
        q.setParameter("minDate", minDate);
        q.setParameter("maxDate", maxDate);
        return ((Long) q.getSingleResult());
    }

    public Long countFindTagLogsByDateBetweenAndSalle(Date minDate, Date maxDate, Salle salle) {
        if (minDate == null) throw new IllegalArgumentException("The minDate argument is required");
        if (maxDate == null) throw new IllegalArgumentException("The maxDate argument is required");
        if (salle == null) throw new IllegalArgumentException("The salle argument is required");
        EntityManager em = entityManager;
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM TagLog AS o WHERE o.date BETWEEN :minDate AND :maxDate  AND o.salle = :salle", Long.class);
        q.setParameter("minDate", minDate);
        q.setParameter("maxDate", maxDate);
        q.setParameter("salle", salle);
        return ((Long) q.getSingleResult());
    }

    public Long countFindTagLogsBySalle(Salle salle) {
        if (salle == null) throw new IllegalArgumentException("The salle argument is required");
        EntityManager em = entityManager;
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM TagLog AS o WHERE o.salle = :salle", Long.class);
        q.setParameter("salle", salle);
        return ((Long) q.getSingleResult());
    }

    public TypedQuery<TagLog> findTagLogsByDateBetween(Date minDate, Date maxDate) {
        if (minDate == null) throw new IllegalArgumentException("The minDate argument is required");
        if (maxDate == null) throw new IllegalArgumentException("The maxDate argument is required");
        EntityManager em = entityManager;
        TypedQuery<TagLog> q = em.createQuery("SELECT o FROM TagLog AS o WHERE o.date BETWEEN :minDate AND :maxDate", TagLog.class);
        q.setParameter("minDate", minDate);
        q.setParameter("maxDate", maxDate);
        return q;
    }

    public TypedQuery<TagLog> findTagLogsByDateBetween(Date minDate, Date maxDate, String sortFieldName, String sortOrder) {
        if (minDate == null) throw new IllegalArgumentException("The minDate argument is required");
        if (maxDate == null) throw new IllegalArgumentException("The maxDate argument is required");
        EntityManager em = entityManager;
        StringBuilder queryBuilder = new StringBuilder("SELECT o FROM TagLog AS o WHERE o.date BETWEEN :minDate AND :maxDate");
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            queryBuilder.append(" ORDER BY ").append(sortFieldName);
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                queryBuilder.append(" ").append(sortOrder);
            }
        }
        TypedQuery<TagLog> q = em.createQuery(queryBuilder.toString(), TagLog.class);
        q.setParameter("minDate", minDate);
        q.setParameter("maxDate", maxDate);
        return q;
    }

    public TypedQuery<TagLog> findTagLogsBySalle(Salle salle) {
        if (salle == null) throw new IllegalArgumentException("The salle argument is required");
        EntityManager em = entityManager;
        TypedQuery<TagLog> q = em.createQuery("SELECT o FROM TagLog AS o WHERE o.salle = :salle", TagLog.class);
        q.setParameter("salle", salle);
        return q;
    }

    public TypedQuery<TagLog> findTagLogsBySalle(Salle salle, String sortFieldName, String sortOrder) {
        if (salle == null) throw new IllegalArgumentException("The salle argument is required");
        EntityManager em = entityManager;
        StringBuilder queryBuilder = new StringBuilder("SELECT o FROM TagLog AS o WHERE o.salle = :salle");
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            queryBuilder.append(" ORDER BY ").append(sortFieldName);
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                queryBuilder.append(" ").append(sortOrder);
            }
        }
        TypedQuery<TagLog> q = em.createQuery(queryBuilder.toString(), TagLog.class);
        q.setParameter("salle", salle);
        return q;
    }

    public TypedQuery<TagLog> findTagLogsByDateBetweenAndSalle(Date minDate, Date maxDate, Salle salle) {
        if (minDate == null) throw new IllegalArgumentException("The minDate argument is required");
        if (maxDate == null) throw new IllegalArgumentException("The maxDate argument is required");
        if (salle == null) throw new IllegalArgumentException("The salle argument is required");
        EntityManager em = entityManager;
        TypedQuery<TagLog> q = em.createQuery("SELECT o FROM TagLog AS o WHERE o.date BETWEEN :minDate AND :maxDate  AND o.salle = :salle", TagLog.class);
        q.setParameter("minDate", minDate);
        q.setParameter("maxDate", maxDate);
        q.setParameter("salle", salle);
        return q;
    }

    public TypedQuery<TagLog> findTagLogsByDateBetweenAndSalle(Date minDate, Date maxDate, Salle salle, String sortFieldName, String sortOrder) {
        if (minDate == null) throw new IllegalArgumentException("The minDate argument is required");
        if (maxDate == null) throw new IllegalArgumentException("The maxDate argument is required");
        if (salle == null) throw new IllegalArgumentException("The salle argument is required");
        EntityManager em = entityManager;
        StringBuilder queryBuilder = new StringBuilder("SELECT o FROM TagLog AS o WHERE o.date BETWEEN :minDate AND :maxDate  AND o.salle = :salle");
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            queryBuilder.append(" ORDER BY ").append(sortFieldName);
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                queryBuilder.append(" ").append(sortOrder);
            }
        }
        TypedQuery<TagLog> q = em.createQuery(queryBuilder.toString(), TagLog.class);
        q.setParameter("minDate", minDate);
        q.setParameter("maxDate", maxDate);
        q.setParameter("salle", salle);
        return q;
    }

    public TypedQuery<TagLog> findTagLogsByEtudiant(Etudiant etudiant) {
        if (etudiant == null) throw new IllegalArgumentException("The etudiant argument is required");
        EntityManager em = entityManager;
        TypedQuery<TagLog> q = em.createQuery("SELECT o FROM TagLog AS o WHERE o.etudiant = :etudiant", TagLog.class);
        q.setParameter("etudiant", etudiant);
        return q;
    }

    public TypedQuery<TagLog> findTagLogsByEtudiant(Etudiant etudiant, String sortFieldName, String sortOrder) {
        if (etudiant == null) throw new IllegalArgumentException("The etudiant argument is required");
        EntityManager em = entityManager;
        StringBuilder queryBuilder = new StringBuilder("SELECT o FROM TagLog AS o WHERE o.etudiant = :etudiant");
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            queryBuilder.append(" ORDER BY ").append(sortFieldName);
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                queryBuilder.append(" ").append(sortOrder);
            }
        }
        TypedQuery<TagLog> q = em.createQuery(queryBuilder.toString(), TagLog.class);
        q.setParameter("etudiant", etudiant);
        return q;
    }

    public TagLog findTagLog(Long id) {
        if (id == null) return null;
        return entityManager().find(TagLog.class, id);
    }

    public List<TagLog> findAllTagLogs() {
        return entityManager().createQuery("SELECT o FROM TagLog o", TagLog.class).getResultList();
    }

    public final EntityManager entityManager() {
        EntityManager em = entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public long countTagLogs() {
        return entityManager().createQuery("SELECT COUNT(o) FROM TagLog o", Long.class).getSingleResult();
    }

    public List<TagLog> findAllTagLogs(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM TagLog o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, TagLog.class).getResultList();
    }

    public List<TagLog> findTagLogEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM TagLog o", TagLog.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public List<TagLog> findTagLogEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM TagLog o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, TagLog.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public TypedQuery<TagLog> findTagLogsBySalleAndDateBetween(Salle salle, Date minDate, Date maxDate) {
        if (minDate == null) throw new IllegalArgumentException("The minDate argument is required");
        if (maxDate == null) throw new IllegalArgumentException("The maxDate argument is required");
        EntityManager em = entityManager;
        TypedQuery<TagLog> q = em.createQuery("SELECT o FROM TagLog AS o WHERE o.salle = :salle AND o.date BETWEEN :minDate AND :maxDate ORDER BY o.date DESC", TagLog.class);
        q.setParameter("minDate", minDate);
        q.setParameter("maxDate", maxDate);
        q.setParameter("salle", salle);
        return q;
    }

    public List<String> findAnnees() {
        EntityManager em = entityManager;
        String sql = "SELECT CAST(DATE_PART('year', date) AS INTEGER) AS year FROM tag_log GROUP BY year ORDER BY year DESC";
        return em.createNativeQuery(sql).getResultList();
    }

    public TypedQuery<TagLog> findTagLogs(Integer annee, String salleFilter, String dateFilter, String searchString, Integer page, Integer size, String sortFieldName, String sortOrder) {

        EntityManager em = entityManager;
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<TagLog> query = criteriaBuilder.createQuery(TagLog.class);
        Root<TagLog> taglogRoot = query.from(TagLog.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        List<Order> orders = new ArrayList<Order>();


        if(salleFilter != null && salleFilter != ""){
            Join<TagLog, Salle> taglogSalleJoin = taglogRoot.join("salle");
            predicates.add(criteriaBuilder.equal(taglogSalleJoin.get("nom"), salleFilter));
        }else{
            salleFilter="";
        }

        if(sortOrder.equals("asc")){
            orders.add(criteriaBuilder.asc(taglogRoot.get(sortFieldName)));
        } else {
            orders.add(criteriaBuilder.desc(taglogRoot.get(sortFieldName)));
        }

        if(dateFilter != null && dateFilter != ""){
            try {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
                Date dateBegin = format.parse(dateFilter);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateBegin);
                calendar.add(Calendar.DATE, 1);
                Date dateEnd = calendar.getTime();
                predicates.add(criteriaBuilder.between(taglogRoot.<Date>get("date"), dateBegin, dateEnd));
            } catch (ParseException e) {
                log.warn("format de date incorrect : " + dateFilter, e);
                dateFilter = "erreur";
            }
        } else {
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
                predicates.add(criteriaBuilder.between(taglogRoot.<Date>get("date"), dateBegin, dateEnd));
            } catch (ParseException e) {
                log.error("Problème date de debut ou de fin", e);
            }
        }


        if(searchString != null && searchString != ""){
            Expression<Boolean> fullTestSearchExpression = criteriaBuilder.function("fts", Boolean.class, criteriaBuilder.literal(searchString));
            Expression<Double> fullTestSearchRanking = criteriaBuilder.function("ts_rank", Double.class, criteriaBuilder.literal(searchString));
            predicates.add(criteriaBuilder.isTrue(fullTestSearchExpression));
            orders.add(criteriaBuilder.desc(fullTestSearchRanking));
        }else{
            searchString = "";
        }

        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        query.orderBy(orders);
        query.select(taglogRoot);

        int sizeNo = size == null ? 10 : size.intValue();
        final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;

        return em.createQuery(query).setFirstResult(firstResult).setMaxResults(sizeNo);
    }

    public long countFindTagLogs(Integer annee, String salleFilter, String dateFilter, String searchString) {

        EntityManager em = entityManager;
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<TagLog> taglogRoot = query.from(TagLog.class);
        List<Predicate> predicates = new ArrayList<Predicate>();

        if(salleFilter != null && salleFilter != ""){
            Join<TagLog, Salle> taglogSalleJoin = taglogRoot.join("salle");
            predicates.add(criteriaBuilder.equal(taglogSalleJoin.get("nom"), salleFilter));
        }else{
            salleFilter="";
        }

        if(dateFilter != null && dateFilter != ""){
            try {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
                Date dateBegin = format.parse(dateFilter);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateBegin);
                calendar.add(Calendar.DATE, 1);
                Date dateEnd = calendar.getTime();
                predicates.add(criteriaBuilder.between(taglogRoot.<Date>get("date"), dateBegin, dateEnd));
            } catch (ParseException e) {
                log.warn("format de date incorrect : " + dateFilter, e);
                dateFilter = "erreur";
            }
        } else {
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
                predicates.add(criteriaBuilder.between(taglogRoot.<Date>get("date"), dateBegin, dateEnd));
            } catch (ParseException e) {
                log.error("Problème date de debut ou de fin", e);
            }
        }


        if(searchString != null && searchString != ""){
            Expression<Boolean> fullTestSearchExpression = criteriaBuilder.function("fts", Boolean.class, criteriaBuilder.literal(searchString));
            predicates.add(criteriaBuilder.isTrue(fullTestSearchExpression));
        }else{
            searchString = "";
        }

        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        query.select(criteriaBuilder.count(taglogRoot));

        return em.createQuery(query).getSingleResult();
    }

    @Transactional
    public TagLog merge(TagLog tagLog) {
        if (this.entityManager == null) this.entityManager = entityManager();
        TagLog merged = entityManager.merge(tagLog);
        this.entityManager.flush();
        return merged;
    }

    @Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

    @Transactional
    public void persist(TagLog tagLog) {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(tagLog);
    }

    @Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

    @Transactional
    public void remove(Long id) {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            TagLog attached = findTagLog(id);
            this.entityManager.remove(attached);
        }
    }
}
