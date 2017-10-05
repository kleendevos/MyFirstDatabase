package be.vdab.myfirstdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vdabcursist on 05/10/2017.
 */

public class MyDBAdapter {

    private static final String DATABASE_NAME = "data";

    private Context context;
    private MyDBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private int DATABASE_VERSION = 1;

    public MyDBAdapter(Context context) {
        this.context = context;
        dbHelper = new MyDBHelper(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open(){
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public class MyDBHelper extends SQLiteOpenHelper {
        public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String query = "CREATE TABLE students (id integer primary key autoincrement, name text, faculty integer);";
            sqLiteDatabase.execSQL(query);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            String query = "DROP TABLE IF EXISTS students;";
            sqLiteDatabase.execSQL(query);
            onCreate(sqLiteDatabase);

        }
    }

    public void insertStudent (String name, int faculty){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("faculty", faculty);
        sqLiteDatabase.insert("students", null,contentValues);
    }

    public List <String> selectAllStudents(){
        List<String> allstudents = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query("students", null,null, null, null,null,null);
        if(cursor != null && cursor.moveToFirst()){
            do {
                allstudents.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return allstudents;
    }

    public void deleteAllEngineers(){
        sqLiteDatabase.delete("students","faculty=1",null);
    }
}
