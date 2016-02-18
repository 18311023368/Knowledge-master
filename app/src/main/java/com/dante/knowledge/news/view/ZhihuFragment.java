package com.dante.knowledge.news.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.dante.knowledge.MainActivity;
import com.dante.knowledge.R;
import com.dante.knowledge.net.API;
import com.dante.knowledge.net.Constants;
import com.dante.knowledge.news.interf.NewsPresenter;
import com.dante.knowledge.news.interf.NewsView;
import com.dante.knowledge.news.interf.OnListFragmentInteract;
import com.dante.knowledge.news.model.ZhihuData;
import com.dante.knowledge.news.other.ZhihuListAdapter;
import com.dante.knowledge.news.presenter.ZhihuDataPresenter;
import com.dante.knowledge.utils.UiUtils;
import com.zhy.http.okhttp.OkHttpUtils;


public class ZhihuFragment extends RecyclerFragment implements NewsView<ZhihuData>, SwipeRefreshLayout.OnRefreshListener, OnListFragmentInteract {

    private NewsPresenter presenter;
    private ZhihuListAdapter adapter;
    private ConvenientBanner banner;
    private LinearLayoutManager layoutManager;

    @Override
    public void onDestroyView() {
        OkHttpUtils.getInstance().cancelTag(API.TAG_ZHIHU);
        super.onDestroyView();
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
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
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ZhihuListAdapter(getActivity(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter.getItemCount()
                        && adapter.isHasFooter()) {
                    presenter.loadBefore();
                }
            }
        });

    }

    @Override
    protected void initData() {
        presenter = new ZhihuDataPresenter(this, getContext());
        initBanner();
        onRefresh();
    }

    private void initBanner() {
        if (null == banner) {
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    if (recyclerView.getChildCount() != 0) {
                        banner = (ConvenientBanner) layoutManager.findViewByPosition(0);
                        banner.setScrollDuration(1000);
                        banner.startTurning(5000);
                    }
                }
            });
        }
    }

    @Override
    public void showProgress() {
        changeProgress(true);
    }

    @Override
    public void addNews(ZhihuData news) {
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
    public void onRefresh() {
        adapter.clear();
        presenter.loadNews();
    }

    @Override
    public void onListFragmentInteraction(RecyclerView.ViewHolder viewHolder) {

        if (viewHolder instanceof ZhihuListAdapter.ViewHolder) {
            ZhihuListAdapter.ViewHolder holder = (ZhihuListAdapter.ViewHolder) viewHolder;
            Intent intent = new Intent(getActivity(), ZhihuDetailActivity.class);
            intent.putExtra(Constants.ID, holder.zhihuItem.getId());
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                    holder.mImage, getString(R.string.shared_img));
            ActivityCompat.startActivity(getActivity(), intent, optionsCompat.toBundle());

            holder.mTitle.setTextColor(ZhihuListAdapter.textGrey);
        }
    }

}
