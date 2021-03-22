package com.deu.multisolvermko.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.deu.multisolvermko.R;
import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class DssFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_dss,container,false);

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TopsisFragment ahpFragment = new TopsisFragment();
        fragmentTransaction.replace(R.id.fragmentFrameLayout,ahpFragment).commit();

        SmoothBottomBar bottomNav = viewGroup.findViewById(R.id.bottomBar);
        bottomNav.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelect(int i) {

                switch (i){
                    case 0:
                        FragmentManager fragmentManager = getParentFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        TopsisFragment topsisFragment = new TopsisFragment();
                        fragmentTransaction.replace(R.id.fragmentFrameLayout,topsisFragment).commit();
                        break;
                    case 1:
                        FragmentManager fragmentManager2 = getParentFragmentManager();
                        FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                        AhpFragment ahpFragment1  = new AhpFragment();
                        fragmentTransaction2.replace(R.id.fragmentFrameLayout,ahpFragment1).commit();
                        break;
                    case 2:
                        FragmentManager fragmentManager3 = getParentFragmentManager();
                        FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                        PremiumFragment premiumFragment  = new PremiumFragment();
                        fragmentTransaction3.replace(R.id.fragmentFrameLayout,premiumFragment).commit();
                        break;
                }
            }
        });

        return viewGroup;
    }
}

