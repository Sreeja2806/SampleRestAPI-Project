package org.vikas.assigment.project.resources;


    import org.vikas.assigment.project.model.Message;
    import org.vikas.assigment.project.service.MessageService;

    import javax.ws.rs.*;
    import javax.ws.rs.core.MediaType;
    import java.util.List;


/**
 * Created by Vikas on 9/12/16.
 */

@Path("/profiles/{userId}/messages/")
//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {

    private MessageService messageService = new MessageService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Message> getAllMessages(@PathParam("userId") long userId) {
        return messageService.getAllMessages(userId);
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Message addMessage(@PathParam("userId") long userId, Message message) {
        return messageService.addMessage(userId, message);
    }

    @PUT
    @Path("/{messageId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Message updateMessage(@PathParam("userId") long userId, @PathParam("messageId") long messageId, Message message) {
        message.setMessageId(messageId);
        return messageService.updateMessage(userId, message);
    }

    @DELETE
    @Path("/{messageId}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteMessage(@PathParam("userId") long userId, @PathParam("messageId") long messageId) {
        messageService.removeMessage(userId, messageId);
    }

    @GET
    @Path("/{messageId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Message getMessage(@PathParam("userId") long userId, @PathParam("messageId") long messageId) {
        return messageService.getMessage(userId, messageId);
    }
/*
    @Path("/profiles/{userId}/{messageId}/comments")
    public CommentResource getCommentResource() { return new CommentResource(); }
*/
}
