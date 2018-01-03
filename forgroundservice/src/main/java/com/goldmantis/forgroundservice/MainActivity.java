package com.goldmantis.forgroundservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.goldmantis.forgroundservice.service.MusicPlayerService;

public class MainActivity extends AppCompatActivity
{
    private Button btn_notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, MusicPlayerService.class);
        startService(intent);
        btn_notification = findViewById(R.id.btn_notification);
        final NotificationManager manger = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        btn_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this);
                builder.setContentTitle("横幅通知");
                builder.setContentText("请在设置通知管理中开启消息横幅提醒权限");
                builder.setDefaults(NotificationCompat.DEFAULT_ALL);
                builder.setSmallIcon(R.mipmap.icon_asset_normal);
                builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.bir_woman_thr));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.baidu.com"));
                PendingIntent pIntent = PendingIntent.getActivity(MainActivity.this, 1, intent, 0);
                builder.setContentIntent(pIntent);
                builder.setFullScreenIntent(pIntent, true);
                builder.setAutoCancel(true);
                Notification notification = builder.build();
                manger.notify(6, notification);
            }
        });
    }
}
