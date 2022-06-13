package com.example.cms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class mainAdapter extends RecyclerView.Adapter<mainAdapter.MyViewHolder> {

    ArrayList<com.example.cms.main> list;
    com.example.cms.OnMainBoardRowClickListener listener;

    public mainAdapter(ArrayList<com.example.cms.main> arrayList, com.example.cms.OnMainBoardRowClickListener listener)
    {
        this.list = arrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.main_board_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        com.example.cms.main current=list.get(position);
        holder.name.setText(list.get(position).getName());
        holder.position.setText(list.get(position).getPosition());
        Glide.with(holder.img.getContext()).load(current.getPhoto()).into(holder.img);
         holder.bind(current,listener);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(ArrayList<com.example.cms.main> filteredlist) {
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView name,position;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.profile);
            name = itemView.findViewById(R.id.txt_name);
            position = itemView.findViewById(R.id.txt_role);
        }
        public void bind(final com.example.cms.main item, final com.example.cms.OnMainBoardRowClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
