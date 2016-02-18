package com.dante.knowledge.Images.model;

import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.dante.knowledge.KnowledgeApp;
import com.dante.knowledge.news.other.Data;

import java.util.concurrent.ExecutionException;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by yons on 16/2/17.
 */
public class Image extends RealmObject implements Data {
    private int id;
    private int type;//Gank or DB
    private String publishedAt;

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @PrimaryKey
    private String url;
    private int width;
    private int height;

    public static Image getFixedImage(String url, int type) throws ExecutionException, InterruptedException {
        Image image = new Image(url, type);
        Bitmap bitmap = Glide.with(KnowledgeApp.context)
                .load(url)
                .asBitmap()
                .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .get();
        image.setWidth(bitmap.getWidth());
        image.setHeight(bitmap.getHeight());
        return image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Image(String url) {
        this.url = url;
    }

    public Image() {
    }

    public Image(String url, int type) {
        this.url = url;
        this.type = type;

    }

}
