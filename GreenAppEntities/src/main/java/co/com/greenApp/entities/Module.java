package co.com.greenApp.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author wsalazar@ias.com.co
 */
@Entity
@Table(name = "module", catalog = "greenapp", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Module.findAll", query = "SELECT m FROM Module m")
    , @NamedQuery(name = "Module.findByIdModule", query = "SELECT m FROM Module m WHERE m.idModule = :idModule")
    , @NamedQuery(name = "Module.findByName", query = "SELECT m FROM Module m WHERE m.name = :name")
    , @NamedQuery(name = "Module.findByLinkInitialVideo", query = "SELECT m FROM Module m WHERE m.linkInitialVideo = :linkInitialVideo")})
public class Module implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idModule", fetch = FetchType.LAZY)
    private List<ImagesModule> imagesModuleList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idModule", nullable = false)
    private Integer idModule;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 45)
    private String name;
    @Basic(optional = false)
    @Column(name = "linkInitialVideo", nullable = false, length = 255)
    private String linkInitialVideo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idModule", fetch = FetchType.LAZY)
    private List<ModuleDescription> moduleDescriptionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idModule", fetch = FetchType.LAZY)
    private List<Discussion> discussionList;

    public Module() {
    }

    public Module(Integer idModule) {
        this.idModule = idModule;
    }

    public Module(Integer idModule, String name, String linkInitialVideo) {
        this.idModule = idModule;
        this.name = name;
        this.linkInitialVideo = linkInitialVideo;
    }

    public Integer getIdModule() {
        return idModule;
    }

    public void setIdModule(Integer idModule) {
        this.idModule = idModule;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinkInitialVideo() {
        return linkInitialVideo;
    }

    public void setLinkInitialVideo(String linkInitialVideo) {
        this.linkInitialVideo = linkInitialVideo;
    }

    @XmlTransient
    public List<ModuleDescription> getModuleDescriptionList() {
        return moduleDescriptionList;
    }

    public void setModuleDescriptionList(List<ModuleDescription> moduleDescriptionList) {
        this.moduleDescriptionList = moduleDescriptionList;
    }

    @XmlTransient
    public List<Discussion> getDiscussionList() {
        return discussionList;
    }

    public void setDiscussionList(List<Discussion> discussionList) {
        this.discussionList = discussionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idModule != null ? idModule.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Module)) {
            return false;
        }
        Module other = (Module) object;
        if ((this.idModule == null && other.idModule != null) || (this.idModule != null && !this.idModule.equals(other.idModule))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.greenApp.entities.Module[ idModule=" + idModule + " ]";
    }

    @XmlTransient
    public List<ImagesModule> getImagesModuleList() {
        return imagesModuleList;
    }

    public void setImagesModuleList(List<ImagesModule> imagesModuleList) {
        this.imagesModuleList = imagesModuleList;
    }

}
