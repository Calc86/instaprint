package com.instagram;

/**
 * Created by calc on 04.07.14.
 */
public class Images {
    private Image low_resolution;
    private Image thumbnail;
    private Image standard_resolution;

    public Image getLow() {
        return low_resolution;
    }

    public Image getThumbnail() {
        return thumbnail;
    }

    public Image getStandard() {
        return standard_resolution;
    }
}
