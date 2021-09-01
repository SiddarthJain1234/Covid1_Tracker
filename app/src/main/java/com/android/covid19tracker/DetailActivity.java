package com.android.covid19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class DetailActivity extends AppCompatActivity {

    private int positionState;
    ScrollView scrollView;
    PieChart pieChart;
    TextView tvCases,tvRecovered,tvActive,tvTodayCases,tvTotalDeaths,tvTodayDeaths,tvstateName;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent=getIntent();
        positionState=intent.getIntExtra("position",0);

        getSupportActionBar().setTitle("Details Of"+" "+Affected_States.stateModelList.get(positionState).getState());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);




        tvstateName=findViewById(R.id.tvName);
        tvCases = findViewById(R.id.tvCases);
        tvRecovered = findViewById(R.id.tvRecovered);
        //tvCritical = findViewById(R.id.tvCritical);
        tvActive = findViewById(R.id.tvActive);
        tvTodayCases = findViewById(R.id.tvTodayCases);
        tvTotalDeaths = findViewById(R.id.tvTotalDeaths);
        tvTodayDeaths = findViewById(R.id.tvTodayDeaths);
        //tvTests = findViewById(R.id.tvTests);
        scrollView = findViewById(R.id.scrollStats);
        pieChart = findViewById(R.id.piechart);
        btn=findViewById(R.id.btnTrack);

        tvstateName.setText(Affected_States.stateModelList.get(positionState).getState());
        tvCases.setText(Affected_States.stateModelList.get(positionState).getCases());
        tvRecovered.setText(Affected_States.stateModelList.get(positionState).getRecovered());
        //tvCritical.setText(AffectedCountries.countryModelsList.get(positionCountry).getCritical());
        tvActive.setText(Affected_States.stateModelList.get(positionState).getActive());
        tvTodayCases.setText(Affected_States.stateModelList.get(positionState).getTodayCases());
        tvTotalDeaths.setText(Affected_States.stateModelList.get(positionState).getDeaths());
        tvTodayDeaths.setText(Affected_States.stateModelList.get(positionState).getTodayDeaths());


        //pieChart.addPieSlice(new PieModel("Cases",Integer.parseInt(tvCases.getText().toString()), Color.parseColor("#FFA726")));
        pieChart.addPieSlice(new PieModel("Recoverd",Integer.parseInt(tvRecovered.getText().toString()), Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(new PieModel("Deaths",Integer.parseInt(tvTotalDeaths.getText().toString()), Color.parseColor("#EF5350")));
        pieChart.addPieSlice(new PieModel("Active",Integer.parseInt(tvActive.getText().toString()), Color.parseColor("#29B6F6")));
        pieChart.startAnimation();





        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Affected_Districts.class).putExtra("statename",Affected_States.stateModelList.get(positionState).getState()));
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }





}