package com.moying.energyring.myAcativity.Pk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
import com.example.sanjay.selectorphotolibrary.utils.PermissionUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.moying.energyring.Model.AddPhoto_Model;
import com.moying.energyring.Model.Base_Model;
import com.moying.energyring.Model.Pk_HuiZong_Model;
import com.moying.energyring.Model.ShareContent;
import com.moying.energyring.Model.huiZongpkPhoto_Model;
import com.moying.energyring.Model.isFristSee_Model;
import com.moying.energyring.Model.person_daypk_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.AutoScaleTextView;
import com.moying.energyring.waylenBaseView.DragSortView;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnMultiCompressListener;

public class Pk_HuiZong extends Activity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private LinearLayout yiLin;
    public List<String> photoPaths;
    private DragSortView drag_SortView;
    private ImageButton add_photo_Img;
    private TextView finsh_Txt;
    private EditText add_Edit;
    public List<String> photoID;
    private ImageView huizong_remid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pk__hui_zong);

        initTitle();
        initView();
        initData();
    }

    private void initTitle() {
        View title_Include = (View) findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(ContextCompat.getColor(this,R.color.colorFristWhite));
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(ContextCompat.getColor(this,R.color.colorFristBlack));
        cententtxt.setText("今日已打卡");
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
        yiLin = (LinearLayout) findViewById(R.id.yi_Lin);
//        addPhoto_Recy = (RecyclerView)findViewById(R.id.addPhoto_Recy);
        drag_SortView = (DragSortView) findViewById(R.id.drag_SortView);
        add_photo_Img = (ImageButton) findViewById(R.id.add_photo_Img);
        finsh_Txt = (TextView) findViewById(R.id.finsh_Txt);
        add_Edit = (EditText) findViewById(R.id.add_Edit);
        RelativeLayout edit_Rel = (RelativeLayout) findViewById(R.id.edit_Rel);
        StaticData.ViewScale(add_photo_Img, 138, 138);
        StaticData.ViewScale(edit_Rel, 0, 700);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int mag = (int) (Float.parseFloat(saveFile.getShareData("scale", Pk_HuiZong.this)) * 77);
        params.setMargins(0, mag, 0, 0);
        params.addRule(RelativeLayout.BELOW, R.id.edit_Rel);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        finsh_Txt.setLayoutParams(params);

        huizong_remid = (ImageView) findViewById(R.id.huizong_remid);
        RelativeLayout.LayoutParams paramsRemid = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramsRemid.addRule(RelativeLayout.ABOVE, R.id.drag_SortView);
        StaticData.layoutParamsScale(paramsRemid, 172, 92);
        int magL = (int) (Float.parseFloat(saveFile.getShareData("scale", Pk_HuiZong.this)) * 15);
        paramsRemid.setMargins(magL, 0, 0, 0);
        huizong_remid.setLayoutParams(paramsRemid);

        StaticData.ViewScale(finsh_Txt, 590, 100);
        add_photo_Img.setOnClickListener(new add_photo_Img());
        finsh_Txt.setOnClickListener(new finsh_Txt());
    }

    private void initData() {
        photoPaths = new ArrayList<>();
        photoID = new ArrayList<>();

        guideFristData(Pk_HuiZong.this, saveFile.BaseUrl + saveFile.GuidePerFirst_Url);//展示功能提醒页
        String userId = saveFile.getShareData("userId", Pk_HuiZong.this);
        ListData(Pk_HuiZong.this, saveFile.BaseUrl + saveFile.DayPk_Url + "?UserID=" + userId);
        reportPhotoData(Pk_HuiZong.this, saveFile.BaseUrl + saveFile.reportImg_Url);
    }

    private static final int REQUEST_IMAGE = 1000;
    private static final String EXTRA_DATA = "extra_data";

    private class add_photo_Img implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            /*申请读取存储的权限*/
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PermissionUtils.requestPermission(Pk_HuiZong.this, PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE, permissionGrant);
            } else {
                int choice = 9 - photoPaths.size();
                ImgOptions options = new ImgOptions(choice, 1, true);
                startActivityForResult(SelectedPhotoActivity.makeIntent(Pk_HuiZong.this, options), REQUEST_IMAGE);
            }
        }
    }

    private PermissionUtils.PermissionGrant permissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:
