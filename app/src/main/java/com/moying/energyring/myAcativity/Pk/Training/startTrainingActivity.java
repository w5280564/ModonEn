package com.moying.energyring.myAcativity.Pk.Training;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanjay.selectorphotolibrary.utils.PermissionUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.kyleduo.switchbutton.SwitchButton;
import com.moying.energyring.Model.BaseDataString_Model;
import com.moying.energyring.Model.Base_Model;
import com.moying.energyring.Model.JiFenAndBadge_Model;
import com.moying.energyring.Model.TrainFileList_Model;
import com.moying.energyring.Model.Training_Detail_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.basePopup.showCountDown;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.myAcativity.MainActivity;
import com.moying.energyring.myAcativity.Pk.Pk_AddReport_Succ;
import com.moying.energyring.myAcativity.Pk.Training.Inteface.OnDownLoadBackListener;
import com.moying.energyring.myAcativity.Pk.Training.constant.DownLoadConstant;
import com.moying.energyring.myAcativity.Pk.Training.service.DownloadService;
import com.moying.energyring.myAcativity.Pk.Training.utils.FileInfo;
import com.moying.energyring.myAcativity.Pk.Training.utils.FileUtil;
import com.moying.energyring.myAcativity.Pk.Training.utils.GetFilesUtils;
import com.moying.energyring.myAcativity.Pk.Training.utils.NetBroadcastReceiver;
import com.moying.energyring.myAcativity.Pk.Training.utils.NetUtil;
import com.moying.energyring.myAcativity.Pk.Training.utils.TrainBGMMediaPlayer;
import com.moying.energyring.myAcativity.Pk.Training.utils.TrainMediaPlayer;
import com.moying.energyring.myAcativity.Pk.Training.utils.ZipUtils;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.BasePopupWindow;
import com.moying.energyring.waylenBaseView.CountDownView;
import com.moying.energyring.waylenBaseView.HorizontalselectedView;
import com.moying.energyring.waylenBaseView.MySeekBar;
import com.moying.energyring.waylenBaseView.WaveView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class startTrainingActivity extends Activity implements WaveView.UpdateLoadingListener, NetBroadcastReceiver.NetChangeListener {

    private MySeekBar titleMybar;
    private Button set_Btn;
    private TextView time_Txt, quan_Txt, name_Txt, count_Txt, coach_Txt;
    private WaveView waveView;
    private ImageView training_push_Img;
    private static final String BASE_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator;
    //    private ProgressDialog progressDialog;
    String url, path;
    private boolean popisShow = false;
    private String TrainID;
    private DownloadService.MyBinder mBinder;
    private ArrayList<FileInfo> mFileList;
    FileInfo fileData;

    private NetBroadcastReceiver netBroadcastReceiver;
    public static NetBroadcastReceiver.NetChangeListener listener;
    private int netType;
    private boolean Is_HaveCommon = true;
    private TrainMediaPlayer mediaPlayer;
    private TrainBGMMediaPlayer BGMPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        listener = this;
        initNetState();
        checkNet();
        initView();
        initData(true);

    }

    private void initNetState() {
        //Android 7.0以上需要动态注册
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //实例化IntentFilter对象
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            netBroadcastReceiver = new NetBroadcastReceiver();
            //注册广播接收
            registerReceiver(netBroadcastReceiver, filter);
        }
    }

    /**
     * 初始化时判断有没有网络
     */
    public boolean checkNet() {
        this.netType = NetUtil.getNetWorkState(startTrainingActivity.this);
        if (!isNetConnect()) {
            //网络异常，请检查网络
            showNetDialog();
//            T.showShort("网络异常，请检查网络，哈哈");
        }
        return isNetConnect();
    }

    @Override
    public void onChangeListener(int status) {
        if (status == -1) {
            //网络异常，请检查网络
            showNetDialog();
        } else if (status == 0) {
            //4G网络状态
            if (popupDown != null) {
                noConnect_Lin.setVisibility(View.GONE);
                mobile_Lin.setVisibility(View.VISIBLE);
                mBinder.DownLoadStop(fileData);
            }

        } else if (status == 1) {
            //无线网络恢复正常
            if (popupDown != null) {
                noConnect_Lin.setVisibility(View.GONE);
//                mBinder.DownLoadStart(fileData);
            }
        }
//        if (!isNetConnect()) {
//            网络异常，请检查网络
//            showNetDialog();
//        } else {
        //网络恢复正常
//            if (popupDown != null) {
//                noConnect_Lin.setVisibility(View.GONE);
//                mBinder.DownLoadStart(fileData);
//            }
//        }

    }

    //弹出设置网络框
    private void showNetDialog() {
        //线程是否在下载
        if (popupDown != null && fileData != null) {
            if (fileData.getDownType() == 2) {
                mBinder.DownLoadStop(fileData);
            } else {
                mBinder.DownLoadStart(fileData);//没网 会type==5 线程会被取消 所以开启线程
            }
        } else {
            if (popupDown.isShowing()) {
                noConnect_Lin.setVisibility(View.VISIBLE);
            }
        }
    }

    //服务中需要恢复 弹窗进度 放在接口确认是否弹出再启动后台服务
    private void initService() {
        Intent intent = new Intent(this, DownloadService.class);
        bindService(intent, mDownLoadConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * 与Service连接时交互的类
     */
    private ServiceConnection mDownLoadConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mBinder = (DownloadService.MyBinder) service;
            mBinder.registDownLoadListener(loadBackListener);//注册观察者

            mFileList = mBinder.getCurrentList();//先从service中获取当前列表
            if (mFileList.size() == 0) {
                mFileList = mBinder.getCurrentListFShareP();
            }
            refresh(mFileList);
        }

        // 与服务端连接异常丢失时才调用，调用unBindService不调用此方法哎
        public void onServiceDisconnected(ComponentName className) {
            mBinder.unregistDownLoadListener(loadBackListener);
        }
    };

    //下载列表状态回调
    private OnDownLoadBackListener loadBackListener = new OnDownLoadBackListener() {
        @Override
        public void onDownloadSize(ArrayList<FileInfo> list) {
//            if(mAdapter != null){
//                mAdapter.setData(list);
//            }
            refresh(list);
        }
    };

    private void refresh(ArrayList<FileInfo> list) {
        if (list != null && !list.isEmpty()) {// list不为null，且list不是empty
            int index = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId() == Integer.parseInt(TrainID)) {
                    index = i;
                    break;
                }
            }

            fileData = list.get(index);
            int[] currentSize = new int[list.size()];
            switch (fileData.getDownType()) {
                case 0://等待
//                viewHolder.netSpeed.setText("准备下载...");
                    start_Txt.setText("等待中");
                    trainCount_Txt.setVisibility(View.GONE);
                    playone_bar.setVisibility(View.GONE);
                    noConnect_Lin.setVisibility(View.GONE);
                    break;
                case 1://开始下载
//                viewHolder.netSpeed.setText("开始下载");
                    trainCount_Txt.setVisibility(View.VISIBLE);
                    playone_bar.setVisibility(View.VISIBLE);
                    noConnect_Lin.setVisibility(View.GONE);
                    start_Txt.setText("开始下载");
                    break;
                case 2://下载中
//                viewHolder.startButton.setVisibility(View.GONE);
//                viewHolder.stop_button.setVisibility(View.VISIBLE);
                    if (currentSize[index] == 0) {
                        currentSize[index] = list.get(index).getDownlenth();
                    } else if (list.get(index).getDownlenth() - currentSize[index] <= 0) {
//                    viewHolder.netSpeed.setVisibility(View.GONE);
                    } else {
//                    viewHolder.netSpeed.setVisibility(View.VISIBLE);
//                    viewHolder.netSpeed.setText(NetUtil.FormetFileSize(list.get(index).getDownlenth() - currentSize[index])+"/s");
//                    viewHolder.netSpeed.setTextColor(Color.GREEN);
                        currentSize[index] = list.get(index).getDownlenth();
                    }
                    trainCount_Txt.setVisibility(View.VISIBLE);
                    playone_bar.setVisibility(View.VISIBLE);
                    noConnect_Lin.setVisibility(View.GONE);
                    start_Txt.setText("下载中");
                    break;
                case 3://下载成功
                    //通知service停止下载
                    mBinder.deleyeCurrentList(fileData);
                    trainCount_Txt.setVisibility(View.GONE);
                    playone_bar.setVisibility(View.GONE);
                    noConnect_Lin.setVisibility(View.GONE);
                    start_Txt.setText("下载完成");
//                    finish();

                    try {
                        // 解压文件
                        String zipString = DownLoadConstant.PATH + getfileName(modelDown.getData());//解压文件地址
                        String afterStr = DownLoadConstant.PATH; //解压后的地址
                        ZipUtils.upZipFile(zipString, afterStr);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (popupDown != null) {
                        popupDown.dismiss();
                    }

                    mediaStartTrain(trainPos, 0);
                    BGMPlayerStart(getBgmName(oneData.getBGMFileName()));
                    BGMname = oneData.getBGMFileName().replace(".mp3", "");
                    saveFile.saveShareData("BGMname", BGMname, startTrainingActivity.this);
                    saveFile.saveShareData("trainBGM_push", "true", startTrainingActivity.this);

                    break;
                case 4://下载失败
//                viewHolder.netSpeed.setText("下载失败");
                    if (NetUtil.currentNetwork(startTrainingActivity.this) == 2) {
//                        Toast.makeText(startTrainingActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                        noConnect_Lin.setVisibility(View.VISIBLE);
                    }
//                    if (netType == -1) {
//                        //网络异常，请检查网络
//                        noConnect_Lin.setVisibility(View.VISIBLE);
//                    }

                    start_Txt.setText("下载失败");
//                    NetUtil.currentNetwork(startTrainingActivity.this);
                    break;
                case 5://下载取消
//                viewHolder.netSpeed.setText("取消成功");
                    trainCount_Txt.setVisibility(View.VISIBLE);
                    playone_bar.setVisibility(View.VISIBLE);
                    noConnect_Lin.setVisibility(View.GONE);
                    start_Txt.setText("已暂停");
                    break;
            }

            trainCount_Txt.setText(FileUtil.getFormatSize(fileData.getDownlenth()) + "/" + FileUtil.getFormatSize(fileData.getLength()));
            playone_bar.setMax(fileData.getLength());
            playone_bar.setProgress(fileData.getDownlenth());
        } else {

//            trainCount_Txt.setText("0");

        }

    }


    @Override
    public void onDestroy() {
        if (mBinder != null) {
            unbindService(mDownLoadConnection);//解绑service
            mBinder.unregistDownLoadListener(loadBackListener);//注销观察者
        }
        stopSendTimerTask();
        mediaPlayer.stop();
        BGMPlayer.stop();
        super.onDestroy();
    }

    private void initView() {
        titleMybar = (MySeekBar) findViewById(R.id.titleMybar);
        name_Txt = (TextView) findViewById(R.id.name_Txt);
        quan_Txt = (TextView) findViewById(R.id.quan_Txt);
        waveView = (WaveView) findViewById(R.id.waveView);
        coach_Txt = (TextView) findViewById(R.id.coach_Txt);

        training_push_Img = (ImageView) findViewById(R.id.training_push_Img);
        count_Txt = (TextView) findViewById(R.id.count_Txt);
        time_Txt = (TextView) findViewById(R.id.time_Txt);
        set_Btn = (Button) findViewById(R.id.set_Btn);

        StaticData.ViewScale(titleMybar, 0, 120);
        StaticData.ViewScale(waveView, 326, 326);
        StaticData.ViewScale(training_push_Img, 326, 326);
        StaticData.ViewScale(set_Btn, 80, 80);

//        time_Txt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                waveView.animatorPause();
//            }
//        });

//        player(backPlayer, "bgm/bgm_1.mp3", 0.1f);
//        player(xunPlayer, "deep.mp3", 0.5f);


        waveView.setUpdateListener(this);
        training_push_Img.setOnClickListener(new training_push_Img());
        set_Btn.setOnClickListener(new set_Btn());
    }

    private void initData(boolean isDown) {
        mediaPlayer = TrainMediaPlayer.getInstance();
        BGMPlayer = TrainBGMMediaPlayer.getInstance();
        Intent intent = getIntent();
        TrainID = intent.getStringExtra("TrainID");

        todayDetailData(startTrainingActivity.this, saveFile.BaseUrl + saveFile.TrainDetail_Get_Url + "?TrainID=" + TrainID, isDown);
    }

    private void initDataTrainUpd(boolean isFinish) {
        int GroupNo = modelDetail.getData().getPro_Train().getCurrGroupNo();
        int Duration = (int) (uptimer_couting / 1000);
        TrainUpdData(startTrainingActivity.this, saveFile.BaseUrl + saveFile.Train_GroupUpd_Url + "?TrainID=" + TrainID + "&GroupNo=" + GroupNo + "&Duration=" + Duration, isFinish);
    }

    private class set_Btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            view.setEnabled(false);
            showChangeVolume(startTrainingActivity.this, set_Btn);
        }
    }


    private class training_push_Img implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            training_push_Img.setEnabled(false);
            stopSendTimerTask();//停止计时器
