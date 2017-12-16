package org.vikas.assigment.project.service;

    import org.vikas.assigment.project.database.DatabaseClass;
    import org.vikas.assigment.project.model.Message;
    import org.vikas.assigment.project.model.Profile;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.Map;

/**
 * Created by Vikas on 9/12/16.
 */
public class MessageService {

    private Map<Long, Profile> profiles = DatabaseClass.getProfiles();
    //private Map<Long, Message> messages = DatabaseClass.getMessages();


    public MessageService() {

    }


    public List<Message> getAllMessages(long userId) {
        Map<Long, Message> messages = profiles.get(userId).getMessages();
        return new ArrayList<Message>(messages.values());
    }

    public Message getMessage(long userId, long messageId) {
        Map<Long, Message> messages = profiles.get(userId).getMessages();
        return messages.get(messageId);
    }

    public Message addMessage(long userId, Message message) {
        Map<Long, Message> messages = profiles.get(userId).getMessages();
        message.setMessageId(messages.size() + 1);
        messages.put(message.getMessageId(), message);
        return message;
    }

    public Message updateMessage(long userId, Message message) {
        Map<Long, Message> messages = profiles.get(userId).getMessages();
        if ( message.getMessageId() <= 0) {
            return null;
        }
        messages.put(message.getMessageId(), message);
        return message;
    }

    public Message removeMessage(long userId, long messageId) {
        Map<Long, Message> messages = profiles.get(userId).getMessages();
        return messages.remove(messageId);
    }
}
