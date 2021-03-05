package com.deu.multisolvermko.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.deu.multisolvermko.R;
import com.deu.multisolvermko.fragments.adapters.TravelLocation;
import com.deu.multisolvermko.fragments.adapters.TravelLocationsAdapter;
import com.deu.multisolvermko.topsis_ahp.ahp.AhpFourProcess;
import com.deu.multisolvermko.topsis_ahp.ahp.AhpThreeProcess;
import com.deu.multisolvermko.topsis_ahp.topsis.TopsisActivity;
import com.deu.multisolvermko.topsis_ahp.topsis.TopsisFourActivity;

import java.util.ArrayList;
import java.util.List;

public class AhpFragment extends Fragment {

    private TravelLocationsAdapter.RecyclerViewClickListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_ahp,container,false);

        ViewPager2 locationsViewPager = viewGroup.findViewById(R.id.locationsViewPager);
        List<TravelLocation> travelLocations = new ArrayList<>();

        TravelLocation travelLocationEiffelTower = new TravelLocation();
        travelLocationEiffelTower.imageUrl = "https://salom.com.tr/uploads/news/b_1572020uui1hdiKYGL7S59vhc7Jy5Jp5.jpg";
        travelLocationEiffelTower.title = "Kullanılabilir";
        travelLocationEiffelTower.location = "by MKO";
        travelLocationEiffelTower.starRating = "3 Kriterli";
        travelLocations.add(travelLocationEiffelTower);

        TravelLocation travelLocationMountainView = new TravelLocation();
        travelLocationMountainView.imageUrl = "https://a-static.besthdwallpaper.com/kucuk-golet-mountain-view-duvar-kagidi-1920x1280-28790_38.jpg";
        travelLocationMountainView.title = "Kullanılabilir";
        travelLocationMountainView.location = "by MKO";
        travelLocationMountainView.starRating = "4 Kriterli";
        travelLocations.add(travelLocationMountainView);

        TravelLocation travelLocationTajMahal = new TravelLocation();
        travelLocationTajMahal.imageUrl = "https://i.pinimg.com/originals/38/e3/42/38e342f5490eb155e5bd8f99608a304a.jpg";
        travelLocationTajMahal.title = "Premium";
        travelLocationTajMahal.location = "by MKO";
        travelLocationTajMahal.starRating = "5 Kriterli";
        travelLocations.add(travelLocationTajMahal);

        TravelLocation travelLocationLakeView= new TravelLocation();
        travelLocationLakeView.imageUrl = "https://www.pandotrip.com/wp-content/uploads/2018/06/Moraine-Lake-in-Banff-National-park-Canada.jpg";
        travelLocationLakeView.title = "Premium";
        travelLocationLakeView.location = "by MKO";
        travelLocationLakeView.starRating = "6 Kriterli";
        travelLocations.add(travelLocationLakeView);

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

        return viewGroup;
    }

    private void setOnClickListener() {
        listener = new TravelLocationsAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                if (position == 0){
                    Intent intent = new Intent(getActivity(),AhpThreeProcess.class);
                    startActivity(intent);
                }else if (position == 1){
                    Intent intent = new Intent(getActivity(),AhpFourProcess.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "Premium", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }
}