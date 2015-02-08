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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);
        if (convertView == null) {
            // need to create a new view
            // inflate the xml file for this view
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }

        // get all the views
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvCaptionText = (TextView) convertView.findViewById(R.id.tvCaptionText);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        ImageView ivUserPhoto = (ImageView) convertView.findViewById(R.id.ivUserPhoto);
        TextView tvLikesCount = (TextView) convertView.findViewById(R.id.tvLikesCount);
        TextView tvCreatedTime = (TextView) convertView.findViewById(R.id.tvPostedAgo);

        // popualte all the views:
        tvUsername.setText(photo.username);

        String formattedText = "<font color=\"#275b84\">" + photo.username + "</font> " + photo.caption;
        tvCaptionText.setText(Html.fromHtml(formattedText));

        String postedAgo = DateUtils.getRelativeTimeSpanString(photo.createdTime * 1000, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        tvCreatedTime.setText(postedAgo);

        String formattedLikesCount = NumberFormat.getInstance().format(photo.likesCount);
        tvLikesCount.setText(formattedLikesCount + " likes");

        ivUserPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.profilepic).into(ivUserPhoto); // HACK: going to assume user photos are always 150x150 for now

        ivPhoto.setImageResource(0); // clear out image view while loading new image, since this could be a recycled view
        Picasso.with(getContext()).load(photo.imageUrl).placeholder(R.drawable.loading).into(ivPhoto);



        return convertView;
    }
}
