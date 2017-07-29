/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.com.greenApp.controllers;

import co.com.greenApp.controllers.exceptions.NonexistentEntityException;
import co.com.greenApp.entities.Foro;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.com.greenApp.entities.Module;
import co.com.greenApp.entities.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author wsalazar@ias.com.co
 */
public class ForoJpaController implements Serializable {

    public ForoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Foro foro) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Module idModule = foro.getIdModule();
            if (idModule != null) {
                idModule = em.getReference(idModule.getClass(), idModule.getIdModule());
                foro.setIdModule(idModule);
            }
            User idUser = foro.getIdUser();
            if (idUser != null) {
                idUser = em.getReference(idUser.getClass(), idUser.getIdUser());
                foro.setIdUser(idUser);
            }
            em.persist(foro);
            if (idModule != null) {
                idModule.getForoList().add(foro);
                idModule = em.merge(idModule);
            }
            if (idUser != null) {
                idUser.getForoList().add(foro);
                idUser = em.merge(idUser);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Foro foro) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Foro persistentForo = em.find(Foro.class, foro.getIdForo());
            Module idModuleOld = persistentForo.getIdModule();
            Module idModuleNew = foro.getIdModule();
            User idUserOld = persistentForo.getIdUser();
            User idUserNew = foro.getIdUser();
            if (idModuleNew != null) {
                idModuleNew = em.getReference(idModuleNew.getClass(), idModuleNew.getIdModule());
                foro.setIdModule(idModuleNew);
            }
            if (idUserNew != null) {
                idUserNew = em.getReference(idUserNew.getClass(), idUserNew.getIdUser());
                foro.setIdUser(idUserNew);
            }
            foro = em.merge(foro);
            if (idModuleOld != null && !idModuleOld.equals(idModuleNew)) {
                idModuleOld.getForoList().remove(foro);
                idModuleOld = em.merge(idModuleOld);
            }
            if (idModuleNew != null && !idModuleNew.equals(idModuleOld)) {
                idModuleNew.getForoList().add(foro);
                idModuleNew = em.merge(idModuleNew);
            }
            if (idUserOld != null && !idUserOld.equals(idUserNew)) {
                idUserOld.getForoList().remove(foro);
                idUserOld = em.merge(idUserOld);
            }
            if (idUserNew != null && !idUserNew.equals(idUserOld)) {
                idUserNew.getForoList().add(foro);
                idUserNew = em.merge(idUserNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = foro.getIdForo();
                if (findForo(id) == null) {
                    throw new NonexistentEntityException("The foro with id " + id + " no longer exists.");
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
            Foro foro;
            try {
                foro = em.getReference(Foro.class, id);
                foro.getIdForo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The foro with id " + id + " no longer exists.", enfe);
            }
            Module idModule = foro.getIdModule();
            if (idModule != null) {
                idModule.getForoList().remove(foro);
                idModule = em.merge(idModule);
            }
            User idUser = foro.getIdUser();
            if (idUser != null) {
                idUser.getForoList().remove(foro);
                idUser = em.merge(idUser);
            }
            em.remove(foro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Foro> findForoEntities() {
        return findForoEntities(true, -1, -1);
    }

    public List<Foro> findForoEntities(int maxResults, int firstResult) {
        return findForoEntities(false, maxResults, firstResult);
    }

    private List<Foro> findForoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Foro.class));
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

    public Foro findForo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Foro.class, id);
        } finally {
            em.close();
        }
    }

    public int getForoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Foro> rt = cq.from(Foro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
