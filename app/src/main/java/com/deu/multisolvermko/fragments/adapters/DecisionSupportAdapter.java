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

public class DecisionSupportAdapter extends RecyclerView.Adapter<DecisionSupportAdapter.DecisionSupportViewHolder>{

    private final List<DecisionSupport> decisionSupports;
    private final RecyclerViewClickListener listener;

    public DecisionSupportAdapter(List<DecisionSupport> decisionSupports, RecyclerViewClickListener listener) {
        this.decisionSupports = decisionSupports;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DecisionSupportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DecisionSupportViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_name,parent,false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull DecisionSupportViewHolder holder, int position) {
        holder.setLocationData(decisionSupports.get(position));

    }

    @Override
    public int getItemCount() {
        return decisionSupports.size();
    }

    public class DecisionSupportViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final KenBurnsView kbvName;
        private final TextView textTitle;
        private final TextView textName;
        private final TextView textFeature;

        DecisionSupportViewHolder(@NonNull View itemView) {
            super(itemView);
            kbvName = itemView.findViewById(R.id.kbvName);
            textTitle = itemView.findViewById(R.id.textTitle);
            textName = itemView.findViewById(R.id.textName);
            textFeature = itemView.findViewById(R.id.textFeature);
            itemView.setOnClickListener(this);
        }

        void setLocationData(DecisionSupport decisionSupport){
            Picasso.get().load(decisionSupport.imageUrl).into(kbvName);
            textTitle.setText(decisionSupport.title);
            textName.setText(decisionSupport.name);
            textFeature.setText(String.valueOf(decisionSupport.feature));

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
