package com.example.faultlog;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

public class FirstFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.agbara).setOnClickListener(this);
        view.findViewById(R.id.aiyetoro).setOnClickListener(this);
        view.findViewById(R.id.oko_afo).setOnClickListener(this);
        view.findViewById(R.id.igborosun).setOnClickListener(this);
        view.findViewById(R.id.aradagun).setOnClickListener(this);
        view.findViewById(R.id.badagry).setOnClickListener(this);
        view.findViewById(R.id.om).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
//        FirstFragmentDirections.ActionFirstFragmentToFaultList action ;
        Intent i = new Intent(getActivity(), FormActivity.class);
        switch (v.getId()){
            case R.id.agbara:
//                 action =
//                        FirstFragmentDirections.actionFirstFragmentToFaultList("agbara");
//                action.setZone("agbara");
//                Navigation.findNavController(v).navigate(action);

                i.putExtra(Contracts.ZONE_ARGUMENT, Contracts.AGBARA);
                startActivity(i);

                break;
            case R.id.aiyetoro:
//                action =
//                        FirstFragmentDirections.actionFirstFragmentToFaultList("aiyetoro");
//                action.setZone("aiyetoro");
//                Navigation.findNavController(v).navigate(action);

                i.putExtra(Contracts.ZONE_ARGUMENT, Contracts.AIYETORO);
                startActivity(i);

                break;
            case R.id.oko_afo:
//                 action =
//                        FirstFragmentDirections.actionFirstFragmentToFaultList("okoAfo");
//                action.setZone("okoAfo");
//                Navigation.findNavController(v).navigate(action);

                i.putExtra(Contracts.ZONE_ARGUMENT, Contracts.OKOAFO);
                startActivity(i);
                break;
            case R.id.igborosun:
//                 action =
//                        FirstFragmentDirections.actionFirstFragmentToFaultList("igborosun");
//                action.setZone("igborosun");
//                Navigation.findNavController(v).navigate(action);

                i.putExtra(Contracts.ZONE_ARGUMENT, Contracts.IGBOROSUN);
                startActivity(i);
                break;
            case R.id.aradagun:
//                action =
//                        FirstFragmentDirections.actionFirstFragmentToFaultList("aradagun");
//                action.setZone("aradagun");
//                Navigation.findNavController(v).navigate(action);

                i.putExtra(Contracts.ZONE_ARGUMENT, Contracts.ARADAGUN);
                startActivity(i);
                break;
            case R.id.badagry:
//                action =
//                        FirstFragmentDirections.actionFirstFragmentToFaultList("badagry");
//                action.setZone("badagry");
//                Navigation.findNavController(v).navigate(action);

                i.putExtra(Contracts.ZONE_ARGUMENT, Contracts.BADAGRY);
                startActivity(i);
                break;
            case R.id.om:
//                action =
//                        FirstFragmentDirections.actionFirstFragmentToFaultList("om");
//                action.setZone("om");
//                Navigation.findNavController(v).navigate(action);

                i.putExtra(Contracts.ZONE_ARGUMENT, Contracts.OM);
                startActivity(i);
                break;
        }
    }
}