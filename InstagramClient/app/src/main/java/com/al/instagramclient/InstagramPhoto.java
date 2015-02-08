package com.al.instagramclient;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Al on 2/5/2015.
 */
public class InstagramPhoto {
    public String username;
    public String caption;
    public String imageUrl;
    public int imageHeight;
    public int likesCount;
    public String profilepic;
    public long createdTime;

    public void populateWithJSON(JSONObject jsonData) {
        try {
            // check for missing attributes in the returned json data
            if (jsonData.optJSONObject("user") == null ||
                    jsonData.optJSONObject("caption") == null ||
                    jsonData.optJSONObject("images") == null ||
                    jsonData.getJSONObject("images").optJSONObject("standard_resolution") == null ||
                    jsonData.optJSONObject("likes") == null) {
                Log.e("InstagramClient", "Missing a JSON attribute: " + jsonData.toString());
            }
            this.username = jsonData.getJSONObject("user").getString("username");
            //Log.w(">>>>>>>", this.username);
            this.profilepic = jsonData.getJSONObject("user").getString("profile_picture");
            this.caption = jsonData.getJSONObject("caption").getString("text");
            this.imageUrl = jsonData.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
            this.imageHeight = jsonData.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
            this.likesCount = jsonData.getJSONObject("likes").getInt("count");
            this.createdTime = jsonData.getLong("created_time");

        } catch (JSONException e) {
            //Log.w("JSON DATA:", jsonData.toString());
            e.printStackTrace();
        }
    }
}
