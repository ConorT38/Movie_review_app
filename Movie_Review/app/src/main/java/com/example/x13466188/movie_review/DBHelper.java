package com.example.x13466188.movie_review;

/**
 * Created by x13466188 on 18/11/2016.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movie_app.db";
    public static final String MOVIES_TABLE_NAME = "movies";
    public static final String MOVIES_COLUMN_ID = "id";
    public static final String MOVIES_COLUMN_TITLE = "title";
    public static final String MOVIES_COLUMN_DESCRIPTION = "description";
    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table movies " +
                        "(id integer primary key, title text,description text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS movies");
        onCreate(db);
    }

    public boolean insertMovie(String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("description", description);
        db.insert("contacts", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from movies where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, MOVIES_TABLE_NAME);
        return numRows;
    }

    public boolean updateMovie (Integer id, String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("description", description);
        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteMovie (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("movies",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllMovies() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from movies", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(MOVIES_COLUMN_TITLE)));
            res.moveToNext();
        }
        return array_list;
    }
}