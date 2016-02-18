package com.dante.knowledge.news.view;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.dante.knowledge.R;
import com.dante.knowledge.news.interf.NewsDetailPresenter;
import com.dante.knowledge.news.interf.NewsDetailView;
import com.dante.knowledge.news.model.FreshDetail;
import com.dante.knowledge.news.model.FreshItem;
import com.dante.knowledge.news.presenter.FreshDetailPresenter;
import com.dante.knowledge.ui.BaseFragment;
import com.dante.knowledge.utils.ShareUtil;
import com.dante.knowledge.utils.UiUtils;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FreshDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FreshDetailFragment extends BaseFragment implements NewsDetailView<FreshDetail> {

    private static final String FRESH_ITEM = "fresh_news";
    private static final String FRESH_PREVIOUS_ITEM = "previous_news";

    @Bind(R.id.progress)
    ProgressBar progress;
    @Bind(R.id.web_container)
    FrameLayout webContainer;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private WebView webView;

    private FreshItem freshItem;
    private NewsDetailPresenter<FreshItem> presenter;
    private ShareActionProvider mShareActionProvider;

    public FreshDetailFragment() {
    }

    public static FreshDetailFragment newInstance(FreshItem freshItem) {
        FreshDetailFragment fragment = new FreshDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(FRESH_ITEM, freshItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            freshItem = (FreshItem) getArguments().getSerializable(FRESH_ITEM);
        }
        setHasOptionsMenu(true);
    }

    @Override
    protected void initLayoutId() {
        layoutId = R.layout.fragment_fresh_detail;
    }

    @Override
    protected void initViews() {
        presenter = new FreshDetailPresenter(this,getContext());
    }

    @Override
    protected void initData() {
        presenter.loadNewsDetail(freshItem);
        toolbar.setTitle(freshItem.getTitle());
        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

    }

    @Override
    protected void AlwaysInit() {
        super.AlwaysInit();
        initWebView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        webView = new WebView(getActivity());
        webContainer.addView(webView);
        webView.setVisibility(View.INVISIBLE);
        WebSettings settings = webView.getSettings();
        settings.setTextZoom(110);
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(final WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            view.setVisibility(View.VISIBLE);
                            hideProgress();
                        }
                    }, 200);
                }
            }
        });
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void showDetail(FreshDetail detailNews) {
        setShareIntent();
        webView.loadDataWithBaseURL("x-data://base", detailNews.getPost().getContent(), "text/html", "UTF-8", null);
    }


    @Override
    public void hideProgress() {
        if (null != progress) {
            progress.setVisibility(View.GONE);
        }
    }

    @Override
    public void showLoadFailed(String msg) {
        UiUtils.showSnackLong(rootView, R.string.load_fail);

    }

    @Override
    public void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    public void onDestroyView() {
//        webContainer.removeView(webView);
//        webView.removeAllViews();
//        webView.destroy();
        super.onDestroyView();
    }

    private void setShareIntent() {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(
                    ShareUtil.getShareIntent(freshItem.getUrl()));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.share_menu, menu);
        MenuItem item = menu.findItem(R.id.menu_item_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
    }

}
