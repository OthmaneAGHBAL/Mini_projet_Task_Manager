package com.example.mini_projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Inscription_activity extends AppCompatActivity {
    EditText inscription_nom,inscription_pass,inscription_email;
    Button btnInscription;
    TextView toConnexion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        inscription_nom = findViewById(R.id.editNom);
        inscription_pass = findViewById(R.id.editMotPass);
        inscription_email = findViewById(R.id.editMail);
        btnInscription = findViewById(R.id.button_inscription);

        toConnexion = findViewById(R.id.toConnexion);

        this.btnInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper myDataBase = new DataBaseHelper(Inscription_activity.this);
                if(myDataBase.findUser(inscription_nom.getText().toString()) != null){
                    Toast.makeText(Inscription_activity.this, "Utilisateur deja existe ", Toast.LENGTH_SHORT).show();
                }else{
                    User user = new User(inscription_nom.getText().toString(),inscription_pass.getText().toString(),inscription_email.getText().toString());
                    System.out.println(user.getNom_user());
                    System.out.println(user.getMp_user());
                    System.out.println(user.getEmail_user());
                    myDataBase.saveUser(user);
                    Intent new_Intent = new Intent(Inscription_activity.this,Accueil_activity.class);
                    startActivity(new_Intent);
                }
            }
        });

        toConnexion.setOnClickListener(view -> {
            Intent connexion_activity = new Intent(Inscription_activity.this,Connexion_activity.class);
            startActivity(connexion_activity);
        });
    }


}