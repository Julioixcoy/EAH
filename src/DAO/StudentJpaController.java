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
import Entidades.Family;
import Entidades.Persona;
import Entidades.LevelAcademic;
import java.util.ArrayList;
import java.util.Collection;
import Entidades.Finance;
import Entidades.State;
import Entidades.Emotion;
import Entidades.Student;
import Entidades.StudentPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Julio Fernando Ixcoy
 */
public class StudentJpaController implements Serializable {

    public StudentJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Student student) throws PreexistingEntityException, Exception {
        if (student.getStudentPK() == null) {
            student.setStudentPK(new StudentPK());
        }
        if (student.getLevelAcademicCollection() == null) {
            student.setLevelAcademicCollection(new ArrayList<LevelAcademic>());
        }
        if (student.getFinanceCollection() == null) {
            student.setFinanceCollection(new ArrayList<Finance>());
        }
        if (student.getStateCollection() == null) {
            student.setStateCollection(new ArrayList<State>());
        }
        if (student.getEmotionCollection() == null) {
            student.setEmotionCollection(new ArrayList<Emotion>());
        }
        student.getStudentPK().setPersonaidPersona(student.getPersona().getIdPersona());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Family familyidFamily = student.getFamilyidFamily();
            if (familyidFamily != null) {
                familyidFamily = em.getReference(familyidFamily.getClass(), familyidFamily.getIdFamily());
                student.setFamilyidFamily(familyidFamily);
            }
            Persona persona = student.getPersona();
            if (persona != null) {
                persona = em.getReference(persona.getClass(), persona.getIdPersona());
                student.setPersona(persona);
            }
            Collection<LevelAcademic> attachedLevelAcademicCollection = new ArrayList<LevelAcademic>();
            for (LevelAcademic levelAcademicCollectionLevelAcademicToAttach : student.getLevelAcademicCollection()) {
                levelAcademicCollectionLevelAcademicToAttach = em.getReference(levelAcademicCollectionLevelAcademicToAttach.getClass(), levelAcademicCollectionLevelAcademicToAttach.getIdLevelAcademic());
                attachedLevelAcademicCollection.add(levelAcademicCollectionLevelAcademicToAttach);
            }
            student.setLevelAcademicCollection(attachedLevelAcademicCollection);
            Collection<Finance> attachedFinanceCollection = new ArrayList<Finance>();
            for (Finance financeCollectionFinanceToAttach : student.getFinanceCollection()) {
                financeCollectionFinanceToAttach = em.getReference(financeCollectionFinanceToAttach.getClass(), financeCollectionFinanceToAttach.getFinancePK());
                attachedFinanceCollection.add(financeCollectionFinanceToAttach);
            }
            student.setFinanceCollection(attachedFinanceCollection);
            Collection<State> attachedStateCollection = new ArrayList<State>();
            for (State stateCollectionStateToAttach : student.getStateCollection()) {
                stateCollectionStateToAttach = em.getReference(stateCollectionStateToAttach.getClass(), stateCollectionStateToAttach.getStatePK());
                attachedStateCollection.add(stateCollectionStateToAttach);
            }
            student.setStateCollection(attachedStateCollection);
            Collection<Emotion> attachedEmotionCollection = new ArrayList<Emotion>();
            for (Emotion emotionCollectionEmotionToAttach : student.getEmotionCollection()) {
                emotionCollectionEmotionToAttach = em.getReference(emotionCollectionEmotionToAttach.getClass(), emotionCollectionEmotionToAttach.getEmotionPK());
                attachedEmotionCollection.add(emotionCollectionEmotionToAttach);
            }
            student.setEmotionCollection(attachedEmotionCollection);
            em.persist(student);
            if (familyidFamily != null) {
                familyidFamily.getStudentCollection().add(student);
                familyidFamily = em.merge(familyidFamily);
            }
            if (persona != null) {
                persona.getStudentCollection().add(student);
                persona = em.merge(persona);
            }
            for (LevelAcademic levelAcademicCollectionLevelAcademic : student.getLevelAcademicCollection()) {
                levelAcademicCollectionLevelAcademic.getStudentCollection().add(student);
                levelAcademicCollectionLevelAcademic = em.merge(levelAcademicCollectionLevelAcademic);
            }
            for (Finance financeCollectionFinance : student.getFinanceCollection()) {
                Student oldStudentOfFinanceCollectionFinance = financeCollectionFinance.getStudent();
                financeCollectionFinance.setStudent(student);
                financeCollectionFinance = em.merge(financeCollectionFinance);
                if (oldStudentOfFinanceCollectionFinance != null) {
                    oldStudentOfFinanceCollectionFinance.getFinanceCollection().remove(financeCollectionFinance);
                    oldStudentOfFinanceCollectionFinance = em.merge(oldStudentOfFinanceCollectionFinance);
                }
            }
            for (State stateCollectionState : student.getStateCollection()) {
                Student oldStudentOfStateCollectionState = stateCollectionState.getStudent();
                stateCollectionState.setStudent(student);
                stateCollectionState = em.merge(stateCollectionState);
                if (oldStudentOfStateCollectionState != null) {
                    oldStudentOfStateCollectionState.getStateCollection().remove(stateCollectionState);
                    oldStudentOfStateCollectionState = em.merge(oldStudentOfStateCollectionState);
                }
            }
            for (Emotion emotionCollectionEmotion : student.getEmotionCollection()) {
                Student oldStudentOfEmotionCollectionEmotion = emotionCollectionEmotion.getStudent();
                emotionCollectionEmotion.setStudent(student);
                emotionCollectionEmotion = em.merge(emotionCollectionEmotion);
                if (oldStudentOfEmotionCollectionEmotion != null) {
                    oldStudentOfEmotionCollectionEmotion.getEmotionCollection().remove(emotionCollectionEmotion);
                    oldStudentOfEmotionCollectionEmotion = em.merge(oldStudentOfEmotionCollectionEmotion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findStudent(student.getStudentPK()) != null) {
                throw new PreexistingEntityException("Student " + student + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Student student) throws IllegalOrphanException, NonexistentEntityException, Exception {
        student.getStudentPK().setPersonaidPersona(student.getPersona().getIdPersona());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Student persistentStudent = em.find(Student.class, student.getStudentPK());
            Family familyidFamilyOld = persistentStudent.getFamilyidFamily();
            Family familyidFamilyNew = student.getFamilyidFamily();
            Persona personaOld = persistentStudent.getPersona();
            Persona personaNew = student.getPersona();
            Collection<LevelAcademic> levelAcademicCollectionOld = persistentStudent.getLevelAcademicCollection();
            Collection<LevelAcademic> levelAcademicCollectionNew = student.getLevelAcademicCollection();
            Collection<Finance> financeCollectionOld = persistentStudent.getFinanceCollection();
            Collection<Finance> financeCollectionNew = student.getFinanceCollection();
            Collection<State> stateCollectionOld = persistentStudent.getStateCollection();
            Collection<State> stateCollectionNew = student.getStateCollection();
            Collection<Emotion> emotionCollectionOld = persistentStudent.getEmotionCollection();
            Collection<Emotion> emotionCollectionNew = student.getEmotionCollection();
            List<String> illegalOrphanMessages = null;
            for (Finance financeCollectionOldFinance : financeCollectionOld) {
                if (!financeCollectionNew.contains(financeCollectionOldFinance)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Finance " + financeCollectionOldFinance + " since its student field is not nullable.");
                }
            }
            for (State stateCollectionOldState : stateCollectionOld) {
                if (!stateCollectionNew.contains(stateCollectionOldState)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain State " + stateCollectionOldState + " since its student field is not nullable.");
                }
            }
            for (Emotion emotionCollectionOldEmotion : emotionCollectionOld) {
                if (!emotionCollectionNew.contains(emotionCollectionOldEmotion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Emotion " + emotionCollectionOldEmotion + " since its student field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (familyidFamilyNew != null) {
                familyidFamilyNew = em.getReference(familyidFamilyNew.getClass(), familyidFamilyNew.getIdFamily());
                student.setFamilyidFamily(familyidFamilyNew);
            }
            if (personaNew != null) {
                personaNew = em.getReference(personaNew.getClass(), personaNew.getIdPersona());
                student.setPersona(personaNew);
            }
            Collection<LevelAcademic> attachedLevelAcademicCollectionNew = new ArrayList<LevelAcademic>();
            for (LevelAcademic levelAcademicCollectionNewLevelAcademicToAttach : levelAcademicCollectionNew) {
                levelAcademicCollectionNewLevelAcademicToAttach = em.getReference(levelAcademicCollectionNewLevelAcademicToAttach.getClass(), levelAcademicCollectionNewLevelAcademicToAttach.getIdLevelAcademic());
                attachedLevelAcademicCollectionNew.add(levelAcademicCollectionNewLevelAcademicToAttach);
            }
            levelAcademicCollectionNew = attachedLevelAcademicCollectionNew;
            student.setLevelAcademicCollection(levelAcademicCollectionNew);
            Collection<Finance> attachedFinanceCollectionNew = new ArrayList<Finance>();
            for (Finance financeCollectionNewFinanceToAttach : financeCollectionNew) {
                financeCollectionNewFinanceToAttach = em.getReference(financeCollectionNewFinanceToAttach.getClass(), financeCollectionNewFinanceToAttach.getFinancePK());
                attachedFinanceCollectionNew.add(financeCollectionNewFinanceToAttach);
            }
            financeCollectionNew = attachedFinanceCollectionNew;
            student.setFinanceCollection(financeCollectionNew);
            Collection<State> attachedStateCollectionNew = new ArrayList<State>();
            for (State stateCollectionNewStateToAttach : stateCollectionNew) {
                stateCollectionNewStateToAttach = em.getReference(stateCollectionNewStateToAttach.getClass(), stateCollectionNewStateToAttach.getStatePK());
                attachedStateCollectionNew.add(stateCollectionNewStateToAttach);
            }
            stateCollectionNew = attachedStateCollectionNew;
            student.setStateCollection(stateCollectionNew);
            Collection<Emotion> attachedEmotionCollectionNew = new ArrayList<Emotion>();
            for (Emotion emotionCollectionNewEmotionToAttach : emotionCollectionNew) {
                emotionCollectionNewEmotionToAttach = em.getReference(emotionCollectionNewEmotionToAttach.getClass(), emotionCollectionNewEmotionToAttach.getEmotionPK());
                attachedEmotionCollectionNew.add(emotionCollectionNewEmotionToAttach);
            }
            emotionCollectionNew = attachedEmotionCollectionNew;
            student.setEmotionCollection(emotionCollectionNew);
            student = em.merge(student);
            if (familyidFamilyOld != null && !familyidFamilyOld.equals(familyidFamilyNew)) {
                familyidFamilyOld.getStudentCollection().remove(student);
                familyidFamilyOld = em.merge(familyidFamilyOld);
            }
            if (familyidFamilyNew != null && !familyidFamilyNew.equals(familyidFamilyOld)) {
                familyidFamilyNew.getStudentCollection().add(student);
                familyidFamilyNew = em.merge(familyidFamilyNew);
            }
            if (personaOld != null && !personaOld.equals(personaNew)) {
                personaOld.getStudentCollection().remove(student);
                personaOld = em.merge(personaOld);
            }
            if (personaNew != null && !personaNew.equals(personaOld)) {
                personaNew.getStudentCollection().add(student);
                personaNew = em.merge(personaNew);
            }
            for (LevelAcademic levelAcademicCollectionOldLevelAcademic : levelAcademicCollectionOld) {
                if (!levelAcademicCollectionNew.contains(levelAcademicCollectionOldLevelAcademic)) {
                    levelAcademicCollectionOldLevelAcademic.getStudentCollection().remove(student);
                    levelAcademicCollectionOldLevelAcademic = em.merge(levelAcademicCollectionOldLevelAcademic);
                }
            }
            for (LevelAcademic levelAcademicCollectionNewLevelAcademic : levelAcademicCollectionNew) {
                if (!levelAcademicCollectionOld.contains(levelAcademicCollectionNewLevelAcademic)) {
                    levelAcademicCollectionNewLevelAcademic.getStudentCollection().add(student);
                    levelAcademicCollectionNewLevelAcademic = em.merge(levelAcademicCollectionNewLevelAcademic);
                }
            }
            for (Finance financeCollectionNewFinance : financeCollectionNew) {
                if (!financeCollectionOld.contains(financeCollectionNewFinance)) {
                    Student oldStudentOfFinanceCollectionNewFinance = financeCollectionNewFinance.getStudent();
                    financeCollectionNewFinance.setStudent(student);
                    financeCollectionNewFinance = em.merge(financeCollectionNewFinance);
                    if (oldStudentOfFinanceCollectionNewFinance != null && !oldStudentOfFinanceCollectionNewFinance.equals(student)) {
                        oldStudentOfFinanceCollectionNewFinance.getFinanceCollection().remove(financeCollectionNewFinance);
                        oldStudentOfFinanceCollectionNewFinance = em.merge(oldStudentOfFinanceCollectionNewFinance);
                    }
                }
            }
            for (State stateCollectionNewState : stateCollectionNew) {
                if (!stateCollectionOld.contains(stateCollectionNewState)) {
                    Student oldStudentOfStateCollectionNewState = stateCollectionNewState.getStudent();
                    stateCollectionNewState.setStudent(student);
                    stateCollectionNewState = em.merge(stateCollectionNewState);
                    if (oldStudentOfStateCollectionNewState != null && !oldStudentOfStateCollectionNewState.equals(student)) {
                        oldStudentOfStateCollectionNewState.getStateCollection().remove(stateCollectionNewState);
                        oldStudentOfStateCollectionNewState = em.merge(oldStudentOfStateCollectionNewState);
                    }
                }
            }
            for (Emotion emotionCollectionNewEmotion : emotionCollectionNew) {
                if (!emotionCollectionOld.contains(emotionCollectionNewEmotion)) {
                    Student oldStudentOfEmotionCollectionNewEmotion = emotionCollectionNewEmotion.getStudent();
                    emotionCollectionNewEmotion.setStudent(student);
                    emotionCollectionNewEmotion = em.merge(emotionCollectionNewEmotion);
                    if (oldStudentOfEmotionCollectionNewEmotion != null && !oldStudentOfEmotionCollectionNewEmotion.equals(student)) {
                        oldStudentOfEmotionCollectionNewEmotion.getEmotionCollection().remove(emotionCollectionNewEmotion);
                        oldStudentOfEmotionCollectionNewEmotion = em.merge(oldStudentOfEmotionCollectionNewEmotion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                StudentPK id = student.getStudentPK();
                if (findStudent(id) == null) {
                    throw new NonexistentEntityException("The student with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(StudentPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Student student;
            try {
                student = em.getReference(Student.class, id);
                student.getStudentPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The student with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Finance> financeCollectionOrphanCheck = student.getFinanceCollection();
            for (Finance financeCollectionOrphanCheckFinance : financeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Student (" + student + ") cannot be destroyed since the Finance " + financeCollectionOrphanCheckFinance + " in its financeCollection field has a non-nullable student field.");
            }
            Collection<State> stateCollectionOrphanCheck = student.getStateCollection();
            for (State stateCollectionOrphanCheckState : stateCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Student (" + student + ") cannot be destroyed since the State " + stateCollectionOrphanCheckState + " in its stateCollection field has a non-nullable student field.");
            }
            Collection<Emotion> emotionCollectionOrphanCheck = student.getEmotionCollection();
            for (Emotion emotionCollectionOrphanCheckEmotion : emotionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Student (" + student + ") cannot be destroyed since the Emotion " + emotionCollectionOrphanCheckEmotion + " in its emotionCollection field has a non-nullable student field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Family familyidFamily = student.getFamilyidFamily();
            if (familyidFamily != null) {
                familyidFamily.getStudentCollection().remove(student);
                familyidFamily = em.merge(familyidFamily);
            }
            Persona persona = student.getPersona();
            if (persona != null) {
                persona.getStudentCollection().remove(student);
                persona = em.merge(persona);
            }
            Collection<LevelAcademic> levelAcademicCollection = student.getLevelAcademicCollection();
            for (LevelAcademic levelAcademicCollectionLevelAcademic : levelAcademicCollection) {
                levelAcademicCollectionLevelAcademic.getStudentCollection().remove(student);
                levelAcademicCollectionLevelAcademic = em.merge(levelAcademicCollectionLevelAcademic);
            }
            em.remove(student);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Student> findStudentEntities() {
        return findStudentEntities(true, -1, -1);
    }

    public List<Student> findStudentEntities(int maxResults, int firstResult) {
        return findStudentEntities(false, maxResults, firstResult);
    }

    private List<Student> findStudentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Student.class));
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

    public Student findStudent(StudentPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Student.class, id);
        } finally {
            em.close();
        }
    }

    public int getStudentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Student> rt = cq.from(Student.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
