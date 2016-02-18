package com.dante.knowledge.news.interf;

import com.dante.knowledge.news.other.NewsDetail;

/**
 * when detail loaded, this interface is called
 */
public interface OnLoadDetailListener<T extends NewsDetail> {
    void onDetailSuccess(T detailNews);
    void onFailure(String msg, Exception e);

}
