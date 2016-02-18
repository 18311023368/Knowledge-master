package com.dante.knowledge.news.presenter;

import android.content.Context;

import com.dante.knowledge.net.API;
import com.dante.knowledge.news.interf.NewsModel;
import com.dante.knowledge.news.interf.NewsPresenter;
import com.dante.knowledge.news.interf.NewsView;
import com.dante.knowledge.news.interf.OnLoadDataListener;
import com.dante.knowledge.news.model.ZhihuData;
import com.dante.knowledge.news.model.ZhihuDetail;
import com.dante.knowledge.news.model.ZhihuItem;
import com.dante.knowledge.news.model.ZhihuNewsModel;

/**
 * helps to present zhihu news list
 */
public class ZhihuDataPresenter implements NewsPresenter, OnLoadDataListener<ZhihuData> {

    private NewsView<ZhihuData> mNewsView;
    private NewsModel<ZhihuItem, ZhihuData, ZhihuDetail> mNewsModel;

    public ZhihuDataPresenter(NewsView<ZhihuData> newsView, Context context) {
        this.mNewsView = newsView;
        mNewsModel = new ZhihuNewsModel(context);
    }


    @Override
    public void loadNews() {
        mNewsModel.init();
        mNewsView.showProgress();
        mNewsModel.getNews(API.TYPE_LATEST, this);
    }


    @Override
    public void loadBefore() {
        mNewsModel.getNews(API.TYPE_BEFORE, this);
    }


    @Override
    public void onDataSuccess(ZhihuData news) {
        mNewsView.addNews(news);
        mNewsView.hideProgress();
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mNewsView.hideProgress();
        mNewsView.loadFailed(msg);
    }

}
