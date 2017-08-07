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
import co.com.greenApp.entities.ModuleDescription;
import java.util.ArrayList;
import java.util.List;
import co.com.greenApp.entities.Foro;
import co.com.greenApp.entities.Module;
import co.com.greenApp.entities.Module_;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

/**
 *
 * @author wsalazar@ias.com.co
 */
public class ModuleJpaController implements Serializable {

    public ModuleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Module module) {
        if (module.getModuleDescriptionList() == null) {
            module.setModuleDescriptionList(new ArrayList<ModuleDescription>());
        }
        if (module.getForoList() == null) {
            module.setForoList(new ArrayList<Foro>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ModuleDescription> attachedModuleDescriptionList = new ArrayList<ModuleDescription>();
            for (ModuleDescription moduleDescriptionListModuleDescriptionToAttach : module.getModuleDescriptionList()) {
                moduleDescriptionListModuleDescriptionToAttach = em.getReference(moduleDescriptionListModuleDescriptionToAttach.getClass(), moduleDescriptionListModuleDescriptionToAttach.getIdModuleDescription());
                attachedModuleDescriptionList.add(moduleDescriptionListModuleDescriptionToAttach);
            }
            module.setModuleDescriptionList(attachedModuleDescriptionList);
            List<Foro> attachedForoList = new ArrayList<Foro>();
            for (Foro foroListForoToAttach : module.getForoList()) {
                foroListForoToAttach = em.getReference(foroListForoToAttach.getClass(), foroListForoToAttach.getIdForo());
                attachedForoList.add(foroListForoToAttach);
            }
            module.setForoList(attachedForoList);
            em.persist(module);
            for (ModuleDescription moduleDescriptionListModuleDescription : module.getModuleDescriptionList()) {
                Module oldIdModuleOfModuleDescriptionListModuleDescription = moduleDescriptionListModuleDescription.getIdModule();
                moduleDescriptionListModuleDescription.setIdModule(module);
                moduleDescriptionListModuleDescription = em.merge(moduleDescriptionListModuleDescription);
                if (oldIdModuleOfModuleDescriptionListModuleDescription != null) {
                    oldIdModuleOfModuleDescriptionListModuleDescription.getModuleDescriptionList().remove(moduleDescriptionListModuleDescription);
                    oldIdModuleOfModuleDescriptionListModuleDescription = em.merge(oldIdModuleOfModuleDescriptionListModuleDescription);
                }
            }
            for (Foro foroListForo : module.getForoList()) {
                Module oldIdModuleOfForoListForo = foroListForo.getIdModule();
                foroListForo.setIdModule(module);
                foroListForo = em.merge(foroListForo);
                if (oldIdModuleOfForoListForo != null) {
                    oldIdModuleOfForoListForo.getForoList().remove(foroListForo);
                    oldIdModuleOfForoListForo = em.merge(oldIdModuleOfForoListForo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Module module) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Module persistentModule = em.find(Module.class, module.getIdModule());
            List<ModuleDescription> moduleDescriptionListOld = persistentModule.getModuleDescriptionList();
            List<ModuleDescription> moduleDescriptionListNew = module.getModuleDescriptionList();
            List<Foro> foroListOld = persistentModule.getForoList();
            List<Foro> foroListNew = module.getForoList();
            List<String> illegalOrphanMessages = null;
            for (ModuleDescription moduleDescriptionListOldModuleDescription : moduleDescriptionListOld) {
                if (!moduleDescriptionListNew.contains(moduleDescriptionListOldModuleDescription)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ModuleDescription " + moduleDescriptionListOldModuleDescription + " since its idModule field is not nullable.");
                }
            }
            for (Foro foroListOldForo : foroListOld) {
                if (!foroListNew.contains(foroListOldForo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Foro " + foroListOldForo + " since its idModule field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ModuleDescription> attachedModuleDescriptionListNew = new ArrayList<ModuleDescription>();
            for (ModuleDescription moduleDescriptionListNewModuleDescriptionToAttach : moduleDescriptionListNew) {
                moduleDescriptionListNewModuleDescriptionToAttach = em.getReference(moduleDescriptionListNewModuleDescriptionToAttach.getClass(), moduleDescriptionListNewModuleDescriptionToAttach.getIdModuleDescription());
                attachedModuleDescriptionListNew.add(moduleDescriptionListNewModuleDescriptionToAttach);
            }
            moduleDescriptionListNew = attachedModuleDescriptionListNew;
            module.setModuleDescriptionList(moduleDescriptionListNew);
            List<Foro> attachedForoListNew = new ArrayList<Foro>();
            for (Foro foroListNewForoToAttach : foroListNew) {
                foroListNewForoToAttach = em.getReference(foroListNewForoToAttach.getClass(), foroListNewForoToAttach.getIdForo());
                attachedForoListNew.add(foroListNewForoToAttach);
            }
            foroListNew = attachedForoListNew;
            module.setForoList(foroListNew);
            module = em.merge(module);
            for (ModuleDescription moduleDescriptionListNewModuleDescription : moduleDescriptionListNew) {
                if (!moduleDescriptionListOld.contains(moduleDescriptionListNewModuleDescription)) {
                    Module oldIdModuleOfModuleDescriptionListNewModuleDescription = moduleDescriptionListNewModuleDescription.getIdModule();
                    moduleDescriptionListNewModuleDescription.setIdModule(module);
                    moduleDescriptionListNewModuleDescription = em.merge(moduleDescriptionListNewModuleDescription);
                    if (oldIdModuleOfModuleDescriptionListNewModuleDescription != null && !oldIdModuleOfModuleDescriptionListNewModuleDescription.equals(module)) {
                        oldIdModuleOfModuleDescriptionListNewModuleDescription.getModuleDescriptionList().remove(moduleDescriptionListNewModuleDescription);
                        oldIdModuleOfModuleDescriptionListNewModuleDescription = em.merge(oldIdModuleOfModuleDescriptionListNewModuleDescription);
                    }
                }
            }
            for (Foro foroListNewForo : foroListNew) {
                if (!foroListOld.contains(foroListNewForo)) {
                    Module oldIdModuleOfForoListNewForo = foroListNewForo.getIdModule();
                    foroListNewForo.setIdModule(module);
                    foroListNewForo = em.merge(foroListNewForo);
                    if (oldIdModuleOfForoListNewForo != null && !oldIdModuleOfForoListNewForo.equals(module)) {
                        oldIdModuleOfForoListNewForo.getForoList().remove(foroListNewForo);
                        oldIdModuleOfForoListNewForo = em.merge(oldIdModuleOfForoListNewForo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = module.getIdModule();
                if (findModule(id) == null) {
                    throw new NonexistentEntityException("The module with id " + id + " no longer exists.");
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
            Module module;
            try {
                module = em.getReference(Module.class, id);
                module.getIdModule();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The module with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ModuleDescription> moduleDescriptionListOrphanCheck = module.getModuleDescriptionList();
            for (ModuleDescription moduleDescriptionListOrphanCheckModuleDescription : moduleDescriptionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Module (" + module + ") cannot be destroyed since the ModuleDescription " + moduleDescriptionListOrphanCheckModuleDescription + " in its moduleDescriptionList field has a non-nullable idModule field.");
            }
            List<Foro> foroListOrphanCheck = module.getForoList();
            for (Foro foroListOrphanCheckForo : foroListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Module (" + module + ") cannot be destroyed since the Foro " + foroListOrphanCheckForo + " in its foroList field has a non-nullable idModule field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(module);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Module> findModuleEntities() {
        return findModuleEntities(true, -1, -1);
    }

    public List<Module> findModuleEntities(int maxResults, int firstResult) {
        return findModuleEntities(false, maxResults, firstResult);
    }

    private List<Module> findModuleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Module.class));
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

    public Module findModule(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Module.class, id);
        } finally {
            em.close();
        }
    }

    public int getModuleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Module> rt = cq.from(Module.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    /**
     * Método que consulta la información de un modulo por medio de su nombre
     * @param nameModule
     * @return Module
     * @throws Exception 
     */
    public Module getModuleByName(String nameModule) throws Exception{
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            Root<Module> module = cq.from(Module.class);
            cq.select(module);
            List<Predicate> predicates = new ArrayList<>();
            
            predicates.add(cb.equal(module.get(Module_.name), nameModule));
            
            cq.where(predicates.toArray(new Predicate[predicates.size()]));
            Query q = em.createQuery(cq);

            if (q.getResultList() != null && !q.getResultList().isEmpty()) {
                return (Module) q.getResultList().get(q.getResultList().size() - 1);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

}
