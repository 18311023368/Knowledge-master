package com.dante.knowledge.utils;

import android.content.Context;
import android.content.Intent;

import com.dante.knowledge.R;

/**
 * Util to share data, make share intent, etc.
 */
public class ShareUtil {

    public static Intent getShareIntent(String shareText) {
        Intent textIntent = new Intent();
        textIntent.setAction(Intent.ACTION_SEND);
        textIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        textIntent.setType("text/plain");
        return textIntent;
    }
    public static Intent getShareHtmlIntent(String htmlText) {
        Intent textIntent = new Intent();
        textIntent.setAction(Intent.ACTION_SEND);
        textIntent.putExtra(Intent.EXTRA_TEXT, "This is html");
        textIntent.putExtra(Intent.EXTRA_HTML_TEXT, htmlText);
        textIntent.setType("text/plain");
        return textIntent;
    }

    public static void shareText(Context context, String text) {
        context.startActivity(
                Intent.createChooser(getShareIntent(text),
                        context.getString(R.string.share_to)));
    }
    public static void shareHtmlText(Context context, String text) {
        context.startActivity(
                Intent.createChooser(getShareHtmlIntent(text),
                        context.getString(R.string.share_to)));
    }
}
