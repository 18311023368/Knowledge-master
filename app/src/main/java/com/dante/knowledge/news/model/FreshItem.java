package com.dante.knowledge.news.model;

import com.dante.knowledge.news.other.NewsItem;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * fresh things item in list
 */
public class FreshItem extends RealmObject implements NewsItem, Serializable {
    private int id;
    private String url;
    private String title;
    @PrimaryKey
    private String date;
    /**
     * id : 593
     * slug : banana
     * name : 一只咸鱼
     * first_name :
     * last_name :
     * nickname : 一只咸鱼
     * url :
     * description :
     */
    @Ignore
    private AuthorEntity author;
    private int comment_count;
    private CustomFieldsEntity custom_fields;
    /**
     * id : 489
     * slug : %e5%86%b7%e6%96%b0%e9%97%bb
     * title : 冷新闻
     * description :
     * post_count : 3769
     */
    @Ignore
    private List<TagsEntity> tags;

    public void setId(int id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public void setCustom_fields(CustomFieldsEntity custom_fields) {
        this.custom_fields = custom_fields;
    }

    public void setTags(List<TagsEntity> tags) {
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public AuthorEntity getAuthor() {
        return author;
    }

    public int getComment_count() {
        return comment_count;
    }

    public CustomFieldsEntity getCustom_fields() {
        return custom_fields;
    }

    public List<TagsEntity> getTags() {
        return tags;
    }

    public static class AuthorEntity implements Serializable {
        private int id;
        private String slug;
        private String name;
        private String first_name;
        private String last_name;
        private String nickname;
        private String url;
        private String description;

        public void setId(int id) {
            this.id = id;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getId() {
            return id;
        }

        public String getSlug() {
            return slug;
        }

        public String getName() {
            return name;
        }

        public String getFirst_name() {
            return first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public String getNickname() {
            return nickname;
        }

        public String getUrl() {
            return url;
        }

        public String getDescription() {
            return description;
        }
    }


    public static class TagsEntity implements Serializable {
        private int id;
        private String slug;
        private String title;
        private String description;
        private int post_count;

        public void setId(int id) {
            this.id = id;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setPost_count(int post_count) {
            this.post_count = post_count;
        }

        public int getId() {
            return id;
        }

        public String getSlug() {
            return slug;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public int getPost_count() {
            return post_count;
        }
    }
}
