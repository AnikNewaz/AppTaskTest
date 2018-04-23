package com.appmaester.apptest.Helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.google.gson.Gson;
import com.appmaester.apptest.Model.Item;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Nik on 4/23/2018.
 */

public class Database {

    public void createDB(Context mContext){
        SQLiteDatabase ocoDB = mContext.openOrCreateDatabase("apptestdb", MODE_PRIVATE, null);
        ocoDB.execSQL("CREATE TABLE IF NOT EXISTS apptestposts (published DATETIME, item TEXT, favorite LONG)");
        ocoDB.close();
    }

    public Boolean checkIfExists(Context mContext,Item post){
        Gson gson = new Gson();
        String json = gson.toJson(post);
        SQLiteDatabase ocoDB = mContext.openOrCreateDatabase("apptestdb", MODE_PRIVATE, null);
        String rawSQL = "SELECT * FROM apptestposts WHERE item= ?";
        Cursor c = ocoDB.rawQuery(rawSQL, new String[]{json});
        Boolean response = false;
        if (c.getCount() > 0){
            response = true;
        }
        ocoDB.close();
        return response;
    }

    public void insertPost(Context mContext, Item post){
        SQLiteDatabase ocoDB = mContext.openOrCreateDatabase("apptestdb", MODE_PRIVATE, null);
        Gson gson = new Gson();
        String json = gson.toJson(post);
        String sql = "INSERT INTO apptestposts (published, item, favorite) VALUES (?, ?, ?)";
        SQLiteStatement statement = ocoDB.compileStatement(sql);

        statement.bindString(1, post.getPublished().toString());
        statement.bindString(2, json);
        statement.bindLong(3,0);

        statement.execute();
        statement.close();

        ocoDB.close();
    }

    public void makeFavorite(Context mContext, Item post, Integer fav){
        SQLiteDatabase ocoDB = mContext.openOrCreateDatabase("apptestdb", MODE_PRIVATE, null);
        Gson gson = new Gson();
        String json = gson.toJson(post);
        String sql = "UPDATE apptestposts SET favorite = ? WHERE item = ?";
        SQLiteStatement statement = ocoDB.compileStatement(sql);
        statement.bindLong(1, fav);
        statement.bindString(2, json);
        statement.execute();
        statement.close();
        ocoDB.close();
    }

    public Boolean isFavorite(Context mContext, Item post){
        SQLiteDatabase ocoDB = mContext.openOrCreateDatabase("apptestdb", MODE_PRIVATE, null);
        Gson gson = new Gson();
        String json = gson.toJson(post);
        String rawSQL = "SELECT * FROM apptestposts WHERE item = ?";
        Cursor c = ocoDB.rawQuery(rawSQL, new String[]{json});
        int itemIndex = c.getColumnIndex("favorite");
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Long favorite = c.getLong(itemIndex);
            Log.d("is favorite", ""+favorite);
            if (favorite == 1){
                return true;
            }
            c.moveToNext();
        }
        return false;
    }

    public Boolean isDBEmpty(Context mContext){
        SQLiteDatabase ocoDB = mContext.openOrCreateDatabase("apptestdb", MODE_PRIVATE, null);
        Cursor c = ocoDB.rawQuery("SELECT * FROM apptestposts", null);
        if (c.getCount() == 0){
            return true;
        }
        return false;
    }
}

