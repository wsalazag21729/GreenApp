
package co.com.greenApp.services;

import co.com.greenApp.foro.dto.InfoCommentsDTO;
import co.com.greenApp.foro.dto.InfoDiscussionsDTO;
import java.util.List;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author wsalazar@ias.com.co
 */
@Path("/foroService")
public interface ForoService {
    
    /**
     * Método que obtiene la información de las discusiones de un modulo
     * @param idModule
     * @return List<InfoDiscussions>
     * @throws RuntimeException 
     */
    @POST
    @Produces("application/json")
    @Path("getInfoDiscussions")
    List<InfoDiscussionsDTO> getInfoDiscussions(String idModule) throws RuntimeException;
    
    /**
     * Método que consulta la lista de comentarios de una discusión
     * @param idDiscussion
     * @return List<InfoComments>
     * @throws RuntimeException 
     */
    @POST
    @Produces("application/json")
    @Path("getInfoComments")
    List<InfoCommentsDTO> getInfoComments(String idDiscussion) throws RuntimeException;
    
    /**
     * Método que guarda la información de una discusión
     * @param discussionsDTO
     * @return String
     * @throws RuntimeException 
     */
    @POST
    @Produces("application/json")
    @Path("saveDiscussion")
    String saveDiscussion(InfoDiscussionsDTO discussionsDTO) throws RuntimeException;

}
