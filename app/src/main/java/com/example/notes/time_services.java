package com.example.notes;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.security.Provider;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.Timer;

public class time_services extends Service {
    static int i=0;
    static int stop=0;
    threads thread;
    boolean runs=true;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return Service.START_STICKY;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        thread=new threads();
        thread.start();

    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        runs=false;
    }

    class threads extends Thread{
        @Override
        public void run() {
            super.run();
            while(runs){
                if(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)%2==0&&stop==0){ //||Calendar.getInstance().get(Calendar.MINUTE)==10) {
//                    NotificationCompat.Builder nbuilder2 = new NotificationCompat.Builder(time_services.this);
//                    nbuilder2.setSmallIcon(R.drawable.ic_launcher_background);
//                    nbuilder2.setContentTitle("Note ");
//                    nbuilder2.setContentText("hello");
//                    nbuilder2.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
//                    NotificationManager notification2 = (NotificationManager) time_services.this.getSystemService(Service.NOTIFICATION_SERVICE);
//                    notification2.notify(i, nbuilder2.build());

                    Sql_Connection con=new Sql_Connection(time_services.this);
                    ArrayList<Integer> array=new ArrayList<Integer>();
                    int num=1;
                    if(con.isNotesId()==true) {
                            array=con.getNotesId();
                            if(array.size()>0) {
                                Random random = new Random();
                                int rand_id = (random.nextInt(array.size())+1) - 1;
                                rand_id = array.get(rand_id);
//                                Intent intent=new Intent(time_services.this,MainActivity.class);

                                Intent intent=new Intent(time_services.this,Edit_Note.class);
                                intent.putExtra("type","edit");
                                intent.putExtra("Id",rand_id);
                                intent.putExtra("Note",con.getNotes(rand_id));
                                intent.putExtra("Color",con.getNotesColor(rand_id));
                                intent.putExtra("notification_number",num);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                PendingIntent pi=PendingIntent.getActivity(time_services.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                                NotificationCompat.BigTextStyle bigTextStyle=new NotificationCompat.BigTextStyle();
                                bigTextStyle.bigText(""+con.getNotes(rand_id));
                                bigTextStyle.setBigContentTitle("Note " + rand_id);
                                bigTextStyle.setSummaryText("@RAGT2020");

                                NotificationCompat.Builder nbuilder = new NotificationCompat.Builder(time_services.this);
                                nbuilder.setSmallIcon(R.drawable.ic_launcher_background);
                                nbuilder.setContentTitle("Note " + rand_id);
                                nbuilder.setContentText("" + con.getNotes(rand_id));

                                nbuilder.setContentIntent(pi);
                                nbuilder.setStyle(bigTextStyle);
                                nbuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
                                NotificationManager notification = (NotificationManager) time_services.this.getSystemService(Context.NOTIFICATION_SERVICE);
                                notification.notify(num, nbuilder.build());

                                num=num+1;
                                i += 1;
                                stop += 1;
                            }
                    }
                }else if(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)%2!=0){
                    stop=0;
                }

            }

        }
    }
}

