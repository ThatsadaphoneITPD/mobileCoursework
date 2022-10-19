package com.example.tripexpense;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    //add new context and array property

    private Context context;
    private Activity activity;
    private ArrayList trip_id, trip_title, trip_destination, trip_date, trip_risk, trip_description;
    //put Animation to MainLayout
    Animation translate_anim;
    //identify each file in List data
    int position;
    public TripAdapter ( Activity activity,
                  Context context,
                  ArrayList trip_id,
                  ArrayList trip_title,
                  ArrayList trip_destination,
                  ArrayList trip_date,
                  ArrayList trip_risk,
                  ArrayList trip_description){
        this.activity = activity;
        this.context = context;
        this.trip_id = trip_id;
        this.trip_title = trip_title;
        this.trip_destination = trip_destination;
        this.trip_date = trip_date;
        this.trip_risk = trip_risk;
        this.trip_description = trip_description;

    }

    @NonNull
    @Override
    public TripAdapter.TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //put or inflater in row layout of recycleView by inflater method
        LayoutInflater inflater = LayoutInflater.from(context);
        //pass our layout and second to group parent
        View view = inflater.inflate(R.layout.trip_row, parent, false);
        return new TripViewHolder(view);
    }
    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull TripAdapter.TripViewHolder holder, int position) {
        //on click some item on list to Edit page
        this.position = position;

        holder.trip_id_txt.setText(String.valueOf(trip_id.get(position)));
        holder.trip_title_txt.setText(String.valueOf(trip_title.get(position)));
        holder.trip_destination_txt.setText(String.valueOf(trip_destination.get(position)));
        holder.trip_date_txt.setText(String.valueOf(trip_date.get(position)));
        holder.trip_risk_txt.setText(String.valueOf(trip_risk.get(position)));
        holder.trip_description_txt.setText(String.valueOf(trip_description.get(position)));

        //click for edit function page

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                pass one context data from those position key id or mainActivity to Update page by putExtra
                Intent intent = new Intent(view.getContext(), EditTripActivity.class);

//                define a data to pass from mainAct to UpdateACt
                intent.putExtra("id", String.valueOf(trip_id.get(position)));
                intent.putExtra("title", String.valueOf(trip_title.get(position)));
                intent.putExtra("destination", String.valueOf(trip_destination.get(position)));
                intent.putExtra("date", String.valueOf(trip_date.get(position)));
                intent.putExtra("risk", String.valueOf(trip_risk.get(position)));
                intent.putExtra("description", String.valueOf(trip_description.get(position)));
//                this StatACTForResult will refresh Data view list after Edit

                activity.startActivityForResult(intent,1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return trip_id.size();
    }

    public class TripViewHolder extends RecyclerView.ViewHolder {
        TextView trip_id_txt,trip_title_txt, trip_destination_txt, trip_date_txt, trip_risk_txt, trip_description_txt;
        //declare values layout from My_Row
        ConstraintLayout mainLayout;
        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            trip_id_txt = itemView.findViewById(R.id.trip_id_txt);
            trip_title_txt  = itemView.findViewById(R.id.trip_title_txt);
            trip_destination_txt  = itemView.findViewById(R.id.trip_address_txt);
            trip_date_txt  = itemView.findViewById(R.id.trip_date_txt);
            trip_risk_txt  = itemView.findViewById(R.id.trip_risk_txt);
            trip_description_txt = itemView.findViewById(R.id.trip_message_txt);
            //mainLayout form my_row
            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animate recyclerview from res/anim into mainLayout
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }

}
