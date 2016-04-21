package quikr.com.dietpro.Activity.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

import quikr.com.dietpro.Activity.Fragment.DietMateDetailFragment;
import quikr.com.dietpro.Activity.Fragment.DietMateFragment;
import quikr.com.dietpro.Activity.MainActivity;
import quikr.com.dietpro.Activity.Model.DietModel;
import quikr.com.dietpro.Activity.Utils.AppController;
import quikr.com.dietpro.R;

/**
 * Created by Rakshith
 */
public class DietMateAdapter extends BaseAdapter implements View.OnClickListener{
    Fragment                    mFragment;
    ArrayList<DietModel>        dietModelList;
    LayoutInflater              inflater;
    Holder                      holder;

    public DietMateAdapter(DietMateFragment mFragment, ArrayList<DietModel> dietModels)
    {
        this.mFragment      = mFragment;
        this.dietModelList  = dietModels;
    }

    @Override
    public int getCount() {
        return dietModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return dietModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DietModel dietModel = dietModelList.get(position);

        if(convertView == null)
        {
            inflater = (LayoutInflater)mFragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.diet_row , null);
            holder      = new Holder();
            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder) convertView.getTag();
        }
        holder.rlContainer  = (RelativeLayout) convertView.findViewById(R.id.dietRowrlContainer);
        holder.ivBackground = (ImageView) convertView.findViewById(R.id.dietRowivBackgroundImage);
        holder.tvTitle      = (TextView) convertView.findViewById(R.id.dietRowtvTitle);

        holder.rlContainer.setOnClickListener(this);
        AppController.getInstance().getImageLoader().get(dietModel.getBackgroundImage(), ImageLoader.getImageListener(holder.ivBackground, R.drawable.diet_pro_image1, R.drawable.diet_pro_image1));
//        holder.ivBackground.setImageResource(dietModel.getBackgroundImage());
        holder.tvTitle.setText(dietModel.getTitle());

        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.dietRowrlContainer:
//                Intent intent = new Intent(mFragment.getActivity() , DietMateDetailActivity.class);
//                mFragment.getActivity().startActivity(intent);
                MainActivity.replaceFragment(new DietMateDetailFragment(), "dietMateDetailFragment", new Bundle());
                break;
            default:
                break;
        }
    }

    class Holder
    {
        RelativeLayout  rlContainer;
        ImageView       ivBackground;
        TextView        tvTitle;
    }
}
