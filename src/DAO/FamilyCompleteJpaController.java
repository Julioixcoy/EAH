/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Family;
import Entidades.FamilyComplete;
import Entidades.FamilyCompletePK;
import Entidades.Parents;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Julio Fernando Ixcoy
 */
public class FamilyCompleteJpaController implements Serializable {

    public FamilyCompleteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FamilyComplete familyComplete) throws PreexistingEntityException, Exception {
        if (familyComplete.getFamilyCompletePK() == null) {
            familyComplete.setFamilyCompletePK(new FamilyCompletePK());
        }
        familyComplete.getFamilyCompletePK().setFamilyidFamily(familyComplete.getFamily().getIdFamily());
        familyComplete.getFamilyCompletePK().setParentsPersonaidPersona(familyComplete.getParents().getParentsPK().getPersonaidPersona());
        familyComplete.getFamilyCompletePK().setParentsidParents(familyComplete.getParents().getParentsPK().getIdParents());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Family family = familyComplete.getFamily();
            if (family != null) {
                family = em.getReference(family.getClass(), family.getIdFamily());
                familyComplete.setFamily(family);
            }
            Parents parents = familyComplete.getParents();
            if (parents != null) {
                parents = em.getReference(parents.getClass(), parents.getParentsPK());
                familyComplete.setParents(parents);
            }
            em.persist(familyComplete);
            if (family != null) {
                family.getFamilyCompleteCollection().add(familyComplete);
                family = em.merge(family);
            }
            if (parents != null) {
                parents.getFamilyCompleteCollection().add(familyComplete);
                parents = em.merge(parents);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFamilyComplete(familyComplete.getFamilyCompletePK()) != null) {
                throw new PreexistingEntityException("FamilyComplete " + familyComplete + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FamilyComplete familyComplete) throws NonexistentEntityException, Exception {
        familyComplete.getFamilyCompletePK().setFamilyidFamily(familyComplete.getFamily().getIdFamily());
        familyComplete.getFamilyCompletePK().setParentsPersonaidPersona(familyComplete.getParents().getParentsPK().getPersonaidPersona());
        familyComplete.getFamilyCompletePK().setParentsidParents(familyComplete.getParents().getParentsPK().getIdParents());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FamilyComplete persistentFamilyComplete = em.find(FamilyComplete.class, familyComplete.getFamilyCompletePK());
            Family familyOld = persistentFamilyComplete.getFamily();
            Family familyNew = familyComplete.getFamily();
            Parents parentsOld = persistentFamilyComplete.getParents();
            Parents parentsNew = familyComplete.getParents();
            if (familyNew != null) {
                familyNew = em.getReference(familyNew.getClass(), familyNew.getIdFamily());
                familyComplete.setFamily(familyNew);
            }
            if (parentsNew != null) {
                parentsNew = em.getReference(parentsNew.getClass(), parentsNew.getParentsPK());
                familyComplete.setParents(parentsNew);
            }
            familyComplete = em.merge(familyComplete);
            if (familyOld != null && !familyOld.equals(familyNew)) {
                familyOld.getFamilyCompleteCollection().remove(familyComplete);
                familyOld = em.merge(familyOld);
            }
            if (familyNew != null && !familyNew.equals(familyOld)) {
                familyNew.getFamilyCompleteCollection().add(familyComplete);
                familyNew = em.merge(familyNew);
            }
            if (parentsOld != null && !parentsOld.equals(parentsNew)) {
                parentsOld.getFamilyCompleteCollection().remove(familyComplete);
                parentsOld = em.merge(parentsOld);
            }
            if (parentsNew != null && !parentsNew.equals(parentsOld)) {
                parentsNew.getFamilyCompleteCollection().add(familyComplete);
                parentsNew = em.merge(parentsNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                FamilyCompletePK id = familyComplete.getFamilyCompletePK();
                if (findFamilyComplete(id) == null) {
                    throw new NonexistentEntityException("The familyComplete with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(FamilyCompletePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FamilyComplete familyComplete;
            try {
                familyComplete = em.getReference(FamilyComplete.class, id);
                familyComplete.getFamilyCompletePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The familyComplete with id " + id + " no longer exists.", enfe);
            }
            Family family = familyComplete.getFamily();
            if (family != null) {
                family.getFamilyCompleteCollection().remove(familyComplete);
                family = em.merge(family);
            }
            Parents parents = familyComplete.getParents();
            if (parents != null) {
                parents.getFamilyCompleteCollection().remove(familyComplete);
                parents = em.merge(parents);
            }
            em.remove(familyComplete);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FamilyComplete> findFamilyCompleteEntities() {
        return findFamilyCompleteEntities(true, -1, -1);
    }

    public List<FamilyComplete> findFamilyCompleteEntities(int maxResults, int firstResult) {
        return findFamilyCompleteEntities(false, maxResults, firstResult);
    }

    private List<FamilyComplete> findFamilyCompleteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FamilyComplete.class));
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

    public FamilyComplete findFamilyComplete(FamilyCompletePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FamilyComplete.class, id);
        } finally {
            em.close();
        }
    }

    public int getFamilyCompleteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FamilyComplete> rt = cq.from(FamilyComplete.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
