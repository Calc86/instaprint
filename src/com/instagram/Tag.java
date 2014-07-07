package com.instagram;

import java.util.List;

/**
 * Created by calc on 04.07.14.
 *
 */
public class Tag {
    //private String attribution;
    private List<String> tags;
    private String type;
    private Location location;
    //comments
    //filter
    private long created_time;
    private String link;
    //likes
    private Images images;
    //users_in_photo
    //caption
    private boolean user_has_liked;
    private String id;
    private User user;

    public List<String> getTags() {
        return tags;
    }

    public String getType() {
        return type;
    }

    public Location getLocation() {
        return location;
    }

    public long getCreated_time() {
        return created_time;
    }

    public String getLink() {
        return link;
    }

    public Images getImages() {
        return images;
    }

    public boolean isUserHasLiked() {
        return user_has_liked;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }
}
