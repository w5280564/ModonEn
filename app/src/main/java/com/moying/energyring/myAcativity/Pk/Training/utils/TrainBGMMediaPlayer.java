package com.moying.energyring.myAcativity.Pk.Training.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.util.Log;

/**
 * Created by waylen on 2018/4/25.
 */

public class TrainBGMMediaPlayer {

    private static TrainBGMMediaPlayer instance;

    private TrainBGMMediaPlayer() {

    }

    public static TrainBGMMediaPlayer getInstance() {
        if (instance == null) {
            instance = new TrainBGMMediaPlayer();
        }
        return instance;
    }

    public MediaPlayer TrainBGMMediaPlayer = null;

    public MediaPlayer createPlayerIfNeed() {
        if (TrainBGMMediaPlayer == null) {
            TrainBGMMediaPlayer = new MediaPlayer();
        }
        return TrainBGMMediaPlayer;
    }

    /**
     * 播放音乐
     */
    public void Play(Context context, String path, float Volume) {
        createPlayerIfNeed();
        try {

            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor fileDescriptor = assetManager.openFd(path);
            TrainBGMMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
            TrainBGMMediaPlayer.prepareAsync();//异步播放
            TrainBGMMediaPlayer.setVolume(Volume, Volume);//调节一个音量 左右声道
            TrainBGMMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {//异步必须OnPreparedListener中 start
                    mp.start();
                }
            });

            TrainBGMMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });

        } catch (Exception e) {
            Log.e("player", "player prepare() err");
        }

    }

    public void Play(Context context, String path) {
        createPlayerIfNeed();
        try {
            TrainBGMMediaPlayer.reset();// 把各项参数恢复到初始状态
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor fileDescriptor = assetManager.openFd(path);
            TrainBGMMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
            TrainBGMMediaPlayer.prepareAsync();//异步播放
            TrainBGMMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {//异步必须OnPreparedListener中 start
                    mp.start();
                }
            });

            TrainBGMMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });

        } catch (Exception e) {
            Log.e("player", "player prepare() err");
        }

    }


    public void MediaVolume(int count) {
        if (count != 0) {
            float vocount = count / 100f;
            TrainBGMMediaPlayer.setVolume(vocount, vocount);
        }
    }

    public void Pause() {
        if (TrainBGMMediaPlayer != null && TrainBGMMediaPlayer.isPlaying()) {
            TrainBGMMediaPlayer.pause();
        }
    }

    public void Resume() {
        if (TrainBGMMediaPlayer != null) {
            TrainBGMMediaPlayer.start();
        }
    }

    public void stop() {
        if (null != TrainBGMMediaPlayer) {
            TrainBGMMediaPlayer.stop();
            TrainBGMMediaPlayer.release();
            TrainBGMMediaPlayer = null;
        }

    }


}
