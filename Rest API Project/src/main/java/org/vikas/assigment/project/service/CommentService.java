package org.vikas.assigment.project.service;

    import org.vikas.assigment.project.database.DatabaseClass;
    import org.vikas.assigment.project.model.Comment;
    import org.vikas.assigment.project.model.Message;
    import org.vikas.assigment.project.model.Profile;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.Map;

public class CommentService {

    private Map<Long, Profile> profiles = DatabaseClass.getProfiles();
    private Map<Long, Message> messages = DatabaseClass.getMessages();


    public List<Comment> getAllComments(long userId, long messageId) {
        Map<Long, Message> messages = profiles.get(userId).getMessages();
        Map<Long, Comment> comments = messages.get(messageId).getComments();
        return new ArrayList<Comment>(comments.values());
    }

    public Comment getComment(long userId, long messageId, long commentId) {
        Map<Long, Message> messages = profiles.get(userId).getMessages();
        Map<Long, Comment> comments = messages.get(messageId).getComments();
        return comments.get(commentId);
    }

    public Comment addComment(long userId, long messageId, Comment comment) {
        Map<Long, Message> messages = profiles.get(userId).getMessages();
        Map<Long, Comment> comments = messages.get(messageId).getComments();
        comment.setCommentId(comments.size()+1);
        comments.put(comment.getCommentId(), comment);
        return comment;
    }

    public Comment updateComment(long userId, long messageId, Comment comment) {
        Map<Long, Message> messages = profiles.get(userId).getMessages();
        Map<Long, Comment> comments = messages.get(messageId).getComments();
        if (comment.getCommentId() <= 0) {
            return null;
        }
        comments.put(comment.getCommentId(), comment);
        return comment;
    }

    public Comment removeComment(long userId, long messageId, long commentId) {
        Map<Long, Message> messages = profiles.get(userId).getMessages();
        Map<Long, Comment> comments = messages.get(messageId).getComments();
        return comments.remove(commentId);
    }
}