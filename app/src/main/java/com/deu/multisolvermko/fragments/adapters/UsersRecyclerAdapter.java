package com.deu.multisolvermko.fragments.adapters;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.deu.multisolvermko.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.PostHolder> {

    ArrayList<String> nameList;
    ArrayList<String> emailList;
    ArrayList<String> surnameList;
    ArrayList<String> urlfoto;
    Context context;

    Integer a = 0;

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

    @Override
    public void onBindViewHolder(@NonNull final PostHolder holder, final int position) {

        holder.isimTextView.setText(nameList.get(position));
        holder.surnameTextView.setText(surnameList.get(position));
        Picasso.with(context).load(urlfoto.get(position)).into(holder.fotoImageView);
        holder.UsersLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("vvvvv");
                System.out.println(v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    class PostHolder extends RecyclerView.ViewHolder {
        TextView isimTextView,surnameTextView;
        ImageView fotoImageView;
        ConstraintLayout UsersLayout;

        public PostHolder(@NonNull View itemView) {
            super(itemView);

            isimTextView=itemView.findViewById(R.id.isim);
            surnameTextView=itemView.findViewById(R.id.surnameTextView);
            fotoImageView=itemView.findViewById(R.id.fotoImageView);
            UsersLayout=itemView.findViewById(R.id.UsersLayout);

        }
    }
}





