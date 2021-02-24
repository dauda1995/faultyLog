package com.example.faultlog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.faultlog.dummy.DummyContent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

/**
 * A fragment representing a list of Items.
 */
public class FaultList extends Fragment {

    View mView;
    private FaultListViewModel faultListViewModel;
    public static String ZONE_ARGS = "zone";

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FaultList() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static FaultList newInstance(int columnCount) {
        FaultList fragment = new FaultList();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        faultListViewModel =  new ViewModelProvider(getActivity()).get(FaultListViewModel.class);


        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fault_list_list, container, false);
        // Set the adapter
        mView = view;
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        String zone = FaultListArgs.fromBundle(getArguments()).getZone();
        String logType = FaultListArgs.fromBundle(getArguments()).getLogType();

        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }

        faultListViewModel.getFaultLog(zone, logType).observe(getViewLifecycleOwner(), model ->{
            recyclerView.setAdapter(new MyFaultItemRecyclerViewAdapter(model));
        });


//        FloatingActionButton fab = view.findViewById(R.id.fab);
//        fab.setOnClickListener(v ->  {
//
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
////                FaultListDirections.ActionFaultListToSubmitFaultLog action =
////                        FaultListDirections.actionFaultListToSubmitFaultLog(zone).setZone(zone);
////                Navigation.findNavController(view).navigate(action);
////            Intent i = new Intent(getActivity(), FormActivity.class);
////            i.putExtra(ZONE_ARGS, zone);
////            startActivity(i);
//        });

    }


}