package com.moying.energyring.myAcativity.Person;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanjay.selectorphotolibrary.SelectedPhotoActivity;
import com.example.sanjay.selectorphotolibrary.bean.ImgOptions;
import com.example.sanjay.selectorphotolibrary.utils.PermissionUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.gson.Gson;
import com.moying.energyring.Model.AddPhoto_Model;
import com.moying.energyring.Model.PostAndPk_Add;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.NoDoubleClickListener;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.myAcativity.Pk.JiFenActivity;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.pinyin.SortModel;
import com.moying.energyring.waylenBaseView.FlowLayout;
import com.moying.energyring.waylenBaseView.MyActivityManager;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnMultiCompressListener;

public class Person_Feedback_Posting extends Activity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private FlowLayout photoLayout;
    private ImageButton add_photo_Img;
    List<SortModel> choiceModel;
    List<Integer> choiceId;
    private EditText content_Edit;
    private Button right_Btn;
    int postType;
    int TagID;//活动标签

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_posting);
        MyActivityManager mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中

        initTitle();
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
//        title_Include.setBackgroundColor(Color.parseColor("#ffffff"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            title_Include.setElevation(2f);//阴影
        }
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundColor(Color.parseColor("#00000000"));
        return_Btn.setVisibility(View.VISIBLE);
        return_Btn.setTextColor(Color.parseColor("#ffffff"));
        return_Btn.setText("取消");
        TextView cententTxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententTxt.setTextColor(Color.parseColor("#ffffff"));
        cententTxt.setText("发帖");
        right_Btn = (Button) title_Include.findViewById(R.id.right_Btn);
        right_Btn.setVisibility(View.VISIBLE);
        right_Btn.setTextColor(Color.parseColor("#ffffff"));
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
        postType = 8;
        TagID = 0;
    }


    private class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }


    private class right_Btn extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            if (StaticData.isSpace(content_Edit.getText().toString())) {
                Toast.makeText(Person_Feedback_Posting.this, "吐槽内容不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            right_Btn.setEnabled(false);
            MobclickAgent.onEvent(Person_Feedback_Posting.this, "releaseAdd");//统计页签
            if (photoPaths.size() == 0) {
                AddPost_Data(Person_Feedback_Posting.this, saveFile.BaseUrl + saveFile.Post_Add_Url, "");
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
                        upload_PhotoData(Person_Feedback_Posting.this, saveFile.BaseUrl + saveFile.uploadPhoto_Url, filesList);

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
        content_Edit = (EditText) findViewById(R.id.content_Edit);

        StaticData.ViewScale(edit_Rel, 710, 1032);
//        StaticData.ViewScale(add_photo_Img, 82, 82);
        add_photo_Img.setOnClickListener(new add_photo_Img());
    }


    private List<String> photoPaths;
    private static final int REQUEST_IMAGE = 1000;
    private static final String EXTRA_DATA = "extra_data";

    private class add_photo_Img implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            /*申请读取存储的权限*/
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PermissionUtils.requestPermission(Person_Feedback_Posting.this, PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE, permissionGrant);
            } else {
                int choice = 9 - photoPaths.size();
                ImgOptions options = new ImgOptions(choice, 1, true);
                startActivityForResult(SelectedPhotoActivity.makeIntent(Person_Feedback_Posting.this, options), REQUEST_IMAGE);
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
                    startActivityForResult(SelectedPhotoActivity.makeIntent(Person_Feedback_Posting.this, options), REQUEST_IMAGE);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_IMAGE:
                if (resultCode == RESULT_OK) {
                    List<String> pathList = data.getStringArrayListExtra(EXTRA_DATA);
                    imgFlow(photoLayout, pathList);
                }
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
                    AddPost_Data(Person_Feedback_Posting.this, saveFile.BaseUrl + saveFile.Post_Add_Url, files);
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


    public void AddPost_Data(final Context context, String baseUrl, String files) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
            params.setHeader("version", StaticData.getversionName(context));
        }
        JSONObject obj = new JSONObject();
        try {
            obj.put("PostContent", content_Edit.getText());
            obj.put("PostType", postType);//1是普通帖子
            obj.put("FileIDs", files);
            String choiceIds = choiceId.toString().replace("[", "").replace("]", "");
            obj.put("ToUsers", choiceIds);
            obj.put("TagID", TagID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params.setBodyContent(obj.toString());
        params.setAsJsonContent(true);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                right_Btn.setEnabled(true);
                PostAndPk_Add model = new Gson().fromJson(resultString, PostAndPk_Add.class);
                if (model.isIsSuccess()) {
                    Toast.makeText(Person_Feedback_Posting.this, "发布成功", Toast.LENGTH_SHORT).show();

                    String data = model.getData();
                    int ArticleCount = Integer.parseInt(data.substring(data.indexOf(",") + 1));
                    if (ArticleCount != 0) { // 0是超过发帖上限
                        Intent intent = new Intent(context, JiFenActivity.class);
                        intent.putExtra("jifen", ArticleCount);
                        startActivity(intent);
                    }
                    finish();
                } else {
//                    Toast.makeText(ChangePhone.this, model.getMsg(), 3000).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                right_Btn.setEnabled(true);
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")) {
                    Intent intent = new Intent(context, MainLogin.class);
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        photoPaths.clear();
        choiceModel.clear();
        choiceId.clear();
    }
}
