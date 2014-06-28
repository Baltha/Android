package com.example.movieproject.app;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

/**
 * Created by AdrienPC on 23/05/2014.
 * Classe qui permet la création de la Base de donnée ainsi que de la table Vidéos
 */
public class Database extends SQLiteOpenHelper {

    private static final String TABLE_VIDEOS = "table_videos";
    private static final String COL_ID = "ID";
    private static final String COL_NOM = "Nom";
    private static final String COL_REALISTEUR = "Realisateur";
    private static final String COL_IMAGE="ImageC";
    private static final String COL_DATE="Date";
    private static final String COL_VISIBLE="Visible";
    private static final String COL_DISPO="Dispo";
    private static final String COL_ACTEUR="Acteur";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_VIDEOS + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NOM + " TEXT NOT NULL, "
            + COL_REALISTEUR + " TEXT NOT NULL, "
            + COL_IMAGE + " TEXT NOT NULL, "
            + COL_DATE + " TEXT NOT NULL,"
            + COL_VISIBLE + " INTEGER NOT NULL,"
            + COL_DISPO + " INTEGER NOT NULL,"
            + COL_ACTEUR + " TEXT NOT NULL);";

    public Database(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    public boolean onCreateTable(SQLiteDatabase db) {

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", TABLE_VIDEOS});
        cursor.moveToFirst();
        if (cursor.getCount() <= 0 || cursor.getInt(0) <= 0)
        {
            db.execSQL(CREATE_BDD);
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
    public void dropTable(SQLiteDatabase db)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VIDEOS + ";");
    }
}
