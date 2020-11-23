package com.deu.multisolvermko.topsis_ahp.topsis.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.deu.multisolvermko.R;
import com.deu.multisolvermko.topsis_ahp.topsis.models.PaymentModel;
import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {

    Context context;
    List<PaymentModel> payment_list;

    public PaymentAdapter(Context context, List<PaymentModel> payment_list) {
        this.context = context;
        this.payment_list = payment_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (payment_list != null && payment_list.size() > 0){
            PaymentModel model = payment_list.get(position);
            holder.id_tv.setText(model.getId());
            holder.name_tv.setText(model.getName());
            holder.payment_tv.setText(model.getPayment());

        }else{
            return;
        }

    }

    @Override
    public int getItemCount() {
        return payment_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView id_tv,name_tv,payment_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id_tv = itemView.findViewById(R.id.id_tv);
            name_tv = itemView.findViewById(R.id.name_tv);
            payment_tv = itemView.findViewById(R.id.payment_tv);



        }
    }
}
