package co.com.greenApp.servicesImpl;

import co.com.greenApp.services.ModuleService;
import co.com.greenApp.configuracion.BaseService;
import co.com.greenApp.controllers.ModuleDescriptionJpaController;
import co.com.greenApp.controllers.ModuleJpaController;
import co.com.greenApp.entities.Module;
import co.com.greenApp.entities.ModuleDescription;
import co.com.greenApp.module.dto.ModuleInfoDTO;
import co.com.greenApp.module.dto.ModuleInfoDescriptionDTO;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wsalazar@ias.com.co
 */
public class ModuleServiceImpl extends BaseService implements ModuleService {

    /**
     * {@inheritDoc }
     */
    @Override
    public ModuleInfoDTO getInfoModule(String name) throws RuntimeException {
        try {
            ModuleJpaController moduleJpaController = (ModuleJpaController) getContextAttribute("moduleJpaController");
            Module module = moduleJpaController.getModuleByName(name);
            if (module != null) {
                ModuleInfoDTO moduleInfoDTO = new ModuleInfoDTO();
                moduleInfoDTO.setIdModule(module.getIdModule());
                moduleInfoDTO.setNameModule(module.getName());
                moduleInfoDTO.setLinkInitialVideo(module.getLinkInitialVideo());
                
                return moduleInfoDTO;
            } else {
                return null;
            }
        } catch (Exception ex) {
            Logger.getLogger(ModuleServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /** {@inheritDoc } */
    @Override
    public ModuleInfoDescriptionDTO getInfoModuleDescription(String idModule) throws RuntimeException {
        try {
            ModuleDescriptionJpaController moduleDescriptionJpaController = (ModuleDescriptionJpaController) getContextAttribute("moduleDescriptionJpaController");
            ModuleDescription moduleDescription = moduleDescriptionJpaController.getModuleDescriptionByIdModule(Integer.parseInt(idModule));
            if (moduleDescription != null) {
                ModuleInfoDescriptionDTO moduleInfoDTO = new ModuleInfoDescriptionDTO();
                moduleInfoDTO.setIdModule(moduleDescription.getIdModule().getIdModule());
                moduleInfoDTO.setIdModuleDescription(moduleDescription.getIdModuleDescription());
                moduleInfoDTO.setLinkVideo(moduleDescription.getLinkVideo());
                moduleInfoDTO.setModuleDescription(moduleDescription.getModuleDescriptioncol());
                
                return moduleInfoDTO;
            } else {
                return null;
            }
        } catch (Exception ex) {
            Logger.getLogger(ModuleServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
