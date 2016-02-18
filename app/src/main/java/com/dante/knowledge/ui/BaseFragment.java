package com.dante.knowledge.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dante.knowledge.KnowledgeApp;

import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * BaseFragment helps onCreateView, and initViews(when root is null), init data on Activity Created.
 */
public abstract class BaseFragment extends Fragment {
    protected View rootView;
    protected int layoutId;
    protected Realm realm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            initLayoutId();
            rootView = inflater.inflate(layoutId, container, false);
            ButterKnife.bind(this, rootView);
            initViews();
        }
        AlwaysInit();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    protected abstract void initLayoutId();

    protected void AlwaysInit() {
        ButterKnife.bind(this, rootView);
    }

    protected abstract void initViews();

    protected abstract void initData();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        KnowledgeApp.getWatcher(getActivity()).watch(this);
        realm.close();
    }

    public boolean isLive() {
        return getActivity() != null && !getActivity().isDestroyed();
    }
}
