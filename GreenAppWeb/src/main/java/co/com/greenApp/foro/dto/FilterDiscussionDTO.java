package co.com.greenApp.foro.dto;

import java.io.Serializable;

/**
 *
 * @author wsalazar@ias.com.co
 */
public class FilterDiscussionDTO implements Serializable {

    private String idModule;
    private Long dateTime;
    private String searchFilter;

    public FilterDiscussionDTO() {
    }

    public String getIdModule() {
        return idModule;
    }

    public void setIdModule(String idModule) {
        this.idModule = idModule;
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
