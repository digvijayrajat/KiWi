package com.digvijaysingh.kiwi.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.digvijaysingh.kiwi.Adapters.MyPagerAdapter;
import com.digvijaysingh.kiwi.CustomView.NonSwipeableViewPager;
import com.digvijaysingh.kiwi.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HomeActivity extends AppCompatActivity {
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    private View drawerContainer;
    private View drawerContent;
    private View mainContent;
    private ImageView ivDrawer;
    private CardView cardView;
    private NonSwipeableViewPager viewPager;
    private BottomNavigationView bottomNavigationMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        BindViews();
        setUpDrawer();
        setListners();

    }

    private void setListners() {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(HomeActivity.this,
                                cardView,
                                ViewCompat.getTransitionName(cardView));
                startActivity(intent, options.toBundle());
            }
        });
    }

    private void setUpDrawer() {
        toggle = new ActionBarDrawerToggle( this,
                drawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawer, float slideOffset) {
                super.onDrawerSlide(drawer, slideOffset);
                drawerContent.setTranslationX(drawerContainer.getWidth() * (1 - slideOffset));
                mainContent.setTranslationX(drawerContainer.getWidth() * slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                ivDrawer.setImageResource(R.drawable.ic_drawer_active);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                ivDrawer.setImageResource(R.drawable.ic_drawer);

            }
        };
        drawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));

        drawerLayout.addDrawerListener(toggle);
        ivDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawerLayout.isDrawerOpen(Gravity.START))
                {
                    drawerLayout.closeDrawer(Gravity.END);
                }
                else {
                    drawerLayout.openDrawer(Gravity.START);
                }
            }
        });
        bottomNavigationMenu.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_favorites: {
                                viewPager.setCurrentItem(0);
                                break;

                            }
                            case R.id.action_schedules: {
                                viewPager.setCurrentItem(1);
                                break;
                            }
                            case R.id.action_music: {
                                viewPager.setCurrentItem(2);
                                break;
                            }
                        }
                        return true;
                    }
                });
    }

    private void BindViews() {
        drawerLayout       = findViewById(R.id.drawer_layout);
        drawerContainer    = findViewById(R.id.drawer_container);
        drawerContent      = findViewById(R.id.drawer_content);
        mainContent        = findViewById(R.id.main_content);
        cardView           = findViewById(R.id.card_view);
        ivDrawer           = findViewById(R.id.ivDrawer);
        viewPager          = findViewById(R.id.viewPager);
        bottomNavigationMenu= findViewById(R.id.bottom_navigation);


        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (toggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }




}

