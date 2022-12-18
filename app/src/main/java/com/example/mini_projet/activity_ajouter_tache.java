package com.example.mini_projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class activity_ajouter_tache extends AppCompatActivity {

    // Menu Buttons
    Button btnTOadd;
    Button btnTOhome;
    Button btnLogout;

    EditText taskLabel,taskDescription,taskTime;

    Button addTask;
    User user;
    RadioGroup priorityRadioGrp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_tache);

        // Recuperation des Buttons
        btnTOadd = findViewById(R.id.btnTOadd);
        btnTOhome = findViewById(R.id.btnTOhome);
        btnLogout = findViewById(R.id.btnTOlogout);

        addTask = findViewById(R.id.addTask);

        priorityRadioGrp = findViewById(R.id.priorityRadioGrp);
        taskLabel = findViewById(R.id.taskLabel);
        taskDescription = findViewById(R.id.taskDescription);
        taskTime = findViewById(R.id.taskTime);

        DataBaseHelper myDB = new DataBaseHelper(activity_ajouter_tache.this);
        user = myDB.connectedUser();

         //Event click sur les Buttons

        /*// add task btn
        this.btnTOadd.setOnClickListener(view -> {
            Intent activity_add = new Intent(Accueil_activity.this, activity_ajouter_tache.class);
            startActivity(activity_add);
            });*/
        // home btn
        this.btnTOhome.setOnClickListener(view -> {
            Intent activity_home = new Intent(activity_ajouter_tache.this, Accueil_activity.class);
            startActivity(activity_home);
        });
        // logout btn
        this.btnLogout.setOnClickListener(view -> {
            myDB.disconnected();
            Intent activity_logout = new Intent(activity_ajouter_tache.this, Connexion_activity.class);
            startActivity(activity_logout);
        });

        this.addTask.setOnClickListener(view -> {
            DataBaseHelper myDataBase = new DataBaseHelper(activity_ajouter_tache.this);
            RadioButton checkedRadioBtn = findViewById(priorityRadioGrp.getCheckedRadioButtonId());
            Task task = new Task(taskLabel.getText().toString(),taskDescription.getText().toString(),taskTime.getText().toString(),checkedRadioBtn.getText().toString());
            myDB.addTask(user,task);
        });
    }
}