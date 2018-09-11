package com.moying.energyring.myAcativity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.moying.energyring.Model.Friend_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.pinyin.AssortView;
import com.moying.energyring.pinyin.CharacterParser;
import com.moying.energyring.pinyin.HeaderListView;
import com.moying.energyring.pinyin.PinyinComparator;
import com.moying.energyring.pinyin.SortAdapter;
import com.moying.energyring.pinyin.SortModel;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RemindListActivity extends Activity {

    private Button right_Btn;
    private LinearLayout choiceLin;
    private EditText seek_edit;
    private HorizontalScrollView choiceScro;
    int PageIndex;
    int PageSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_list);

        View title_Include = findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(ContextCompat.getColor(this,R.color.colorFristWhite));
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(0);
        return_Btn.setTextSize(14);
        return_Btn.setText("取消");
        return_Btn.setTextColor(ContextCompat.getColor(this,R.color.colorFristBlack));
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(ContextCompat.getColor(this,R.color.colorFristBlack));
        cententtxt.setText("提醒谁看");
        right_Btn = (Button) title_Include.findViewById(R.id.right_Btn);
        right_Btn.setVisibility(View.VISIBLE);
        right_Btn.setBackgroundResource(0);
        right_Btn.setTextSize(14);
        right_Btn.setText("确定");
        right_Btn.setTextColor(ContextCompat.getColor(this,R.color.colorFristBlack));
        StaticData.ViewScale(return_Btn, 120, 88);
        StaticData.ViewScale(title_Include, 0, 88);
        StaticData.ViewScale(right_Btn, 120, 88);

        choiceScro = (HorizontalScrollView) findViewById(R.id.choiceScro);
        choiceLin = (LinearLayout) findViewById(R.id.choiceLin);
        seek_edit = (EditText) findViewById(R.id.seek_edit);
        seek_edit.setOnEditorActionListener(new seek_edit());
        seek_edit.setOnKeyListener(new seekKey());

        return_Btn.setOnClickListener(new return_Btn());
        right_Btn.setOnClickListener(new right_Btn());

        List<SortModel> baseModel = (List<SortModel>) getIntent().getSerializableExtra("baseModel");
        choiceModel = baseModel;
        choiceLin_List(choiceModel);

        initData("");

    }

    private void initData(String SearchKey) {
        PageIndex = 1;
        PageSize = 100;
        FriendData(saveFile.BaseUrl + saveFile.My_Friend_Url + "?SearchKey=" + SearchKey + "&PageIndex=" + PageIndex + "&PageSize=" + PageSize);
    }

    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            setResult(2000);
            finish();
        }
    }

    public class right_Btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.putExtra("choiceModel", (Serializable) choiceModel);
            setResult(2000, intent);
            finish();
//            Intent intent = new Intent(RemindListActivity.this,ReleaseTestActivity.class);
//            setResult(intent);
        }
    }

    public class seek_edit implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
//            if (actionId == EditorInfo.IME_ACTION_SEARCH && !seek_edit.getText().toString().equals("")) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {//打开软键盘
                imm.hideSoftInputFromWindow(seek_edit.getWindowToken(), 0);
            }
//            FriendData(saveFile.BaseUrl + "/user/BothHeart" + "?SearchKey=" + seek_edit.getText().toString())
            initData(seek_edit.getText().toString());
//                seek_edit.setText("");
            return true;
