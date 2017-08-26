package co.com.greenApp.foro.dto;

import java.io.Serializable;

/**
 *
 * @author wsalazar@ias.com.co
 */
public class FilterCommentsDTO implements Serializable{
    
    private String idDiscussion;
    private Long dateTime;
    private String searchFilter;

    public FilterCommentsDTO() {
    }

    public String getIdDiscussion() {
        return idDiscussion;
    }

    public void setIdDiscussion(String idDiscussion) {
        this.idDiscussion = idDiscussion;
    }

    public Long getDateTime() {
        return dateTime;
    }

    public void setDateTime(Long dateTime) {
        this.dateTime = dateTime;
    }

    public String getSearchFilter() {
        return searchFilter;
    }

    public void setSearchFilter(String searchFilter) {
        this.searchFilter = searchFilter;
    }
    
    

}