//            waveView.animatorPause();

//            paush(backPlayer);
//            paush(xunPlayer);
            training_Push();
            showPaush(startTrainingActivity.this, training_push_Img);
//            Intent intent = new Intent(startTrainingActivity.this,)
        }
    }

//    public boolean isupdate = false;

    //一组训练结束 进度到100
    @Override
    public void updateListener(int progress) {
//        Log.e("进度", "当前进度" + progress);
//        Intent intent = new Intent(startTrainingActivity.this,trainingCountDown.class);
//        startActivity(intent);
        stopSendTimerTask();
        ++trainPos;
        if (trainPos < modelDetail.getData().getAudio_List().size()) {
            showCountDown(startTrainingActivity.this, set_Btn);
            initDataTrainUpd(false);//上传更新数据
        } else {
            initDataTrainUpd(true);//训练完成
        }

    }

    private void showCountDown(final Context mContext, View view) {
        waveRestart();

        int time = modelDetail.getData().getPro_Train().getRestInterval();
        String projectName = oneData.getProjectName();
        int currtGroup = oneData.getCurrGroupNo();
        int GroupCount = oneData.getGroupCount();
        final showCountDown showCountDown = new showCountDown(mContext, set_Btn, time, 0, projectName, currtGroup, GroupCount);
        final CountDownView countdown_View = (CountDownView) showCountDown.getContentView().findViewById(R.id.countdown_View);
        countdown_View.setAddCountDownListener(new CountDownView.OnCountDownFinishListener() {
            @Override
            public void countDownFinished() {
                if (showCountDown.isShowing()) {
                    showCountDown.dismiss();
                    mediaStartTrain(trainPos, 0);
                }
            }
        });

        showCountDown.getContentView().findViewById(R.id.my_Rel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showCountDown.isShowing()) {
//                    countdown_View.stopAnimator();
                    showCountDown.dismiss();
                    p = 0;
                    mediaStartTrain(trainPos, 0);
                }
            }
        });
    }

    //完成一组训练
    private void waveRestart() {
        waveView.restart();//进度复原
        String countStr = "0/" + modelDetail.getData().getPro_Train().getGroupNum();
        count_Txt.setText(countStr);
    }


    private void showPaush(final Context mContext, View view) {
        View contentView = View.inflate(mContext, R.layout.training_paush, null);
        final BasePopupWindow popupPaush = new BasePopupWindow(mContext);
//        popupPaush.setmShowAlpha(0.5f);
        popupPaush.setBackgroundDrawable(new ColorDrawable(0x1a000000));
        popupPaush.setWidth(RadioGroup.LayoutParams.MATCH_PARENT);
        popupPaush.setHeight(RadioGroup.LayoutParams.MATCH_PARENT);
        popupPaush.setFocusable(false);
        popupPaush.setTouchable(true);
        popupPaush.setContentView(contentView);
//        popupWindow.showAsDropDown(view, 0, 0);
        popupPaush.showAtLocation(contentView, Gravity.CENTER, 0, 0);
        view.setEnabled(true);

        View stop_Img = contentView.findViewById(R.id.stop_Img);
        View play_Img = contentView.findViewById(R.id.play_Img);
        StaticData.ViewScale(stop_Img, 280, 280);
        StaticData.ViewScale(play_Img, 280, 280);
        stop_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showStop(mContext, view, popupPaush);
            }
        });

        play_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                training_Resume(popupPaush);
                timerStatus = PREPARE;
                initAddStatus((int) uptimer_couting);
                startTimer();
                if (trainStatus == 1) {
                    inintTrainCount(trainCount);
                    TrainTimer(trainStatus);
                }
            }
        });
    }

    private void showStop(final Context mContext, View view, final BasePopupWindow popup) {
        View contentView = View.inflate(mContext, R.layout.training_stop, null);
        final BasePopupWindow popupWindow = new BasePopupWindow(mContext);
//        popupWindow.setmShowAlpha(0.5f);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x1a000000));
        popupWindow.setWidth(RadioGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(RadioGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setTouchable(true);
        popupWindow.setContentView(contentView);
//        popupWindow.showAsDropDown(view, 0, 0);
        popupWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);
        view.setEnabled(true);

        View my_Rel = contentView.findViewById(R.id.my_Rel);
        View hint_Txt = contentView.findViewById(R.id.hint_Txt);
        View cha_Lin = contentView.findViewById(R.id.cha_Lin);
        TextView cancel_Txt = (TextView) contentView.findViewById(R.id.cancel_Txt);
        View sure_Txt = contentView.findViewById(R.id.sure_Txt);
        StaticData.ViewScale(my_Rel, 610, 490);
        StaticData.ViewScale(hint_Txt, 0, 120);
        StaticData.ViewScale(cha_Lin, 0, 120);
        cancel_Txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TrainFinishData(mContext, saveFile.BaseUrl + saveFile.Train_End_Url, popupWindow);
            }
        });
        sure_Txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popupWindow.dismiss();
