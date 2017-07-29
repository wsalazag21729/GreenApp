/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.com.greenApp.controllers;

import co.com.greenApp.controllers.exceptions.IllegalOrphanException;
import co.com.greenApp.controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.com.greenApp.entities.Foro;
import co.com.greenApp.entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author wsalazar@ias.com.co
 */
public class UserJpaController implements Serializable {

    public UserJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(User user) {
        if (user.getForoList() == null) {
            user.setForoList(new ArrayList<Foro>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Foro> attachedForoList = new ArrayList<Foro>();
            for (Foro foroListForoToAttach : user.getForoList()) {
                foroListForoToAttach = em.getReference(foroListForoToAttach.getClass(), foroListForoToAttach.getIdForo());
                attachedForoList.add(foroListForoToAttach);
            }
            user.setForoList(attachedForoList);
            em.persist(user);
            for (Foro foroListForo : user.getForoList()) {
                User oldIdUserOfForoListForo = foroListForo.getIdUser();
                foroListForo.setIdUser(user);
                foroListForo = em.merge(foroListForo);
                if (oldIdUserOfForoListForo != null) {
                    oldIdUserOfForoListForo.getForoList().remove(foroListForo);
                    oldIdUserOfForoListForo = em.merge(oldIdUserOfForoListForo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(User user) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User persistentUser = em.find(User.class, user.getIdUser());
            List<Foro> foroListOld = persistentUser.getForoList();
            List<Foro> foroListNew = user.getForoList();
            List<String> illegalOrphanMessages = null;
            for (Foro foroListOldForo : foroListOld) {
                if (!foroListNew.contains(foroListOldForo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Foro " + foroListOldForo + " since its idUser field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Foro> attachedForoListNew = new ArrayList<Foro>();
            for (Foro foroListNewForoToAttach : foroListNew) {
                foroListNewForoToAttach = em.getReference(foroListNewForoToAttach.getClass(), foroListNewForoToAttach.getIdForo());
                attachedForoListNew.add(foroListNewForoToAttach);
            }
            foroListNew = attachedForoListNew;
            user.setForoList(foroListNew);
            user = em.merge(user);
            for (Foro foroListNewForo : foroListNew) {
                if (!foroListOld.contains(foroListNewForo)) {
                    User oldIdUserOfForoListNewForo = foroListNewForo.getIdUser();
                    foroListNewForo.setIdUser(user);
                    foroListNewForo = em.merge(foroListNewForo);
                    if (oldIdUserOfForoListNewForo != null && !oldIdUserOfForoListNewForo.equals(user)) {
                        oldIdUserOfForoListNewForo.getForoList().remove(foroListNewForo);
                        oldIdUserOfForoListNewForo = em.merge(oldIdUserOfForoListNewForo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = user.getIdUser();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
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
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getIdUser();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Foro> foroListOrphanCheck = user.getForoList();
            for (Foro foroListOrphanCheckForo : foroListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Foro " + foroListOrphanCheckForo + " in its foroList field has a non-nullable idUser field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(user);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
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

    public User findUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
