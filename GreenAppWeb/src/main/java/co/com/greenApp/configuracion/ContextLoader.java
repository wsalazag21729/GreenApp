package co.com.greenApp.configuracion;

import co.com.greenApp.controllers.CommentsJpaController;
import co.com.greenApp.controllers.DiscussionJpaController;
import co.com.greenApp.controllers.ModuleDescriptionJpaController;
import co.com.greenApp.controllers.ModuleJpaController;
import co.com.greenApp.controllers.UserJpaController;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author wsalazar
 */
public class ContextLoader implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_GreenAppEntities_jar_1.0-SNAPSHOTPU");

        UserJpaController userJpaController = new UserJpaController(emf);
        sce.getServletContext().setAttribute("userJpaController", userJpaController);

        ModuleJpaController moduleJpaController = new ModuleJpaController(emf);
        sce.getServletContext().setAttribute("moduleJpaController", moduleJpaController);

        ModuleDescriptionJpaController moduleDescriptionJpaController = new ModuleDescriptionJpaController(emf);
        sce.getServletContext().setAttribute("moduleDescriptionJpaController", moduleDescriptionJpaController);

        DiscussionJpaController discussionJpaController = new DiscussionJpaController(emf);
        sce.getServletContext().setAttribute("discussionJpaController", discussionJpaController);
        
        CommentsJpaController commentsJpaController = new CommentsJpaController(emf);
        sce.getServletContext().setAttribute("commentsJpaController", commentsJpaController);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