//                popup.setmShowAlpha(0.5f);
            }
        });

    }

    ProgressBar playone_bar;
    TextView start_Txt, trainCount_Txt;
    LinearLayout mobile_Lin, noConnect_Lin;
    BasePopupWindow popupDown;

    private void showDown(final Context mContext, View view, Training_Detail_Model.DataBean.ProTrainBean oneData) {
        View contentView = View.inflate(mContext, R.layout.training_down, null);
        popupDown = new BasePopupWindow(mContext);
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        lp.alpha = 1.0f;
        window.setAttributes(lp);
//        popupWindow.setmShowAlpha(0.5f);
        popupDown.setBackgroundDrawable(new ColorDrawable(0x1a000000));
        popupDown.setWidth(RadioGroup.LayoutParams.MATCH_PARENT);
        popupDown.setHeight(RadioGroup.LayoutParams.MATCH_PARENT);
        popupDown.setFocusable(false);//不获取焦点 父view Activity中 可监听返回键
        popupDown.setTouchable(true);
        popupDown.setContentView(contentView);
//        popupWindow.showAsDropDown(view, 0, 0);
        popupDown.showAtLocation(contentView, Gravity.CENTER, 0, 0);
        view.setEnabled(true);

        View my_View = contentView.findViewById(R.id.my_View);
        View my_Rel = contentView.findViewById(R.id.my_Rel);
        View back_Btn = contentView.findViewById(R.id.back_Btn);
        playone_bar = (ProgressBar) contentView.findViewById(R.id.playpro_bar);
        TextView count_Txt = (TextView) contentView.findViewById(R.id.count_Txt);
        TextView group_Txt = (TextView) contentView.findViewById(R.id.group_Txt);
        TextView times_Txt = (TextView) contentView.findViewById(R.id.times_Txt);
        TextView hint_Txt = (TextView) contentView.findViewById(R.id.hint_Txt);
        SimpleDraweeView project_simple = (SimpleDraweeView) contentView.findViewById(R.id.project_simple);
        View tilte_Lin = contentView.findViewById(R.id.tilte_Lin);
        View cha_Lin = contentView.findViewById(R.id.cha_Lin);
        trainCount_Txt = (TextView) contentView.findViewById(R.id.trainCount_Txt);
        start_Txt = (TextView) contentView.findViewById(R.id.start_Txt);
        mobile_Lin = (LinearLayout) contentView.findViewById(R.id.mobile_Lin);
        View mobileconnect_img = contentView.findViewById(R.id.mobileconnect_img);
        View finsh_Btn = contentView.findViewById(R.id.finsh_Btn);
        View carry_Btn = contentView.findViewById(R.id.carry_Btn);
        noConnect_Lin = (LinearLayout) contentView.findViewById(R.id.noConnect_Lin);
        View noconnect_img = contentView.findViewById(R.id.noconnect_img);

        StaticData.ViewScale(my_Rel, 610, 580);
        StaticData.ViewScale(back_Btn, 80, 88);
        StaticData.ViewScale(tilte_Lin, 0, 120);
        StaticData.ViewScale(cha_Lin, 0, 120);
        StaticData.ViewScale(playone_bar, 516, 8);
        StaticData.ViewScale(mobile_Lin, 0, 360);
        StaticData.ViewScale(mobileconnect_img, 56, 56);
        StaticData.ViewScale(finsh_Btn, 200, 80);
        StaticData.ViewScale(carry_Btn, 200, 80);
        StaticData.ViewScale(noConnect_Lin, 370, 180);
        StaticData.ViewScale(noconnect_img, 56, 56);
        StaticData.ViewScale(project_simple, 80, 80);
        count_Txt.setText(oneData.getTargetNum() + oneData.getProjectUnit());
        group_Txt.setText(oneData.getGroupCount() + "组");
        times_Txt.setText(oneData.getNeedsTime() + "分钟");
        hint_Txt.setText(oneData.getProjectName());
        Uri imgUri = Uri.parse(oneData.getPro_FilePath());
        project_simple.setImageURI(imgUri);

        cha_Lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String url = saveFile.BaseUrl+saveFile.TrainDown_Get_Url+"?ProjectID="+TrainID+"&Is_HaveCommon="+"true";
                /*申请读取存储的权限*/
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    PermissionUtils.requestPermission((Activity) mContext, PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE, permissionGrant);
                } else {
                    CommonData(startTrainingActivity.this, saveFile.BaseUrl + saveFile.Train_CommonFileList_Url);
                }

                 /*申请读取存储的权限*/
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    PermissionUtils.requestPermission((Activity) mContext, PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE, permissionGrant);
//                } else {
//                    startTrain();
//                }

            }
        });
        start_Txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                popupWindow.dismiss();
