package com.moying.energyring.myAcativity.Pk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanjay.selectorphotolibrary.SelectedPhotoActivity;
import com.example.sanjay.selectorphotolibrary.bean.ImgOptions;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.gson.Gson;
import com.mob.tools.utils.UIHandler;
import com.moying.energyring.Model.AddPhoto_Model;
import com.moying.energyring.Model.PostAndPk_Add;
import com.moying.energyring.Model.ProjectModel;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.NoDoubleClickListener;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.LoginRegister;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.FlowLayout;
import com.moying.energyring.waylenBaseView.MyActivityManager;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
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

public class Pk_DayPkAdd_More extends Activity implements PlatformActionListener, Handler.Callback {
    RelativeLayout project_Rel;
    SimpleDraweeView project_simple;
    ImageView add_Img;
    Button add_Btn, mu_Btn;
    private TextView centent_Txt, unit_Txt;
    final int RESULT_CODE_MORE = 255;
    private EditText count_Edit;
    private List<String> photoPaths;
    FlowLayout photoLayout;
    ImageButton add_photo_Img;
    String ProjectID = "0";
    private CheckBox add_energy_Img;
    private Map<Integer, Boolean> shareFlag;
    private ImageView share_friend, share_mom, share_sina, share_qq, share_qzone;
    private List<ProjectModel> projectModel;
    private LinearLayout project_Lin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pk__day_pk_add_more);
        MyActivityManager mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中

        RelativeLayout title_Rel = (RelativeLayout) findViewById(R.id.title_Rel);
        Button return_Btn = (Button) findViewById(R.id.return_Btn);
        centent_Txt = (TextView) findViewById(R.id.centent_Txt);
        StaticData.ViewScale(return_Btn, 48, 48);
        StaticData.ViewScale(title_Rel, 0, 88);

//        ImageView bg_img = (ImageView) findViewById(R.id.bg_img);
//        StaticData.ViewScale(bg_img, 0, 740);

        add_Btn = (Button) findViewById(R.id.add_Btn);
        StaticData.ViewScale(add_Btn, 62, 62);
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        StaticData.layoutParamsScale(params, 62, 62);
//        int padtop = (int) (Float.parseFloat(saveFile.getShareData("scale", this)) * 420);
//        int padleft = (int) (Float.parseFloat(saveFile.getShareData("scale", this)) * 345);
//        params.setMargins(padleft, padtop, 0, 0);
//        add_Btn.setLayoutParams(params);

        mu_Btn = (Button) findViewById(R.id.mu_Btn);
        StaticData.ViewScale(mu_Btn, 400, 120);
//        add_Img = (ImageView) findViewById(R.id.add_Img);
//        StaticData.ViewScale(add_Img, 82, 82);
        photoLayout = (FlowLayout) findViewById(R.id.photoLayout);
        add_photo_Img = (ImageButton) findViewById(R.id.add_photo_Img);
        StaticData.ViewScale(add_photo_Img, 82, 82);
        project_Rel = (RelativeLayout) findViewById(R.id.project_Rel);
        project_simple = (SimpleDraweeView) findViewById(R.id.project_simple);
        unit_Txt = (TextView) findViewById(R.id.unit_Txt);
        count_Edit = (EditText) findViewById(R.id.count_Edit);
        StaticData.ViewScale(project_simple, 400, 400);
        RelativeLayout energy_Rel = (RelativeLayout) findViewById(R.id.energy_Rel);
        StaticData.ViewScale(energy_Rel, 0, 90);
        add_energy_Img = (CheckBox) findViewById(R.id.add_energy_Img);
        StaticData.ViewScale(add_energy_Img, 72, 72);

        LinearLayout share_Lin = (LinearLayout) findViewById(R.id.share_Lin);
        share_friend = (ImageView) findViewById(R.id.share_friend);
        share_mom = (ImageView) findViewById(R.id.share_mom);
        share_sina = (ImageView) findViewById(R.id.share_sina);
        share_qq = (ImageView) findViewById(R.id.share_qq);
        share_qzone = (ImageView) findViewById(R.id.share_qzone);
        project_Lin = (LinearLayout) findViewById(R.id.project_Lin);

        StaticData.ViewScale(share_Lin, 0, 90);
        StaticData.ViewScale(share_friend, 72, 72);
        StaticData.ViewScale(share_mom, 72, 72);
        StaticData.ViewScale(share_sina, 72, 72);
        StaticData.ViewScale(share_qq, 72, 72);
        StaticData.ViewScale(share_qzone, 72, 72);

        return_Btn.setOnClickListener(new return_Btn());
        add_Btn.setOnClickListener(new add_Btn());
        project_simple.setOnClickListener(new project_simple());
        count_Edit.addTextChangedListener(new count_Edit());
        mu_Btn.setOnClickListener(new mu_Btn());
        add_photo_Img.setOnClickListener(new add_photo_Img());

        share_friend.setOnClickListener(new share_friend());
        share_mom.setOnClickListener(new share_mom());
        share_sina.setOnClickListener(new share_sina());
        share_qq.setOnClickListener(new share_qq());
        share_qzone.setOnClickListener(new share_qzone());

        photoPaths = new ArrayList<>();
        setShareFlag();
        projectModel = new ArrayList<>();
        projectModel = saveFile.getGosnClass(Pk_DayPkAdd_More.this, "moreModel", ProjectModel.class);
        projectList(project_Lin, projectModel);
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


    private class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }


    private class add_Btn extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            Intent intent = new Intent(Pk_DayPkAdd_More.this, Pk_DayPkAdd_Project_More.class);
            intent.putExtra("baseModel", (Serializable) projectModel);
            //第二个参数为请求码，可以根据业务需求自己编号
            startActivityForResult(intent, RESULT_CODE_MORE);
        }
    }

    private class project_simple extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            Intent intent = new Intent(Pk_DayPkAdd_More.this, Pk_DayPkAdd_Project_More.class);
            intent.putExtra("baseModel", (Serializable) projectModel);
            startActivityForResult(intent, RESULT_CODE_MORE);
        }
    }

    private class count_Edit implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length() > 0) {
                mu_Btn.setBackgroundResource(R.drawable.allperson_btn);
                mu_Btn.setTextColor(Color.parseColor("#ffffff"));
                mu_Btn.setEnabled(true);
            } else {
                mu_Btn.setBackgroundResource(R.drawable.timechioce_next);
                mu_Btn.setTextColor(Color.parseColor("#f24d4d"));
                mu_Btn.setEnabled(false);
            }
        }
    }

    private class mu_Btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
