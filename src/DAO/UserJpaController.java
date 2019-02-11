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
import Entidades.User;
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
public class UserJpaController implements Serializable {

    public UserJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(User user) throws PreexistingEntityException, Exception {
        if (user.getBlackBoxCollection() == null) {
            user.setBlackBoxCollection(new ArrayList<BlackBox>());
        }
        if (user.getUserModuleCollection() == null) {
            user.setUserModuleCollection(new ArrayList<UserModule>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<BlackBox> attachedBlackBoxCollection = new ArrayList<BlackBox>();
            for (BlackBox blackBoxCollectionBlackBoxToAttach : user.getBlackBoxCollection()) {
                blackBoxCollectionBlackBoxToAttach = em.getReference(blackBoxCollectionBlackBoxToAttach.getClass(), blackBoxCollectionBlackBoxToAttach.getIdBlackBox());
                attachedBlackBoxCollection.add(blackBoxCollectionBlackBoxToAttach);
            }
            user.setBlackBoxCollection(attachedBlackBoxCollection);
            Collection<UserModule> attachedUserModuleCollection = new ArrayList<UserModule>();
            for (UserModule userModuleCollectionUserModuleToAttach : user.getUserModuleCollection()) {
                userModuleCollectionUserModuleToAttach = em.getReference(userModuleCollectionUserModuleToAttach.getClass(), userModuleCollectionUserModuleToAttach.getIdUserModule());
                attachedUserModuleCollection.add(userModuleCollectionUserModuleToAttach);
            }
            user.setUserModuleCollection(attachedUserModuleCollection);
            em.persist(user);
            for (BlackBox blackBoxCollectionBlackBox : user.getBlackBoxCollection()) {
                User oldUseriduserOfBlackBoxCollectionBlackBox = blackBoxCollectionBlackBox.getUseriduser();
                blackBoxCollectionBlackBox.setUseriduser(user);
                blackBoxCollectionBlackBox = em.merge(blackBoxCollectionBlackBox);
                if (oldUseriduserOfBlackBoxCollectionBlackBox != null) {
                    oldUseriduserOfBlackBoxCollectionBlackBox.getBlackBoxCollection().remove(blackBoxCollectionBlackBox);
                    oldUseriduserOfBlackBoxCollectionBlackBox = em.merge(oldUseriduserOfBlackBoxCollectionBlackBox);
                }
            }
            for (UserModule userModuleCollectionUserModule : user.getUserModuleCollection()) {
                User oldUseridUserOfUserModuleCollectionUserModule = userModuleCollectionUserModule.getUseridUser();
                userModuleCollectionUserModule.setUseridUser(user);
                userModuleCollectionUserModule = em.merge(userModuleCollectionUserModule);
                if (oldUseridUserOfUserModuleCollectionUserModule != null) {
                    oldUseridUserOfUserModuleCollectionUserModule.getUserModuleCollection().remove(userModuleCollectionUserModule);
                    oldUseridUserOfUserModuleCollectionUserModule = em.merge(oldUseridUserOfUserModuleCollectionUserModule);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUser(user.getIduser()) != null) {
                throw new PreexistingEntityException("User " + user + " already exists.", ex);
            }
            throw ex;
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
            User persistentUser = em.find(User.class, user.getIduser());
            Collection<BlackBox> blackBoxCollectionOld = persistentUser.getBlackBoxCollection();
            Collection<BlackBox> blackBoxCollectionNew = user.getBlackBoxCollection();
            Collection<UserModule> userModuleCollectionOld = persistentUser.getUserModuleCollection();
            Collection<UserModule> userModuleCollectionNew = user.getUserModuleCollection();
            List<String> illegalOrphanMessages = null;
            for (BlackBox blackBoxCollectionOldBlackBox : blackBoxCollectionOld) {
                if (!blackBoxCollectionNew.contains(blackBoxCollectionOldBlackBox)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain BlackBox " + blackBoxCollectionOldBlackBox + " since its useriduser field is not nullable.");
                }
            }
            for (UserModule userModuleCollectionOldUserModule : userModuleCollectionOld) {
                if (!userModuleCollectionNew.contains(userModuleCollectionOldUserModule)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UserModule " + userModuleCollectionOldUserModule + " since its useridUser field is not nullable.");
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
            user.setBlackBoxCollection(blackBoxCollectionNew);
            Collection<UserModule> attachedUserModuleCollectionNew = new ArrayList<UserModule>();
            for (UserModule userModuleCollectionNewUserModuleToAttach : userModuleCollectionNew) {
                userModuleCollectionNewUserModuleToAttach = em.getReference(userModuleCollectionNewUserModuleToAttach.getClass(), userModuleCollectionNewUserModuleToAttach.getIdUserModule());
                attachedUserModuleCollectionNew.add(userModuleCollectionNewUserModuleToAttach);
            }
            userModuleCollectionNew = attachedUserModuleCollectionNew;
            user.setUserModuleCollection(userModuleCollectionNew);
            user = em.merge(user);
            for (BlackBox blackBoxCollectionNewBlackBox : blackBoxCollectionNew) {
                if (!blackBoxCollectionOld.contains(blackBoxCollectionNewBlackBox)) {
                    User oldUseriduserOfBlackBoxCollectionNewBlackBox = blackBoxCollectionNewBlackBox.getUseriduser();
                    blackBoxCollectionNewBlackBox.setUseriduser(user);
                    blackBoxCollectionNewBlackBox = em.merge(blackBoxCollectionNewBlackBox);
                    if (oldUseriduserOfBlackBoxCollectionNewBlackBox != null && !oldUseriduserOfBlackBoxCollectionNewBlackBox.equals(user)) {
                        oldUseriduserOfBlackBoxCollectionNewBlackBox.getBlackBoxCollection().remove(blackBoxCollectionNewBlackBox);
                        oldUseriduserOfBlackBoxCollectionNewBlackBox = em.merge(oldUseriduserOfBlackBoxCollectionNewBlackBox);
                    }
                }
            }
            for (UserModule userModuleCollectionNewUserModule : userModuleCollectionNew) {
                if (!userModuleCollectionOld.contains(userModuleCollectionNewUserModule)) {
                    User oldUseridUserOfUserModuleCollectionNewUserModule = userModuleCollectionNewUserModule.getUseridUser();
                    userModuleCollectionNewUserModule.setUseridUser(user);
                    userModuleCollectionNewUserModule = em.merge(userModuleCollectionNewUserModule);
                    if (oldUseridUserOfUserModuleCollectionNewUserModule != null && !oldUseridUserOfUserModuleCollectionNewUserModule.equals(user)) {
                        oldUseridUserOfUserModuleCollectionNewUserModule.getUserModuleCollection().remove(userModuleCollectionNewUserModule);
                        oldUseridUserOfUserModuleCollectionNewUserModule = em.merge(oldUseridUserOfUserModuleCollectionNewUserModule);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = user.getIduser();
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
                user.getIduser();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<BlackBox> blackBoxCollectionOrphanCheck = user.getBlackBoxCollection();
            for (BlackBox blackBoxCollectionOrphanCheckBlackBox : blackBoxCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the BlackBox " + blackBoxCollectionOrphanCheckBlackBox + " in its blackBoxCollection field has a non-nullable useriduser field.");
            }
            Collection<UserModule> userModuleCollectionOrphanCheck = user.getUserModuleCollection();
            for (UserModule userModuleCollectionOrphanCheckUserModule : userModuleCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the UserModule " + userModuleCollectionOrphanCheckUserModule + " in its userModuleCollection field has a non-nullable useridUser field.");
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
