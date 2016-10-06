package com.hiteshsahu.parallaxpullrefreshconcept.view.demo;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hiteshsahu.parallaxpullrefreshconcept.R;


public class TestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView mediaName, mediaDesc, mediaInfo;
    private ImageView mediaImage;
    private CardView container;

    public TestViewHolder(View v) {
        super(v);
        mediaName = (TextView) v.findViewById(R.id.product_name);
        mediaDesc = (TextView) v.findViewById(R.id.product_desc);
        mediaInfo = (TextView) v.findViewById(R.id.loan_range);
        mediaImage = (ImageView) v.findViewById(R.id.product_image);
        container = (CardView) v.findViewById(R.id.list_container);
    }

    public TextView getImageTitle() {
        return mediaInfo;
    }

    public ImageView getMediaImage() {
        return mediaImage;
    }

    public void setMediaImage(ImageView mediaImage) {
        this.mediaImage = mediaImage;
    }

    public TextView getMediaName() {
        return mediaName;
    }

    public TextView getMediaDesc() {
        return mediaDesc;
    }

    public CardView getContainer() {
        return container;
    }


    @Override
    public void onClick(View view) {

    }
}