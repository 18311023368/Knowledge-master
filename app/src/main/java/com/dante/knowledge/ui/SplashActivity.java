package com.dante.knowledge.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.appcompat.R.anim;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dante.knowledge.MainActivity;
import com.dante.knowledge.R;
import com.dante.knowledge.net.API;
import com.dante.knowledge.net.Constants;
import com.dante.knowledge.net.Net;
import com.dante.knowledge.utils.ImageUtil;
import com.dante.knowledge.utils.Shared;
import com.dante.knowledge.utils.StringUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import okhttp3.Call;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 0;
    private static final String SPLASH = "splash";
    private ImageView splash;
    private String today;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
        splash = (ImageView) findViewById(R.id.splash);
        if (Shared.getBoolean(SettingFragment.ORIGINAL_SPLASH)) {
            Glide.with(this).load(R.drawable.splash).crossFade(1500).into(splash);
            startAppDelay();
            return;
        }
        initSplash();
    }


    private void initSplash() {
        today = StringUtil.parseStandardDate(new Date());
        if (today.equals(Shared.get(Constants.DATE, ""))) {
            loadImageFile();
        } else {
            //if today is not latest getBoolean splash date, getSplash.
            getSplash();
        }
    }


    private void getSplash() {
        loadImageFile();
        if (!Net.isOnline(this)) {
            return;
        }

        Net.get(API.SPLASH, new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                startApp();
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String url = jsonObject.getString("img");
                    Shared.save(SPLASH, url);
                    Shared.save(Constants.DATE, today);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, API.TAG_SPLASH);
    }

    private void loadImageFile() {
        String url = Shared.get(SPLASH, "");
//        if ("".equals(url)) {
//            Glide.with(this).load(R.drawable.splash).crossFade(SPLASH_DURATION).into(splash);
//            getSplash();
//        } else {
            ImageUtil.load(url, R.anim.scale_anim, splash);
//        }
        startAppDelay();
    }

    private void startAppDelay() {
        splash.postDelayed(new Runnable() {
            @Override
            public void run() {
                startApp();
            }
        }, SPLASH_DURATION);
    }

    private void startApp() {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(anim.abc_grow_fade_in_from_bottom, anim.abc_shrink_fade_out_from_bottom);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(API.TAG_SPLASH);
    }
}
