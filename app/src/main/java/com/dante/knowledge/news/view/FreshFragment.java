package com.dante.knowledge.news.view;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dante.knowledge.MainActivity;
import com.dante.knowledge.R;
import com.dante.knowledge.net.API;
import com.dante.knowledge.news.interf.NewsPresenter;
import com.dante.knowledge.news.interf.NewsView;
import com.dante.knowledge.news.interf.OnListFragmentInteract;
import com.dante.knowledge.news.model.FreshData;
import com.dante.knowledge.news.other.NewsListAdapter;
import com.dante.knowledge.news.other.ZhihuListAdapter;
import com.dante.knowledge.news.presenter.FreshDataPresenter;
import com.dante.knowledge.utils.UiUtils;
import com.zhy.http.okhttp.OkHttpUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class FreshFragment extends RecyclerFragment implements SwipeRefreshLayout.OnRefreshListener, NewsView<FreshData>, OnListFragmentInteract {

    private NewsPresenter presenter;
    private NewsListAdapter adapter;
    private LinearLayoutManager layoutManager;

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public void onDestroyView() {
        OkHttpUtils.getInstance().cancelTag(API.TAG_FRESH);
        super.onDestroyView();
    }

    @Override
    protected void initLayoutId() {
        layoutId = R.layout.fragment_recycler;
    }

    @Override
    protected void initViews() {
        super.initViews();
        Context context = getActivity();
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NewsListAdapter(context, this);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter.getItemCount()) {

                    presenter.loadBefore();
                }
            }
        });

    }

    @Override
    protected void initData() {
        presenter = new FreshDataPresenter(this, getContext());
        onRefresh();
    }

    @Override
    public void onRefresh() {
        presenter.loadNews();
    }

    @Override
    public void showProgress() {
        changeProgress(true);
    }

    @Override
    public void addNews(FreshData news) {
        adapter.addNews(news);
    }

    @Override
    public void hideProgress() {
        changeProgress(false);
    }

    @Override
    public void loadFailed(String msg) {
        if (isLive()) {
            UiUtils.showSnack(((MainActivity) getActivity()).getDrawerLayout(), R.string.load_fail);
        }
    }

    @Override
    public void onListFragmentInteraction(RecyclerView.ViewHolder viewHolder) {

        if (viewHolder instanceof NewsListAdapter.ViewHolder) {
            NewsListAdapter.ViewHolder holder = (NewsListAdapter.ViewHolder) viewHolder;
            holder.mTitle.setTextColor(ZhihuListAdapter.textGrey);
            Intent intent = new Intent(getActivity(), FreshDetailActivity.class);
            intent.putExtra(NewsListAdapter.FRESH_ITEM_POSITION, holder.getAdapterPosition());
            startActivity(intent);
        }
    }
}
