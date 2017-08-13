package co.com.greenApp.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author wsalazar@ias.com.co
 */
@Entity
@Table(name = "discussion", catalog = "greenapp", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Discussion.findAll", query = "SELECT d FROM Discussion d")
    , @NamedQuery(name = "Discussion.findByIdDiscussion", query = "SELECT d FROM Discussion d WHERE d.idDiscussion = :idDiscussion")
    , @NamedQuery(name = "Discussion.findByTitle", query = "SELECT d FROM Discussion d WHERE d.title = :title")
    , @NamedQuery(name = "Discussion.findByNameUser", query = "SELECT d FROM Discussion d WHERE d.nameUser = :nameUser")
    , @NamedQuery(name = "Discussion.findByCreateTimestamp", query = "SELECT d FROM Discussion d WHERE d.createTimestamp = :createTimestamp")})
public class Discussion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDiscussion", nullable = false)
    private Integer idDiscussion;
    @Basic(optional = false)
    @Column(name = "title", nullable = false, length = 100)
    private String title;
    @Basic(optional = false)
    @Lob
    @Column(name = "description", nullable = false, length = 65535)
    private String description;
    @Basic(optional = false)
    @Column(name = "nameUser", nullable = false, length = 100)
    private String nameUser;
    @Basic(optional = false)
    @Column(name = "createTimestamp", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTimestamp;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDiscussion", fetch = FetchType.LAZY)
    private List<Comments> commentsList;
    @JoinColumn(name = "idModule", referencedColumnName = "idModule", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Module idModule;

    public Discussion() {
    }

    public Discussion(Integer idDiscussion) {
        this.idDiscussion = idDiscussion;
    }

    public Discussion(Integer idDiscussion, String title, String description, String nameUser, Date createTimestamp) {
        this.idDiscussion = idDiscussion;
        this.title = title;
        this.description = description;
        this.nameUser = nameUser;
        this.createTimestamp = createTimestamp;
    }

    public Integer getIdDiscussion() {
        return idDiscussion;
    }

    public void setIdDiscussion(Integer idDiscussion) {
        this.idDiscussion = idDiscussion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public Date getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Date createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    @XmlTransient
    public List<Comments> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comments> commentsList) {
        this.commentsList = commentsList;
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
        hash += (idDiscussion != null ? idDiscussion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Discussion)) {
            return false;
        }
        Discussion other = (Discussion) object;
        if ((this.idDiscussion == null && other.idDiscussion != null) || (this.idDiscussion != null && !this.idDiscussion.equals(other.idDiscussion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.greenApp.entities.Discussion[ idDiscussion=" + idDiscussion + " ]";
    }

}
