package com.moying.energyring.StaticData;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.moying.energyring.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * package : com.xgk.library.activity<br>
 * description:<B>图片获取Activity;<i><br>需要给此activity通过"view"指定layout,并且里面的控件id已由resource指定,
 * 不可更改,否则无效,
 * 若不指定layout,则使用默认layout<br>
 * 在配置清单中其theme指定为dialog型<br>
 * 最后选择的图片会从result中返回String类型的图片路径</i></B>
 * <pre>    Intent intent = new Intent(this,ImagePickerActivity.class);
 *  intent.putExtra("view", R.layout.dialog_select_image_way);
 *  startActivityForResult(intent,REQUEST_CODE_START_IMAGE_PICKER);
 *  -----------------------------------------------------------------
 *      protected void onActivityResult(int requestCode, int resultCode, Intent data) {
 *          super.onActivityResult(requestCode, resultCode, data);
 *              switch (requestCode) {
 *                  case REQUEST_CODE_START_IMAGE_PICKER:
 *                      if (resultCode == RESULT_OK) {
 *                          String path = data.getStringExtra("path");
 *                       }
 *                  break;
 *               }
 *      }
 *  </pre>
 *
 * @author Cookie Xie
 * @version v1.0
 *          email : cookiexie@adinnet.cn<br>
 *          date : 2015/8/4 18:08<br>
 */
public class ImagePickerActivity extends Activity implements View.OnClickListener {
    private View byCamera, byChoose, cancel;
    public final static int REQUEST_CODE_SELECT_PIC_KITKAT = 44;
    public final static int REQUEST_CODE_SELECT_PIC = 40;
    public final static int REQUEST_CODE_CAMERA = 10;
    public final static int REQUEST_CODE_CUT_PHOTO = 20;
    private boolean mIsCamera = false;
    private Uri mCameraUri, mPhotoUri;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView();
        initWidget();
        startInvoke();
    }

    public void setView() {
//        setTheme(R.style.dialog_base_style);
        setContentView(getIntent().getIntExtra("view", R.layout.dialog_select_image_way));
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(layoutParams);
    }

    public void initWidget() {
        byCamera = findViewById(R.id.image_pick_by_camera);
        byChoose = findViewById(R.id.image_pick_by_choose);
        cancel = findViewById(R.id.image_pick_cancel);
    }

    public void startInvoke() {
        try {
            byCamera.setOnClickListener(this);
            byChoose.setOnClickListener(this);
            cancel.setOnClickListener(this);
        } catch (NullPointerException e) {
            e.printStackTrace();
            new Throwable("layout中的控件ID为空").printStackTrace();
        }
    }

    public void onClick(View v) {
        if (v.getId() == R.id.image_pick_by_camera) {
            startCamera();
        } else if (v.getId() == R.id.image_pick_by_choose) {
            startSelectionDoc();
        } else if (v.getId() == R.id.image_pick_cancel) {
            setResult(RESULT_CANCELED);
            onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SELECT_PIC_KITKAT: {
                    mIsCamera = false;
                    if (null != data) {
                        String path = PhotoUtil.getPhotoPathByNew(this, data);
//                        String path ="";
//                        startCutPhoto(Uri.fromFile(new File(path)));//裁剪图片
                        mPhotoUri = Uri.fromFile(new File(path));
                        resultPhotoPath();
                    }
                    break;
                }
                case REQUEST_CODE_SELECT_PIC: {
                    mIsCamera = false;
                    if (null != data) {
                        String path = PhotoUtil.getPhotoPathByOld(this, data);
//                        String path = "";
//                        startCutPhoto(Uri.fromFile(new File(path)));//裁剪图片
                        mPhotoUri = Uri.fromFile(new File(path));
                        resultPhotoPath();
                    }
                    break;
                }
                case REQUEST_CODE_CAMERA: {
                    mIsCamera = true;
//                    startCutPhoto(mCameraUri);//裁剪图片
                    resultPhotoPath();
                    break;
                }
                case REQUEST_CODE_CUT_PHOTO: {
                    resultPhotoPath();
                    break;
                }

            }
        }
    }

    private void startCamera() {
        mCameraUri = getUri();
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE"); // 照相
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mCameraUri); // 指定图片输出地址
        startActivityForResult(intent, REQUEST_CODE_CAMERA); // 启动照相
    }

    private Uri getUri() {
        String mFileName = getPhotoName();
        File outputImage = new File(mFileName);
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 将File对象转换为Uri并启动照相程序
        return Uri.fromFile(outputImage);
    }

    private String getPhotoName() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        Date date = new Date(System.currentTimeMillis());
        return getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator
                + format.format(date) + ".jpg";
    }


    private void startSelectionDoc() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);// ACTION_OPEN_DOCUMENT
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/jpeg");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            startActivityForResult(intent, REQUEST_CODE_SELECT_PIC_KITKAT);
        } else {
            startActivityForResult(intent, REQUEST_CODE_SELECT_PIC);
        }
    }


    private void startCutPhoto(Uri oldPhotoUri) {
        Intent intent = new Intent("com.android.camera.action.CROP"); // 剪裁
        intent.putExtra("scale", true);
        // 设置宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 设置裁剪图片宽高
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.setDataAndType(oldPhotoUri, "image/*");
        if (mIsCamera) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mCameraUri);
        } else {
            mPhotoUri = getUri();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
        }

        // intent.putExtra("return-data", true);
        // 广播刷新相册
        // Intent intentBc = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        // intentBc.setData(mImageURI);
        // this.sendBroadcast(intentBc);
        startActivityForResult(intent, REQUEST_CODE_CUT_PHOTO); // 设置裁剪参数显示图片至ImageView
    }


    private void resultPhotoPath() {
        String path;
        if (mIsCamera) {
            path = mCameraUri.getPath();
        } else {
            path = mPhotoUri.getPath();
        }
        if (!TextUtils.isEmpty(path) && (path.endsWith("jpg") || path.endsWith("jpeg") || path.endsWith("png"))) {
            Intent intent = new Intent();
            intent.putExtra("path", path);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
