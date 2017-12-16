package org.vikas.assigment.project.service;

    import org.vikas.assigment.project.database.DatabaseClass;
    import org.vikas.assigment.project.model.Profile;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.Map;


/**
 * Created by Vikas on 9/12/16.
 */
public class ProfileService {


    private Map<Long, Profile> profiles = DatabaseClass.getProfiles();

    public ProfileService() {

    }

    public List<Profile> getAllProfiles() {
        return new ArrayList<Profile>(profiles.values());
    }

    public Profile getProfile(long userId) {
        return profiles.get(userId);
    }

    public Profile addProfile(Profile profile) {
        profile.setUserId(profiles.size() + 1);
        profiles.put(profile.getUserId(), profile);
        return profile;
    }

    public Profile updateProfile(long userId, Profile profile) {
        if (profile.getUserId() <= 0) {
            return null;
        }
        profiles.put(profile.getUserId(), profile);
        return profile;
    }

    public Profile removeProfile(long profileId) {
        return profiles.remove(profileId);
    }
}


