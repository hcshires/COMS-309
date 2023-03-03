package edu.iastate.cs309.hb6.foodtime.ui.cookbook;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CookBookViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CookBookViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}