package com.moying.energyring.myAcativity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanjay.selectorphotolibrary.SelectedPhotoActivity;
import com.example.sanjay.selectorphotolibrary.bean.ImgOptions;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.gson.Gson;
import com.mob.tools.utils.UIHandler;
import com.moying.energyring.Model.AddPhoto_Model;
import com.moying.energyring.Model.BaseDataInt_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.NoDoubleClickListener;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.database.ChildInfo;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.pinyin.SortModel;
import com.moying.energyring.waylenBaseView.FlowLayout;
import com.moying.energyring.waylenBaseView.MyActivityManager;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.db.table.TableEntity;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnMultiCompressListener;

public class PostingActivity extends Activity implements PlatformActionListener, Handler.Callback {

    private FlowLayout photoLayout;
    private ImageButton add_photo_Img;
    private LinearLayout choice_Lin;
    List<SortModel> choiceModel;
    List<Integer> choiceId;
    private EditText content_Edit;
    private Button right_Btn;
    private ImageView share_friend, share_mom, share_sina, share_qq, share_qzone, energy_img;
    private Map<Integer, Boolean> shareFlag;
    private LinearLayout hero_Lin;
    private TextView hero_Txt;
    int postType;
    private boolean isrole;
    private String dbId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting);
        MyActivityManager mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中

        initTitle();
        initView();
        initView();
        initData();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("PostingActivity"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);          //统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("PostingActivity"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }


    private void initTitle() {
        View title_Include = findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(Color.parseColor("#ffffff"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            title_Include.setElevation(2f);//阴影
        }
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundColor(Color.parseColor("#00000000"));
        return_Btn.setVisibility(View.VISIBLE);
        return_Btn.setTextColor(Color.parseColor("#000000"));
        return_Btn.setText("取消");
        TextView cententTxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententTxt.setTextColor(Color.parseColor("#000000"));
        cententTxt.setText("成长日志");
        right_Btn = (Button) title_Include.findViewById(R.id.right_Btn);
        right_Btn.setVisibility(View.VISIBLE);
        right_Btn.setTextColor(Color.parseColor("#000000"));
        right_Btn.setText("发布");
        StaticData.ViewScale(return_Btn, 100, 100);
        StaticData.ViewScale(title_Include, 0, 88);

        return_Btn.setOnClickListener(new return_Btn());
        right_Btn.setOnClickListener(new right_Btn());
    }

    private void initData() {
        photoPaths = new ArrayList<>();
        choiceModel = new ArrayList<>();
        choiceId = new ArrayList<>();
        setShareFlag();
        postType = 1;
        if (!saveFile.getShareData("role", PostingActivity.this).equals("false")) {
            int role = Integer.parseInt(saveFile.getShareData("role", PostingActivity.this));
            if (role < 3) {
                isrole = false;
                hero_Lin.setVisibility(View.VISIBLE);
                hero_Txt.setTextColor(Color.parseColor("#b9b9b9"));
            }
        }
        initDb();//初始化数据库
        dbId = getIntent().getStringExtra("dbId");
        if (dbId != null) {
            dbFind(dbId);//数据库
        }

    }

    private save_Popup savePop;

    private class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (!StaticData.isSpace(content_Edit.getText().toString()) || photoPaths.size() > 0) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);//关闭键盘
                imm.hideSoftInputFromWindow(content_Edit.getWindowToken(), 0);
                savePop = new save_Popup(PostingActivity.this, right_Btn);
            } else {
                finish();
            }
        }
    }


    private class right_Btn extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            if (StaticData.isSpace(content_Edit.getText().toString())) {
                Toast.makeText(PostingActivity.this, "发帖内容不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            right_Btn.setEnabled(false);
            MobclickAgent.onEvent(PostingActivity.this, "releaseAdd");//统计页签
            if (photoPaths.size() == 0) {
                AddPost_Data(PostingActivity.this, saveFile.BaseUrl + saveFile.Post_Add_Url, "");
            } else {

                mFileList = new ArrayList<>();
                for (String photo : photoPaths) {
                    mFileList.add(new File(photo));
                }
                compressMultiListener(Luban.FIRST_GEAR);//压缩 THIRD_GEAR  普通压缩 FIRST_GEAR快速压缩

            }
        }
    }

    //压缩图片
    private List<File> mFileList;

    private void compressMultiListener(int gear) {
        if (mFileList.isEmpty()) {
            return;
        }
        Luban.compress(this, mFileList)
                .putGear(gear)
                .launch(new OnMultiCompressListener() {
                    @Override
                    public void onStart() {
//                        Log.i(TAG, "start");
                    }

                    @Override
                    public void onSuccess(List<File> filesList) {
                        int size = filesList.size();
//                        while (size-- > 0) {
//                            mImageViews.get(size).setImageURI(Uri.fromFile(fileList.get(size)));
//                        }
                        upload_PhotoData(PostingActivity.this, saveFile.BaseUrl + saveFile.uploadPhoto_Url, filesList);

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }


    private void initView() {
        RelativeLayout edit_Rel = (RelativeLayout) findViewById(R.id.edit_Rel);
        photoLayout = (FlowLayout) findViewById(R.id.photoLayout);
        add_photo_Img = (ImageButton) findViewById(R.id.add_photo_Img);
        ImageView arrow_img = (ImageView) findViewById(R.id.arrow_img);
        RelativeLayout remindRel = (RelativeLayout) findViewById(R.id.remindRel);
        choice_Lin = (LinearLayout) findViewById(R.id.choice_Lin);
        content_Edit = (EditText) findViewById(R.id.content_Edit);
        share_friend = (ImageView) findViewById(R.id.share_friend);
        share_mom = (ImageView) findViewById(R.id.share_mom);
        share_sina = (ImageView) findViewById(R.id.share_sina);
        share_qq = (ImageView) findViewById(R.id.share_qq);
        share_qzone = (ImageView) findViewById(R.id.share_qzone);
        hero_Lin = (LinearLayout) findViewById(R.id.hero_Lin);
        energy_img = (ImageView) findViewById(R.id.energy_img);
        hero_Txt = (TextView) findViewById(R.id.hero_Txt);

        StaticData.ViewScale(edit_Rel, 710, 1032);
        StaticData.ViewScale(arrow_img, 16, 30);
        StaticData.ViewScale(share_friend, 72, 72);
        StaticData.ViewScale(share_mom, 72, 72);
        StaticData.ViewScale(share_sina, 72, 72);
        StaticData.ViewScale(share_qq, 72, 72);
        StaticData.ViewScale(share_qzone, 72, 72);
        StaticData.ViewScale(energy_img, 40, 40);
//        StaticData.ViewScale(add_photo_Img, 82, 82);
        add_photo_Img.setOnClickListener(new add_photo_Img());
        remindRel.setOnClickListener(new remindRel());
        share_friend.setOnClickListener(new share_friend());
        share_mom.setOnClickListener(new share_mom());
        share_sina.setOnClickListener(new share_sina());
        share_qq.setOnClickListener(new share_qq());
        share_qzone.setOnClickListener(new share_qzone());
        hero_Lin.setOnClickListener(new hero_Lin());
    }


    public class remindRel implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(PostingActivity.this, RemindListActivity.class);
            intent.putExtra("baseModel", (Serializable) choiceModel);
            startActivityForResult(intent, 2000);
        }
    }

    private class hero_Lin implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (isrole) {
                isrole = false;
                hero_Txt.setTextColor(Color.parseColor("#b9b9b9"));
                postType = 1;
            } else {
                isrole = true;
                hero_Txt.setTextColor(Color.parseColor("#F3DB23"));
                postType = 4;
            }
        }
    }


    private List<String> photoPaths;
    private static final int REQUEST_IMAGE = 1000;
    private static final String EXTRA_DATA = "extra_data";

    private class add_photo_Img implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int choice = 9 - photoPaths.size();
            ImgOptions options = new ImgOptions(choice, 1, true);
            startActivityForResult(SelectedPhotoActivity.makeIntent(PostingActivity.this, options), REQUEST_IMAGE);
        }
    }

    private boolean popisShow = false;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        try {
            if (dbId != null) {
                return;
            }
            if (!popisShow) {
                popisShow = true;
                if (db.findAll(ChildInfo.class) != null && db.findAll(ChildInfo.class).size() > 0) {
                    new wei_Popup(PostingActivity.this, right_Btn);
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_IMAGE:
                if (resultCode == RESULT_OK) {
                    List<String> pathList = data.getStringArrayListExtra(EXTRA_DATA);
                    imgFlow(photoLayout, pathList);
                }
            case 2000:
                if (resultCode == 2000 && data != null) {
                    choiceModel = (List<SortModel>) data.getSerializableExtra("choiceModel");
                    choiceLin_List(choice_Lin, choiceModel);
                    choiceId = new ArrayList<>();
                    for (int i = 0; i < choiceModel.size(); i++) {
                        int Id = Integer.parseInt(choiceModel.get(i).getId());
                        choiceId.add(Id);
                    }
                }
        }
    }

    public void choiceLin_List(LinearLayout choiceLin, List<SortModel> choiceModel) {
        if (choiceLin != null) {
            choiceLin.removeAllViews();
        }
        List<SimpleDraweeView> mySimpleArr = new ArrayList<>();
        int size = choiceModel.size();
        for (int i = 0; i < size; i++) {
            RadioGroup.LayoutParams itemParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            int pad = (int) (Float.parseFloat(saveFile.getShareData("scale", this)) * 10);
            itemParams.setMargins(pad, 0, pad, 0);
            StaticData.layoutParamsScale(itemParams, 60, 60);
            SimpleDraweeView mySimple = new SimpleDraweeView(this);
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
        }
    }


    //图片选择器
    public void imgFlow(FlowLayout myFlow, final List<String> imgList) {
        int size = imgList.size();
        for (int i = 0; i < size; i++) {
            photoPaths.add(imgList.get(i));
            LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            StaticData.layoutParamsScale(itemParams, 140, 140);
            int pad = (int) (Float.parseFloat(saveFile.getShareData("scale", this)) * 10);
            itemParams.setMargins(pad, pad, 0, 0);
            final SimpleDraweeView mySimple = new SimpleDraweeView(this);
            add_photo_Img.setLayoutParams(itemParams);
            mySimple.setLayoutParams(itemParams);
            addSimplePath(mySimple, imgList.get(i));//多图修改尺寸

            mySimple.setTag(i);
//            mySimpleArr.add(mySimple);
            int position = photoLayout.getChildCount() - 1;//下标
            myFlow.addView(mySimple, position);
            mySimple.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    for (int i = 0; i < photoLayout.getChildCount(); i++) {
                        if (view == photoLayout.getChildAt(i)) {
                            deletePhotoByPosition(i);
                            break;
                        }
                    }
                    photoLayout.removeView(view);
                    return true;
                }
            });
            mySimple.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mySimple.setFocusable(false);
                    int tag = (Integer) v.getTag();
                    List<String> myArr = new ArrayList<>();
                    for (int i = 0; i < imgList.size(); i++) {
                        myArr.add(imgList.get(i));
                    }
                }
            });
        }
        ChangeAddPhotoImage();//检查增加图片是否显示
    }

    public void deletePhotoByPosition(int position) {
        photoPaths.remove(position);
        ChangeAddPhotoImage();
    }


    /**
     * 检查并切换增加图片的图标
     */
    private void ChangeAddPhotoImage() {
        if (photoPaths.size() >= 9) {
            add_photo_Img.setVisibility(View.GONE);
        } else {
            add_photo_Img.setVisibility(View.VISIBLE);
        }
    }

    private void addSimplePath(SimpleDraweeView simple, String path) {
        Uri uri = Uri.fromFile(new File(path));
        int width = 50, height = 50;
        ImageRequest request = ImageRequestBuilder
                .newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(width, height))
                .build();
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
//                .setOldController(simple.getController())
                .setImageRequest(request).build();
        simple.setController(controller);
    }

    //上传图片
    private AddPhoto_Model model;

    public void upload_PhotoData(final Context context, String baseUrl, List<File> filesList) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        params.setMultipart(true);//表单格式
        params.setCancelFast(true);//支持断点续传
        for (int i = 0; i < filesList.size(); i++) {
            try {
//                FileInputStream fileStream = new FileInputStream(new File(photoPaths.get(i)));
                FileInputStream fileStream = new FileInputStream(filesList.get(i));
                params.addBodyParameter("file" + i, fileStream, null, filesList.get(i).getName());
                //最后fileName InputStream参数获取不到文件名, 最好设置, 除非服务端不关心这个参数.
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                model = new Gson().fromJson(resultString, AddPhoto_Model.class);
                if (model.isIsSuccess()) {
                    String files = model.getData().toString().replace("[", "").replace("]", "");
                    AddPost_Data(PostingActivity.this, saveFile.BaseUrl + saveFile.Post_Add_Url, files);
                } else {
//                    Toast.makeText(ChangePhone.this, model.getMsg(), 3000).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")) {
                    Intent intent = new Intent(context, LoginRegister.class);
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


    public void AddPost_Data(final Context context, String baseUrl, String files) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        JSONObject obj = new JSONObject();
        try {
            obj.put("PostContent", content_Edit.getText());
            obj.put("PostType", postType);//1是普通帖子
            obj.put("FileIDs", files);
            String choiceIds = choiceId.toString().replace("[", "").replace("]", "");
            obj.put("ToUsers", choiceIds);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params.setBodyContent(obj.toString());
        params.setAsJsonContent(true);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                right_Btn.setEnabled(true);
                BaseDataInt_Model model = new Gson().fromJson(resultString, BaseDataInt_Model.class);
                if (model.isIsSuccess()) {
                    shareTitle = content_Edit.getText().toString();
                    shareContent = "我的能量源是" + saveFile.getShareData("InviteCode", PostingActivity.this);
                    shareUrl = saveFile.BaseUrl + "Share/PostDetails?PostID=" + model.getData();

                    isShare(shareIndex());//同步分享
                } else {
//                    Toast.makeText(ChangePhone.this, model.getMsg(), 3000).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                right_Btn.setEnabled(true);
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(CancelledException e) {
                right_Btn.setEnabled(true);
            }

            @Override
            public void onFinished() {
                right_Btn.setEnabled(true);
            }
        });
    }

    public class share_friend implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (shareFlag.get(0).equals(false)) {
                shareFlag.put(0, true);
                share_friend.setBackgroundResource(R.drawable.share_friend_select);
            } else {
                shareFlag.put(0, false);
                share_friend.setBackgroundResource(R.drawable.share_friend_icon);
            }
        }
    }

    public class share_mom implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (shareFlag.get(1).equals(false)) {
                shareFlag.put(1, true);
                share_mom.setBackgroundResource(R.drawable.share_wechat_select);
            } else {
                shareFlag.put(1, false);
                share_mom.setBackgroundResource(R.drawable.share_wechat_icon);
            }
        }
    }

    public class share_sina implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (shareFlag.get(2).equals(false)) {
                shareFlag.put(2, true);
                share_sina.setBackgroundResource(R.drawable.share_sina_select);
            } else {
                shareFlag.put(2, false);
                share_sina.setBackgroundResource(R.drawable.share_sina_icon);
            }
        }
    }

    public class share_qq implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (shareFlag.get(3).equals(false)) {
                shareFlag.put(3, true);
                share_qq.setBackgroundResource(R.drawable.share_qq_select);
            } else {
                shareFlag.put(3, false);
                share_qq.setBackgroundResource(R.drawable.share_qq_icon);
            }
        }
    }

    public class share_qzone implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (shareFlag.get(4).equals(false)) {
                shareFlag.put(4, true);
                share_qzone.setBackgroundResource(R.drawable.share_qzone_select);
            } else {
                shareFlag.put(4, false);
                share_qzone.setBackgroundResource(R.drawable.share_qzone_icon);
            }
        }
    }

    private void setShareFlag() {//分享map
        shareFlag = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            shareFlag.put(i, false);
        }
    }

    //分享下标
    public int shareIndex() {
        int index = 5;//4 finsh此页面
        for (int i = 0; i < shareFlag.size(); i++) {
            if (shareFlag.get(i).equals(true)) {
                shareFlag.put(i, false);
                index = i;
//                jifen = 4;//分享积分
                return index;
            }
        }
        return index;
    }

    //按index 对应分享
    public void isShare(int pos) {
        if (pos == 0) {
            shareWechatQuan();
        } else if (pos == 1) {
            shareWechatFriend();
        } else if (pos == 2) {
            shareSina();
        } else if (pos == 3) {
            share_qq();
        } else if (pos == 4) {
            share_qzone();
            finish();
        } else {
            finish();
        }
    }

    private String shareTitle = "";
    private String shareContent = "";
    private String shareUrl = "";

    //分享给微信朋友
    public void shareWechatFriend() {
        ShareSDK.initSDK(this);
        Platform.ShareParams wechat = new Platform.ShareParams();
        wechat.setTitle(shareTitle);
        wechat.setText(shareContent);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ring);
