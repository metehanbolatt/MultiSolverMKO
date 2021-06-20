package com.deu.multisolvermko.topsis_ahp.topsis.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.deu.multisolvermko.R;
import com.deu.multisolvermko.topsis_ahp.topsis.models.FourCriteriaTopsisModel;
import java.util.List;

public class FourCriteriaTopsisAdapter extends RecyclerView.Adapter<FourCriteriaTopsisAdapter.ViewHolder> {

    Context context;
    List<FourCriteriaTopsisModel> fourCriteriaTopsisModels;

    public FourCriteriaTopsisAdapter(Context context, List<FourCriteriaTopsisModel> fourCriteriaTopsisModels) {
        this.context = context;
        this.fourCriteriaTopsisModels = fourCriteriaTopsisModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout_four,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (fourCriteriaTopsisModels != null && fourCriteriaTopsisModels.size()>0) {
            FourCriteriaTopsisModel fourModel = fourCriteriaTopsisModels.get(position);
            holder.alternate1.setText(fourModel.getAlternate1());
            holder.alternate2.setText(fourModel.getAlternate2());
            holder.alternate3.setText(fourModel.getAlternate3());
            holder.alternate4.setText(fourModel.getAlternate4());
        }
    }

    @Override
    public int getItemCount() {
        return fourCriteriaTopsisModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView alternate1, alternate2, alternate3, alternate4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            alternate1 = itemView.findViewById(R.id.alternate1Text);
            alternate2 = itemView.findViewById(R.id.alternate2Text);
            alternate3 = itemView.findViewById(R.id.alternate3Text);
            alternate4 = itemView.findViewById(R.id.alternate4Text);

        }
    }
}