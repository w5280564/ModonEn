package com.moying.energyring.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@SuppressLint({"WorldReadableFiles", "WorldWriteableFiles"})
public class saveFile {
//    public static String BaseUrl = "http://www.ec.dev.com";
    public static String BaseUrl = "http://192.168.1.111/";
    public static String CodeUrl = "ec/Account/PhoneCode_Get";
    public static String LoginUrl = "ec/Account/Login";
    public static String EnergyListUrl = "ec/Post/Post_List";


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


    public static void savelistData(List<Map<Integer, Boolean>> listA, Context context) {
        Editor editor = context.getSharedPreferences("listselect", Context.MODE_WORLD_WRITEABLE).edit();
        editor.clear();
        List<Map<Integer, Boolean>> list = listA;
        editor.putInt("size", list.size());
        for (int i = 0; i < list.size(); i++) {
            editor.remove("isexist" + i);
            editor.putString("isexist" + i, list.get(i).get(i).toString());
        }
        editor.commit();
    }

    public static ArrayList<Map<Integer, Boolean>> getlistData(Context context) {
        SharedPreferences sp = context.getSharedPreferences("listselect", Context.MODE_WORLD_READABLE);
        int size = sp.getInt("size", 0);
        ArrayList<Map<Integer, Boolean>> list2 = new ArrayList<Map<Integer, Boolean>>();
        if (size == 0) {
            list2 = new ArrayList<Map<Integer, Boolean>>();
        } else {
            for (int j = 0; j < size; j++) {
                Map<Integer, Boolean> map = new HashMap<Integer, Boolean>();
                map.put(j, Boolean.valueOf(sp.getString("isexist" + j, "")));
                list2.add(map);
            }
        }
        return list2;
    }

    //保存List历史
    public static void locList(Context context, String KEY_NAME, String content, List<String> baseList) {
        baseList.add(content);
        SharedPreferences sp = context.getSharedPreferences("locArr", Context.MODE_WORLD_READABLE);
        Editor editor = sp.edit();
        editor.putString(KEY_NAME, baseList.toString());
        editor.commit();
    }

