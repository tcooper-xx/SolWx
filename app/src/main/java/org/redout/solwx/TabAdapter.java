package org.redout.solwx;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList =  new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return this.mFragmentList.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        this.mFragmentList.add(fragment);
        this.mFragmentTitleList.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return this.mFragmentTitleList.get(position);
    }

    @Override
    public int getCount() {
        return this.mFragmentList.size();
    }
}
