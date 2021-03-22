package com.deu.multisolvermko.information.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.deu.multisolvermko.R;
import com.deu.multisolvermko.information.items.InformationItem;
import java.util.List;

public class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.InformationViewHolder>{

    private final List<InformationItem> informationItems;

    public InformationAdapter(List<InformationItem> informationItems) {
        this.informationItems = informationItems;
    }

    @NonNull
    @Override
    public InformationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InformationViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_information, parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull InformationViewHolder holder, int position) {
        holder.setOnboardingData(informationItems.get(position));

    }

    @Override
    public int getItemCount() {
        return informationItems.size();
    }

    static class InformationViewHolder extends RecyclerView.ViewHolder{

        private final TextView textTitle;
        private final TextView textDescription;
        private final ImageView imageInformation;

        public InformationViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDescription = itemView.findViewById(R.id.textDescription);
            imageInformation = itemView.findViewById(R.id.imageOnboarding);

        }

        void setOnboardingData(InformationItem informationItem){
            textTitle.setText(informationItem.getTitle());
            textDescription.setText(informationItem.getDescription());
            imageInformation.setImageResource(informationItem.getImage());

        }
    }
}

