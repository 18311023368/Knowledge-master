package com.dante.knowledge.news.model;

import android.content.Context;

import com.dante.knowledge.net.API;
import com.dante.knowledge.net.Constants;
import com.dante.knowledge.net.DB;
import com.dante.knowledge.net.Json;
import com.dante.knowledge.net.Net;
import com.dante.knowledge.news.interf.NewsModel;
import com.dante.knowledge.news.interf.OnLoadDataListener;
import com.dante.knowledge.news.interf.OnLoadDetailListener;
import com.dante.knowledge.utils.Shared;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * deals with the fresh news' data work
 */
public class FreshNewsModel implements NewsModel<FreshItem, FreshData, FreshDetail> {
    /**
     * clear page record to zero and start new request
     */
    public static final int TYPE_FRESH = 0;
    /**
     * a continuous request with increasing one page each time
     */
    public static final int TYPE_CONTINUOUS = 1;
    private Context context;

    public FreshNewsModel(Context context) {
        this.context = context;
    }

    private int page;
    private long lastGetTime;
    public static final int GET_DURATION = 3000;

    @Override
    public void getNews(int type, final OnLoadDataListener<FreshData> listener) {

        lastGetTime = System.currentTimeMillis();
        if (type == TYPE_FRESH) {
            page = 1;//如果是全新请求，就初始化page为1
        }
        getFreshNews(listener);
    }

    private void getFreshNews(final OnLoadDataListener<FreshData> listener) {
        StringCallback callback = new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                if (System.currentTimeMillis() - lastGetTime < GET_DURATION) {
                    Net.get(API.FRESH_NEWS + page, this, API.TAG_FRESH);
                    return;
                }
                listener.onFailure("load fresh news failed", e);
            }

            @Override
            public void onResponse(String response) {
                FreshData news = Json.parseFreshNews(response);
                DB.saveList(news.getPosts());
                Shared.save(Constants.PAGE, page);
                listener.onDataSuccess(news);
                page++;
            }
        };

        Net.get(API.FRESH_NEWS + page, callback, API.TAG_FRESH);
    }

    @Override
    public void getNewsDetail(final FreshItem freshItem, final OnLoadDetailListener<FreshDetail> listener) {
        if (getDetailFromDB(freshItem, listener)) return;

        requestData(freshItem, listener);
    }

    private boolean getDetailFromDB(FreshItem freshItem, OnLoadDetailListener<FreshDetail> listener) {
        PostEntity post = DB.getById(freshItem.getId(), PostEntity.class);
        if (null != post) {
            FreshDetail detailNews = new FreshDetail();
            detailNews.setPost(post);
            listener.onDetailSuccess(detailNews);
            return true;
        }
        return false;
    }

    @Override
    public void init() {

    }

    private void requestData(final FreshItem freshItem, final OnLoadDetailListener<FreshDetail> listener) {
        lastGetTime = System.currentTimeMillis();
        StringCallback callback = new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                if (System.currentTimeMillis() - lastGetTime < GET_DURATION) {
                    Net.get(API.FRESH_NEWS_DETAIL + freshItem.getId(), this, API.TAG_FRESH);
                    return;
                }
                listener.onFailure("load fresh detail failed", e);

            }

            @Override
            public void onResponse(String response) {
                FreshDetail detail = Json.parseFreshDetail(response);
                DB.save(detail.getPost());
                listener.onDetailSuccess(detail);
            }
        };
        Net.get(API.FRESH_NEWS_DETAIL + freshItem.getId(), callback, API.TAG_FRESH);
    }
}
