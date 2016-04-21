package quikr.com.dietpro.Activity.Adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;
import java.util.Locale;

import quikr.com.dietpro.Activity.Fragment.PersonalTrainerFragment;
import quikr.com.dietpro.Activity.Model.TrainerModel;
import quikr.com.dietpro.Activity.Utils.AppController;
import quikr.com.dietpro.Activity.Utils.Constants;
import quikr.com.dietpro.R;

/**
 * Created by Rakshith
 */
public class RecyclerPersonalTrainerAdapter extends RecyclerView.Adapter<PersonalTrainerHolder> implements View.OnClickListener{

    Fragment                fragment;
    List<TrainerModel>      trainerModels;
    TrainerModel            trainerModel;
    String                  assetUrl;

    public RecyclerPersonalTrainerAdapter(PersonalTrainerFragment fragment, List<TrainerModel> trainerModels) {
        this.fragment       = fragment;
        this.trainerModels  = trainerModels;
        assetUrl            = Constants.getSharedPrefrence(fragment.getActivity(), Constants.DIET_PRO_ASSET_URL);
    }

    @Override
    public PersonalTrainerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_personal_trainer , null);
        return new PersonalTrainerHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonalTrainerHolder holder, int position) {
        trainerModel = trainerModels.get(position);

        AppController.getInstance().getImageLoader().get(assetUrl+trainerModel.getTrainerImage(), ImageLoader.getImageListener(holder.ivTrainerPic, R.drawable.diet_pro_image1, R.drawable.diet_pro_image1));
        holder.tvTrainerName.setText(trainerModel.getTrainerName());
        holder.tvTrainerLocation.setText(trainerModel.getTrainerLocation());
        holder.tvTrainerDetail.setText(trainerModel.getTrainerDetail());
        holder.tvTrainerLikeCount.setText(trainerModel.getTrainerContact());

        holder.ivTrainerLocationIcon.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return trainerModels.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fragmentPersonalTrainerivLocation:
                Float latitude   = Float.valueOf(trainerModel.getTrainerLatitude());
                Float longitude  = Float.valueOf(trainerModel.getTrainerLongitude());

                String uri = String.format(Locale.ENGLISH , "geo:%f,%f" , latitude , longitude);
                Intent intent = new Intent(Intent.ACTION_VIEW , Uri.parse(uri));
                fragment.startActivity(intent);
                break;
            default:
                break;
        }
    }
}

class PersonalTrainerHolder extends RecyclerView.ViewHolder
{
    ImageView           ivTrainerPic;
    TextView            tvTrainerName;
    TextView            tvTrainerLocation;
    TextView            tvTrainerDetail;
    TextView            tvTrainerLikeCount;
    ImageView           ivTrainerLocationIcon;

    public PersonalTrainerHolder(View itemView) {
        super(itemView);
        ivTrainerPic            = (ImageView) itemView.findViewById(R.id.fragmentPersonalTrainerivPic);
        tvTrainerName           = (TextView) itemView.findViewById(R.id.fragmentPersonalTrainertvName);
        tvTrainerLocation       = (TextView) itemView.findViewById(R.id.fragmentPersonalTrainertvLocation);
        tvTrainerDetail         = (TextView) itemView.findViewById(R.id.fragmentPersonalTrainertvDetail);
        tvTrainerLikeCount      = (TextView) itemView.findViewById(R.id.fragmentPersonalTrainertvLikeCount);
        ivTrainerLocationIcon   = (ImageView) itemView.findViewById(R.id.fragmentPersonalTrainerivLocation);
    }
}
