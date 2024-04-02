package edu.thomas.ui.train;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TrainViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public TrainViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is train fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}