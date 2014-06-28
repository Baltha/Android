package com.example.movieproject.app;

import java.util.Date;

/**
 * Created by AdrienPC on 23/05/2014.
 * Classe ayant pour volonté de pouvoir set/get l'ensemble des colonnes de la table vidéo.
 */
public class Video {

    private int id;
    private String nom;
    private String realisateur;
    private String imageC;
    private String dateSortie;
    private int visible;
    private int dispo;
    private String acteur;

    public Video(){}

    public Video(String nom, String realisateur, String imageC,String dateSortie,int visible,int dispo,String acteur){
        this.nom = nom;
        this.realisateur = realisateur;
        this.imageC=imageC;
        this.dateSortie=dateSortie;
        this.visible=visible;
        this.dispo=dispo;
        this.acteur=acteur;
    }
    public String getActeur()
    {
        return acteur;
    }
    public void setActeur(String acteur)
    {
        this.acteur=acteur;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getRealisateur() {
        return realisateur;
    }

    public void setRealisateur(String realisateur) {
        this.realisateur = realisateur;
    }
    public void setVisible(int visible){
        this.visible=visible;
    }
    public int getVisible()
    {
        return visible;
    }
    public void setDispo(int dispo)
    {
        this.dispo=dispo;
    }
    public int getDispo()
    {
        return dispo;
    }
    public String getimageC() {
        return imageC;
    }

    public void setimageC(String imageC) {
        this.imageC = imageC;
    }

    public String getDateSortie() {
        return dateSortie;
    }

    public void setDateSortie(String dateSortie) {
        this.dateSortie = dateSortie;
    }

    public String toString(){
        return "ID : "+id+"\nNOM : "+nom+"\nDescription : "+realisateur;
    }
}