//            if (StaticData.isSpace(count_Edit.getText().toString())) {
//                Toast.makeText(Pk_DayPkAdd_More.this, "请填写pk数", Toast.LENGTH_SHORT).show();
//                return;
//            }

            for (int i = 0; i < projectModel.size(); i++) {
                if (StaticData.isSpace(projectModel.get(i).getReportNum())) {
                    Toast.makeText(Pk_DayPkAdd_More.this, "请填写pk数", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            MobclickAgent.onEvent(Pk_DayPkAdd_More.this, "releasePk");//统计页签
            if (photoPaths.size() == 0) {
                AddPk_Data(Pk_DayPkAdd_More.this, saveFile.BaseUrl + saveFile.AddPk_Url, "");
            } else {
                mFileList = new ArrayList<>();
                for (String photo : photoPaths) {
                    mFileList.add(new File(photo));
                }
                compressMultiListener(Luban.FIRST_GEAR);//THIRD_GEAR普通压缩,FIRST_GEAR快速压缩

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
                        while (size-- > 0) {
                            int size1 = filesList.size();
//                            mImageViews.get(size).setImageURI(Uri.fromFile(fileList.get(size)));
//                        Log.e("图片尺寸1111111111111111111",filesList.get(0).length() / 1024 + "k");
                        }
//                        upload_PhotoData(Pk_DayPkAdd.this, saveFile.BaseUrl + saveFile.uploadPhoto_Url,filesList);
                        upload_PhotoData(Pk_DayPkAdd_More.this, saveFile.BaseUrl + saveFile.uploadPhoto_Url, filesList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

    private static final int REQUEST_IMAGE = 1000;
    private static final String EXTRA_DATA = "extra_data";

    private class add_photo_Img implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int choice = 9 - photoPaths.size();
            ImgOptions options = new ImgOptions(choice, 1, true);
            startActivityForResult(SelectedPhotoActivity.makeIntent(Pk_DayPkAdd_More.this, options), REQUEST_IMAGE);
        }
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
    public void
    isShare(int pos) {
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
            if (ArticleCount != 0) {
                Intent intent = new Intent(Pk_DayPkAdd_More.this, JiFenActivity.class);
                intent.putExtra("jifen", ArticleCount);
                startActivity(intent);
            }
            finish();
        } else {
            if (ArticleCount != 0) {
                Intent intent = new Intent(Pk_DayPkAdd_More.this, JiFenActivity.class);
                intent.putExtra("jifen", ArticleCount);
                startActivity(intent);
            }
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
        Toast.makeText(this, "微信朋友分享成功", Toast.LENGTH_SHORT).show();
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
//        Toast.makeText(this, "微信朋友圈分享成功", Toast.LENGTH_SHORT).show();
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
//        Toast.makeText(this, "新浪微博分享成功", Toast.LENGTH_SHORT).show();
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
//        Toast.makeText(this, "qq分享成功", Toast.LENGTH_SHORT).show();
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
//        Toast.makeText(this, "qq空间分享成功", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 255 && data != null) {
            projectModel = (List<ProjectModel>) data.getSerializableExtra("projectModel");

            projectList(project_Lin, projectModel);

        } else if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                List<String> pathList = data.getStringArrayListExtra(EXTRA_DATA);
                imgFlow(photoLayout, pathList);
            }
        }

    }

    //
    public void projectList(LinearLayout myFlow, final List<ProjectModel> myBean) {
        if (myFlow != null) {
            myFlow.removeAllViews();
        }
//        List<SimpleDraweeView> mySimpleArr = new ArrayList<>();
        int size = myBean.size();
        for (int i = 0; i < size; i++) {
            RelativeLayout myview = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.pkadd_project, null);
            RadioGroup.LayoutParams itemParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            StaticData.layoutParamsScale(itemParams, 0, 100);
            myview.setLayoutParams(itemParams);
            TextView project_Name = (TextView) myview.findViewById(R.id.project_Name);
            TextView project_Unit = (TextView) myview.findViewById(R.id.project_Unit);
            final EditText project_Edit = (EditText) myview.findViewById(R.id.project_Edit);
            project_Name.setText(myBean.get(i).getName() + ":");
            project_Unit.setText(myBean.get(i).getUnit());
            project_Edit.setText(myBean.get(i).getReportNum() + "");

            project_Edit.setTag(i);
            project_Edit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    int tag = (Integer) project_Edit.getTag();
                    myBean.get(tag).setReportNum(editable.toString());
                    saveFile.putClass(Pk_DayPkAdd_More.this, "moreModel", projectModel);
                    mu_Btn.setBackgroundResource(R.drawable.allperson_btn);
                    mu_Btn.setTextColor(Color.parseColor("#ffffff"));
                    mu_Btn.setEnabled(true);
                }
            });

            myFlow.addView(myview);
        }
    }


    //图片选择器
    public void imgFlow(FlowLayout myFlow, final List<String> imgList) {
        int size = imgList.size();
        for (int i = 0; i < size; i++) {
            photoPaths.add(imgList.get(i));
            LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            StaticData.layoutParamsScale(itemParams, 100, 100);
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
//                    Intent i = new Intent(this, ImagePagerActivity.class);
////                    i.putExtra("imgArr", (Parcelable) myBean);
//                    i.putExtra("imgArr", (Serializable) myArr);
//                    i.putExtra("tag", tag);
//                    startActivity(i);
//                    mySimple.setFocusable(true);
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
        for (int i = 0; i < photoPaths.size(); i++) {
//            params.addBodyParameter("file"+i,photoPaths.get(i),null,photoPaths.get(i));
            try {
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
                    String filesStr = model.getData().toString().replace("[", "").replace("]", "");
                    AddPk_Data(Pk_DayPkAdd_More.this, saveFile.BaseUrl + saveFile.AddPk_Url, filesStr);
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

    int ArticleCount = 0;

    public void AddPk_Data(final Context context, String baseUrl, String files) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
            params.setHeader("version", StaticData.getversionName(context));
        }
        JSONObject allObj = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < projectModel.size(); i++) {
                JSONObject obj = new JSONObject();
                obj.put("ProjectID", projectModel.get(i).getProjectId());
                obj.put("ReportNum", projectModel.get(i).getReportNum());
                jsonArray.put(obj);
            }
            allObj.put("Report_Items", jsonArray);
            allObj.put("FileIDs", files);
            boolean sync = false;
            if (add_energy_Img.isChecked()) {
                sync = true;
            }
            allObj.put("Is_Sync", sync);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        params.setBodyContent(allObj.toString());
        params.setAsJsonContent(true);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                PostAndPk_Add model = new Gson().fromJson(resultString, PostAndPk_Add.class);
                if (model.isIsSuccess()) {
                    Toast.makeText(Pk_DayPkAdd_More.this, "发布成功", Toast.LENGTH_SHORT).show();

                    String sbf = centent_Txt.getText().toString() + count_Edit.getText().toString() + unit_Txt.getText().toString() + "，";
//                    String contenttxt = "【"+ saveFile.getShareData("NickName",context)+"蜕变之旅 "+ StaticData.getTodaystyle() +"】  我完成了" + sbf +"欢迎到每日pk来挑战我！ 【来自能量圈APP-每日PK】";
                    shareTitle = "我今天" + sbf + "(根据实际的汇报内容)，加入能量圈，和我一起PK吧！";
//                    shareContent = "我的能量源是" + saveFile.getShareData("InviteCode",Pk_DayPkAdd.this);
                    shareContent = "";
                    shareUrl = saveFile.BaseUrl + "Share/PkDetails?ReportID=" + model.getData().split("\\*")[0];

                    String data = model.getData();
//                    ArticleCount = Integer.parseInt(data.substring(data.indexOf(",") + 1));
                    ArticleCount = Integer.parseInt(data.split("\\*")[1]);
                    isShare(shareIndex());//同步分享
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

}
