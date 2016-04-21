package quikr.com.dietpro.Activity.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import quikr.com.dietpro.Activity.Model.KnowMateModel;
import quikr.com.dietpro.R;

/**
 * Created by Rakshith
 */
public class KnowMateDetailFragment extends Fragment {

    WebView tvKnowMateDetail;
    KnowMateModel knowMateDetail;
    TextView        tvTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.know_mate_detail, container, false);

        Bundle bundle = getArguments();
        knowMateDetail = (KnowMateModel) bundle.getSerializable("model");

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String data = knowMateDetail.getKnowMateDetail();

        tvTitle     = (TextView) getView().findViewById(R.id.knowMateDetailtvTitle);
        tvTitle.setText(knowMateDetail.getKnowMateName());

        tvKnowMateDetail = (WebView) getView().findViewById(R.id.knowMateDetailtvDetail);
        tvKnowMateDetail.getSettings().setJavaScriptEnabled(true);
        tvKnowMateDetail.loadDataWithBaseURL("", data, "text/html", "UTF-8", "");
    }
}
