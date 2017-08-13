package co.com.greenApp.controllers;

import co.com.greenApp.controllers.exceptions.IllegalOrphanException;
import co.com.greenApp.controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.com.greenApp.entities.Module;
import co.com.greenApp.entities.Comments;
import co.com.greenApp.entities.Discussion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author wsalazar@ias.com.co
 */
public class DiscussionJpaController implements Serializable {

    public DiscussionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Discussion discussion) {
        if (discussion.getCommentsList() == null) {
            discussion.setCommentsList(new ArrayList<Comments>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Module idModule = discussion.getIdModule();
            if (idModule != null) {
                idModule = em.getReference(idModule.getClass(), idModule.getIdModule());
                discussion.setIdModule(idModule);
            }
            List<Comments> attachedCommentsList = new ArrayList<Comments>();
            for (Comments commentsListCommentsToAttach : discussion.getCommentsList()) {
                commentsListCommentsToAttach = em.getReference(commentsListCommentsToAttach.getClass(), commentsListCommentsToAttach.getIdComment());
                attachedCommentsList.add(commentsListCommentsToAttach);
            }
            discussion.setCommentsList(attachedCommentsList);
            em.persist(discussion);
            if (idModule != null) {
                idModule.getDiscussionList().add(discussion);
                idModule = em.merge(idModule);
            }
            for (Comments commentsListComments : discussion.getCommentsList()) {
                Discussion oldIdDiscussionOfCommentsListComments = commentsListComments.getIdDiscussion();
                commentsListComments.setIdDiscussion(discussion);
                commentsListComments = em.merge(commentsListComments);
                if (oldIdDiscussionOfCommentsListComments != null) {
                    oldIdDiscussionOfCommentsListComments.getCommentsList().remove(commentsListComments);
                    oldIdDiscussionOfCommentsListComments = em.merge(oldIdDiscussionOfCommentsListComments);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Discussion discussion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Discussion persistentDiscussion = em.find(Discussion.class, discussion.getIdDiscussion());
            Module idModuleOld = persistentDiscussion.getIdModule();
            Module idModuleNew = discussion.getIdModule();
            List<Comments> commentsListOld = persistentDiscussion.getCommentsList();
            List<Comments> commentsListNew = discussion.getCommentsList();
            List<String> illegalOrphanMessages = null;
            for (Comments commentsListOldComments : commentsListOld) {
                if (!commentsListNew.contains(commentsListOldComments)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Comments " + commentsListOldComments + " since its idDiscussion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idModuleNew != null) {
                idModuleNew = em.getReference(idModuleNew.getClass(), idModuleNew.getIdModule());
                discussion.setIdModule(idModuleNew);
            }
            List<Comments> attachedCommentsListNew = new ArrayList<Comments>();
            for (Comments commentsListNewCommentsToAttach : commentsListNew) {
                commentsListNewCommentsToAttach = em.getReference(commentsListNewCommentsToAttach.getClass(), commentsListNewCommentsToAttach.getIdComment());
                attachedCommentsListNew.add(commentsListNewCommentsToAttach);
            }
            commentsListNew = attachedCommentsListNew;
            discussion.setCommentsList(commentsListNew);
            discussion = em.merge(discussion);
            if (idModuleOld != null && !idModuleOld.equals(idModuleNew)) {
                idModuleOld.getDiscussionList().remove(discussion);
                idModuleOld = em.merge(idModuleOld);
            }
            if (idModuleNew != null && !idModuleNew.equals(idModuleOld)) {
                idModuleNew.getDiscussionList().add(discussion);
                idModuleNew = em.merge(idModuleNew);
            }
            for (Comments commentsListNewComments : commentsListNew) {
                if (!commentsListOld.contains(commentsListNewComments)) {
                    Discussion oldIdDiscussionOfCommentsListNewComments = commentsListNewComments.getIdDiscussion();
                    commentsListNewComments.setIdDiscussion(discussion);
                    commentsListNewComments = em.merge(commentsListNewComments);
                    if (oldIdDiscussionOfCommentsListNewComments != null && !oldIdDiscussionOfCommentsListNewComments.equals(discussion)) {
                        oldIdDiscussionOfCommentsListNewComments.getCommentsList().remove(commentsListNewComments);
                        oldIdDiscussionOfCommentsListNewComments = em.merge(oldIdDiscussionOfCommentsListNewComments);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = discussion.getIdDiscussion();
                if (findDiscussion(id) == null) {
                    throw new NonexistentEntityException("The discussion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Discussion discussion;
            try {
                discussion = em.getReference(Discussion.class, id);
                discussion.getIdDiscussion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The discussion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Comments> commentsListOrphanCheck = discussion.getCommentsList();
            for (Comments commentsListOrphanCheckComments : commentsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Discussion (" + discussion + ") cannot be destroyed since the Comments " + commentsListOrphanCheckComments + " in its commentsList field has a non-nullable idDiscussion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Module idModule = discussion.getIdModule();
            if (idModule != null) {
                idModule.getDiscussionList().remove(discussion);
                idModule = em.merge(idModule);
            }
            em.remove(discussion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Discussion> findDiscussionEntities() {
        return findDiscussionEntities(true, -1, -1);
    }

    public List<Discussion> findDiscussionEntities(int maxResults, int firstResult) {
        return findDiscussionEntities(false, maxResults, firstResult);
    }

    private List<Discussion> findDiscussionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Discussion.class));
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

    public Discussion findDiscussion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Discussion.class, id);
        } finally {
            em.close();
        }
    }

    public int getDiscussionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Discussion> rt = cq.from(Discussion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
