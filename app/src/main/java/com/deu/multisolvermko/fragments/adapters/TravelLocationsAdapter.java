package com.deu.multisolvermko.fragments.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deu.multisolvermko.R;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TravelLocationsAdapter extends RecyclerView.Adapter<TravelLocationsAdapter.TravelLocationViewHolder>{

    private List<TravelLocation> travelLocations;
    private RecyclerViewClickListener listener;

    public TravelLocationsAdapter(List<TravelLocation> travelLocations, RecyclerViewClickListener listener) {
        this.travelLocations = travelLocations;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TravelLocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TravelLocationViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_location,parent,false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull TravelLocationViewHolder holder, int position) {
        holder.setLocationData(travelLocations.get(position));

    }

    @Override
    public int getItemCount() {
        return travelLocations.size();
    }

    public class TravelLocationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private KenBurnsView kbvLocation;
        private TextView textTitle, textLocation, textStarRating;

        TravelLocationViewHolder(@NonNull View itemView) {
            super(itemView);
            kbvLocation = itemView.findViewById(R.id.kbvLocation);
            textTitle = itemView.findViewById(R.id.textTitle);
            textLocation = itemView.findViewById(R.id.textLocation);
            textStarRating = itemView.findViewById(R.id.textStarRating);
            itemView.setOnClickListener(this);
        }

        void setLocationData(TravelLocation travelLocation){
            Picasso.get().load(travelLocation.imageUrl).into(kbvLocation);
            textTitle.setText(travelLocation.title);
            textLocation.setText(travelLocation.location);
            textStarRating.setText(String.valueOf(travelLocation.starRating));

        }

        @Override
        public void onClick(View v) {
            listener.onClick(itemView,getAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }
}
