package com.dante.knowledge.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.dante.knowledge.R;
import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * BaseActivity includes a base layoutId, init its toolbar (if the layout has one)
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected int layoutId = R.layout.activity_base;
    protected Toolbar toolbar;
    protected Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayoutId();
        initViews();
    }

    protected abstract void initLayoutId();

    /**
     * MUST override and call the SUPER method
     */
    protected void initViews() {
        setContentView(layoutId);
        ButterKnife.bind(this);
        initAppBar();
        Logger.init();
        realm = Realm.getDefaultInstance();
    }

    private void initAppBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (null != toolbar) {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void replaceFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, fragment, tag);
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != realm) {
            realm.close();
        }
    }
}
