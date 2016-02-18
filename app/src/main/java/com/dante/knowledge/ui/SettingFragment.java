package com.dante.knowledge.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dante.knowledge.R;
import com.dante.knowledge.utils.Shared;
import com.dante.knowledge.utils.Tool;
import com.dante.knowledge.utils.UiUtils;

import java.io.File;

/**
 * the view in setting activity.
 */
public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {
    public static final String CLEAR_CACHE = "clear_cache";
    public static final String FEED_BACK = "feedback";
    public static final String APP_VERSION = "version";
    public static final String ORIGINAL_SPLASH = "original_splash";
    public static final String SECRET_MODE = "secret_mode";
    private static final long DURATION = 500;

    private Preference clearCache;
    private Preference about;
    private Preference version;
    private Preference splash;
    private CheckBoxPreference enableSister;
    private View rootView;

    private long startTime;
    private boolean first = true;
    private int secretIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        clearCache = findPreference(CLEAR_CACHE);
        about = findPreference(FEED_BACK);
        version = findPreference(APP_VERSION);
        splash = findPreference(ORIGINAL_SPLASH);
        clearCache.setSummary(clearCache.getSummary() + fileDirSize());
        splash.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                if (System.currentTimeMillis() - startTime < DURATION * 3) {
                    if (secretIndex < 2) {
                        return true;
                    }
                    Log.i("test", "splash " + secretIndex);
                    secretIndex++;
                }
                if (secretIndex == 5) {
                    if (Shared.getBoolean(SECRET_MODE)) {
                        Shared.save(SECRET_MODE, false);
                        UiUtils.showSnack(rootView, R.string.secret_mode_closed);
                    } else {
                        Shared.save(SECRET_MODE, true);
                        UiUtils.showSnackLong(rootView, R.string.secret_mode_opened);
                    }
                    secretIndex++;
                }
                return true;
            }
        });
        version.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (first) {
                    startTime = System.currentTimeMillis();
                    first = false;
                    Log.i("test", "first " + secretIndex);
                }
                if (System.currentTimeMillis() - startTime < DURATION) {
                    if (secretIndex > 2) {
                        return true;
                    }
                    Log.i("test", "version " + secretIndex);
                    secretIndex++;
                }

                return true;
            }
        });

        clearCache.setOnPreferenceClickListener(this);
        about.setOnPreferenceClickListener(this);
    }

    private String fileDirSize() {
        File file = getActivity().getApplicationContext().getFilesDir();
        return Tool.getFileSize(file);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (null == rootView) {
            rootView = super.onCreateView(inflater, container, savedInstanceState);
        }
        return rootView;

    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();
        File dir = getActivity().getApplicationContext().getFilesDir();

        if (key.equals(CLEAR_CACHE)) {

            for (File file : dir.listFiles()) {
                if (!file.delete()) {
                    Snackbar.make(rootView, R.string.clear_cache_failed, Snackbar.LENGTH_SHORT).show();
                    return true;
                }
            }
            Snackbar.make(rootView, R.string.clear_cache_success, Snackbar.LENGTH_SHORT).show();
            clearCache.setSummary(getString(R.string.set_current_cache) + fileDirSize());

        } else if (key.equals(FEED_BACK)) {
            sendEmail();
        }
        return true;
    }

    private void sendEmail() {
        Intent email = new Intent(Intent.ACTION_SENDTO);
        email.setData(Uri.parse("mailto:danteandroi@gmail.com"));
        email.putExtra(Intent.EXTRA_SUBJECT, "Knowledge Feedback");
        email.putExtra(Intent.EXTRA_TEXT, "Hi，");
        startActivity(email);
    }

}
