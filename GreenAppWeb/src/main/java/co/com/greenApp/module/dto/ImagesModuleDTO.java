package co.com.greenApp.module.dto;

import java.io.Serializable;

/**
 *
 * @author wsalazar@ias.com.co
 */
public class ImagesModuleDTO implements Serializable{
    
    private Integer idImagesModule;
    private Integer idModule;
    private String name;
    private String message;

    public ImagesModuleDTO() {
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

    public Integer getIdModule() {
        return idModule;
    }

    public void setIdModule(Integer idModule) {
        this.idModule = idModule;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
