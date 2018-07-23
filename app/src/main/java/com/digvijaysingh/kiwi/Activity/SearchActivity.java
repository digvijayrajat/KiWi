package com.digvijaysingh.kiwi.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.digvijaysingh.kiwi.Adapters.HistorySuggession;
import com.digvijaysingh.kiwi.Adapters.SearchSeggetionAdapter;
import com.digvijaysingh.kiwi.NetworkLayer.RestClient;
import com.digvijaysingh.kiwi.R;
import com.digvijaysingh.kiwi.Utils.Constant;
import com.digvijaysingh.kiwi.Utils.LimitedSizeQueue;
import com.digvijaysingh.kiwi.pojo.Page;
import com.digvijaysingh.kiwi.pojo.PojoSearch;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SearchActivity extends AppCompatActivity {

    RecyclerView recyclerView,recyclerViewhistory;
    private List<Page> results= new ArrayList<>();
    private SearchSeggetionAdapter adapter;

    private CardView cardViewSeeMore, cardViewResult, cardViewHistory;
    private EditText etSearch;
    private ProgressBar prg;

    private LimitedSizeQueue<String> historyQueue=new LimitedSizeQueue<>(3);
    private SharedPreferences sharedPreferences;
    private HistorySuggession historyadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        BindViews();
        setData();

        setListners();
    }

    private void setListners() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 1)
                    search(s.toString());
                else {
                    cardViewResult.setVisibility(View.GONE);
                    cardViewHistory.setVisibility(View.VISIBLE);
                    cardViewSeeMore.setVisibility(View.GONE);
                    // handle error
                }
            }
        });

        cardViewSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, ResultsActivity.class).putExtra("search",etSearch.getText().toString());
                startActivity(intent);
            }
        });
    }

    private void setData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        recyclerViewhistory.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        adapter=new SearchSeggetionAdapter(SearchActivity.this,results);
        historyadapter=new HistorySuggession(SearchActivity.this,historyQueue);
        recyclerView.setAdapter(adapter);
        recyclerViewhistory.setAdapter(historyadapter);

        if (!sharedPreferences.getString(Constant.KEY_HISTORY, "").isEmpty()) {
            String sthistory = sharedPreferences.getString(Constant.KEY_HISTORY, "");
            historyQueue.addAll(new Gson().fromJson(sthistory,   new TypeToken<List<String>>(){}.getType()));
            }
        else
            cardViewHistory.setVisibility(View.GONE);
    }

    private void BindViews() {
        etSearch=findViewById(R.id.etSearch);
        prg=findViewById(R.id.prg);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(SearchActivity.this);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerViewhistory=findViewById(R.id.recyclerViewhistory);
        cardViewSeeMore = findViewById(R.id.cardViewSeeMore);
        cardViewResult = findViewById(R.id.cardViewResult);
        cardViewHistory = findViewById(R.id.cardViewHistory);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    private void search(String s) {
        prg.setVisibility(View.VISIBLE);
        new RestClient(SearchActivity.this).get().getSearchResults("query","json","pageimages|pageterms","prefixsearch",
                "1","2","thumbnail","50","10","description",s,"4","0").enqueue(new Callback<PojoSearch>() {
            @Override
            public void onResponse(Call<PojoSearch> call, Response<PojoSearch> response) {
                if(response.isSuccessful()) {
                    if(response.body().getQuery().getPages()!=null&&response.body().getQuery().getPages().size()>0) {
                        results.clear();
                        results.addAll(response.body().getQuery().getPages());
                        adapter.notifyDataSetChanged();
                        cardViewResult.setVisibility(View.VISIBLE);
                        cardViewHistory.setVisibility(View.GONE);
                        cardViewSeeMore.setVisibility(View.VISIBLE);
                        prg.setVisibility(View.INVISIBLE);
                    }
                } else {
                    cardViewResult.setVisibility(View.VISIBLE);
                    cardViewSeeMore.setVisibility(View.GONE);
                    cardViewHistory.setVisibility(View.GONE);
                    // handle error
                }
            }

            @Override
            public void onFailure(Call<PojoSearch> call, Throwable t) {
                prg.setVisibility(View.INVISIBLE);
                cardViewResult.setVisibility(View.VISIBLE);
                cardViewHistory.setVisibility(View.GONE);
                cardViewSeeMore.setVisibility(View.GONE);

            }

        });
    }
    public void saveToPrefs(String value) {
            historyQueue.add(value);
            sharedPreferences.edit().putString("history",new Gson().toJson(historyQueue)).commit();
        }
    }

