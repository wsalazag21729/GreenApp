package co.com.greenApp.module.dto;

import java.io.Serializable;

/**
 *
 * @author wsalazar@ias.com.co
 */
public class ModuleInfoDescriptionDTO implements Serializable{
    
    private Integer idModuleDescription;
    private String linkVideo;
    private String moduleDescription;
    private Integer idModule;

    public ModuleInfoDescriptionDTO() {
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

    public String getModuleDescription() {
        return moduleDescription;
    }

    public void setModuleDescription(String moduleDescription) {
        this.moduleDescription = moduleDescription;
    }

    public Integer getIdModule() {
        return idModule;
    }

    public void setIdModule(Integer idModule) {
        this.idModule = idModule;
    }
}
