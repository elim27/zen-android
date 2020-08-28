package com.MADAPPS.zen.ui.stats;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class ViewPageAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();

    public ViewPageAdapter(@NonNull FragmentManager fm, int behavior){
        super(fm, behavior);
    }




public void addFragment(Fragment fragment, String name){
        fragments.add(fragment);
        names.add(name);
}

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return names.get(position);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
