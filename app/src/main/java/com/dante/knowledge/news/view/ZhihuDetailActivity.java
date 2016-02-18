package com.dante.knowledge.news.view;

import android.annotation.SuppressLint;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.dante.knowledge.R;
import com.dante.knowledge.net.Constants;
import com.dante.knowledge.net.DB;
import com.dante.knowledge.news.interf.NewsDetailPresenter;
import com.dante.knowledge.news.interf.NewsDetailView;
import com.dante.knowledge.news.model.ZhihuDetail;
import com.dante.knowledge.news.model.ZhihuItem;
import com.dante.knowledge.news.model.ZhihuTop;
import com.dante.knowledge.news.presenter.ZhihuDetailPresenter;
import com.dante.knowledge.ui.BaseActivity;
import com.dante.knowledge.utils.ImageUtil;
import com.dante.knowledge.utils.ShareUtil;
import com.dante.knowledge.utils.UiUtils;

import butterknife.Bind;

public class ZhihuDetailActivity extends BaseActivity implements NewsDetailView<ZhihuDetail> {
    @Bind(R.id.detail_img)
    ImageView detailImg;
    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.progress)
    ProgressBar progress;
    @Bind(R.id.web_container)
    FrameLayout webContainer;
    private WebView webView;
    private int id;
    private ZhihuItem story;
    private ZhihuDetail zhihuDetail;
    private NewsDetailPresenter<ZhihuItem> presenter;

    @Override
    protected void initLayoutId() {
        layoutId = R.layout.activity_news_detail;
    }

    @Override
    protected void initViews() {
        super.initViews();
        id = getIntent().getIntExtra(Constants.ID, 0);
        story = DB.getById(id, ZhihuItem.class);
        zhihuDetail = DB.getById(id, ZhihuDetail.class);
        if (story == null) {
            //can't find zhihuItem, so this id is passed by Zhihutop
            toolbarLayout.setTitle(DB.getById(id, ZhihuTop.class).getTitle());
        } else {
            toolbarLayout.setTitle(story.getTitle());
        }
        presenter = new ZhihuDetailPresenter(this, this);
        initWebView();
        presenter.loadNewsDetail(story);
        initFAB();
    }

    private void initFAB() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtil.shareText(ZhihuDetailActivity.this, zhihuDetail.getShare_url());
            }
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        webView = new WebView(this);
        webContainer.addView(webView);
        webView.setVisibility(View.INVISIBLE);
        WebSettings settings = webView.getSettings();
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
                    }, 300);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        webView.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        webContainer.removeView(webView);
//        webView.removeAllViews();
//        webView.destroy();
        System.exit(0);
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void showDetail(ZhihuDetail detailNews) {
        zhihuDetail = detailNews;
        ImageUtil.load(this, detailNews.getImage(), detailImg);
        //add css style to webView
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";
        String html = "<html><head>" + css + "</head><body>" + detailNews.getBody() + "</body></html>";
        html = html.replace("<div class=\"img-place-holder\">", "");
        webView.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void showLoadFailed(String msg) {
        UiUtils.showSnackLong(webContainer, R.string.load_fail);

    }

}
