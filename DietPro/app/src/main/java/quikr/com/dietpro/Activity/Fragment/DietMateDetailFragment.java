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

/**
 * Created by Rakshith
 */
public class DietMateDetailFragment extends Fragment {
    ViewPager               viewPager;
    TabLayout               tabLayout;
    ViewPagerAdapter        viewPagerAdapter;
    String                  categoryId;
    Bundle                  bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_view_pager , container , false);

        bundle = getArguments();
        categoryId = bundle.getString("categoryId");

        viewPager   = (ViewPager) view.findViewById(R.id.activityViewPagervpViewPager);
        setViewPager(viewPager);

        tabLayout   = (TabLayout) view.findViewById(R.id.activityViewPagertbTabs);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void setViewPager(ViewPager viewPager) {
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new CarbohydratesFragment() , "Carbohydrates", bundle);
        viewPagerAdapter.addFragment(new ProtienFragment() , "Protein" , bundle);
        viewPagerAdapter.addFragment(new FibersFragment(), "Fibers" , bundle);
        viewPagerAdapter.addFragment(new VitaminsFragment() , "Vitamins" , bundle);
        viewPager.setAdapter(viewPagerAdapter);
    }

}
