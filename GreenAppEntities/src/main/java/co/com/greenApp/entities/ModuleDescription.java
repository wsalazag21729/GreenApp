/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.com.greenApp.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author wsalazar@ias.com.co
 */
@Entity
@Table(name = "module_description", catalog = "greenapp", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ModuleDescription.findAll", query = "SELECT m FROM ModuleDescription m")
    , @NamedQuery(name = "ModuleDescription.findByIdModuleDescription", query = "SELECT m FROM ModuleDescription m WHERE m.idModuleDescription = :idModuleDescription")
    , @NamedQuery(name = "ModuleDescription.findByLinkVideo", query = "SELECT m FROM ModuleDescription m WHERE m.linkVideo = :linkVideo")})
public class ModuleDescription implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idModuleDescription", nullable = false)
    private Integer idModuleDescription;
    @Basic(optional = false)
    @Column(name = "linkVideo", nullable = false, length = 255)
    private String linkVideo;
    @Basic(optional = false)
    @Lob
    @Column(name = "module_descriptioncol", nullable = false, length = 65535)
    private String moduleDescriptioncol;
    @JoinColumn(name = "idModule", referencedColumnName = "idModule", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Module idModule;

    public ModuleDescription() {
    }

    public ModuleDescription(Integer idModuleDescription) {
        this.idModuleDescription = idModuleDescription;
    }

    public ModuleDescription(Integer idModuleDescription, String linkVideo, String moduleDescriptioncol) {
        this.idModuleDescription = idModuleDescription;
        this.linkVideo = linkVideo;
        this.moduleDescriptioncol = moduleDescriptioncol;
    }

    public Integer getIdModuleDescription() {
        return idModuleDescription;
    }

    public void setIdModuleDescription(Integer idModuleDescription) {
        this.idModuleDescription = idModuleDescription;
    }

    public String getLinkVideo() {
        return linkVideo;
    }

    public void setLinkVideo(String linkVideo) {
        this.linkVideo = linkVideo;
    }

    public String getModuleDescriptioncol() {
        return moduleDescriptioncol;
    }

    public void setModuleDescriptioncol(String moduleDescriptioncol) {
        this.moduleDescriptioncol = moduleDescriptioncol;
    }

    public Module getIdModule() {
        return idModule;
    }

    public void setIdModule(Module idModule) {
        this.idModule = idModule;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idModuleDescription != null ? idModuleDescription.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ModuleDescription)) {
            return false;
        }
        ModuleDescription other = (ModuleDescription) object;
        if ((this.idModuleDescription == null && other.idModuleDescription != null) || (this.idModuleDescription != null && !this.idModuleDescription.equals(other.idModuleDescription))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.greenApp.entities.ModuleDescription[ idModuleDescription=" + idModuleDescription + " ]";
    }

}
