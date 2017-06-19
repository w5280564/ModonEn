package com.moying.energyring.myAcativity.Find;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.moying.energyring.Model.RadioList_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.StaticData.myMediaplayer;
import com.moying.energyring.myAcativity.Find.radioInfo.LocalRadioInfo;
import com.moying.energyring.myAcativity.Find.radioInfo.radio_Model;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.MySeekBar;
import com.moying.energyring.waylenBaseView.viewpagercards.Radio_CardItem;
import com.moying.energyring.waylenBaseView.viewpagercards.Radio_CardPagerAdapter;
import com.moying.energyring.waylenBaseView.viewpagercards.Radio_ShadowTransformer;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FindRadioListActivity extends AppCompatActivity {

    private SimpleDraweeView radio_bg;
    private ViewPager viewPager;
    private TextView radio_Name_Txt, countName_Txt, Time_Txt;
    private ImageView find_arrow_img;
    public ImageView radio_list_play, radio_list_left, radio_list_more, radio_list_right, radio_list_arr, colockplay_img, colockpush_img;
    private myMediaplayer myplayer;
    private MySeekBar radio_seekBar;
    private RelativeLayout radioplay_Rel, radioList_Rel, colockdown_Rel;
    private LinearLayout radioList_Lin;
    private TextView radio_cancel_Txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeAppCompat);
        setContentView(R.layout.activity_find_radio_list);
        initView();
        initData();

    }

    private void initView() {
        radio_bg = (SimpleDraweeView) findViewById(R.id.radio_bg);
        find_arrow_img = (ImageView) findViewById(R.id.find_arrow_img);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        radio_Name_Txt = (TextView) findViewById(R.id.radio_Name_Txt);
        countName_Txt = (TextView) findViewById(R.id.countName_Txt);
        Time_Txt = (TextView) findViewById(R.id.Time_Txt);
        radio_list_play = (ImageView) findViewById(R.id.radio_list_play);
        radio_list_left = (ImageView) findViewById(R.id.radio_list_left);
        radio_list_more = (ImageView) findViewById(R.id.radio_list_more);
        radio_list_right = (ImageView) findViewById(R.id.radio_list_right);
        radio_list_arr = (ImageView) findViewById(R.id.radio_list_arr);
        radio_seekBar = (MySeekBar) findViewById(R.id.radio_seekBar);

        radioplay_Rel = (RelativeLayout) findViewById(R.id.radioplay_Rel);
        radioList_Lin = (LinearLayout) findViewById(R.id.radioList_Lin);
        radioList_Rel = (RelativeLayout) findViewById(R.id.radioList_Rel);
        radio_cancel_Txt = (TextView) findViewById(R.id.radio_cancel_Txt);
        colockdown_Rel = (RelativeLayout) findViewById(R.id.colockdown_Rel);
        colockplay_img = (ImageView) findViewById(R.id.colockplay_img);
        colockpush_img = (ImageView) findViewById(R.id.colockpush_img);
        TextView down_cancel_Txt = (TextView) findViewById(R.id.down_cancel_Txt);
        LinearLayout down_colockplay_Lin = (LinearLayout) findViewById(R.id.down_colockplay_Lin);
        LinearLayout down_colockpush_Lin = (LinearLayout) findViewById(R.id.down_colockpush_Lin);
        StaticData.ViewScale(viewPager, 0, 628);
        StaticData.ViewScale(find_arrow_img, 48, 48);
        StaticData.ViewScale(radio_list_play, 170, 170);
        StaticData.ViewScale(radio_list_left, 68, 68);
        StaticData.ViewScale(radio_list_more, 60, 60);
        StaticData.ViewScale(radio_list_right, 68, 68);
        StaticData.ViewScale(radio_list_arr, 60, 60);
        StaticData.ViewScale(radio_seekBar, 630, 0);
        StaticData.ViewScale(radio_cancel_Txt, 0, 116);
        StaticData.ViewScale(down_cancel_Txt, 0, 116);
        StaticData.ViewScale(colockplay_img, 72, 72);
        StaticData.ViewScale(colockpush_img, 72, 72);
        find_arrow_img.setOnClickListener(new find_arrow_img());
        radio_list_play.setOnClickListener(new radio_list_play());
        radio_list_left.setOnClickListener(new radio_list_left());
        radio_list_right.setOnClickListener(new radio_list_right());
        radio_list_more.setOnClickListener(new radio_list_more());
        radio_list_arr.setOnClickListener(new radio_list_arr());
        radio_cancel_Txt.setOnClickListener(new radio_cancel_Txt());
        down_cancel_Txt.setOnClickListener(new down_cancel_Txt());
        down_colockplay_Lin.setOnClickListener(new down_colockplay_Lin());
        down_colockpush_Lin.setOnClickListener(new down_colockpush_Lin());
    }

    private void initData() {
//        RadioList_Model radioModel = (RadioList_Model) getIntent() .getSerializableExtra("radioList");
        int PageIndex = 1;
        int pageSize = 20;
        RadioData(saveFile.BaseUrl + saveFile.RadioList_Url + "?PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }

    LocalRadioInfo radioInfo;

    private void initRadioData() {
        myplayer = myMediaplayer.getInstance();
        radioInfo = new LocalRadioInfo(this);


        String stopCount = saveFile.getShareData("stopCount", this);//定时播放关闭时间
        if (stopCount.equals("false") || stopCount.equals("关闭")) {
            countName_Txt.setText("收听时长");

            if (!radioInfo.getUserInfo().getRadioName().equals("false")) {
                if (radioInfo.getUserInfo().getRadioisPlay() == 2) {
                    //正在播放
                    myplayer.playUrl(radioInfo.getUserInfo().getRadioUrl(),radio_list_play);
                    startUpTimer();//启动计时器
                    radio_list_play.setBackgroundResource(R.drawable.radio_list_push);
                } else {
                    radio_list_play.setBackgroundResource(R.drawable.radio_list_play);
                }
                int idex = radioModel.getData().size();
                String locaName = radioInfo.getUserInfo().getRadioName();
                for (int i = 0; i < idex; i++) {
                    String radioName = radioModel.getData().get(i).getRadioName();
                    if (locaName.equals(radioName)) {
                        viewPager.setCurrentItem(i, true);

                        break;
                    }
                }
            }
        } else {

            int idex = radioModel.getData().size();
            String locaName = saveFile.getShareData("englishVideo", this);
            for (int i = 0; i < idex; i++) {
                String radioName = radioModel.getData().get(i).getRadioName();
                if (locaName.equals(radioName)) {
                    viewPager.setCurrentItem(i, true);
                    myplayer.playUrl(radioInfo.getUserInfo().getRadioUrl(),radio_list_play);
                    break;
                }
            }
            startDownTimer();//启动倒计时
        }

    }

    private void startUpTimer() {
        initAddStatus(0);
        timerStatus = PREPARE;
        startTimer();
        timerStatus = START;
    }

    public void startDownTimer() {
        initTimerStatus();//倒计时开始
        timerStatus = PREPARE;
        timer_couting = Long.parseLong(saveFile.getShareData("stopCount", FindRadioListActivity.this));
        radio_list_play.setBackgroundResource(R.drawable.radio_list_push);
        countName_Txt.setText("倒计时长");
        countDownTimer();
        timerStatus = START;
    }


    private class find_arrow_img implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    //打開電台列表
    private class radio_list_more implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (radioplay_Rel.isShown()) {
                radioplay_Rel.setVisibility(View.INVISIBLE);
                radioList_Rel.setVisibility(View.VISIBLE);
                RadioList(radioList_Lin, radioModel);
            }
        }
    }

    //關閉電台列表
    private class radio_cancel_Txt implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (radioList_Rel.isShown()) {
                radioplay_Rel.setVisibility(View.VISIBLE);
                radioList_Rel.setVisibility(View.INVISIBLE);
            }
        }
    }

    //打開定時頁面
    private class down_cancel_Txt implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (colockdown_Rel.isShown()) {
                radioplay_Rel.setVisibility(View.VISIBLE);
                colockdown_Rel.setVisibility(View.INVISIBLE);
            }
        }
    }

    //關閉定時頁面
    private class radio_list_arr implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (radioplay_Rel.isShown()) {
                radioplay_Rel.setVisibility(View.INVISIBLE);
                colockdown_Rel.setVisibility(View.VISIBLE);
            }
        }
    }

    //打開定時鬧鐘
    private class down_colockplay_Lin implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(FindRadioListActivity.this, Person_Play.class);
            startActivity(intent);
        }
    }

    //打開倒計時時長
    private class down_colockpush_Lin implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(FindRadioListActivity.this, Person_StopVideo.class);
            startActivity(intent);

        }
    }

    private boolean isUpfristTimer = false;
    private boolean downfristTimer = false;


    private class radio_list_play implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int pos = viewPager.getCurrentItem();
            RadioList_Model.DataBean oneData = radioModel.getData().get(pos);

