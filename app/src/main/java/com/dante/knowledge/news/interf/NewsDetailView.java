package com.dante.knowledge.news.interf;

import com.dante.knowledge.news.other.NewsDetail;

/**
 * fragment or activity need to implement this to show news detail.
 */
public interface NewsDetailView<T extends NewsDetail> {
    void showProgress();
    void showDetail(T detailNews);
    void hideProgress();
    void showLoadFailed(String msg);
}
