package com.nanodegree.pankaj.popularmoviesapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nanodegree.pankaj.popularmoviesapp.R;
import com.nanodegree.pankaj.popularmoviesapp.utils.ImagePathUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Panaj Singh on 11/1/2016.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> items;


    public ImageAdapter(Context c, ArrayList<String> items) {
        mContext = c;
        this.items = items;
    }

    public int getCount() {
        return items.size();
    }

    public Object getItem(int position) {
        return items.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setAdjustViewBounds(true);
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (ImageView) convertView;
        }

        Picasso.with(mContext).load(ImagePathUtil.getMovieImagePosterPath(mContext, items.get(position))).placeholder(R.drawable.loading_image_placeholder).error(R.drawable.loading_image_error_place_holder).into(imageView);

        return imageView;

    }


}
