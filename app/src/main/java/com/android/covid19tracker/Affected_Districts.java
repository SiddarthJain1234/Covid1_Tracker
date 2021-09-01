package com.android.covid19tracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Affected_Districts extends AppCompatActivity {
    ProgressDialog progressBar;
    EditText edtSearch;
    ListView listView;
    private RequestQueue requestQueue;
    String stateName;

    public static List<districtModel> districtModelList = new ArrayList<>();
    districtModel districtmodel;
    myCustomAdapterDistrict myCustomAdapterDistrict;

    @Override
    protected void onCreate(Bundle savedInstancedistrict) {
        super.onCreate(savedInstancedistrict);
        setContentView(R.layout.activity_affected_list);

        Intent intent = getIntent();
        stateName = intent.getStringExtra("statename");


        edtSearch = findViewById(R.id.edtSearch);
        listView = findViewById(R.id.listView);
        progressBar = new ProgressDialog(this);

        requestQueue = Volley.newRequestQueue(this);

        getSupportActionBar().setTitle("Districts"+" of "+stateName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        fetchData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(), DetailActivity_District.class).putExtra("position", position));
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                myCustomAdapterDistrict.getFilter().filter(s);
                myCustomAdapterDistrict.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();}
        return super.onOptionsItemSelected(item);
    }






    private void fetchData() {
        progressBar.show();
        progressBar.setContentView(R.layout.progress_dialog);
        progressBar.getWindow().setBackgroundDrawableResource(android.R.color.white);



        String dataURL = "https://data.covid19india.org/v2/state_district_wise.json";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, dataURL, null, new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray jsonArray) {
                try {
                    districtModelList.clear();
                    for (int i = 1; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        if (stateName.toLowerCase().equals(jsonObject.getString("state").toLowerCase())) {
                            JSONArray jsonArray2 = jsonObject.getJSONArray("districtData");
                            for (int j = 0; j < jsonArray2.length(); j++) {
                                JSONObject jsonObject2 = jsonArray2.getJSONObject(j);
                                String districtName = jsonObject2.getString("district");
                                String districtConfirmed = jsonObject2.getString("confirmed");
                                String districtActive = jsonObject2.getString("active");
                                String districtDeceased = jsonObject2.getString("deceased");
                                String districtRecovered = jsonObject2.getString("recovered");
                                Log.d("data", districtName+" "+districtConfirmed+" "+districtActive+" "+districtDeceased+" "+districtRecovered);

                                districtModelList.add(new districtModel(districtName,districtConfirmed,districtDeceased,districtRecovered,districtActive));
                            }
                        }

                    }
                    Runnable progressRunnable = new Runnable() {
                        @Override
                        public void run() {
                            myCustomAdapterDistrict = new myCustomAdapterDistrict(Affected_Districts.this,districtModelList);
                            listView.setAdapter(myCustomAdapterDistrict);
                            progressBar.dismiss();

                            //districtAdapter.setOnItemClickListner(DistrictwiseDataActivity.this);
                        }
                    };
                    Handler pdCanceller = new Handler();
                    pdCanceller.postDelayed(progressRunnable, 500);
                    //confirmation = 1;

                }

                catch(JSONException e) {
                    e.printStackTrace();
                    progressBar.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
               /* simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);*/
                Toast.makeText(Affected_Districts.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonArrayRequest);

    }
}