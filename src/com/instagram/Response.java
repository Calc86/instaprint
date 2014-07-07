package com.instagram;

import java.util.List;

/**
 * Created by calc on 03.07.14.
 *
 */
public class Response {
    private Meta meta;
    private List<Tag> data;
    private Pagination pagination;

    public Meta getMeta() {
        return meta;
    }

    public List<Tag> getData() {
        return data;
    }

    public Pagination getPagination() {
        return pagination;
    }
}
