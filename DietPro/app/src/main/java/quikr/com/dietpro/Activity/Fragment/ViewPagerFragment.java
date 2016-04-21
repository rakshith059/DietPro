package quikr.com.dietpro.Activity.Fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import quikr.com.dietpro.Activity.Adapter.ViewPagerAdapter;
import quikr.com.dietpro.R;

public class ViewPagerFragment extends Fragment {

    ViewPager       viewPager;
    TabLayout       tabLayout;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_view_pager , container , false);
        viewPager = (ViewPager) view.findViewById(R.id.activityViewPagervpViewPager);
        setUpViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.activityViewPagertbTabs);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    private void setUpViewPager(ViewPager viewPager) {
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new DietMateFragment() , "Diet Mate", new Bundle());
        viewPagerAdapter.addFragment(new KnowMateFragment() , "Know Mate", new Bundle());
//        viewPagerAdapter.addFragment(new NearByFragment() , "Near By", new Bundle());
        viewPagerAdapter.addFragment(new PersonalTrainerFragment() , "Personal Trainer", new Bundle());
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        viewPagerAdapter = null;
    }
}
