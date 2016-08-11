package ru.yandex.yamblz.ui.fragments;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Switch;

import ru.yandex.yamblz.R;

/**
 * Created by dan on 19.07.16.
 */
public class HeadSetReciever extends BroadcastReceiver {

    private NotificationManager notificationManager;
    public static final String MUSICBUTTON = "MUSICBUTTON";
    public static final String RADIOBUTTON = "RADIOBUTTON";
    private static final String MUSICWEBSITE = "ru.yandex.music";
    private static final String RADIOWEBSITE = "ru.yandex.radio";



    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        switch (action) {
            case Intent.ACTION_HEADSET_PLUG:
                int state = intent.getIntExtra("state", -1);
                switch (state) {
                    case 0:
                        Log.d("!!!!!", "Headset unplugged");
                        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.cancel(228);
                        break;
                    case 1:
                        createNotification(context);
                        Log.d("!!!!", "Headset plugged");
                        break;
                    default:
                }
                break;
            case MUSICBUTTON:
                openApp(context, MUSICWEBSITE);
                break;
            case RADIOBUTTON:
                openApp(context, RADIOWEBSITE);
                break;
        }


    }

    private void openApp(Context context, String websiteName) {
        PackageManager manager = context.getPackageManager();
        Intent intent = manager.getLaunchIntentForPackage(websiteName);
        if (intent != null) {
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            context.startActivity(intent);
        } else {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + websiteName)));
        }
    }

    private void createNotification(Context context) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_layout);
        Intent mIntent = new Intent().setAction(MUSICBUTTON);
        Intent rIntent = new Intent().setAction(RADIOBUTTON);
        PendingIntent mpIntent = PendingIntent.getBroadcast(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent rpIntent = PendingIntent.getBroadcast(context, 1, rIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.musicButton, mpIntent);
        remoteViews.setOnClickPendingIntent(R.id.radioButton, rpIntent);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context).setSmallIcon(R.drawable.leak_canary_icon).setContentTitle("My notification").setContentText("Hello World!").setContent(remoteViews);
        Notification notification = mBuilder.build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(228, notification);
    }
}
