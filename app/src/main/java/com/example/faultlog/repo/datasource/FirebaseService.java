package com.example.faultlog.repo.datasource;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.faultlog.dummy.DummyContent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FirebaseService {

    private static final DatabaseReference mref = FirebaseDatabase.getInstance().getReference();
    private List<DummyContent> faultList = new ArrayList<DummyContent>();
    FirebaseQueryLiveData mLiveData;
    public MutableLiveData<Boolean> returnValue = new MutableLiveData<>();

    public FirebaseService(Context context) {
    }

    public void submitDetails(DummyContent content){

        DatabaseReference mdatabase = mref.child(content.zone).child(content.log).child(content.id);
        mdatabase.setValue(content).addOnCompleteListener(v ->{
            if(v.isSuccessful()){
                returnValue.setValue(true);
            }else {
                returnValue.setValue(false);
            }
        });
//
    }

    public void upDateDetails(DummyContent content){

        DatabaseReference mdatabase = mref.child(content.zone).child(content.log).child(content.id);
        mdatabase.setValue(content).addOnCompleteListener(v ->{
            if(v.isSuccessful()){
                returnValue.setValue(true);
            }else {
                returnValue.setValue(false);
            }
        });


//
    }


    public LiveData<List<DummyContent>> getFaultLog(String zone, String logType){
        DatabaseReference mdatabase = mref.child(zone).child(logType);
        mLiveData = new FirebaseQueryLiveData(mdatabase);
        LiveData<List<DummyContent>> mFaultLiveData =
                Transformations.map(mLiveData, new DesserializerFaultList());
        return mFaultLiveData;


    }



    private class DesserializerFaultList implements Function<DataSnapshot, List<DummyContent>> {

        @Override
        public List<DummyContent> apply(DataSnapshot dataSnapshot) {
            faultList.clear();
            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                DummyContent content = snapshot.getValue(DummyContent.class);
                faultList.add(content);
            }
            return faultList;
        }
    }


}
