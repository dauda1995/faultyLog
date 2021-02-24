package com.example.faultlog.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.faultlog.Contracts;
import com.example.faultlog.PreventiveViewModel;
import com.example.faultlog.R;
import com.example.faultlog.dummy.DummyContent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PreventiveEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PreventiveEditFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = "PreventiveEditFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText feeder, isolatedDt, natureOfFault,
            action, duration, remark, timeFault;
    Spinner capacity, voltage;

    private String capacityValue;
    private String voltageValue;
    private String zone;

    private View mView;
    PreventiveViewModel preventiveViewModel;

    DummyContent args;

    private static int state = 0;
    private FloatingActionButton fab;

    public PreventiveEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PreventiveEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PreventiveEditFragment newInstance(String param1, String param2) {
        PreventiveEditFragment fragment = new PreventiveEditFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_preventive_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        args = getArguments().getParcelable(Contracts.BUNDLETOVIEWLOG);

        NavController navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(R.id.preventiveEditFragment).build();
        Toolbar toolbar = view.findViewById(R.id.toolbar2);
        initialize(view);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.action_list){
                    if(state == 1){
                        enable();
                        item.setIcon(R.drawable.ic_baseline_edit_24);
                        state =0;

                    }else{
                        disable();
                        item.setIcon(R.drawable.ic_baseline_visibility_24);
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

    private void initialize(View v) {
        voltage =v.findViewById(R.id.voltage);
        capacity = v.findViewById(R.id.capacity);
        feeder = v.findViewById(R.id.feeder_sub);
        action = v.findViewById(R.id.action);
        remark = v.findViewById(R.id.remark);
        isolatedDt = v.findViewById(R.id.isolateddt);
        natureOfFault = v.findViewById(R.id.details);
        duration = v.findViewById(R.id.duration_of_);
        timeFault = v.findViewById(R.id.date_of_occurr);
        setupSpinner(v.getContext(), R.array.ratings, android.R.layout.simple_spinner_item, capacity);
        setupSpinner(v.getContext(), R.array.voltage, android.R.layout.simple_spinner_item, voltage);

        setValues(args);
        voltage.setOnItemSelectedListener(this);
        capacity.setOnItemSelectedListener(this);


        fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(view ->  {

            Log.d(TAG, "initialize: fab clicked");
            submit();
        });

        disable();

    }

    private boolean validate(String volt, String capacity_sp, String feed,
                             String action, String remark, String isolatedDt, String natureFault,
                             String duration, String timeFault) {
        if (TextUtils.isEmpty(volt)) {
            Snackbar.make(mView, "Voltage is not set", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        } else if (TextUtils.isEmpty(feed)) {
            feeder.setError(getString(R.string.required));
            return false;
        } else if (TextUtils.isEmpty(action)) {
            this.action.setError(getString(R.string.required));
            return false;
        } else if (TextUtils.isEmpty(remark)) {
            this.remark.setError(getString(R.string.required));
            return false;
        } else if (TextUtils.isEmpty(isolatedDt)) {
            this.isolatedDt.setError(getString(R.string.required));
            return false;
        } else if (TextUtils.isEmpty(natureFault)) {
            this.natureOfFault.setError(getString(R.string.required));
            return false;
        } else if (TextUtils.isEmpty(duration)) {
            this.duration.setError(getString(R.string.required));
            return false;
        } else if (TextUtils.isEmpty(timeFault)) {
            this.timeFault.setError(getString(R.string.required));
            return false;
        } else if (TextUtils.isEmpty(capacity_sp)) {
            Snackbar.make(mView, "capacity is not set", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        } else {
            this.feeder.setError(null);
            this.action.setError(null);
            this.remark.setError(null);
            this.isolatedDt.setError(null);
            this.natureOfFault.setError(null);
            this.duration.setError(null);
            this.timeFault.setError(null);
            return true;
        }
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
        fab.setVisibility(View.VISIBLE);

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
        fab.setVisibility(View.GONE);
        state = 1;


    }




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
        dummyContent.zone = args.zone;
        dummyContent.id = getTime();
        dummyContent.log = Contracts.PREVENTIVE;
        dummyContent.feeder = feeder.getText().toString().isEmpty()? null: feeder.getText().toString() ;
        dummyContent.actionTaken = this.action.getText().toString().isEmpty()? null : this.action.getText().toString();
        dummyContent.remarks = this.remark.getText().toString().isEmpty()? null : this.remark.getText().toString();
        dummyContent.isolatedDt = this.isolatedDt.getText().toString().isEmpty()? null : this.isolatedDt.getText().toString();
        dummyContent.detailsOfMaintenance = this.natureOfFault.getText().toString().isEmpty()? null : this.natureOfFault.getText().toString();
        dummyContent.durationOfOutage = this.duration.getText().toString().isEmpty()? null : this.duration.getText().toString();
        dummyContent.timeIn = this.timeFault.getText().toString().isEmpty()? null : this.timeFault.getText().toString();
        dummyContent.voltageLvl = voltageValue;
        dummyContent.capacity = capacityValue;
        return  dummyContent;
    }

    private void setValues(DummyContent dummyContent){

        feeder.setText(dummyContent.feeder.isEmpty() ? " ": dummyContent.feeder);
        action.setText(dummyContent.actionTaken.isEmpty()? " ":dummyContent.actionTaken);
        remark.setText(dummyContent.remarks.isEmpty()? " " : dummyContent.remarks);
        isolatedDt.setText(dummyContent.isolatedDt.isEmpty()? " " : dummyContent.isolatedDt);
        natureOfFault.setText(dummyContent.detailsOfMaintenance.isEmpty()? " " : dummyContent.detailsOfMaintenance);
        duration.setText(dummyContent.durationOfOutage.isEmpty()? " " : dummyContent.durationOfOutage);
        timeFault.setText(dummyContent.timeIn.isEmpty()? " " : dummyContent.timeIn);
        zone = dummyContent.zone.isEmpty()? args.zone : dummyContent.zone;


    }


    private void submit() {

        Snackbar.make( mView, R.string.warning_update, Snackbar.LENGTH_LONG)
                .setAction(R.string.yes, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DummyContent values = getValues();

                        if(validate(voltageValue, capacityValue,  feeder.getText().toString(),
                                action.getText().toString(), remark.getText().toString(),
                                isolatedDt.getText().toString(), natureOfFault.getText().toString(),
                                duration.getText().toString(),
                                timeFault.getText().toString())){

                            LiveData<Boolean> res = preventiveViewModel.getResponse();
                            res.observe(getViewLifecycleOwner(), result->{
                                if(result) {
                                    Log.d(TAG, "submit: "+ result);
                                    Snackbar.make( mView, R.string.submit, Snackbar.LENGTH_LONG)
                                            .setAction(R.string.ok, new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                }
                                            }).show();
                //                    SubmitFaultLogDirections.ActionSubmitFaultLogToFaultList actionValue =
                //                            SubmitFaultLogDirections.actionSubmitFaultLogToFaultList(zone).setZone(zone);
                //                    Navigation.findNavController(mView).navigate(actionValue);

                                }else {
                                    Snackbar.make( mView, R.string.unable, Snackbar.LENGTH_LONG)
                                            .setAction(R.string.ok, null).show();
                                }
                            });
                            preventiveViewModel.submitFaultLog(values);


                        }


                    }
                }).show();
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