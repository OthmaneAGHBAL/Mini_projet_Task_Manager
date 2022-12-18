package com.example.mini_projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button connexionBtn;
    Button inscriptionBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.connexionBtn = findViewById(R.id.connexionBtn);
        this.inscriptionBtn = findViewById(R.id.inscriptionBtn);

        // Fct connexionBtn
        this.connexionBtn.setOnClickListener(view -> {
            Intent connexionIntent = new Intent(MainActivity.this,Connexion_activity.class);
            //connexionIntent.putExtra("test", "test");
            //test à récupérer dans hello par getStringExtra("test");
            startActivity(connexionIntent);
        });

        // Fct inscriptionBtn
        this.inscriptionBtn.setOnClickListener(view -> {
            Intent inscriptionIntent = new Intent(MainActivity.this,Inscription_activity.class);
            startActivity(inscriptionIntent);
        });
    }


}