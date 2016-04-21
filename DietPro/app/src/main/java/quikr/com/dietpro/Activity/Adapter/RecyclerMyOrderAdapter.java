package quikr.com.dietpro.Activity.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import quikr.com.dietpro.Activity.Fragment.MyOrderDetailFragment;
import quikr.com.dietpro.Activity.MainActivity;
import quikr.com.dietpro.Activity.Model.DietModel;
import quikr.com.dietpro.Activity.Model.MyOrderModel;
import quikr.com.dietpro.R;

/**
 * Created by Rakshith
 */
public class RecyclerMyOrderAdapter extends RecyclerView.Adapter<RecyclerMyOrderAdapter.MyOrderHolder> implements View.OnClickListener {
    Fragment                fragment;
    List<MyOrderModel>      myOrdersList;
    ArrayList<DietModel> dietModels = new ArrayList<DietModel>();

    public RecyclerMyOrderAdapter(Fragment fragment, List<MyOrderModel> myOrders) {
        this.fragment   = fragment;
        this.myOrdersList   = myOrders;
    }

    @Override
    public MyOrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_row , parent , false);
        return new MyOrderHolder(view);
    }

    @Override
    public void onBindViewHolder(MyOrderHolder holder, int position) {
        MyOrderModel    myOrderModel = myOrdersList.get(position);

        String orderDate = myOrderModel.getOrderDate();
        String orderPrice = myOrderModel.getOrderPrice();
        String orderQty = myOrderModel.getOrderQty();

        if(!TextUtils.isEmpty(orderDate)) {
            holder.tvDate.setText(orderDate);
        }
        if(!TextUtils.isEmpty(orderPrice)) {
            holder.tvPrice.setText(fragment.getActivity().getResources().getString(R.string.ruppe_symbol) + " " +orderPrice);
        }
//        if(!TextUtils.isEmpty(orderQty)) {
//            holder.tvDetail.setText(orderQty);
//        }

//        holder.tvDetail.setText(myOrderModel.getOrderDetail());

        try {
            JSONObject jsonObject = new JSONObject(myOrderModel.getOrderDetail());
            String name = jsonObject.getString("name");

            holder.tvDetail.setText(name);

//            JSONObject jsonObject = new JSONObject(myOrderModel.getOrderDetail());
//            JSONArray jsonArray = jsonObject.getJSONArray("item");
//            for(int i=0 ; i < jsonArray.length() ; i++) {
//                JSONObject object = jsonArray.optJSONObject(i);
//                String name = object.getString("name");
//                String price = object.getString("price");
//                String detail = object.getString("qty");
//                Log.d("Rakshith", "name " + name + " price " + price + " detail " + detail);
//                holder.tvDetail.setText(myOrderModel.getOrderDetail());
//            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("Rakshith", "exception " + e);
        }

//        dietModels.add(new DietModel(R.drawable.side_nav_bar, "Carbohydrates", categoryContent, categoryTitle));
//        dietModels.add(new DietModel(R.drawable.side_nav_bar, "Protein", categoryContent, categoryTitle));
//        dietModels.add(new DietModel(R.drawable.side_nav_bar, "Fibers", categoryContent, categoryTitle));
//        dietModels.add(new DietModel(R.drawable.side_nav_bar, "Vitamins", categoryContent, categoryTitle));

        holder.btnDetail.setTag(position);
        holder.btnDetail.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return myOrdersList.size();
    }

    @Override
    public void onClick(View v) {
        int pos = (int) v.getTag();
        switch (v.getId())
        {
            case R.id.myOrderRowbtnOrderDetail:
                Log.d("Rakshith", "clicked on Order detail");
                Bundle  bundle = new Bundle();
                bundle.putString("orderDate", myOrdersList.get(pos).getOrderDate());
                bundle.putString("orderTotalPrice", myOrdersList.get(pos).getOrderPrice());
                bundle.putString("orderDetail" , myOrdersList.get(pos).getOrderDetail());
//                DialogFragment dialogFragment = new MyOrderDetailFragment();
//                dialogFragment.show(fragment.getFragmentManager() , "orderDetailFragment");
                MainActivity.replaceFragment(new MyOrderDetailFragment(), "myOrderDetailScreen", bundle);
                break;
            default:
                break;
        }
    }

    class MyOrderHolder extends RecyclerView.ViewHolder {

        TextView    tvDate;
        TextView    tvPrice;
        TextView    tvDetail;
        Button      btnDetail;

        public MyOrderHolder(View itemView) {
            super(itemView);
            tvDate    = (TextView) itemView.findViewById(R.id.myOrderRowtvOrderDate);
            tvPrice   = (TextView) itemView.findViewById(R.id.myOrderRowtvOrderPrice);
            tvDetail    = (TextView) itemView.findViewById(R.id.myOrderRowtvOrderDetail);
            btnDetail  = (Button) itemView.findViewById(R.id.myOrderRowbtnOrderDetail);
        }
    }

}