//                popup.setmShowAlpha(0.5f);
            }
        });

        back_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        finsh_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        carry_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTrain();
            }
        });


    }

    private String BGMname;
    View choice_Rel_On, choice_Rel_Off;
    ImageView train_volume_Img;
    SeekBar train_seekBar;

    private void showChangeVolume(final Context mContext, View view) {
        View contentView = View.inflate(mContext, R.layout.training_volume, null);
        final BasePopupWindow popupTrain = new BasePopupWindow(mContext);
//        popupPaush.setmShowAlpha(0.5f);
        popupTrain.setBackgroundDrawable(new ColorDrawable(0x1a000000));
        popupTrain.setWidth(RadioGroup.LayoutParams.MATCH_PARENT);
        popupTrain.setHeight(RadioGroup.LayoutParams.WRAP_CONTENT);
        popupTrain.setTouchable(true);
        popupTrain.setContentView(contentView);
//        popupWindow.showAsDropDown(view, 0, 0);
        popupTrain.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
        view.setEnabled(true);

        choice_Rel_On = contentView.findViewById(R.id.choice_Rel_On);
        choice_Rel_Off = contentView.findViewById(R.id.choice_Rel_Off);
        View my_Lin = contentView.findViewById(R.id.my_Lin);
        View cha_Btn = contentView.findViewById(R.id.cha_Btn);
        View left_Img = contentView.findViewById(R.id.left_Img);
        View right_Img = contentView.findViewById(R.id.right_Img);
        train_volume_Img = (ImageView) contentView.findViewById(R.id.train_volume_Img);
        final SwitchButton volume_switch = (SwitchButton) contentView.findViewById(R.id.volume_switch);
        final HorizontalselectedView select_View = (HorizontalselectedView) contentView.findViewById(R.id.select_View);
        train_seekBar = (SeekBar) contentView.findViewById(R.id.train_seekBar);

        StaticData.ViewScale(my_Lin, 0, 600);
        StaticData.ViewScale(cha_Btn, 40, 40);
        StaticData.ViewScale(left_Img, 40, 40);
        StaticData.ViewScale(right_Img, 40, 40);
        StaticData.ViewScale(train_volume_Img, 40, 40);


        train_seekBar.setMax(100);
        if (saveFile.getShareData("trainBGM_push", mContext).equals("false")) {
            volume_switch.setChecked(false);
            bgmOff(mContext);
        } else {
            volume_switch.setChecked(true);
            bgmOn(mContext, false);

            if (saveFile.getShareData("BGM_volume", mContext).equals("false")) {
                train_seekBar.setProgress(10);
//            BGMPlayer.MediaVolume(10);
                saveFile.saveShareData("BGM_volume", 10 + "", mContext);
            } else {

//            if (volume_switch.isChecked())
                int seekProgress = Integer.parseInt(saveFile.getShareData("BGM_volume", mContext));
                train_seekBar.setProgress(seekProgress);
                BGMPlayer.MediaVolume(seekProgress);
            }
        }


//        try {
//            String[] BGMnameArr = mContext.getAssets().list("bgm");//获取assets目录下的所有文件及目录名
//            BGMList = Arrays.asList(BGMnameArr);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        List<String> BGMList = new ArrayList<>();
        BGMList.add("梦的海洋");
        BGMList.add("Intro");
        BGMList.add("Pacific Rim");
        BGMList.add("Battle");
        select_View.setData(BGMList);
        select_View.setSeeSize(5);
        String selectStr = "梦的海洋";
        if (saveFile.getShareData("BGMname", mContext).equals("false")) {
            selectStr = "梦的海洋";
        } else {
            selectStr = saveFile.getShareData("BGMname", mContext);
        }
        select_View.setSelectOne(selectStr);


        cha_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupTrain.dismiss();
            }
        });

        volume_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (saveFile.getShareData("trainBGM_push", mContext).equals("false")) {
                    volume_switch.setChecked(true);
                    saveFile.saveShareData("trainBGM_push", "true", mContext);
//                    JPushInterface.resumePush(getApplicationContext());
                    bgmOn(mContext, true);
                } else {
                    volume_switch.setChecked(false);
                    saveFile.saveShareData("trainBGM_push", "false", mContext);
//                    JPushInterface.stopPush(getApplicationContext());
                    bgmOff(mContext);
                }
            }
        });

        left_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_View.setAnRightOffset();
                saveFile.saveShareData("BGMname", select_View.getSelectedString(), mContext);
                String path = getBgmName(select_View.getSelectedString() + ".mp3");
                BGMPlayer.Play(mContext, path);
