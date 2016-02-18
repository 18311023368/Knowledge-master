package com.dante.knowledge.news.interf;

import com.dante.knowledge.news.other.Data;

/**
 * when news loaded, this interface is called
 */
public interface OnLoadDataListener<T extends Data> {
    void onDataSuccess(T news);
    void onFailure(String msg, Exception e);
}
