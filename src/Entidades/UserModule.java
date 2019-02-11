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
@Table(name = "UserModule")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserModule.findAll", query = "SELECT u FROM UserModule u")
    , @NamedQuery(name = "UserModule.findByIdUserModule", query = "SELECT u FROM UserModule u WHERE u.idUserModule = :idUserModule")
    , @NamedQuery(name = "UserModule.findBySetup", query = "SELECT u FROM UserModule u WHERE u.setup = :setup")
    , @NamedQuery(name = "UserModule.findByEnable", query = "SELECT u FROM UserModule u WHERE u.enable = :enable")
    , @NamedQuery(name = "UserModule.findByItem", query = "SELECT u FROM UserModule u WHERE u.item = :item")
    , @NamedQuery(name = "UserModule.findByIdUser", query = "Select u FROM UserModule u WHERE u.useridUser.iduser=:idUser")
    , @NamedQuery(name = "UserModule.findByidUserAndidModule", query = "SELECT u FROM UserModule u WHERE u.moduleidModule.idModule=:idModule AND u.useridUser.iduser=:iduser") 
    //, @NamedQuery(name = "UserModule.FindByidU")    
})
public class UserModule implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idUserModule")
    private Integer idUserModule;
    @Column(name = "setup")
    private String setup;
    @Column(name = "enable")
    private Boolean enable;
    @Column(name = "item")
    private String item;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userModuleidUserModule")
    private Collection<BlackBox> blackBoxCollection;
    @JoinColumn(name = "User_idUser", referencedColumnName = "iduser")
    @ManyToOne(optional = false)
    private User useridUser;
    @JoinColumn(name = "Module_idModule", referencedColumnName = "idModule")
    @ManyToOne(optional = false)
    private Module moduleidModule;

    public UserModule() {
    }

    public UserModule(Integer idUserModule) {
        this.idUserModule = idUserModule;
    }

    public Integer getIdUserModule() {
        return idUserModule;
    }

    public void setIdUserModule(Integer idUserModule) {
        this.idUserModule = idUserModule;
    }

    public String getSetup() {
        return setup;
    }

    public void setSetup(String setup) {
        this.setup = setup;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @XmlTransient
    public Collection<BlackBox> getBlackBoxCollection() {
        return blackBoxCollection;
    }

    public void setBlackBoxCollection(Collection<BlackBox> blackBoxCollection) {
        this.blackBoxCollection = blackBoxCollection;
    }

    public User getUseridUser() {
        return useridUser;
    }

    public void setUseridUser(User useridUser) {
        this.useridUser = useridUser;
    }

    public Module getModuleidModule() {
        return moduleidModule;
    }

    public void setModuleidModule(Module moduleidModule) {
        this.moduleidModule = moduleidModule;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUserModule != null ? idUserModule.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserModule)) {
            return false;
        }
        UserModule other = (UserModule) object;
        if ((this.idUserModule == null && other.idUserModule != null) || (this.idUserModule != null && !this.idUserModule.equals(other.idUserModule))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.UserModule[ idUserModule=" + idUserModule + " ]";
    }

}
