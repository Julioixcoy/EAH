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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "Persona")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Persona.findAll", query = "SELECT p FROM Persona p")
    , @NamedQuery(name = "Persona.findByIdPersona", query = "SELECT p FROM Persona p WHERE p.idPersona = :idPersona")
    , @NamedQuery(name = "Persona.findByCodigoPerson", query = "SELECT p FROM Persona p WHERE p.codigoPerson = :codigoPerson")
    , @NamedQuery(name = "Persona.findByNombre", query = "SELECT p FROM Persona p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Persona.findByApellido", query = "SELECT p FROM Persona p WHERE p.apellido = :apellido")
    , @NamedQuery(name = "Persona.findByAge", query = "SELECT p FROM Persona p WHERE p.age = :age")
    , @NamedQuery(name = "Persona.findByWasBorn", query = "SELECT p FROM Persona p WHERE p.wasBorn = :wasBorn")
    , @NamedQuery(name = "Persona.findByDireccion", query = "SELECT p FROM Persona p WHERE p.direccion = :direccion")
    , @NamedQuery(name = "Persona.findByEnable", query = "SELECT p FROM Persona p WHERE p.enable = :enable")
    , @NamedQuery(name = "Persona.findByTel", query = "SELECT p FROM Persona p WHERE p.tel = :tel")
    , @NamedQuery(name = "Persona.findByIsParent", query = "SELECT p FROM Persona p WHERE p.isParent = :isParent")
    , @NamedQuery(name = "Persona.findByIsStudent", query = "SELECT p FROM Persona p WHERE p.isStudent = :isStudent")
    , @NamedQuery (name = "Persona.findByStudentAndEnable", query = "SELECT p FROM Persona p WHERE p.isStudent= :isStudent AND p.enable= :enable")
    , @NamedQuery(name = "Persona.findByEtnia", query = "SELECT p FROM Persona p WHERE p.etnia = :etnia")
    
//, @NamedQuery(name = "TblInvoice.findByNumberPurchaseUsingLike", query = "SELECT t FROM TblInvoice t WHERE t.numberPurchase=:noSale")
})
public class Persona implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personaidPersona")
    private Collection<User> userCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personaidPersona")
    private Collection<BlackBox> blackBoxCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPersona")
    private Integer idPersona;
    @Column(name = "codigoPerson")
    private String codigoPerson;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "age")
    private Integer age;
    @Column(name = "wasBorn")
    @Temporal(TemporalType.DATE)
    private Date wasBorn;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "enable")
    private Boolean enable;
    @Basic(optional = false)
    @Column(name = "tel")
    private String tel;
    @Column(name = "isParent")
    private Boolean isParent;
    @Column(name = "isStudent")
    private Boolean isStudent;
    @Column(name = "etnia")
    private String etnia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "persona")
    private Collection<Parents> parentsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "persona")
    private Collection<Student> studentCollection;

    public Persona() {
    }

    public Persona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public Persona(Integer idPersona, String tel) {
        this.idPersona = idPersona;
        this.tel = tel;
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public String getCodigoPerson() {
        return codigoPerson;
    }

    public void setCodigoPerson(String codigoPerson) {
        this.codigoPerson = codigoPerson;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getWasBorn() {
        return wasBorn;
    }

    public void setWasBorn(Date wasBorn) {
        this.wasBorn = wasBorn;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }

    public Boolean getIsStudent() {
        return isStudent;
    }

    public void setIsStudent(Boolean isStudent) {
        this.isStudent = isStudent;
    }

    public String getEtnia() {
        return etnia;
    }

    public void setEtnia(String etnia) {
        this.etnia = etnia;
    }

    @XmlTransient
    public Collection<Parents> getParentsCollection() {
        return parentsCollection;
    }

    public void setParentsCollection(Collection<Parents> parentsCollection) {
        this.parentsCollection = parentsCollection;
    }

    @XmlTransient
    public Collection<Student> getStudentCollection() {
        return studentCollection;
    }

    public void setStudentCollection(Collection<Student> studentCollection) {
        this.studentCollection = studentCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPersona != null ? idPersona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Persona)) {
            return false;
        }
        Persona other = (Persona) object;
        if ((this.idPersona == null && other.idPersona != null) || (this.idPersona != null && !this.idPersona.equals(other.idPersona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Persona[ idPersona=" + idPersona + " ]";
    }

    @XmlTransient
    public Collection<BlackBox> getBlackBoxCollection() {
        return blackBoxCollection;
    }

    public void setBlackBoxCollection(Collection<BlackBox> blackBoxCollection) {
        this.blackBoxCollection = blackBoxCollection;
    }

    @XmlTransient
    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }
    
}
