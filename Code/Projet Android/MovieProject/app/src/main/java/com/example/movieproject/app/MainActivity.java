package com.example.movieproject.app;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {
    private static final int CODE_DE_MON_ACTIVITE = 1;

    private CustomListAdapter cla;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        //On génére la liste des films (la vue attribué est entre_list.xml)
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entre_list);


        cla = null;
        Button buttonAdd = (Button)findViewById(R.id.buttonAdd);
        /*
            Le code qui permet de récupérer l'ensemble de la liste a été placé dans cette fonction (updateList)
            Car lors de l'ajout d'un film nous somme redirigé vers la page d'accueil, il faut donc regénérer l'ensemble des films et donc refaire appel à cette fonction
            Mais pour gagner du temps les miniatures de la liste sont stockés en cache.
         */
        updateList();
        //Un event Listener sur le boutton Add présent dans le header (header.xml)
        View.OnClickListener onClickLister = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                switch(v.getId()){
                    //si on a cliqué sur le button ADD
                    case R.id.buttonAdd:

                        Intent intent = new Intent(MainActivity.this, AddMovie.class);
                        startActivityForResult(intent, CODE_DE_MON_ACTIVITE);
                        break;
                }
            }

        };
        buttonAdd.setOnClickListener(onClickLister);
    }

    private ArrayList getListData(Video[] videoFromBdd) {
        ArrayList results = new ArrayList();

        for (int i=0;i<videoFromBdd.length;i++)
        {
            results.add(videoFromBdd[i]);
        }

        return results;
    }
    /*
        Fournis l'ensemble des données que l'on a besoin pour générer une liste de film.
        Mais aussi génére un bundle au clic sur un item de la liste et l'envoie à une autre Activity(DetailMovie)
     */
    private void updateList()
    {
        VideoBDD videoBdd = new VideoBDD(this);
        videoBdd.open();
        ArrayList image_details = getListData(videoBdd.getAll());
        final ListView lv1 = (ListView) findViewById(R.id.toplist);

        CustomListAdapter ccla = new CustomListAdapter(this, image_details);
        if(cla != null){
            ccla.setCache(cla.getCache());
        }
        cla = ccla;
        lv1.setAdapter(cla);
        lv1.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = lv1.getItemAtPosition(position);
                Video newsData = (Video) o;
                //Création du bundle
                Bundle objetbunble = new Bundle();
                objetbunble.putString("title", newsData.getNom());
                objetbunble.putString("realisateur",newsData.getRealisateur());
                objetbunble.putString("couverture",newsData.getimageC());
                objetbunble.putInt("visible",newsData.getVisible());
                objetbunble.putInt("dispo",newsData.getDispo());
                objetbunble.putString("acteur",newsData.getActeur());
                objetbunble.putString("annee",newsData.getDateSortie());
                //On créé l'Intent qui va nous permettre d'afficher l'autre Activity
                Intent intent = new Intent(MainActivity.this, DetailMovie.class);

                //On affecte à l'Intent le Bundle que l'on a créé
                intent.putExtras(objetbunble);

                //On démarre l'autre Activity
                startActivity(intent);
            }

        });
        videoBdd.close();
    }
    //Si l'on viens de retourner sur la page principal, on régénere l'ensemble de la liste
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode){
            case CODE_DE_MON_ACTIVITE:

                switch(resultCode){
                    case 1:
                        updateList();
                        return;
                }
        }
    }
}