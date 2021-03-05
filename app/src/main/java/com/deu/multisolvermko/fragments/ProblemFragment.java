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

import com.deu.multisolvermko.fragments.adapters.TravelLocation;
import com.deu.multisolvermko.fragments.adapters.TravelLocationsAdapter;
import com.deu.multisolvermko.premium.PremiumActivity;
import com.deu.multisolvermko.problemler.ProblemActivity;
import com.deu.multisolvermko.R;
import com.deu.multisolvermko.topsis_ahp.topsis.TopsisActivity;
import com.deu.multisolvermko.topsis_ahp.topsis.TopsisFourActivity;

import java.util.ArrayList;
import java.util.List;

public class ProblemFragment extends Fragment {

    View layout;
    AlertDialog alertDialog;
    Button premiumQuit, premiumGo;
    ConstraintLayout layoutDialogContainer;
    private TravelLocationsAdapter.RecyclerViewClickListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_problem,container,false);

        layout = inflater.inflate(R.layout.premium_popup,container,false);
        premiumQuit=layout.findViewById(R.id.premiumQuit);
        premiumGo=layout.findViewById(R.id.premiumGo);

        ViewPager2 locationsViewPager = viewGroup.findViewById(R.id.locationsViewPager);
        List<TravelLocation> travelLocations = new ArrayList<>();

        TravelLocation travelLocationEiffelTower = new TravelLocation();
        travelLocationEiffelTower.imageUrl = "https://media.istockphoto.com/photos/logistics-import-export-background-and-container-cargo-transport-picture-id827684740?k=6&m=827684740&s=170667a&w=0&h=ryGi2igONgNODIr1k-uliyI82Icopn9bpoXqpkXY9pE=";
        travelLocationEiffelTower.title = "Gezgin Satıcı Problemi";
        travelLocationEiffelTower.location = "by MKO";
        travelLocationEiffelTower.starRating = "Benzetilmiş Tavlama";
        travelLocations.add(travelLocationEiffelTower);

        TravelLocation travelLocationMountainView = new TravelLocation();
        travelLocationMountainView.imageUrl = "https://i.hizliresim.com/q8yRd4.png";
        travelLocationMountainView.title = "Paralel Makine Çizelgeleme";
        travelLocationMountainView.location = "by MKO";
        travelLocationMountainView.starRating = "Genetik Algoritma";
        travelLocations.add(travelLocationMountainView);

        TravelLocation travelLocationPremium = new TravelLocation();
        travelLocationPremium.imageUrl = "https://a-static.besthdwallpaper.com/kucuk-golet-mountain-view-duvar-kagidi-1920x1280-28790_38.jpg";
        travelLocationPremium.title = "Premium";
        travelLocationPremium.location = "by MKO";
        travelLocationPremium.starRating = "Premium Özellik";
        travelLocations.add(travelLocationPremium);

        setOnClickListener();
        locationsViewPager.setAdapter(new TravelLocationsAdapter(travelLocations,listener));

        locationsViewPager.setClipToPadding(false);
        locationsViewPager.setClipChildren(false);
        locationsViewPager.setOffscreenPageLimit(3);
        locationsViewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.95f + r * 0.05f);
            }
        });

        locationsViewPager.setPageTransformer(compositePageTransformer);

        layoutDialogContainer= viewGroup.findViewById(R.id.layoutDialogContainer);

        return viewGroup;
    }

    private void setOnClickListener() {
        listener = new TravelLocationsAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                if (position == 0){
                    Intent intent = new Intent(getActivity(), ProblemActivity.class);
                    startActivity(intent);
                }else if (position == 1){
                    Toast.makeText(getActivity(), "Yazılacak", Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity(),R.style.AlertDialogTheme);
                    final View viewView = LayoutInflater.from(getContext()).inflate(R.layout.premium_popup,layoutDialogContainer);
                    builder.setView(viewView);

                    alertDialog = builder.create();
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    alertDialog.show();

                    viewView.findViewById(R.id.premiumQuit).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();

                        }
                    });

                    viewView.findViewById(R.id.premiumGo).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), PremiumActivity.class);
                            startActivity(intent);
                            alertDialog.dismiss();
                        }
                    });
                }
            }
        };
    }
}