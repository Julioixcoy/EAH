/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import Entidades.BlackBox;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Family;
import Entidades.Emotion;
import Entidades.FamilyComplete;
import Entidades.Finance;
import Entidades.HistorialMedico;
import Entidades.LevelAcademic;
import Entidades.Module;
import Entidades.Parents;
import Entidades.Persona;
import Entidades.State;
import Entidades.UserModule;
import Entidades.Student;
import Entidades.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author julio
 *
 */
public class BlackBoxJpaController implements Serializable {

    public BlackBoxJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BlackBox blackBox) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Family familyidFamily = blackBox.getFamilyidFamily();
            if (familyidFamily != null) {
                familyidFamily = em.getReference(familyidFamily.getClass(), familyidFamily.getIdFamily());
                blackBox.setFamilyidFamily(familyidFamily);
            }
            Emotion emotion = blackBox.getEmotion();
            if (emotion != null) {
                emotion = em.getReference(emotion.getClass(), emotion.getEmotionPK());
                blackBox.setEmotion(emotion);
            }
            FamilyComplete familyComplete = blackBox.getFamilyComplete();
            if (familyComplete != null) {
                familyComplete = em.getReference(familyComplete.getClass(), familyComplete.getFamilyCompletePK());
                blackBox.setFamilyComplete(familyComplete);
            }
            Finance finance = blackBox.getFinance();
            if (finance != null) {
                finance = em.getReference(finance.getClass(), finance.getFinancePK());
                blackBox.setFinance(finance);
            }
            HistorialMedico historialMedicoidHistorialMedico = blackBox.getHistorialMedicoidHistorialMedico();
            if (historialMedicoidHistorialMedico != null) {
                historialMedicoidHistorialMedico = em.getReference(historialMedicoidHistorialMedico.getClass(), historialMedicoidHistorialMedico.getIdHistorialMedico());
                blackBox.setHistorialMedicoidHistorialMedico(historialMedicoidHistorialMedico);
            }
            LevelAcademic levelAcademicidLevelAcademic = blackBox.getLevelAcademicidLevelAcademic();
            if (levelAcademicidLevelAcademic != null) {
                levelAcademicidLevelAcademic = em.getReference(levelAcademicidLevelAcademic.getClass(), levelAcademicidLevelAcademic.getIdLevelAcademic());
                blackBox.setLevelAcademicidLevelAcademic(levelAcademicidLevelAcademic);
            }
            Module moduleidModule = blackBox.getModuleidModule();
            if (moduleidModule != null) {
                moduleidModule = em.getReference(moduleidModule.getClass(), moduleidModule.getIdModule());
                blackBox.setModuleidModule(moduleidModule);
            }
            Parents parents = blackBox.getParents();
            if (parents != null) {
                parents = em.getReference(parents.getClass(), parents.getParentsPK());
                blackBox.setParents(parents);
            }
            Persona personaidPersona = blackBox.getPersonaidPersona();
            if (personaidPersona != null) {
                personaidPersona = em.getReference(personaidPersona.getClass(), personaidPersona.getIdPersona());
                blackBox.setPersonaidPersona(personaidPersona);
            }
            State state = blackBox.getState();
            if (state != null) {
                state = em.getReference(state.getClass(), state.getStatePK());
                blackBox.setState(state);
            }
            UserModule userModuleidUserModule = blackBox.getUserModuleidUserModule();
            if (userModuleidUserModule != null) {
                userModuleidUserModule = em.getReference(userModuleidUserModule.getClass(), userModuleidUserModule.getIdUserModule());
                blackBox.setUserModuleidUserModule(userModuleidUserModule);
            }
            Student student = blackBox.getStudent();
            if (student != null) {
                student = em.getReference(student.getClass(), student.getStudentPK());
                blackBox.setStudent(student);
            }
            User useriduser = blackBox.getUseriduser();
            if (useriduser != null) {
                useriduser = em.getReference(useriduser.getClass(), useriduser.getIduser());
                blackBox.setUseriduser(useriduser);
            }
            em.persist(blackBox);
            if (familyidFamily != null) {
                familyidFamily.getBlackBoxCollection().add(blackBox);
                familyidFamily = em.merge(familyidFamily);
            }
            if (emotion != null) {
                emotion.getBlackBoxCollection().add(blackBox);
                emotion = em.merge(emotion);
            }
            if (familyComplete != null) {
                familyComplete.getBlackBoxCollection().add(blackBox);
                familyComplete = em.merge(familyComplete);
            }
            if (finance != null) {
                finance.getBlackBoxCollection().add(blackBox);
                finance = em.merge(finance);
            }
            if (historialMedicoidHistorialMedico != null) {
                historialMedicoidHistorialMedico.getBlackBoxCollection().add(blackBox);
                historialMedicoidHistorialMedico = em.merge(historialMedicoidHistorialMedico);
            }
            if (levelAcademicidLevelAcademic != null) {
                levelAcademicidLevelAcademic.getBlackBoxCollection().add(blackBox);
                levelAcademicidLevelAcademic = em.merge(levelAcademicidLevelAcademic);
            }
            if (moduleidModule != null) {
                moduleidModule.getBlackBoxCollection().add(blackBox);
                moduleidModule = em.merge(moduleidModule);
            }
            if (parents != null) {
                parents.getBlackBoxCollection().add(blackBox);
                parents = em.merge(parents);
            }
            if (personaidPersona != null) {
                personaidPersona.getBlackBoxCollection().add(blackBox);
                personaidPersona = em.merge(personaidPersona);
            }
            if (state != null) {
                state.getBlackBoxCollection().add(blackBox);
                state = em.merge(state);
            }
            if (userModuleidUserModule != null) {
                userModuleidUserModule.getBlackBoxCollection().add(blackBox);
                userModuleidUserModule = em.merge(userModuleidUserModule);
            }
            if (student != null) {
                student.getBlackBoxCollection().add(blackBox);
                student = em.merge(student);
            }
            if (useriduser != null) {
                useriduser.getBlackBoxCollection().add(blackBox);
                useriduser = em.merge(useriduser);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBlackBox(blackBox.getIdBlackBox()) != null) {
                throw new PreexistingEntityException("BlackBox " + blackBox + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BlackBox blackBox) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BlackBox persistentBlackBox = em.find(BlackBox.class, blackBox.getIdBlackBox());
            Family familyidFamilyOld = persistentBlackBox.getFamilyidFamily();
            Family familyidFamilyNew = blackBox.getFamilyidFamily();
            Emotion emotionOld = persistentBlackBox.getEmotion();
            Emotion emotionNew = blackBox.getEmotion();
            FamilyComplete familyCompleteOld = persistentBlackBox.getFamilyComplete();
            FamilyComplete familyCompleteNew = blackBox.getFamilyComplete();
            Finance financeOld = persistentBlackBox.getFinance();
            Finance financeNew = blackBox.getFinance();
            HistorialMedico historialMedicoidHistorialMedicoOld = persistentBlackBox.getHistorialMedicoidHistorialMedico();
            HistorialMedico historialMedicoidHistorialMedicoNew = blackBox.getHistorialMedicoidHistorialMedico();
            LevelAcademic levelAcademicidLevelAcademicOld = persistentBlackBox.getLevelAcademicidLevelAcademic();
            LevelAcademic levelAcademicidLevelAcademicNew = blackBox.getLevelAcademicidLevelAcademic();
            Module moduleidModuleOld = persistentBlackBox.getModuleidModule();
            Module moduleidModuleNew = blackBox.getModuleidModule();
            Parents parentsOld = persistentBlackBox.getParents();
            Parents parentsNew = blackBox.getParents();
            Persona personaidPersonaOld = persistentBlackBox.getPersonaidPersona();
            Persona personaidPersonaNew = blackBox.getPersonaidPersona();
            State stateOld = persistentBlackBox.getState();
            State stateNew = blackBox.getState();
            UserModule userModuleidUserModuleOld = persistentBlackBox.getUserModuleidUserModule();
            UserModule userModuleidUserModuleNew = blackBox.getUserModuleidUserModule();
            Student studentOld = persistentBlackBox.getStudent();
            Student studentNew = blackBox.getStudent();
            User useriduserOld = persistentBlackBox.getUseriduser();
            User useriduserNew = blackBox.getUseriduser();
            if (familyidFamilyNew != null) {
                familyidFamilyNew = em.getReference(familyidFamilyNew.getClass(), familyidFamilyNew.getIdFamily());
                blackBox.setFamilyidFamily(familyidFamilyNew);
            }
            if (emotionNew != null) {
                emotionNew = em.getReference(emotionNew.getClass(), emotionNew.getEmotionPK());
                blackBox.setEmotion(emotionNew);
            }
            if (familyCompleteNew != null) {
                familyCompleteNew = em.getReference(familyCompleteNew.getClass(), familyCompleteNew.getFamilyCompletePK());
                blackBox.setFamilyComplete(familyCompleteNew);
            }
            if (financeNew != null) {
                financeNew = em.getReference(financeNew.getClass(), financeNew.getFinancePK());
                blackBox.setFinance(financeNew);
            }
            if (historialMedicoidHistorialMedicoNew != null) {
                historialMedicoidHistorialMedicoNew = em.getReference(historialMedicoidHistorialMedicoNew.getClass(), historialMedicoidHistorialMedicoNew.getIdHistorialMedico());
                blackBox.setHistorialMedicoidHistorialMedico(historialMedicoidHistorialMedicoNew);
            }
            if (levelAcademicidLevelAcademicNew != null) {
                levelAcademicidLevelAcademicNew = em.getReference(levelAcademicidLevelAcademicNew.getClass(), levelAcademicidLevelAcademicNew.getIdLevelAcademic());
                blackBox.setLevelAcademicidLevelAcademic(levelAcademicidLevelAcademicNew);
            }
            if (moduleidModuleNew != null) {
                moduleidModuleNew = em.getReference(moduleidModuleNew.getClass(), moduleidModuleNew.getIdModule());
                blackBox.setModuleidModule(moduleidModuleNew);
            }
            if (parentsNew != null) {
                parentsNew = em.getReference(parentsNew.getClass(), parentsNew.getParentsPK());
                blackBox.setParents(parentsNew);
            }
            if (personaidPersonaNew != null) {
                personaidPersonaNew = em.getReference(personaidPersonaNew.getClass(), personaidPersonaNew.getIdPersona());
                blackBox.setPersonaidPersona(personaidPersonaNew);
            }
            if (stateNew != null) {
                stateNew = em.getReference(stateNew.getClass(), stateNew.getStatePK());
                blackBox.setState(stateNew);
            }
            if (userModuleidUserModuleNew != null) {
                userModuleidUserModuleNew = em.getReference(userModuleidUserModuleNew.getClass(), userModuleidUserModuleNew.getIdUserModule());
                blackBox.setUserModuleidUserModule(userModuleidUserModuleNew);
            }
            if (studentNew != null) {
                studentNew = em.getReference(studentNew.getClass(), studentNew.getStudentPK());
                blackBox.setStudent(studentNew);
            }
            if (useriduserNew != null) {
                useriduserNew = em.getReference(useriduserNew.getClass(), useriduserNew.getIduser());
                blackBox.setUseriduser(useriduserNew);
            }
            blackBox = em.merge(blackBox);
            if (familyidFamilyOld != null && !familyidFamilyOld.equals(familyidFamilyNew)) {
                familyidFamilyOld.getBlackBoxCollection().remove(blackBox);
                familyidFamilyOld = em.merge(familyidFamilyOld);
            }
            if (familyidFamilyNew != null && !familyidFamilyNew.equals(familyidFamilyOld)) {
                familyidFamilyNew.getBlackBoxCollection().add(blackBox);
                familyidFamilyNew = em.merge(familyidFamilyNew);
            }
            if (emotionOld != null && !emotionOld.equals(emotionNew)) {
                emotionOld.getBlackBoxCollection().remove(blackBox);
                emotionOld = em.merge(emotionOld);
            }
            if (emotionNew != null && !emotionNew.equals(emotionOld)) {
                emotionNew.getBlackBoxCollection().add(blackBox);
                emotionNew = em.merge(emotionNew);
            }
            if (familyCompleteOld != null && !familyCompleteOld.equals(familyCompleteNew)) {
                familyCompleteOld.getBlackBoxCollection().remove(blackBox);
                familyCompleteOld = em.merge(familyCompleteOld);
            }
            if (familyCompleteNew != null && !familyCompleteNew.equals(familyCompleteOld)) {
                familyCompleteNew.getBlackBoxCollection().add(blackBox);
                familyCompleteNew = em.merge(familyCompleteNew);
            }
            if (financeOld != null && !financeOld.equals(financeNew)) {
                financeOld.getBlackBoxCollection().remove(blackBox);
                financeOld = em.merge(financeOld);
            }
            if (financeNew != null && !financeNew.equals(financeOld)) {
                financeNew.getBlackBoxCollection().add(blackBox);
                financeNew = em.merge(financeNew);
            }
            if (historialMedicoidHistorialMedicoOld != null && !historialMedicoidHistorialMedicoOld.equals(historialMedicoidHistorialMedicoNew)) {
                historialMedicoidHistorialMedicoOld.getBlackBoxCollection().remove(blackBox);
                historialMedicoidHistorialMedicoOld = em.merge(historialMedicoidHistorialMedicoOld);
            }
            if (historialMedicoidHistorialMedicoNew != null && !historialMedicoidHistorialMedicoNew.equals(historialMedicoidHistorialMedicoOld)) {
                historialMedicoidHistorialMedicoNew.getBlackBoxCollection().add(blackBox);
                historialMedicoidHistorialMedicoNew = em.merge(historialMedicoidHistorialMedicoNew);
            }
            if (levelAcademicidLevelAcademicOld != null && !levelAcademicidLevelAcademicOld.equals(levelAcademicidLevelAcademicNew)) {
                levelAcademicidLevelAcademicOld.getBlackBoxCollection().remove(blackBox);
                levelAcademicidLevelAcademicOld = em.merge(levelAcademicidLevelAcademicOld);
            }
            if (levelAcademicidLevelAcademicNew != null && !levelAcademicidLevelAcademicNew.equals(levelAcademicidLevelAcademicOld)) {
                levelAcademicidLevelAcademicNew.getBlackBoxCollection().add(blackBox);
                levelAcademicidLevelAcademicNew = em.merge(levelAcademicidLevelAcademicNew);
            }
            if (moduleidModuleOld != null && !moduleidModuleOld.equals(moduleidModuleNew)) {
                moduleidModuleOld.getBlackBoxCollection().remove(blackBox);
                moduleidModuleOld = em.merge(moduleidModuleOld);
            }
            if (moduleidModuleNew != null && !moduleidModuleNew.equals(moduleidModuleOld)) {
                moduleidModuleNew.getBlackBoxCollection().add(blackBox);
                moduleidModuleNew = em.merge(moduleidModuleNew);
            }
            if (parentsOld != null && !parentsOld.equals(parentsNew)) {
                parentsOld.getBlackBoxCollection().remove(blackBox);
                parentsOld = em.merge(parentsOld);
            }
            if (parentsNew != null && !parentsNew.equals(parentsOld)) {
                parentsNew.getBlackBoxCollection().add(blackBox);
                parentsNew = em.merge(parentsNew);
            }
            if (personaidPersonaOld != null && !personaidPersonaOld.equals(personaidPersonaNew)) {
                personaidPersonaOld.getBlackBoxCollection().remove(blackBox);
                personaidPersonaOld = em.merge(personaidPersonaOld);
            }
            if (personaidPersonaNew != null && !personaidPersonaNew.equals(personaidPersonaOld)) {
                personaidPersonaNew.getBlackBoxCollection().add(blackBox);
                personaidPersonaNew = em.merge(personaidPersonaNew);
            }
            if (stateOld != null && !stateOld.equals(stateNew)) {
                stateOld.getBlackBoxCollection().remove(blackBox);
                stateOld = em.merge(stateOld);
            }
            if (stateNew != null && !stateNew.equals(stateOld)) {
                stateNew.getBlackBoxCollection().add(blackBox);
                stateNew = em.merge(stateNew);
            }
            if (userModuleidUserModuleOld != null && !userModuleidUserModuleOld.equals(userModuleidUserModuleNew)) {
                userModuleidUserModuleOld.getBlackBoxCollection().remove(blackBox);
                userModuleidUserModuleOld = em.merge(userModuleidUserModuleOld);
            }
            if (userModuleidUserModuleNew != null && !userModuleidUserModuleNew.equals(userModuleidUserModuleOld)) {
                userModuleidUserModuleNew.getBlackBoxCollection().add(blackBox);
                userModuleidUserModuleNew = em.merge(userModuleidUserModuleNew);
            }
            if (studentOld != null && !studentOld.equals(studentNew)) {
                studentOld.getBlackBoxCollection().remove(blackBox);
                studentOld = em.merge(studentOld);
            }
            if (studentNew != null && !studentNew.equals(studentOld)) {
                studentNew.getBlackBoxCollection().add(blackBox);
                studentNew = em.merge(studentNew);
            }
            if (useriduserOld != null && !useriduserOld.equals(useriduserNew)) {
                useriduserOld.getBlackBoxCollection().remove(blackBox);
                useriduserOld = em.merge(useriduserOld);
            }
            if (useriduserNew != null && !useriduserNew.equals(useriduserOld)) {
                useriduserNew.getBlackBoxCollection().add(blackBox);
                useriduserNew = em.merge(useriduserNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = blackBox.getIdBlackBox();
                if (findBlackBox(id) == null) {
                    throw new NonexistentEntityException("The blackBox with id " + id + " no longer exists.");
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
            BlackBox blackBox;
            try {
                blackBox = em.getReference(BlackBox.class, id);
                blackBox.getIdBlackBox();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The blackBox with id " + id + " no longer exists.", enfe);
            }
            Family familyidFamily = blackBox.getFamilyidFamily();
            if (familyidFamily != null) {
                familyidFamily.getBlackBoxCollection().remove(blackBox);
                familyidFamily = em.merge(familyidFamily);
            }
            Emotion emotion = blackBox.getEmotion();
            if (emotion != null) {
                emotion.getBlackBoxCollection().remove(blackBox);
                emotion = em.merge(emotion);
            }
            FamilyComplete familyComplete = blackBox.getFamilyComplete();
            if (familyComplete != null) {
                familyComplete.getBlackBoxCollection().remove(blackBox);
                familyComplete = em.merge(familyComplete);
            }
            Finance finance = blackBox.getFinance();
            if (finance != null) {
                finance.getBlackBoxCollection().remove(blackBox);
                finance = em.merge(finance);
            }
            HistorialMedico historialMedicoidHistorialMedico = blackBox.getHistorialMedicoidHistorialMedico();
            if (historialMedicoidHistorialMedico != null) {
                historialMedicoidHistorialMedico.getBlackBoxCollection().remove(blackBox);
                historialMedicoidHistorialMedico = em.merge(historialMedicoidHistorialMedico);
            }
            LevelAcademic levelAcademicidLevelAcademic = blackBox.getLevelAcademicidLevelAcademic();
            if (levelAcademicidLevelAcademic != null) {
                levelAcademicidLevelAcademic.getBlackBoxCollection().remove(blackBox);
                levelAcademicidLevelAcademic = em.merge(levelAcademicidLevelAcademic);
            }
            Module moduleidModule = blackBox.getModuleidModule();
            if (moduleidModule != null) {
                moduleidModule.getBlackBoxCollection().remove(blackBox);
                moduleidModule = em.merge(moduleidModule);
            }
            Parents parents = blackBox.getParents();
            if (parents != null) {
                parents.getBlackBoxCollection().remove(blackBox);
                parents = em.merge(parents);
            }
            Persona personaidPersona = blackBox.getPersonaidPersona();
            if (personaidPersona != null) {
                personaidPersona.getBlackBoxCollection().remove(blackBox);
                personaidPersona = em.merge(personaidPersona);
            }
            State state = blackBox.getState();
            if (state != null) {
                state.getBlackBoxCollection().remove(blackBox);
                state = em.merge(state);
            }
            UserModule userModuleidUserModule = blackBox.getUserModuleidUserModule();
            if (userModuleidUserModule != null) {
                userModuleidUserModule.getBlackBoxCollection().remove(blackBox);
                userModuleidUserModule = em.merge(userModuleidUserModule);
            }
            Student student = blackBox.getStudent();
            if (student != null) {
                student.getBlackBoxCollection().remove(blackBox);
                student = em.merge(student);
            }
            User useriduser = blackBox.getUseriduser();
            if (useriduser != null) {
                useriduser.getBlackBoxCollection().remove(blackBox);
                useriduser = em.merge(useriduser);
            }
            em.remove(blackBox);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BlackBox> findBlackBoxEntities() {
        return findBlackBoxEntities(true, -1, -1);
    }

    public List<BlackBox> findBlackBoxEntities(int maxResults, int firstResult) {
        return findBlackBoxEntities(false, maxResults, firstResult);
    }

    private List<BlackBox> findBlackBoxEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BlackBox.class));
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

    public BlackBox findBlackBox(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BlackBox.class, id);
        } finally {
            em.close();
        }
    }

    public int getBlackBoxCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BlackBox> rt = cq.from(BlackBox.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
