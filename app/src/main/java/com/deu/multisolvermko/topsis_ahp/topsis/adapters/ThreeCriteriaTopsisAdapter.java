package com.deu.multisolvermko.topsis_ahp.topsis.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.deu.multisolvermko.R;
import com.deu.multisolvermko.topsis_ahp.topsis.models.ThreeCriteriaTopsisModel;
import java.util.List;

public class ThreeCriteriaTopsisAdapter extends RecyclerView.Adapter<ThreeCriteriaTopsisAdapter.ViewHolder> {

    Context context;
    List<ThreeCriteriaTopsisModel> threeCriteriaTopsisModel;

    public ThreeCriteriaTopsisAdapter(Context context, List<ThreeCriteriaTopsisModel> threeCriteriaTopsisModel) {
        this.context = context;
        this.threeCriteriaTopsisModel = threeCriteriaTopsisModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (threeCriteriaTopsisModel != null && threeCriteriaTopsisModel.size() > 0){
            ThreeCriteriaTopsisModel model = threeCriteriaTopsisModel.get(position);
            holder.alternate1.setText(model.getAlternate1());
            holder.alternate2.setText(model.getAlternate2());
            holder.alternate3.setText(model.getAlternate3());

        }
    }

    @Override
    public int getItemCount() {
        return threeCriteriaTopsisModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView alternate1, alternate2, alternate3;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            alternate1 = itemView.findViewById(R.id.tAlternate1Text);
            alternate2 = itemView.findViewById(R.id.tAlternate2Text);
            alternate3 = itemView.findViewById(R.id.tAlternate3Text);

        }
    }
}
