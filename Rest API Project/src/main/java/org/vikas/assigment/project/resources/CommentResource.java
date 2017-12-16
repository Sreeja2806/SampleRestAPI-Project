package org.vikas.assigment.project.resources;

    import org.vikas.assigment.project.model.Comment;
    import org.vikas.assigment.project.service.CommentService;

    import javax.ws.rs.*;
    import javax.ws.rs.core.MediaType;
    import java.util.List;

@Path("/profiles/{userId}/messages/{messageId}/comments")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)

/**
 * Created by Vikas on 9/12/16.
 */
public class CommentResource {

    private CommentService commentService = new CommentService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> getAllComments(@PathParam("userId") long userId, @PathParam("messageId") long messageId) {
        return commentService.getAllComments(userId, messageId);
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Comment addComment(@PathParam("userId") long userId,@PathParam("messageId") long messageId, Comment comment) {
        return commentService.addComment(userId, messageId, comment);
    }

    @PUT
    @Path("/{commentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Comment updateComment(@PathParam("userId") long userId, @PathParam("messageId") long messageId, @PathParam("commentId") long id, Comment comment) {
        comment.setCommentId(id);
        return commentService.updateComment(userId, messageId, comment);
    }

    @DELETE
    @Path("/{commentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteComment(@PathParam("userId") long userId,@PathParam("messageId") long messageId, @PathParam("commentId") long commentId) {
        commentService.removeComment(userId, messageId, commentId);
    }

    @GET
    @Path("/{commentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Comment getMessage(@PathParam("userId") long userId, @PathParam("messageId") long messageId, @PathParam("commentId") long commentId) {
        return commentService.getComment(userId, messageId, commentId);
    }

}
