package com.example.faultlog;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DetailsViewModel extends ViewModel {

    public String zone;
    public MutableLiveData<String> mutableLiveData = new MutableLiveData<>();
    public LiveData<String> liveData;

    public void setZone(String zone){
        this.zone = zone;
        mutableLiveData.setValue(zone);
        liveData = mutableLiveData;
    }

    public LiveData<String> getZone(){
        return mutableLiveData;
    }
}
