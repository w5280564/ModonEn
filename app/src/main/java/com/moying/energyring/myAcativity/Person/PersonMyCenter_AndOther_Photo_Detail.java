package com.moying.energyring.myAcativity.Person;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;
import com.google.gson.Gson;
import com.moying.energyring.Model.BaseDataInt_Model;
import com.moying.energyring.Model.Person_Photo_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.NoDoubleClickListener;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.StaticData.viewTouchDelegate;
import com.moying.energyring.myAcativity.Energy.Energy_WebDetail;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.HackyViewPager;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import me.relex.photodraweeview.OnPhotoTapListener;
import me.relex.photodraweeview.PhotoDraweeView;

import static com.moying.energyring.network.saveFile.getShareData;

public class PersonMyCenter_AndOther_Photo_Detail extends Activity implements HackyViewPager.OnPageChangeListener {
    private RelativeLayout papercontent;
    private ViewPager mPager;
    private List<String> myImgArr;
    private TextView countpage_Txt, save_Txt, content_Txt, talk_Txt, like_Txt;
    private int saveCount = 0; //保存图片tag
    private List<Person_Photo_Model.DataBean> Photo_Model;
    private ImageView energy_like;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personmycenter_andother_photo_detail);
        papercontent = (RelativeLayout) findViewById(R.id.papercontent);
        mPager = (ViewPager) findViewById(R.id.viewpager);
        countpage_Txt = (TextView) findViewById(R.id.countpage_Txt);
        save_Txt = (TextView) findViewById(R.id.save_Txt);
        View top_Rel = findViewById(R.id.top_Rel);
        View back_Img = findViewById(R.id.back_Img);
        viewTouchDelegate.expandViewTouchDelegate(back_Img, 100, 100, 100, 100);
        View bot_Rel = findViewById(R.id.bot_Rel);
        View other_Rel = findViewById(R.id.other_Rel);
        content_Txt = (TextView) findViewById(R.id.content_Txt);
        TextView look_Txt = (TextView) findViewById(R.id.look_Txt);
        viewTouchDelegate.expandViewTouchDelegate(look_Txt, 100, 100, 100, 100);

        ImageView energy_talk = (ImageView) findViewById(R.id.energy_talk);
        energy_like = (ImageView) findViewById(R.id.energy_like);
        talk_Txt = (TextView) findViewById(R.id.talk_Txt);
        like_Txt = (TextView) findViewById(R.id.like_Txt);
        LinearLayout like_Lin = (LinearLayout) findViewById(R.id.like_Lin);
        viewTouchDelegate.expandViewTouchDelegate(like_Lin, 100, 100, 100, 100);

        Intent intent = getIntent();

        Photo_Model = intent.getParcelableArrayListExtra("Photo_Model");
        myImgArr = (List<String>) intent.getSerializableExtra("imgArr");
        int tag = intent.getIntExtra("tag", 0);
        initViewPager(this, myImgArr, tag);

