package com.example.faultlog.ui;

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

import com.example.faultlog.BreakdownViewModel;
import com.example.faultlog.Contracts;
import com.example.faultlog.R;
import com.example.faultlog.dummy.DummyContent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BreakdownEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BreakdownEditFragment extends Fragment implements AdapterView.OnItemSelectedListener  {

    EditText feeder, isolatedDt, natureOfFault,
            action, duration, remark, timeFault, dateRestore;
    Spinner capacity, voltage ;

    private String capacityValue;
    private String voltageValue;
    private String zone;
    DummyContent args;

    private static int state = 0;

    private View mView;

    private static final String TAG = "BreakdownEditFragment";
    private BreakdownViewModel breakdownViewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FloatingActionButton fab;

    public BreakdownEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BreakdownEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BreakdownEditFragment newInstance(String param1, String param2) {
        BreakdownEditFragment fragment = new BreakdownEditFragment();
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
        breakdownViewModel =  new ViewModelProvider(getActivity()).get(BreakdownViewModel.class);
        mView = inflater.inflate(R.layout.fragment_breakdown_edit, container, false);

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        args = getArguments().getParcelable(Contracts.BUNDLETOVIEWLOG);

        NavController navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(R.id.breakdownEditFragment).build();
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

        Log.d(TAG, "initialize: " + args.feeder);
        voltage =v.findViewById(R.id.voltage);
        capacity = v.findViewById(R.id.capacity);
        feeder = v.findViewById(R.id.feeder_sub);
        action = v.findViewById(R.id.action);
        remark = v.findViewById(R.id.remark);
        isolatedDt = v.findViewById(R.id.isolateddt);
        natureOfFault = v.findViewById(R.id.details);
        duration = v.findViewById(R.id.duration_of_outage);
        timeFault = v.findViewById(R.id.date_of_occurr);
        dateRestore = v.findViewById(R.id.duration_of_);
        setupSpinner(v.getContext(), R.array.ratings, android.R.layout.simple_spinner_item, capacity);
        setupSpinner(v.getContext(), R.array.voltage, android.R.layout.simple_spinner_item, voltage);

        voltage.setOnItemSelectedListener(this);
        capacity.setOnItemSelectedListener(this);

        setValues(args);

        fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(view ->  {

            Log.d(TAG, "initialize: fab clicked");
            submit();
        });

        disable();

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

    private void setValues(DummyContent dummyContent){
        feeder.setText(dummyContent.feeder.isEmpty()|| dummyContent.feeder.equals(" ") ? null: dummyContent.feeder);
        action.setText(dummyContent.actionTaken.isEmpty()? null:dummyContent.actionTaken);
        remark.setText(dummyContent.remarks.isEmpty()? null : dummyContent.remarks);
        isolatedDt.setText(dummyContent.isolatedDt.isEmpty()? null : dummyContent.isolatedDt);
        natureOfFault.setText(dummyContent.detailsOfMaintenance.isEmpty()? null : dummyContent.detailsOfMaintenance);
        duration.setText(dummyContent.durationOfOutage.isEmpty()? null : dummyContent.durationOfOutage);
        timeFault.setText(dummyContent.timeIn.isEmpty()? null : dummyContent.timeIn);
        dateRestore.setText(dummyContent.restorationDate.isEmpty()? null : dummyContent.restorationDate);
        zone = dummyContent.zone;

    }

    private boolean validate(String volt, String capacity_sp, String feed,
                             String action, String remark, String isolatedDt, String natureFault,
                             String duration, String timeFault, String restore){
        if(TextUtils.isEmpty(volt)){
            Snackbar.make( mView, "Voltage is not set", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }else if(TextUtils.isEmpty(feed)){
            feeder.setError(getString(R.string.required));
            return false;
        }else if(TextUtils.isEmpty(action)){
            this.action.setError(getString(R.string.required));
            return false;
        }else if(TextUtils.isEmpty(remark)){
            this.remark.setError(getString(R.string.required));
            return false;
        }else if(TextUtils.isEmpty(isolatedDt)){
            this.isolatedDt.setError(getString(R.string.required));
            return false;
        }else if(TextUtils.isEmpty(restore)){
            this.dateRestore.setError(getString(R.string.required));
            return false;
        }else if(TextUtils.isEmpty(natureFault)){
            this.natureOfFault.setError(getString(R.string.required));
            return false;
        }else if(TextUtils.isEmpty(duration)){
            this.duration.setError(getString(R.string.required));
            return false;
        }else if(TextUtils.isEmpty(timeFault)){
            this.timeFault.setError(getString(R.string.required));
            return false;
        }else if(TextUtils.isEmpty(capacity_sp)){
            Snackbar.make( mView, "capacity is not set", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }else {
            this.feeder.setError(null);
            this.action.setError(null);
            this.remark.setError(null);
            this.isolatedDt.setError(null);
            this.natureOfFault.setError(null);
            this.duration.setError(null);
            this.timeFault.setError(null);
            this.dateRestore.setError(null);
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
        dateRestore.setEnabled(true);
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
        dateRestore.setEnabled(false);
        fab.setVisibility(View.GONE);
        state = 1;


    }



    private DummyContent getValues(){
        DummyContent dummyContent = new DummyContent();
        dummyContent.log = Contracts.BREAKDOWN;
        dummyContent.feeder = feeder.getText().toString().isEmpty()? " ": feeder.getText().toString() ;
        dummyContent.actionTaken = this.action.getText().toString().isEmpty()? " " : this.action.getText().toString();
        dummyContent.remarks = this.remark.getText().toString().isEmpty()? " " : this.remark.getText().toString();
        dummyContent.isolatedDt = this.isolatedDt.getText().toString().isEmpty()? " " : this.isolatedDt.getText().toString();
        dummyContent.detailsOfMaintenance = this.natureOfFault.getText().toString().isEmpty()? " " : this.natureOfFault.getText().toString();
        dummyContent.durationOfOutage = this.duration.getText().toString().isEmpty()? " " : this.duration.getText().toString();
        dummyContent.timeIn = this.timeFault.getText().toString().isEmpty()? " " : this.timeFault.getText().toString();
        dummyContent.voltageLvl = voltageValue;
        dummyContent.capacity = capacityValue;
        dummyContent.zone = args.zone;
        dummyContent.restorationDate = this.dateRestore.getText().toString().isEmpty()? " " : this.dateRestore.getText().toString();
        dummyContent.id = args.id;

        return  dummyContent;
    }

    private void submit() {

        Snackbar.make( mView, R.string.warning_update, Snackbar.LENGTH_LONG)
                .setAction(R.string.yes, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        DummyContent values = getValues();
                        Log.d(TAG, "submit: " + zone +" " + values.feeder + " " + values.zone + " " + values.log);

                        Log.d(TAG, "submit: ");
                        if(validate(voltageValue, capacityValue,  feeder.getText().toString(),
                                action.getText().toString(), remark.getText().toString(),
                                isolatedDt.getText().toString(), natureOfFault.getText().toString(),
                                duration.getText().toString(),
                                timeFault.getText().toString(),
                                dateRestore.getText().toString())){

                            Log.d(TAG, "submit: 2");
                            LiveData<Boolean> res = breakdownViewModel.getResponse();
                            res.observe(getViewLifecycleOwner(), result->{
                                if(result) {
                                    Log.d(TAG, "submit: "+ result);
                                    Snackbar.make( mView, R.string.update, Snackbar.LENGTH_LONG)
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
                            breakdownViewModel.updateFaultLog(values);



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