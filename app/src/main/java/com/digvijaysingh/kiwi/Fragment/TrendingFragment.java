package com.digvijaysingh.kiwi.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digvijaysingh.kiwi.R;
import com.digvijaysingh.kiwi.Utils.Constant;

public class TrendingFragment  extends Fragment {
    /**
     * Declation of views and variables
     *
     * */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trending_fragment, container, false);
        return view;

    }

    public static TrendingFragment newInstance(String text) {
        TrendingFragment fragment = new TrendingFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.MSG, text);
        fragment.setArguments(bundle);
        return fragment;
    }


}
