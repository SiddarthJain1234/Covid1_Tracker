package com.android.covid19tracker;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class myCustomAdapter extends ArrayAdapter<stateModel> {

    private Context context;
    private List<stateModel> stateModelList;
    private List<stateModel> stateModelListFiltered;

    public myCustomAdapter(Context context, List<stateModel> stateModelList){
        super(context, R.layout.list_custom_item,stateModelList);
        this.context=context;
        this.stateModelList=stateModelList;
        this.stateModelListFiltered=stateModelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_custom_item,null,true);
        TextView tvStateName=view.findViewById(R.id.tvStateName);

        tvStateName.setText(stateModelListFiltered.get(position).getState());


        return view;
    }

    @Override
    public int getCount() {
        return stateModelListFiltered.size();
    }

    @Nullable
    @Override
    public stateModel getItem(int position) {
        return stateModelListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = stateModelList.size();
                    filterResults.values = stateModelList;

                }else{
                    List<stateModel> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(stateModel itemsModel:stateModelList){
                        if(itemsModel.getState().toLowerCase().contains(searchStr)){
                            resultsModel.add(itemsModel);

                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }


                }

                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                stateModelListFiltered = (List<stateModel>) results.values;
                Affected_States.stateModelList = (List<stateModel>) results.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }
}
