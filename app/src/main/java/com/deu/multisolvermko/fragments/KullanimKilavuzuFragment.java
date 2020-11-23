package com.deu.multisolvermko.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.deu.multisolvermko.R;
import com.deu.multisolvermko.mainactivity.FirstMainActivity;


public class KullanimKilavuzuFragment extends Fragment {

    int a = 5;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_kullanim_kilavuzu,container,false);

        Button button = viewGroup.findViewById(R.id.kilavuzButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FirstMainActivity.class);
                startActivity(intent);
            }
        });
        return viewGroup;
    }
}