//            } else {
//                Toast.makeText(RemindListActivity.this, "请输入姓名", Toast.LENGTH_SHORT).show();
//            }
//            return false;
        }
    }

    public static List<SortModel> choiceModel = new ArrayList<>();
    private HeaderListView friendList;
    private AssortView assort;
    private List<SortModel> ListModel;
    private SortAdapter hotadapter;
    private CharacterParser characterParser;//汉字转换成拼音的类
    private PinyinComparator pinyinComparator;//根据拼音来排列ListView里面的数据类

    private void initView() {
        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        assort = (AssortView) findViewById(R.id.assort);
        friendList = (HeaderListView) findViewById(R.id.friendList);

        if (friend_Model != null) {
            ListModel = filledData(friend_Model);
            // 根据a-z进行排序源数据
            Collections.sort(ListModel, pinyinComparator);
        }
        Map<Integer, Boolean> mcheckflag = new HashMap<>();
        for (int i = 0; i < ListModel.size(); i++) {//初始化
            mcheckflag.put(i, false);
        }
        if (choiceModel != null || choiceModel.size() > 0) {//有选择@人
            for (int k = 0; k < ListModel.size(); k++) {
                for (int i = 0; i < choiceModel.size(); i++) {
                    if (choiceModel.get(i).getId().equals(ListModel.get(k).getId())) {
                        mcheckflag.put(k, true);
                    }
                }
            }
        }
        choiceLin_List(choiceModel);//加载@人
        hotadapter = new SortAdapter(this, ListModel, mcheckflag);
        friendList.setAdapter(hotadapter);
        //创建HeaderView
        View HeaderView = getLayoutInflater().inflate(R.layout.item_header, friendList, false);
        friendList.setPinnedHeader(HeaderView);
        friendList.setOnScrollListener(hotadapter);

        // 设置右侧触摸监听
        assort.setOnTouchingLetterChangedListener(new AssortView.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = hotadapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    friendList.setSelection(position);
                }
            }
        });


        friendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SortAdapter.ViewHolder viewHolder = (SortAdapter.ViewHolder) view.getTag();
                if (choiceModel.size() < 10) {
                    viewHolder.choice_check.toggle();
                    hotadapter.mcheckflag.put(position, viewHolder.choice_check.isChecked());
                    if (viewHolder.choice_check.isChecked()) {
                        choiceModel.add(ListModel.get(position));
                    } else {
                        removeData(position);
                    }
                } else {
                    //人数大于10人
                    if (viewHolder.choice_check.isChecked()) {
                        viewHolder.choice_check.toggle();
                        hotadapter.mcheckflag.put(position, viewHolder.choice_check.isChecked());
                        removeData(position);

                    } else {
                        Toast.makeText(RemindListActivity.this, "最多选择10人", Toast.LENGTH_SHORT).show();
                    }

                }
                choiceLin_List(choiceModel);
//                Intent intent = new Intent();
//                intent.putExtra("result", mSortList.get(position).getName());
//                /*
//                 * 调用setResult回传值，在onActivityResult取值
//                 */
//                setResult(2000, intent);
//                finish();

            }
        });
    }

    //删除数据
    private void removeData(int pos) {
        for (int i = 0; i < choiceModel.size(); i++) {
            if (choiceModel.get(i).getId().equals(ListModel.get(pos).getId())) {
                choiceModel.remove(i);
            }
        }
    }

    /**
     * 为ListView填充数据
     *
     * @param
     * @return
     */
    List<SortModel> mSortList;

    private List<SortModel> filledData(Friend_Model list) {
        mSortList = new ArrayList<SortModel>();
        for (int i = 0; i < list.getData().size(); i++) {
            SortModel sortModel = new SortModel();
            sortModel.setId(list.getData().get(i).getUserID() + "");
            sortModel.setName(list.getData().get(i).getNickName());
            sortModel.setImgUrl(list.getData().get(i).getProfilePicture());
            // 汉字转换成拼音
            if (TextUtils.isEmpty(list.getData().get(i).getNickName())) {
                sortModel.setSortLetters("#");
            } else {
                String pinyin = characterParser.getSelling(list.getData().get(i).getNickName());
                String sortString = pinyin.substring(0, 1).toUpperCase();
                // 正则表达式，判断首字母是否是英文字母
                if (sortString.matches("[A-Z]")) {
                    sortModel.setSortLetters(sortString.toUpperCase());
                } else {
                    sortModel.setSortLetters("#");
                }
            }
            mSortList.add(sortModel);
        }
        return mSortList;
    }

    private Friend_Model friend_Model;

    public void FriendData(String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", this) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", this));
        }
