package com.example.faultlog;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.faultlog.dummy.DummyContent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class ViewFragment extends Fragment implements AdapterView.OnItemSelectedListener {



    EditText feeder, isolatedDt, natureOfFault,
            action, duration, remark, timeFault, timeOut, areaAff;
    Spinner capacity, voltage;

    private View mView;
    private String capacityValue;
    private String voltageValue;
    private String zone ;
    private View brklayout, prevlayout, txlayout;
    private static final String TAG = "ViewFragment";
    private DummyContent args;
    private FaultListViewModel faultListViewModel;
    private static int state = 0;


    public ViewFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        args = getArguments().getParcelable(Contracts.BUNDLETOVIEWLOG);
        Log.d(TAG, "onCreateView: view frag "+ args.log);
        if(args.log.equals(Contracts.BREAKDOWN)){
            mView = inflater.inflate(R.layout.fragment_submit_fault_log, container, false);
        }else if(args.log.equals(Contracts.PREVENTIVE)){
            mView = inflater.inflate(R.layout.fragment_preventive, container, false);
        }else {
            mView = inflater.inflate(R.layout.fragment_transformer, container, false);
        }


        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        args = getArguments().getParcelable(Contracts.BUNDLETOVIEWLOG);

        faultListViewModel = new ViewModelProvider(getActivity()).get(FaultListViewModel.class);
        Log.d(TAG, "onViewCreated: "+ args.log);
        initialize(view);
        NavController navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(R.id.preventiveFragment2).build();
        Toolbar toolbar = view.findViewById(R.id.toolbar);


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                item.setIcon(R.drawable.ic_baseline_edit_24);
                if(item.getItemId() == R.id.action_list){
                    if(state == 1){
                        enable();
                        state =0;

                    }else{
                        disable();
                        state =1;
                    }
                    Log.d(TAG, "onMenuItemClick: list have been clicked to navigate" + state );
                }
                return false;
            }
        });

        NavigationUI.setupWithNavController(
                toolbar, navController, appBarConfiguration);

    }

    private void disable(){
        voltage.setEnabled(false);
        capacity.setEnabled(false);
        feeder.setEnabled(false);
        action.setEnabled(false);
        remark.setEnabled(false);
        isolatedDt.setEnabled(false);
        natureOfFault.setEnabled(false);
        duration.setEnabled(false);
        timeFault.setEnabled(false);
        if(args.log.equals(Contracts.TRANSFORMER) ||args.log.equals(Contracts.BREAKDOWN)) {
            timeOut.setEnabled(false);
        }
        if(args.log.equals(Contracts.TRANSFORMER) ) {
            areaAff.setEnabled(false);
        }

        state = 1;


    }

    private void enable(){
        voltage.setEnabled(true);
        capacity.setEnabled(true);
        feeder.setEnabled(true);
        action.setEnabled(true);
        remark.setEnabled(true);
        isolatedDt.setEnabled(true);
        natureOfFault.setEnabled(true);
        duration.setEnabled(true);
        timeFault.setEnabled(true);
        if(args.log.equals(Contracts.TRANSFORMER) ||args.log.equals(Contracts.BREAKDOWN)) {
            timeOut.setEnabled(true);
        }
        if(args.log.equals(Contracts.TRANSFORMER) ) {
            areaAff.setEnabled(true);
        }
    }

    private void initialize(View v) {
        voltage =v.findViewById(R.id.voltage);
        capacity = v.findViewById(R.id.capacity);
        feeder = v.findViewById(R.id.feeder_sub);
        action = v.findViewById(R.id.action);
        remark = v.findViewById(R.id.remark);
        isolatedDt = v.findViewById(R.id.isolateddt);
        natureOfFault = v.findViewById(R.id.duration_of_);
        duration = v.findViewById(R.id.details);
        if(args.log.equals(Contracts.TRANSFORMER) || args.log.equals(Contracts.BREAKDOWN)) {
            timeOut = v.findViewById(R.id.duration_of_);
        }
        if(args.log.equals(Contracts.TRANSFORMER) ) {
            areaAff = v.findViewById(R.id.area_aff);
        }
        txlayout = v.findViewById(R.id.txlayout);
        prevlayout = v.findViewById(R.id.prvLayout);
        brklayout = v.findViewById(R.id.brklayout);

        timeFault = v.findViewById(R.id.date_of_occurr);
        setupSpinner(mView.getContext(), R.array.ratings, android.R.layout.simple_spinner_item, capacity);
        setupSpinner(mView.getContext(), R.array.voltage, android.R.layout.simple_spinner_item, voltage);

        voltage.setOnItemSelectedListener(this);
        capacity.setOnItemSelectedListener(this);
        setValues(args);
        disable();

        Log.d(TAG, "initialize: " + args.log);
//        switch(args.log) {
//            case Contracts.BREAKDOWN:
//                txlayout.setVisibility(View.GONE);
//                prevlayout.setVisibility(View.GONE);
//                break;
//            case Contracts.PREVENTIVE:
//                txlayout.setVisibility(View.GONE);
//                brklayout.setVisibility(View.GONE);
//                break;
//            case Contracts.TRANSFORMER:
//                brklayout.setVisibility(View.GONE);
//                prevlayout.setVisibility(View.GONE);
//                break;
//            default:
////                brklayout.setVisibility(View.GONE);
////                txlayout.setVisibility(View.GONE);
////                prevlayout.setVisibility(View.GONE);

//        }

        FloatingActionButton fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(view ->  {

            Log.d(TAG, "initialize: fab clicked");
            submit();
        });
       

    }

