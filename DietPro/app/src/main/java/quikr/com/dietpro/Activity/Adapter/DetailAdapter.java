package quikr.com.dietpro.Activity.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

import quikr.com.dietpro.Activity.Model.DetailModel;
import quikr.com.dietpro.Activity.Utils.AppController;
import quikr.com.dietpro.Activity.Utils.Constants;
import quikr.com.dietpro.R;

/**
 * Created by Rakshith
 */
public class DetailAdapter extends BaseAdapter{
    ArrayList<DetailModel>  detailModelList;
    Fragment                mContext;
    LayoutInflater          inflater;
    Holder                  holder;
    String                  assetUrl;

    public DetailAdapter(Fragment mFragment, ArrayList<DetailModel> detailModelList) {
        this.mContext = mFragment;
        this.detailModelList = detailModelList;
        assetUrl            = Constants.getSharedPrefrence(mContext.getActivity(), Constants.DIET_PRO_ASSET_URL);
    }

    @Override
    public int getCount() {
        return detailModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return detailModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final DetailModel   detailModel = detailModelList.get(position);
        if(convertView == null)
        {
            inflater = (LayoutInflater) mContext.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.detail_row , null);
            holder = new Holder();
            convertView.setTag(holder);
        }
        else
        {
            convertView.getTag();
        }

        holder.ivRowImage = (ImageView) convertView.findViewById(R.id.detailRowivRowImage);
        holder.tvRowTitle = (TextView) convertView.findViewById(R.id.detailRowtvRowTitle);
        holder.tvRowQuantityPerGram = (TextView) convertView.findViewById(R.id.detailRowtvRowQuantityPerGram);
        holder.tvRowPricePerGram = (TextView) convertView.findViewById(R.id.detailRowtvRowPrice);
        holder.spRowTotalQuantity = (Spinner) convertView.findViewById(R.id.detailRowspSpinner);
        holder.ivRowCartIcon    = (ImageView) convertView.findViewById(R.id.detailRowivRowCartImage);

        AppController.getInstance().getImageLoader().get(assetUrl + detailModel.getVitaminItemImage(), ImageLoader.getImageListener(holder.ivRowImage, R.drawable.diet_pro_image1, R.drawable.diet_pro_image1));
        holder.tvRowTitle.setText(detailModel.getVitaminName());
        holder.tvRowQuantityPerGram.setText(detailModel.getVitaminQtyRs()  + " " + mContext.getActivity().getResources().getString(R.string.carb_per_gram));
        holder.tvRowPricePerGram.setText(mContext.getActivity().getResources().getString(R.string.ruppe_symbol) + " " + detailModel.getVitaminPrice());

        ArrayList<String> list = new ArrayList<String>();
        list.add("100");
        list.add("200");
        list.add("300");
        list.add("400");
        list.add("500");
        list.add("600");
        list.add("700");
        list.add("800");
        list.add("900");
        list.add("1000");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext.getActivity() , android.R.layout.simple_spinner_dropdown_item , list);
        holder.spRowTotalQuantity.setAdapter(arrayAdapter);
        holder.spRowTotalQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {
                    int selectedItemPos = position + 1;
                    final int totalPrice = selectedItemPos * Integer.valueOf(detailModel.getVitaminPrice());

                    Log.d("Rakshith", selectedItemPos + " totalPrice " + totalPrice);
                    mContext.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            holder.tvRowPricePerGram.setText(mContext.getActivity().getResources().getString(R.string.ruppe_symbol) + " " + totalPrice);
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return convertView;
    }

    class Holder
    {
        ImageView       ivRowImage;
        TextView        tvRowTitle;
        TextView        tvRowQuantityPerGram;
        TextView        tvRowPricePerGram;
        ImageView       ivRowCartIcon;
        Spinner         spRowTotalQuantity;
    }
}
