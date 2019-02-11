/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Finance;
import Entidades.HistorialMedico;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Julio Fernando Ixcoy
 */
public class HistorialMedicoJpaController implements Serializable {

    public HistorialMedicoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HistorialMedico historialMedico) {
        if (historialMedico.getFinanceCollection() == null) {
            historialMedico.setFinanceCollection(new ArrayList<Finance>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Finance> attachedFinanceCollection = new ArrayList<Finance>();
            for (Finance financeCollectionFinanceToAttach : historialMedico.getFinanceCollection()) {
                financeCollectionFinanceToAttach = em.getReference(financeCollectionFinanceToAttach.getClass(), financeCollectionFinanceToAttach.getFinancePK());
                attachedFinanceCollection.add(financeCollectionFinanceToAttach);
            }
            historialMedico.setFinanceCollection(attachedFinanceCollection);
            em.persist(historialMedico);
            for (Finance financeCollectionFinance : historialMedico.getFinanceCollection()) {
                HistorialMedico oldHistorialMedicoOfFinanceCollectionFinance = financeCollectionFinance.getHistorialMedico();
                financeCollectionFinance.setHistorialMedico(historialMedico);
                financeCollectionFinance = em.merge(financeCollectionFinance);
                if (oldHistorialMedicoOfFinanceCollectionFinance != null) {
                    oldHistorialMedicoOfFinanceCollectionFinance.getFinanceCollection().remove(financeCollectionFinance);
                    oldHistorialMedicoOfFinanceCollectionFinance = em.merge(oldHistorialMedicoOfFinanceCollectionFinance);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HistorialMedico historialMedico) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HistorialMedico persistentHistorialMedico = em.find(HistorialMedico.class, historialMedico.getIdHistorialMedico());
            Collection<Finance> financeCollectionOld = persistentHistorialMedico.getFinanceCollection();
            Collection<Finance> financeCollectionNew = historialMedico.getFinanceCollection();
            List<String> illegalOrphanMessages = null;
            for (Finance financeCollectionOldFinance : financeCollectionOld) {
                if (!financeCollectionNew.contains(financeCollectionOldFinance)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Finance " + financeCollectionOldFinance + " since its historialMedico field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Finance> attachedFinanceCollectionNew = new ArrayList<Finance>();
            for (Finance financeCollectionNewFinanceToAttach : financeCollectionNew) {
                financeCollectionNewFinanceToAttach = em.getReference(financeCollectionNewFinanceToAttach.getClass(), financeCollectionNewFinanceToAttach.getFinancePK());
                attachedFinanceCollectionNew.add(financeCollectionNewFinanceToAttach);
            }
            financeCollectionNew = attachedFinanceCollectionNew;
            historialMedico.setFinanceCollection(financeCollectionNew);
            historialMedico = em.merge(historialMedico);
            for (Finance financeCollectionNewFinance : financeCollectionNew) {
                if (!financeCollectionOld.contains(financeCollectionNewFinance)) {
                    HistorialMedico oldHistorialMedicoOfFinanceCollectionNewFinance = financeCollectionNewFinance.getHistorialMedico();
                    financeCollectionNewFinance.setHistorialMedico(historialMedico);
                    financeCollectionNewFinance = em.merge(financeCollectionNewFinance);
                    if (oldHistorialMedicoOfFinanceCollectionNewFinance != null && !oldHistorialMedicoOfFinanceCollectionNewFinance.equals(historialMedico)) {
                        oldHistorialMedicoOfFinanceCollectionNewFinance.getFinanceCollection().remove(financeCollectionNewFinance);
                        oldHistorialMedicoOfFinanceCollectionNewFinance = em.merge(oldHistorialMedicoOfFinanceCollectionNewFinance);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = historialMedico.getIdHistorialMedico();
                if (findHistorialMedico(id) == null) {
                    throw new NonexistentEntityException("The historialMedico with id " + id + " no longer exists.");
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
            HistorialMedico historialMedico;
            try {
                historialMedico = em.getReference(HistorialMedico.class, id);
                historialMedico.getIdHistorialMedico();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historialMedico with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Finance> financeCollectionOrphanCheck = historialMedico.getFinanceCollection();
            for (Finance financeCollectionOrphanCheckFinance : financeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This HistorialMedico (" + historialMedico + ") cannot be destroyed since the Finance " + financeCollectionOrphanCheckFinance + " in its financeCollection field has a non-nullable historialMedico field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(historialMedico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<HistorialMedico> findHistorialMedicoEntities() {
        return findHistorialMedicoEntities(true, -1, -1);
    }

    public List<HistorialMedico> findHistorialMedicoEntities(int maxResults, int firstResult) {
        return findHistorialMedicoEntities(false, maxResults, firstResult);
    }

    private List<HistorialMedico> findHistorialMedicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HistorialMedico.class));
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

    public HistorialMedico findHistorialMedico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HistorialMedico.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistorialMedicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HistorialMedico> rt = cq.from(HistorialMedico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
