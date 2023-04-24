package edu.iastate.cs309.hb6.foodtime.ui.cookbook;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * View model for the cookbook
 */
public class CookBookViewModel extends ViewModel {

    /** The text to be displayed */
    private final MutableLiveData<String> mText;

    /**
     * Constructor for the view model
     */
    public CookBookViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is CookBook fragment");
    }
}