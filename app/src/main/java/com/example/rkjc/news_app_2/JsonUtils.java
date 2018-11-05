package com.example.rkjc.news_app_2;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class JsonUtils {
    public static ArrayList<NewsItem> parseNews(String JSONString) {
        ArrayList<NewsItem> list = new ArrayList<NewsItem>();
        try {
            JSONObject o = new JSONObject(JSONString);
            JSONArray a = o.getJSONArray("articles");
        for (int i = 0; i < a.length(); i++) {
            JSONObject json = new JSONObject(a.getString(i));
            NewsItem n = new NewsItem();
            n.setAuthorFromJSON(json.getString("author"));
            n.setTitleFromJSON(json.getString("title"));
            n.setDescriptionFromJSON(json.getString("description"));
            n.setPublishedFromJSON(json.getString("publishedAt"));
            n.setUrlFromJSON(json.getString("url"));
            n.setUrlToImageFromJSON(json.getString("urlToImage"));
            list.add(n);
        }
        }
        catch(JSONException e){
            e.printStackTrace();
        }
        return list;
    }
}