//        countpage_Txt.setText(tag + 1 + "/" + myImgArr.size());
        saveCount = tag;

        StaticData.ViewScale(top_Rel, 0, 88);
        StaticData.ViewScale(back_Img, 40, 38);
        StaticData.ViewScale(bot_Rel, 0, 220);
        StaticData.ViewScale(other_Rel, 0, 88);
        StaticData.ViewScale(energy_talk, 36, 34);
        StaticData.ViewScale(energy_like, 36, 36);

        save_Txt.setOnClickListener(new save_Txt());
        back_Img.setOnClickListener(new back_Img());
        look_Txt.setOnClickListener(new look_Txt());
        like_Lin.setOnClickListener(new like_Lin());

        initData(saveCount);
    }

    public class save_Txt implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            downLoadFile(myImgArr.get(saveCount));
        }
    }

    private class back_Img extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            finish();
        }
    }

    private class look_Txt extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            String content = Photo_Model.get(saveCount).getPostContent();
            String postId = Photo_Model.get(saveCount).getPostID() + "";
            String url = saveFile.BaseUrl + "Share/PostDetails?PostID=" + Photo_Model.get(saveCount).getPostID();
            Intent intent = new Intent(PersonMyCenter_AndOther_Photo_Detail.this, Energy_WebDetail.class);
            intent.putExtra("content", content);
            intent.putExtra("postId", postId);
            intent.putExtra("url", url);
            intent.putExtra("imgUrl", Photo_Model.get(saveCount).getFilePath());
            startActivity(intent);
        }
    }

    private class like_Lin extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            int PostID = Photo_Model.get(saveCount).getPostID();
            zanData(PersonMyCenter_AndOther_Photo_Detail.this, saveFile.BaseUrl + saveFile.PostLike_Url + "?PostID=" + PostID, saveCount);
        }
    }


    private void initData(int Count) {
        content_Txt.setText(Photo_Model.get(Count).getPostContent());
        talk_Txt.setText(Photo_Model.get(Count).getCommentNum() + "");
        like_Txt.setText(Photo_Model.get(Count).getLikes() + "");


        if (Photo_Model.get(Count).getIs_Like() == 1) {
            energy_like.setImageResource(R.drawable.photo_zan_select);
        } else {
            energy_like.setImageResource(R.drawable.photo_zan_icon);
        }
    }

    private List<ImageView> views;
    private int currentItem = 1;

    private void initViewPager(Context context, List<String> myImgArr, int tag) {
        if (views != null) {
            views.clear();
        }
        views = new ArrayList<>();
        int length = myImgArr.size();
        for (int i = 0; i < length; i++) {


            final PhotoDraweeView photoView = new PhotoDraweeView(context);
            photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Uri imgurl = Uri.parse(myImgArr.get(i));
            PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
            controller.setUri(imgurl);
            controller.setOldController(photoView.getController());
            controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
                @Override
                public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                    super.onFinalImageSet(id, imageInfo, animatable);
                    if (imageInfo == null || photoView == null) {
                        return;
                    }
                    photoView.update(imageInfo.getWidth(), imageInfo.getHeight());
                }
            });
            photoView.setController(controller.build());
            photoView.setOnPhotoTapListener(new OnPhotoTapListener() {//photoView点击事件
                @Override
                public void onPhotoTap(View view, float x, float y) {
//                    finish();
                }
            });
            views.add(photoView);
        }
        mPager.setAdapter(new myPagerAdapter());
        mPager.setCurrentItem(tag);
        mPager.addOnPageChangeListener(this);
    }

    class myPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        @Override
        public Object instantiateItem(View container, final int position) {
            ((ViewPager) container).addView(views.get(position));
            if (views.get(position) != null) {
                views.get(position).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        finish();
                    }
                });
            }
            return views.get(position);
        }
    }

    @Override//滑动时改变样式
    public void onPageSelected(int arg0) {
        int pageIndex = arg0;
        if (arg0 == 0) {
            arg0 = myImgArr.size();
        } else if (arg0 == myImgArr.size()) {
            pageIndex = 1;
        }
        if (arg0 != pageIndex) {
            currentItem = pageIndex;
        } else {
            currentItem = arg0;
        }

        for (int i = 0; i < views.size(); i++) {
            if (i == currentItem) {
                countpage_Txt.setText(i + 1 + "/" + myImgArr.size());
                saveCount = i;
            }
        }
        mPager.setCurrentItem(currentItem, false);

        initData(saveCount);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }


    public void zanData(final Context context, String baseUrl, final int pos) {
        RequestParams params = new RequestParams(baseUrl);
        if (getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    BaseDataInt_Model model = new Gson().fromJson(resultString, BaseDataInt_Model.class);
                    if (model.isIsSuccess()) {
                        Person_Photo_Model.DataBean oneData = Photo_Model.get(pos);
                        if (oneData.getIs_Like() == 1) {
                            oneData.setIs_Like(0);
                            if (oneData.getLikes() == 0) {
                                oneData.setLikes(0);
                            } else {
                                oneData.setLikes(oneData.getLikes() - 1);
                            }
                        } else {
                            oneData.setIs_Like(1);
                            oneData.setLikes(oneData.getLikes() + 1);
                        }
                        initData(pos);

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
                    context.startActivity(intent);
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


    /**
     * 下载文件
     */
    private void downLoadFile(String downloadUrl) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "EnergyM");//文件夹
        if (!appDir.exists()) {// 若不存在，创建文件夹
            appDir.mkdir();
        }
        int idx = downloadUrl.lastIndexOf(".");
        String ext = downloadUrl.substring(idx);
        final String fileName = System.currentTimeMillis() + ".jpg";//用时间命名，保持唯一性
        final File file = new File(appDir, fileName);

        RequestParams params = new RequestParams(downloadUrl);
        //设置断点续传
        params.setAutoResume(true);
        params.setSaveFilePath(file.getAbsolutePath());
        x.http().get(params, new Callback.CommonCallback<File>() {
            @Override
            public void onSuccess(File result) {
                try {
                    MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), fileName, null);
                    Toast.makeText(PersonMyCenter_AndOther_Photo_Detail.this, "保存成功", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(PersonMyCenter_AndOther_Photo_Detail.this, "保存失败", Toast.LENGTH_SHORT).show();
                }
                // 最后通知图库更新
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public static void delete_File(String path) {
        File filepath = new File(path);
        filepath.delete();
    }


}



