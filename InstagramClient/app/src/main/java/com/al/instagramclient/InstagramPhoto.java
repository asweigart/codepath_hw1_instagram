package com.al.instagramclient;

import android.util.Log;

import org.json.JSONArray;
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
    public String comment1user;
    public String comment1text;
    public String comment2user;
    public String comment2text;
    public int numComments;


    public void populateWithJSON(JSONObject jsonData) {
        try {
            // check for missing attributes in the returned json data
            if (jsonData.optJSONObject("user") == null) {
                Log.e("InstagramClient", "Missing JSON attribute 'user': " + jsonData.toString());
            }
            if (jsonData.optJSONObject("caption") == null) {
                Log.e("InstagramClient", "Missing JSON attribute 'caption': " + jsonData.toString());
                this.caption = "";
            }
            else {
                this.caption = jsonData.getJSONObject("caption").getString("text");
            }
            if (jsonData.optJSONObject("comments") == null) {
                Log.e("InstagramClient", "Missing JSON attribute 'comments': " + jsonData.toString());
            }
            if (jsonData.optJSONObject("images").optJSONObject("standard_resolution") == null) {
                Log.e("InstagramClient", "Missing JSON attribute 'images/standard_resolution': " + jsonData.toString());
            }
            if (jsonData.optJSONObject("user") == null) {
                Log.e("InstagramClient", "Missing JSON attribute 'user': " + jsonData.toString());
            }
            if (jsonData.optJSONObject("likes") == null) {
                Log.e("InstagramClient", "Missing JSON attribute 'likes': " + jsonData.toString());
            }

            this.username = jsonData.getJSONObject("user").getString("username");
            this.profilepic = jsonData.getJSONObject("user").getString("profile_picture");
            this.imageUrl = jsonData.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
            this.imageHeight = jsonData.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
            this.likesCount = jsonData.getJSONObject("likes").getInt("count");
            this.createdTime = jsonData.getLong("created_time");


            JSONArray commentsData = jsonData.getJSONObject("comments").getJSONArray("data");

            if (commentsData.length() > 0) {
                JSONObject comment1 = commentsData.getJSONObject(commentsData.length() - 1);
                this.comment1user = comment1.getJSONObject("from").getString("username");
                this.comment1text = comment1.getString("text");
            }

            if (commentsData.length() > 1) {
                JSONObject comment2 = commentsData.getJSONObject(commentsData.length() - 2);
                this.comment2user = comment2.getJSONObject("from").getString("username");
                this.comment2text = comment2.getString("text");
            }

            this.numComments = jsonData.getJSONObject("comments").getInt("count");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
