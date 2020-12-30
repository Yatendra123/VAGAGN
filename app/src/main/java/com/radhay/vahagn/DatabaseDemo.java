package com.radhay.vahagn;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
public class DatabaseDemo extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="Home_Details";
    public  static final String Table_name="Personal_detail";
    public  static final String Table_name1="Contact_detail";
    public  static final String Table_name2="location";
    public static final String Col_1 = "Name";
    public static final String Col_2 = "Address";
    public static final String Col_3 = "Phone";
    public static final String Col_4 = "blood_type";
    public static final String Col_5 = "Contact_Name";
    public static final String Col_6 = "Contact_No";
    public static final String col1 = "Name";
    public static final String col2 = "E_Contact";
    public static final String col11 = "loc";
    public DatabaseDemo(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+Table_name+" (Name TEXT,Address TEXT,Phone NUM,blood_type TEXT,Contact_Name TEXT,Contact_No NUM not null primary key)");
        db.execSQL("create table "+Table_name1+" (Name TEXT,E_Contact NUM NOT Null primary key)");
        db.execSQL("create table "+Table_name2+"(loc TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Table_name);
        db.execSQL("DROP TABLE IF EXISTS "+Table_name1);
        db.execSQL("DROP TABLE IF EXISTS "+Table_name2);
        onCreate(db);
    }

    public boolean insertData(String name, String address, String phone, String blood_type, String Contact_name, String Contact_no){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(Col_1, name);
            contentValues.put(Col_2, address);
            contentValues.put(Col_3, phone);
            contentValues.put(Col_4, blood_type);
            contentValues.put(Col_5, Contact_name);
            contentValues.put(Col_6, Contact_no);
            long result = db.insert(Table_name, null, contentValues);
            return result != -1;
        }catch (Exception e){
            return false;
        }
    }
    public boolean addData(String Name,String E_Contact){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(col1, Name);
            contentValues.put(col2, E_Contact);
            long result = db.insert(Table_name1, null, contentValues);
            return result != -1;
        }catch (Exception e){
            return false;
        }
    }
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from "+Table_name,null);
    }
    public Cursor setData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from "+Table_name1,null);
    }
    public boolean addlocation(String location){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(col1, location);
            long result = db.insert(Table_name2, null, contentValues);
            return result != -1;
        }catch (Exception e){
            return false;
        }
    }
    public boolean updateloc(String loc1){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("update " + Table_name2 + " set loc='" + loc1);
            return  true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public Cursor getloc(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from "+Table_name2,null);
    }
    public boolean updateData(String Contact_name, String Contact_no){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor=getAllData();
            cursor.moveToFirst();
            String number = cursor.getString(5);
            System.out.println("the updated number "+number);
            db.execSQL("update " + Table_name + " set Contact_name='" + Contact_name + "',Contact_no= '" + Contact_no + "'");
            db.execSQL("update " + Table_name1 + " set Contact_name='" + Contact_name + "',Contact_no= '" + Contact_no + "' where Contact_no='"+number+"'");
            
            return true;
        }catch (Exception e){
            return  false;
        }
    }
    public boolean userUpdate(String name,String Address,String phone,String blood_type){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("update " + Table_name + " set Name='" + name + "',Address='" + Address + "',Phone='" + phone + "',blood_type='" + blood_type + "'");
            return true;
        }catch (Exception e){
            return  false;
        }
    }
}