//            if (myplayer.mediaPlayer == null)//添加是空判断 空第一次播放 监听mediaplay事件加载完成 timer开始加载

            if (myplayer.mediaPlayer != null && myplayer.mediaPlayer.isPlaying()) {

                if (oneData.getRadioName().equals(saveFile.getShareData("englishVideo", FindRadioListActivity.this))) {//定时播放的电台暂停
                    stopSendTimerTask(); //暂停播放
                    timerStatus = PREPARE;
                    mHandler.sendEmptyMessage(2);
                    saveFile.saveShareData("stopCount", timer_couting + "", FindRadioListActivity.this);
                } else {
                    stopSendTimerTask(); //暂停播放
                    timerStatus = PREPARE;
                    mHandler.sendEmptyMessage(1);

//                    initAddStatus(0);
//                    timerStatus = PREPARE;
//                    startTimer();
                }


                AddOneLocaInfo(oneData, 0);//存储数据
                radio_list_play.setBackgroundResource(R.drawable.radio_list_play);
                myplayer.pause();
            } else {
                AddOneLocaInfo(oneData, 2);//存储数据
                radio_list_play.setBackgroundResource(R.drawable.radio_list_push);
                myplayer.playUrl(oneData.getRadioUrl(),radio_list_play);

                if (oneData.getRadioName().equals(saveFile.getShareData("englishVideo", FindRadioListActivity.this))) {//定时播放的电台暂停
                    String stopCount = saveFile.getShareData("stopCount", FindRadioListActivity.this);//定时播放关闭时间
                    if (stopCount.equals("false") || stopCount.equals("关闭")) {

                    } else {
                        startDownTimer();
                    }
                } else {

//                    isUpfristTimer =! isUpfristTimer;
//                    if (isUpfristTimer){
                    initAddStatus((int) uptimer_couting);
                    timerStatus = PREPARE;
                    startTimer();

//                    myplayer.mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                        @Override
//                        public void onPrepared(MediaPlayer mediaPlayer) {
//                            mediaPlayer.start();
//                            radio_list_play.setEnabled(true);
//
//                        }
//                    });



//                    }


                }

            }
        }
    }


    //存储一条电台数据
    public void AddOneLocaInfo(RadioList_Model.DataBean onedata, int state) {
        radio_Model model = new radio_Model();
        model.setRadioName(onedata.getRadioName());
        model.setRadioImgpath(onedata.getRadio_Icon());
        model.setRadioUrl(onedata.getRadioUrl());
        model.setRadioisPlay(state);
        radioInfo.setUserInfo(model);
    }

    private class radio_list_left implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int pos = viewPager.getCurrentItem();
            if (pos > 0) {
                pos -= 1;
                viewPager.setCurrentItem(pos, true);
                RadioList_Model.DataBean oneData = radioModel.getData().get(pos);
                if (myplayer.mediaPlayer != null && myplayer.mediaPlayer.isPlaying()) {
                    AddOneLocaInfo(oneData, 2);//存储数据
                    isplayRadio(oneData, pos);

                    if (oneData.getRadioName().equals(saveFile.getShareData("englishVideo", FindRadioListActivity.this))) {//定时播放的电台暂停
                        stopSendTimerTask(); //暂停播放
                        timerStatus = PREPARE;
                        mHandler.sendEmptyMessage(2);
                        saveFile.saveShareData("stopCount", timer_couting + "", FindRadioListActivity.this);
                    }


                } else {
                    AddOneLocaInfo(oneData, 0);
                }
            }
        }
    }

    private class radio_list_right implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int pos = viewPager.getCurrentItem() + 1;
            if (pos < radioModel.getData().size()) {
                RadioList_Model.DataBean oneData = radioModel.getData().get(pos);
                if (myplayer.mediaPlayer != null && myplayer.mediaPlayer.isPlaying()) {//数据页数不需要加一页
                    isplayRadio(oneData, pos);
                    AddOneLocaInfo(oneData, 2);//存储数据
                } else {
                    AddOneLocaInfo(oneData, 0);
                }
                viewPager.setCurrentItem(pos, true);
            }
        }
    }


    public void isplayRadio(RadioList_Model.DataBean oneData, int pos) { //正在播放需要停止后重新加载
        myplayer.stop();
        myplayer.playUrl(oneData.getRadioUrl(),radio_list_play);
    }


    private Radio_CardPagerAdapter mCardAdapter;
    private Radio_ShadowTransformer mCardShadowTransformer;

    private void initList() {
        mCardAdapter = new Radio_CardPagerAdapter();
        for (int i = 0; i < radioModel.getData().size(); i++) {
            RadioList_Model.DataBean oneData = radioModel.getData().get(i);
            mCardAdapter.addCardItem(new Radio_CardItem(oneData.getRadio_Bg(), oneData.getRadioName()));
        }
        mCardShadowTransformer = new Radio_ShadowTransformer(FindRadioListActivity.this, radioModel, viewPager, mCardAdapter, radio_bg, radio_Name_Txt);
        mCardShadowTransformer.enableScaling(true);
        viewPager.setAdapter(mCardAdapter);
        viewPager.setPageTransformer(false, mCardShadowTransformer);
        viewPager.setOffscreenPageLimit(3);
//        viewPager.setCurrentItem(0);
    }

    //电台列表
    public void RadioList(LinearLayout myFlow, final RadioList_Model myBean) {
        if (myFlow != null) {
            myFlow.removeAllViews();
        }
        List<SimpleDraweeView> mySimpleArr = new ArrayList<>();
        int size = myBean.getData().size();
        for (int i = 0; i < size; i++) {
            RelativeLayout myview = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.radiolist, null);
            RadioGroup.LayoutParams itemParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            StaticData.layoutParamsScale(itemParams, 115, 0);
            myview.setLayoutParams(itemParams);
            final SimpleDraweeView mySimple = (SimpleDraweeView) myview.findViewById(R.id.redio_sim);
//            mySimple.setScaleType(ImageView.ScaleType.FIT_XY);
            StaticData.ViewScale(mySimple, 80, 80);
            RadioList_Model.DataBean oneList = myBean.getData().get(i);
            if (oneList.getRadio_Icon() != null) {
                Uri imgUri = Uri.parse(oneList.getRadio_Icon());
                mySimple.setImageURI(imgUri);
            }
            TextView banner_Txt = (TextView) myview.findViewById(R.id.banner_Txt);
            banner_Txt.setText(oneList.getRadioName());
            mySimple.setTag(i);
            mySimpleArr.add(mySimple);
            myFlow.addView(myview);
            mySimple.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (radioInfo.getUserInfo().getRadioisPlay() == 2) {//正在播放
                        myplayer.playUrl(radioInfo.getUserInfo().getRadioUrl(),radio_list_play);
                        radio_list_play.setBackgroundResource(R.drawable.radio_list_push);
                    } else {
                        radio_list_play.setBackgroundResource(R.drawable.radio_list_play);
                    }
                    int tag = (int) v.getTag();
                    viewPager.setCurrentItem(tag, true);
                    if (radioList_Rel.isShown()) {
                        radioList_Rel.setVisibility(View.INVISIBLE);
                        radioplay_Rel.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }


    RadioList_Model radioModel;

    //电台
    public void RadioData(String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    radioModel = new Gson().fromJson(resultString, RadioList_Model.class);
                    if (radioModel.isIsSuccess() && !radioModel.getData().equals("[]")) {
                        if (radioModel.getData().size() > 0) {
                            RadioList_Model.DataBean oneData = radioModel.getData().get(0);
                            radio_Name_Txt.setText(oneData.getRadioName());
                            Uri uri = Uri.parse(oneData.getRadio_Bg_Dim());
                            radio_bg.setImageURI(uri);
                        }
                        initList();
                        initRadioData();
//                        radio_Name.setText(oneData.getRadioName());
                    } else {
                        Toast.makeText(FindRadioListActivity.this, "数据获取失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(FindRadioListActivity.this, "数据获取失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
            }

            @Override
            public void onCancelled(CancelledException e) {
            }

            @Override
            public void onFinished() {
            }
        });
    }


    public final int PREPARE = 0;
    public final int START = 1;
    public final int PASUSE = 2;
    private long timer_unit = 1000;//计时间隔
    private long timerAdd = timer_unit * 60;//计时总时长到达后添加时间
    //    public  long distination_total = timer_unit * 60 * 10;
    public long distination_total = timer_unit * 60; //总时长
    private long timer_couting;
    private long uptimer_couting;
    private int timerStatus = PREPARE;
    private TimerTask timerTask;
    private Timer timer = null;

    //初始化
    private void startTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
        timer = new Timer();
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        timerTask = new MyTimerTask();
        timer.scheduleAtFixedRate(timerTask, 0, timer_unit);
