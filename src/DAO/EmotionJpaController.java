/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import Entidades.Emotion;
import Entidades.EmotionPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Student;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Julio Fernando Ixcoy
 */
public class EmotionJpaController implements Serializable {

    public EmotionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Emotion emotion) throws PreexistingEntityException, Exception {
        if (emotion.getEmotionPK() == null) {
            emotion.setEmotionPK(new EmotionPK());
        }
        emotion.getEmotionPK().setStudentidStudent(emotion.getStudent().getStudentPK().getIdStudent());
        emotion.getEmotionPK().setStudentPersonaidPersona(emotion.getStudent().getStudentPK().getPersonaidPersona());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Student student = emotion.getStudent();
            if (student != null) {
                student = em.getReference(student.getClass(), student.getStudentPK());
                emotion.setStudent(student);
            }
            em.persist(emotion);
            if (student != null) {
                student.getEmotionCollection().add(emotion);
                student = em.merge(student);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmotion(emotion.getEmotionPK()) != null) {
                throw new PreexistingEntityException("Emotion " + emotion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Emotion emotion) throws NonexistentEntityException, Exception {
        emotion.getEmotionPK().setStudentidStudent(emotion.getStudent().getStudentPK().getIdStudent());
        emotion.getEmotionPK().setStudentPersonaidPersona(emotion.getStudent().getStudentPK().getPersonaidPersona());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Emotion persistentEmotion = em.find(Emotion.class, emotion.getEmotionPK());
            Student studentOld = persistentEmotion.getStudent();
            Student studentNew = emotion.getStudent();
            if (studentNew != null) {
                studentNew = em.getReference(studentNew.getClass(), studentNew.getStudentPK());
                emotion.setStudent(studentNew);
            }
            emotion = em.merge(emotion);
            if (studentOld != null && !studentOld.equals(studentNew)) {
                studentOld.getEmotionCollection().remove(emotion);
                studentOld = em.merge(studentOld);
            }
            if (studentNew != null && !studentNew.equals(studentOld)) {
                studentNew.getEmotionCollection().add(emotion);
                studentNew = em.merge(studentNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                EmotionPK id = emotion.getEmotionPK();
                if (findEmotion(id) == null) {
                    throw new NonexistentEntityException("The emotion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(EmotionPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Emotion emotion;
            try {
                emotion = em.getReference(Emotion.class, id);
                emotion.getEmotionPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The emotion with id " + id + " no longer exists.", enfe);
            }
            Student student = emotion.getStudent();
            if (student != null) {
                student.getEmotionCollection().remove(emotion);
                student = em.merge(student);
            }
            em.remove(emotion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Emotion> findEmotionEntities() {
        return findEmotionEntities(true, -1, -1);
    }

    public List<Emotion> findEmotionEntities(int maxResults, int firstResult) {
        return findEmotionEntities(false, maxResults, firstResult);
    }

    private List<Emotion> findEmotionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Emotion.class));
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

    public Emotion findEmotion(EmotionPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Emotion.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmotionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Emotion> rt = cq.from(Emotion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
