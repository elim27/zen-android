package com.MADAPPS.zen.ui.stats;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


import com.MADAPPS.zen.R;
import com.google.android.material.tabs.TabLayout;

public class StatsFragment extends Fragment {

    private StatsViewModel statsViewModel;


    //tab layout
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_stats, container, false);
        toolbar = view.findViewById(R.id.toolbarStats);
        ((AppCompatActivity) this.getActivity()).setSupportActionBar(toolbar);

        //sets up our view page adapter
        viewPager = view.findViewById(R.id.viewpagerStats);
        setViewPager(viewPager);

        //sets up tab layout
        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_clipboard);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_trend);

        return view;
    }

    private void setViewPager(ViewPager viewPager){
        OverviewFragment overviewFragment = new OverviewFragment();
        AnalysisFragment analysFragment = new AnalysisFragment();

        ViewPageAdapter adapter = new ViewPageAdapter(getChildFragmentManager(), 0);
        adapter.addFragment(overviewFragment, "Overview");
        adapter.addFragment(analysFragment, "Analysis");
        viewPager.setAdapter(adapter);

    }


}
