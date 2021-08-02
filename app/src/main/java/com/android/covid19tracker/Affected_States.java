package com.android.covid19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Affected_States extends AppCompatActivity {
    ProgressDialog progressBar;
    EditText edtSearch;
    ListView listView;


    public static List<stateModel> stateModelList = new ArrayList<>();
    stateModel stateModel;
    myCustomAdapter myCustomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affected_states);

        edtSearch = findViewById(R.id.edtSearch);
        listView = findViewById(R.id.listView);
        progressBar = new ProgressDialog(this);

        getSupportActionBar().setTitle("Affected States");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fetchData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(),DetailActivity.class).putExtra("position",position));
            }
        });




        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                myCustomAdapter.getFilter().filter(s);
                myCustomAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }

   /* @Override
    protected void onPause() {
        stateModelList=new ArrayList<>();
        super.onPause();
    }

    @Override
    protected void onStop() {
        stateModelList=new ArrayList<>();
        // trimCache(getApplicationContext());
        super.onStop();
    }*/


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();}
        return super.onOptionsItemSelected(item);
    }

    private void fetchData() {

        String url  = "https://disease.sh/v3/covid-19/gov/india";

       /* simpleArcLoader.start();*/
        progressBar.show();
        progressBar.setContentView(R.layout.progress_dialog);
        progressBar.getWindow().setBackgroundDrawableResource(android.R.color.white);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject1=new JSONObject(response);

                            JSONArray jsonArray=jsonObject1.getJSONArray("states");
                            //Log.d("sizeofarray", String.valueOf(jsonArray.length()));
                            for(int i=0;i<jsonArray.length();i++){

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String stateName = jsonObject.getString("state");
                                String cases = jsonObject.getString("cases");
                                String todayCases = jsonObject.getString("todayCases");
                                String deaths = jsonObject.getString("deaths");
                                String todayDeaths = jsonObject.getString("todayDeaths");
                                String recovered = jsonObject.getString("recovered");
                                String active = jsonObject.getString("active");
                                /*String critical = jsonObject.getString("critical");*/

                                /*JSONObject object = jsonObject.getJSONObject("countryInfo");
                                String flagUrl = object.getString("flag");
*/
                                stateModel = new stateModel(stateName,cases,todayCases,deaths,todayDeaths,recovered,active);
                                stateModelList.add(stateModel);

                            }

                            myCustomAdapter = new myCustomAdapter(Affected_States.this,stateModelList);
                            listView.setAdapter(myCustomAdapter);
                            progressBar.dismiss();
                            /*simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);
*/





                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressBar.dismiss();
                            /*simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);*/
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
               /* simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);*/
                Toast.makeText(Affected_States.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        //Log.d("sizeofstatemodel", String.valueOf(stateModelList.size()));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }
}