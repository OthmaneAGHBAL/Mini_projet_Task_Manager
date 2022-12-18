package com.example.mini_projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class Accueil_activity extends AppCompatActivity {

    // Menu Buttons
    Button btnTOadd;
    Button btnTOhome;
    Button btnLogout;

    ListView listTaches;
    ArrayList<Task> liste ;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        // Recuperation des Buttons
        btnTOadd = findViewById(R.id.btnTOadd);
        btnTOhome = findViewById(R.id.btnTOhome);
        btnLogout = findViewById(R.id.btnTOlogout);

        // Bd, recuperation de user et liste
        DataBaseHelper myDB = new DataBaseHelper(Accueil_activity.this);
        user = myDB.connectedUser();
        liste = myDB.findTASKS_USER(user);

        // add task btn
        this.btnTOadd.setOnClickListener(view -> {
            Intent activity_add = new Intent(Accueil_activity.this, activity_ajouter_tache.class);
            startActivity(activity_add);
            });
        /*// home btn
        this.btnTOadd.setOnClickListener(view -> {
            Intent activity_home = new Intent(Accueil_activity.this, activity_ajouter_tache.class);
            startActivity(activity_add);
        });*/
        // logout btn
        this.btnLogout.setOnClickListener(view -> {
            myDB.disconnected();
            Intent activity_logout = new Intent(Accueil_activity.this, Connexion_activity.class);
            startActivity(activity_logout);
        });


        listTaches = findViewById(R.id.listTaches);
        Task_List_Adapter theAdapter = new Task_List_Adapter(this,liste);
        listTaches.setAdapter(theAdapter);

        listTaches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent tache_details = new Intent(Accueil_activity.this,activity_tache_details.class);
                tache_details.putExtra("position_tache",String.valueOf(i));
                startActivity(tache_details);
            }
        });


    }
}