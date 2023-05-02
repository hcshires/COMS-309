package edu.iastate.cs309.hb6.foodtime.ui.pantry;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * View model for the pantry
 */
public class PantryViewModel extends ViewModel {

    /**
     * The text for the pantry view model
     */
    private final MutableLiveData<String> mText;

    /**
     * Constructor for the pantry view model
     */
    public PantryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is pantry fragment");
    }
}