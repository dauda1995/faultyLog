package com.example.faultlog;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavArgument;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class FormActivity extends AppCompatActivity {

    private static final String TAG = "FormActivity";


    public DetailsViewModel detailsViewModel;
    public BreakdownViewModel breakdownViewModel;
    public TransformerViewModel transformerViewModel;
    public PreventiveViewModel preventiveViewModel;
    public FaultListViewModel faultListViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_content);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        String zone = getIntent().getExtras().get(Contracts.ZONE_ARGUMENT).toString();

        faultListViewModel = new ViewModelProvider(this).get(FaultListViewModel.class);
        detailsViewModel = new ViewModelProvider(this).get(DetailsViewModel.class);
        breakdownViewModel = new ViewModelProvider(this).get(BreakdownViewModel.class);
        transformerViewModel = new ViewModelProvider(this).get(TransformerViewModel.class);
        preventiveViewModel = new ViewModelProvider(this).get(PreventiveViewModel.class);
        detailsViewModel.zone = zone;
        Log.d(TAG, "onCreate: formactivity " + detailsViewModel.getZone());


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.submitFaultLog2, R.id.transformerFragment2, R.id.preventiveFragment2)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment2);

        NavigationUI.setupWithNavController(navView, navController);

//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                switch(destination.getId()){
//                case  R.id.faultList2:
//                navView.setVisibility(View.GONE);
////                argments.putString()
                    case R.id.submitFaultLog2:
                        navView.setVisibility(View.VISIBLE);
                        return;
                    case R.id.transformerFragment2:
                        navView.setVisibility(View.VISIBLE);
                        return;
                    case R.id.preventiveFragment2:
                        navView.setVisibility(View.VISIBLE);

                        return;
                    default:
                        navView.setVisibility(View.GONE);


                }

        }


        });

    }


}

