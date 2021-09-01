package com.android.covid19tracker;

import android.content.Context;
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

public class myCustomAdapterDistrict extends ArrayAdapter<districtModel> {

    private Context context;
    private List<districtModel> districtModelList;
    private List<districtModel> districtModelListFiltered;

    public myCustomAdapterDistrict(Context context, List<districtModel> districtModelList){
        super(context, R.layout.list_custom_item,districtModelList);
        this.context=context;
        this.districtModelList=districtModelList;
        this.districtModelListFiltered=districtModelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_custom_item,null,true);
        TextView tvdistrictName=view.findViewById(R.id.tvName);

        tvdistrictName.setText(districtModelListFiltered.get(position).getdistrict());


        return view;
    }

    @Override
    public int getCount() {
        return districtModelListFiltered.size();
    }

    @Nullable
    @Override
    public districtModel getItem(int position) {
        return districtModelListFiltered.get(position);
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
                    filterResults.count = districtModelList.size();
                    filterResults.values = districtModelList;

                }else{
                    List<districtModel> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(districtModel itemsModel:districtModelList){
                        if(itemsModel.getdistrict().toLowerCase().contains(searchStr)){
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

                districtModelListFiltered = (List<districtModel>) results.values;
                Affected_Districts.districtModelList = (List<districtModel>) results.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }

}
