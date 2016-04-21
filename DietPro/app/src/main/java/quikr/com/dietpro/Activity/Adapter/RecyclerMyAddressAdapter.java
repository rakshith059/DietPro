package quikr.com.dietpro.Activity.Adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import quikr.com.dietpro.Activity.MainActivity;
import quikr.com.dietpro.Activity.Model.MyAddressModel;
import quikr.com.dietpro.Activity.Utils.Constants;
import quikr.com.dietpro.Activity.Utils.NetworkVolleyRequest;
import quikr.com.dietpro.R;

/**
 * Created by Rakshith
 */
public class RecyclerMyAddressAdapter extends RecyclerView.Adapter<MyAddressHolder> implements View.OnClickListener{
    ArrayList<MyAddressModel>   myAddressList;
    Fragment                    fragment;
    int                         width;
    String                      from;
    String addressTitle;
    String addressPhone;
    String addressDetail;
    String accessToken;

    public RecyclerMyAddressAdapter(Fragment fragment, ArrayList<MyAddressModel> myAddressList, String from)
    {
        this.fragment       = fragment;
        this.myAddressList  = myAddressList;
        this.from           = from;

        DisplayMetrics displayMetrics = fragment.getActivity().getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels;
    }

    @Override
    public MyAddressHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(fragment.getActivity()).inflate(R.layout.my_address_row, null);
        accessToken = Constants.getSharedPrefrence(fragment.getActivity(), Constants.DIET_PRO_ACCESS_TOKEN_KEY);
        return new MyAddressHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAddressHolder holder, int position) {
        MyAddressModel addressModel = myAddressList.get(position);
        addressTitle     = addressModel.getAddressTitle();
        addressPhone     = addressModel.getAddressPhone();
        addressDetail    = addressModel.getAddressDetail();

        holder.llAddressLayout.setMinimumWidth(width);
        if(!TextUtils.isEmpty(addressTitle)) {
            holder.tvName.setText(addressTitle);
        }
        if(!TextUtils.isEmpty(addressPhone)) {
            holder.tvPhoneNo.setText(addressPhone);
        }
        if(!TextUtils.isEmpty(addressDetail)) {
            holder.tvAddress.setText(addressDetail);
        }
        if(!TextUtils.isEmpty(from) && from.equalsIgnoreCase(Constants.DIET_PRO_FROM_CHECKOUT_FRAGMENT))
        {
            holder.llAddressLayout.setOnClickListener(this);
            holder.llAddressLayout.setTag(position);
        }
        holder.ivDeleteAddress.setOnClickListener(this);
        holder.ivDeleteAddress.setVisibility(View.VISIBLE);
        holder.ivDeleteAddress.setTag(position);
    }

    @Override
    public int getItemCount() {
        return myAddressList.size();
    }

    @Override
    public void onClick(View v) {
        final int pos = (int) v.getTag();
        switch (v.getId())
        {
            case R.id.myAddressRowllAddressContainer:
                MyAddressModel modelPos = myAddressList.get(pos);
                Constants.setSharedPrefrence(fragment.getActivity(), Constants.DIET_PRO_ADDRESS_ID, modelPos.getAddressId());
                Constants.setSharedPrefrence(fragment.getActivity() , Constants.DIET_PRO_ADDRESS_TITLE , modelPos.getAddressTitle());
                Constants.setSharedPrefrence(fragment.getActivity() , Constants.DIET_PRO_ADDRESS_PHONE , modelPos.getAddressPhone());
                Constants.setSharedPrefrence(fragment.getActivity() , Constants.DIET_PRO_ADDRESS_DETAIL , modelPos.getAddressDetail());
                MainActivity.popCurrentFragment();
                break;
            case R.id.myAddressRowivDeleteAddress:
                deleteApi(pos);
                break;
            default:
                break;
        }
    }

    private void deleteApi(final int pos) {
        MainActivity.showLoader(fragment.getActivity());
        String addressId = myAddressList.get(pos).getAddressId();
        HashMap<String , String> header = new HashMap<>();
        header.put(Constants.DIET_PRO_AUTHORIZATION, accessToken);

        NetworkVolleyRequest deleteAddress = new NetworkVolleyRequest(NetworkVolleyRequest.RequestMethod.GET, Constants.DIET_PRO_DELETE_ADDRESS + addressId, String.class, header, new HashMap<>(), new NetworkVolleyRequest.Callback() {
            @Override
            public void onSuccess(Object response) {
                try {
                    MainActivity.hideLoader();
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String message = jsonObject.getString("message");
                    if(!TextUtils.isEmpty(message)) {
                        Toast.makeText(fragment.getActivity(), message, Toast.LENGTH_SHORT).show();
                        myAddressList.remove(pos);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int errorCode, String errorMessage) {

            }
        } , NetworkVolleyRequest.ContentType.JSON);
        deleteAddress.execute();
    }
}

class MyAddressHolder extends RecyclerView.ViewHolder
{
    LinearLayout        llAddressLayout;
    TextView            tvName;
    TextView            tvPhoneNo;
    TextView            tvAddress;

    ImageView           ivDeleteAddress;

    public MyAddressHolder(View itemView) {
        super(itemView);
        llAddressLayout = (LinearLayout) itemView.findViewById(R.id.myAddressRowllAddressContainer);
        tvName      = (TextView) itemView.findViewById(R.id.myAddressRowtvName);
        tvPhoneNo   = (TextView) itemView.findViewById(R.id.myAddressRowtvPhone);
        tvAddress   = (TextView) itemView.findViewById(R.id.myAddressRowtvAddress);
        ivDeleteAddress = (ImageView) itemView.findViewById(R.id.myAddressRowivDeleteAddress);
    }
}