//        timer.schedule(timerTask, 0, timer_unit);
    }

    /**
     * 清空监听的每条消息的发送时间处理消息超时
     * 这里关闭掉Timer 与 TimerTask
     */
    private void stopSendTimerTask() {
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }

    /**
     * formate timer shown in textview
     *
     * @param time
     * @return
     */
    private String formateTimer(long time) {
        String str = "00:00:00";
        int hour = 0;
        if (time >= 1000 * 3600) {
            hour = (int) (time / (1000 * 3600));
            time -= hour * 1000 * 3600;
        }
        int minute = 0;
        if (time >= 1000 * 60) {
            minute = (int) (time / (1000 * 60));
            time -= minute * 1000 * 60;
        }
        int sec = (int) (time / 1000);
//        str = formateNumber(hour) + ":" + formateNumber(minute) + ":" + formateNumber(sec);
        str = formateNumber(minute) + ":" + formateNumber(sec);
        return str;
    }

    /**
     * formate time number with two numbers auto add 0
     *
     * @param time
     * @return
     */
    private String formateNumber(int time) {
        return String.format("%02d", time);
    }


    //计时器初始化
    private void initAddStatus(int time) {
        timerStatus = PREPARE;
        uptimer_couting = time;
        radio_seekBar.setMax((int) distination_total);
    }

    //计时器添加时间
    private void initTimeAdd() {
        timerStatus = PREPARE;
        uptimer_couting = distination_total;
        distination_total += timerAdd;
        radio_seekBar.setProgress((int) distination_total);
        radio_seekBar.setMax((int) distination_total);
    }


    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
