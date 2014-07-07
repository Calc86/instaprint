package com.instagram;

/**
 * Created by calc on 04.07.14.
 */
public class Urls {
    public static String getTagsRecent(String tag, String accessToken){
        return "https://api.instagram.com/v1/tags/" + tag + "/media/recent?access_token=" + accessToken;
    }
}
