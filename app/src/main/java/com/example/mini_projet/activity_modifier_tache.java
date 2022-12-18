package com.example.mini_projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Hashtable;

public class activity_modifier_tache extends AppCompatActivity {

    // Menu Buttons
    Button btnTOadd;
    Button btnTOhome;
    Button btnLogout;

    EditText taskLabelModif,taskDescriptionModif,taskTimeModif;
    RadioButton priorityRadioBtn;
    RadioGroup priorityRadioGrp;
    Button updateTask;
    Task task;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_tache);

        // Recuperation des Buttons
        btnTOadd = findViewById(R.id.btnTOadd);
        btnTOhome = findViewById(R.id.btnTOhome);
        btnLogout = findViewById(R.id.btnTOlogout);

        taskLabelModif = findViewById(R.id.taskLabelModif);
        taskDescriptionModif = findViewById(R.id.taskDescriptionModif);
        taskTimeModif = findViewById(R.id.taskTimeModif);

        updateTask = findViewById(R.id.updateTask);



        // Bd, recuperation de user et liste
        DataBaseHelper myDB = new DataBaseHelper(activity_modifier_tache.this);
        user = myDB.connectedUser();
        Intent i = getIntent();
        if(i != null){
            task = myDB.findTASKS_USER(user).get(Integer.parseInt(i.getStringExtra("position_tache")));
        }



        taskLabelModif.setText(task.getLabel_task());
        taskDescriptionModif.setText(task.getDescription_task());
        taskTimeModif.setText(task.getTime_task());

        if(task.getPriority_task() == "urgent , important"){
            priorityRadioBtn = findViewById(R.id.priority1);
        }if(task.getPriority_task() == "urgent , pas important"){
            priorityRadioBtn = findViewById(R.id.priority2);
        }if(task.getPriority_task() == "pas urgent , important"){
            priorityRadioBtn = findViewById(R.id.priority3);
        }if(task.getPriority_task() == "pas urgent , pas important"){
            priorityRadioBtn = findViewById(R.id.priority4);
        }

        priorityRadioBtn.setChecked(true);


        // add task btn
        this.btnTOadd.setOnClickListener(view -> {
            Intent activity_add = new Intent(activity_modifier_tache.this, activity_ajouter_tache.class);
            startActivity(activity_add);
        });
        // home btn
        this.btnTOhome.setOnClickListener(view -> {
            Intent activity_home = new Intent(activity_modifier_tache.this, Accueil_activity.class);
            startActivity(activity_home);
        });
        // logout btn
        this.btnLogout.setOnClickListener(view -> {
            myDB.disconnected();
            Intent activity_logout = new Intent(activity_modifier_tache.this, Connexion_activity.class);
            startActivity(activity_logout);
        });

        updateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                priorityRadioGrp = findViewById(R.id.priorityRadioGrp);
                RadioButton checkedRadioBtn = findViewById(priorityRadioGrp.getCheckedRadioButtonId());
                Task new_task = new Task(taskLabelModif.getText().toString(),taskDescriptionModif.getText().toString(),taskTimeModif.getText().toString(),checkedRadioBtn.getText().toString());
                myDB.updateTask(myDB.find_Task_Id_From_List(user,i.getStringExtra("position_tache")),new_task);

                Intent activity_home = new Intent(activity_modifier_tache.this, Accueil_activity.class);
                startActivity(activity_home);

            }
        });

    }
}