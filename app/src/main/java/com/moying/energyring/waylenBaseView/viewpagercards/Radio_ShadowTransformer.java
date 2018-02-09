package com.moying.energyring.waylenBaseView.viewpagercards;

import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.moying.energyring.Model.RadioList_Model;
import com.moying.energyring.StaticData.myMediaplayer;
import com.moying.energyring.myAcativity.Find.FindRadioListActivity;
import com.moying.energyring.myAcativity.Find.radioInfo.LocalRadioInfo;
import com.moying.energyring.myAcativity.Find.radioInfo.radio_Model;


public class Radio_ShadowTransformer implements ViewPager.OnPageChangeListener, ViewPager.PageTransformer {

    private myMediaplayer myplayer;
    private ViewPager mViewPager;
    private Radio_CardAdapter mAdapter;
    private float mLastOffset;
    private boolean mScalingEnabled;
    private SimpleDraweeView radio_bg;
    RadioList_Model radioModel;
    TextView radio_Name_Txt;
    private FindRadioListActivity context;

    public Radio_ShadowTransformer(FindRadioListActivity context, RadioList_Model radioModel, ViewPager viewPager, Radio_CardAdapter adapter, SimpleDraweeView radio_bg, TextView radio_Name_Txt) {
        mViewPager = viewPager;
        viewPager.addOnPageChangeListener(this);
        mAdapter = adapter;
        this.radio_bg = radio_bg;
        this.radioModel = radioModel;
        this.radio_Name_Txt = radio_Name_Txt;
        myplayer = myMediaplayer.getInstance();
        this.context = context;

    }

    public void enableScaling(boolean enable) {
        if (mScalingEnabled && !enable) {
            // shrink main card
            CardView currentCard = mAdapter.getCardViewAt(mViewPager.getCurrentItem());
            if (currentCard != null) {
                currentCard.animate().scaleY(1);
                currentCard.animate().scaleX(1);
            }
        } else if (!mScalingEnabled && enable) {
            // grow main card
            CardView currentCard = mAdapter.getCardViewAt(mViewPager.getCurrentItem());
            if (currentCard != null) {
                currentCard.animate().scaleY(1.1f);
                currentCard.animate().scaleX(1.1f);
            }
        }

        mScalingEnabled = enable;
    }

    @Override
    public void transformPage(View page, float position) {

    }

    int position;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int realCurrentPosition;
        this.position = position;
        int nextPosition;
        float baseElevation = mAdapter.getBaseElevation();
        float realOffset;
        boolean goingLeft = mLastOffset > positionOffset;

        // If we're going backwards, onPageScrolled receives the last position
        // instead of the current one
        if (goingLeft) {
            realCurrentPosition = position + 1;
            nextPosition = position;
            realOffset = 1 - positionOffset;
        } else {
            nextPosition = position + 1;
            realCurrentPosition = position;
            realOffset = positionOffset;
        }

        // Avoid crash on overscroll
        if (nextPosition > mAdapter.getCount() - 1
                || realCurrentPosition > mAdapter.getCount() - 1) {
            return;
        }

        CardView currentCard = mAdapter.getCardViewAt(realCurrentPosition);

        // This might be null if a fragment is being used
        // and the views weren't created yet
        if (currentCard != null) {
            if (mScalingEnabled) {
                currentCard.setScaleX((float) (1 + 0.1 * (1 - realOffset)));
                currentCard.setScaleY((float) (1 + 0.1 * (1 - realOffset)));
            }
            currentCard.setCardElevation((baseElevation + baseElevation
                    * (Radio_CardAdapter.MAX_ELEVATION_FACTOR - 1) * (1 - realOffset)));
        }

        CardView nextCard = mAdapter.getCardViewAt(nextPosition);

        // We might be scrolling fast enough so that the next (or previous) card
        // was already destroyed or a fragment might not have been created yet
        if (nextCard != null) {
            if (mScalingEnabled) {
                nextCard.setScaleX((float) (1 + 0.1 * (realOffset)));
                nextCard.setScaleY((float) (1 + 0.1 * (realOffset)));
            }
            nextCard.setCardElevation((baseElevation + baseElevation
                    * (Radio_CardAdapter.MAX_ELEVATION_FACTOR - 1) * (realOffset)));
        }

        mLastOffset = positionOffset;
    }

    @Override
    public void onPageSelected(int position) {
        RadioList_Model.DataBean oneData = radioModel.getData().get(position);
        if (oneData.getRadio_Bg_Dim() != null) {
            Uri uri = Uri.parse(oneData.getRadio_Bg_Dim());
            radio_bg.setImageURI(uri);
        }
        radio_Name_Txt.setText(oneData.getRadioName());
//        if (myplayer.mediaPlayer == null){
//            context.radioPlayUrl(position);
//        }else
            if (myplayer.mediaPlayer != null && myplayer.mediaPlayer.isPlaying()) {//对象不是空 并且在播放
//            isplayRadio(position);
//           AddOneLocaInfo(oneData, 2);//存储数据
            context.radioPlaySlide(position);
        } else {
            context.radioStop(oneData);
//           AddOneLocaInfo(oneData, 0);
        }
//        saveFile.saveShareData("englishVideo","0",context);
//        context.startDownTimer();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    public void isplayRadio(int pos) { //正在播放需要停止后重新加载
        RadioList_Model.DataBean oneData = radioModel.getData().get(pos);
        myplayer.stop();
        myplayer.playUrl(oneData.getRadioUrl(), context.radio_list_play);
    }

    public void AddOneLocaInfo(RadioList_Model.DataBean onedata, int state) {
        LocalRadioInfo radioInfo = new LocalRadioInfo(context);
        radio_Model model = new radio_Model();
        model.setRadioName(onedata.getRadioName());
        model.setRadioImgpath(onedata.getRadio_Icon());
        model.setRadioUrl(onedata.getRadioUrl());
        model.setRadioisPlay(state);
        radioInfo.setUserInfo(model);
    }


}
