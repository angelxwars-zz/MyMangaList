package com.josea.mymangalist.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.josea.mymangalist.model.DetailsManga;
import com.josea.mymangalist.model.MangaDB;
import com.josea.mymangalist.model.MangaDB.MangaEntry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MangaDbHelper  extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Manga.db";

    public MangaDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + MangaEntry.TABLE_NAME + " ("
                + MangaEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MangaEntry.ID + " INTEGER NOT NULL,"
                + MangaEntry.IMAGE_URL + " TEXT NOT NULL,"
                + MangaEntry.TITLE + " TEXT NOT NULL,"
                + MangaEntry.PUBLISHING + " BOOLEAN NOT NULL,"
                + MangaEntry.SYNOPSIS + " TEXT NOT NULL,"
                + MangaEntry.CHAPTERS + " INTEGER,"
                + MangaEntry.RANK + " INTEGER,"
                + MangaEntry.SCORE + " FLOAT NOT NULL,"
                + MangaEntry.START_DATE + " TEXT,"
                + MangaEntry.END_DATE + " TEXT,"
                + MangaEntry.VOLUMES + " TEXT,"
                + "UNIQUE (" + MangaEntry.ID + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MangaEntry.TABLE_NAME );

        sqLiteDatabase.execSQL("CREATE TABLE " + MangaEntry.TABLE_NAME + " ("
                + MangaEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MangaEntry.ID + " INTEGER NOT NULL,"
                + MangaEntry.IMAGE_URL + " TEXT NOT NULL,"
                + MangaEntry.TITLE + " TEXT NOT NULL,"
                + MangaEntry.PUBLISHING + " BOOLEAN NOT NULL,"
                + MangaEntry.SYNOPSIS + " TEXT NOT NULL,"
                + MangaEntry.CHAPTERS + " INTEGER,"
                + MangaEntry.RANK + " INTEGER,"
                + MangaEntry.SCORE + " FLOAT NOT NULL,"
                + MangaEntry.START_DATE + " TEXT,"
                + MangaEntry.END_DATE + " TEXT,"
                + MangaEntry.VOLUMES + " TEXT,"
                + "UNIQUE (" + MangaEntry.ID + "))");
    }

    public long saveManga(DetailsManga manga, int rank) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                MangaEntry.TABLE_NAME,
                null,
                manga.toContentValues(Integer.toString(rank)));
    }

    public long deleteManga(DetailsManga manga) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.delete(
                MangaEntry.TABLE_NAME,
                MangaEntry.ID + " LIKE ?",
                new String[]{Integer.toString(manga.getId())});
    }

    public List<MangaDB> getMyMangas(){
        ArrayList<MangaDB> list = new ArrayList<MangaDB>();
        Cursor cursor;
//        String regex = "T.*";
//        final SimpleDateFormat formatter = new SimpleDateFormat(ISO_8601_24H_FULL_FORMAT);
        Locale spanish = new Locale("es", "ES");
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);


        String selectQuery = "SELECT  * FROM " + MangaEntry.TABLE_NAME;
        try {
            cursor = getReadableDatabase().rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            try {
                if (cursor.moveToFirst()) {
                    do {
                        String start_date_str = cursor.getString(9);
                        String end_date_str = cursor.getString(10);

                        Date start_date = formatter.parse(start_date_str);
                        Date end_date = end_date_str == null ? null : formatter.parse(end_date_str);

                        MangaDB manga = new MangaDB(
                                 cursor.getInt(1),
                                 cursor.getString(2),
                                 cursor.getString(3),
                                 cursor.getInt(4) == 1,
                                 cursor.getString(5),
                                 cursor.getInt(6),
                                 cursor.getInt(7),
                                 cursor.getFloat(8),
                                 start_date,
                                end_date
                                 );
                        Log.d("manga", manga.toString());
                        //only one column
//                        manga.setId(cursor.getString(0));


                        list.add(manga);
                    } while (cursor.moveToNext());
                }
            } catch (ParseException e) {
                e.printStackTrace();
            } finally {
                try {
                    cursor.close();
                } catch (Exception ignore) {
                    Log.d("Exception", ignore.toString());
                }
            }

        } finally {
            try { getReadableDatabase().close(); } catch (Exception ignore) {}
            }
        return list;
    }

    public Cursor getMangaById(String mangaId) {
        return getReadableDatabase()
                .query(
                        MangaEntry.TABLE_NAME,
                        null,
                        MangaEntry.ID + " LIKE ?",
                        new String[]{mangaId},
                        null,
                        null,
                        null);
    }
}
