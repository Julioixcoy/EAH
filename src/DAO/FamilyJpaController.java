/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import Entidades.Family;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.FamilyComplete;
import java.util.ArrayList;
import java.util.Collection;
import Entidades.Student;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Julio Fernando Ixcoy
 */
public class FamilyJpaController implements Serializable {

    public FamilyJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Family family) {
        if (family.getFamilyCompleteCollection() == null) {
            family.setFamilyCompleteCollection(new ArrayList<FamilyComplete>());
        }
        if (family.getStudentCollection() == null) {
            family.setStudentCollection(new ArrayList<Student>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<FamilyComplete> attachedFamilyCompleteCollection = new ArrayList<FamilyComplete>();
            for (FamilyComplete familyCompleteCollectionFamilyCompleteToAttach : family.getFamilyCompleteCollection()) {
                familyCompleteCollectionFamilyCompleteToAttach = em.getReference(familyCompleteCollectionFamilyCompleteToAttach.getClass(), familyCompleteCollectionFamilyCompleteToAttach.getFamilyCompletePK());
                attachedFamilyCompleteCollection.add(familyCompleteCollectionFamilyCompleteToAttach);
            }
            family.setFamilyCompleteCollection(attachedFamilyCompleteCollection);
            Collection<Student> attachedStudentCollection = new ArrayList<Student>();
            for (Student studentCollectionStudentToAttach : family.getStudentCollection()) {
                studentCollectionStudentToAttach = em.getReference(studentCollectionStudentToAttach.getClass(), studentCollectionStudentToAttach.getStudentPK());
                attachedStudentCollection.add(studentCollectionStudentToAttach);
            }
            family.setStudentCollection(attachedStudentCollection);
            em.persist(family);
            for (FamilyComplete familyCompleteCollectionFamilyComplete : family.getFamilyCompleteCollection()) {
                Family oldFamilyOfFamilyCompleteCollectionFamilyComplete = familyCompleteCollectionFamilyComplete.getFamily();
                familyCompleteCollectionFamilyComplete.setFamily(family);
                familyCompleteCollectionFamilyComplete = em.merge(familyCompleteCollectionFamilyComplete);
                if (oldFamilyOfFamilyCompleteCollectionFamilyComplete != null) {
                    oldFamilyOfFamilyCompleteCollectionFamilyComplete.getFamilyCompleteCollection().remove(familyCompleteCollectionFamilyComplete);
                    oldFamilyOfFamilyCompleteCollectionFamilyComplete = em.merge(oldFamilyOfFamilyCompleteCollectionFamilyComplete);
                }
            }
            for (Student studentCollectionStudent : family.getStudentCollection()) {
                Family oldFamilyidFamilyOfStudentCollectionStudent = studentCollectionStudent.getFamilyidFamily();
                studentCollectionStudent.setFamilyidFamily(family);
                studentCollectionStudent = em.merge(studentCollectionStudent);
                if (oldFamilyidFamilyOfStudentCollectionStudent != null) {
                    oldFamilyidFamilyOfStudentCollectionStudent.getStudentCollection().remove(studentCollectionStudent);
                    oldFamilyidFamilyOfStudentCollectionStudent = em.merge(oldFamilyidFamilyOfStudentCollectionStudent);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Family family) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Family persistentFamily = em.find(Family.class, family.getIdFamily());
            Collection<FamilyComplete> familyCompleteCollectionOld = persistentFamily.getFamilyCompleteCollection();
            Collection<FamilyComplete> familyCompleteCollectionNew = family.getFamilyCompleteCollection();
            Collection<Student> studentCollectionOld = persistentFamily.getStudentCollection();
            Collection<Student> studentCollectionNew = family.getStudentCollection();
            List<String> illegalOrphanMessages = null;
            for (FamilyComplete familyCompleteCollectionOldFamilyComplete : familyCompleteCollectionOld) {
                if (!familyCompleteCollectionNew.contains(familyCompleteCollectionOldFamilyComplete)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain FamilyComplete " + familyCompleteCollectionOldFamilyComplete + " since its family field is not nullable.");
                }
            }
            for (Student studentCollectionOldStudent : studentCollectionOld) {
                if (!studentCollectionNew.contains(studentCollectionOldStudent)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Student " + studentCollectionOldStudent + " since its familyidFamily field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<FamilyComplete> attachedFamilyCompleteCollectionNew = new ArrayList<FamilyComplete>();
            for (FamilyComplete familyCompleteCollectionNewFamilyCompleteToAttach : familyCompleteCollectionNew) {
                familyCompleteCollectionNewFamilyCompleteToAttach = em.getReference(familyCompleteCollectionNewFamilyCompleteToAttach.getClass(), familyCompleteCollectionNewFamilyCompleteToAttach.getFamilyCompletePK());
                attachedFamilyCompleteCollectionNew.add(familyCompleteCollectionNewFamilyCompleteToAttach);
            }
            familyCompleteCollectionNew = attachedFamilyCompleteCollectionNew;
            family.setFamilyCompleteCollection(familyCompleteCollectionNew);
            Collection<Student> attachedStudentCollectionNew = new ArrayList<Student>();
            for (Student studentCollectionNewStudentToAttach : studentCollectionNew) {
                studentCollectionNewStudentToAttach = em.getReference(studentCollectionNewStudentToAttach.getClass(), studentCollectionNewStudentToAttach.getStudentPK());
                attachedStudentCollectionNew.add(studentCollectionNewStudentToAttach);
            }
            studentCollectionNew = attachedStudentCollectionNew;
            family.setStudentCollection(studentCollectionNew);
            family = em.merge(family);
            for (FamilyComplete familyCompleteCollectionNewFamilyComplete : familyCompleteCollectionNew) {
                if (!familyCompleteCollectionOld.contains(familyCompleteCollectionNewFamilyComplete)) {
                    Family oldFamilyOfFamilyCompleteCollectionNewFamilyComplete = familyCompleteCollectionNewFamilyComplete.getFamily();
                    familyCompleteCollectionNewFamilyComplete.setFamily(family);
                    familyCompleteCollectionNewFamilyComplete = em.merge(familyCompleteCollectionNewFamilyComplete);
                    if (oldFamilyOfFamilyCompleteCollectionNewFamilyComplete != null && !oldFamilyOfFamilyCompleteCollectionNewFamilyComplete.equals(family)) {
                        oldFamilyOfFamilyCompleteCollectionNewFamilyComplete.getFamilyCompleteCollection().remove(familyCompleteCollectionNewFamilyComplete);
                        oldFamilyOfFamilyCompleteCollectionNewFamilyComplete = em.merge(oldFamilyOfFamilyCompleteCollectionNewFamilyComplete);
                    }
                }
            }
            for (Student studentCollectionNewStudent : studentCollectionNew) {
                if (!studentCollectionOld.contains(studentCollectionNewStudent)) {
                    Family oldFamilyidFamilyOfStudentCollectionNewStudent = studentCollectionNewStudent.getFamilyidFamily();
                    studentCollectionNewStudent.setFamilyidFamily(family);
                    studentCollectionNewStudent = em.merge(studentCollectionNewStudent);
                    if (oldFamilyidFamilyOfStudentCollectionNewStudent != null && !oldFamilyidFamilyOfStudentCollectionNewStudent.equals(family)) {
                        oldFamilyidFamilyOfStudentCollectionNewStudent.getStudentCollection().remove(studentCollectionNewStudent);
                        oldFamilyidFamilyOfStudentCollectionNewStudent = em.merge(oldFamilyidFamilyOfStudentCollectionNewStudent);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = family.getIdFamily();
                if (findFamily(id) == null) {
                    throw new NonexistentEntityException("The family with id " + id + " no longer exists.");
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
            Family family;
            try {
                family = em.getReference(Family.class, id);
                family.getIdFamily();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The family with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<FamilyComplete> familyCompleteCollectionOrphanCheck = family.getFamilyCompleteCollection();
            for (FamilyComplete familyCompleteCollectionOrphanCheckFamilyComplete : familyCompleteCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Family (" + family + ") cannot be destroyed since the FamilyComplete " + familyCompleteCollectionOrphanCheckFamilyComplete + " in its familyCompleteCollection field has a non-nullable family field.");
            }
            Collection<Student> studentCollectionOrphanCheck = family.getStudentCollection();
            for (Student studentCollectionOrphanCheckStudent : studentCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Family (" + family + ") cannot be destroyed since the Student " + studentCollectionOrphanCheckStudent + " in its studentCollection field has a non-nullable familyidFamily field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(family);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Family> findFamilyEntities() {
        return findFamilyEntities(true, -1, -1);
    }

    public List<Family> findFamilyEntities(int maxResults, int firstResult) {
        return findFamilyEntities(false, maxResults, firstResult);
    }

    private List<Family> findFamilyEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Family.class));
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

    public Family findFamily(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Family.class, id);
        } finally {
            em.close();
        }
    }

    public int getFamilyCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Family> rt = cq.from(Family.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
