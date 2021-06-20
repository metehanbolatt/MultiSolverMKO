package com.deu.multisolvermko.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.deu.multisolvermko.R;
import com.deu.multisolvermko.fragments.adapters.DecisionSupport;
import com.deu.multisolvermko.fragments.adapters.DecisionSupportAdapter;
import com.deu.multisolvermko.premium.PremiumActivity;
import com.deu.multisolvermko.topsis_ahp.ahp.AhpFourCriteriaActivity;
import com.deu.multisolvermko.topsis_ahp.ahp.AhpThreeCriteriaActivity;
import java.util.ArrayList;
import java.util.List;

public class AhpFragment extends Fragment {

    View layout;
    AlertDialog alertDialog;
    Button premiumQuit, premiumGo;
    ConstraintLayout layoutDialogContainer;
    private DecisionSupportAdapter.RecyclerViewClickListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_ahp,container,false);

        layout = inflater.inflate(R.layout.premium_popup,container,false);
        premiumQuit=layout.findViewById(R.id.premiumQuit);
        premiumGo=layout.findViewById(R.id.premiumGo);

        ViewPager2 decisionViewPager = viewGroup.findViewById(R.id.decisionViewPager);
        List<DecisionSupport> decisionSupports = new ArrayList<>();

        DecisionSupport decisionSupportThreeAhp = new DecisionSupport();
        decisionSupportThreeAhp.imageUrl = "https://i.hizliresim.com/e5z2m9l.png";
        decisionSupportThreeAhp.title = "Kullanılabilir";
        decisionSupportThreeAhp.name = "MKO";
        decisionSupportThreeAhp.feature = "3 Kriterli";
        decisionSupports.add(decisionSupportThreeAhp);

        DecisionSupport decisionSupportFourAhp = new DecisionSupport();
        decisionSupportFourAhp.imageUrl = "https://i.hizliresim.com/e5z2m9l.png";
        decisionSupportFourAhp.title = "Kullanılabilir";
        decisionSupportFourAhp.name = "MKO";
        decisionSupportFourAhp.feature = "4 Kriterli";
        decisionSupports.add(decisionSupportFourAhp);

        DecisionSupport decisionSupportFiveAhp = new DecisionSupport();
        decisionSupportFiveAhp.imageUrl = "https://i.hizliresim.com/e5z2m9l.png";
        decisionSupportFiveAhp.title = "Premium";
        decisionSupportFiveAhp.name = "by MKO";
        decisionSupportFiveAhp.feature = "5 Kriterli";
        decisionSupports.add(decisionSupportFiveAhp);

        DecisionSupport decisionSupportSixAhp = new DecisionSupport();
        decisionSupportSixAhp.imageUrl = "https://i.hizliresim.com/e5z2m9l.png";
        decisionSupportSixAhp.title = "Premium";
        decisionSupportSixAhp.name = "by MKO";
        decisionSupportSixAhp.feature = "6 Kriterli";
        decisionSupports.add(decisionSupportSixAhp);

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
                Intent intent = new Intent(getActivity(), AhpThreeCriteriaActivity.class);
                startActivity(intent);
            }else if (position == 1){
                Intent intent = new Intent(getActivity(), AhpFourCriteriaActivity.class);
                startActivity(intent);
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