package com.moying.energyring.myAcativity.Pk.Training.utils;

import android.media.MediaPlayer;
import android.util.Log;

/**
 * Created by waylen on 2018/4/25.
 */

public class TrainMediaPlayer {

    private static TrainMediaPlayer instance;

    private TrainMediaPlayer() {

    }

    public static TrainMediaPlayer getInstance() {
        if (instance == null) {
            instance = new TrainMediaPlayer();
        }
        return instance;
    }

    public MediaPlayer mediaPlayer = null;

    public MediaPlayer createPlayerIfNeed() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        return mediaPlayer;
    }

    /**
     * 播放音乐
     */
    public void PlayMusic(int current, String path, float Volume) {
        createPlayerIfNeed();
        try {
            mediaPlayer.reset();// 把各项参数恢复到初始状态
            mediaPlayer.setDataSource(path);//地址
            mediaPlayer.prepareAsync();//异步播放
            mediaPlayer.setVolume(Volume, Volume);//调节一个音量 左右声道
//            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {//异步必须OnPreparedListener中 start
//                    mp.start();
//                }
//            });
        } catch (Exception e) {
            Log.e("player", "player prepare() err");
        }

//        try {
//            mediaPlayer.reset();// 把各项参数恢复到初始状态
//            mediaPlayer.setDataSource(path);
//            mediaPlayer.prepare(); // 进行缓冲
//            mediaPlayer.setOnPreparedListener(new PreparedListener(current));// 注册一个监听器
//            //mHandler.sendEmptyMessage(1);  //更新时间UI
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }



    public void  Pause(){
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void Resume(){
        if (mediaPlayer != null){
            mediaPlayer.start();
        }
    }


    /**
     * 暂停与开始音乐
     */
    public void PauseNResumeMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//            isPlaying = false;
            mediaPlayer.pause();
        } else {
//            isPlaying = true;
            mediaPlayer.start();
        }
    }


    public void stop() {
        if (null != mediaPlayer) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

    }


    private final class PreparedListener implements MediaPlayer.OnPreparedListener {
        private int currentTime;

        public PreparedListener(int currentTime) {
            this.currentTime = currentTime;
        }

        @Override
        public void onPrepared(MediaPlayer mp) {
            mediaPlayer.start(); // 开始播放
            if (currentTime > 0) { // 如果音乐不是从头播放
                mediaPlayer.seekTo(currentTime);
            }
            //更新时间
//            mHandler.sendEmptyMessage(1);
//            //更新 歌曲时间
//            Intent intent = new Intent();
//            intent.setAction(ACTION_MUSIC_DURATION);
//            duration = mediaPlayer.getDuration();
//            intent.putExtra("duration", duration);  //通过Intent来传递歌曲的总长度
//            sendBroadcast(intent);
        }
    }


}
