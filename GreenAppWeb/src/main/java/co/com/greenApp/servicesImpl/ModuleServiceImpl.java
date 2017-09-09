package co.com.greenApp.servicesImpl;

import co.com.greenApp.services.ModuleService;
import co.com.greenApp.configuracion.BaseService;
import co.com.greenApp.controllers.ModuleDescriptionJpaController;
import co.com.greenApp.controllers.ModuleJpaController;
import co.com.greenApp.entities.ImagesModule;
import co.com.greenApp.entities.Module;
import co.com.greenApp.entities.ModuleDescription;
import co.com.greenApp.module.dto.ImagesModuleDTO;
import co.com.greenApp.module.dto.ModuleInfoDTO;
import co.com.greenApp.module.dto.ModuleInfoDescriptionDTO;
import java.util.ArrayList;
import java.util.List;
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

    /**
     * {@inheritDoc }
     */
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

    /**
     * {@inheritDoc }
     */
    @Override
    public List<ModuleInfoDTO> getModules() throws RuntimeException {
        try {
            ModuleJpaController moduleJpaController = (ModuleJpaController) getContextAttribute("moduleJpaController");
            List<Module> listModule = moduleJpaController.findModuleEntities();
            if (listModule != null) {
                List<ModuleInfoDTO> listModuleDTO = new ArrayList<>();
                for (Module module : listModule) {
                    ModuleInfoDTO moduleInfoDTO = new ModuleInfoDTO();
                    moduleInfoDTO.setIdModule(module.getIdModule());
                    moduleInfoDTO.setNameModule(module.getName());
                    moduleInfoDTO.setLinkInitialVideo(module.getLinkInitialVideo());
                    
                    listModuleDTO.add(moduleInfoDTO);
                }

                return listModuleDTO;
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
    public List<ImagesModuleDTO> getImagesByModule(String idModule) throws RuntimeException {
        try {
            ModuleJpaController moduleJpaController = (ModuleJpaController) getContextAttribute("moduleJpaController");
            Module module = moduleJpaController.findModule(Integer.parseInt(idModule));
            if (module != null && module.getImagesModuleList() != null && !module.getImagesModuleList().isEmpty()) {
                List<ImagesModuleDTO> listImages = new ArrayList<>();
                for (ImagesModule imageModule : module.getImagesModuleList()) {
                    ImagesModuleDTO imagesModuleDTO = new ImagesModuleDTO();
                    imagesModuleDTO.setIdImagesModule(imageModule.getIdImagesModule());
                    imagesModuleDTO.setIdModule(Integer.parseInt(idModule));
                    imagesModuleDTO.setName(imageModule.getName());
                    imagesModuleDTO.setMessage(imageModule.getMessage());
                    
                    listImages.add(imagesModuleDTO);
                }
                return listImages;
            } else {
                return null;
            }
        } catch (Exception ex) {
            Logger.getLogger(ModuleServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
