package com.dante.knowledge;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Init LeakCanary, Utils.
 */
public class KnowledgeApp extends Application {

    private RefWatcher refWatcher;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher = LeakCanary.install(this);
        context = this;
        Logger.init();
        setupRealm();
    }

    private void setupRealm() {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public static RefWatcher getWatcher(Context context) {
        KnowledgeApp application = (KnowledgeApp) context.getApplicationContext();
        return application.refWatcher;
    }


}
