package com.example.faultlog;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.faultlog.dummy.DummyContent;


import java.util.List;


public class MyFaultItemRecyclerViewAdapter extends RecyclerView.Adapter<MyFaultItemRecyclerViewAdapter.ViewHolder> {

    private final List<DummyContent> mValues;
    private static final String TAG = "MyFaultItemRecyclerView";
    private Context mContext;

    public MyFaultItemRecyclerViewAdapter(List<DummyContent> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_fault_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.feeder.setText(mValues.get(position).feeder);
        holder.summary.setText(mValues.get(position).detailsOfMaintenance);
//        holder.duration.setText(mValues.get(position).durationOfOutage);
        holder.isolatedDt.setText(mValues.get(position).isolatedDt);
        holder.time.setText(mValues.get(position).id);
        Log.d(TAG, "onBindViewHolder: " + mValues.get(position).feeder);
        holder.onClick();

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView feeder;
        public final TextView summary;
        public final TextView time;
//        public final TextView duration;
        public final TextView isolatedDt;
        public DummyContent mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            feeder = (TextView) view.findViewById(R.id.feeder_sub);
            summary = (TextView) view.findViewById(R.id.summary);
//            duration = (TextView) view.findViewById(R.id.duration);
            time = (TextView) view.findViewById(R.id.timeStamp);
            isolatedDt = (TextView) view.findViewById(R.id.isolated_name);

        }
            public void onClick(){
            mView.setOnClickListener(view ->{
                Bundle bundle = new Bundle();
                bundle.putParcelable(Contracts.BUNDLETOVIEWLOG, mItem);
                switch (mItem.log){
                    case Contracts.BREAKDOWN:
                        Log.d(TAG, "onClick: breakdown");
                        Navigation.findNavController(mView).navigate(R.id.breakdownEditFragment, bundle);
                        break;
                    case Contracts.PREVENTIVE:
                        Log.d(TAG, "onClick: preventive");
                        Navigation.findNavController(mView).navigate(R.id.preventiveEditFragment, bundle);
                        break;
                    case Contracts.TRANSFORMER:
                        Log.d(TAG, "onClick: transformer");
                        Navigation.findNavController(mView).navigate(R.id.transformerEditFragment, bundle);
                        break;

                }

            });
            }



        @Override
        public String toString() {
            return super.toString() + " '" + summary.getText() + "'";
        }
    }
}