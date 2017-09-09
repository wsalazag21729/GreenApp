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
@Table(name = "images_module", catalog = "greenapp", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ImagesModule.findAll", query = "SELECT i FROM ImagesModule i")
    , @NamedQuery(name = "ImagesModule.findByIdImagesModule", query = "SELECT i FROM ImagesModule i WHERE i.idImagesModule = :idImagesModule")
    , @NamedQuery(name = "ImagesModule.findByName", query = "SELECT i FROM ImagesModule i WHERE i.name = :name")})
public class ImagesModule implements Serializable {

    @Basic(optional = false)
    @Lob
    @Column(name = "message", nullable = false, length = 65535)
    private String message;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idImagesModule", nullable = false)
    private Integer idImagesModule;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 45)
    private String name;
    @JoinColumn(name = "idModule", referencedColumnName = "idModule", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Module idModule;

    public ImagesModule() {
    }

    public ImagesModule(Integer idImagesModule) {
        this.idImagesModule = idImagesModule;
    }

    public ImagesModule(Integer idImagesModule, String name) {
        this.idImagesModule = idImagesModule;
        this.name = name;
    }

    public Integer getIdImagesModule() {
        return idImagesModule;
    }

    public void setIdImagesModule(Integer idImagesModule) {
        this.idImagesModule = idImagesModule;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        hash += (idImagesModule != null ? idImagesModule.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ImagesModule)) {
            return false;
        }
        ImagesModule other = (ImagesModule) object;
        if ((this.idImagesModule == null && other.idImagesModule != null) || (this.idImagesModule != null && !this.idImagesModule.equals(other.idImagesModule))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.greenApp.entities.ImagesModule[ idImagesModule=" + idImagesModule + " ]";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