//        params.addQueryStringParameter("userid", LoginSession.getSession().getUserInfo().getUse_id() + "");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    friend_Model = new Gson().fromJson(resultString, Friend_Model.class);
                    if (friend_Model.isIsSuccess()) {
//                        init();
                        initView();
                    } else {
                        Toast.makeText(RemindListActivity.this, "数据获取失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")) {
                    Intent intent = new Intent(RemindListActivity.this, MainLogin.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(CancelledException e) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

    public void choiceLin_List(final List<SortModel> choiceModel) {
        if (choiceLin != null) {
            choiceLin.removeAllViews();
        }
        List<SimpleDraweeView> mySimpleArr = new ArrayList<>();
        RadioGroup.LayoutParams choiceParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        choiceParams.gravity = Gravity.CENTER;
        choiceScro.setLayoutParams(choiceParams);
        int size = choiceModel.size();
        if (size >= 6) {
            StaticData.layoutParamsScale(choiceParams, 500, 0);
        }
        for (int i = 0; i < size; i++) {
            RadioGroup.LayoutParams itemParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            int pad = (int) (Float.parseFloat(saveFile.getShareData("scale", RemindListActivity.this)) * 10);
            itemParams.setMargins(pad, 0, pad, 0);
            StaticData.layoutParamsScale(itemParams, 60, 60);
            SimpleDraweeView mySimple = new SimpleDraweeView(RemindListActivity.this);
            RoundingParams roundingParams = RoundingParams.fromCornersRadius(7f);
            roundingParams.setRoundAsCircle(true);
            mySimple.getHierarchy().setRoundingParams(roundingParams);
            mySimple.getHierarchy().setPlaceholderImage(R.drawable.loading_icon);

            if (choiceModel.get(i).getImgUrl() != null) {
                Uri imgUri = Uri.parse(choiceModel.get(i).getImgUrl());
                mySimple.setImageURI(imgUri);
            }
            mySimple.setLayoutParams(itemParams);
            mySimple.setTag(i);
            mySimpleArr.add(mySimple);
            choiceLin.addView(mySimple);

            mySimple.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    mySimple.setFocusable(false);
                    int tag = (Integer) v.getTag();
//                    List<String> myArr = new ArrayList<>();
////                    myArr.addAll(myBean.get(myposition).getArtPic());
//                    for (int i = 0;i<myBean.size();i++){
//                        myArr.add(myBean.get(i).getA_PICURL());
//                    }
//                    Intent i = new Intent(context, ImagePagerActivity.class);
////                    i.putExtra("imgArr", (Parcelable) myBean);
//                    i.putExtra("imgArr", (Serializable) myArr);
//                    i.putExtra("tag", tag);
//                    context.startActivity(i);
//                    mySimple.setFocusable(true);
                    if (choiceModel.size() != 0) {
                        removeChoiceData(tag);
                    }
                }
            });
        }
    }


    //软键盘删除按钮
    public class seekKey implements View.OnKeyListener {
        @Override
        public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
            if (seek_edit.getText().length() > 0) {
                seek_edit.getText().toString().substring(seek_edit.getText().length());

            } else {
                if (keyCode == KeyEvent.KEYCODE_DEL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if (choiceModel.size() != 0) {
                        removeChoiceData(choiceModel.size() - 1);
                    }
//                    return true;
                }
            }

            return false;
        }
    }

    //删除@人并刷新列表
    private void removeChoiceData(int pos) {
        String choiceid = choiceModel.get(pos).getId();
        for (int i = 0; i < ListModel.size(); i++) {
            if (ListModel.get(i).getId().equals(choiceid)) {
                hotadapter.mcheckflag.put(i, false);
            }
        }
        hotadapter.notifyDataSetChanged();
        choiceModel.remove(pos);
        choiceLin_List(choiceModel);//刷新
    }


}
