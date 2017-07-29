/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.com.greenApp.controllers;

import co.com.greenApp.controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.com.greenApp.entities.Module;
import co.com.greenApp.entities.ModuleDescription;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author wsalazar@ias.com.co
 */
public class ModuleDescriptionJpaController implements Serializable {

    public ModuleDescriptionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ModuleDescription moduleDescription) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Module idModule = moduleDescription.getIdModule();
            if (idModule != null) {
                idModule = em.getReference(idModule.getClass(), idModule.getIdModule());
                moduleDescription.setIdModule(idModule);
            }
            em.persist(moduleDescription);
            if (idModule != null) {
                idModule.getModuleDescriptionList().add(moduleDescription);
                idModule = em.merge(idModule);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ModuleDescription moduleDescription) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ModuleDescription persistentModuleDescription = em.find(ModuleDescription.class, moduleDescription.getIdModuleDescription());
            Module idModuleOld = persistentModuleDescription.getIdModule();
            Module idModuleNew = moduleDescription.getIdModule();
            if (idModuleNew != null) {
                idModuleNew = em.getReference(idModuleNew.getClass(), idModuleNew.getIdModule());
                moduleDescription.setIdModule(idModuleNew);
            }
            moduleDescription = em.merge(moduleDescription);
            if (idModuleOld != null && !idModuleOld.equals(idModuleNew)) {
                idModuleOld.getModuleDescriptionList().remove(moduleDescription);
                idModuleOld = em.merge(idModuleOld);
            }
            if (idModuleNew != null && !idModuleNew.equals(idModuleOld)) {
                idModuleNew.getModuleDescriptionList().add(moduleDescription);
                idModuleNew = em.merge(idModuleNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = moduleDescription.getIdModuleDescription();
                if (findModuleDescription(id) == null) {
                    throw new NonexistentEntityException("The moduleDescription with id " + id + " no longer exists.");
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
            ModuleDescription moduleDescription;
            try {
                moduleDescription = em.getReference(ModuleDescription.class, id);
                moduleDescription.getIdModuleDescription();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The moduleDescription with id " + id + " no longer exists.", enfe);
            }
            Module idModule = moduleDescription.getIdModule();
            if (idModule != null) {
                idModule.getModuleDescriptionList().remove(moduleDescription);
                idModule = em.merge(idModule);
            }
            em.remove(moduleDescription);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ModuleDescription> findModuleDescriptionEntities() {
        return findModuleDescriptionEntities(true, -1, -1);
    }

    public List<ModuleDescription> findModuleDescriptionEntities(int maxResults, int firstResult) {
        return findModuleDescriptionEntities(false, maxResults, firstResult);
    }

    private List<ModuleDescription> findModuleDescriptionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ModuleDescription.class));
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

    public ModuleDescription findModuleDescription(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ModuleDescription.class, id);
        } finally {
            em.close();
        }
    }

    public int getModuleDescriptionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ModuleDescription> rt = cq.from(ModuleDescription.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
