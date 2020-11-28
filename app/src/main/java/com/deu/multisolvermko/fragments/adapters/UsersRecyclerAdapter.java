package com.deu.multisolvermko.fragments.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.deu.multisolvermko.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.PostHolder> {

    ArrayList<String> nameList;
    ArrayList<String> emailList;
    ArrayList<String> surnameList;
    ArrayList<String> urlfoto;
    Context context;

    public UsersRecyclerAdapter(ArrayList<String> nameList, ArrayList<String> emailList, ArrayList<String> surnameList, ArrayList<String> urlfoto) {
        this.nameList = nameList;
        this.emailList = emailList;
        this.surnameList = surnameList;
        this.urlfoto = urlfoto;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.users_ra,parent,false);
        return new PostHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final PostHolder holder, final int position) {

        if (urlfoto.get(position).equals("null")){
            holder.fotoImageView.setImageResource(R.drawable.profile);
            holder.isimTextView.setText(nameList.get(position)+" "+surnameList.get(position));
            holder.emailText.setText(emailList.get(position));

        }else{
            holder.isimTextView.setText(nameList.get(position)+" "+surnameList.get(position));
            holder.emailText.setText(emailList.get(position));
            Picasso.with(context).load(urlfoto.get(position)).into(holder.fotoImageView);
        }
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    class PostHolder extends RecyclerView.ViewHolder {
        TextView isimTextView,emailText;
        ImageView fotoImageView;
        ConstraintLayout UsersLayout;

        public PostHolder(@NonNull View itemView) {
            super(itemView);
            emailText = itemView.findViewById(R.id.emailUsers);
            isimTextView=itemView.findViewById(R.id.isim);
            fotoImageView=itemView.findViewById(R.id.fotoImageView);
            UsersLayout=itemView.findViewById(R.id.UsersLayout);

        }
    }
}
