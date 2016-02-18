package com.dante.knowledge.news.model;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by yons on 16/2/16.
 */
public  class CustomFieldsEntity  extends RealmObject implements Serializable {
    private RealmList<RealmString> thumb_c;
    public RealmList<RealmString> getThumb_c() {
        return thumb_c;
    }

    public void setThumb_c(RealmList<RealmString> thumb_c) {
        this.thumb_c = thumb_c;
    }
}