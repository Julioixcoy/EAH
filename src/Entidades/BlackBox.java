/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author julio
 *
 */
@Entity
@Table(name = "BlackBox")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BlackBox.findAll", query = "SELECT b FROM BlackBox b")
    , @NamedQuery(name = "BlackBox.findByIdBlackBox", query = "SELECT b FROM BlackBox b WHERE b.idBlackBox = :idBlackBox")
    , @NamedQuery(name = "BlackBox.findByDate", query = "SELECT b FROM BlackBox b WHERE b.date = :date")
    , @NamedQuery(name = "BlackBox.findByTypeMovement", query = "SELECT b FROM BlackBox b WHERE b.typeMovement = :typeMovement")
    , @NamedQuery(name = "BlackBox.findByJustification", query = "SELECT b FROM BlackBox b WHERE b.justification = :justification")})
public class BlackBox implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idBlackBox")
    private Integer idBlackBox;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "typeMovement")
    private String typeMovement;
    @Column(name = "justification")
    private String justification;
    @JoinColumn(name = "Family_idFamily", referencedColumnName = "idFamily")
    @ManyToOne(optional = false)
    private Family familyidFamily;
    @JoinColumns({
        @JoinColumn(name = "Emotion_idEmotion", referencedColumnName = "idEmotion")
        , @JoinColumn(name = "Emotion_Student_idStudent", referencedColumnName = "Student_idStudent")
        , @JoinColumn(name = "Emotion_Student_Persona_idPersona", referencedColumnName = "Student_Persona_idPersona")
        , @JoinColumn(name = "Emotion_Student_Family_idFamily", referencedColumnName = "Student_Family_idFamily")
        , @JoinColumn(name = "Emotion_Student_Family_Parents_idParents", referencedColumnName = "Student_Family_Parents_idParents")
        , @JoinColumn(name = "Emotion_Student_Family_Parents_Persona_idPersona", referencedColumnName = "Student_Family_Parents_Persona_idPersona")})
    @ManyToOne(optional = false)
    private Emotion emotion;
    @JoinColumns({
        @JoinColumn(name = "FamilyComplete_FamilyCompleteID", referencedColumnName = "FamilyCompleteID")
        , @JoinColumn(name = "FamilyComplete_Parents_idParents", referencedColumnName = "Parents_idParents")
        , @JoinColumn(name = "FamilyComplete_Parents_Persona_idPersona", referencedColumnName = "Parents_Persona_idPersona")
        , @JoinColumn(name = "FamilyComplete_Family_idFamily", referencedColumnName = "Family_idFamily")})
    @ManyToOne(optional = false)
    private FamilyComplete familyComplete;
    @JoinColumns({
        @JoinColumn(name = "Finance_idFinance", referencedColumnName = "idFinance")
        , @JoinColumn(name = "Finance_Student_Persona_idPersona", referencedColumnName = "Student_Persona_idPersona")
        , @JoinColumn(name = "Finance_HistorialMedico_idHistorialMedico", referencedColumnName = "HistorialMedico_idHistorialMedico")
        , @JoinColumn(name = "Finance_Student_idStudent", referencedColumnName = "Student_idStudent")})
    @ManyToOne(optional = false)
    private Finance finance;
    @JoinColumn(name = "HistorialMedico_idHistorialMedico", referencedColumnName = "idHistorialMedico")
    @ManyToOne(optional = false)
    private HistorialMedico historialMedicoidHistorialMedico;
    @JoinColumn(name = "LevelAcademic_idLevelAcademic", referencedColumnName = "idLevelAcademic")
    @ManyToOne(optional = false)
    private LevelAcademic levelAcademicidLevelAcademic;
    @JoinColumn(name = "Module_idModule", referencedColumnName = "idModule")
    @ManyToOne(optional = false)
    private Module moduleidModule;
    @JoinColumns({
        @JoinColumn(name = "Parents_idParents", referencedColumnName = "idParents")
        , @JoinColumn(name = "Parents_Persona_idPersona", referencedColumnName = "Persona_idPersona")})
    @ManyToOne(optional = false)
    private Parents parents;
    @JoinColumn(name = "Persona_idPersona", referencedColumnName = "idPersona")
    @ManyToOne(optional = false)
    private Persona personaidPersona;
    @JoinColumns({
        @JoinColumn(name = "State_idState", referencedColumnName = "idState")
        , @JoinColumn(name = "State_Student_idStudent", referencedColumnName = "Student_idStudent")
        , @JoinColumn(name = "State_Student_Persona_idPersona", referencedColumnName = "Student_Persona_idPersona")})
    @ManyToOne(optional = false)
    private State state;
    @JoinColumn(name = "UserModule_idUserModule", referencedColumnName = "idUserModule")
    @ManyToOne(optional = false)
    private UserModule userModuleidUserModule;
    @JoinColumns({
        @JoinColumn(name = "Student_idStudent", referencedColumnName = "idStudent")
        , @JoinColumn(name = "Student_Persona_idPersona", referencedColumnName = "Persona_idPersona")})
    @ManyToOne(optional = false)
    private Student student;
    @JoinColumn(name = "User_iduser", referencedColumnName = "iduser")
    @ManyToOne(optional = false)
    private User useriduser;

    public BlackBox() {
    }

    public BlackBox(Integer idBlackBox) {
        this.idBlackBox = idBlackBox;
    }

    public Integer getIdBlackBox() {
        return idBlackBox;
    }

    public void setIdBlackBox(Integer idBlackBox) {
        this.idBlackBox = idBlackBox;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTypeMovement() {
        return typeMovement;
    }

    public void setTypeMovement(String typeMovement) {
        this.typeMovement = typeMovement;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public Family getFamilyidFamily() {
        return familyidFamily;
    }

    public void setFamilyidFamily(Family familyidFamily) {
        this.familyidFamily = familyidFamily;
    }

    public Emotion getEmotion() {
        return emotion;
    }

    public void setEmotion(Emotion emotion) {
        this.emotion = emotion;
    }

    public FamilyComplete getFamilyComplete() {
        return familyComplete;
    }

    public void setFamilyComplete(FamilyComplete familyComplete) {
        this.familyComplete = familyComplete;
    }

    public Finance getFinance() {
        return finance;
    }

    public void setFinance(Finance finance) {
        this.finance = finance;
    }

    public HistorialMedico getHistorialMedicoidHistorialMedico() {
        return historialMedicoidHistorialMedico;
    }

    public void setHistorialMedicoidHistorialMedico(HistorialMedico historialMedicoidHistorialMedico) {
        this.historialMedicoidHistorialMedico = historialMedicoidHistorialMedico;
    }

    public LevelAcademic getLevelAcademicidLevelAcademic() {
        return levelAcademicidLevelAcademic;
    }

    public void setLevelAcademicidLevelAcademic(LevelAcademic levelAcademicidLevelAcademic) {
        this.levelAcademicidLevelAcademic = levelAcademicidLevelAcademic;
    }

    public Module getModuleidModule() {
        return moduleidModule;
    }

    public void setModuleidModule(Module moduleidModule) {
        this.moduleidModule = moduleidModule;
    }

    public Parents getParents() {
        return parents;
    }

    public void setParents(Parents parents) {
        this.parents = parents;
    }

    public Persona getPersonaidPersona() {
        return personaidPersona;
    }

    public void setPersonaidPersona(Persona personaidPersona) {
        this.personaidPersona = personaidPersona;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public UserModule getUserModuleidUserModule() {
        return userModuleidUserModule;
    }

    public void setUserModuleidUserModule(UserModule userModuleidUserModule) {
        this.userModuleidUserModule = userModuleidUserModule;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public User getUseriduser() {
        return useriduser;
    }

    public void setUseriduser(User useriduser) {
        this.useriduser = useriduser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBlackBox != null ? idBlackBox.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BlackBox)) {
            return false;
        }
        BlackBox other = (BlackBox) object;
        if ((this.idBlackBox == null && other.idBlackBox != null) || (this.idBlackBox != null && !this.idBlackBox.equals(other.idBlackBox))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.BlackBox[ idBlackBox=" + idBlackBox + " ]";
    }

}
