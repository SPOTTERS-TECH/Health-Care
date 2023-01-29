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

public class RecyclerAdapter2 extends RecyclerView.Adapter<RecyclerAdapter2.viewHolder> {

    private java.util.List<Product> List;
    private onItemClickListener clickListener;


    public RecyclerAdapter2(onItemClickListener clickListener, List<Product> List) {
        this.List = List;
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design2, parent, false);
        return new viewHolder(view,  clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.receipentname.setText(List.get(position).getUser_name());
        holder.gender.setText(List.get(position).getUser_gender());
        holder.dateofappointment.setText(List.get(position).getAppointment_date());
        holder.timeofappointment.setText(List.get(position).getAppointment_time());

        holder.removebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClickproduct2(List.get(position));
                notifyItemChanged(position);

            }
        });

        holder.acceptbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClickproduct(List.get(position));
                notifyItemChanged(position);

            }
        });

    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView receipentname, gender, dateofappointment, timeofappointment;
        Button acceptbtn, removebtn;

        public viewHolder(@NonNull View view, onItemClickListener clickListener) {
            super(view);
           // ordernumb = view.findViewById(R.id.order_id);
            receipentname = view.findViewById(R.id.receipent_name);
            gender = view.findViewById(R.id.gender);
            dateofappointment = view.findViewById(R.id.dateofappointment);
            timeofappointment = view.findViewById(R.id.timeofappointment);
            acceptbtn = view.findViewById(R.id.acceptbtn);
            removebtn = view.findViewById(R.id.cancelbtn);


        }


    }


    public interface onItemClickListener {
        void onItemClickproduct(Product product);
        void onItemClickproduct2(Product product);

    }

}
