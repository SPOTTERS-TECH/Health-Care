package com.caregiver.healthcare;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Vitals_RecyclerAdapter extends RecyclerView.Adapter<Vitals_RecyclerAdapter.viewHolder> {

    private List<Vitals> List;
    private onItemClickListener clickListener;


    public Vitals_RecyclerAdapter(onItemClickListener clickListener, List<Vitals> List) {
        this.List = List;
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public Vitals_RecyclerAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.previous_vitals_design, parent, false);
        return new viewHolder(view,  clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Vitals_RecyclerAdapter.viewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.dateofappointment.setText(List.get(position).getDate());
        holder.timeofappointment.setText(List.get(position).getTime());



    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView dateofappointment, timeofappointment;


        public viewHolder(@NonNull View view, Vitals_RecyclerAdapter.onItemClickListener clickListener) {
            super(view);
            // ordernumb = view.findViewById(R.id.order_id);
            dateofappointment = view.findViewById(R.id.v_userdate);
            timeofappointment = view.findViewById(R.id.v_usertime);


        }


    }


    public interface onItemClickListener {
        void onItemClickproduct(Product product);
        void onItemClickproduct2(Product product);

    }
}
