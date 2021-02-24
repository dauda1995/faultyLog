package com.example.faultlog.repo;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.faultlog.dummy.DummyContent;
import com.example.faultlog.repo.datasource.FirebaseService;

import java.util.List;

public class RepositoryImpl implements Repository {

    private FirebaseService firebaseService;

    public RepositoryImpl(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    public static Repository create(Context mAppContext){
        final FirebaseService firebaseService = new FirebaseService(mAppContext);
        return  new RepositoryImpl(firebaseService);

    }


    @Override
    public void submitFaultLog(DummyContent content) {
        firebaseService.submitDetails(content);

    }

    @Override
    public LiveData<List<DummyContent>> getFaultLog(String zone, String logType) {
        return firebaseService.getFaultLog(zone, logType);
    }

    @Override
    public LiveData<Boolean> getResponse() {
        return firebaseService.returnValue;
    }

    @Override
    public void updateFaultLog(DummyContent content) {
        firebaseService.upDateDetails(content);
    }
}
