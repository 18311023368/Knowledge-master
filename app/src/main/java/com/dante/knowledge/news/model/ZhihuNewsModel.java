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

import io.realm.Realm;
import io.realm.Sort;
import okhttp3.Call;

/**
 * deals with the zhihu news' data work
 */
public class ZhihuNewsModel implements NewsModel<ZhihuItem, ZhihuData, ZhihuDetail> {

    private String date;
    private long lastGetTime;
    private Context context;
    public static final int GET_DURATION = 2000;
    private int type;
    private boolean isBefore;

    public ZhihuNewsModel(Context context) {
        this.context = context;
    }

    public void init() {
        isBefore = false;
    }

    @Override
    public void getNews(final int type, final OnLoadDataListener<ZhihuData> listener) {
        this.type = type;

        lastGetTime = System.currentTimeMillis();
        final StringCallback callback = new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                if (System.currentTimeMillis() - lastGetTime < GET_DURATION) {
                    getData(this);
                    return;
                }
                listener.onFailure("load zhihu news failed", e);
            }

            @Override
            public void onResponse(String response) {
                ZhihuData news = Json.parseZhihuNews(response);
                date = news.getDate();
                addFooter(news);
                DB.realm.beginTransaction();
                DB.realm.copyToRealmOrUpdate(news);
                DB.realm.allObjectsSorted(ZhihuData.class, "date", Sort.DESCENDING);
                DB.realm.commitTransaction();
                Shared.save(Constants.DATE, date);
                listener.onDataSuccess(news);
            }
        };

        getData(callback);
    }

    private void addFooter(ZhihuData zhihuNews) {
        if (type == API.TYPE_BEFORE) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(new ZhihuItem(Integer.valueOf(zhihuNews.getDate()), 1));
            realm.commitTransaction();
        }

    }

    private void getData(StringCallback callback) {
        if (type == API.TYPE_LATEST) {
            Net.get(API.NEWS_LATEST, callback, API.TAG_ZHIHU);

        } else if (type == API.TYPE_BEFORE) {
            Net.get(API.NEWS_BEFORE + date, callback, API.TAG_ZHIHU);
        }
    }


    @Override
    public void getNewsDetail(final ZhihuItem newsItem, final OnLoadDetailListener<ZhihuDetail> listener) {
        if (getDetailFromDB(newsItem, listener)) return;

        requestData(newsItem, listener);
    }

    private boolean getDetailFromDB(ZhihuItem newsItem, OnLoadDetailListener<ZhihuDetail> listener) {
        ZhihuDetail detailNews = DB.getById(newsItem.getId(), ZhihuDetail.class);
        if (null != detailNews) {
            listener.onDetailSuccess(detailNews);
            return true;
        }
        return false;
    }

    private void requestData(final ZhihuItem newsItem, final OnLoadDetailListener<ZhihuDetail> listener) {
        lastGetTime = System.currentTimeMillis();

        StringCallback callback = new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                if (System.currentTimeMillis() - lastGetTime < GET_DURATION) {
                    Net.get(API.BASE_URL + newsItem.getId(), this, API.TAG_ZHIHU);
                    return;
                } else {
                    if (getDetailFromDB(newsItem, listener)) return;
                }
                listener.onFailure("load zhihu detail failed", e);
            }

            @Override
            public void onResponse(String response) {
                ZhihuDetail detailNews = Json.parseZhihuDetail(response);
                DB.saveOrUpdate(detailNews);
                listener.onDetailSuccess(detailNews);
            }
        };
        Net.get(API.BASE_URL + newsItem.getId(), callback, API.TAG_ZHIHU);
    }


}
