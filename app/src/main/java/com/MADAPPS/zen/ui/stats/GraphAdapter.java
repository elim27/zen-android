package com.MADAPPS.zen.ui.stats;

public class GraphAdapter extends com.robinhood.spark.SparkAdapter {
    private float[] yData;

    public GraphAdapter(float[] yData){
        this.yData = yData;
    }

    @Override
    public int getCount() {
        return yData.length;
    }

    @Override
    public Object getItem(int index) {
        return yData[index];
    }

    @Override
    public float getY(int index) {
        return yData[index];
    }
}
