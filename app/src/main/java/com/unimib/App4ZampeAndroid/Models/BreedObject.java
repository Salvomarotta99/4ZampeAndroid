package com.unimib.App4ZampeAndroid.Models;

public class BreedObject {

    public String imperial;
    public String metric;


    public String getImperial() {
        return imperial;
    }

    public void setImperial(String imperial) {
        this.imperial = imperial;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }


    @Override
    public String toString() {
        return "BreedObject{" +
                "imperial='" + imperial + '\'' +
                ", metric='" + metric + '\'' +
                '}';
    }
}
