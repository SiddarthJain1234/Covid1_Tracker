package com.android.covid19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TextView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class DetailActivity_District extends AppCompatActivity {

    private int positionDistrict;
    ScrollView scrollView;
    PieChart pieChart;
    TextView tvCases,tvRecovered,tvActive,tvTotalDeaths,tvdistrictName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_district);

        Intent intent=getIntent();
        positionDistrict=intent.getIntExtra("position",0);


        getSupportActionBar().setTitle("Details Of"+" "+Affected_Districts.districtModelList.get(positionDistrict).getdistrict());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        tvdistrictName=findViewById(R.id.districtname);
        tvCases = findViewById(R.id.districtcases);
        tvRecovered = findViewById(R.id.districtrecovered);
        tvActive = findViewById(R.id.districtactive);
        tvTotalDeaths = findViewById(R.id.districtdeaths);
        scrollView = findViewById(R.id.districtscroll);
        pieChart = findViewById(R.id.districtpiechart);


        tvdistrictName.setText(Affected_Districts.districtModelList.get(positionDistrict).getdistrict());
        tvCases.setText(Affected_Districts.districtModelList.get(positionDistrict).getCases());
        tvRecovered.setText(Affected_Districts.districtModelList.get(positionDistrict).getRecovered());
        //tvCritical.setText(AffectedCountries.countryModelsList.get(positionCountry).getCritical());
        tvActive.setText(Affected_Districts.districtModelList.get(positionDistrict).getActive());
        tvTotalDeaths.setText(Affected_Districts.districtModelList.get(positionDistrict).getDeaths());


        pieChart.addPieSlice(new PieModel("Recoverd",Integer.parseInt(tvRecovered.getText().toString()), Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(new PieModel("Deaths",Integer.parseInt(tvTotalDeaths.getText().toString()), Color.parseColor("#EF5350")));
        pieChart.addPieSlice(new PieModel("Active",Integer.parseInt(tvActive.getText().toString()), Color.parseColor("#29B6F6")));
        pieChart.startAnimation();

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

}