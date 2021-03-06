package co.com.greenApp.controllers;

import co.com.greenApp.controllers.exceptions.NonexistentEntityException;
import co.com.greenApp.entities.Comments;
import co.com.greenApp.entities.Comments_;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.com.greenApp.entities.Discussion;
import co.com.greenApp.entities.Discussion_;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

/**
 *
 * @author wsalazar@ias.com.co
 */
public class CommentsJpaController implements Serializable {

    public CommentsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comments comments) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Discussion idDiscussion = comments.getIdDiscussion();
            if (idDiscussion != null) {
                idDiscussion = em.getReference(idDiscussion.getClass(), idDiscussion.getIdDiscussion());
                comments.setIdDiscussion(idDiscussion);
            }
            em.persist(comments);
            if (idDiscussion != null) {
                idDiscussion.getCommentsList().add(comments);
                idDiscussion = em.merge(idDiscussion);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comments comments) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comments persistentComments = em.find(Comments.class, comments.getIdComment());
            Discussion idDiscussionOld = persistentComments.getIdDiscussion();
            Discussion idDiscussionNew = comments.getIdDiscussion();
            if (idDiscussionNew != null) {
                idDiscussionNew = em.getReference(idDiscussionNew.getClass(), idDiscussionNew.getIdDiscussion());
                comments.setIdDiscussion(idDiscussionNew);
            }
            comments = em.merge(comments);
            if (idDiscussionOld != null && !idDiscussionOld.equals(idDiscussionNew)) {
                idDiscussionOld.getCommentsList().remove(comments);
                idDiscussionOld = em.merge(idDiscussionOld);
            }
            if (idDiscussionNew != null && !idDiscussionNew.equals(idDiscussionOld)) {
                idDiscussionNew.getCommentsList().add(comments);
                idDiscussionNew = em.merge(idDiscussionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = comments.getIdComment();
                if (findComments(id) == null) {
                    throw new NonexistentEntityException("The comments with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comments comments;
            try {
                comments = em.getReference(Comments.class, id);
                comments.getIdComment();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comments with id " + id + " no longer exists.", enfe);
            }
            Discussion idDiscussion = comments.getIdDiscussion();
            if (idDiscussion != null) {
                idDiscussion.getCommentsList().remove(comments);
                idDiscussion = em.merge(idDiscussion);
            }
            em.remove(comments);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comments> findCommentsEntities() {
        return findCommentsEntities(true, -1, -1);
    }

    public List<Comments> findCommentsEntities(int maxResults, int firstResult) {
        return findCommentsEntities(false, maxResults, firstResult);
    }

    private List<Comments> findCommentsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comments.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Comments findComments(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comments.class, id);
        } finally {
            em.close();
        }
    }

    public int getCommentsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comments> rt = cq.from(Comments.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    /**
     * Método que consulta la lista de comentarios de una discusión que cumlen con los filtros
     * @param idDiscussion
     * @param dateTime
     * @param searchFilter
     * @return List<Comments>
     */
    public List<Comments> getInfoCommentsByFilters(Integer idDiscussion, Long dateTime, String searchFilter) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            Root<Comments> comments = cq.from(Comments.class);
            cq.select(comments);
            List<Predicate> predicates = new ArrayList<Predicate>();
            Predicate[] predicatesOr = new Predicate[2];

            predicates.add(cb.and(cb.equal(comments.get(Comments_.idDiscussion).get(Discussion_.idDiscussion), idDiscussion)));

            Calendar initialDate = Calendar.getInstance();
            Calendar finalDate = Calendar.getInstance();
            if (dateTime != null) {
                initialDate.setTime(new Date(dateTime));
                initialDate.set(Calendar.HOUR, 0);
                initialDate.set(Calendar.MINUTE, 0);
                initialDate.set(Calendar.SECOND, 0);

                finalDate.setTime(new Date(dateTime));
                finalDate.set(Calendar.HOUR, 23);
                finalDate.set(Calendar.MINUTE, 59);
            }

            if (dateTime != null) {
                Predicate predicate = cb.between(comments.get(Comments_.createTimestamp), initialDate.getTime(), finalDate.getTime());
                predicates.add(cb.and(predicate));
            }

            if (searchFilter != null && !"".equals(searchFilter)) {
                predicatesOr[0] = cb.like(comments.get(Comments_.userName), "%" + searchFilter + "%");
                predicatesOr[1] = cb.like(comments.get(Comments_.comment), "%" + searchFilter + "%");
            }

            if (searchFilter != null && !"".equals(searchFilter)) {
                predicates.add(cb.or(predicatesOr));
            }
            cq.where(predicates.toArray(new Predicate[predicates.size()]));
            cq.orderBy(cb.desc(comments.get(Comments_.createTimestamp)));
            Query q = em.createQuery(cq);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Ocurrió un error tratando de consultar los comentarios de una discussión");
        } finally {
            em.close();
        }
    }

}
