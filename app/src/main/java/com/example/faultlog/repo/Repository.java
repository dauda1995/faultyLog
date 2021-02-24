package com.example.faultlog.repo;

import androidx.lifecycle.LiveData;

import com.example.faultlog.dummy.DummyContent;

import java.util.List;

public interface Repository {
    void submitFaultLog(DummyContent content);
    LiveData<List<DummyContent>> getFaultLog(String zone, String logType);
    LiveData<Boolean> getResponse();
    void updateFaultLog(DummyContent content);

}
