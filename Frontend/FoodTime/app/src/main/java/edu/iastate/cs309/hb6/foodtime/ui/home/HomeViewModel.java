package edu.iastate.cs309.hb6.foodtime.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * View model for the home page
 */
public class HomeViewModel extends ViewModel {

    /** The text to display */
    private final MutableLiveData<String> mText;

    /**
     * Constructor for the view model
     */
    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }
}