/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Julio Fernando Ixcoy
 */
@Entity
@Table(name = "Emotion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Emotion.findAll", query = "SELECT e FROM Emotion e")
    , @NamedQuery(name = "Emotion.findByIdEmotion", query = "SELECT e FROM Emotion e WHERE e.emotionPK.idEmotion = :idEmotion")
    , @NamedQuery(name = "Emotion.findByDescripcion", query = "SELECT e FROM Emotion e WHERE e.descripcion = :descripcion")
    , @NamedQuery(name = "Emotion.findByType", query = "SELECT e FROM Emotion e WHERE e.type = :type")
    , @NamedQuery(name = "Emotion.findByGrave", query = "SELECT e FROM Emotion e WHERE e.grave = :grave")
    , @NamedQuery(name = "Emotion.findByStudentidStudent", query = "SELECT e FROM Emotion e WHERE e.emotionPK.studentidStudent = :studentidStudent")
    , @NamedQuery(name = "Emotion.findByStudentPersonaidPersona", query = "SELECT e FROM Emotion e WHERE e.emotionPK.studentPersonaidPersona = :studentPersonaidPersona")
    , @NamedQuery(name = "Emotion.findByStudentFamilyidFamily", query = "SELECT e FROM Emotion e WHERE e.emotionPK.studentFamilyidFamily = :studentFamilyidFamily")
    , @NamedQuery(name = "Emotion.findByStudentFamilyParentsidParents", query = "SELECT e FROM Emotion e WHERE e.emotionPK.studentFamilyParentsidParents = :studentFamilyParentsidParents")
    , @NamedQuery(name = "Emotion.findByStudentFamilyParentsPersonaidPersona", query = "SELECT e FROM Emotion e WHERE e.emotionPK.studentFamilyParentsPersonaidPersona = :studentFamilyParentsPersonaidPersona")})
public class Emotion implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "emotion")
    private Collection<BlackBox> blackBoxCollection;

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EmotionPK emotionPK;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "type")
    private String type;
    @Column(name = "grave")
    private Boolean grave;
    @JoinColumns({
        @JoinColumn(name = "Student_idStudent", referencedColumnName = "idStudent", insertable = false, updatable = false)
        , @JoinColumn(name = "Student_Persona_idPersona", referencedColumnName = "Persona_idPersona", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Student student;

    public Emotion() {
    }

    public Emotion(EmotionPK emotionPK) {
        this.emotionPK = emotionPK;
    }

    public Emotion(int idEmotion, int studentidStudent, int studentPersonaidPersona, int studentFamilyidFamily, int studentFamilyParentsidParents, int studentFamilyParentsPersonaidPersona) {
        this.emotionPK = new EmotionPK(idEmotion, studentidStudent, studentPersonaidPersona, studentFamilyidFamily, studentFamilyParentsidParents, studentFamilyParentsPersonaidPersona);
    }

    public EmotionPK getEmotionPK() {
        return emotionPK;
    }

    public void setEmotionPK(EmotionPK emotionPK) {
        this.emotionPK = emotionPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getGrave() {
        return grave;
    }

    public void setGrave(Boolean grave) {
        this.grave = grave;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (emotionPK != null ? emotionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Emotion)) {
            return false;
        }
        Emotion other = (Emotion) object;
        if ((this.emotionPK == null && other.emotionPK != null) || (this.emotionPK != null && !this.emotionPK.equals(other.emotionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Emotion[ emotionPK=" + emotionPK + " ]";
    }

    @XmlTransient
    public Collection<BlackBox> getBlackBoxCollection() {
        return blackBoxCollection;
    }

    public void setBlackBoxCollection(Collection<BlackBox> blackBoxCollection) {
        this.blackBoxCollection = blackBoxCollection;
    }
    
}
