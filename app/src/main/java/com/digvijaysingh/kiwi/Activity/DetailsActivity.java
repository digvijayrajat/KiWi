package com.digvijaysingh.kiwi.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.digvijaysingh.kiwi.CustomView.BlurTransformation;
import com.digvijaysingh.kiwi.NetworkLayer.RestClient;
import com.digvijaysingh.kiwi.R;
import com.digvijaysingh.kiwi.Utils.Constant;
import com.digvijaysingh.kiwi.pojo.PojoSearch;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DetailsActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {
        private boolean mIsTheTitleVisible          = false;
        private boolean mIsTheTitleContainerVisible = true;
        private LinearLayout mTitleContainer;
        private TextView mTitle,tvDescription,tvTitle,tvBigTitle,tvSmallDescription;
        private AppBarLayout mAppBarLayout;
        private Toolbar mToolbar;
        private ImageView ivImage,ivBackground;
        private LinearLayout llGoogle,llYoutube;
        private Button readMore;
        private String pageId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
            bindActivity();
            mAppBarLayout.addOnOffsetChangedListener(this);
            mToolbar.inflateMenu(R.menu.menu_main);
            startAlphaAnimation(mTitle, 0, View.INVISIBLE);
            setData();
        }






    private void setData() {
        pageId=getIntent().getStringExtra(Constant.PAGE_ID);
        search(getIntent().getStringExtra(Constant.PAGE_ID));
        if(getIntent().getStringExtra(Constant.IMAGE_URL)!=null) {
            Glide.with(DetailsActivity.this).load(getIntent().getStringExtra(Constant.IMAGE_URL)).apply(RequestOptions.circleCropTransform()).into(ivImage);
            Glide.with(DetailsActivity.this).load(getIntent().getStringExtra(Constant.IMAGE_URL)).apply(RequestOptions.bitmapTransform(new BlurTransformation(20, 2))).into(ivBackground);
        }
        tvBigTitle.setText(getIntent().getStringExtra(Constant.TITLE));
        mTitle.setText(getIntent().getStringExtra(Constant.TITLE));
        if(getIntent().getStringExtra(Constant.DESCRIPTION)!=null)
        tvSmallDescription.setText(getIntent().getStringExtra(Constant.DESCRIPTION));

        setListners();
    }





    private void setListners() {
        llGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailsActivity.this, PageViewActivity.class).putExtra(Constant.KEY_URL, Constant.GOOGLE_API+tvBigTitle.getText().toString()));
            }
        });
        llYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailsActivity.this, PageViewActivity.class).putExtra(Constant.KEY_URL,Constant.YOUTUBE_API+tvBigTitle.getText().toString()));

            }
        });
        readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailsActivity.this, PageViewActivity.class).putExtra(Constant.KEY_URL,Constant.WIKI_PAGE_API+pageId));

            }
        });
    }




    private void bindActivity() {
        mToolbar           = (Toolbar) findViewById(R.id.main_toolbar);
        mTitle             = (TextView) findViewById(R.id.tvTitle);
        mTitleContainer    = (LinearLayout) findViewById(R.id.main_linearlayout_title);
        mAppBarLayout      = (AppBarLayout) findViewById(R.id.main_appbar);
        tvDescription      = (TextView) findViewById(R.id.tvDescription);
        ivImage            = (ImageView) findViewById(R.id.ivImage);
        ivBackground       = (ImageView) findViewById(R.id.ivBackground);
        tvBigTitle         = (TextView) findViewById(R.id.tvBigTitle);
        tvSmallDescription = (TextView) findViewById(R.id.tvSmallDescription);
        llGoogle           = (LinearLayout) findViewById(R.id.llGoogle);
        llYoutube          = (LinearLayout) findViewById(R.id.llYoutube);
        readMore           = (Button) findViewById(R.id.btnWiki);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_share)
        {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType(Constant.MEDIA_TYPE);
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Constant.WIKI_PAGE_API+pageId);
            startActivity(Intent.createChooser(sharingIntent,Constant.SHARE_TITLE));
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);

    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= Constant.PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if(!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, Constant.ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, Constant.ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= Constant.PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, Constant.ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, Constant.ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }


    private void search(String s) {
        new RestClient(DetailsActivity.this).get().getDetails("query","json","extracts","",
                s,"").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    if(response.body()!=null) {
                        JSONObject mainObject = null;
                        try {
                            mainObject = new JSONObject(response.body().string());
                            JSONObject uniObject = mainObject.getJSONObject("query");
                            JSONObject pageObject= uniObject.getJSONObject("pages");
                            JSONObject idObject = pageObject.getJSONObject(s);
                            String disc = idObject.getString("extract");
                            tvDescription.setText(disc);
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {

                    Toast.makeText(DetailsActivity.this,Constant.NO_RESPONSE,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(DetailsActivity.this,Constant.NO_RESPONSE,Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
