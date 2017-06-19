package com.moying.energyring.waylenBaseView.viewpagercards;


import android.support.v7.widget.CardView;

public interface Radio_CardAdapter {

    int MAX_ELEVATION_FACTOR = 8;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();
}
