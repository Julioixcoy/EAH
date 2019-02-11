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
import Entidades.Parents;
import Entidades.Persona;
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
public class PersonaJpaController implements Serializable {

    public PersonaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Persona persona) {
        if (persona.getParentsCollection() == null) {
            persona.setParentsCollection(new ArrayList<Parents>());
        }
        if (persona.getStudentCollection() == null) {
            persona.setStudentCollection(new ArrayList<Student>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Parents> attachedParentsCollection = new ArrayList<Parents>();
            for (Parents parentsCollectionParentsToAttach : persona.getParentsCollection()) {
                parentsCollectionParentsToAttach = em.getReference(parentsCollectionParentsToAttach.getClass(), parentsCollectionParentsToAttach.getParentsPK());
                attachedParentsCollection.add(parentsCollectionParentsToAttach);
            }
            persona.setParentsCollection(attachedParentsCollection);
            Collection<Student> attachedStudentCollection = new ArrayList<Student>();
            for (Student studentCollectionStudentToAttach : persona.getStudentCollection()) {
                studentCollectionStudentToAttach = em.getReference(studentCollectionStudentToAttach.getClass(), studentCollectionStudentToAttach.getStudentPK());
                attachedStudentCollection.add(studentCollectionStudentToAttach);
            }
            persona.setStudentCollection(attachedStudentCollection);
            em.persist(persona);
            for (Parents parentsCollectionParents : persona.getParentsCollection()) {
                Persona oldPersonaOfParentsCollectionParents = parentsCollectionParents.getPersona();
                parentsCollectionParents.setPersona(persona);
                parentsCollectionParents = em.merge(parentsCollectionParents);
                if (oldPersonaOfParentsCollectionParents != null) {
                    oldPersonaOfParentsCollectionParents.getParentsCollection().remove(parentsCollectionParents);
                    oldPersonaOfParentsCollectionParents = em.merge(oldPersonaOfParentsCollectionParents);
                }
            }
            for (Student studentCollectionStudent : persona.getStudentCollection()) {
                Persona oldPersonaOfStudentCollectionStudent = studentCollectionStudent.getPersona();
                studentCollectionStudent.setPersona(persona);
                studentCollectionStudent = em.merge(studentCollectionStudent);
                if (oldPersonaOfStudentCollectionStudent != null) {
                    oldPersonaOfStudentCollectionStudent.getStudentCollection().remove(studentCollectionStudent);
                    oldPersonaOfStudentCollectionStudent = em.merge(oldPersonaOfStudentCollectionStudent);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Persona persona) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persistentPersona = em.find(Persona.class, persona.getIdPersona());
            Collection<Parents> parentsCollectionOld = persistentPersona.getParentsCollection();
            Collection<Parents> parentsCollectionNew = persona.getParentsCollection();
            Collection<Student> studentCollectionOld = persistentPersona.getStudentCollection();
            Collection<Student> studentCollectionNew = persona.getStudentCollection();
            List<String> illegalOrphanMessages = null;
            for (Parents parentsCollectionOldParents : parentsCollectionOld) {
                if (!parentsCollectionNew.contains(parentsCollectionOldParents)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Parents " + parentsCollectionOldParents + " since its persona field is not nullable.");
                }
            }
            for (Student studentCollectionOldStudent : studentCollectionOld) {
                if (!studentCollectionNew.contains(studentCollectionOldStudent)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Student " + studentCollectionOldStudent + " since its persona field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Parents> attachedParentsCollectionNew = new ArrayList<Parents>();
            for (Parents parentsCollectionNewParentsToAttach : parentsCollectionNew) {
                parentsCollectionNewParentsToAttach = em.getReference(parentsCollectionNewParentsToAttach.getClass(), parentsCollectionNewParentsToAttach.getParentsPK());
                attachedParentsCollectionNew.add(parentsCollectionNewParentsToAttach);
            }
            parentsCollectionNew = attachedParentsCollectionNew;
            persona.setParentsCollection(parentsCollectionNew);
            Collection<Student> attachedStudentCollectionNew = new ArrayList<Student>();
            for (Student studentCollectionNewStudentToAttach : studentCollectionNew) {
                studentCollectionNewStudentToAttach = em.getReference(studentCollectionNewStudentToAttach.getClass(), studentCollectionNewStudentToAttach.getStudentPK());
                attachedStudentCollectionNew.add(studentCollectionNewStudentToAttach);
            }
            studentCollectionNew = attachedStudentCollectionNew;
            persona.setStudentCollection(studentCollectionNew);
            persona = em.merge(persona);
            for (Parents parentsCollectionNewParents : parentsCollectionNew) {
                if (!parentsCollectionOld.contains(parentsCollectionNewParents)) {
                    Persona oldPersonaOfParentsCollectionNewParents = parentsCollectionNewParents.getPersona();
                    parentsCollectionNewParents.setPersona(persona);
                    parentsCollectionNewParents = em.merge(parentsCollectionNewParents);
                    if (oldPersonaOfParentsCollectionNewParents != null && !oldPersonaOfParentsCollectionNewParents.equals(persona)) {
                        oldPersonaOfParentsCollectionNewParents.getParentsCollection().remove(parentsCollectionNewParents);
                        oldPersonaOfParentsCollectionNewParents = em.merge(oldPersonaOfParentsCollectionNewParents);
                    }
                }
            }
            for (Student studentCollectionNewStudent : studentCollectionNew) {
                if (!studentCollectionOld.contains(studentCollectionNewStudent)) {
                    Persona oldPersonaOfStudentCollectionNewStudent = studentCollectionNewStudent.getPersona();
                    studentCollectionNewStudent.setPersona(persona);
                    studentCollectionNewStudent = em.merge(studentCollectionNewStudent);
                    if (oldPersonaOfStudentCollectionNewStudent != null && !oldPersonaOfStudentCollectionNewStudent.equals(persona)) {
                        oldPersonaOfStudentCollectionNewStudent.getStudentCollection().remove(studentCollectionNewStudent);
                        oldPersonaOfStudentCollectionNewStudent = em.merge(oldPersonaOfStudentCollectionNewStudent);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = persona.getIdPersona();
                if (findPersona(id) == null) {
                    throw new NonexistentEntityException("The persona with id " + id + " no longer exists.");
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
            Persona persona;
            try {
                persona = em.getReference(Persona.class, id);
                persona.getIdPersona();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persona with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Parents> parentsCollectionOrphanCheck = persona.getParentsCollection();
            for (Parents parentsCollectionOrphanCheckParents : parentsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Parents " + parentsCollectionOrphanCheckParents + " in its parentsCollection field has a non-nullable persona field.");
            }
            Collection<Student> studentCollectionOrphanCheck = persona.getStudentCollection();
            for (Student studentCollectionOrphanCheckStudent : studentCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Student " + studentCollectionOrphanCheckStudent + " in its studentCollection field has a non-nullable persona field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(persona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Persona> findPersonaEntities() {
        return findPersonaEntities(true, -1, -1);
    }

    public List<Persona> findPersonaEntities(int maxResults, int firstResult) {
        return findPersonaEntities(false, maxResults, firstResult);
    }

    private List<Persona> findPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Persona.class));
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

    public Persona findPersona(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Persona.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Persona> rt = cq.from(Persona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
