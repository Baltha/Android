package com.example.movieproject.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by AdrienPC on 28/05/2014.
 */
public class DetailMovie extends Activity{

    private String titre;
    private String realisateur;
    private String couverture;
    private int visible;
    private int dispo;
    private String acteur;
    private String annee;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //on lui associe le layout detail_movie.xml
        setContentView(R.layout.detail_movie);

        //on récupère tous les éléments
        Button buttonBack = (Button)findViewById(R.id.buttonBack);
        LinearLayout blocDetail=(LinearLayout)findViewById(R.id.BlocDetail);
        TextView titreDetail=(TextView)findViewById(R.id.Titredufilm);
        TextView bigTitleDetail=(TextView)findViewById(R.id.bigTitle);
        TextView realisateurDetail=(TextView)findViewById(R.id.detaitRealisateur);
        CheckBox checkVu=(CheckBox)findViewById(R.id.checkVu);
        CheckBox checkPret=(CheckBox)findViewById(R.id.checkPret);
        TextView acteurDetail=(TextView)findViewById(R.id.acteur);
        TextView anneeDetail=(TextView)findViewById(R.id.annee);

        //On récupère l'objet Bundle envoyé par l'autre Activity
        Bundle objetbunble = this.getIntent().getExtras();

        //On récupère les données du Bundle
        if (objetbunble != null && objetbunble.containsKey("title") && objetbunble.containsKey("realisateur") && objetbunble.containsKey("couverture") && objetbunble.containsKey("visible") && objetbunble.containsKey("dispo") ) {
            titre = this.getIntent().getStringExtra("title");
            realisateur = this.getIntent().getStringExtra("realisateur");
            couverture= this.getIntent().getStringExtra("couverture");
            visible=this.getIntent().getIntExtra("visible", 0);
            dispo=this.getIntent().getIntExtra("dispo",0);
            acteur=this.getIntent().getStringExtra("acteur");
            annee=this.getIntent().getStringExtra("annee");

        } else {
            //Erreur
            titre = "Error";
            realisateur = "Error";
        }

        //On set les champs présents dans la vue

        titreDetail.setText(titre);
        bigTitleDetail.setText(titre);
        realisateurDetail.setText(realisateur);
        acteurDetail.setText(acteur);
        anneeDetail.setText("("+ annee +")");
        if(visible==1)
        checkVu.setChecked(true);
        else
            checkVu.setChecked(false);

        if(dispo==1)
            checkPret.setChecked(true);
        else
            checkPret.setChecked(false);

        if (blocDetail != null) {
            new ImageLayoutDownloaderTask(blocDetail).execute(couverture);
        }
        //En cas de clic sur la flêche on retourne à la page d'accueil
        View.OnClickListener onClickLister = new View.OnClickListener() {

            @Override
            public void onClick(View v){
                switch(v.getId()){

                    case R.id.buttonBack:
                        setResult(1);
                        finish();
                        break;
                }
            }

        };

        buttonBack.setOnClickListener(onClickLister);

    }
}
