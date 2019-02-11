/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import Entidades.TypeUser;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.User;
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
public class TypeUserJpaController implements Serializable {

    public TypeUserJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TypeUser typeUser) {
        if (typeUser.getUserCollection() == null) {
            typeUser.setUserCollection(new ArrayList<User>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<User> attachedUserCollection = new ArrayList<User>();
            for (User userCollectionUserToAttach : typeUser.getUserCollection()) {
                userCollectionUserToAttach = em.getReference(userCollectionUserToAttach.getClass(), userCollectionUserToAttach.getIduser());
                attachedUserCollection.add(userCollectionUserToAttach);
            }
            typeUser.setUserCollection(attachedUserCollection);
            em.persist(typeUser);
            for (User userCollectionUser : typeUser.getUserCollection()) {
                TypeUser oldTypeUseridTypeUserOfUserCollectionUser = userCollectionUser.getTypeUseridTypeUser();
                userCollectionUser.setTypeUseridTypeUser(typeUser);
                userCollectionUser = em.merge(userCollectionUser);
                if (oldTypeUseridTypeUserOfUserCollectionUser != null) {
                    oldTypeUseridTypeUserOfUserCollectionUser.getUserCollection().remove(userCollectionUser);
                    oldTypeUseridTypeUserOfUserCollectionUser = em.merge(oldTypeUseridTypeUserOfUserCollectionUser);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TypeUser typeUser) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TypeUser persistentTypeUser = em.find(TypeUser.class, typeUser.getIdTypeUser());
            Collection<User> userCollectionOld = persistentTypeUser.getUserCollection();
            Collection<User> userCollectionNew = typeUser.getUserCollection();
            List<String> illegalOrphanMessages = null;
            for (User userCollectionOldUser : userCollectionOld) {
                if (!userCollectionNew.contains(userCollectionOldUser)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain User " + userCollectionOldUser + " since its typeUseridTypeUser field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<User> attachedUserCollectionNew = new ArrayList<User>();
            for (User userCollectionNewUserToAttach : userCollectionNew) {
                userCollectionNewUserToAttach = em.getReference(userCollectionNewUserToAttach.getClass(), userCollectionNewUserToAttach.getIduser());
                attachedUserCollectionNew.add(userCollectionNewUserToAttach);
            }
            userCollectionNew = attachedUserCollectionNew;
            typeUser.setUserCollection(userCollectionNew);
            typeUser = em.merge(typeUser);
            for (User userCollectionNewUser : userCollectionNew) {
                if (!userCollectionOld.contains(userCollectionNewUser)) {
                    TypeUser oldTypeUseridTypeUserOfUserCollectionNewUser = userCollectionNewUser.getTypeUseridTypeUser();
                    userCollectionNewUser.setTypeUseridTypeUser(typeUser);
                    userCollectionNewUser = em.merge(userCollectionNewUser);
                    if (oldTypeUseridTypeUserOfUserCollectionNewUser != null && !oldTypeUseridTypeUserOfUserCollectionNewUser.equals(typeUser)) {
                        oldTypeUseridTypeUserOfUserCollectionNewUser.getUserCollection().remove(userCollectionNewUser);
                        oldTypeUseridTypeUserOfUserCollectionNewUser = em.merge(oldTypeUseridTypeUserOfUserCollectionNewUser);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = typeUser.getIdTypeUser();
                if (findTypeUser(id) == null) {
                    throw new NonexistentEntityException("The typeUser with id " + id + " no longer exists.");
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
            TypeUser typeUser;
            try {
                typeUser = em.getReference(TypeUser.class, id);
                typeUser.getIdTypeUser();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The typeUser with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<User> userCollectionOrphanCheck = typeUser.getUserCollection();
            for (User userCollectionOrphanCheckUser : userCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TypeUser (" + typeUser + ") cannot be destroyed since the User " + userCollectionOrphanCheckUser + " in its userCollection field has a non-nullable typeUseridTypeUser field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(typeUser);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TypeUser> findTypeUserEntities() {
        return findTypeUserEntities(true, -1, -1);
    }

    public List<TypeUser> findTypeUserEntities(int maxResults, int firstResult) {
        return findTypeUserEntities(false, maxResults, firstResult);
    }

    private List<TypeUser> findTypeUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TypeUser.class));
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

    public TypeUser findTypeUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TypeUser.class, id);
        } finally {
            em.close();
        }
    }

    public int getTypeUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TypeUser> rt = cq.from(TypeUser.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
