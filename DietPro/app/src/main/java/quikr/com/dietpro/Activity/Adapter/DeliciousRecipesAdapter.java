package quikr.com.dietpro.Activity.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

import quikr.com.dietpro.Activity.Fragment.RecipesDetailFragment;
import quikr.com.dietpro.Activity.MainActivity;
import quikr.com.dietpro.Activity.Model.DeliciousRecipesModel;
import quikr.com.dietpro.Activity.Utils.AppController;
import quikr.com.dietpro.Activity.Utils.Constants;
import quikr.com.dietpro.R;

/**
 * Created by Rakshith
 */
public class DeliciousRecipesAdapter extends BaseAdapter implements View.OnClickListener {

    Fragment fragment;
    ArrayList<DeliciousRecipesModel> recipesModelList;
    LayoutInflater inflater;
    Holder         holder;
    String          assetUrl;

    public DeliciousRecipesAdapter(Fragment fragment, ArrayList<DeliciousRecipesModel> recipesModelList)
    {
        this.fragment = fragment;
        this.recipesModelList = recipesModelList;
        assetUrl            = Constants.getSharedPrefrence(fragment.getActivity(), Constants.DIET_PRO_ASSET_URL);
    }

    @Override
    public int getCount() {
        return recipesModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return recipesModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DeliciousRecipesModel recipesModel = recipesModelList.get(position);

        if(convertView == null)
        {
            inflater = (LayoutInflater) fragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.delicious_recipes_row , null);
            holder = new Holder();
            convertView.setTag(holder);
        }
        else
        {
            convertView.getTag();
        }

        holder.llMainLayout = (CardView) convertView.findViewById(R.id.deliciousRecipesRowllMainLayout);
        holder.ivGridImage  = (ImageView) convertView.findViewById(R.id.deliciousRecipesRowivGridImage);
        holder.tvTextView   = (TextView) convertView.findViewById(R.id.deliciousRecipesRowivGridTitle);

        AppController.getInstance().getImageLoader().get(assetUrl + recipesModel.getGridImage(), ImageLoader.getImageListener(holder.ivGridImage, R.drawable.diet_pro_image1, R.drawable.diet_pro_image1));
//        holder.ivGridImage.setImageResource(recipesModel.getGridImage());
        holder.tvTextView.setText(recipesModel.getdRecipesName());

        holder.llMainLayout.setOnClickListener(this);
        holder.llMainLayout.setTag(recipesModel);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        DeliciousRecipesModel model = (DeliciousRecipesModel) v.getTag();
        switch (v.getId())
        {
            case R.id.deliciousRecipesRowllMainLayout:
                Bundle bundle = new Bundle();
                bundle.putString("dRecipesId" , model.getdRecipesId());
                MainActivity.replaceFragment(new RecipesDetailFragment(), "recipesDetailFragment", bundle);
                break;
            default:
                break;
        }
    }

    class Holder
    {
        CardView    llMainLayout;
        ImageView   ivGridImage;
        TextView    tvTextView;
    }
}
