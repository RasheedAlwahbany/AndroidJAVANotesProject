package com.example.notes;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onRestart() {
        super.onRestart();
        fillData();
    }
    static IntentFilter S_intentFilter;
    private static  BroadcastReceiver broadcastReceiver;
    List_Adapter list_adapter;
    ListView listView;
    Sql_Connection con;

    public void fillData(){
        try {
            list_adapter = new List_Adapter(MainActivity.this , getSupportFragmentManager());
            listView.setAdapter(list_adapter);

        }catch(Exception ex){
            Toast.makeText(MainActivity.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        con=new Sql_Connection(MainActivity.this);
//        startService( new Intent(MainActivity.this,time_services.class));



//        broadcastReceiver=new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {


//                int num=Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
//                if(num==3) {
//                    Sql_Connection con=new Sql_Connection(context);
//                    ArrayList<Integer> array=new ArrayList<Integer>();
//                    array=con.getNotesId();
//                    Random random=new Random();
//                    int rand_id=random.nextInt(array.size()-0)+0;
//                    rand_id=array.get(rand_id);
//                    NotificationCompat.Builder nbuilder = new NotificationCompat.Builder(context);
//                    nbuilder.setSmallIcon(R.drawable.ic_launcher_background);
//                    nbuilder.setContentTitle("Note "+rand_id);
//                    nbuilder.setContentText(""+con.getNotes(rand_id));
//                    nbuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
//                    NotificationManager notification = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//                    notification.notify(3, nbuilder.build());
//                }
//            }
//        };

//            registerReceiver(broadcastReceiver,new IntentFilter(Intent.ACTION_TIME_TICK));


        listView =findViewById(R.id.notes_list);

        fillData();

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                try {
//
//                            Toast.makeText(MainActivity.this, "hello", Toast.LENGTH_LONG).show();
//                    //view.setSelected(true);
//
//                } catch (Exception ex) {
//                    Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
//
//                }
//
//            }
//        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar_menu,menu);
        Cursor cursor=con.get_notify_state(1,"notify");
        if(cursor.moveToFirst()) {
            if(cursor.getInt(0)==1) {
                MenuItem item=menu.findItem(R.id.notifyme);
                item.setChecked(true);
            }
        }
        con.close();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.Add){
            Intent i=new Intent(MainActivity.this,Edit_Note.class);
            i.putExtra("type","add");
            startActivityForResult(i,1);
        }else if(item.getItemId()==R.id.notifyme){
            Intent i=new Intent(MainActivity.this,time_services.class);
            if(!item.isChecked()){
                con.Notification(1,"notify",1);
                startService(i);
                item.setChecked(true);
            }else{
                con.Notification(1,"notify",0);
                stopService(i);
                item.setChecked(false);
            }
        }else if(item.getItemId()==R.id.aboutus){
            Random random = new Random();
            Toast.makeText(MainActivity.this,"@RAGT2020\nEng.Rasheed Adnan Al-Wahpany.\nCS10 (17_0144)\nPhone: 774258832\nTo Copy any note long press on it",Toast.LENGTH_LONG).show();
        }
        con.close();

        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            fillData();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(broadcastReceiver);
    }
}
