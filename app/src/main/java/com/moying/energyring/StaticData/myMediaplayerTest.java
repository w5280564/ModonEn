package com.moying.energyring.StaticData;

import android.media.MediaPlayer;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by waylen on 2017/6/6.
 */

public class myMediaplayerTest implements MediaPlayer.OnPreparedListener, MediaPlayer.OnBufferingUpdateListener,
        MediaPlayer.OnCompletionListener {
    public static boolean ifplay = false;
    public static boolean iffirst = false;
    private static myMediaplayerTest instance;

    private myMediaplayerTest() {

    }

    public static myMediaplayerTest getInstance() {
        if (instance == null) {
            instance = new myMediaplayerTest();
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


    public void playSlideUrl(String videoUrl, final ImageView playBtn) {
//        if (!iffirst) {
        if (ifplay) {
//            mediaPlayer.seekTo(0);
            playBtn.setEnabled(false);
            createPlayerIfNeed();
//                  mediaPlayer = new MediaPlayer();//如果被停止了，则为null
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
//            iffirst = true;
        }
//        }
//        mediaPlayer.start();
//        context.ifplay = true;
    }

    public void playUrl(String videoUrl, final ImageView playBtn) {
        if (!iffirst) {
            playBtn.setEnabled(false);
            createPlayerIfNeed();
//                  mediaPlayer = new MediaPlayer();//如果被停止了，则为null
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
            iffirst = true;
        }
        mediaPlayer.start();
        ifplay = true;
    }

    public void pause() {
        mediaPlayer.pause();
        ifplay = false;
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
//            mediaPlayer.pause();
//            mediaPlayer.seekTo(0);
            mediaPlayer = null;
            iffirst = false;
            ifplay = false;
        }
    }

    public void isStart() {
        boolean isstart = false;
        if (mediaPlayer.isPlaying()) {
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