//    private boolean validate(String volt, String capacity_sp, String feed,
//                             String action, String remark, String isolatedDt, String natureFault,
//                             String duration, String timeFault){
//        if(TextUtils.isEmpty(volt)){
//            Snackbar.make( mView, "Voltage is not set", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
//            return false;
//        }else if(TextUtils.isEmpty(feed)){
//            feeder.setError(getString(R.string.required));
//            return false;
//        }else if(TextUtils.isEmpty(action)){
//            this.action.setError(getString(R.string.required));
//            return false;
//        }else if(TextUtils.isEmpty(remark)){
//            this.remark.setError(getString(R.string.required));
//            return false;
//        }else if(TextUtils.isEmpty(isolatedDt)){
//            this.isolatedDt.setError(getString(R.string.required));
//            return false;
//        }else if(TextUtils.isEmpty(natureFault)){
//            this.natureOfFault.setError(getString(R.string.required));
//            return false;
//        }else if(TextUtils.isEmpty(duration)){
//            this.duration.setError(getString(R.string.required));
//            return false;
//        }else if(TextUtils.isEmpty(timeFault)){
//            this.timeFault.setError(getString(R.string.required));
//            return false;
//        }else if(TextUtils.isEmpty(capacity_sp)){
//            Snackbar.make( mView, "capacity is not set", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
//            return false;
//        }else {
//            this.feeder.setError(null);
//            this.action.setError(null);
//            this.remark.setError(null);
//            this.isolatedDt.setError(null);
//            this.natureOfFault.setError(null);
//            this.duration.setError(null);
//            this.timeFault.setError(null);
//            return true;
//        }
//    }

    public void setupSpinner(Context context, int textArrayResid, int textViewResid, Spinner spinner){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, textArrayResid, textViewResid);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String time = sdf.format(new Date());
        return time;
    }


    private DummyContent getValues(){
        DummyContent dummyContent = new DummyContent();
        dummyContent.feeder = feeder.getText().toString().isEmpty()? " ": feeder.getText().toString() ;

        Log.d(TAG, "getValues: is null ? " +  feeder.getText().toString().equals(null) + "  is empty? " + feeder.getText().toString().isEmpty());
        dummyContent.actionTaken = this.action.getText().toString().isEmpty()? " " : this.action.getText().toString();
        dummyContent.remarks = this.remark.getText().toString().isEmpty()? " " : this.remark.getText().toString();
        dummyContent.isolatedDt = this.isolatedDt.getText().toString().isEmpty()? " " : this.isolatedDt.getText().toString();
        dummyContent.detailsOfMaintenance = this.natureOfFault.getText().toString().isEmpty()? " " : this.natureOfFault.getText().toString();
        dummyContent.durationOfOutage = this.duration.getText().toString().isEmpty()? " " : this.duration.getText().toString();
        dummyContent.timeIn = this.timeFault.getText().toString().isEmpty()? " " : this.timeFault.getText().toString();
        dummyContent.voltageLvl = voltageValue;
        dummyContent.capacity = capacityValue;
        dummyContent.zone = args.zone;
        dummyContent.restorationDate = this.timeOut.getText().toString().isEmpty()? " " : this.timeOut.getText().toString();
        dummyContent.areaAffected = this.areaAff.getText().toString().isEmpty()? " " : this.areaAff.getText().toString();
        dummyContent.id = getTime();
        dummyContent.log = args.log;

        return  dummyContent;
    }

    private void setValues(DummyContent dummyContent){
        Log.d(TAG, "setValues: setting arfs");
        feeder.setText(dummyContent.feeder.isEmpty() ? " ": dummyContent.feeder);
        action.setText(dummyContent.actionTaken.isEmpty()? " ":dummyContent.actionTaken);
        remark.setText(dummyContent.remarks.isEmpty()? " " : dummyContent.remarks);
        isolatedDt.setText(dummyContent.isolatedDt.isEmpty()? " " : dummyContent.isolatedDt);
        natureOfFault.setText(dummyContent.detailsOfMaintenance.isEmpty()? " " : dummyContent.detailsOfMaintenance);
        if(dummyContent.log.equals(Contracts.PREVENTIVE) || dummyContent.log.equals(Contracts.BREAKDOWN)) {
            duration.setText(dummyContent.durationOfOutage.isEmpty() ? " " : dummyContent.durationOfOutage);
        }
        timeFault.setText(dummyContent.timeIn.isEmpty()? " " : dummyContent.timeIn);
        if(dummyContent.log.equals(Contracts.TRANSFORMER) || dummyContent.log.equals(Contracts.BREAKDOWN)){
            timeOut.setText(dummyContent.restorationDate.isEmpty()? " " : dummyContent.restorationDate);
        }
        if(dummyContent.log.equals(Contracts.TRANSFORMER)){
            areaAff.setText(dummyContent.areaAffected.isEmpty()? " " : dummyContent.areaAffected);
        }
        zone = dummyContent.zone.isEmpty()? args.zone : dummyContent.zone;

    }

    private void submit() {

        DummyContent values = getValues();
        Log.d(TAG, "submit: " + zone +" " + values.feeder + " " + values.zone + " " + values.log);

//        if(validate(voltageValue, capacityValue, values.feeder, values.actionTaken, values.remarks,
//                values.isolatedDt, values.detailsOfMaintenance, values.durationOfOutage, values.timeIn)){

            LiveData<Boolean> res = faultListViewModel.getResponse();
            res.observe(getViewLifecycleOwner(), result->{
                if(result) {
                    Log.d(TAG, "submit: "+ result);
                    Snackbar.make( mView, "form updated", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                }else {
                    Snackbar.make( mView, "Unable to submit form", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
            faultListViewModel.updateFaultLog(values);
//        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.voltage:
                voltageValue = ((String) voltage.getSelectedItem());
                break;
            case R.id.capacity:
                capacityValue = ((String) capacity.getSelectedItem());
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}