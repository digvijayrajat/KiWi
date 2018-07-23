package com.digvijaysingh.kiwi.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.digvijaysingh.kiwi.Fragment.BookmarkFragment;
import com.digvijaysingh.kiwi.Fragment.HomeFragment;
import com.digvijaysingh.kiwi.Fragment.TrendingFragment;
import com.digvijaysingh.kiwi.Utils.Constant;

public class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int pos) {
            switch (pos) {
                case 0:
                    return HomeFragment.newInstance(Constant.FIRST_FRAGMENT);
                case 1:
                    return BookmarkFragment.newInstance(Constant.SECOND_FRAGMENT);
                case 2:
                    return TrendingFragment.newInstance(Constant.THIRD_FRAGMENT);
                default:
                    return TrendingFragment.newInstance(Constant.THIRD_FRAGMENT);
            }
        }
        @Override
        public int getCount() {
            return Constant.FRAGMENT_COUNT;
        }
    }