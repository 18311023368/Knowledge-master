package com.dante.knowledge.news.view;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.dante.knowledge.R;
import com.dante.knowledge.ui.BaseFragment;

import butterknife.Bind;


/**
 * All fragments have recyclerView & swipeRefresh must implement this.
 */
public abstract class RecyclerFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.list)
    RecyclerView recyclerView;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    boolean isFirst=true;
    @Override
    protected void initViews() {
        recyclerView.setHasFixedSize(true);
        swipeRefresh.setColorSchemeColors(R.color.colorPrimary,
                R.color.colorPrimaryDark, R.color.colorAccent);
        swipeRefresh.setOnRefreshListener(this);
    }
    public void changeProgress(final boolean refreshState) {
        if (null != swipeRefresh) {
            swipeRefresh.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefresh.setRefreshing(refreshState);
                }
            });
        }}

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
}
