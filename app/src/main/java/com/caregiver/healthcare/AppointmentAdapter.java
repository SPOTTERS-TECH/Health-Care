package com.caregiver.healthcare;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.viewHolder> {
   List<Product> List;
    private onItemClickListener clickListener;


    public AppointmentAdapter(onItemClickListener clickListener, java.util.List<Product> List) {
        this.List = List;
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public AppointmentAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_design, parent, false);
        return new AppointmentAdapter.viewHolder(view,  clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.viewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.receipentname.setText(List.get(position).getUser_name());
        holder.phone.setText(List.get(position).getUser_phone());
        holder.date.setText(List.get(position).getAppointment_date());
        holder.time.setText(List.get(position).getAppointment_time());
        holder.gender.setText(List.get(position).getUser_gender());



        holder.mview.setOnClickListener(new View.OnClickListener() {
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
        TextView receipentname, phone, time, date,gender;
         LinearLayout body;
         View mview;

        public viewHolder(@NonNull View view, AppointmentAdapter.onItemClickListener clickListener) {
            super(view);
            mview = view;
            // ordernumb = view.findViewById(R.id.order_id);
            receipentname = view.findViewById(R.id.a_username);
            phone = view.findViewById(R.id.a_userphone);
            time = view.findViewById(R.id.a_usertime);
            date = view.findViewById(R.id.user_date);
            gender = view.findViewById(R.id.a_gender);



        }

        public View getView()
        {
            return this.mview;
        }
        public void setView(View view)
        {
            mview = view;
        }


    }


    public interface onItemClickListener {
        void onItemClickproduct(Product product);

    }

}
