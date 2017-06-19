package com.moying.energyring.StaticData;

import android.media.MediaPlayer;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by waylen on 2017/6/6.
 */

public class myMediaplayer implements MediaPlayer.OnPreparedListener, MediaPlayer.OnBufferingUpdateListener,
        MediaPlayer.OnCompletionListener {
    private static myMediaplayer instance;

    private myMediaplayer() {

    }

    public static myMediaplayer getInstance() {
        if (instance == null) {
            instance = new myMediaplayer();
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


//    public void play() {
//        mediaPlayer.start();
//    }

    public void playUrl(String videoUrl, final ImageView playBtn) {
//        if(mediaPlayer != null && mediaPlayer.isPlaying()){
//            pause();//暂停
//        }else{

//        try {
//            mediaPlayer.reset();// 把各项参数恢复到初始状态
//            mediaPlayer.setDataSource(videoUrl);//地址
////            media.prepare();//同步
////            media.start();
//            mediaPlayer.prepareAsync();//异步播放
//            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {//异步必须OnPreparedListener中 start
//                    mp.start();
//                }
//            });
//        } catch (IOException e) {
//            mediaPlayer.release();
//            e.printStackTrace();
//        }

//        }

        if (mediaPlayer == null) {
            playBtn.setEnabled(false);
            createPlayerIfNeed();
//            mediaPlayer = new MediaPlayer();//如果被停止了，则为null
                mediaPlayer.reset();// 把各项参数恢复到初始状态
            try {
                mediaPlayer.setDataSource(videoUrl);//地址
                mediaPlayer.prepareAsync();//异步播放
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {//异步必须OnPreparedListener中 start
                        mp.start();
                        playBtn.setEnabled(true);
                    }
                });
            } catch (Exception e) {
                Log.e("player", "player prepare() err");
            }
        }
        mediaPlayer.start();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
//            mediaPlayer.pause();
//            mediaPlayer.seekTo(0);
            mediaPlayer = null;
        }
    }
    public void isStart(){
        boolean isstart = false;
        if (mediaPlayer.isPlaying()){
            isstart = true;
        }
        return;
    }

    @Override
    /**
     * 通过onPrepared播放
     */
    public void onPrepared(MediaPlayer arg0) {
        arg0.start();
        Log.e("mediaPlayer", "onPrepared");
    }

    @Override
    public void onCompletion(MediaPlayer arg0) {
        Log.e("mediaPlayer", "onCompletion");
    }

    @Override
    public void onBufferingUpdate(MediaPlayer arg0, int bufferingProgress) {
//        skbProgress.setSecondaryProgress(bufferingProgress);
//        int currentProgress=skbProgress.getMax()*mediaPlayer.getCurrentPosition()/mediaPlayer.getDuration();
//        Log.e(currentProgress+"% play", bufferingProgress + "% buffer");
    }


}
