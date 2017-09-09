package co.com.greenApp.services;

import co.com.greenApp.module.dto.ImagesModuleDTO;
import co.com.greenApp.module.dto.ModuleInfoDTO;
import co.com.greenApp.module.dto.ModuleInfoDescriptionDTO;
import java.util.List;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author wsalazar@ias.com.co
 */

@Path("/moduleService")
public interface ModuleService {
    
    /**
     * Método que consulta la información de un módulo
     * @param name
     * @return ModuleInfoDTO
     * @throws RuntimeException 
     */
    @POST
    @Produces("application/json")
    @Path("getInfoModule")
    ModuleInfoDTO getInfoModule(String name) throws RuntimeException;
    
    /**
     * Método que consulta el video y la descripción de un módulo
     * @param idModule
     * @return ModuleInfoDescriptionDTO
     * @throws RuntimeException 
     */
    @POST
    @Produces("application/json")
    @Path("getInfoModuleDescription")
    ModuleInfoDescriptionDTO getInfoModuleDescription(String idModule) throws RuntimeException;
    
    /**
     * Método que cosulta la información de los módulos registrados en la aplicación
     * @return List<ModuleInfoDTO>
     * @throws RuntimeException 
     */
    @POST
    @Produces("application/json")
    @Path("getModules")
    List<ModuleInfoDTO> getModules() throws RuntimeException;
    
    /**
     * Servicio que consulta la información de las imagenes registradas para un módulo
     * @param idModule
     * @return
     * @throws RuntimeException 
     */
    @POST
    @Produces("application/json")
    @Path("getImagesByModule")
    List<ImagesModuleDTO> getImagesByModule(String idModule) throws RuntimeException;

}
