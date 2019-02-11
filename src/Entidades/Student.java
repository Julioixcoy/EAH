/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Julio Fernando Ixcoy
 */
@Entity
@Table(name = "Student")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Student.findAll", query = "SELECT s FROM Student s")
    , @NamedQuery(name = "Student.findByIdStudent", query = "SELECT s FROM Student s WHERE s.studentPK.idStudent = :idStudent")
    , @NamedQuery(name = "Student.findByEnable", query = "SELECT s FROM Student s WHERE s.enable = :enable")
    , @NamedQuery(name = "Student.findByDateInit", query = "SELECT s FROM Student s WHERE s.dateInit = :dateInit")
    , @NamedQuery(name = "Student.findByDescripcion", query = "SELECT s FROM Student s WHERE s.descripcion = :descripcion")
    , @NamedQuery(name = "Student.findByTelStudent", query = "SELECT s FROM Student s WHERE s.telStudent = :telStudent")
    , @NamedQuery(name = "Student.findByLivesWith", query = "SELECT s FROM Student s WHERE s.livesWith = :livesWith")
    , @NamedQuery(name = "Student.findByGenero", query = "SELECT s FROM Student s WHERE s.genero = :genero")
    , @NamedQuery(name = "Student.findByPersonaidPersona", query = "SELECT s FROM Student s WHERE s.studentPK.personaidPersona = :personaidPersona")
    , @NamedQuery(name = "Student.findByUse", query = "SELECT s FROM Student s WHERE s.isUse = :isUse")
    , @NamedQuery(name = "Student.findByidFamily", query = "SELECT s FROM Student s WHERE s.familyidFamily.idFamily= :idFamily")
    , @NamedQuery(name = "Student.findByIsHuerfano", query = "SELECT s FROM Student s WHERE s.isHuerfano = :isHuerfano")})
public class Student implements Serializable {

    @Column(name = "school")
    private String school;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private Collection<BlackBox> blackBoxCollection;

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StudentPK studentPK;
    @Basic(optional = false)
    @Column(name = "enable")
    private boolean enable;
    @Basic(optional = false)
    @Column(name = "dateInit")
    @Temporal(TemporalType.DATE)
    private Date dateInit;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "telStudent")
    private String telStudent;
    @Basic(optional = false)
    @Column(name = "livesWith")
    private String livesWith;
    @Column(name = "genero")
    private String genero;
    @Column(name = "isUse")
    private Boolean isUse;
    @Column(name = "isHuerfano")
    private Boolean isHuerfano;
    @ManyToMany(mappedBy = "studentCollection")
    private Collection<LevelAcademic> levelAcademicCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private Collection<Finance> financeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private Collection<State> stateCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private Collection<Emotion> emotionCollection;
    @JoinColumn(name = "Family_idFamily", referencedColumnName = "idFamily")
    @ManyToOne(optional = false)
    private Family familyidFamily;
    @JoinColumn(name = "Persona_idPersona", referencedColumnName = "idPersona", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Persona persona;

    public Student() {
    }

    public Student(StudentPK studentPK) {
        this.studentPK = studentPK;
    }

    public Student(StudentPK studentPK, boolean enable, Date dateInit, String livesWith) {
        this.studentPK = studentPK;
        this.enable = enable;
        this.dateInit = dateInit;
        this.livesWith = livesWith;
    }

    public Student(int idStudent, int personaidPersona) {
        this.studentPK = new StudentPK(idStudent, personaidPersona);
    }

    public StudentPK getStudentPK() {
        return studentPK;
    }

    public void setStudentPK(StudentPK studentPK) {
        this.studentPK = studentPK;
    }

    public boolean getEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Date getDateInit() {
        return dateInit;
    }

    public void setDateInit(Date dateInit) {
        this.dateInit = dateInit;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTelStudent() {
        return telStudent;
    }

    public void setTelStudent(String telStudent) {
        this.telStudent = telStudent;
    }

    public String getLivesWith() {
        return livesWith;
    }

    public void setLivesWith(String livesWith) {
        this.livesWith = livesWith;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Boolean getIsUse() {
        return isUse;
    }

    public void setIsUse(Boolean isUse) {
        this.isUse = isUse;
    }

    public Boolean getIsHuerfano() {
        return isHuerfano;
    }

    public void setIsHuerfano(Boolean isHuerfano) {
        this.isHuerfano = isHuerfano;
    }

    @XmlTransient
    public Collection<LevelAcademic> getLevelAcademicCollection() {
        return levelAcademicCollection;
    }

    public void setLevelAcademicCollection(Collection<LevelAcademic> levelAcademicCollection) {
        this.levelAcademicCollection = levelAcademicCollection;
    }

    @XmlTransient
    public Collection<Finance> getFinanceCollection() {
        return financeCollection;
    }

    public void setFinanceCollection(Collection<Finance> financeCollection) {
        this.financeCollection = financeCollection;
    }

    @XmlTransient
    public Collection<State> getStateCollection() {
        return stateCollection;
    }

    public void setStateCollection(Collection<State> stateCollection) {
        this.stateCollection = stateCollection;
    }

    @XmlTransient
    public Collection<Emotion> getEmotionCollection() {
        return emotionCollection;
    }

    public void setEmotionCollection(Collection<Emotion> emotionCollection) {
        this.emotionCollection = emotionCollection;
    }

    public Family getFamilyidFamily() {
        return familyidFamily;
    }

    public void setFamilyidFamily(Family familyidFamily) {
        this.familyidFamily = familyidFamily;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentPK != null ? studentPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        if ((this.studentPK == null && other.studentPK != null) || (this.studentPK != null && !this.studentPK.equals(other.studentPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Student[ studentPK=" + studentPK + " ]";
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    @XmlTransient
    public Collection<BlackBox> getBlackBoxCollection() {
        return blackBoxCollection;
    }

    public void setBlackBoxCollection(Collection<BlackBox> blackBoxCollection) {
        this.blackBoxCollection = blackBoxCollection;
    }
    
}
