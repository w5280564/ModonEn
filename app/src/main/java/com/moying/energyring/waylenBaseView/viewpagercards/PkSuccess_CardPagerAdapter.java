package com.moying.energyring.waylenBaseView.viewpagercards;

import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;

import java.util.ArrayList;
import java.util.List;

public class PkSuccess_CardPagerAdapter extends PagerAdapter implements PkSuccess_CardAdapter{

    private List<CardView> mViews;
    private List<PkSuccess_CardItem> mData;
    private float mBaseElevation;

    public PkSuccess_CardPagerAdapter() {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public void addCardItem(PkSuccess_CardItem item) {
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
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.pksuccess_cardpageradapter, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);


        //阴影效果
//        if (mBaseElevation == 0) {
//            mBaseElevation = cardView.getCardElevation();
//        }
//        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(PkSuccess_CardItem item, View view) {
        RelativeLayout card_Rel = (RelativeLayout) view.findViewById(R.id.card_Rel);
        StaticData.ViewScale(card_Rel,354,492);//413
        SimpleDraweeView content_simple = (SimpleDraweeView) view.findViewById(R.id.content_simple);
        TextView project_Txt = (TextView) view.findViewById(R.id.project_Txt);
        TextView lei_Txt = (TextView) view.findViewById(R.id.lei_Txt);
        ImageView success_lei_icon = (ImageView) view.findViewById(R.id.success_lei_icon);
        TextView today_Txt = (TextView) view.findViewById(R.id.today_Txt);
        ImageView success_today_icon = (ImageView) view.findViewById(R.id.success_today_icon);
        StaticData.ViewScale(content_simple,190,190);
        StaticData.ViewScale(success_lei_icon,54,54);
        StaticData.ViewScale(success_today_icon,54,54);
        Uri  uri = Uri.parse(item.getImgUri());
        content_simple.setImageURI(uri);
        project_Txt.setText(item.getProjectName());

        lei_Txt.setText("今日累计："+item.getLeiName()+item.getProjectUilt());
        today_Txt.setText("今日排名："+item.getTodayName());
    }

}
