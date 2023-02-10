package com.example.notes;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class network_receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder nbuilder=new NotificationCompat.Builder(context);
        nbuilder.setSmallIcon(R.drawable.ic_launcher_background);
        nbuilder.setContentTitle("Network");
        nbuilder.setContentText("Network on or off");
        nbuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
        NotificationManager notification= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notification.notify(1,nbuilder.build());

        //Toast.makeText(context,"network on",Toast.LENGTH_LONG).show();
    }
}
