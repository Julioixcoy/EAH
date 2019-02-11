/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import Entidades.LevelAcademic;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Student;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Julio Fernando Ixcoy
 */
public class LevelAcademicJpaController implements Serializable {

    public LevelAcademicJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LevelAcademic levelAcademic) {
        if (levelAcademic.getStudentCollection() == null) {
            levelAcademic.setStudentCollection(new ArrayList<Student>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Student> attachedStudentCollection = new ArrayList<Student>();
            for (Student studentCollectionStudentToAttach : levelAcademic.getStudentCollection()) {
                studentCollectionStudentToAttach = em.getReference(studentCollectionStudentToAttach.getClass(), studentCollectionStudentToAttach.getStudentPK());
                attachedStudentCollection.add(studentCollectionStudentToAttach);
            }
            levelAcademic.setStudentCollection(attachedStudentCollection);
            em.persist(levelAcademic);
            for (Student studentCollectionStudent : levelAcademic.getStudentCollection()) {
                studentCollectionStudent.getLevelAcademicCollection().add(levelAcademic);
                studentCollectionStudent = em.merge(studentCollectionStudent);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LevelAcademic levelAcademic) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LevelAcademic persistentLevelAcademic = em.find(LevelAcademic.class, levelAcademic.getIdLevelAcademic());
            Collection<Student> studentCollectionOld = persistentLevelAcademic.getStudentCollection();
            Collection<Student> studentCollectionNew = levelAcademic.getStudentCollection();
            Collection<Student> attachedStudentCollectionNew = new ArrayList<Student>();
            for (Student studentCollectionNewStudentToAttach : studentCollectionNew) {
                studentCollectionNewStudentToAttach = em.getReference(studentCollectionNewStudentToAttach.getClass(), studentCollectionNewStudentToAttach.getStudentPK());
                attachedStudentCollectionNew.add(studentCollectionNewStudentToAttach);
            }
            studentCollectionNew = attachedStudentCollectionNew;
            levelAcademic.setStudentCollection(studentCollectionNew);
            levelAcademic = em.merge(levelAcademic);
            for (Student studentCollectionOldStudent : studentCollectionOld) {
                if (!studentCollectionNew.contains(studentCollectionOldStudent)) {
                    studentCollectionOldStudent.getLevelAcademicCollection().remove(levelAcademic);
                    studentCollectionOldStudent = em.merge(studentCollectionOldStudent);
                }
            }
            for (Student studentCollectionNewStudent : studentCollectionNew) {
                if (!studentCollectionOld.contains(studentCollectionNewStudent)) {
                    studentCollectionNewStudent.getLevelAcademicCollection().add(levelAcademic);
                    studentCollectionNewStudent = em.merge(studentCollectionNewStudent);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = levelAcademic.getIdLevelAcademic();
                if (findLevelAcademic(id) == null) {
                    throw new NonexistentEntityException("The levelAcademic with id " + id + " no longer exists.");
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
            LevelAcademic levelAcademic;
            try {
                levelAcademic = em.getReference(LevelAcademic.class, id);
                levelAcademic.getIdLevelAcademic();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The levelAcademic with id " + id + " no longer exists.", enfe);
            }
            Collection<Student> studentCollection = levelAcademic.getStudentCollection();
            for (Student studentCollectionStudent : studentCollection) {
                studentCollectionStudent.getLevelAcademicCollection().remove(levelAcademic);
                studentCollectionStudent = em.merge(studentCollectionStudent);
            }
            em.remove(levelAcademic);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LevelAcademic> findLevelAcademicEntities() {
        return findLevelAcademicEntities(true, -1, -1);
    }

    public List<LevelAcademic> findLevelAcademicEntities(int maxResults, int firstResult) {
        return findLevelAcademicEntities(false, maxResults, firstResult);
    }

    private List<LevelAcademic> findLevelAcademicEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LevelAcademic.class));
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

    public LevelAcademic findLevelAcademic(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LevelAcademic.class, id);
        } finally {
            em.close();
        }
    }

    public int getLevelAcademicCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LevelAcademic> rt = cq.from(LevelAcademic.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
