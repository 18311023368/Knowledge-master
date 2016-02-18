package com.dante.knowledge.news.model;

import io.realm.RealmObject;

/**
 * Created by yons on 16/2/15.
 */
public class PostEntity extends RealmObject {
    private int id;
    private String content;

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}