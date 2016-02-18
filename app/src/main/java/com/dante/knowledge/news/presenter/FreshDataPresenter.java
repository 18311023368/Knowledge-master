package com.dante.knowledge.news.presenter;

import android.content.Context;

import com.dante.knowledge.news.interf.NewsModel;
import com.dante.knowledge.news.interf.NewsPresenter;
import com.dante.knowledge.news.interf.NewsView;
import com.dante.knowledge.news.interf.OnLoadDataListener;
import com.dante.knowledge.news.model.FreshData;
import com.dante.knowledge.news.model.FreshDetail;
import com.dante.knowledge.news.model.FreshItem;
import com.dante.knowledge.news.model.FreshNewsModel;

/**
 * helps to present fresh news list
 */
public class FreshDataPresenter implements NewsPresenter, OnLoadDataListener<FreshData> {
    private NewsView<FreshData> mNewsView;
    private NewsModel<FreshItem, FreshData, FreshDetail> mNewsModel;

    public FreshDataPresenter(NewsView<FreshData> newsView, Context context) {
        this.mNewsView = newsView;
        mNewsModel = new FreshNewsModel(context);
    }

    @Override
    public void loadNews() {
        mNewsView.showProgress();
        mNewsModel.getNews(FreshNewsModel.TYPE_FRESH, this);
    }

    @Override
    public void loadBefore() {
        mNewsView.showProgress();
        mNewsModel.getNews(FreshNewsModel.TYPE_CONTINUOUS, this);

    }

    @Override
    public void onDataSuccess(FreshData news) {
        mNewsView.addNews(news);
        mNewsView.hideProgress();
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mNewsView.hideProgress();
        mNewsView.loadFailed(msg);
    }
}
