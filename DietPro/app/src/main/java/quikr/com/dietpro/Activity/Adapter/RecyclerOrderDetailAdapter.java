package quikr.com.dietpro.Activity.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import quikr.com.dietpro.Activity.Model.RecyclerOrderDetailModel;
import quikr.com.dietpro.R;

/**
 * Created by Rakshith
 */
public class RecyclerOrderDetailAdapter extends RecyclerView.Adapter<RecyclerOrderDetailAdapter.RecyclerOrderDetailHolder>{
    Activity                            activity;
    List<RecyclerOrderDetailModel>      detailModelList;

    public RecyclerOrderDetailAdapter(Activity activity, List<RecyclerOrderDetailModel> detailModelList) {
        this.activity           = activity;
        this.detailModelList    = detailModelList;
    }

    @Override
    public RecyclerOrderDetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_detail_row , parent , false);
        return new RecyclerOrderDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerOrderDetailHolder holder, int position) {
        RecyclerOrderDetailModel orderDetailModel = detailModelList.get(position);

        holder.tvItemName.setText(orderDetailModel.getOrderItemName());
        holder.tvItemQuantity.setText(orderDetailModel.getOrderItemQuantity());
        holder.tvItemPrice.setText(activity.getResources().getString(R.string.ruppe_symbol) + " " + orderDetailModel.getOrderItemPrice().toString());
    }

    @Override
    public int getItemCount() {
        return detailModelList.size();
    }

    class RecyclerOrderDetailHolder extends RecyclerView.ViewHolder
    {
        TextView            tvItemName;
        TextView            tvItemQuantity;
        TextView            tvItemPrice;

        public RecyclerOrderDetailHolder(View itemView) {
            super(itemView);
            tvItemName = (TextView) itemView.findViewById(R.id.myOrderDetailRowtvItemName);
            tvItemQuantity = (TextView) itemView.findViewById(R.id.myOrderDetailRowtvItemQuantity);
            tvItemPrice = (TextView) itemView.findViewById(R.id.myOrderDetailRowtvItemPrice);
        }
    }
}
