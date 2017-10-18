package com.today.step.lib.Static;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

@SuppressLint({"WorldReadableFiles", "WorldWriteableFiles"})
public class saveFile {


    private static SharedPreferences mShared = null;
    /**
     * 程序中可以同时存在多个SharedPreferences数据， 根据SharedPreferences的名称就可以拿到对象
     **/
    public final static String SHARED_MAIN = "main";
    /**
     * SharedPreferences中储存数据的Key名称
     **/
    public final static String KEY_NAME = "name";
    public final static String KEY_NUMBER = "number";

    /**
     * SharedPreferences中储存数据的路径
     **/
    public final static String DATA_URL = "/data/test/";
    public final static String SHARED_MAIN_XML = "test.xml";

    public static void saveShareData(String keyname, String key, Context context) {
        mShared = context.getSharedPreferences(SHARED_MAIN, Context.MODE_PRIVATE);
        Editor editor = mShared.edit();
        editor.putString(keyname, key);
        /**put完毕必需要commit()否则无法保存**/
        editor.commit();
    }

    public static String getShareData(String keyname, Context context) {
        mShared = context.getSharedPreferences(SHARED_MAIN, Context.MODE_PRIVATE);
        String name = mShared.getString(keyname, "false");
        return name;
    }

    public static void clearShareData(String keyname, Context context) {
        mShared = context.getSharedPreferences(SHARED_MAIN, Context.MODE_PRIVATE);
        Editor editor = mShared.edit();
        editor.remove(keyname);
        editor.commit();
    }


}
