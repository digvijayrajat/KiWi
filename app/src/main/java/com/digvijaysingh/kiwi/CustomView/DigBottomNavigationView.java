package com.digvijaysingh.kiwi.CustomView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;

import java.lang.reflect.Field;

public class DigBottomNavigationView extends BottomNavigationView {

      @RequiresApi(api = Build.VERSION_CODES.KITKAT)
      public DigBottomNavigationView(Context context, AttributeSet attrs) {
          super(context, attrs);
          centerMenuIcon();
      }

      @RequiresApi(api = Build.VERSION_CODES.KITKAT)
      @SuppressLint("RestrictedApi")
      private void centerMenuIcon() {
          BottomNavigationMenuView menuView = getBottomMenuView();

          if (menuView != null) {
              for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView menuItemView = (BottomNavigationItemView) menuView.getChildAt(i);

                AppCompatImageView icon = (AppCompatImageView) menuItemView.getChildAt(0);

                FrameLayout.LayoutParams params = (LayoutParams) icon.getLayoutParams();
                params.gravity = Gravity.CENTER;

                menuItemView.setShiftingMode(true);
              }
          }
      }

      @RequiresApi(api = Build.VERSION_CODES.KITKAT)
      private BottomNavigationMenuView getBottomMenuView() {
          Object menuView = null;
          try {
              Field field = BottomNavigationView.class.getDeclaredField("mMenuView");
              field.setAccessible(true);
              menuView = field.get(this);
          } catch (NoSuchFieldException | IllegalAccessException e) {
              e.printStackTrace();
          }

          return (BottomNavigationMenuView) menuView;
      }
    }