package org.vikas.assigment.project.database;

    import org.vikas.assigment.project.model.Message;
    import org.vikas.assigment.project.model.Profile;

    import java.util.HashMap;
    import java.util.Map;

/**
 * Created by Vikas on 9/12/16.
 */
public class DatabaseClass {
    private static Map<Long, Profile> profiles = new HashMap<>();

    private static Map<Long, Message> messages = new HashMap<>();

    public static Map<Long, Profile> getProfiles() {
        return profiles;
    }

    public static Map<Long, Message> getMessages() { return messages; }

}

