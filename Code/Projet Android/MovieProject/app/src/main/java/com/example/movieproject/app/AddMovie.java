package com.example.movieproject.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by AdrienPC on 03/06/2014.
 */
public class AddMovie extends Activity{

    private EditText titreAdd;
    private EditText realisateurAdd;
    private CheckBox checkVuAdd;
    private CheckBox checkPretAdd;
    private EditText acteurAdd;
    private EditText anneeAdd;
    private EditText urlAdd;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final VideoBDD  videoBdd = new VideoBDD(this);
        setContentView(R.layout.addmovie);

        //on récupère tous les éléments
        Button buttonBack = (Button)findViewById(R.id.buttonBack);
        Button buttonValider = (Button)findViewById(R.id.buttonValider);
        titreAdd=(EditText)findViewById(R.id.editTextTitle);
        realisateurAdd=(EditText)findViewById(R.id.editTextReal);
        checkVuAdd=(CheckBox)findViewById(R.id.checkBoxVu);
        checkPretAdd=(CheckBox)findViewById(R.id.checkBoxDispo);
        acteurAdd=(EditText)findViewById(R.id.editTextActeur);
        anneeAdd=(EditText)findViewById(R.id.editTextAnnee);
        urlAdd=(EditText)findViewById(R.id.editTextUrl);

           //En cas de clic sur Ajouter, on récupère les valeurs des champs et l'on ajoute le film dans la bdd.
        View.OnClickListener onClickLister = new View.OnClickListener() {

            @Override
            public void onClick(View v){
                switch(v.getId()){

                    case R.id.buttonValider:
                        Video video = new Video(
                                titreAdd.getText().toString(),
                                realisateurAdd.getText().toString(),
                                urlAdd.getText().toString(),
                                anneeAdd.getText().toString(),
                                (checkVuAdd.isChecked() ? 1 : 0),
                                (checkPretAdd.isChecked() ? 1 : 0),
                                acteurAdd.getText().toString()
                        );
                        videoBdd.insertVideo(video);
                        setResult(1);
                        finish();
                        break;
                    case R.id.buttonBack:
                        setResult(1);
                        finish();
                        break;
                }
            }

        };

        buttonValider.setOnClickListener(onClickLister);
        buttonBack.setOnClickListener(onClickLister);

    }
}
