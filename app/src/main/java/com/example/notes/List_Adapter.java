package com.example.notes;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.provider.CalendarContract;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.zip.Inflater;

import static android.view.View.inflate;

public class List_Adapter extends BaseAdapter {
    ArrayList<Row> arrayList;
    Context context;
    Sql_Connection con;
    Cursor cursor;
    private ClipboardManager clipboardManager;
    private ClipData clipData;
    FragmentManager fragmentManager2;
    List_Adapter(Context c, FragmentManager fragmentManager){
        fragmentManager2=fragmentManager;
        clipboardManager=(ClipboardManager)c.getSystemService(Context.CLIPBOARD_SERVICE);
            arrayList = new ArrayList<Row>();
try {
    con = new Sql_Connection(c);
    cursor = con.get_notes();
    if (cursor.moveToFirst()) {
//        Toast.makeText(c, cursor.getString(1), Toast.LENGTH_LONG).show();
        arrayList.add(new Row(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
        while (cursor.moveToNext())
            arrayList.add(new Row(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
    }

}catch (Exception ex){
    Toast.makeText(c,ex.getMessage(),Toast.LENGTH_LONG).show();
}
        context=c;

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view= LayoutInflater.from(context).inflate(R.layout.row,viewGroup,false);
        final Row r=arrayList.get(i);
        TextView id=(TextView)view.findViewById(R.id.note_id);
        final TextView note=(TextView)view.findViewById(R.id.note_content);
        final ImageView edit=(ImageView)view.findViewById(R.id.edit);
        ImageView color=(ImageView)view.findViewById(R.id.colors);
        ImageView delete=(ImageView)view.findViewById(R.id.delete);
        LinearLayout layout=(LinearLayout)view.findViewById(R.id.Row_layout);
        try {
            layout.setBackgroundColor(context.getResources().getColor(r.BackgroundColor));
//            color.setBackgroundColor(context.getResources().getColor(r.BackgroundColor));
        }catch(Exception ex){

        }
        //Toast.makeText(context,""+r.BackgroundColor,Toast.LENGTH_LONG).show();

//        note.setTextColor(r.Color);


        id.setText(""+r.Id);
        note.setText(""+r.Note);
        final float scale=context.getResources().getDisplayMetrics().density;
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(note.getLayoutParams().height != ViewGroup.LayoutParams.WRAP_CONTENT) {
                    note.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;

                }else {
                    int px=(int)((51*scale)+0.5);
                    note.getLayoutParams().height=px;
                }
                note.requestLayout();
            }
        });
        note.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                clipData=ClipData.newPlainText("text",note.getText().toString());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(context,"Note Copied.",Toast.LENGTH_LONG).show();
                return true;
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context.getApplicationContext(),Edit_Note.class);
                i.putExtra("type","edit");
                i.putExtra("Id",r.Id);
                i.putExtra("Note",r.Note);
                i.putExtra("Color",r.BackgroundColor);

                context.startActivity(i);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // con.delete(r.Id);
                Conferm_Delete c=new Conferm_Delete();
                c.show(fragmentManager2,""+r.Id);
            }
        });
        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Colors_Picker color=new Colors_Picker();
                color.show(fragmentManager2,""+r.Id);
                Toast.makeText(context,"Color",Toast.LENGTH_LONG).show();
            }
        });


        return view;
    }

}
