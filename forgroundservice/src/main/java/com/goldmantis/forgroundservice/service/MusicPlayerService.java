package com.goldmantis.forgroundservice.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.goldmantis.forgroundservice.MainActivity;
import com.goldmantis.forgroundservice.R;

public class MusicPlayerService extends Service
{
    private static final String TAG = MusicPlayerService.class.getSimpleName();
    public MusicPlayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) 
    {
        Log.d(TAG, "onStartCommand");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),"test");
        Intent intent1 = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent1, 0);
        builder.setContentIntent(pendingIntent)
                .setTicker("你有新的消息啊")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.bir_woman_thr))
                .setContentTitle("我ishi内容的标题啊啊")
                .setSmallIcon(R.mipmap.icon_asset_normal)
                .setContentText("我是目标的内容啊")
                .setFullScreenIntent(pendingIntent, true)
                .setWhen(System.currentTimeMillis());
        Notification compat = builder.build();
        compat.defaults = Notification.DEFAULT_SOUND;
        startForeground(110, compat);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate()
    {
        Log.d(TAG, "onCreate()");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
