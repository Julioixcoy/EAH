/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import Entidades.State;
import Entidades.StatePK;
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
public class StateJpaController implements Serializable {

    public StateJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(State state) throws PreexistingEntityException, Exception {
        if (state.getStatePK() == null) {
            state.setStatePK(new StatePK());
        }
        state.getStatePK().setStudentPersonaidPersona(state.getStudent().getStudentPK().getPersonaidPersona());
        state.getStatePK().setStudentidStudent(state.getStudent().getStudentPK().getIdStudent());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Student student = state.getStudent();
            if (student != null) {
                student = em.getReference(student.getClass(), student.getStudentPK());
                state.setStudent(student);
            }
            em.persist(state);
            if (student != null) {
                student.getStateCollection().add(state);
                student = em.merge(student);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findState(state.getStatePK()) != null) {
                throw new PreexistingEntityException("State " + state + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(State state) throws NonexistentEntityException, Exception {
        state.getStatePK().setStudentPersonaidPersona(state.getStudent().getStudentPK().getPersonaidPersona());
        state.getStatePK().setStudentidStudent(state.getStudent().getStudentPK().getIdStudent());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            State persistentState = em.find(State.class, state.getStatePK());
            Student studentOld = persistentState.getStudent();
            Student studentNew = state.getStudent();
            if (studentNew != null) {
                studentNew = em.getReference(studentNew.getClass(), studentNew.getStudentPK());
                state.setStudent(studentNew);
            }
            state = em.merge(state);
            if (studentOld != null && !studentOld.equals(studentNew)) {
                studentOld.getStateCollection().remove(state);
                studentOld = em.merge(studentOld);
            }
            if (studentNew != null && !studentNew.equals(studentOld)) {
                studentNew.getStateCollection().add(state);
                studentNew = em.merge(studentNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                StatePK id = state.getStatePK();
                if (findState(id) == null) {
                    throw new NonexistentEntityException("The state with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(StatePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            State state;
            try {
                state = em.getReference(State.class, id);
                state.getStatePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The state with id " + id + " no longer exists.", enfe);
            }
            Student student = state.getStudent();
            if (student != null) {
                student.getStateCollection().remove(state);
                student = em.merge(student);
            }
            em.remove(state);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<State> findStateEntities() {
        return findStateEntities(true, -1, -1);
    }

    public List<State> findStateEntities(int maxResults, int firstResult) {
        return findStateEntities(false, maxResults, firstResult);
    }

    private List<State> findStateEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(State.class));
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

    public State findState(StatePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(State.class, id);
        } finally {
            em.close();
        }
    }

    public int getStateCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<State> rt = cq.from(State.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
