package com.example.notes;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class boot_load extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Sql_Connection con=new Sql_Connection(context);
        Cursor cursor=con.get_notify_state(1,"notify");
        if(cursor.moveToFirst()) {
            if(cursor.getInt(0)==1) {
                context.startService(new Intent(context, time_services.class));
            }
        }
        con.close();
    }
}
