package com.example.mini_projet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class activity_tache_details extends AppCompatActivity {


    // Menu Buttons
    Button btnTOadd;
    Button btnTOhome;
    Button btnLogout;

    TextView label_content,priority_content,time_content,description_content;

    ArrayList<Task> liste ;
    User user;
    Task task;

    Button doneBtn,updateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tache_details);

        // Recuperation des Buttons
        btnTOadd = findViewById(R.id.btnTOadd);
        btnTOhome = findViewById(R.id.btnTOhome);
        btnLogout = findViewById(R.id.btnTOlogout);

        // Bd, recuperation de user et liste
        DataBaseHelper myDB = new DataBaseHelper(activity_tache_details.this);
        user = myDB.connectedUser();
        liste = myDB.findTASKS_USER(user);

        label_content = findViewById(R.id.label_content);
        priority_content = findViewById(R.id.priority_content);
        time_content = findViewById(R.id.time_content);
        description_content = findViewById(R.id.description_content);

        doneBtn = findViewById(R.id.doneBtn);
        updateBtn = findViewById(R.id.updateBtn);

        //Event click sur les Buttons

        // add task btn
        this.btnTOadd.setOnClickListener(view -> {
            Intent activity_add = new Intent(activity_tache_details.this, activity_ajouter_tache.class);
            startActivity(activity_add);
            });
        // home btn
        this.btnTOhome.setOnClickListener(view -> {
            Intent activity_home = new Intent(activity_tache_details.this, Accueil_activity.class);
            startActivity(activity_home);
        });
        // logout btn
        this.btnLogout.setOnClickListener(view -> {
            myDB.disconnected();
            Intent activity_logout = new Intent(activity_tache_details.this, Connexion_activity.class);
            startActivity(activity_logout);
        });

        Intent recup = getIntent();

        if(recup != null){
            task = myDB.findTASKS_USER(user).get(Integer.parseInt(recup.getStringExtra("position_tache")));
            //task = myDB.find_Task_From_List(user,recup.getStringExtra("position_tache"));
            label_content.setText(task.getLabel_task());
            description_content.setText(task.getDescription_task());
            time_content.setText(new StringBuilder().append(task.getTime_task()).append(" heures").toString());
            priority_content.setText(task.getPriority_task());
            myDB.close();
        }


        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity_tache_details.this);
                builder.setCancelable(true);
                builder.setTitle("Confirmer votre choix");
                builder.setMessage("Est ce que vous avez terminé la tache ?");
                builder.setPositiveButton("Oui",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                myDB.getWritableDatabase();
                                myDB.deleteTask(myDB.find_Task_Id_From_List(user,recup.getStringExtra("position_tache")));
                                Intent activity_home = new Intent(activity_tache_details.this, Accueil_activity.class);
                                startActivity(activity_home);
                            }
                        });
                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tache_modifier = new Intent(activity_tache_details.this,activity_modifier_tache.class);
                tache_modifier.putExtra("position_tache",String.valueOf(recup.getStringExtra("position_tache")));
                startActivity(tache_modifier);
            }
        });

    }
}