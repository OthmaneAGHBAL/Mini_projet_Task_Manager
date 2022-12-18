package com.example.mini_projet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Task_List_Adapter extends ArrayAdapter<Task> {

    public Task_List_Adapter(Context context, ArrayList<Task> list_task) {
        super(context,0,list_task);
    }


    @Override
    //hadi hya la methode lli kan3ytolha fkolla mrra kanbghi n ajouter wahd la ligne
    public View getView(int position, View view, ViewGroup viewGroup) {
        Task ts = getItem(position);
        if (view == null) {
            LayoutInflater li =  LayoutInflater.from(getContext());

            view = li.inflate(R.layout.item, viewGroup,false);
            TextView tacheNom, tacheDuree;
            tacheNom = view.findViewById(R.id.tacheNom);
            tacheDuree = view.findViewById(R.id.tacheDuree);

            //affectation  des valeurs
            tacheNom.setText(ts.getLabel_task());
            tacheDuree.setText(new StringBuilder().append(ts.getTime_task()).append(" heures").toString());

        }
        return view;
    }
}

