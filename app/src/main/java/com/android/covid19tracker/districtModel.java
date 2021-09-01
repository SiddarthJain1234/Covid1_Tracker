package com.android.covid19tracker;

public class districtModel {

    private String district,cases,active,deaths,recovered;

    public districtModel(){

    }


    public districtModel(String district, String cases,  String deaths,  String recovered, String active) {
        this.district = district;
        this.cases = cases;
        //this.todayCases = todayCases;
        this.deaths = deaths;
        //this.todayDeaths = todayDeaths;
        this.recovered = recovered;
        this.active = active;
    }

    public String getdistrict() {
        return district;
    }

    public void setState(String state) {
        this.district = district;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }



    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }



    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
