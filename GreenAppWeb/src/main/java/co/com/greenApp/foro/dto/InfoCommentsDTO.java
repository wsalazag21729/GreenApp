package co.com.greenApp.foro.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author wsalazar@ias.com.co
 */
public class InfoCommentsDTO implements Serializable{
    
    private Integer idComment;
    private String comment;
    private Date createTimestamp;
    private Integer idDiscussion;
    private String userName;

    public InfoCommentsDTO() {
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

    public Integer getIdDiscussion() {
        return idDiscussion;
    }

    public void setIdDiscussion(Integer idDiscussion) {
        this.idDiscussion = idDiscussion;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