//                Toast.makeText(ImagePickerActivity.this, "读取存储权限已打开", Toast.LENGTH_SHORT).show();
                    int choice = 9 - photoPaths.size();
                    ImgOptions options = new ImgOptions(choice, 1, true);
                    startActivityForResult(SelectedPhotoActivity.makeIntent(Pk_HuiZong.this, options), REQUEST_IMAGE);
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


    private class finsh_Txt implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (StaticData.isSpace(add_Edit.getText().toString())) {
                Toast.makeText(Pk_HuiZong.this, "心得内容不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            finsh_Txt.setEnabled(false);
            MobclickAgent.onEvent(Pk_HuiZong.this, "releaseAdd");//统计页签
            if (photoPaths.size() == 0) {
                AddPost_Data(Pk_HuiZong.this, saveFile.BaseUrl + saveFile.Report_Status_Url, "");
            } else {

                mFileList = new ArrayList<>();
//                for (String photo : photoPaths) {
//                    mFileList.add(new File(photo));
//                }

                for (int i = 0; i < photoPaths.size(); i++) {
                    if (photoID.get(i).equals("-1")) {
                        mFileList.add(new File(photoPaths.get(i)));
                    }
                }
                if (mFileList.size() == 0) {
                    getFilesId();
                    String files = photoID.toString().replace("[", "").replace("]", "");
                    AddPost_Data(Pk_HuiZong.this, saveFile.BaseUrl + saveFile.Report_Status_Url, files);
                } else {
                    compressMultiListener(Luban.FIRST_GEAR);//压缩 THIRD_GEAR  普通压缩 FIRST_GEAR快速压缩
                }


            }
        }
    }

    //获取服务器图片ID
    private void getFilesId() {
        int strId = 0;
        for (int i = 0; i < photoID.size(); i++) {
            if (photoID.get(i).equals("-1")) {
                for (int j = 0; j < model.getData().size(); j++) {
                    photoID.set(i, model.getData().get(strId) + "");
                    break;
                }
                if (strId + 1 < model.getData().size()) {
                    strId++;
                }
            }
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_IMAGE:
                if (resultCode == RESULT_OK) {
                    List<String> pathList = data.getStringArrayListExtra(EXTRA_DATA);
//                    imgFlow(photoLayout, pathList);
//                    photoPaths.addAll(pathList);
//                    for (String photo : pathList) {
//                        pathList.add(new File(photo));
//                    }
                    for (int i = 0; i < pathList.size(); i++) {
                        pathList.set(i, pathList.get(i));
                        photoID.add("-1");
                    }
                    addphoto(pathList);
                }
        }

    }

    public void guideFristData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    isFristSee_Model isFristModel = new Gson().fromJson(resultString, isFristSee_Model.class);
                    if (isFristModel.isIsSuccess()) {
                        if (!isFristModel.getData().isIs_FirstPool_Pic()) {
                            huizong_remid.setVisibility(View.VISIBLE);

                        } else {
                            huizong_remid.setVisibility(View.GONE);
                        }

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

    public void updguidePer_Data(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Base_Model Model = new Gson().fromJson(resultString, Base_Model.class);
                    if (Model.isIsSuccess()) {

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


    person_daypk_Model baseModel;

    public void ListData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    baseModel = new Gson().fromJson(resultString, person_daypk_Model.class);
                    if (baseModel.isIsSuccess() && !baseModel.getData().equals("[]")) {
                        successList(context, yiLin, baseModel.getData());
                        add_Edit.setText(sbf.toString());
                        add_Edit.setSelection(sbf.toString().length());
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

    StringBuffer sbf;

    public void successList(final Context context, LinearLayout myFlow, final List<person_daypk_Model.DataBean> myBean) {
        if (myFlow != null) {
            myFlow.removeAllViews();
        }
        sbf = new StringBuffer();
        sbf.append("今日已完成");
        List<SimpleDraweeView> mySimpleArr = new ArrayList<>();
        int size = myBean.size();
        for (int i = 0; i < size; i++) {
            RelativeLayout myview = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.success_list, null);
            RadioGroup.LayoutParams itemParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            int pad = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 20);
            itemParams.setMargins(pad, 0, 0, 0);
            StaticData.layoutParamsScale(itemParams, 190, 230);
            myview.setLayoutParams(itemParams);
            final SimpleDraweeView mySimple = (SimpleDraweeView) myview.findViewById(R.id.project_Simple);
//            mySimple.setScaleType(ImageView.ScaleType.FIT_XY);
            StaticData.ViewScale(mySimple, 130, 130);
            if (myBean.get(i).getFilePath() != null) {
//                StaticData.addPlace(mySimple, getActivity());
                Uri imgUri = Uri.parse(myBean.get(i).getFilePath());
                mySimple.setImageURI(imgUri);
            }
            AutoScaleTextView project_Txt = (AutoScaleTextView) myview.findViewById(R.id.project_Txt);
            TextView count_Txt = (TextView) myview.findViewById(R.id.count_Txt);
            TextView unit_Txt = (TextView) myview.findViewById(R.id.unit_Txt);
            project_Txt.setText(myBean.get(i).getProjectName());
            NumberFormat nf = new DecimalFormat("#.#");//# 0 不显示
            count_Txt.setText(nf.format(myBean.get(i).getReportNum()));
            unit_Txt.setText(myBean.get(i).getProjectUnit());
            sbf.append(myBean.get(i).getProjectName() + nf.format(myBean.get(i).getReportNum()) + myBean.get(i).getProjectUnit() + "，");
            mySimple.setTag(i);
            mySimpleArr.add(mySimple);
            myFlow.addView(myview);
            mySimple.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (int) v.getTag();
//                    String url = myBean.get(tag).getBannerContent();
//                    MobclickAgent.onEvent(context, "bannerList");
//                    String content = myBean.get(tag).getBannerName();
//                    String url = myBean.get(tag).getBannerContent();
//                    Intent intent = new Intent(context, Find_BannerDetail.class);
//                    intent.putExtra("content", content);
//                    intent.putExtra("url", url);
//                    startActivity(intent);
                }
            });
        }
    }

    huiZongpkPhoto_Model Photo_Model;

    public void reportPhotoData(final Context context, final String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Photo_Model = new Gson().fromJson(resultString, huiZongpkPhoto_Model.class);
                    if (Photo_Model.isIsSuccess() && !Photo_Model.getData().equals("[]")) {

                        List<String> photos = new ArrayList<>();
                        for (int i = 0; i < Photo_Model.getData().size(); i++) {
                            photos.add(Photo_Model.getData().get(i).getFilePath());
                            photoID.add(Photo_Model.getData().get(i).getFileID() + "");
                        }
                        addphoto(photos);

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


    private void addphoto(List<String> imgList) {
        photoPaths.addAll(imgList);
        drag_SortView.setData(imgList, photoPaths);
        ChangeAddPhotoImage();
        drag_SortView.setChaClickLitener(new DragSortView.chaClick() {
            @Override
            public void chaClick(View view, int postion) {
                for (int i = 0; i < drag_SortView.getChildCount(); i++) {
                    if (view == drag_SortView.getChildAt(i)) {
                        photoPaths.remove(i);
                        photoID.remove(i);
                        ChangeAddPhotoImage();
                        break;
                    }
                }
                drag_SortView.removeView(view);
            }

            @Override
            public void sortClick(int startPos, int endPos) {
                Log.e("yyyyyyyyyyy", startPos + "aa" + endPos);
                Collections.swap(photoPaths, startPos, endPos);
                Collections.swap(photoID, startPos, endPos);
            }
        });
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
                        upload_PhotoData(Pk_HuiZong.this, saveFile.BaseUrl + saveFile.uploadPhoto_Url, filesList);

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
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

                    getFilesId();

                    String files = photoID.toString().replace("[", "").replace("]", "");
//                    String files = model.getData().toString().replace("[", "").replace("]", "");
                    AddPost_Data(context, saveFile.BaseUrl + saveFile.Report_Status_Url, files);
                } else {
//                    Toast.makeText(ChangePhone.this, model.getMsg(), 3000).show();
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

    public void AddPost_Data(final Context context, String baseUrl, final String files) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
            params.setHeader("version", StaticData.getversionName(context));
        }
        JSONObject obj = new JSONObject();
        try {
            obj.put("PostContent", add_Edit.getText());
            obj.put("FileIDs", files);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params.setBodyContent(obj.toString());
        params.setAsJsonContent(true);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                finsh_Txt.setEnabled(true);

//                BaseDataInt_Model reteModel = new Gson().fromJson(resultString, BaseDataInt_Model.class);
                Pk_HuiZong_Model reteModel = new Gson().fromJson(resultString, Pk_HuiZong_Model.class);

                if (reteModel.isIsSuccess()) {
                    String shareTitle = "";
                    String shareConStr = "我的能量源是" + saveFile.getShareData("InviteCode", context);
                    String shareUrl = "";
                    String imgUrl = null;
//                String shareUrl = saveFile.BaseUrl + "Share/PostDetails?PostID=" + postId;
                    ShareContent shareContent = new ShareContent(shareUrl, shareTitle, shareConStr, imgUrl);

                    if (!files.equals("")) {
                        updguidePer_Data(context, saveFile.BaseUrl + saveFile.upd_guidePerFirst_Url + "?str=" + "Is_FirstPool_Pic");//展示功能提醒页
                    }
                    int integral = Integer.parseInt(reteModel.getData().getIntegral());
                    if (integral == -1) {
                        Toast.makeText(context, "请汇报更多pk", Toast.LENGTH_SHORT).show();
                    } else if (integral == 0) {
                        Toast.makeText(context, "汇报成功", Toast.LENGTH_SHORT).show();
                        Intent intentSucc = new Intent(context, Pk_HuiZong_Success.class);
                        intentSucc.putExtra("shareContent", shareContent);
                        startActivity(intentSucc);
                        finish();
                    } else if (integral > 0) {
                        Toast.makeText(context, "汇报成功", Toast.LENGTH_SHORT).show();
                        Intent intentSucc = new Intent(context, Pk_HuiZong_Success.class);
//                    intentSucc.putExtra("shareContent", shareContent);
                        startActivity(intentSucc);

                        Intent intent = new Intent(context, JiFenActivity.class);
                        intent.putExtra("media", "huizong");
                        intent.putExtra("jifen", integral);
                        intent.putExtra("DailyTask", reteModel.getData().getDailyTask());
                        startActivity(intent);
                        finish();
                    }

                }else {
                    Toast.makeText(context, reteModel.getMsg(), Toast.LENGTH_SHORT).show();
                }

//                else if (integral == 0) {
////                    Intent intent = new Intent(context, Pk_HuiZong.class);
////                    startActivity(intent);
//                    finish();
//                } else if (integral > 0) {
//                    finish();
//                    Toast.makeText(context, "汇报成功", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(context, JiFenActivity.class);
//                    intent.putExtra("media", "huizong");
//                    intent.putExtra("jifen", integral);
//                    startActivity(intent);
//                }




            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                finsh_Txt.setEnabled(true);
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")) {
                    Intent intent = new Intent(context, MainLogin.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(CancelledException e) {
                finsh_Txt.setEnabled(true);
            }

            @Override
            public void onFinished() {
                finsh_Txt.setEnabled(true);
            }
        });
    }


}


