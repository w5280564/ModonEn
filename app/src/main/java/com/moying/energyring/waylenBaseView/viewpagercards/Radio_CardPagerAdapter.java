package com.moying.energyring.waylenBaseView.viewpagercards;

import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;

import java.util.ArrayList;
import java.util.List;

public class Radio_CardPagerAdapter extends PagerAdapter implements Radio_CardAdapter {

    private List<CardView> mViews;
    private List<Radio_CardItem> mData;
    private float mBaseElevation;

    public Radio_CardPagerAdapter() {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public void addCardItem(Radio_CardItem item) {
        mViews.add(null);
        mData.add(item);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.radio_cardpageradapter, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);
        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(Radio_CardItem item, View view) {
        SimpleDraweeView content_simple = (SimpleDraweeView) view.findViewById(R.id.content_simple);
        StaticData.ViewScale(content_simple,638,628);
        Uri  uri = Uri.parse(item.getImgUri());
        content_simple.setImageURI(uri);
//        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
//        TextView contentTextView = (TextView) view.findViewById(R.id.contentTextView);
//        titleTextView.setText(item.getTitle()+"");
//        contentTextView.setText(item.getText()+"");
    }

}
