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
import Entidades.User;
import Entidades.Module;
import Entidades.BlackBox;
import Entidades.UserModule;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author julio
 *
 */
public class UserModuleJpaController implements Serializable {

    public UserModuleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UserModule userModule) throws PreexistingEntityException, Exception {
        if (userModule.getBlackBoxCollection() == null) {
            userModule.setBlackBoxCollection(new ArrayList<BlackBox>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User useridUser = userModule.getUseridUser();
            if (useridUser != null) {
                useridUser = em.getReference(useridUser.getClass(), useridUser.getIduser());
                userModule.setUseridUser(useridUser);
            }
            Module moduleidModule = userModule.getModuleidModule();
            if (moduleidModule != null) {
                moduleidModule = em.getReference(moduleidModule.getClass(), moduleidModule.getIdModule());
                userModule.setModuleidModule(moduleidModule);
            }
            Collection<BlackBox> attachedBlackBoxCollection = new ArrayList<BlackBox>();
            for (BlackBox blackBoxCollectionBlackBoxToAttach : userModule.getBlackBoxCollection()) {
                blackBoxCollectionBlackBoxToAttach = em.getReference(blackBoxCollectionBlackBoxToAttach.getClass(), blackBoxCollectionBlackBoxToAttach.getIdBlackBox());
                attachedBlackBoxCollection.add(blackBoxCollectionBlackBoxToAttach);
            }
            userModule.setBlackBoxCollection(attachedBlackBoxCollection);
            em.persist(userModule);
            if (useridUser != null) {
                useridUser.getUserModuleCollection().add(userModule);
                useridUser = em.merge(useridUser);
            }
            if (moduleidModule != null) {
                moduleidModule.getUserModuleCollection().add(userModule);
                moduleidModule = em.merge(moduleidModule);
            }
            for (BlackBox blackBoxCollectionBlackBox : userModule.getBlackBoxCollection()) {
                UserModule oldUserModuleidUserModuleOfBlackBoxCollectionBlackBox = blackBoxCollectionBlackBox.getUserModuleidUserModule();
                blackBoxCollectionBlackBox.setUserModuleidUserModule(userModule);
                blackBoxCollectionBlackBox = em.merge(blackBoxCollectionBlackBox);
                if (oldUserModuleidUserModuleOfBlackBoxCollectionBlackBox != null) {
                    oldUserModuleidUserModuleOfBlackBoxCollectionBlackBox.getBlackBoxCollection().remove(blackBoxCollectionBlackBox);
                    oldUserModuleidUserModuleOfBlackBoxCollectionBlackBox = em.merge(oldUserModuleidUserModuleOfBlackBoxCollectionBlackBox);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUserModule(userModule.getIdUserModule()) != null) {
                throw new PreexistingEntityException("UserModule " + userModule + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UserModule userModule) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserModule persistentUserModule = em.find(UserModule.class, userModule.getIdUserModule());
            User useridUserOld = persistentUserModule.getUseridUser();
            User useridUserNew = userModule.getUseridUser();
            Module moduleidModuleOld = persistentUserModule.getModuleidModule();
            Module moduleidModuleNew = userModule.getModuleidModule();
            Collection<BlackBox> blackBoxCollectionOld = persistentUserModule.getBlackBoxCollection();
            Collection<BlackBox> blackBoxCollectionNew = userModule.getBlackBoxCollection();
            List<String> illegalOrphanMessages = null;
            for (BlackBox blackBoxCollectionOldBlackBox : blackBoxCollectionOld) {
                if (!blackBoxCollectionNew.contains(blackBoxCollectionOldBlackBox)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain BlackBox " + blackBoxCollectionOldBlackBox + " since its userModuleidUserModule field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (useridUserNew != null) {
                useridUserNew = em.getReference(useridUserNew.getClass(), useridUserNew.getIduser());
                userModule.setUseridUser(useridUserNew);
            }
            if (moduleidModuleNew != null) {
                moduleidModuleNew = em.getReference(moduleidModuleNew.getClass(), moduleidModuleNew.getIdModule());
                userModule.setModuleidModule(moduleidModuleNew);
            }
            Collection<BlackBox> attachedBlackBoxCollectionNew = new ArrayList<BlackBox>();
            for (BlackBox blackBoxCollectionNewBlackBoxToAttach : blackBoxCollectionNew) {
                blackBoxCollectionNewBlackBoxToAttach = em.getReference(blackBoxCollectionNewBlackBoxToAttach.getClass(), blackBoxCollectionNewBlackBoxToAttach.getIdBlackBox());
                attachedBlackBoxCollectionNew.add(blackBoxCollectionNewBlackBoxToAttach);
            }
            blackBoxCollectionNew = attachedBlackBoxCollectionNew;
            userModule.setBlackBoxCollection(blackBoxCollectionNew);
            userModule = em.merge(userModule);
            if (useridUserOld != null && !useridUserOld.equals(useridUserNew)) {
                useridUserOld.getUserModuleCollection().remove(userModule);
                useridUserOld = em.merge(useridUserOld);
            }
            if (useridUserNew != null && !useridUserNew.equals(useridUserOld)) {
                useridUserNew.getUserModuleCollection().add(userModule);
                useridUserNew = em.merge(useridUserNew);
            }
            if (moduleidModuleOld != null && !moduleidModuleOld.equals(moduleidModuleNew)) {
                moduleidModuleOld.getUserModuleCollection().remove(userModule);
                moduleidModuleOld = em.merge(moduleidModuleOld);
            }
            if (moduleidModuleNew != null && !moduleidModuleNew.equals(moduleidModuleOld)) {
                moduleidModuleNew.getUserModuleCollection().add(userModule);
                moduleidModuleNew = em.merge(moduleidModuleNew);
            }
            for (BlackBox blackBoxCollectionNewBlackBox : blackBoxCollectionNew) {
                if (!blackBoxCollectionOld.contains(blackBoxCollectionNewBlackBox)) {
                    UserModule oldUserModuleidUserModuleOfBlackBoxCollectionNewBlackBox = blackBoxCollectionNewBlackBox.getUserModuleidUserModule();
                    blackBoxCollectionNewBlackBox.setUserModuleidUserModule(userModule);
                    blackBoxCollectionNewBlackBox = em.merge(blackBoxCollectionNewBlackBox);
                    if (oldUserModuleidUserModuleOfBlackBoxCollectionNewBlackBox != null && !oldUserModuleidUserModuleOfBlackBoxCollectionNewBlackBox.equals(userModule)) {
                        oldUserModuleidUserModuleOfBlackBoxCollectionNewBlackBox.getBlackBoxCollection().remove(blackBoxCollectionNewBlackBox);
                        oldUserModuleidUserModuleOfBlackBoxCollectionNewBlackBox = em.merge(oldUserModuleidUserModuleOfBlackBoxCollectionNewBlackBox);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = userModule.getIdUserModule();
                if (findUserModule(id) == null) {
                    throw new NonexistentEntityException("The userModule with id " + id + " no longer exists.");
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
            UserModule userModule;
            try {
                userModule = em.getReference(UserModule.class, id);
                userModule.getIdUserModule();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The userModule with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<BlackBox> blackBoxCollectionOrphanCheck = userModule.getBlackBoxCollection();
            for (BlackBox blackBoxCollectionOrphanCheckBlackBox : blackBoxCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This UserModule (" + userModule + ") cannot be destroyed since the BlackBox " + blackBoxCollectionOrphanCheckBlackBox + " in its blackBoxCollection field has a non-nullable userModuleidUserModule field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            User useridUser = userModule.getUseridUser();
            if (useridUser != null) {
                useridUser.getUserModuleCollection().remove(userModule);
                useridUser = em.merge(useridUser);
            }
            Module moduleidModule = userModule.getModuleidModule();
            if (moduleidModule != null) {
                moduleidModule.getUserModuleCollection().remove(userModule);
                moduleidModule = em.merge(moduleidModule);
            }
            em.remove(userModule);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UserModule> findUserModuleEntities() {
        return findUserModuleEntities(true, -1, -1);
    }

    public List<UserModule> findUserModuleEntities(int maxResults, int firstResult) {
        return findUserModuleEntities(false, maxResults, firstResult);
    }

    private List<UserModule> findUserModuleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UserModule.class));
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

    public UserModule findUserModule(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UserModule.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserModuleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UserModule> rt = cq.from(UserModule.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
