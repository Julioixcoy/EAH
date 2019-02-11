/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.BlackBox;
import Entidades.Module;
import java.util.ArrayList;
import java.util.Collection;
import Entidades.UserModule;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author julio
 *
 */
public class ModuleJpaController implements Serializable {

    public ModuleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Module module) throws PreexistingEntityException, Exception {
        if (module.getBlackBoxCollection() == null) {
            module.setBlackBoxCollection(new ArrayList<BlackBox>());
        }
        if (module.getUserModuleCollection() == null) {
            module.setUserModuleCollection(new ArrayList<UserModule>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<BlackBox> attachedBlackBoxCollection = new ArrayList<BlackBox>();
            for (BlackBox blackBoxCollectionBlackBoxToAttach : module.getBlackBoxCollection()) {
                blackBoxCollectionBlackBoxToAttach = em.getReference(blackBoxCollectionBlackBoxToAttach.getClass(), blackBoxCollectionBlackBoxToAttach.getIdBlackBox());
                attachedBlackBoxCollection.add(blackBoxCollectionBlackBoxToAttach);
            }
            module.setBlackBoxCollection(attachedBlackBoxCollection);
            Collection<UserModule> attachedUserModuleCollection = new ArrayList<UserModule>();
            for (UserModule userModuleCollectionUserModuleToAttach : module.getUserModuleCollection()) {
                userModuleCollectionUserModuleToAttach = em.getReference(userModuleCollectionUserModuleToAttach.getClass(), userModuleCollectionUserModuleToAttach.getIdUserModule());
                attachedUserModuleCollection.add(userModuleCollectionUserModuleToAttach);
            }
            module.setUserModuleCollection(attachedUserModuleCollection);
            em.persist(module);
            for (BlackBox blackBoxCollectionBlackBox : module.getBlackBoxCollection()) {
                Module oldModuleidModuleOfBlackBoxCollectionBlackBox = blackBoxCollectionBlackBox.getModuleidModule();
                blackBoxCollectionBlackBox.setModuleidModule(module);
                blackBoxCollectionBlackBox = em.merge(blackBoxCollectionBlackBox);
                if (oldModuleidModuleOfBlackBoxCollectionBlackBox != null) {
                    oldModuleidModuleOfBlackBoxCollectionBlackBox.getBlackBoxCollection().remove(blackBoxCollectionBlackBox);
                    oldModuleidModuleOfBlackBoxCollectionBlackBox = em.merge(oldModuleidModuleOfBlackBoxCollectionBlackBox);
                }
            }
            for (UserModule userModuleCollectionUserModule : module.getUserModuleCollection()) {
                Module oldModuleidModuleOfUserModuleCollectionUserModule = userModuleCollectionUserModule.getModuleidModule();
                userModuleCollectionUserModule.setModuleidModule(module);
                userModuleCollectionUserModule = em.merge(userModuleCollectionUserModule);
                if (oldModuleidModuleOfUserModuleCollectionUserModule != null) {
                    oldModuleidModuleOfUserModuleCollectionUserModule.getUserModuleCollection().remove(userModuleCollectionUserModule);
                    oldModuleidModuleOfUserModuleCollectionUserModule = em.merge(oldModuleidModuleOfUserModuleCollectionUserModule);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findModule(module.getIdModule()) != null) {
                throw new PreexistingEntityException("Module " + module + " already exists.", ex);
            }
            throw ex;
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
            Collection<BlackBox> blackBoxCollectionOld = persistentModule.getBlackBoxCollection();
            Collection<BlackBox> blackBoxCollectionNew = module.getBlackBoxCollection();
            Collection<UserModule> userModuleCollectionOld = persistentModule.getUserModuleCollection();
            Collection<UserModule> userModuleCollectionNew = module.getUserModuleCollection();
            List<String> illegalOrphanMessages = null;
            for (BlackBox blackBoxCollectionOldBlackBox : blackBoxCollectionOld) {
                if (!blackBoxCollectionNew.contains(blackBoxCollectionOldBlackBox)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain BlackBox " + blackBoxCollectionOldBlackBox + " since its moduleidModule field is not nullable.");
                }
            }
            for (UserModule userModuleCollectionOldUserModule : userModuleCollectionOld) {
                if (!userModuleCollectionNew.contains(userModuleCollectionOldUserModule)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UserModule " + userModuleCollectionOldUserModule + " since its moduleidModule field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<BlackBox> attachedBlackBoxCollectionNew = new ArrayList<BlackBox>();
            for (BlackBox blackBoxCollectionNewBlackBoxToAttach : blackBoxCollectionNew) {
                blackBoxCollectionNewBlackBoxToAttach = em.getReference(blackBoxCollectionNewBlackBoxToAttach.getClass(), blackBoxCollectionNewBlackBoxToAttach.getIdBlackBox());
                attachedBlackBoxCollectionNew.add(blackBoxCollectionNewBlackBoxToAttach);
            }
            blackBoxCollectionNew = attachedBlackBoxCollectionNew;
            module.setBlackBoxCollection(blackBoxCollectionNew);
            Collection<UserModule> attachedUserModuleCollectionNew = new ArrayList<UserModule>();
            for (UserModule userModuleCollectionNewUserModuleToAttach : userModuleCollectionNew) {
                userModuleCollectionNewUserModuleToAttach = em.getReference(userModuleCollectionNewUserModuleToAttach.getClass(), userModuleCollectionNewUserModuleToAttach.getIdUserModule());
                attachedUserModuleCollectionNew.add(userModuleCollectionNewUserModuleToAttach);
            }
            userModuleCollectionNew = attachedUserModuleCollectionNew;
            module.setUserModuleCollection(userModuleCollectionNew);
            module = em.merge(module);
            for (BlackBox blackBoxCollectionNewBlackBox : blackBoxCollectionNew) {
                if (!blackBoxCollectionOld.contains(blackBoxCollectionNewBlackBox)) {
                    Module oldModuleidModuleOfBlackBoxCollectionNewBlackBox = blackBoxCollectionNewBlackBox.getModuleidModule();
                    blackBoxCollectionNewBlackBox.setModuleidModule(module);
                    blackBoxCollectionNewBlackBox = em.merge(blackBoxCollectionNewBlackBox);
                    if (oldModuleidModuleOfBlackBoxCollectionNewBlackBox != null && !oldModuleidModuleOfBlackBoxCollectionNewBlackBox.equals(module)) {
                        oldModuleidModuleOfBlackBoxCollectionNewBlackBox.getBlackBoxCollection().remove(blackBoxCollectionNewBlackBox);
                        oldModuleidModuleOfBlackBoxCollectionNewBlackBox = em.merge(oldModuleidModuleOfBlackBoxCollectionNewBlackBox);
                    }
                }
            }
            for (UserModule userModuleCollectionNewUserModule : userModuleCollectionNew) {
                if (!userModuleCollectionOld.contains(userModuleCollectionNewUserModule)) {
                    Module oldModuleidModuleOfUserModuleCollectionNewUserModule = userModuleCollectionNewUserModule.getModuleidModule();
                    userModuleCollectionNewUserModule.setModuleidModule(module);
                    userModuleCollectionNewUserModule = em.merge(userModuleCollectionNewUserModule);
                    if (oldModuleidModuleOfUserModuleCollectionNewUserModule != null && !oldModuleidModuleOfUserModuleCollectionNewUserModule.equals(module)) {
                        oldModuleidModuleOfUserModuleCollectionNewUserModule.getUserModuleCollection().remove(userModuleCollectionNewUserModule);
                        oldModuleidModuleOfUserModuleCollectionNewUserModule = em.merge(oldModuleidModuleOfUserModuleCollectionNewUserModule);
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
            Collection<BlackBox> blackBoxCollectionOrphanCheck = module.getBlackBoxCollection();
            for (BlackBox blackBoxCollectionOrphanCheckBlackBox : blackBoxCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Module (" + module + ") cannot be destroyed since the BlackBox " + blackBoxCollectionOrphanCheckBlackBox + " in its blackBoxCollection field has a non-nullable moduleidModule field.");
            }
            Collection<UserModule> userModuleCollectionOrphanCheck = module.getUserModuleCollection();
            for (UserModule userModuleCollectionOrphanCheckUserModule : userModuleCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Module (" + module + ") cannot be destroyed since the UserModule " + userModuleCollectionOrphanCheckUserModule + " in its userModuleCollection field has a non-nullable moduleidModule field.");
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

}
