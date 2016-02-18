package com.dante.knowledge.news.model;

import com.dante.knowledge.news.other.Data;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Fresh things news class
 */
public class FreshData extends RealmObject implements Data {

    /**
     * status : ok
     * count : 24
     * count_total : 53379
     * pages : 2225
     * posts : [{"id":74787,"url":"http://jandan.net/2016/02/03/gun-free-signs.html","title":"美国新法规或叫停\u201c枪支自由\u201d？","date":"2016-02-03 12:30:45","tags":[{"id":489,"slug":"%e5%86%b7%e6%96%b0%e9%97%bb","title":"冷新闻","description":"","post_count":3769}],"author":{"id":593,"slug":"banana","name":"一只咸鱼","first_name":"","last_name":"","nickname":"一只咸鱼","url":"","description":""},"comment_count":17,"custom_fields":{"thumb_c":["http://tankr.net/s/custom/2CRS.jpg"]}},...]
     */
    private RealmList<FreshItem> posts;
    private String status;
    private int count;
    private int count_total;

    public RealmList<FreshItem> getPosts() {
        return posts;
    }

    public void setPosts(RealmList<FreshItem> posts) {
        this.posts = posts;
    }

    /**
     * id : 74787
     * url : http://jandan.net/2016/02/03/gun-free-signs.html
     * title : 美国新法规或叫停“枪支自由”？
     * date : 2016-02-03 12:30:45
     * tags : [{"id":489,"slug":"%e5%86%b7%e6%96%b0%e9%97%bb","title":"冷新闻","description":"","post_count":3769}]
     * author : {"id":593,"slug":"banana","name":"一只咸鱼","first_name":"","last_name":"","nickname":"一只咸鱼","url":"","description":""}
     * comment_count : 17
     * custom_fields : {"thumb_c":["http://tankr.net/s/custom/2CRS.jpg"]}
     */

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setCount_total(int count_total) {
        this.count_total = count_total;
    }


    public String getStatus() {
        return status;
    }

    public int getCount() {
        return count;
    }

    public int getCount_total() {
        return count_total;
    }


}
