package com.dante.knowledge.news.model;

import io.realm.RealmObject;

/**
 * Created by yons on 16/2/15.
 */
public class RealmString extends RealmObject{

    private String val;

    public RealmString() {
    }

    public RealmString(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
