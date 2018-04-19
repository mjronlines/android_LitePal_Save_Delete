package com.yc.day.SqlLit;

import org.litepal.crud.DataSupport;

/**
 * Created by King on 2018/4/19.
 */

public class DayLilst extends DataSupport {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private long id;
}