    public static List<String> getlocList(Context context, String KEY_NAME) {
        List<String> baseList = new ArrayList<>();
        SharedPreferences sp = context.getSharedPreferences("locArr", Context.MODE_PRIVATE);
        String result = sp.getString(KEY_NAME, "");
        try {
            JSONArray array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                baseList.add(0, array.getString(i));
            }
        } catch (JSONException e) {
        }
        return baseList;
    }


    public static void saveInfo(Context context, String key, List<Map<String, String>> datas) {
        JSONArray mJsonArray = new JSONArray();
        for (int i = 0; i < datas.size(); i++) {
            Map<String, String> itemMap = datas.get(i);
            Iterator<Map.Entry<String, String>> iterator = itemMap.entrySet().iterator();
            JSONObject object = new JSONObject();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                try {
                    object.put(entry.getKey(), entry.getValue());
                } catch (JSONException e) {

                }
            }
            mJsonArray.put(object);
        }
        SharedPreferences sp = context.getSharedPreferences("finals", Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putString(key, mJsonArray.toString());
        editor.commit();
    }

    public static List<Map<String, String>> getInfo(Context context, String key) {
        List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
        SharedPreferences sp = context.getSharedPreferences("finals", Context.MODE_PRIVATE);
        String result = sp.getString(key, "");
        try {
            JSONArray array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                JSONObject itemObject = array.getJSONObject(i);
                Map<String, String> itemMap = new HashMap<String, String>();
                JSONArray names = itemObject.names();
                if (names != null) {
                    for (int j = 0; j < names.length(); j++) {
                        String name = names.getString(j);
                        String value = itemObject.getString(name);
                        itemMap.put(name, value);
                    }
                }
                datas.add(itemMap);
            }
        } catch (JSONException e) {
        }
        return datas;
    }


    //存List<String>
    public static void saveUserList(Context context, List<String> baseArr, String KEY_NAME) {
//        baseArr = new ArrayList<>(new HashSet<>(baseArr));
        Editor editor = context.getSharedPreferences(KEY_NAME, Context.MODE_WORLD_WRITEABLE).edit();
        editor.clear();
        editor.putInt("size", baseArr.size());
        for (int i = 0; i < baseArr.size(); i++) {
            editor.remove("seekname" + i);
            editor.putString("seekname" + i, baseArr.get(i));
        }
        editor.commit();
    }

    //取List<String>
    public static List<String> getUserList(Context context, String KEY_NAME) {
        List<String> baseArr = null;
        if (baseArr != null) {
            baseArr.clear();
        }
        baseArr = new ArrayList<>();
        SharedPreferences sp = context.getSharedPreferences(KEY_NAME, Context.MODE_WORLD_READABLE);
        int size = sp.getInt("size", 0);
        for (int i = 0; i < size; i++) {
            baseArr.add(sp.getString("seekname" + i, null));
        }
//        baseArr = new ArrayList<String>(new HashSet<String>(baseArr));
        return baseArr;
    }

    //删除一条订阅
    public static void removeUserOne(Context context, String KEY_NAME, String valen_Name) {
        SharedPreferences sp = context.getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        int size = sp.getInt("size", 0);
        List<String> baseArr = getUserList(context, "listselect");//
        for (int i = 0; i < size; i++) {//删除
            if (baseArr.get(i).equals(valen_Name)) {
                baseArr.remove(i);
//                size = size-1;//删除一个数据 size-1
                break;
            }
        }
        saveUserList(context, baseArr, "listselect");//保存
        editor.commit();
    }

    //添加一条订阅
    public static void addUserone(Context context, String KEY_NAME, String valen_Name) {
        SharedPreferences sp = context.getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        List<String> baseArr = getUserList(context, "listselect");
        baseArr.add(valen_Name);
        saveUserList(context, baseArr, "listselect");//保存
        editor.commit();
    }





    //保存map
    public static String SceneList2String(HashMap<Integer, Boolean> hashmap)
            throws IOException {
        // 实例化一个ByteArrayOutputStream对象，用来装载压缩后的字节文件。
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 然后将得到的字符数据装载到ObjectOutputStream
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        // writeObject 方法负责写入特定类的对象的状态，以便相应的 readObject 方法可以还原它
        objectOutputStream.writeObject(hashmap);
        // 最后，用Base64.encode将字节文件转换成Base64编码保存在String中
        String SceneListString = new String(Base64.encode(
                byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
        // 关闭objectOutputStream
        objectOutputStream.close();
        return SceneListString;
    }

    @SuppressWarnings("unchecked")
    public static HashMap<Integer, Boolean> String2SceneList(
            String SceneListString) throws StreamCorruptedException,
            IOException, ClassNotFoundException {
        byte[] mobileBytes = Base64.decode(SceneListString.getBytes(),
                Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                mobileBytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        HashMap<Integer, Boolean> SceneList = (HashMap<Integer, Boolean>) objectInputStream
                .readObject();
        objectInputStream.close();
        return SceneList;
    }


    public static boolean putHashMap(HashMap<Integer, Boolean> hashmap, String key, Context context) {
        SharedPreferences settings = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        Editor editor = settings.edit();
        try {
            String liststr = SceneList2String(hashmap);
            editor.putString(key, liststr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return editor.commit();
    }


    public static HashMap<Integer, Boolean> getHashMap(String key, Context context) {
        SharedPreferences settings = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        String liststr = settings.getString(key, "");
        try {
            return String2SceneList(liststr);
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * true在今天之前flase在之后
     *
     * @param str
     * @return
     */
    public static Boolean getBig(String str) {
        Boolean flag = null;
        try {
            Date nowdate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            Date d = sdf.parse(str);
            if (d == nowdate) {
                flag = true;//日期相同
            } else {
                flag = d.before(nowdate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 根据用户传入的时间表示格式，返回当前时间的格式 如果是MMdd，注意字母y不能大写。
     * @param sformat MMdd
     * @return
     */
    public static String getUserDate(String sformat) {
        String dateString = "2017-03-28";
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(sformat);//先转固定格式
            dateString = new SimpleDateFormat("MM/dd").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateString;
    }





}
