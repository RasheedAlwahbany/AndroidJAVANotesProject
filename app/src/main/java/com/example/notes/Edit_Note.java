package com.example.notes;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Edit_Note extends AppCompatActivity {

    Sql_Connection con;
    EditText editText;
    String type;
    int add_num=0;
    int add_id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        if(getIntent().getIntExtra("notification_number", 0)!=0) {
            NotificationManager notification = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notification.cancel(getIntent().getIntExtra("notification_number", 0));
        }
        type=getIntent().getStringExtra("type");
        //TextView textView=(TextView)findViewById(R.id.note_id);
        editText = (EditText) findViewById(R.id.Note_etxt);
// For Edits
            editText.setText(getIntent().getStringExtra("Note"));
//        int d=Color.green(Integer.parseInt( getIntent().getStringExtra("Color")));
//        editText.setBackgroundColor(d);
            //textView.setText( getIntent().getStringExtra("Id"));

            con = new Sql_Connection(Edit_Note.this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notes_bar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.Save_Notes) {
            if(type.equals("edit")) {
//                       Toast.makeText(Edit_Note.this,getIntent().getIntExtra("Id",0)+ editText.getText().toString()+ getIntent().getIntExtra("Color",0),Toast.LENGTH_LONG).show();
                con.edit(getIntent().getIntExtra("Id",0), editText.getText().toString(), getIntent().getIntExtra("Color",0));

            }else{
                if(add_num==0) {
                    if(!editText.getText().toString().equals("")&&!editText.getText().toString().equals(null)) {
                        con.add(con.getRowCount() + 1, editText.getText().toString(), R.color.custome_default);
                        add_id = con.getRowCount();
                        add_num = add_num + 1;
                    }
                }else if(add_id!=0) {
                    con.edit(add_id, editText.getText().toString(), R.color.custome_default);
                }
            }
        }else if(item.getItemId()==R.id.Cancel_Noptes){
            finish();
            if(getParent() instanceof MainActivity)
                ((MainActivity)getParent()).fillData();
        }
        return true;
    }
}
