package com.dante.knowledge.news.interf;

import com.dante.knowledge.news.other.Data;
import com.dante.knowledge.news.other.NewsDetail;
import com.dante.knowledge.news.other.NewsItem;

/**
 * deals with the data work
 */
public interface NewsModel<I extends NewsItem, N extends Data, D extends NewsDetail> {

    void getNews(int type, OnLoadDataListener<N> listener);

    void getNewsDetail(I newsItem, OnLoadDetailListener<D> listener);

    void init();
}