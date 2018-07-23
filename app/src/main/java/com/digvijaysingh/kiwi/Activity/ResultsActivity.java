package com.digvijaysingh.kiwi.Activity;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.digvijaysingh.kiwi.Adapters.ResultsAdapter;
import com.digvijaysingh.kiwi.NetworkLayer.RestClient;
import com.digvijaysingh.kiwi.R;
import com.digvijaysingh.kiwi.Utils.Constant;
import com.digvijaysingh.kiwi.pojo.Page;
import com.digvijaysingh.kiwi.pojo.PojoSearch;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ResultsActivity extends AppCompatActivity {
    private List<Page> results= new ArrayList<>();

    RecyclerView recyclerView;
    ResultsAdapter adapter;
    TextView tvTitle;
    FloatingActionButton ivSwitch;
    boolean gridShowing=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        BindViews();
    }

    private void BindViews() {
        recyclerView=findViewById(R.id.recyclerView);
        ivSwitch=findViewById(R.id.ivSwitch);
        adapter= new ResultsAdapter(ResultsActivity.this,results);
        recyclerView.setLayoutManager(new LinearLayoutManager(ResultsActivity.this));
        recyclerView.setAdapter(adapter);
        tvTitle=findViewById(R.id.tvTitle);
        String s = getIntent().getStringExtra("search");
        tvTitle.setText(s);
        search(s);
        setLisners();
    }

    private void setLisners() {
        ivSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gridShowing) {
                    gridShowing=false;
                    recyclerView.setLayoutManager(new LinearLayoutManager(ResultsActivity.this));

                    ivSwitch.setImageResource(R.drawable.ic_grid);
                }

                else {
                    gridShowing=true;
                    recyclerView.setLayoutManager(new GridLayoutManager(ResultsActivity.this, 2));
                    ivSwitch.setImageResource(R.drawable.ic_list);
                }

            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void search(String s) {
        new RestClient(ResultsActivity.this).get().getSearchResults("query","json","pageimages|pageterms","prefixsearch",
                "1","2","thumbnail","200","10","description",s,"20","0").enqueue(new Callback<PojoSearch>() {
            @Override
            public void onResponse(Call<PojoSearch> call, Response<PojoSearch> response) {
                if(response.isSuccessful()) {
                    if(response.body().getQuery().getPages()!=null&&response.body().getQuery().getPages().size()>0) {
                        results.clear();
                        results.addAll(response.body().getQuery().getPages());
                        adapter.notifyDataSetChanged();
                        Toast.makeText(ResultsActivity.this,Constant.NO_RESPONSE,Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ResultsActivity.this, Constant.NO_RESPONSE,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PojoSearch> call, Throwable t) {
                Toast.makeText(ResultsActivity.this, Constant.NO_RESPONSE,Toast.LENGTH_SHORT).show();
               }

        });
    }

}
