package com.example.room3;

import static com.example.room3.appDatabase.MIGRATION_1_2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class myAdapter extends RecyclerView.Adapter<myAdapter.myViewHolder> {
    List<User> user;
    appDatabase db;

    public myAdapter(List<User> user) {
        this.user = user;
    }

    @NonNull
    @NotNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_layout_design, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.first.setText(user.get(position).getFirstName());
        holder.second.setText(user.get(position).getMiddleName());
        holder.third.setText(user.get(position).getLastName());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                db = Room.databaseBuilder(holder.first.getContext(),
                                appDatabase.class, "mineDatabase")
                        .addMigrations(MIGRATION_1_2)
                        .build();
                // this is to delete the record from the database
                new Thread(()-> {
                    db.userDao().deleteByName(user.get(position).getFirstName());
                    // this is to delete the record from the arraylist which is the source of the recyclerview data

                    ((Activity) holder.first.getContext()).runOnUiThread(() -> {
                        user.remove(position);
                        //update the fresh list of the array list to the recyclerview
                        notifyDataSetChanged();
                    });
                }).start();
            }
        });

    }


    @Override
    public int getItemCount() {
        return user.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView first;
        TextView second;
        TextView third;
        ImageView delete;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            first = itemView.findViewById(R.id.first);
            second = itemView.findViewById(R.id.second);
            third = itemView.findViewById(R.id.third);
            delete = itemView.findViewById(R.id.delete);

        }
    }
}
