package com.dante.knowledge.net;

import com.dante.knowledge.news.model.FreshData;
import com.dante.knowledge.news.model.FreshDetail;
import com.dante.knowledge.news.model.RealmString;
import com.dante.knowledge.news.model.ZhihuData;
import com.dante.knowledge.news.model.ZhihuDetail;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.lang.reflect.Type;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.internal.IOException;

/**
 * Json parser util.
 */
public class Json {
    public Json() {
    }

    public static Type token = new TypeToken<RealmList<RealmString>>() {
    }.getType();


    public static Gson mGson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            return f.getDeclaringClass().equals(RealmObject.class);
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }
    })
            .registerTypeAdapter(token, new TypeAdapter<RealmList<RealmString>>() {

                @Override
                public void write(JsonWriter out, RealmList<RealmString> value) throws IOException {
                    // Ignore
                }

                @Override
                public RealmList<RealmString> read(JsonReader in) throws IOException, java.io.IOException {
                    RealmList<RealmString> list = new RealmList<>();
                    in.beginArray();
                    while (in.hasNext()) {
                        list.add(new RealmString(in.nextString()));
                    }
                    in.endArray();
                    return list;
                }
            }).create();

    public static ZhihuData parseZhihuNews(String latest) {
        return mGson.fromJson(latest, ZhihuData.class);
    }

    public static ZhihuDetail parseZhihuDetail(String detail) {
        return mGson.fromJson(detail, ZhihuDetail.class);
    }

    public static FreshData parseFreshNews(String fresh) {
        return mGson.fromJson(fresh, FreshData.class);
    }

    public static FreshDetail parseFreshDetail(String detail) {
        return mGson.fromJson(detail, FreshDetail.class);
    }

    public static <Data> Data parseNews(String response, Class<Data> clz) {
        return mGson.fromJson(response, clz);
    }

}
