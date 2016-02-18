package com.dante.knowledge.news.interf;

import com.dante.knowledge.news.other.NewsItem;

/**
 * helps to present news detail page
 */
public interface NewsDetailPresenter<T extends NewsItem> {
    void loadNewsDetail(T newsItem);
}
