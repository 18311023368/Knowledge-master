package com.dante.knowledge.news.view;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.dante.knowledge.R;
import com.dante.knowledge.net.Constants;
import com.dante.knowledge.ui.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;

/**
 * A tab fragment {@link Fragment} contains different news fragment.
 */
public class NewsTabFragment extends BaseFragment {

    private static final int SMOOTHSCROLL_TOP_POSITION = 50;
    @Bind(R.id.pager)
    ViewPager pager;
    @Bind(R.id.tabs)
    TabLayout tabs;
    public static final int TAG_ZHIHU = 0;
    public static final int TAG_FRESH = 1;

    public static final String MENU_NEWS = "news";
    public static final String MENU_PIC = "pic";
    public static final String MENU_SECRET = "secret";

    private List<RecyclerFragment> fragments = new ArrayList<>();
    private List<String> titles;
    private NewsTabPagerAdapter adapter;
    private String menuType;

    public static NewsTabFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString(Constants.TYPE, type);
        NewsTabFragment fragment = new NewsTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initLayoutId() {
        layoutId = R.layout.fragment_news_tab;
    }

    @Override
    protected void initViews() {
        adapter = new NewsTabPagerAdapter(getChildFragmentManager());
        initFragments();
        pager.setAdapter(adapter);
        if (MENU_PIC.equals(menuType)) {
            tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
        tabs.setupWithViewPager(pager);
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                scrollToTop(fragments.get(tab.getPosition()).getRecyclerView());
            }
        });
    }

    private void initFragments() {
        menuType = getArguments().getString(Constants.TYPE);

        if (MENU_PIC.equals(menuType)) {
            String[] titles = new String[]{getString(R.string.gank), getString(R.string.db_breast), getString(R.string.db_butt), getString(R.string.db_silk), getString(R.string.db_leg), getString(R.string.db_rank)};
            this.titles = Arrays.asList(titles);
            for (int i = 0; i < titles.length; i++) {
                //ensure the types are from 0 to length before using 'for' loop
                fragments.add(PictureFragment.newInstance(i));
            }

        } else if (MENU_SECRET.equals(menuType)) {
            String[] titles = new String[]{getString(R.string.h_asia), getString(R.string.h_selfie), getString(R.string.h_street), getString(R.string.h_silk)};
            this.titles = Arrays.asList(titles);
            for (int i = PictureFragment.TYPE_H_ASIA; i < 1+PictureFragment.TYPE_H_STREET; i++) {
                //ensure the types are from 0 to length before using 'for' loop
                fragments.add(PictureFragment.newInstance(i));
            }

        } else {
            titles = new ArrayList<>();
            fragments.add(new ZhihuFragment());
            fragments.add(new FreshFragment());
            titles.add(getString(R.string.zhihu_news));
            titles.add(getString(R.string.fresh_news));
        }

        adapter.setFragments(fragments, titles);
    }

    private void scrollToTop(RecyclerView list) {
        if (null != list) {
            int lastPosition;
            if (MENU_PIC.equals(menuType)) {
                StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) list.getLayoutManager();
                lastPosition = manager.findLastVisibleItemPositions(
                        new int[manager.getSpanCount()])[1];

            } else {
                LinearLayoutManager manager = (LinearLayoutManager) list.getLayoutManager();
                lastPosition = manager.findLastVisibleItemPosition();
            }
            if (lastPosition < SMOOTHSCROLL_TOP_POSITION) {
                list.smoothScrollToPosition(0);
            } else {
                list.scrollToPosition(0);
            }
        }
    }

    @Override
    protected void initData() {

    }


    public static class NewsTabPagerAdapter extends FragmentPagerAdapter {

        private List<RecyclerFragment> fragments;
        private List<String> titles;

        public NewsTabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setFragments(List<RecyclerFragment> fragments, List<String> titles) {
            this.fragments = fragments;
            this.titles = titles;
        }

        public void addFragment(RecyclerFragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

}
