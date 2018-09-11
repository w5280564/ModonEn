package com.moying.energyring.myAcativity.Energy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanjay.selectorphotolibrary.utils.PermissionUtils;
import com.google.gson.Gson;
import com.moying.energyring.Model.Friend_InviteAll_Model;
import com.moying.energyring.Model.Friend_Invite_Model;
import com.moying.energyring.Model.MemberEntity;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.GetPhoneContact;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.groupadapter.BaseViewHolder;
import com.moying.energyring.waylenBaseView.groupadapter.Friend_GroupedListAdapter;
import com.moying.energyring.waylenBaseView.groupadapter.GroupedRecyclerViewAdapter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Energy_CommunityFriend_Invite extends Activity {

    private EditText seek_Edit;
    private List<MemberEntity> memberList;
    private RecyclerView rv_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.energy_communityfriend_invate);


        initTitle();
        initView();

   /*申请读取存储的权限*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionUtils.requestPermission(this, PermissionUtils.CODE_GET_ACCOUNTS, permissionGrant);
        } else {
            memberList = GetPhoneContact.queryContact(Energy_CommunityFriend_Invite.this);
            initData();
        }


    }


    private PermissionUtils.PermissionGrant permissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_GET_ACCOUNTS:
//                Toast.makeText(ImagePickerActivity.this, "读取存储权限已打开", Toast.LENGTH_SHORT).show();
                    memberList = GetPhoneContact.queryContact(Energy_CommunityFriend_Invite.this);
                    initData();
                    break;
            }
        }
    };

    /*申请权限的回调*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, permissionGrant);
    }

    private void initTitle() {
        View title_Include = (View) findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(ContextCompat.getColor(this, R.color.colorFristWhite));
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(ContextCompat.getColor(this, R.color.colorFristBlack));
        cententtxt.setText("通讯录");
        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(title_Include, 0, 88);

        return_Btn.setOnClickListener(new return_Btn());
    }

    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    private void initView() {
        LinearLayout seek_Lin = (LinearLayout) findViewById(R.id.seek_Lin);
        seek_Edit = (EditText) findViewById(R.id.seek_Edit);
        View seek_Img = findViewById(R.id.seek_Img);
        rv_list = (RecyclerView) findViewById(R.id.rv_list);

        StaticData.ViewScale(seek_Lin, 0, 60);
        StaticData.ViewScale(seek_Img, 22, 24);

        seek_Edit.addTextChangedListener(new seek_Edit());
    }

    private class seek_Edit implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (StaticData.Stringspace(editable.toString())) {
                initlist(Energy_CommunityFriend_Invite.this, rv_list, baseModel);
            } else {
                initlist(Energy_CommunityFriend_Invite.this, rv_list, search(editable.toString(), baseModel));
            }
        }
    }

    //搜索
    public Friend_InviteAll_Model search(String name, Friend_InviteAll_Model list) {
        Friend_InviteAll_Model Data = new Friend_InviteAll_Model();
        List<Friend_InviteAll_Model.DataBean> SysDataList = new ArrayList<>();
        List<Friend_InviteAll_Model.DataBean.ProjectListBean> SysData_ProjectList = new ArrayList();

        Pattern pattern = Pattern.compile(name);

//        for (int i = 0; i < list.getData().size(); i++) {
//            if (SysData_ProjectList != null) {
//                SysData_ProjectList.clear();
//            }
//            Friend_InviteAll_Model.DataBean SysData = new Friend_InviteAll_Model.DataBean();
//            SysData.ProjectTypeName = list.getData().get(i).getProjectTypeName();
//
//            for (int k = 0; k < list.getData().get(i).get_Project_List().size(); k++) {
////                Friend_InviteAll_Model.DataBean.ProjectListBean Projectdata = new Friend_InviteAll_Model.DataBean.ProjectListBean();
//                String SysName = list.getData().get(i).get_Project_List().get(k).getNickName().toString();
//                Matcher matcher = pattern.matcher(SysName);
//                if (matcher.find()) {//模糊查询
//                    SysData_ProjectList.add(list.getData().get(i).get_Project_List().get(k));
//                }
//            }
//
//            SysData.set_Project_List(SysData_ProjectList);
//            SysDataList.add(SysData);
//        }
//        Data.setData(SysDataList);
//        return Data;

        List<Friend_InviteAll_Model.DataBean.ProjectListBean> NotSysData_ProjectList = new ArrayList();


        for (int i = 0; i < list.getData().size(); i++) {
            Friend_InviteAll_Model.DataBean SysData = new Friend_InviteAll_Model.DataBean();
            if (i == 0) {
                SysData.ProjectTypeName = list.getData().get(i).getProjectTypeName();
                for (int k = 0; k < list.getData().get(i).get_Project_List().size(); k++) {
//                Friend_InviteAll_Model.DataBean.ProjectListBean Projectdata = new Friend_InviteAll_Model.DataBean.ProjectListBean();
                    String SysName = list.getData().get(i).get_Project_List().get(k).getNickName().toString();
                    Matcher matcher = pattern.matcher(SysName);
                    if (matcher.find()) {//模糊查询
                        SysData_ProjectList.add(list.getData().get(i).get_Project_List().get(k));
                    }
                }
                SysData.set_Project_List(SysData_ProjectList);
                SysDataList.add(SysData);


            } else {
                SysData.ProjectTypeName = list.getData().get(i).getProjectTypeName();
                for (int k = 0; k < list.getData().get(i).get_Project_List().size(); k++) {
//                Friend_InviteAll_Model.DataBean.ProjectListBean Projectdata = new Friend_InviteAll_Model.DataBean.ProjectListBean();
                    String SysName = list.getData().get(i).get_Project_List().get(k).getNickName().toString();
                    Matcher matcher = pattern.matcher(SysName);
                    if (matcher.find()) {//模糊查询
                        NotSysData_ProjectList.add(list.getData().get(i).get_Project_List().get(k));
                    }
                }
                SysData.set_Project_List(NotSysData_ProjectList);
                SysDataList.add(SysData);

            }

        }
        Data.setData(SysDataList);
        return Data;


//        List results = new ArrayList();
//        Pattern pattern = Pattern.compile(name);
//        for (int i = 0; i < list.size(); i++) {
//            Matcher matcher = pattern.matcher(((Employee) list.get(i)).getName());
//            if (matcher.find()) {
//                results.add(list.get(i));
//            }
//        }
//        return results;
    }


    private void initData() {

        StringBuffer stringBuffer = null;
        if (!memberList.isEmpty()) {
            stringBuffer = new StringBuffer();
            for (int i = 0; i < memberList.size(); i++) {
                stringBuffer.append(memberList.get(i).getPhone().replaceAll("\\s*", "") + ",");
            }
            AddressData(this, saveFile.BaseUrl + saveFile.AddressBook_List_Url + "?PhoneNums=" + stringBuffer.toString());
        }
    }

    //加载弹框pk项目
    public void initlist(final Context context, RecyclerView rv_List, Friend_InviteAll_Model baseModel) {
        Friend_GroupedListAdapter adapter = new Friend_GroupedListAdapter(context, baseModel);

        adapter.setOnHeaderClickListener(new GroupedRecyclerViewAdapter.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder, int groupPosition) {
            }
        });
        adapter.setOnFooterClickListener(new GroupedRecyclerViewAdapter.OnFooterClickListener() {
            @Override
            public void onFooterClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder, int groupPosition) {
            }
        });
        adapter.setOnChildClickListener(new GroupedRecyclerViewAdapter.OnChildClickListener() {
            @Override
            public void onChildClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder,
                                     int groupPosition, int childPosition) {
//                popupWindow.dismiss();
//                String childprojectId =   project_Infication_Model.getData().get(groupPosition).get_Project_List().get(childPosition).getProjectID()+"";
//                int size = baseModel.getData().size();
//                for (int i = 0; i < size; i++) {
//                    if (childprojectId.equals(baseModel.getData().get(i).getProjectID() + "")) {
//                        setpkbg(i);
//                        break;
//                    }
//                }
//                // 实例化汉字转拼音类
//                String ProjectName = project_Infication_Model.getData().get(groupPosition).get_Project_List().get(childPosition).getProjectName();
//                CharacterParser characterParser = CharacterParser.getInstance();
//                String pinyin = characterParser.getSelling(ProjectName);
//                MobclickAgent.onEvent(Pk_DayPk_Project_Detail_RankAll.this, pinyin);//统计页签

//                int tag = (Integer) v.getTag();
//                setpkbg(tag);
            }
        });
        adapter.hasFooter(0);//不显示组尾
        rv_List.setAdapter(adapter);

        //直接使用GroupedGridLayoutManager实现子项的Grid效果
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
//        GroupedGridLayoutManager gridLayoutManager = new GroupedGridLayoutManager(this, 1, adapter);
        rv_List.setLayoutManager(manager);

    }


    public Friend_Invite_Model FriendModel;
    private Friend_InviteAll_Model baseModel;

    public void AddressData(final Context context, final String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    FriendModel = new Gson().fromJson(resultString, Friend_Invite_Model.class);
                    if (FriendModel.isIsSuccess()) {

                        baseModel = new Friend_InviteAll_Model();
                        List<Friend_InviteAll_Model.DataBean> Model = new ArrayList<>();

                        Friend_InviteAll_Model.DataBean InSysData = new Friend_InviteAll_Model.DataBean();
                        InSysData.setProjectTypeName("已加入能量圈");
                        List<Friend_InviteAll_Model.DataBean.ProjectListBean> InSysDataList = new ArrayList<>();
                        for (int i = 0; i < FriendModel.getData().getInSys().size(); i++) {
                            Friend_InviteAll_Model.DataBean.ProjectListBean data = new Friend_InviteAll_Model.DataBean.ProjectListBean();
//                            for (int k = 0; k < memberList.size(); k++) {
//                                String InSysNum = FriendModel.getData().getInSys().get(i).getPhoneNum();
//                                if (memberList.get(k).getPhone().equals(InSysNum)) {
//                                    data.setNickName(memberList.get(k).getUser_name());
//                                    break;
//                                }
//                            }

                            data.setPhoneNum(FriendModel.getData().getInSys().get(i).getPhoneNum());
                            data.setUserID(FriendModel.getData().getInSys().get(i).getUserID());
                            data.setNickName(FriendModel.getData().getInSys().get(i).getNickName());
                            data.setProfilePicture(FriendModel.getData().getInSys().get(i).getProfilePicture());
                            data.setIs_Friend(FriendModel.getData().getInSys().get(i).isIs_Friend());
                            data.setIs_Attention(FriendModel.getData().getInSys().get(i).isIs_Attention());
                            data.setIs_Attention_Me(FriendModel.getData().getInSys().get(i).isIs_Attention_Me());
                            data.setIntegralLevel(FriendModel.getData().getInSys().get(i).getIntegralLevel());
                            data.setProjectID(FriendModel.getData().getInSys().get(i).getProjectID());
                            data.setProjectName(FriendModel.getData().getInSys().get(i).getProjectName());
                            data.setReport_Days(FriendModel.getData().getInSys().get(i).getReport_Days());
                            InSysDataList.add(data);
                        }
                        InSysData.set_Project_List(InSysDataList);
                        Model.add(InSysData);

                        Friend_InviteAll_Model.DataBean NotInSysData = new Friend_InviteAll_Model.DataBean();
                        NotInSysData.setProjectTypeName("邀请加入能量圈");
                        List<Friend_InviteAll_Model.DataBean.ProjectListBean> NotInSysDataList = new ArrayList<>();
                        for (int i = 0; i < FriendModel.getData().getNotInSys().size(); i++) {
                            Friend_InviteAll_Model.DataBean.ProjectListBean data = new Friend_InviteAll_Model.DataBean.ProjectListBean();

                            for (int k = 0; k < memberList.size(); k++) {
                                String NotInSysNum = FriendModel.getData().getNotInSys().get(i).getPhoneNum();
                                if (memberList.get(k).getPhone().equals(NotInSysNum)) {
                                    data.setNickName(memberList.get(k).getUser_name());
                                    break;
                                }
                            }

                            data.setPhoneNum(FriendModel.getData().getNotInSys().get(i).getPhoneNum());
                            data.setUserID(FriendModel.getData().getNotInSys().get(i).getUserID());
                            data.setProfilePicture(FriendModel.getData().getNotInSys().get(i).getProfilePicture());
                            data.setIs_Friend(FriendModel.getData().getNotInSys().get(i).isIs_Friend());
                            data.setIs_Attention(FriendModel.getData().getNotInSys().get(i).isIs_Attention());
                            data.setIs_Attention_Me(FriendModel.getData().getNotInSys().get(i).isIs_Attention_Me());
                            data.setIntegralLevel(FriendModel.getData().getNotInSys().get(i).getIntegralLevel());
                            data.setProjectID(FriendModel.getData().getNotInSys().get(i).getProjectID());
                            data.setProjectName(FriendModel.getData().getNotInSys().get(i).getProjectName());
                            data.setReport_Days(FriendModel.getData().getNotInSys().get(i).getReport_Days());
                            NotInSysDataList.add(data);
                        }
                        NotInSysData.set_Project_List(NotInSysDataList);
                        Model.add(NotInSysData);
                        baseModel.setData(Model);

                        initlist(context, rv_list, baseModel);


                    } else {
                        Toast.makeText(context, "数据获取失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "数据获取失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")) {
                    Intent intent = new Intent(context, MainLogin.class);
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


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    v.clearFocus();
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }



}
