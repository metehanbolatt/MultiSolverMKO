package com.deu.multisolvermko.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.deu.multisolvermko.R;
import com.deu.multisolvermko.fragments.adapters.UsersRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Map;

public class UsersFragment extends Fragment {

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    ArrayList<String> UsersNameList;
    ArrayList<String> UsersSurnameList;
    ArrayList<String> UsersEmailList;

    UsersRecyclerAdapter usersRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_users, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        UsersNameList = new ArrayList<>();
        UsersSurnameList = new ArrayList<>();
        UsersEmailList = new ArrayList<>();

        CollectionReference collectionReference = firebaseFirestore.collection("Users");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    Toast.makeText(getContext().getApplicationContext(), error.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                }

                if (value != null) {

                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                        Map<String, Object> data = snapshot.getData();
                        String name = (String) data.get("name");
                        String surname = (String) data.get("surname");
                        String email = (String) data.get("useremail");

                        UsersNameList.add(name);
                        UsersSurnameList.add(surname);
                        UsersEmailList.add(email);

                        usersRecyclerAdapter.notifyDataSetChanged();

                    }
                }
            }
        });

        RecyclerView recyclerView = viewGroup.findViewById(R.id.recyclerViewUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
        usersRecyclerAdapter = new UsersRecyclerAdapter(UsersNameList, UsersEmailList,UsersSurnameList);
        recyclerView.setAdapter(usersRecyclerAdapter);

        return viewGroup;
    }
}