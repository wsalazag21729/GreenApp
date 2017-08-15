package co.com.greenApp.servicesImpl;

import co.com.greenApp.configuracion.BaseService;
import co.com.greenApp.configuracion.Constants;
import co.com.greenApp.controllers.CommentsJpaController;
import co.com.greenApp.controllers.DiscussionJpaController;
import co.com.greenApp.controllers.ModuleJpaController;
import co.com.greenApp.entities.Comments;
import co.com.greenApp.entities.Discussion;
import co.com.greenApp.entities.Module;
import co.com.greenApp.foro.dto.InfoCommentsDTO;
import co.com.greenApp.foro.dto.InfoDiscussionsDTO;
import co.com.greenApp.services.ForoService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wsalazar@ias.com.co
 */
public class ForoServiceImpl extends BaseService implements ForoService {

    /**
     * {@inheritDoc }
     */
    @Override
    public List<InfoDiscussionsDTO> getInfoDiscussions(String idModule) throws RuntimeException {
        try {
            ModuleJpaController moduleJpaController = (ModuleJpaController) getContextAttribute("moduleJpaController");
            Module module = moduleJpaController.findModule(Integer.parseInt(idModule));
            List<Discussion> listDiscussions = module.getDiscussionList();
            if (listDiscussions != null && !listDiscussions.isEmpty()) {
                List<InfoDiscussionsDTO> listDiscussionsDTO = new ArrayList<>();
                for (Discussion discussion : listDiscussions) {
                    InfoDiscussionsDTO discussionDTO = new InfoDiscussionsDTO();
                    discussionDTO.setIdDiscussion(discussion.getIdDiscussion());
                    discussionDTO.setTitle(discussion.getTitle());
                    discussionDTO.setDescription(discussion.getDescription());
                    discussionDTO.setCreateTimestamp(discussion.getCreateTimestamp());
                    discussionDTO.setIdModule(Integer.parseInt(idModule));
                    discussionDTO.setNameModule(discussion.getIdModule().getName());
                    discussionDTO.setNameUser(discussion.getNameUser());

                    listDiscussionsDTO.add(discussionDTO);
                }
                return listDiscussionsDTO;
            } else {
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(ForoServiceImpl.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<InfoCommentsDTO> getInfoComments(String idDiscussion) throws RuntimeException {
        try {
            DiscussionJpaController discussionJpaController = (DiscussionJpaController) getContextAttribute("discussionJpaController");
            Discussion discussion = discussionJpaController.findDiscussion(Integer.parseInt(idDiscussion));
            List<Comments> listComments = discussion.getCommentsList();
            if (listComments != null && !listComments.isEmpty()) {
                List<InfoCommentsDTO> listCommentsDTO = new ArrayList<>();
                for (Comments comment : listComments) {
                    InfoCommentsDTO commentDTO = new InfoCommentsDTO();
                    commentDTO.setIdComment(comment.getIdComment());
                    commentDTO.setComment(comment.getComment());
                    commentDTO.setCreateTimestamp(comment.getCreateTimestamp());
                    commentDTO.setIdDiscussion(comment.getIdDiscussion().getIdDiscussion());
                    commentDTO.setNameUser(comment.getUserName());

                    listCommentsDTO.add(commentDTO);
                }
                return listCommentsDTO;
            } else {
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(ForoServiceImpl.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String saveDiscussion(InfoDiscussionsDTO discussionsDTO) throws RuntimeException {
        try {
            DiscussionJpaController discussionJpaController = (DiscussionJpaController) getContextAttribute("discussionJpaController");
            ModuleJpaController moduleJpaController = (ModuleJpaController) getContextAttribute("moduleJpaController");
            Discussion discussion = new Discussion();
            discussion.setIdModule(moduleJpaController.findModule(discussionsDTO.getIdModule()));
            discussion.setCreateTimestamp(new Date());
            discussion.setTitle(discussionsDTO.getTitle());
            discussion.setDescription(discussionsDTO.getDescription());
            discussion.setNameUser(discussionsDTO.getNameUser());

            discussionJpaController.create(discussion);
            return Constants.SUCCESS;
        } catch (Exception e) {
            Logger.getLogger(ForoServiceImpl.class.getName()).log(Level.SEVERE, null, e);
            return Constants.FAILURE;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String saveComment(InfoCommentsDTO infoCommentDTO) throws RuntimeException {
        try {
            CommentsJpaController commentsJpaController = (CommentsJpaController) getContextAttribute("commentsJpaController");
            DiscussionJpaController discussionJpaController = (DiscussionJpaController) getContextAttribute("discussionJpaController");
            
            Comments comment = new Comments();
            comment.setIdDiscussion(discussionJpaController.findDiscussion(infoCommentDTO.getIdDiscussion()));
            comment.setCreateTimestamp(new Date());
            comment.setUserName(infoCommentDTO.getNameUser());
            comment.setComment(infoCommentDTO.getComment());
            
            commentsJpaController.create(comment);
            return Constants.SUCCESS;
        } catch (Exception e) {
            Logger.getLogger(ForoServiceImpl.class.getName()).log(Level.SEVERE, null, e);
            return Constants.FAILURE;
        }
    }

}
