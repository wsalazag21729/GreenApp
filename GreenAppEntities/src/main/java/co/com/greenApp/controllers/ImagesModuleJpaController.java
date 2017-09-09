package co.com.greenApp.controllers;

import co.com.greenApp.controllers.exceptions.NonexistentEntityException;
import co.com.greenApp.entities.ImagesModule;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.com.greenApp.entities.Module;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author wsalazar@ias.com.co
 */
public class ImagesModuleJpaController implements Serializable {

    public ImagesModuleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ImagesModule imagesModule) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Module idModule = imagesModule.getIdModule();
            if (idModule != null) {
                idModule = em.getReference(idModule.getClass(), idModule.getIdModule());
                imagesModule.setIdModule(idModule);
            }
            em.persist(imagesModule);
            if (idModule != null) {
                idModule.getImagesModuleList().add(imagesModule);
                idModule = em.merge(idModule);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ImagesModule imagesModule) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ImagesModule persistentImagesModule = em.find(ImagesModule.class, imagesModule.getIdImagesModule());
            Module idModuleOld = persistentImagesModule.getIdModule();
            Module idModuleNew = imagesModule.getIdModule();
            if (idModuleNew != null) {
                idModuleNew = em.getReference(idModuleNew.getClass(), idModuleNew.getIdModule());
                imagesModule.setIdModule(idModuleNew);
            }
            imagesModule = em.merge(imagesModule);
            if (idModuleOld != null && !idModuleOld.equals(idModuleNew)) {
                idModuleOld.getImagesModuleList().remove(imagesModule);
                idModuleOld = em.merge(idModuleOld);
            }
            if (idModuleNew != null && !idModuleNew.equals(idModuleOld)) {
                idModuleNew.getImagesModuleList().add(imagesModule);
                idModuleNew = em.merge(idModuleNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = imagesModule.getIdImagesModule();
                if (findImagesModule(id) == null) {
                    throw new NonexistentEntityException("The imagesModule with id " + id + " no longer exists.");
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
            ImagesModule imagesModule;
            try {
                imagesModule = em.getReference(ImagesModule.class, id);
                imagesModule.getIdImagesModule();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The imagesModule with id " + id + " no longer exists.", enfe);
            }
            Module idModule = imagesModule.getIdModule();
            if (idModule != null) {
                idModule.getImagesModuleList().remove(imagesModule);
                idModule = em.merge(idModule);
            }
            em.remove(imagesModule);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ImagesModule> findImagesModuleEntities() {
        return findImagesModuleEntities(true, -1, -1);
    }

    public List<ImagesModule> findImagesModuleEntities(int maxResults, int firstResult) {
        return findImagesModuleEntities(false, maxResults, firstResult);
    }

    private List<ImagesModule> findImagesModuleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ImagesModule.class));
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

    public ImagesModule findImagesModule(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ImagesModule.class, id);
        } finally {
            em.close();
        }
    }

    public int getImagesModuleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ImagesModule> rt = cq.from(ImagesModule.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
