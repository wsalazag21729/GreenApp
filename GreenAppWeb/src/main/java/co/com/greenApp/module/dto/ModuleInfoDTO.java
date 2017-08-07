package co.com.greenApp.module.dto;

import java.io.Serializable;

/**
 *
 * @author wsalazar@ias.com.co
 */
public class ModuleInfoDTO implements Serializable{
    
    private Integer idModule;
    private String nameModule;
    private String linkInitialVideo;

    public ModuleInfoDTO() {
    }

    public Integer getIdModule() {
        return idModule;
    }

    public void setIdModule(Integer idModule) {
        this.idModule = idModule;
    }

    public String getNameModule() {
        return nameModule;
    }

    public void setNameModule(String nameModule) {
        this.nameModule = nameModule;
    }

    public String getLinkInitialVideo() {
        return linkInitialVideo;
    }

    public void setLinkInitialVideo(String linkInitialVideo) {
        this.linkInitialVideo = linkInitialVideo;
    }
}
