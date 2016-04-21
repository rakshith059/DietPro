package quikr.com.dietpro.Activity.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

import quikr.com.dietpro.Activity.Fragment.DeliciousRecipesFragment;
import quikr.com.dietpro.Activity.Fragment.DietMateDetailFragment;
import quikr.com.dietpro.Activity.MainActivity;
import quikr.com.dietpro.Activity.Model.DietModel;
import quikr.com.dietpro.Activity.Utils.AppController;
import quikr.com.dietpro.Activity.Utils.Constants;
import quikr.com.dietpro.R;

/**
 * Created by Rakshith
 */
public class RecyclerDietMateAdapter extends RecyclerView.Adapter<RecyclerDietMateAdapter.DietMateHolder> implements View.OnClickListener {
    Fragment                fragment;
    ArrayList<DietModel>    dietModels;
    String                  assetUrl;

    public RecyclerDietMateAdapter(Fragment fragment, ArrayList<DietModel> dietModels) {
        this.fragment       = fragment;
        this.dietModels     = dietModels;
        assetUrl            = Constants.getSharedPrefrence(fragment.getActivity() , Constants.DIET_PRO_ASSET_URL);
    }

    @Override
    public RecyclerDietMateAdapter.DietMateHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diet_row , parent , false);
        return new DietMateHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerDietMateAdapter.DietMateHolder holder, int position) {
        DietModel dietModel = dietModels.get(position);
        holder.rlContainer.setOnClickListener(this);
        holder.rlContainer.setTag(position);
        AppController.getInstance().getImageLoader().get(assetUrl+dietModel.getBackgroundImage(), ImageLoader.getImageListener(holder.ivBackground, R.drawable.diet_pro_image1, R.drawable.diet_pro_image1));
//        holder.ivBackground.setImageResource(dietModel.getBackgroundImage());
        holder.tvTitle.setText(dietModel.getTitle());
    }

    @Override
    public int getItemCount() {
        return dietModels.size();
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        switch (v.getId())
        {
            case R.id.dietRowrlContainer:
                Bundle bundle = new Bundle();
                bundle.putString("categoryId" , dietModels.get(position).getCategoryId());

                if(position == 0)
                {
                    MainActivity.replaceFragment(new DeliciousRecipesFragment() , "deliciousRecipesFragment" , new Bundle());
                }
                else {
                    MainActivity.replaceFragment(new DietMateDetailFragment(), "dietMateDetailFragment", bundle);
                }
                break;
            default:
                break;
        }
    }


    class DietMateHolder extends RecyclerView.ViewHolder {
        RelativeLayout rlContainer;
        ImageView ivBackground;
        TextView tvTitle;

        public DietMateHolder(View convertView) {
            super(convertView);
            rlContainer  = (RelativeLayout) convertView.findViewById(R.id.dietRowrlContainer);
            ivBackground = (ImageView) convertView.findViewById(R.id.dietRowivBackgroundImage);
            tvTitle      = (TextView) convertView.findViewById(R.id.dietRowtvTitle);
        }
    }
}