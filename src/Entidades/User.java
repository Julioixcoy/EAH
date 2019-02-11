/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author julio
 *
 */
@Entity
@Table(name = "User")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
    , @NamedQuery(name = "User.findByIduser", query = "SELECT u FROM User u WHERE u.iduser = :iduser")
    , @NamedQuery(name = "User.findByName", query = "SELECT u FROM User u WHERE u.name = :name")
    , @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password")
    , @NamedQuery(name = "User.findByEnable", query = "SELECT u FROM User u WHERE u.enable = :enable")//tblUsersTblPersonDPI.tblPersonDPI
    //, @NamedQuery (name = "User.findConfigurationUser", query = "SELECT t1 FROM User t1 INNER JOIN UserModule t2 ON t1 = t2.User_idUser WHERE  t1.iduser=:idUser")
    , @NamedQuery(name = "Users.findByidPersona", query = "SELECT u FROM User u WHERE u.personaidPersona.idPersona = :idPersona")    
    //, @NamedQuery(name = "User.findLasUser", query = "SELECT u FROM ")    
})
public class User implements Serializable {

    @JoinColumn(name = "Persona_idPersona", referencedColumnName = "idPersona")
    @ManyToOne(optional = false)
    private Persona personaidPersona;

    @Column(name = "enable")
    private Boolean enable;
    @JoinColumn(name = "TypeUser_idTypeUser", referencedColumnName = "idTypeUser")
    @ManyToOne(optional = false)
    private TypeUser typeUseridTypeUser;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "iduser")
    private Integer iduser;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "useriduser")
    private Collection<BlackBox> blackBoxCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "useridUser")
    private Collection<UserModule> userModuleCollection;

    public User() {
    }

    public User(Integer iduser) {
        this.iduser = iduser;
    }

    public Integer getIduser() {
        return iduser;
    }

    public void setIduser(Integer iduser) {
        this.iduser = iduser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @XmlTransient
    public Collection<BlackBox> getBlackBoxCollection() {
        return blackBoxCollection;
    }

    public void setBlackBoxCollection(Collection<BlackBox> blackBoxCollection) {
        this.blackBoxCollection = blackBoxCollection;
    }

    @XmlTransient
    public Collection<UserModule> getUserModuleCollection() {
        return userModuleCollection;
    }

    public void setUserModuleCollection(Collection<UserModule> userModuleCollection) {
        this.userModuleCollection = userModuleCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iduser != null ? iduser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.iduser == null && other.iduser != null) || (this.iduser != null && !this.iduser.equals(other.iduser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.User[ iduser=" + iduser + " ]";
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public TypeUser getTypeUseridTypeUser() {
        return typeUseridTypeUser;
    }

    public void setTypeUseridTypeUser(TypeUser typeUseridTypeUser) {
        this.typeUseridTypeUser = typeUseridTypeUser;
    }

    public Persona getPersonaidPersona() {
        return personaidPersona;
    }

    public void setPersonaidPersona(Persona personaidPersona) {
        this.personaidPersona = personaidPersona;
    }

}
