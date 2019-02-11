/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import Entidades.Finance;
import Entidades.FinancePK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.HistorialMedico;
import Entidades.Student;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Julio Fernando Ixcoy
 */
public class FinanceJpaController implements Serializable {

    public FinanceJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Finance finance) throws PreexistingEntityException, Exception {
        if (finance.getFinancePK() == null) {
            finance.setFinancePK(new FinancePK());
        }
        finance.getFinancePK().setStudentPersonaidPersona(finance.getStudent().getStudentPK().getPersonaidPersona());
        finance.getFinancePK().setHistorialMedicoidHistorialMedico(finance.getHistorialMedico().getIdHistorialMedico());
        finance.getFinancePK().setStudentidStudent(finance.getStudent().getStudentPK().getIdStudent());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HistorialMedico historialMedico = finance.getHistorialMedico();
            if (historialMedico != null) {
                historialMedico = em.getReference(historialMedico.getClass(), historialMedico.getIdHistorialMedico());
                finance.setHistorialMedico(historialMedico);
            }
            Student student = finance.getStudent();
            if (student != null) {
                student = em.getReference(student.getClass(), student.getStudentPK());
                finance.setStudent(student);
            }
            em.persist(finance);
            if (historialMedico != null) {
                historialMedico.getFinanceCollection().add(finance);
                historialMedico = em.merge(historialMedico);
            }
            if (student != null) {
                student.getFinanceCollection().add(finance);
                student = em.merge(student);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFinance(finance.getFinancePK()) != null) {
                throw new PreexistingEntityException("Finance " + finance + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Finance finance) throws NonexistentEntityException, Exception {
        finance.getFinancePK().setStudentPersonaidPersona(finance.getStudent().getStudentPK().getPersonaidPersona());
        finance.getFinancePK().setHistorialMedicoidHistorialMedico(finance.getHistorialMedico().getIdHistorialMedico());
        finance.getFinancePK().setStudentidStudent(finance.getStudent().getStudentPK().getIdStudent());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Finance persistentFinance = em.find(Finance.class, finance.getFinancePK());
            HistorialMedico historialMedicoOld = persistentFinance.getHistorialMedico();
            HistorialMedico historialMedicoNew = finance.getHistorialMedico();
            Student studentOld = persistentFinance.getStudent();
            Student studentNew = finance.getStudent();
            if (historialMedicoNew != null) {
                historialMedicoNew = em.getReference(historialMedicoNew.getClass(), historialMedicoNew.getIdHistorialMedico());
                finance.setHistorialMedico(historialMedicoNew);
            }
            if (studentNew != null) {
                studentNew = em.getReference(studentNew.getClass(), studentNew.getStudentPK());
                finance.setStudent(studentNew);
            }
            finance = em.merge(finance);
            if (historialMedicoOld != null && !historialMedicoOld.equals(historialMedicoNew)) {
                historialMedicoOld.getFinanceCollection().remove(finance);
                historialMedicoOld = em.merge(historialMedicoOld);
            }
            if (historialMedicoNew != null && !historialMedicoNew.equals(historialMedicoOld)) {
                historialMedicoNew.getFinanceCollection().add(finance);
                historialMedicoNew = em.merge(historialMedicoNew);
            }
            if (studentOld != null && !studentOld.equals(studentNew)) {
                studentOld.getFinanceCollection().remove(finance);
                studentOld = em.merge(studentOld);
            }
            if (studentNew != null && !studentNew.equals(studentOld)) {
                studentNew.getFinanceCollection().add(finance);
                studentNew = em.merge(studentNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                FinancePK id = finance.getFinancePK();
                if (findFinance(id) == null) {
                    throw new NonexistentEntityException("The finance with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(FinancePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Finance finance;
            try {
                finance = em.getReference(Finance.class, id);
                finance.getFinancePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The finance with id " + id + " no longer exists.", enfe);
            }
            HistorialMedico historialMedico = finance.getHistorialMedico();
            if (historialMedico != null) {
                historialMedico.getFinanceCollection().remove(finance);
                historialMedico = em.merge(historialMedico);
            }
            Student student = finance.getStudent();
            if (student != null) {
                student.getFinanceCollection().remove(finance);
                student = em.merge(student);
            }
            em.remove(finance);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Finance> findFinanceEntities() {
        return findFinanceEntities(true, -1, -1);
    }

    public List<Finance> findFinanceEntities(int maxResults, int firstResult) {
        return findFinanceEntities(false, maxResults, firstResult);
    }

    private List<Finance> findFinanceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Finance.class));
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

    public Finance findFinance(FinancePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Finance.class, id);
        } finally {
            em.close();
        }
    }

    public int getFinanceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Finance> rt = cq.from(Finance.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
