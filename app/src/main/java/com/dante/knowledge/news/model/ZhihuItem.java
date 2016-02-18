package com.dante.knowledge.news.model;

import com.dante.knowledge.news.other.NewsItem;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * zhihu news item in list
 */
public class ZhihuItem extends RealmObject implements Serializable, NewsItem {

    /**
     * images : ["http://pic1.zhimg.com/aef18b16a9a6dcb445d5c235784c25a8.jpg"]
     * type : 0
     * id : 7813824
     * ga_prefix : 012915
     * title : 运气好的话，说不定 3 万年就把木星挪过来
     */
    @PrimaryKey
    private int id;
    private int type;
    private String title;
    private RealmList<RealmString> images;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ZhihuItem() {
    }

    public ZhihuItem(int date, int type) {
        id = date; //for header we use date as id
        this.type = type;
    }

    public RealmList<RealmString> getImages() {
        return images;
    }

    public void setImages(RealmList<RealmString> images) {
        this.images = images;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

}