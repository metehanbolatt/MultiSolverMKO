package com.deu.multisolvermko.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;
import com.deu.multisolvermko.fragments.adapters.DecisionSupport;
import com.deu.multisolvermko.fragments.adapters.DecisionSupportAdapter;
import com.deu.multisolvermko.premium.PremiumActivity;
import com.deu.multisolvermko.problems.SalesmanProblemActivity;
import com.deu.multisolvermko.R;
import java.util.ArrayList;
import java.util.List;

public class ProblemFragment extends Fragment {

    View layout;
    AlertDialog alertDialog;
    Button premiumQuit, premiumGo;
    ConstraintLayout layoutDialogContainer;
    private DecisionSupportAdapter.RecyclerViewClickListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_problem,container,false);

        layout = inflater.inflate(R.layout.premium_popup,container,false);
        premiumQuit=layout.findViewById(R.id.premiumQuit);
        premiumGo=layout.findViewById(R.id.premiumGo);

        ViewPager2 decisionViewPager = viewGroup.findViewById(R.id.decisionViewPager);
        List<DecisionSupport> decisionSupports = new ArrayList<>();

        DecisionSupport decisionSupportTravelerSalesman = new DecisionSupport();
        decisionSupportTravelerSalesman.imageUrl = "https://i.hizliresim.com/gx193em.png";
        decisionSupportTravelerSalesman.title = "Gezgin Satıcı Problemi";
        decisionSupportTravelerSalesman.name = "MKO";
        decisionSupportTravelerSalesman.feature = "Benzetilmiş Tavlama";
        decisionSupports.add(decisionSupportTravelerSalesman);

        DecisionSupport decisionSupportParallelMachine = new DecisionSupport();
        decisionSupportParallelMachine.imageUrl = "https://i.hizliresim.com/gx193em.png";
        decisionSupportParallelMachine.title = "Paralel Makine Çizelgeleme";
        decisionSupportParallelMachine.name = "MKO";
        decisionSupportParallelMachine.feature = "Genetik Algoritma";
        decisionSupports.add(decisionSupportParallelMachine);

        DecisionSupport decisionSupportPremium = new DecisionSupport();
        decisionSupportPremium.imageUrl = "https://i.hizliresim.com/gx193em.png";
        decisionSupportPremium.title = "Premium";
        decisionSupportPremium.name = "MKO";
        decisionSupportPremium.feature = "Premium";
        decisionSupports.add(decisionSupportPremium);

        setOnClickListener();
        decisionViewPager.setAdapter(new DecisionSupportAdapter(decisionSupports,listener));

        decisionViewPager.setClipToPadding(false);
        decisionViewPager.setClipChildren(false);
        decisionViewPager.setOffscreenPageLimit(3);
        decisionViewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.95f + r * 0.05f);
        });

        decisionViewPager.setPageTransformer(compositePageTransformer);

        layoutDialogContainer= viewGroup.findViewById(R.id.layoutDialogContainer);

        return viewGroup;
    }

    private void setOnClickListener() {
        listener = (v, position) -> {
            if (position == 0){
                Intent intent = new Intent(getActivity(), SalesmanProblemActivity.class);
                startActivity(intent);
            }else if (position == 1){
                Toast.makeText(getActivity(), "Geliştirilme aşamasında..", Toast.LENGTH_SHORT).show();
            }else{
                AlertDialog.Builder builder=new AlertDialog.Builder(requireActivity(),R.style.AlertDialogTheme);
                final View viewView = LayoutInflater.from(getContext()).inflate(R.layout.premium_popup,layoutDialogContainer);
                builder.setView(viewView);

                alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();

                viewView.findViewById(R.id.premiumQuit).setOnClickListener(view -> alertDialog.dismiss());

                viewView.findViewById(R.id.premiumGo).setOnClickListener(view -> {
                    Intent intent = new Intent(getActivity(), PremiumActivity.class);
                    startActivity(intent);
                    alertDialog.dismiss();
                });
            }
        };
    }
}