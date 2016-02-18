package com.dante.knowledge.Images.model;

import android.os.AsyncTask;

import com.dante.knowledge.net.DB;
import com.dante.knowledge.news.interf.OnLoadDataListener;
import com.dante.knowledge.news.view.PictureFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by yons on 16/2/17.
 */
public class PictureParser {
    private int type;
    private String response;
    private String[] publishAts;
    private OnLoadDataListener<Image> listener;
    private AsyncTask<String, Void, List<Image>> asyncTask;

    public PictureParser(int type, String response, OnLoadDataListener<Image> listener) {
        this.listener = listener;
        this.type = type;
        this.response = response;
    }

    public void parse() {
        if (type == PictureFragment.TYPE_GANK) {

            parseGANK();

        } else if (PictureFragment.TYPE_GANK < type
                && type <= PictureFragment.TYPE_DB_RANK) {

            parseDB();

        } else if (PictureFragment.TYPE_DB_RANK < type
                && type < PictureFragment.TYPE_H_STREET) {
            parseH();
        }
    }

    private void parseH() {

    }

    private void parseDB() {
        Document document = Jsoup.parse(response);
        Elements elements = document.select("div[class=thumbnail] > div[class=img_single] > a > img");
        final int size = elements.size();
        String[] urls = new String[size];
        for (int i = 0; i < size; i++) {
            urls[i] = elements.get(i).attr("src");
        }
        saveImages(urls);
    }

    public void cancelTask() {
        if (null != asyncTask) {
            asyncTask.cancel(true);
        }
    }

    private void parseGANK() {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray array = jsonObject.getJSONArray("results");
            final int size = array.length();
            String[] urls = new String[size];
            publishAts = new String[size];

            for (int i = 0; i < size; i++) {
                jsonObject = array.getJSONObject(i);
                urls[i] = jsonObject.getString("url");
                publishAts[i] = jsonObject.getString("publishedAt");
            }
            saveImages(urls);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void saveImages(String[] urls) {
        asyncTask = new PictureTask();
        asyncTask.execute(urls);

    }

    public class PictureTask extends AsyncTask<String, Void, List<Image>> {
        @Override
        protected List<Image> doInBackground(String... strings) {
            List<Image> images = new ArrayList<>();
            if (isCancelled()) {
                return images;
            }
            for (int i = 0; i < strings.length; i++) {
                try {
                    Image image = Image.getFixedImage(strings[i], type);
                    if (type == PictureFragment.TYPE_GANK) {
                        image.setPublishedAt(publishAts[i]);
                    }
                    images.add(image);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return images;
        }

        @Override
        protected void onPostExecute(List<Image> images) {
            if (isCancelled()) {
                return;
            }
            if (images.size() == 0) {
                listener.onFailure("image task failed", null);
                return;
            }
            DB.saveList(images);
            listener.onDataSuccess(images.get(0));
        }

    }
}
