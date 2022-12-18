package com.example.mini_projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Connexion_activity extends AppCompatActivity {

    EditText connexionNom;
    EditText connexionMotPass;
    TextView toInscription;

    Button seConnecterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DataBaseHelper db = new DataBaseHelper(Connexion_activity.this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        connexionNom = findViewById(R.id.connexionNom);
        connexionMotPass = findViewById(R.id.connexionMotPass);
        seConnecterBtn = findViewById(R.id.seConnecterBtn);

        toInscription = findViewById(R.id.toInscription);

        seConnecterBtn.setOnClickListener(view -> {

            User user = db.findUser(connexionNom.getText().toString());
            if( user != null){
                if(user.getMp_user().equals(connexionMotPass.getText().toString())){
                    db.connectUser(user.getNom_user());
                    Intent accueil = new Intent(Connexion_activity.this,Accueil_activity.class);
                    startActivity(accueil);
                }else{
                    Toast.makeText(Connexion_activity.this, "Error password ", Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(Connexion_activity.this, "Error username ", Toast.LENGTH_SHORT).show();
            }
        });

        toInscription.setOnClickListener(view -> {
            Intent inscription_activity = new Intent(Connexion_activity.this,Inscription_activity.class);
            startActivity(inscription_activity);
        });

    }
}