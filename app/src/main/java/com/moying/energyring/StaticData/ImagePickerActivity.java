package com.moying.energyring.StaticData;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.example.sanjay.selectorphotolibrary.SelectedPhotoActivity;
import com.example.sanjay.selectorphotolibrary.bean.ImgOptions;
import com.example.sanjay.selectorphotolibrary.utils.PermissionUtils;
import com.moying.energyring.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


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
public class ImagePickerActivity extends Activity implements View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback {
    private View byCamera, byChoose, cancel;
    public final static int CHOOSE_PHOTO = 40;
    public final static int REQUEST_CODE_CAMERA = 10;
    public final static int REQUEST_CODE_CUT_PHOTO = 20;
    private Uri mCameraUri, mImageUriFromFile;

    private static final String PERMISSION_WRITE_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final int REQUEST_PERMISSION_CODE = 267;

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
            /*申请拍照的权限*/
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PermissionUtils.requestPermission(this, PermissionUtils.CODE_CAMERA, permissionGrant);
            } else {
                startCamera();
            }
        } else if (v.getId() == R.id.image_pick_by_choose) {
            /*申请读取存储的权限*/
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PermissionUtils.requestPermission(this, PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE, permissionGrant);
            } else {
//                startSelectionDoc();
                choosePhoto();
            }
        } else if (v.getId() == R.id.image_pick_cancel) {
            setResult(RESULT_CANCELED);
            onBackPressed();
        }
    }

    private PermissionUtils.PermissionGrant permissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:
//                Toast.makeText(ImagePickerActivity.this, "读取存储权限已打开", Toast.LENGTH_SHORT).show();
//                    startSelectionDoc();
                    choosePhoto();
                    break;
                case PermissionUtils.CODE_CAMERA:
                    startCamera();
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

    private static final String EXTRA_DATA = "extra_data";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CHOOSE_PHOTO: {
//                    if (null != data) {
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                            handleImageOnKitKat(data);//4.4之后图片解析
//                        } else {
//                            handleImageBeforeKitKat(data);//4.4之前图片解析
//                        }
//                    }
                    if (resultCode == RESULT_OK) {
                        List<String> pathList = data.getStringArrayListExtra(EXTRA_DATA);
                        if (!pathList.isEmpty()) {
                            resultPhotoPath(pathList.get(0));
                        }
                    }
                    break;
                }
                case REQUEST_CODE_CAMERA: {
//                    startCutPhoto(mCameraUri);//裁剪图片
                    galleryAddPic(mImageUriFromFile);
                    resultPhotoPath(mImageUriFromFile.getPath());
                    break;
                }
                case REQUEST_CODE_CUT_PHOTO: {
                    resultPhotoPath(mImageUriFromFile.getPath());
                    break;
                }

            }
        }
    }


    private void startCamera() {
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//打开相机的Intent
        if (takePhotoIntent.resolveActivity(getPackageManager()) != null) {//这句作用是如果没有相机则该应用不会闪退，要是不加这句则当系统没有相机应用的时候该应用会闪退
            File imageFile = createImageFile();//创建用来保存照片的文件
            mImageUriFromFile = Uri.fromFile(imageFile);
            if (imageFile != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    /*7.0以上要通过FileProvider将File转化为Uri*/

                    mCameraUri = FileProvider.getUriForFile(this, ProviderUtil.getFileProviderName(this), imageFile);
//                    mCameraUri = FileProvider.getUriForFile(this, FILE_PROVIDER_AUTHORITY, imageFile);
                } else {
                    /*7.0以下则直接使用Uri的fromFile方法将File转化为Uri*/
                    mCameraUri = Uri.fromFile(imageFile);
                }
                takePhotoIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);//私有目录读写权限
                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCameraUri);//将用于输出的文件Uri传递给相机
                startActivityForResult(takePhotoIntent, REQUEST_CODE_CAMERA);//打开相机
            }
        }

    }

    /**
     * 创建用来存储图片的文件，以时间来命名就不会产生命名冲突
     *
     * @return 创建的图片文件
     */
    private File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = null;
        try {
            imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
    }

    /**
     * 将拍的照片添加到相册
     *
     * @param uri 拍的照片的Uri
     */
    private void galleryAddPic(Uri uri) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(uri);
        sendBroadcast(mediaScanIntent);
    }

    //调用自定义相册
    private void choosePhoto(){
        ImgOptions options = new ImgOptions(1, 1, true);
        startActivityForResult(SelectedPhotoActivity.makeIntent(this, options), CHOOSE_PHOTO);
    }

    private void startSelectionDoc() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);// ACTION_OPEN_DOCUMENT
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/jpeg");
//        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    /**
     * 4.4版本以下对返回的图片Uri的处理：
     * 就是从返回的Intent中取出图片Uri，直接显示就好
     *
     * @param data 调用系统相册之后返回的Uri
     */
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
//        displayImage(imagePath);
        resultPhotoPath(imagePath);
    }

    /**
     * 4.4版本以上对返回的图片Uri的处理：
     * 返回的Uri是经过封装的，要进行处理才能得到真实路径
     *
     * @param data 调用系统相册之后返回的Uri
     */
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果是document类型的Uri，则提供document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的uri，则进行普通处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的uri，则直接获取路径
            imagePath = uri.getPath();
        }
//        displayImage(imagePath);
        resultPhotoPath(imagePath);
    }

    /**
     * 将Uri转化为路径
     *
     * @param uri       要转化的Uri
     * @param selection 4.4之后需要解析Uri，因此需要该参数
     * @return 转化之后的路径
     */
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void resultPhotoPath(String photoPath) {
        if (!TextUtils.isEmpty(photoPath) && (photoPath.endsWith("jpg") || photoPath.endsWith("jpeg") || photoPath.endsWith("png"))) {
            Intent intent = new Intent();
            intent.putExtra("path", photoPath);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
