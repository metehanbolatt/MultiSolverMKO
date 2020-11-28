package com.deu.multisolvermko.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class UsersFragment extends Fragment {
    CollectionReference collectionReference;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    private ArrayList<String> UsersNameList;
    private ArrayList<String> UsersSurnameList;
    private ArrayList<String> UsersEmailList;
    private ArrayList<String> UsersUrlList;
    private Timer timer;
    EditText aramaEditText;
    UsersRecyclerAdapter usersRecyclerAdapter;
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_users, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        aramaEditText = viewGroup.findViewById(R.id.aramaEditText);
        recyclerView = viewGroup.findViewById(R.id.recyclerViewUsers);

        UsersNameList = new ArrayList<>();
        UsersSurnameList = new ArrayList<>();
        UsersEmailList = new ArrayList<>();
        UsersUrlList = new ArrayList<>();

        searchNotes2();

        aramaEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                UsersUrlList.clear();
                UsersSurnameList.clear();
                UsersNameList.clear();
                UsersEmailList.clear();
                cancelTimer();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    searchNotes2();
                }else{
                    searchNotes(s.toString());
                }
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext().getApplicationContext()));
        usersRecyclerAdapter = new UsersRecyclerAdapter(UsersNameList, UsersEmailList,UsersSurnameList,UsersUrlList);
        recyclerView.setAdapter(usersRecyclerAdapter);

        return viewGroup;
    }

    public void searchNotes(final String searchKeyword){

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                collectionReference = firebaseFirestore.collection("Users");
                collectionReference.whereEqualTo("name",searchKeyword).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null) {
                            Toast.makeText(requireContext().getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }

                        if (value != null) {
                            for (DocumentSnapshot snapshot : value.getDocuments()) {

                                final Map<String, Object> data = snapshot.getData();
                                String name = (String) data.get("name");
                                String surname = (String) data.get("surname");
                                String email = (String) data.get("useremail");
                                String url = (String) data.get("urlfoto");

                                UsersNameList.add(name);
                                UsersSurnameList.add(surname);
                                UsersEmailList.add(email);
                                UsersUrlList.add(url);

                                usersRecyclerAdapter.notifyDataSetChanged();

                            }
                        }
                    }
                });
            }
        },500);
    }

    public void searchNotes2(){

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                collectionReference = firebaseFirestore.collection("Users");
                collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null) {
                            Toast.makeText(requireContext().getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }

                        if (value != null) {
                            UsersUrlList.clear();
                            UsersSurnameList.clear();
                            UsersNameList.clear();
                            UsersEmailList.clear();

                            for (DocumentSnapshot snapshot : value.getDocuments()) {
                                final Map<String, Object> data = snapshot.getData();
                                assert data != null;
                                String name = (String) data.get("name");
                                String surname = (String) data.get("surname");
                                String email = (String) data.get("useremail");
                                String url = (String) data.get("urlfoto");

                                UsersNameList.add(name);
                                UsersSurnameList.add(surname);
                                UsersEmailList.add(email);
                                UsersUrlList.add(url);

                                usersRecyclerAdapter.notifyDataSetChanged();

                            }
                        }
                    }
                });
            }
        },500);
    }

    public void cancelTimer(){
        if (timer != null){
            timer.cancel();
        }
    }




}

