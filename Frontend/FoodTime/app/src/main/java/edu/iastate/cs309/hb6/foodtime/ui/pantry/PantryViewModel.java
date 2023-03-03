package edu.iastate.cs309.hb6.foodtime.ui.pantry;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PantryViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PantryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}