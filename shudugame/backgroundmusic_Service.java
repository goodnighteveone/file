package com.example.shudugame;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class backgroundmusic_Service extends Service {
    private MediaPlayer mediaPlayer;

    public backgroundmusic_Service() {

    }

    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.time_to_love1);
        mediaPlayer.setLooping(true);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        // 这里可以执行长时间运行的任务
        mediaPlayer.start();
        return START_STICKY; // 如果服务被意外杀死，会自动重启服务
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}