//        msg.thumbData = Util.bmpToByteArray(bitmap, true);
//        InputStream is = getResources().openRawResource(R.drawable.ring_icon);
//        Bitmap mBitmap = BitmapFactory.decodeStream(is);
        wechat.setImageData(bitmap);
        wechat.setUrl(shareUrl);
//        wechat.setImageData(wechatbit);
        wechat.setShareType(Platform.SHARE_WEBPAGE);
        Platform weixin = ShareSDK.getPlatform(this, Wechat.NAME);
        weixin.setPlatformActionListener(this);
        weixin.share(wechat);
    }


    //分享-微信朋友圈
    public void shareWechatQuan() {
        ShareSDK.initSDK(this);
        Platform.ShareParams wechat = new Platform.ShareParams();
        wechat.setTitle(shareTitle);
        wechat.setText(shareContent);
//        InputStream is = getResources().openRawResource(R.drawable.ring_icon);
//        Bitmap mBitmap = BitmapFactory.decodeStream(is);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ring);
        wechat.setImageData(bitmap);
        wechat.setUrl(shareUrl);
//        wechat.setImageData(wechatfriendbit);
        wechat.setShareType(Platform.SHARE_WEBPAGE);
        Platform weixin = ShareSDK.getPlatform(this, WechatMoments.NAME);
        weixin.setPlatformActionListener(this);
        weixin.share(wechat);
    }

    //分享-新浪微博
    public void shareSina() {
        ShareSDK.initSDK(this);
        SinaWeibo.ShareParams sina = new SinaWeibo.ShareParams();
        sina.setText(shareTitle + shareUrl);
//        sina.setImagePath(sianimg);
        Platform sinap = ShareSDK.getPlatform(this, SinaWeibo.NAME);
        sinap.setPlatformActionListener(this);
//        sinap.SSOSetting(true);//审核未通过
        sinap.share(sina);
    }

    public void share_qq() {
        ShareSDK.initSDK(this);
        QQ.ShareParams sp = new QQ.ShareParams();
        sp.setTitle(shareTitle);
        sp.setTitleUrl(shareUrl); // 标题的超链接
        sp.setText(shareContent);
//        sp.setImageUrl("http://www.someserver.com/测试图片网络地址.jpg");
//        sp.setSite("发布分享的网站名称");
//        sp.setSiteUrl("发布分享网站的地址");
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.setPlatformActionListener(this); // 设置分享事件回调
        qq.share(sp);
    }

    public void share_qzone() {
        ShareSDK.initSDK(this);
        QQ.ShareParams sp = new QQ.ShareParams();
        sp.setTitle(shareTitle);
        sp.setTitleUrl(shareUrl);
        sp.setText(shareContent);
        sp.setSite("能量圈");//分享应用的名称
        sp.setSiteUrl("http://m.pp.cn/detail.html?appid=6863306&ch_src=pp_dev&ch=default");//分享应用的网页地址
        Platform qzone = ShareSDK.getPlatform(QZone.NAME);
        qzone.setPlatformActionListener(this); // 设置分享事件回调
        qzone.share(sp);
    }


    /*
    *
    *分享事件回调
    *
    *
    * */
    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.arg1) {
            case 1: {
                // 成功
                Log.e("分享回调", "分享回调");
                Log.e("shareFlag", shareFlag.toString());
                isShare(shareIndex());
                Toast.makeText(this, "分享成功", Toast.LENGTH_SHORT).show();

                System.out.println("分享回调成功------------");
            }
            break;
            case 2: {
                // 失败
                Log.e("fffffffffffffffffffff", msg.obj.toString());
//					Toast.makeText(HomePage_NewsMan_XiangQing.this,"分享失败", 10000).show();
                Toast.makeText(this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
            }
            break;
            case 3: {
                // 取消
                isShare(shareIndex());

//                Toast.makeText(this, "分享取消", Toast.LENGTH_SHORT).show();
            }
            break;
        }

        return false;
    }

    private static final int MSG_TOAST = 1;
    private static final int MSG_ACTION_CCALLBACK = 2;
    private static final int MSG_CANCEL_NOTIFY = 3;

    @Override
    public void onCancel(Platform arg0, int arg1) {
        // 取消
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 3;
        msg.arg2 = arg1;
        msg.obj = arg0;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
        // 成功
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 1;
        msg.arg2 = arg1;
        msg.obj = arg2;
        UIHandler.sendMessage(msg, this);

    }

    @Override
    public void onError(Platform arg0, int arg1, Throwable arg2) {
        // 失敗
        //打印错误信息,print the error msg
        arg2.printStackTrace();
        //错误监听,handle the error msg
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 2;
        msg.arg2 = arg1;
        msg.obj = arg2;
        UIHandler.sendMessage(msg, this);
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


    public class save_Popup extends PopupWindow {
        public save_Popup(final Context mContext, View parent) {
            super(parent);
            View view = View.inflate(mContext, R.layout.popup_save, null);
            setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            setOutsideTouchable(true);
            setFocusable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.CENTER, 0, 0);
            update();
            Button topbtn = (Button) view.findViewById(R.id.topbtn);
            topbtn.setText("保存");
            Button belowbtn = (Button) view.findViewById(R.id.belowbtn);
            belowbtn.setTextColor(Color.parseColor("#F24d40"));
            belowbtn.setText("不保存");
            Button cancelbtn = (Button) view.findViewById(R.id.cancelbtn);
            StaticData.ViewScale(topbtn, 750, 102);
            StaticData.ViewScale(belowbtn, 750, 102);
            StaticData.ViewScale(cancelbtn, 750, 88);
            topbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String content = content_Edit.getText().toString();
                    String time = StaticData.getTodaystyle();
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < photoPaths.size(); i++) {
                        sb.append(photoPaths.get(i) + ",");
                    }
                    String imgurl = sb.toString();
                    if (dbId != null) {
                        dbUpdate(content, time, imgurl);
                    } else {
                        dbAdd(content, time, imgurl);
                    }
                    dismiss();
                    finish();
                }
            });
            belowbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    finish();
                }
            });
            cancelbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

        }
    }

    public class wei_Popup extends PopupWindow {
        public wei_Popup(final Context mContext, View parent) {
            super(parent);
            final View view = View.inflate(mContext, R.layout.wei_popup, null);
            setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            setOutsideTouchable(true);
            setFocusable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.CENTER, 0, 0);
            update();
            Button cancel_Btn = (Button) view.findViewById(R.id.cancel_Btn);
            Button sure_Btn = (Button) view.findViewById(R.id.sure_Btn);

            sure_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MobclickAgent.onEvent(PostingActivity.this, "postDBAdd");
                    dbFindFrist();
                    dismiss();
                }
            });

            cancel_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

        }
    }


    protected DbManager db;

    protected void initDb() {
        //本地数据的初始化
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("energy.db") //设置数据库名
                .setDbVersion(1) //设置数据库版本,每次启动应用时将会检查该版本号,
                //发现数据库版本低于这里设置的值将进行数据库升级并触发DbUpgradeListener
                .setAllowTransaction(true)//设置是否开启事务,默认为false关闭事务
                .setTableCreateListener(new DbManager.TableCreateListener() {
                    @Override
                    public void onTableCreated(DbManager db, TableEntity<?> table) {
                        Log.i("JAVA", "onTableCreated：" + table.getName());
                    }
                })//设置数据库创建时的Listener
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        //balabala...
                        //开启数据库支持多线程操作，提升性能
                        db.getDatabase().enableWriteAheadLogging();
                    }
                });//设置数据库升级时的Listener,这里可以执行相关数据库表的相关修改,比如alter语句增加字段等
        //.setDbDir(null);//设置数据库.db文件存放的目录,默认为包名下databases目录下
        db = x.getDb(daoConfig);
    }

    protected void dbAdd(String content, String time, String imgurl) {
        //db.save(user);//保存成功之后【不会】对user的主键进行赋值绑定
        //db.saveOrUpdate(user);//保存成功之后【会】对user的主键进行赋值绑定
        //db.saveBindingId(user);//保存成功之后【会】对user的主键进行赋值绑定,并返回保存是否成功
//        List<ChildInfo> users = new ArrayList<ChildInfo>();
        ChildInfo info = new ChildInfo(content, time, imgurl);
//        users.add(info);
        try {
            db.saveBindingId(info);
//            Log.e("Arrrrrrrrr", db.toString());
        } catch (DbException e) {
            e.printStackTrace();
        }
//        Intent intent = new Intent(PostingActivity.this, PersonDeaft.class);
//        startActivity(intent);
    }

    protected void dbFind(String dbId) {
        //List<User> users = db.findAll(User.class);
        //showDbMessage("【dbFind#findAll】第一个对象:"+users.get(0).toString());

        //User user = db.findById(User.class, 1);
        //showDbMessage("【dbFind#findById】第一个对象:" + user.toString());

        //long count = db.selector(User.class).where("name","like","%kevin%").and("email","=","caolbmail@gmail.com").count();//返回复合条件的记录数
        //showDbMessage("【dbFind#selector】复合条件数目:" + count);
        ChildInfo info = null;
        try {
            info = db.findById(ChildInfo.class, dbId);
//            info = db.findFirst(ChildInfo.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (info == null) {
            return;//请先调用dbAdd()方法
        }
        content_Edit.setText(info.getContent());//加载数据库中数据
        String[] imgStr = info.getImgurl().split(",");
        if (imgStr.length == 0 || !imgStr[0].equals("")) {
            List<String> imgList = Arrays.asList(imgStr);
            imgFlow(photoLayout, imgList);
//            for (int i = 0; i < imgStr.length; i++) {
//                addNewPhoto(imgStr[i]);
//            }
        }

    }

    public void dbDelete(int dbId) {
        try {
            List<ChildInfo> users = db.findAll(ChildInfo.class);
            if (users == null || users.size() == 0) {
                return;//先调用dbAdd()方法
            }
            //db.delete(users.get(0)); //删除第一个对象
            //db.delete(User.class);//删除表中所有的User对象【慎用】
            //db.delete(users); //删除users对象集合
            //users =  db.findAll(User.class);
            // showDbMessage("【dbDelete#delete】数据库中还有user数目:" + users.size());
            WhereBuilder whereBuilder = WhereBuilder.b();
            whereBuilder.and("id", "=", dbId);
            db.delete(ChildInfo.class, whereBuilder);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void dbUpdate(String content, String time, String imgurl) {
        try {
            List<ChildInfo> users = db.findAll(ChildInfo.class);
            if (users == null || users.size() == 0) {
                return;//先调用dbAdd()方法
            }
            ChildInfo oldinfo = db.findById(ChildInfo.class, dbId);
//            ChildInfo newinfo = new ChildInfo(content, time, imgurl);
            oldinfo.setContent(content);
            oldinfo.setTime(time);
            oldinfo.setImgurl(imgurl);
            db.update(oldinfo);

//            WhereBuilder whereBuilder = WhereBuilder.b();
//            whereBuilder.and("id", "=", dbId);
//            db.update(ChildInfo.class,whereBuilder,newinfo);
        } catch (DbException e) {
            e.printStackTrace();
        }
//        List<ChildInfo> users = db.findAll(ChildInfo.class);
//        if(users == null || users.size() == 0){
//            return;//请先调用dbAdd()方法
//        }
//        ChildInfo user = users.get(0);
//        user.setEmail(System.currentTimeMillis() / 1000 + "@email.com");
//        //db.replace(user);
//        //db.update(user);
//        //db.update(user,"email");//指定只对email列进行更新
//
//        WhereBuilder whereBuilder = WhereBuilder.b();
//        whereBuilder.and("id",">","5").or("id","=","1").expr(" and mobile > '2015-12-29 00:00:01' ");
//        db.update(ChildInfo.class,whereBuilder, new KeyValue("email",System.currentTimeMillis() / 1000 + "@email.com")
//                ,new KeyValue("mobile","18988888888"));//对User表中复合whereBuilder所表达的条件的记录更新email和mobile
    }

    protected void dbFindFrist() {
        //List<User> users = db.findAll(User.class);
        //showDbMessage("【dbFind#findAll】第一个对象:"+users.get(0).toString());

        //User user = db.findById(User.class, 1);
        //showDbMessage("【dbFind#findById】第一个对象:" + user.toString());

        //long count = db.selector(User.class).where("name","like","%kevin%").and("email","=","caolbmail@gmail.com").count();//返回复合条件的记录数
        //showDbMessage("【dbFind#selector】复合条件数目:" + count);
        ChildInfo info = null;
        try {
            info = db.findFirst(ChildInfo.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (info == null) {
            return;//请先调用dbAdd()方法
        }
        dbId = info.getId() + "";
        content_Edit.setText(info.getContent());//加载数据库中数据
        String[] imgStr = info.getImgurl().split(",");
        if (imgStr.length == 0 || !imgStr[0].equals("")) {
            List<String> imgList = Arrays.asList(imgStr);
            imgFlow(photoLayout, imgList);
//            for (int i = 0; i < imgStr.length; i++) {
//                addNewPhoto(imgStr[i]);
//            }
        }

    }


}