//                BGMPlayer.Play(mContext,);
            }
        });
        right_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_View.setAnLeftOffset();
                saveFile.saveShareData("BGMname", select_View.getSelectedString(), mContext);
                String path = getBgmName(select_View.getSelectedString() + ".mp3");
                BGMPlayer.Play(mContext, path);
            }
        });

        train_seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (BGMPlayer != null) {
                    BGMPlayer.MediaVolume(i);
                    saveFile.saveShareData("BGM_volume", i + "", mContext);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }


    private void bgmOn(Context context, boolean isPlay) {
        choice_Rel_On.setVisibility(View.VISIBLE);
        choice_Rel_Off.setVisibility(View.GONE);
        train_volume_Img.setAlpha(1f);
        train_seekBar.setEnabled(true);
        train_seekBar.setAlpha(1f);
        train_seekBar.setThumbOffset(0);
        if (isPlay) {
//        if (BGMPlayer.TrainBGMMediaPlayer != null && !BGMPlayer.TrainBGMMediaPlayer.isPlaying()) {
            String path = getBgmName(saveFile.getShareData("BGMname", context) + ".mp3");
            BGMPlayer.Play(context, path);
//        }
            if (!saveFile.getShareData("BGM_volume", context).equals("false") && saveFile.getShareData("BGM_volume", context) != null) {
                int seekProgress = Integer.parseInt(saveFile.getShareData("BGM_volume", context));
                train_seekBar.setProgress(seekProgress);
                BGMPlayer.MediaVolume(seekProgress);
            }

        }
    }

    private void bgmOff(Context context) {
        choice_Rel_On.setVisibility(View.GONE);
        choice_Rel_Off.setVisibility(View.VISIBLE);
        train_volume_Img.setAlpha(0.2f);
        train_seekBar.setEnabled(false);
        train_seekBar.setAlpha(0.2f);
//        train_seekBar.setProgress(0);
        train_seekBar.setThumbOffset(1000);
        BGMPlayer.stop();
    }


    //下载 还是恢复缓存
    private void startTrain() {
        if (fileData == null) {
            String url = saveFile.BaseUrl + saveFile.TrainDown_Get_Url + "?TrainID=" + TrainID + "&Is_HaveCommon=" + Is_HaveCommon;
            downData(startTrainingActivity.this, url);
        } else {
            if (fileData.getDownType() == 5 || fileData.getDownType() == 0 || fileData.getDownType() == 4) {
                mBinder.DownLoadStart(fileData);
            } else if (fileData.getDownType() == 2) {
                mBinder.DownLoadStop(fileData);
            }
        }
    }

    private PermissionUtils.PermissionGrant permissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:
