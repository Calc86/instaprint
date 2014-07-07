package com.instagram;

/**
 * Created by calc on 03.07.14.
 */
public class Pagination {
    private long next_max_tag_id;
    private long next_max_id;
    private long next_min_id;
    private long min_tag_id;
    private long max_tag_id;

    String next_url;

    public long getNextMaxTagID() {
        return next_max_tag_id;
    }

    public long getNextMaxID() {
        return next_max_id;
    }

    public long getNextMinID() {
        return next_min_id;
    }

    public String getNextUrl() {
        return next_url;
    }

    public long getMaxTagID() {
        return max_tag_id;
    }

    public long getMinTagID() {
        return min_tag_id;
    }
}
