package edu.iastate.cs309.hb6.foodtime.ui.cookbook;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class PagerAdapter extends FragmentStateAdapter {

//    private final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
//    private final ArrayList<String> fragmentTitles = new ArrayList<>();

    /**
     *
     * @param fragmentManager
     * @param lifecycle
     */
    public PagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    /**
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        IngredientsFragment ingredientsFragment = new IngredientsFragment();
        DirectionsFragment directionsFragment = new DirectionsFragment();
        Bundle args =new Bundle();
        if (position ==1) {
            return directionsFragment;
        }
//        args.putString(IngredientsFragment.TITLE, "Ingredients") ;
////        ingredientsFragment.setArguments(args);
        return ingredientsFragment;
    }

    /**
     * @return
     */
    @Override
    public int getItemCount() {
        return 2;
    }

//    public void addFragment(Fragment fragment, String title) {
//        fragmentArrayList.add(fragment);
//        fragmentTitles.add(title);
//    }

}