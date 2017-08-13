package co.com.greenApp.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author wsalazar@ias.com.co
 */
@Entity
@Table(name = "comments", catalog = "greenapp", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comments.findAll", query = "SELECT c FROM Comments c")
    , @NamedQuery(name = "Comments.findByIdComment", query = "SELECT c FROM Comments c WHERE c.idComment = :idComment")
    , @NamedQuery(name = "Comments.findByCreateTimestamp", query = "SELECT c FROM Comments c WHERE c.createTimestamp = :createTimestamp")})
public class Comments implements Serializable {

    @Basic(optional = false)
    @Column(name = "userName", nullable = false, length = 100)
    private String userName;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idComment", nullable = false)
    private Integer idComment;
    @Basic(optional = false)
    @Lob
    @Column(name = "comment", nullable = false, length = 65535)
    private String comment;
    @Basic(optional = false)
    @Column(name = "createTimestamp", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTimestamp;
    @JoinColumn(name = "idDiscussion", referencedColumnName = "idDiscussion", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Discussion idDiscussion;

    public Comments() {
    }

    public Comments(Integer idComment) {
        this.idComment = idComment;
    }

    public Comments(Integer idComment, String comment, Date createTimestamp) {
        this.idComment = idComment;
        this.comment = comment;
        this.createTimestamp = createTimestamp;
    }

    public Integer getIdComment() {
        return idComment;
    }

    public void setIdComment(Integer idComment) {
        this.idComment = idComment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Date createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public Discussion getIdDiscussion() {
        return idDiscussion;
    }

    public void setIdDiscussion(Discussion idDiscussion) {
        this.idDiscussion = idDiscussion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComment != null ? idComment.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comments)) {
            return false;
        }
        Comments other = (Comments) object;
        if ((this.idComment == null && other.idComment != null) || (this.idComment != null && !this.idComment.equals(other.idComment))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.greenApp.entities.Comments[ idComment=" + idComment + " ]";
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
