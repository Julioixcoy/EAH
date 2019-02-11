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
import Entidades.Persona;
import Entidades.FamilyComplete;
import Entidades.Parents;
import Entidades.ParentsPK;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Julio Fernando Ixcoy
 */
public class ParentsJpaController implements Serializable {

    public ParentsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Parents parents) throws PreexistingEntityException, Exception {
        if (parents.getParentsPK() == null) {
            parents.setParentsPK(new ParentsPK());
        }
        if (parents.getFamilyCompleteCollection() == null) {
            parents.setFamilyCompleteCollection(new ArrayList<FamilyComplete>());
        }
        parents.getParentsPK().setPersonaidPersona(parents.getPersona().getIdPersona());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persona = parents.getPersona();
            if (persona != null) {
                persona = em.getReference(persona.getClass(), persona.getIdPersona());
                parents.setPersona(persona);
            }
            Collection<FamilyComplete> attachedFamilyCompleteCollection = new ArrayList<FamilyComplete>();
            for (FamilyComplete familyCompleteCollectionFamilyCompleteToAttach : parents.getFamilyCompleteCollection()) {
                familyCompleteCollectionFamilyCompleteToAttach = em.getReference(familyCompleteCollectionFamilyCompleteToAttach.getClass(), familyCompleteCollectionFamilyCompleteToAttach.getFamilyCompletePK());
                attachedFamilyCompleteCollection.add(familyCompleteCollectionFamilyCompleteToAttach);
            }
            parents.setFamilyCompleteCollection(attachedFamilyCompleteCollection);
            em.persist(parents);
            if (persona != null) {
                persona.getParentsCollection().add(parents);
                persona = em.merge(persona);
            }
            for (FamilyComplete familyCompleteCollectionFamilyComplete : parents.getFamilyCompleteCollection()) {
                Parents oldParentsOfFamilyCompleteCollectionFamilyComplete = familyCompleteCollectionFamilyComplete.getParents();
                familyCompleteCollectionFamilyComplete.setParents(parents);
                familyCompleteCollectionFamilyComplete = em.merge(familyCompleteCollectionFamilyComplete);
                if (oldParentsOfFamilyCompleteCollectionFamilyComplete != null) {
                    oldParentsOfFamilyCompleteCollectionFamilyComplete.getFamilyCompleteCollection().remove(familyCompleteCollectionFamilyComplete);
                    oldParentsOfFamilyCompleteCollectionFamilyComplete = em.merge(oldParentsOfFamilyCompleteCollectionFamilyComplete);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findParents(parents.getParentsPK()) != null) {
                throw new PreexistingEntityException("Parents " + parents + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Parents parents) throws IllegalOrphanException, NonexistentEntityException, Exception {
        parents.getParentsPK().setPersonaidPersona(parents.getPersona().getIdPersona());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Parents persistentParents = em.find(Parents.class, parents.getParentsPK());
            Persona personaOld = persistentParents.getPersona();
            Persona personaNew = parents.getPersona();
            Collection<FamilyComplete> familyCompleteCollectionOld = persistentParents.getFamilyCompleteCollection();
            Collection<FamilyComplete> familyCompleteCollectionNew = parents.getFamilyCompleteCollection();
            List<String> illegalOrphanMessages = null;
            for (FamilyComplete familyCompleteCollectionOldFamilyComplete : familyCompleteCollectionOld) {
                if (!familyCompleteCollectionNew.contains(familyCompleteCollectionOldFamilyComplete)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain FamilyComplete " + familyCompleteCollectionOldFamilyComplete + " since its parents field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (personaNew != null) {
                personaNew = em.getReference(personaNew.getClass(), personaNew.getIdPersona());
                parents.setPersona(personaNew);
            }
            Collection<FamilyComplete> attachedFamilyCompleteCollectionNew = new ArrayList<FamilyComplete>();
            for (FamilyComplete familyCompleteCollectionNewFamilyCompleteToAttach : familyCompleteCollectionNew) {
                familyCompleteCollectionNewFamilyCompleteToAttach = em.getReference(familyCompleteCollectionNewFamilyCompleteToAttach.getClass(), familyCompleteCollectionNewFamilyCompleteToAttach.getFamilyCompletePK());
                attachedFamilyCompleteCollectionNew.add(familyCompleteCollectionNewFamilyCompleteToAttach);
            }
            familyCompleteCollectionNew = attachedFamilyCompleteCollectionNew;
            parents.setFamilyCompleteCollection(familyCompleteCollectionNew);
            parents = em.merge(parents);
            if (personaOld != null && !personaOld.equals(personaNew)) {
                personaOld.getParentsCollection().remove(parents);
                personaOld = em.merge(personaOld);
            }
            if (personaNew != null && !personaNew.equals(personaOld)) {
                personaNew.getParentsCollection().add(parents);
                personaNew = em.merge(personaNew);
            }
            for (FamilyComplete familyCompleteCollectionNewFamilyComplete : familyCompleteCollectionNew) {
                if (!familyCompleteCollectionOld.contains(familyCompleteCollectionNewFamilyComplete)) {
                    Parents oldParentsOfFamilyCompleteCollectionNewFamilyComplete = familyCompleteCollectionNewFamilyComplete.getParents();
                    familyCompleteCollectionNewFamilyComplete.setParents(parents);
                    familyCompleteCollectionNewFamilyComplete = em.merge(familyCompleteCollectionNewFamilyComplete);
                    if (oldParentsOfFamilyCompleteCollectionNewFamilyComplete != null && !oldParentsOfFamilyCompleteCollectionNewFamilyComplete.equals(parents)) {
                        oldParentsOfFamilyCompleteCollectionNewFamilyComplete.getFamilyCompleteCollection().remove(familyCompleteCollectionNewFamilyComplete);
                        oldParentsOfFamilyCompleteCollectionNewFamilyComplete = em.merge(oldParentsOfFamilyCompleteCollectionNewFamilyComplete);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ParentsPK id = parents.getParentsPK();
                if (findParents(id) == null) {
                    throw new NonexistentEntityException("The parents with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ParentsPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Parents parents;
            try {
                parents = em.getReference(Parents.class, id);
                parents.getParentsPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The parents with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<FamilyComplete> familyCompleteCollectionOrphanCheck = parents.getFamilyCompleteCollection();
            for (FamilyComplete familyCompleteCollectionOrphanCheckFamilyComplete : familyCompleteCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Parents (" + parents + ") cannot be destroyed since the FamilyComplete " + familyCompleteCollectionOrphanCheckFamilyComplete + " in its familyCompleteCollection field has a non-nullable parents field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Persona persona = parents.getPersona();
            if (persona != null) {
                persona.getParentsCollection().remove(parents);
                persona = em.merge(persona);
            }
            em.remove(parents);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Parents> findParentsEntities() {
        return findParentsEntities(true, -1, -1);
    }

    public List<Parents> findParentsEntities(int maxResults, int firstResult) {
        return findParentsEntities(false, maxResults, firstResult);
    }

    private List<Parents> findParentsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Parents.class));
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

    public Parents findParents(ParentsPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Parents.class, id);
        } finally {
            em.close();
        }
    }

    public int getParentsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Parents> rt = cq.from(Parents.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
