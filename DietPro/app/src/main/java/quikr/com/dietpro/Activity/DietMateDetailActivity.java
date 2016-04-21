package quikr.com.dietpro.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import quikr.com.dietpro.Activity.Fragment.DietMateFragment;
import quikr.com.dietpro.R;

public class DietMateDetailActivity extends AppCompatActivity {

    Context     mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        mContext = DietMateDetailActivity.this;
        MainActivity.addFragment(new DietMateFragment(), null);
    }
}
