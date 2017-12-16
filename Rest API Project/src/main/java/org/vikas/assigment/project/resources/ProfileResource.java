package org.vikas.assigment.project.resources;

    import org.vikas.assigment.project.model.Profile;
    import org.vikas.assigment.project.service.ProfileService;

    import javax.ws.rs.*;
    import javax.ws.rs.core.MediaType;
    import java.util.List;


/**
 * Created by Vikas on 9/12/16.
 */

@Path("/profiles")
//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
public class ProfileResource {

    private ProfileService profileService = new ProfileService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Profile> getProfiles() {
        return profileService.getAllProfiles();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Profile addProfile(Profile profile) {
        return profileService.addProfile(profile);
    }

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Profile getProfile(@PathParam("userId") long userId) {
        return profileService.getProfile(userId);
    }

    @PUT
    @Path("/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Profile updateProfile(@PathParam("userId") long userId, Profile profile) {
        profile.setUserId(userId);
        return profileService.updateProfile(userId, profile);
    }

    @DELETE
    @Path("/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteProfile(@PathParam("userId") long userId) {
        profileService.removeProfile(userId);
    }
/*
    @Path("/{userId}/messages")
    public MessageResource getMessageResource() { return new MessageResource(); }

    @Path("/{userId}/messages/{messageId}/comments")
    public CommentResource getCommentResource() {
        return new CommentResource();
    }
*/
}