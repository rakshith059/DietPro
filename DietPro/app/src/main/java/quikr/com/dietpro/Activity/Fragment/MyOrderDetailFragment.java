package quikr.com.dietpro.Activity.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import quikr.com.dietpro.Activity.Adapter.RecyclerOrderDetailAdapter;
import quikr.com.dietpro.Activity.Model.RecyclerOrderDetailModel;
import quikr.com.dietpro.Activity.Utils.RecyclerItemDecorator;
import quikr.com.dietpro.R;

/**
 * Created by Rakshith
 */
public class MyOrderDetailFragment extends Fragment{

    RecyclerOrderDetailAdapter          recyclerOrderDetailAdapter;
    List<RecyclerOrderDetailModel>      detailModelList;
    RecyclerView                        rvRecyclerList;
    LinearLayoutManager                 layoutManager;

    TextView                            tvOrderDate;
    TextView                            tvOrderPrice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_order_detail, container, false);

        Bundle bundle               = getArguments();
        String orderDate            = bundle.getString("orderDate");
        String orderTotalPrice      = bundle.getString("orderTotalPrice");
        String orderDetail          = bundle.getString("orderDetail");

        tvOrderDate     = (TextView) view.findViewById(R.id.fragmentMyOrderDetailtvOrderDate);
        tvOrderPrice    = (TextView) view.findViewById(R.id.fragmentMyOrderDetailtvOrderPrice);

        if(!TextUtils.isEmpty(orderDate)) {
            tvOrderDate.setText(orderDate);
        }
        if (!TextUtils.isEmpty(orderTotalPrice)) {
            tvOrderPrice.setText(getResources().getString(R.string.ruppe_symbol) + " " + orderTotalPrice.toString());
        }

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(orderDetail);
            String id = jsonObject.getString("id");
            String name = jsonObject.getString("name");
            String alt = jsonObject.getString("alt");
            String discription = jsonObject.getString("discription");
            String recipiImage = jsonObject.getString("recipi_image");
            String price = jsonObject.getString("price");

            rvRecyclerList = (RecyclerView) view.findViewById(R.id.commonListlvListView);
            rvRecyclerList.addItemDecoration(new RecyclerItemDecorator(3));
            detailModelList = new ArrayList<RecyclerOrderDetailModel>();
            detailModelList.add(new RecyclerOrderDetailModel(name, discription, price));
//            detailModelList.add(new RecyclerOrderDetailModel("Bread", "50 gm", 80.0));
//            detailModelList.add(new RecyclerOrderDetailModel("Yogurt" , "80 gm" , 120.0));
//            detailModelList.add(new RecyclerOrderDetailModel("Crackers" , "50 gm" , 70.0));

            recyclerOrderDetailAdapter = new RecyclerOrderDetailAdapter(getActivity() , detailModelList);
            layoutManager = new LinearLayoutManager(getActivity());
            rvRecyclerList.setLayoutManager(layoutManager);
            rvRecyclerList.setAdapter(recyclerOrderDetailAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }
}