//            if (saveFile.getShareData("englishVideo", FindRadioListActivity.this).equals("false")) {
            uptimer_couting += timer_unit;
            if (uptimer_couting == distination_total) {
                cancel();
                initTimeAdd();
                timerStatus = PREPARE;
                startTimer();
            }
//            } else {
//
//            }

            mHandler.sendEmptyMessage(1);
        }
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) { //up是1 down是2
                case 1:
                    Time_Txt.setText(formateTimer(uptimer_couting));
                    radio_seekBar.setProgress((int) uptimer_couting);
//                    radio_seekBar.setProgress(1000 * playProgress / holder.videoView.getDuration());
                    break;
                case 2:
                    Time_Txt.setText(formateTimer(timer_couting));
                    radio_seekBar.setProgress((int) timer_couting);
//                    radio_seekBar.setProgress(1000 * playProgress / holder.videoView.getDuration());
                    break;
            }
        }
    };


    //初始化
    private void countDownTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
        timer = new Timer();
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        timerTask = new countDownTimerTask();
        timer.scheduleAtFixedRate(timerTask, 0, timer_unit);
    }

    /**
     * count down task
     */
    class countDownTimerTask extends TimerTask {
        @Override
        public void run() {
            timer_couting -= timer_unit;
            saveFile.saveShareData("stopCount", timer_couting + "", FindRadioListActivity.this);
            if (timer_couting == 0 || timer_couting < 0) {
                cancel();
                timer_couting = 0;
//                myImg.get(tag_postion).setVisibility(View.GONE);
                saveFile.saveShareData("stopCount", "false", FindRadioListActivity.this);
                myplayer.stop();
            }
            mHandler.sendEmptyMessage(2);
        }
    }

    /**
     * init timer status
     */
    private void initTimerStatus() {
        timerStatus = PREPARE;
        timer_couting = distination_total;
        radio_seekBar.setMax((int) distination_total);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopSendTimerTask();
    }
}
