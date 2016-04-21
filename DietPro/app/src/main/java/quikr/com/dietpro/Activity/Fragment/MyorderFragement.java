package quikr.com.dietpro.Activity.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import quikr.com.dietpro.Activity.Adapter.RecyclerMyOrderAdapter;
import quikr.com.dietpro.Activity.MainActivity;
import quikr.com.dietpro.Activity.Model.MyOrderModel;
import quikr.com.dietpro.Activity.Utils.Constants;
import quikr.com.dietpro.Activity.Utils.DataBaseHelper;
import quikr.com.dietpro.Activity.Utils.NetworkVolleyRequest;
import quikr.com.dietpro.Activity.Utils.RecyclerItemDecorator;
import quikr.com.dietpro.R;

/**
 * Created by Rakshith
 */
public class MyorderFragement extends Fragment {
    DataBaseHelper dataBaseHelper;
    RecyclerView    recyclerView;
    RecyclerMyOrderAdapter  recyclerMyOrderAdapter;

    String              accessToken;

    MyOrderModel        myOrderModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.common_list_view , container , false);
        recyclerView = (RecyclerView) view.findViewById(R.id.commonListlvListView);

        accessToken = Constants.getSharedPrefrence(getActivity() , Constants.DIET_PRO_ACCESS_TOKEN_KEY);

        dataBaseHelper = new DataBaseHelper(getActivity());
        dataBaseHelper.deleteDataBase();

        MyOrderApi();

        String detail = "{\n" +
                "    \"item\": [\n" +
                "        {\n" +
                "            \"name\": \"fruit\",\n" +
                "            \"price\": \"100\",\n" +
                "            \"qty\": \"4\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"dry fruit\",\n" +
                "            \"price\": \"300\",\n" +
                "            \"qty\": \"4\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

//            MyOrderModel myOrderModel1 = new MyOrderModel(1 , "22/11/2015" , 5000.50 , detail);
//            MyOrderModel myOrderModel2 = new MyOrderModel(2 , "22/11/2015" , 500.50 , detail);
//            MyOrderModel myOrderModel3 = new MyOrderModel(3 , "22/11/2015" , 3500 , detail);

//            if(dataBaseHelper.isDataPresent("MyOrders" , "myOrderId" , String.valueOf(myOrderModel1.getOrderId())))
//            {
//                long insert1 = dataBaseHelper.insertMyOrder(myOrderModel1);
//                long insert2 = dataBaseHelper.insertMyOrder(myOrderModel2);
//                long insert3 = dataBaseHelper.insertMyOrder(myOrderModel3);
//            }

        return view;
    }

    private void MyOrderApi() {
        HashMap<String , String> header = new HashMap<>();
        header.put(Constants.DIET_PRO_AUTHORIZATION, accessToken);

        String userId = Constants.getSharedPrefrence(getActivity() , Constants.DIET_PRO_USER_ID);
        NetworkVolleyRequest myOrderReq = new NetworkVolleyRequest(NetworkVolleyRequest.RequestMethod.GET, Constants.DIET_PRO_MY_ORDERS + userId, String.class, header, new HashMap<>(), new NetworkVolleyRequest.Callback() {
            @Override
            public void onSuccess(Object response) {
                try
                {
                    ArrayList<JSONObject> cartModels = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for(int i = 0 ; i < jsonArray.length() ; i++)
                    {
                        JSONObject jsonData     = jsonArray.getJSONObject(i);
                        String OrderId          = jsonData.getString("ord_id");
                        String OrderUid         = jsonData.getString("ord_uid");
                        String UserId           = jsonData.getString("user_id");
                        String addressId        = jsonData.getString("addr_id");
                        String CartId           = jsonData.getString("cart_id");
                        String OrderPymtOpt     = jsonData.getString("ord_pymt_opt");
                        String OrderCartAmt     = jsonData.getString("ord_cart_amt");
                        String OrderDeliveryAmt = jsonData.getString("ord_dlvy_amt");
                        String OrderNetAmt      = jsonData.getString("ord_net_amt");
                        String OrderFlavour     = jsonData.getString("ord_flavour");
                        String OrderNotes       = jsonData.getString("ord_notes");
                        String OrderStatus      = jsonData.getString("ord_status");
                        String OrderCrtdt       = jsonData.getString("ord_crtdt");
                        String OrderUpdatedt    = jsonData.getString("ord_upddt");

                        JSONArray cartArray     = jsonData.getJSONArray("cart");
                        for(int j = 0 ; j < cartArray.length() ; j++)
                        {
                            JSONObject jsonCart     = cartArray.getJSONObject(j);
                            String cartId           = jsonCart.getString("cart_id");
                            String cartUserId       = jsonCart.getString("user_id");
                            String cartProdId       = jsonCart.getString("prod_id");
                            String cartProdType     = jsonCart.getString("prod_type");
                            String cartProdQty      = jsonCart.getString("prod_qty");
                            String cartProdPrice    = jsonCart.getString("prod_price");
                            String cartProdDetail   = jsonCart.getString("prod_details");
                            String cartStatus       = jsonCart.getString("cart_status");
                            String cartCrtdt        = jsonCart.getString("cart_crtdt");

                            cartModels.add(jsonCart);
                            myOrderModel = new MyOrderModel(i, OrderCrtdt, OrderNetAmt, cartProdDetail);
                        }
                        if(cartArray.length() == 0) {
                            myOrderModel = new MyOrderModel(i, OrderCrtdt, OrderNetAmt, "dummy name");
                        }

                        long insert = dataBaseHelper.insertMyOrder(myOrderModel);
                    }

                    if(dataBaseHelper.myOrderCount() != 0) {
                        dataBaseHelper.getAllMyOrders();
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "You have not placed any orders yet!", Toast.LENGTH_SHORT).show();
                        MainActivity.popCurrentFragment();
                    }

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.addItemDecoration(new RecyclerItemDecorator(5));
                    recyclerMyOrderAdapter = new RecyclerMyOrderAdapter(MyorderFragement.this , dataBaseHelper.getAllMyOrders());
                    recyclerView.setAdapter(recyclerMyOrderAdapter);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int errorCode, String errorMessage) {

            }
        } , NetworkVolleyRequest.ContentType.JSON);
        myOrderReq.execute();
    }
}
