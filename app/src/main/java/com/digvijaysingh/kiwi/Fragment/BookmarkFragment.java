package com.digvijaysingh.kiwi.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digvijaysingh.kiwi.Activity.SearchActivity;
import com.digvijaysingh.kiwi.Adapters.HistorySuggession;
import com.digvijaysingh.kiwi.R;
import com.digvijaysingh.kiwi.Utils.Constant;
import com.digvijaysingh.kiwi.Utils.LimitedSizeQueue;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class BookmarkFragment  extends Fragment {
    /**
     * Declation of views and variables
     *
     * */
    RecyclerView recyclerViewhistory;
    private LimitedSizeQueue<String> historyQueue=new LimitedSizeQueue<>(3);
    private SharedPreferences sharedPreferences;
    private HistorySuggession historyadapter;
    private CardView cardViewHistory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bookmark_fragment, container, false);
        initialize(view);
        return view;

    }

    private void initialize(View view) {
        recyclerViewhistory=view.findViewById(R.id.recyclerViewhistory);
        cardViewHistory = view.findViewById(R.id.cardViewHistory);

        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (!sharedPreferences.getString(Constant.KEY_HISTORY, "").isEmpty()) {
            String sthistory = sharedPreferences.getString(Constant.KEY_HISTORY, "");
            historyQueue.addAll(new Gson().fromJson(sthistory,   new TypeToken<List<String>>(){}.getType()));
            cardViewHistory.setVisibility(View.VISIBLE);
        }
        historyadapter=new HistorySuggession(getActivity(),historyQueue);
        recyclerViewhistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewhistory.setAdapter(historyadapter);

    }

    /**
     * creating and returning new instance of fragment
     *
     * */
    public static BookmarkFragment newInstance(String text) {
        BookmarkFragment fragment = new BookmarkFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.MSG, text);
        fragment.setArguments(bundle);
        return fragment;
    }


}

