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

import java.util.ArrayList;
import java.util.List;

import quikr.com.dietpro.Activity.Fragment.KnowMateDetailFragment;
import quikr.com.dietpro.Activity.Fragment.KnowMateFragment;
import quikr.com.dietpro.Activity.MainActivity;
import quikr.com.dietpro.Activity.Model.KnowMateModel;
import quikr.com.dietpro.R;

/**
 * Created by Rakshith
 */
public class RecyclerKnowMateAdapter extends RecyclerView.Adapter<KnowMateHolder> implements View.OnClickListener{

    Fragment                    fragment;
    List<KnowMateModel>         knowMateModels;
    KnowMateModel               mateModel;

    public RecyclerKnowMateAdapter(KnowMateFragment knowMateFragment, ArrayList<KnowMateModel> knowMateModels) {
        this.fragment           = knowMateFragment;
        this.knowMateModels     = knowMateModels;
    }

    @Override
    public KnowMateHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diet_row , null);
        return new KnowMateHolder(view);
    }

    @Override
    public void onBindViewHolder(KnowMateHolder holder, int position) {
        mateModel = knowMateModels.get(position);
        holder.tvTitle.setText(mateModel.getKnowMateName());
        holder.rlContainer.setOnClickListener(this);
        holder.rlContainer.setTag(position);
    }

    @Override
    public int getItemCount() {
        return knowMateModels.size();
    }

    @Override
    public void onClick(View v) {
        int pos = (int) v.getTag();
        switch (v.getId())
        {
            case R.id.dietRowrlContainer:
                Bundle bundle = new Bundle();
                bundle.putSerializable("model" , knowMateModels.get(pos));
                MainActivity.replaceFragment(new KnowMateDetailFragment() , "knowMateDetailScreen" , bundle);
                break;
            default:
                break;
        }
    }
}

class KnowMateHolder extends RecyclerView.ViewHolder
{
    RelativeLayout rlContainer;
    ImageView ivBackground;
    TextView tvTitle;

    public KnowMateHolder(View itemView) {
        super(itemView);
        rlContainer  = (RelativeLayout) itemView.findViewById(R.id.dietRowrlContainer);
        ivBackground = (ImageView) itemView.findViewById(R.id.dietRowivBackgroundImage);
        tvTitle      = (TextView) itemView.findViewById(R.id.dietRowtvTitle);
    }
}
