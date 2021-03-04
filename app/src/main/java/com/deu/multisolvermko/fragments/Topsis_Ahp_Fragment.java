package com.deu.multisolvermko.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.deu.multisolvermko.R;
import com.deu.multisolvermko.topsis_ahp.ahp.AhpFourProcess;
import com.deu.multisolvermko.topsis_ahp.ahp.AhpThreeProcess;
import com.deu.multisolvermko.topsis_ahp.topsis.TopsisActivity;
import com.deu.multisolvermko.topsis_ahp.topsis.TopsisFourActivity;

public class Topsis_Ahp_Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_topsis,container,false);

        Button buttontopsis = viewGroup.findViewById(R.id.buttontopsis);
        Button buttonfour = viewGroup.findViewById(R.id.topsisfour);

        Button ahpThree = viewGroup.findViewById(R.id.ahpthree);
        Button ahpFour = viewGroup.findViewById(R.id.ahpfour);

        buttontopsis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TopsisActivity.class);
                startActivity(intent);
            }
        });

        buttonfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TopsisFourActivity.class);
                startActivity(intent);
            }
        });

        ahpThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AhpThreeProcess.class);
                startActivity(intent);
            }
        });

        ahpFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AhpFourProcess.class);
                startActivity(intent);
            }
        });

        return viewGroup;
    }
}

