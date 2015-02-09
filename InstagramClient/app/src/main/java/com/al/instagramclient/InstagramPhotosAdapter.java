package com.al.instagramclient;

import android.content.Context;
import android.media.Image;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.List;

public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    private static class ViewHolder {
        TextView tvUsername;
        TextView tvCaptionText;
        ImageView ivPhoto;
        ImageView ivUserPhoto;
        TextView tvLikesCount;
        TextView tvCreatedTime;
        TextView tvComment1;
        TextView tvComment2;
        TextView tvViewAllComments;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);
        ViewHolder vw;

        if (convertView == null) {
            // need to create a new view
            // inflate the xml file for this view
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);

            // get all the views
            vw = new ViewHolder();
            vw.tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
            vw.tvCaptionText = (TextView) convertView.findViewById(R.id.tvCaptionText);
            vw.ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
            vw.ivUserPhoto = (ImageView) convertView.findViewById(R.id.ivUserPhoto);
            vw.tvLikesCount = (TextView) convertView.findViewById(R.id.tvLikesCount);
            vw.tvCreatedTime = (TextView) convertView.findViewById(R.id.tvPostedAgo);
            vw.tvComment1 = (TextView) convertView.findViewById(R.id.tvLatestComment1);
            vw.tvComment2 = (TextView) convertView.findViewById(R.id.tvLatestComment2);
            vw.tvViewAllComments = (TextView) convertView.findViewById(R.id.tvViewAllComments);
            convertView.setTag(vw);
        }
        else {
            vw = (ViewHolder) convertView.getTag();
        }

        // popualte all the views:
        vw.tvUsername.setText(photo.username);

        String formattedText = "<font color=\"#275b84\">" + photo.username + "</font> " + photo.caption;
        vw.tvCaptionText.setText(Html.fromHtml(formattedText));

        String postedAgo = DateUtils.getRelativeTimeSpanString(photo.createdTime * 1000, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        vw.tvCreatedTime.setText(postedAgo);

        String formattedLikesCount = NumberFormat.getInstance().format(photo.likesCount);
        vw.tvLikesCount.setText(formattedLikesCount + " likes");

        vw.ivUserPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.profilepic).into(vw.ivUserPhoto); // HACK: going to assume user photos are always 150x150 for now

        vw.ivPhoto.setImageResource(0); // clear out image view while loading new image, since this could be a recycled view
        Picasso.with(getContext()).load(photo.imageUrl).placeholder(R.drawable.loading).into(vw.ivPhoto);

        if (photo.comment1user != null) {
            formattedText = "<font color=\"#275b84\">" + photo.comment1user + "</font> " + photo.comment1text;
            vw.tvComment1.setText(Html.fromHtml(formattedText));
        }
        else {
            vw.tvComment1.setText("");
        }
        if (photo.comment2user != null) {
            formattedText = "<font color=\"#275b84\">" + photo.comment2user + "</font> " + photo.comment2text;
            vw.tvComment2.setText(Html.fromHtml(formattedText));
        }
        else {
            vw.tvComment1.setText("");
        }
        vw.tvViewAllComments.setText("view all " + photo.numComments + " comments");


        return convertView;
    }
}