//                Toast.makeText(ImagePickerActivity.this, "读取存储权限已打开", Toast.LENGTH_SHORT).show();
                    CommonData(startTrainingActivity.this, saveFile.BaseUrl + saveFile.Train_CommonFileList_Url);
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


    Training_Detail_Model modelDetail;
    Training_Detail_Model.DataBean.ProTrainBean oneData;

    public void todayDetailData(final Context context, final String baseUrl, final boolean isDown) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    modelDetail = new Gson().fromJson(resultString, Training_Detail_Model.class);
                    if (modelDetail.isIsSuccess() && !modelDetail.getData().equals("[]")) {
                        oneData = modelDetail.getData().getPro_Train();
                        name_Txt.setText(oneData.getProjectName() + oneData.getCurrGroupNo() + "/" + oneData.getGroupCount());
                        quan_Txt.setText(oneData.getTargetNum() + "");
//                        TargetNum = oneData.getTrainlimit();
                        titleMybar.setMax(oneData.getGroupCount());
                        titleMybar.setProgress(oneData.getCurrGroupNo());
                        String coachStr = getCoach(oneData.getSoundType()) + "正在为你加油鼓劲！";
                        coach_Txt.setText(coachStr);

                        int time = oneData.getDuration() * 1000;
                        initAddStatus(time);//校正计数总时间
//                        String countStr = "0/"+modelDetail.getData().getPro_Train().getGroupNum();
//                        count_Txt.setText(countStr);
                        waveRestart();// 训练计数重新开始
                        List<List<String>> model1 = modelDetail.getData().getAudio_List();


                        if (isDown) {
                            showDown(startTrainingActivity.this, training_push_Img, oneData);
                            initService();
                        }


//                        int size = oneData.getTrainlimit() / oneData.getGroupNum();
//                        for (int i = 0; i < size; i++) {
//                            int index = oneData.getGroupNum() * (i + 1);
//                            dataStr.add(index + "");
//                        }
//                        horView.setshowCenterData(dataStr);
//                        horView.setSeeSize(7);

//                        try {
////                            String[] fileNames = context.getResources().getAssets().list("bgm");
//                            String[] fileName = context.getAssets().list("bgm");//获取assets目录下的所有文件及目录名
//                            BGMFileName = fileName[0];
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }

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

    private String getCoach(int SoundType) {
        String coachName = "海哥";
        if (SoundType == 1) {
            coachName = "海哥";
        } else if (SoundType == 2) {
            coachName = "熊威";
        }
        return coachName;
    }

    //上传更新训练数据
    public void TrainUpdData(final Context context, final String baseUrl, final boolean isFinish) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    BaseDataString_Model model = new Gson().fromJson(resultString, BaseDataString_Model.class);
                    if (model.isIsSuccess() && !model.getData().equals("[]")) {
                        if (isFinish) {
                            TrainFinishData(context, saveFile.BaseUrl + saveFile.Train_Finish_Url);
                        } else {
                            initData(false);
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

    //完成训练
    public void TrainFinishData(final Context context, final String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        JSONObject obj = new JSONObject();
        try {
            obj.put("TrainID", TrainID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params.setBodyContent(obj.toString());
        params.setAsJsonContent(true);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    JiFenAndBadge_Model model = new Gson().fromJson(resultString, JiFenAndBadge_Model.class);
                    if (model.isIsSuccess()) {
                        Toast.makeText(context, "完成训练", Toast.LENGTH_SHORT).show();

                        int GroupNo = modelDetail.getData().getPro_Train().getCurrGroupNo();
//                        int Duration = (int) (uptimer_couting / 1000);
                        String projectName = modelDetail.getData().getPro_Train().getProjectName();
                        String projectCount = modelDetail.getData().getPro_Train().getTargetNum() + modelDetail.getData().getPro_Train().getProjectUnit();
                        Intent intent = new Intent(context, Training_Posting.class);
                        intent.putExtra("projectName", projectName);
                        intent.putExtra("projectCount", projectCount);
                        intent.putExtra("time", StaticData.formatTime(uptimer_couting));
                        intent.putExtra("GroupNo", GroupNo + "");
                        intent.putExtra("jiFenmodel", model);
                        startActivity(intent);

                        Intent intentsucc = new Intent(context, Pk_AddReport_Succ.class);
                        intentsucc.putExtra("jiFenmodel", model);
                        startActivity(intentsucc);

//                        finish();

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

    //结束训练
    public void TrainFinishData(final Context context, final String baseUrl, BasePopupWindow popup) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        JSONObject obj = new JSONObject();
        try {
            obj.put("TrainID", TrainID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params.setBodyContent(obj.toString());
        params.setAsJsonContent(true);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Base_Model model = new Gson().fromJson(resultString, Base_Model.class);
                    if (model.isIsSuccess()) {
//                        Toast.makeText(context, "结束训练", Toast.LENGTH_SHORT).show();
//                        finish();
                        Intent i = new Intent(context, MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
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

    //公用文件列表
    TrainFileList_Model modelComm;

    public void CommonData(final Context context, final String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    modelComm = new Gson().fromJson(resultString, TrainFileList_Model.class);
                    if (modelComm.isIsSuccess() && !modelComm.getData().equals("[]")) {

                        TrainFileData(startTrainingActivity.this, saveFile.BaseUrl + saveFile.Train_FileList_Url + "?ProjectID=" + oneData.getProjectID());
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

    //训练文件列表
    public void TrainFileData(final Context context, final String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    TrainFileList_Model modelTrain = new Gson().fromJson(resultString, TrainFileList_Model.class);
                    if (modelTrain.isIsSuccess() && !modelTrain.getData().equals("[]")) {

//                        GetFilesUtils.getInstance().getSonNode(DownLoadConstant.PATH + oneData.getProjectID());
//                        GetFilesUtils.getInstance().getSonNode(DownLoadConstant.PATH + "common");
                        List<String> commList = GetFilesUtils.getInstance().getList(DownLoadConstant.PATH + "common");
                        List<String> trainList = GetFilesUtils.getInstance().getList(DownLoadConstant.PATH + oneData.getProjectID());
                        boolean isComm = compare(commList, modelComm.getData());
                        boolean isTrain = compare(trainList, modelTrain.getData());

                        if (isComm && isTrain) {
                            //本地文件无异常 开始训练
                            if (popupDown != null) {
                                popupDown.dismiss();
                            }
                            mediaStartTrain(trainPos, 0);
                            BGMPlayerStart(getBgmName(oneData.getBGMFileName()));
                            BGMname = oneData.getBGMFileName().replace(".mp3", "");
                            saveFile.saveShareData("BGMname", BGMname, context);
                        } else {
                            //是否下载公用包
                            if (isComm) {
                                Is_HaveCommon = false;
                            } else {
                                Is_HaveCommon = true;
                            }
                            startTrain();
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

    public synchronized <T extends Comparable<T>> boolean compare(List<T> a, List<T> b) {
        if (a == null || b == null)
            return false;
//        if (a.size() != b.size())
//            return false;
        if (a.size() < b.size())
            return false;
        Collections.sort(a);
        Collections.sort(b);
//        for (int i = 0; i < a.size(); i++) {
//            if (!a.get(i).equals(b.get(i)))
//                return false;
//        }

//        for (int i = 0; i < b.size(); i++) {
//            if (!b.get(i).equals(a.get(i)))
//                return false;
//        }


        boolean isDown = false;
//        for (int i = 0; i < b.size(); i++) {
//
//            for (int k = 0; k < a.size(); k++) {
//                if (b.get(i).equals(a.get(k))) {
//                    isDown = true;
//                    break;
//                } else {
//                    isDown = false;
//                }
//
//            }
//
//            if (!isDown) {
//                return isDown;
//            }
//        }
        isDown = a.containsAll(b);

//        return true;
        return isDown;
    }


    //是否下载公用包
    BaseDataString_Model modelDown;

    public void downData(final Context context, final String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    modelDown = new Gson().fromJson(resultString, BaseDataString_Model.class);
                    if (modelDown.isIsSuccess() && !modelDown.getData().equals("[]")) {

//                        String path = Uri.decode(model.getData());
                        goToDown(modelDown.getData());//传入下载链接

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


    private void goToDown(String url) {
        if (mBinder == null) {
            Toast.makeText(this, "Service 初始化失败", Toast.LENGTH_SHORT).show();
            return;
        }
        mBinder.appendToCurrentList(new FileInfo(Integer.parseInt(TrainID), url, getfileName(url), 0, 0, 0));
//        mBinder.appendToCurrentList(new FileInfo(mBinder.getCurrentListSize(),url,getfileName(url),0,0,0));
//        IntentActivity(DownLoadActivity.class);
//        Intent intent = new Intent(startTrainingActivity.this,)
    }

    // 從URL地址中獲取文件名，即/後面的字符（这个根据情况来命名，可以更改）
    private String getfileName(String url) {//根据下载地址给下载文件命名
        try {
            return url.substring(url.lastIndexOf("/") + 1);
        } catch (Exception ex) {
            return mBinder.getCurrentListSize() + "";//返回一个数字
        }
    }

    private void training_Push() {
        isCycle = false;
        mediaPlayer.Pause();
        waveView.setVisibility(View.INVISIBLE);
        waveView.animatorPause();
    }

    private void training_Resume(BasePopupWindow popup) {
        isCycle = true;
        mediaPlayer.Resume();
        popup.dismiss();

//        startUpTimer();

        waveView.setVisibility(View.VISIBLE);
        waveView.animatorResume();
    }

    //开始播放训练

    int trainPos = 0;// 训练的当前组数
    int p = 0;//每组的训练数量
    boolean isCycle = true; // 是否暂停状态

    private void mediaStartTrain(final int pos, int poszi) {
        if (mediaPlayer != null) {

            initAddStatus((int) uptimer_couting);
            timerStatus = PREPARE;
            startTimer();

            final String TrainAudio = modelDetail.getData().getPro_Train().getTrainAudioName();
            final String fuTrainName = modelDetail.getData().getPro_Train().getTrainProName();
            final String benStr = modelDetail.getData().getAudio_List().get(pos).get(p);

            String playStr = "common";
            if (benStr.equals(TrainAudio) || benStr.equals(fuTrainName)) {
                playStr = oneData.getProjectID();
            } else {
                playStr = "common";
            }
            String trainStr = DownLoadConstant.PATH + playStr + "/" + modelDetail.getData().getAudio_List().get(pos).get(p);

            mediaPlayer.PlayMusic(0, trainStr, 0.9f);
            mediaPlayer.mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
//                    inintTrainCount(0,0);
                    if (benStr.equals(TrainAudio)) {
                        waveView.setProgress(100, mp.getDuration());
                        trainStatus = 1;
                        inintTrainCount(0);
                        TrainTimer(trainStatus);
                    } else {
//                        trainStatus = 0;
                    }
                    mp.start();
                }
            });

            mediaPlayer.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    if (isCycle) {
                        ++p;
                        if (p != modelDetail.getData().getAudio_List().get(pos).size()) {
                            mediaStartTrain(pos, p);
                        } else {
                            p = 0;
                        }
                    }
                }
            });


        }
    }

    //获取播放bgm 路径 名字加上 .mp3
    private String getBgmName(String bgmStr) {
        String bgmstr = "bgm/梦的海洋.mp3";
        if (bgmStr.equals("梦的海洋.mp3")) {
            bgmstr = "bgm/梦的海洋.mp3";
        } else if (bgmStr.equals("Intro.mp3")) {
            bgmstr = "bgm/Intro.mp3";
        } else if (bgmStr.equals("Pacific Rim.mp3")) {
            bgmstr = "bgm/Pacific Rim.mp3";
        } else if (bgmStr.equals("Battle.mp3")) {
            bgmstr = "bgm/Battle.mp3";
        }
        return bgmstr;
    }


    private void BGMPlayerStart(String bgmStr) {
        BGMPlayer.Play(this, bgmStr, 0.1f);
    }


//    /**
//     * 播放音频文件
//     *
//     * @param url 音频文件url地址
//     */
//    public void player(MediaPlayer player, String url, final float Volume) {
//        if (player == null) {
//            player = new MediaPlayer();
//        }
//        // 如果当前正在播放,则直接返回
//        if (player.isPlaying()) {
//            Log.i("MediaManager", "MediaPlayer is playing……");
//            return;
//        }
//        try {
////            String path = fileUtils.exists(url); // 判断是否存在缓存文件
////            if(path != null) { // 存在缓存文件
////                // 直接播放缓存文件
////                player.setDataSource(path); // 设置数据源
////                player.prepare(); // 准备(File), 同步
////                player.start(); // 播放音频文件
////            }else { // 不存在音频缓存文件,则边存边播
//            // 异步下载音频文件
////                new AudioAsyncTask().execute(url);
//
//
//            AssetManager assetManager = this.getAssets();
//            AssetFileDescriptor fileDescriptor = assetManager.openFd(url);
//            player.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
////            player.setDataSource(url); // 设置数据源为网络文件
//            player.prepareAsync(); // 准备(InputStream), 异步
//
//            player.setVolume(Volume, Volume);//调节一个音量 左右声道
//            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//                    // 准备完成后, 开始播放音频文件
//                    if (Volume == 0.5) {
//                        waveView.setProgress(100, mp.getDuration());
//                    }
//                    mp.start();
//                }
//            });
////            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (IllegalStateException e) {
//            e.printStackTrace();
//            xunPlayer = null;
//            xunPlayer = new MediaPlayer();
//        }
//
//        xunPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mediaPlayer) {
////                mediaPlayer.release();
//                mediaPlayer = null;
//            }
//        });
//    }
//
//
//    public void Stop(MediaPlayer player) {
//        //停止播放视频
//        if (player != null && player.isPlaying()) {
//            //获取到当前播放视频的位置
////            currentPosition = mediaPlayer.getCurrentPosition();
//            player.stop();
//
//        }
//    }
//
//    public void paush(MediaPlayer player) {
//        if (player.isPlaying()) {
//            player.pause();
//        } else {
//            return;
//        }
//    }


    public final int PREPARE = 0;
    public final int START = 1;
    public final int PASUSE = 2;
    private long timer_unit = 1000;//计时间隔
    public long distination_total = timer_unit * 60 * 10;
    private long uptimer_couting = 0;
    private int timerStatus = PREPARE;
    private TimerTask timerTask;
    private TimerTask trainTask;
    private Timer timer = null;
    private long trainrev;
    int train_unit = 1;
    int trainCount = 0;//训练计数
    private int trainStatus = 0;

    //计时器开始
    private void startUpTimer() {
        initAddStatus(0);
        timerStatus = PREPARE;
//        countName_Txt.setText("收听时长");
        startTimer();
        timerStatus = START;
    }


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
        timer.scheduleAtFixedRate(timerTask, 0, timer_unit); //第一参数 对象  第二 延迟时间 第三 执行周期
//        timer.schedule(timerTask, 0, timer_unit);
//        timer.scheduleAtFixedRate(timerTask, 0, 4000);
    }

    private void TrainTimer(int istime) {
        if (istime == 1) {
            if (trainTask != null) {
                trainTask.cancel();
                trainTask = null;
            }
//        trainCount = 0;
            trainTask = new InterValTask();
//            trainrev = 3000;
            timer.scheduleAtFixedRate(trainTask, 0, timer_unit);
        }
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
        if (trainTask != null) {
            trainTask.cancel();
            trainTask = null;
        }
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }

    }

    //训练计数
    private void inintTrainCount(int count) {
        trainCount = count;
//        trainStatus = status;
    }

    //计时器初始化
    private void initAddStatus(int time) {
        timerStatus = PREPARE;
        uptimer_couting = time;
//        radio_seekBar.setMax((int) distination_total);
    }

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            uptimer_couting += timer_unit;
            mHandler.sendEmptyMessage(1);
        }
    }

    class InterValTask extends TimerTask {
        @Override
        public void run() {
            int Interval = modelDetail.getData().getPro_Train().getInterval();
            trainCount = mediaPlayer.mediaPlayer.getCurrentPosition() / 1000 / Interval + 1;
            mHandler.sendEmptyMessage(2);
        }

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) { //up是1 down是2
                case 1:
                    time_Txt.setText(formateTimer(uptimer_couting));
                    break;
                case 2:
                    int GroupNum = modelDetail.getData().getPro_Train().getGroupNum();
                    count_Txt.setText(trainCount + "/" + GroupNum);
                    break;
            }
        }
    };


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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            stopSendTimerTask();
//            mediaPlayer.stop();
//            BGMPlayer.stop();
//            finish();
            return true;
        }
        return false;
    }

    /**
     * 判断有无网络 。
     *
     * @return true 有网, false 没有网络.
     */

    public boolean isNetConnect() {
        if (netType == 1) {
            return true;
        } else if (netType == 0) {
            return true;
        } else if (netType == -1) {
            return false;
        }
        return false;
    }


}
