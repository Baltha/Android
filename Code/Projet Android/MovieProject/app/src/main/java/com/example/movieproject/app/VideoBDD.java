package com.example.movieproject.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
/**
 * Created by AdrienPC on 23/05/2014.
 * La Classe VideoBdd permet d'éditer, de supprimer, ect.. la table vidéos.
 */
public class VideoBDD {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "mediatheque.db";

    private static final String TABLE_VIDEOS = "table_videos";
    private static final String COL_ID = "ID";
    private static final String COL_NOM = "Nom";
    private static final String COL_DATE = "Date";
    private static final String COL_REALISATEUR = "Realisateur";
    private static final String COL_VISIBLE="Visible";
    private static final String COL_DISPO="Dispo";
    private static final String COL_IMAGE="ImageC";
    private static final String COL_ACTEUR="Acteur";
    private static final int NUM_COL_ID = 0;
    private static final int NUM_COL_NOM = 1;
    private static final int NUM_COL_DESCRIPTION = 2;
    private static final int NUM_COL_IMAGE = 3;
    private static final int NUM_COL_DATE = 4;
    private static final int NUM_COL_VISIBLE=5;
    private static final int NUM_COL_DISPO=6;
    private static final int NUM_COL_ACTEUR=7;

    private SQLiteDatabase bdd;

    private Database database;

    public VideoBDD(Context context){
        database = new Database(context, NOM_BDD, null, VERSION_BDD);
        bdd = database.getWritableDatabase();
    }

    public void open(){

       if(database.onCreateTable(bdd))
       {
           createList();
       }
    }

    public void close(){
        bdd.close();
    }
    public void delete(){
        database.dropTable(bdd);
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }
    public void createList()
    {
        Video video = new Video("Wolverine : Le combat...", "James Mangold","http://oblikon.net/wp-content/uploads/TheWolverine_poster.jpg", "2013",1,1,"Hugh Jackman");
        insertVideo(video);
        video = new Video("World War Z", "Marc Foster","http://www.aucoeurdelhorreur.com/wp-content/uploads/2013/06/world-war-z-poster03.jpg", "2013",1,1,"Brad Pitt");
        insertVideo(video);
        video = new Video("Only God Forgives", "Nicolas Winding Refn","http://www.criticnic.com/wp-content/uploads/2014/01/only-god-forgives.jpg", "2013",0,1,"Ryan Gosling");
        insertVideo(video);
        video = new Video("Gravity", "Alfonso Cuarón","http://www.tuxboard.com/photos/2013/10/Affiche-Gravity-FILM.jpg", "2013",0,1,"Sandra Bullock / George Clooney");
        insertVideo(video);
        video = new Video("Man of Steel", "Zack Snyder ","http://www.lyricis.fr/wp-content/uploads/2013/05/Man-of-Steel-Affiche-SUPERMAN.jpg", "2013",0,1,"Henry Cavill");
        insertVideo(video);
        video = new Video("Il Était une Forêt", "Luc Jacquet","http://www.leblogducinema.com/wp-content/uploads//2013/08/Affiche-du-film-IL-ETAIT-UNE-FORET.jpg", "2013",0,1,"Documentaire");
        insertVideo(video);
        video = new Video("Godzilla", "Gareth Edwards","http://www.lyricis.fr/wp-content/uploads/2014/03/GODZILLA-Affiche-3.jpg", "2013",0,1,"Aaron Taylor-Johnson");
        insertVideo(video);
    }
    public long insertVideo(Video Video){
        ContentValues values = new ContentValues();
        values.put(COL_NOM, Video.getNom());
        values.put(COL_REALISATEUR, Video.getRealisateur());
        values.put(COL_IMAGE,Video.getimageC());
        values.put(COL_DATE,Video.getDateSortie());
        values.put(COL_VISIBLE,Video.getVisible());
        values.put(COL_DISPO,Video.getDispo());
        values.put(COL_ACTEUR,Video.getActeur());
        return bdd.insert(TABLE_VIDEOS, null, values);
    }

    public int updateVideo(int id, Video Video){
        ContentValues values = new ContentValues();
        values.put(COL_NOM, Video.getNom());
        values.put(COL_REALISATEUR, Video.getRealisateur());
        values.put(COL_IMAGE,Video.getimageC());
        values.put(COL_DATE,Video.getDateSortie());
        values.put(COL_VISIBLE,Video.getVisible());
        values.put(COL_DISPO,Video.getDispo());
        values.put(COL_ACTEUR,Video.getActeur());
        return bdd.update(TABLE_VIDEOS, values, COL_ID + " = " +id, null);
    }

    public int removeVideoWithID(int id){
        return bdd.delete(TABLE_VIDEOS, COL_ID + " = " +id, null);
    }

    public Video getVideoWithName(String nom){
        Cursor c = bdd.query(TABLE_VIDEOS, new String[] {COL_ID, COL_NOM, COL_REALISATEUR, COL_IMAGE, COL_DATE, COL_VISIBLE, COL_DISPO, COL_ACTEUR}, COL_NOM + " LIKE \"" + nom +"\"", null, null, null, null);
        return cursorToVideo(c);
    }
    public Video[] getAll(){
        Cursor c=bdd.query(TABLE_VIDEOS,new String[] {COL_ID, COL_NOM, COL_REALISATEUR, COL_IMAGE, COL_DATE, COL_VISIBLE, COL_DISPO, COL_ACTEUR},"1", null, null, null, null);
        Video[] ret = new Video[c.getCount()];
        for(int i = 0; c.moveToPosition(i); i++)
        {
            ret[i]=new Video();
            ret[i].setId(c.getInt(NUM_COL_ID));
            ret[i].setNom(c.getString(NUM_COL_NOM));
            ret[i].setRealisateur(c.getString(NUM_COL_DESCRIPTION));
            ret[i].setimageC(c.getString(NUM_COL_IMAGE));
            ret[i].setDateSortie(c.getString(NUM_COL_DATE));
            ret[i].setVisible(c.getInt(NUM_COL_VISIBLE));
            ret[i].setDispo(c.getInt(NUM_COL_DISPO));
            ret[i].setActeur(c.getString(NUM_COL_ACTEUR));
        }
            c.close();
        return ret;
    }
    private Video cursorToVideo(Cursor c){
        if (c.getCount() == 0)
            return null;

        c.moveToFirst();

        Video Video = new com.example.movieproject.app.Video();
        Video.setId(c.getInt(NUM_COL_ID));
        Video.setNom(c.getString(NUM_COL_NOM));
        Video.setRealisateur(c.getString(NUM_COL_DESCRIPTION));
        Video.setimageC(c.getString(NUM_COL_IMAGE));
        Video.setDateSortie(c.getString(NUM_COL_DATE));
        Video.setVisible(c.getInt(NUM_COL_VISIBLE));
        Video.setDispo(c.getInt(NUM_COL_DISPO));
        Video.setActeur(c.getString(NUM_COL_ACTEUR));

        c.close();

        return Video;
    }
}
