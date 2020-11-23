package com.deu.multisolvermko.topsis_ahp.topsis.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.deu.multisolvermko.R;
import com.deu.multisolvermko.topsis_ahp.topsis.models.PaymentModelFour;
import java.util.List;

public class PaymentAdapterFour extends RecyclerView.Adapter<PaymentAdapterFour.ViewHolder> {

    Context context;
    List<PaymentModelFour> fourpayment_list;

    public PaymentAdapterFour(Context context, List<PaymentModelFour> fourpayment_list) {
        this.context = context;
        this.fourpayment_list = fourpayment_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View fourview = LayoutInflater.from(context).inflate(R.layout.item_layout_four,parent,false);
        return new ViewHolder(fourview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (fourpayment_list != null && fourpayment_list.size()>0){
            PaymentModelFour fourmodel = fourpayment_list.get(position);
            holder.fourid_tv.setText(fourmodel.getId());
            holder.fourname_tv.setText(fourmodel.getName());
            holder.fourpayment_tv.setText(fourmodel.getPayment());
            holder.fourpayment1_tv.setText(fourmodel.getPayment1());


        }else{
            return;
        }


    }

    @Override
    public int getItemCount() {
        return fourpayment_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView fourid_tv,fourname_tv,fourpayment_tv,fourpayment1_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fourid_tv = itemView.findViewById(R.id.fourid_tv);
            fourname_tv = itemView.findViewById(R.id.fourname_tv);
            fourpayment_tv = itemView.findViewById(R.id.fourpayment_tv);
            fourpayment1_tv= itemView.findViewById(R.id.fourpayment1_tv);
        }
    }
}
