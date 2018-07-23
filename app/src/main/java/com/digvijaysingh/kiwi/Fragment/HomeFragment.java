package com.digvijaysingh.kiwi.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.digvijaysingh.kiwi.Adapters.NewsAdapter;
import com.digvijaysingh.kiwi.R;
import com.digvijaysingh.kiwi.Utils.Constant;
import com.digvijaysingh.kiwi.pojo.PojoNews;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
   /**
     * Declation of views and variables
     * 
    * */   

    private NewsAdapter newsAdapter;
    private List<PojoNews> data=new ArrayList<>();
    private RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        initialize(view);
        return view;

    }
    /**
     * initialize of views and variables
     * 
    * */

    private void initialize(View view) {
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        newsAdapter=new NewsAdapter(getActivity(),data);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));
        recyclerView.setAdapter(newsAdapter);
        filldata();
    }

    private void filldata() {
        data.add(new PojoNews("XDA Developers","Project Trebel affects custom rom development",R.drawable.ic_xda,"https://en.wikipedia.org/wiki/XDA_Developers"));
        data.add(new PojoNews("Sanju","Day 23, still surviving at 328cr",R.drawable.ic_sanju,"https://en.wikipedia.org/wiki/Sanju"));
        data.add(new PojoNews("OnePlus 6","Oxygen OS update bring camera improvements",R.drawable.ic_one,"https://en.wikipedia.org/wiki/OnePlus_6"));
        data.add(new PojoNews("James Gunn","Got fired from MCU by Diesney",R.drawable.ic_gunn,"https://en.wikipedia.org/wiki/James_Gunn"));
        newsAdapter.notifyDataSetChanged();
    }


    /**
     * creating and returning new instance of fragment
     * 
    * */
    public static HomeFragment newInstance(String text) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.MSG, text);
        fragment.setArguments(bundle);
        return fragment;
    }


}
