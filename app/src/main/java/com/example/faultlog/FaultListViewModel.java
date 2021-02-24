package com.example.faultlog;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.faultlog.dummy.DummyContent;
import com.example.faultlog.repo.Repository;
import com.example.faultlog.repo.RepositoryImpl;

import java.util.List;

public class FaultListViewModel extends AndroidViewModel implements Repository {
    private Repository mRepository;
    private DummyContent valuesSaved = new DummyContent();
    MutableLiveData<DummyContent> content = new MutableLiveData<>();
    private String zone;
    MutableLiveData<String> zoneLiveData = new MutableLiveData<>();
    public FaultListViewModel(@NonNull Application application) {
        super(application);
        mRepository = RepositoryImpl.create(application);
    }

    @Override
    public void submitFaultLog(DummyContent content) {
        mRepository.submitFaultLog(content);
    }

    @Override
    public LiveData<List<DummyContent>> getFaultLog(String zone, String logType) {
        return mRepository.getFaultLog(zone, logType);
    }

    @Override
    public LiveData<Boolean> getResponse() {
        return mRepository.getResponse();
    }

    @Override
    public void updateFaultLog(DummyContent content) {
        mRepository.updateFaultLog(content);

    }

    public void setZone(String zone){
        this.zone = zone;
    }

    public String getZone(){
        return zone;
    }

    public void setSavedInstanceState(DummyContent val){
        this.valuesSaved = val;
        content.postValue(val);
    }

    public LiveData<DummyContent> getSavedInstanceState(){
        return content;
    }


}
