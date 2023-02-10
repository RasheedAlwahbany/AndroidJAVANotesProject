package com.example.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class Sql_Connection extends SQLiteOpenHelper {
    Context parent;
    public Sql_Connection(Context c){
    super(c,"Notes.dp",null,1);
    SQLiteDatabase dbs=this.getReadableDatabase();
    parent=c;

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Notes(id integer primary key,notes text,color integer)");
        db.execSQL("create table Notifications(id integer primary key,name text,state integer)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("Drop table if exists Notes");
        db.execSQL("Drop table if exists Notifications");
        onCreate(db);
    }
    public void add(int i,String note,int color){
        SQLiteDatabase db=this.getWritableDatabase();
        try{
            ContentValues values=new ContentValues();
            values.put("id",i);
            values.put("notes",note);
            values.put("color",color);
            if(db.insert("Notes",null,values)!=-1)
                Toast.makeText(parent,"Add Successfully.",Toast.LENGTH_LONG).show();
            else
                Toast.makeText(parent,"Add Error.",Toast.LENGTH_LONG).show();

        }catch(Exception ex){
            Toast.makeText(parent,ex.getMessage(),Toast.LENGTH_LONG).show();
        }finally {
            db.close();
        }
    }
    public void edit(int i,String note,int color){
        SQLiteDatabase db=this.getWritableDatabase();
        try{
            db.execSQL("update Notes set notes='"+note+"',color="+color+" where id="+i);
                Toast.makeText(parent,"Updated Successfully.",Toast.LENGTH_LONG).show();

        }catch(Exception ex){
            Toast.makeText(parent,ex.getMessage(),Toast.LENGTH_LONG).show();
        }finally {
            db.close();
        }
    }
    public void edit_color(int i,int color){
        SQLiteDatabase db=this.getWritableDatabase();
        try{
            db.execSQL("update Notes set color="+color+" where id="+i);
//            Toast.makeText(parent,"Updated Successfully."+color,Toast.LENGTH_LONG).show();

        }catch(Exception ex){
            Toast.makeText(parent,ex.getMessage(),Toast.LENGTH_LONG).show();
        }finally {
            db.close();
        }
    }
    public Cursor get_notes(){
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.rawQuery("select * from Notes", null);

            return cursor;

    }
    public int getRowCount(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select max(id) from Notes", null);
        int i=0;
          if(cursor.moveToFirst())
            i=cursor.getInt(0);

            db.close();
        return i;

    }


    public void delete(int i){
        SQLiteDatabase db=this.getWritableDatabase();
        try{
            db.execSQL("delete from Notes  where id="+i);
            Toast.makeText(parent,"Deleted Successfully.",Toast.LENGTH_LONG).show();

        }catch(Exception ex){
            Toast.makeText(parent,ex.getMessage(),Toast.LENGTH_LONG).show();
        }finally {
            db.close();
        }
    }

    public boolean isNotesId(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select id from Notes", null);
        ArrayList<Integer> array=new ArrayList<Integer>();
        if(cursor.moveToFirst()){
            db.close();
            return true;
        }
        db.close();
        return false;
    }
    public int getNotesColor(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select color from Notes where id="+id, null);
        int color=0;
        if(cursor.moveToFirst()){
            color=cursor.getInt(0);

        }

        db.close();
        return color;
    }
    public ArrayList<Integer> getNotesId(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select id from Notes", null);
        ArrayList<Integer> array=new ArrayList<Integer>();
        if(cursor.moveToFirst()){
            array.add(cursor.getInt(0));
            while(cursor.moveToNext())
                array.add(cursor.getInt(0));

        }

        return array;
    }
    public String getNotes(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String notes="";
        Cursor cursor = db.rawQuery("select notes from Notes where id="+id, null);
        if(cursor.moveToFirst())
            notes=cursor.getString(0);
        return notes;

    }
    public void Notification(int id,String name,int state){
        SQLiteDatabase db=this.getWritableDatabase();
        try{
            String notes="";
            Cursor cursor = db.rawQuery("select name from Notifications where name='"+name+"' and id="+id, null);
            if(!cursor.moveToFirst()) {
                db.close();
                db = this.getReadableDatabase();
                ContentValues values = new ContentValues();
                values.put("id", id);
                values.put("name", name);
                values.put("state", state);
                if (db.insert("Notifications", null, values) != -1)
                    Toast.makeText(parent, "Add Successfully.", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(parent, "Add Error.", Toast.LENGTH_LONG).show();
            }else{
                db.close();
                db = this.getReadableDatabase();
                try{
                    db.execSQL("update Notifications set state="+state+" where id="+id+" and name='"+name+"'");
                    Toast.makeText(parent,"Updated Successfully.",Toast.LENGTH_LONG).show();

                }catch(Exception ex){
                    Toast.makeText(parent,ex.getMessage(),Toast.LENGTH_LONG).show();
                }finally {
                    db.close();
                }
            }

        }catch(Exception ex){
            Toast.makeText(parent,ex.getMessage(),Toast.LENGTH_LONG).show();
        }finally {
            db.close();
        }
    }
    public Cursor get_notify_state(int id,String name){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select state from Notifications where id="+id, null);

        return cursor;

    }
}
