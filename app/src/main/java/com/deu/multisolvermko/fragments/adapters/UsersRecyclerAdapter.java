package com.deu.multisolvermko.fragments.adapters;

import android.annotation.SuppressLint;
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
    ArrayList<String> urlImage;

    public UsersRecyclerAdapter(ArrayList<String> nameList, ArrayList<String> emailList, ArrayList<String> surnameList, ArrayList<String> urlImage) {
        this.nameList = nameList;
        this.emailList = emailList;
        this.surnameList = surnameList;
        this.urlImage = urlImage;
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

        if (urlImage.get(position).equals("null")){
            holder.userImageView.setImageResource(R.drawable.profile);
            holder.nameTextView.setText(nameList.get(position)+" "+surnameList.get(position));
            holder.emailText.setText(emailList.get(position));

        }else{
            holder.nameTextView.setText(nameList.get(position)+" "+surnameList.get(position));
            holder.emailText.setText(emailList.get(position));
            Picasso.get().load(urlImage.get(position)).into(holder.userImageView);
        }
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    static class PostHolder extends RecyclerView.ViewHolder {
        TextView nameTextView,emailText;
        ImageView userImageView;
        ConstraintLayout usersLayout;

        public PostHolder(@NonNull View itemView) {
            super(itemView);
            emailText = itemView.findViewById(R.id.emailUserText);
            nameTextView =itemView.findViewById(R.id.nameText);
            userImageView =itemView.findViewById(R.id.userImageView);
            usersLayout =itemView.findViewById(R.id.usersLayout);

        }
    }
}