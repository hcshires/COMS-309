package edu.iastate.cs309.hb6.foodtime.ui.cookbook;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

import edu.iastate.cs309.hb6.foodtime.R;


public class PagerAdapter extends androidx.viewpager.widget.PagerAdapter {

    public PagerAdapter() {

    }

    /**
     * @return
     */
    @Override
    public int getCount() {
        return 0;
    }

    /**
     * @param view   Page View to check for association with <code>object</code>
     * @param object Object to check for association with <code>view</code>
     * @return
     */
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }


}
