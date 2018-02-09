package com.moying.energyring.myAcativity.Find.radioInfo;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by waylen on 2017/6/9.
 */

public class LocalRadioInfo {
    SharedPreferences share = null; //本地存储类

    public LocalRadioInfo(Context context) {
        share = context.getSharedPreferences("Radio_Mode", Context.MODE_PRIVATE);
    }

    //读取本地数据
    public radio_Model getUserInfo() {
        radio_Model radioInfo = new radio_Model();
        radioInfo.setRadioName(share.getString("radioName","false"));
        radioInfo.setRadioImgpath(share.getString("radioImgpath","false"));
        radioInfo.setRadioUrl(share.getString("radioUrl","false"));
        radioInfo.setRadioisPlay(share.getInt("radioisPlay",0));
        radioInfo.setRadioStopColock(share.getString("radioStopColock","false"));
        return radioInfo;
    }

    //存储本地数据
    public void setUserInfo(radio_Model Model) {
        SharedPreferences.Editor editor = share.edit();
        editor.putString("radioName",Model.getRadioName());
        editor.putString("radioImgpath",Model.getRadioImgpath());
        editor.putString("radioUrl",Model.getRadioUrl());
        editor.putInt("radioisPlay",Model.getRadioisPlay());
        editor.putString("radioStopColock",Model.getRadioStopColock());
        editor.commit();
    }


    public void setPlatState(int state){
        SharedPreferences.Editor editor = share.edit();

        editor.putInt("radioisPlay",state);
        editor.commit();
    }

}