package com.example.web_app.model;

public class Marks {
    private double avg;
    private double max;
    private double min;
    private double median;

    public Marks(double avg, double max, double min, double median) {
        this.avg = avg;
        this.max = max;
        this.min = min;
        this.median = median;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMedian() {
        return median;
    }

    public void setMedian(double median) {
        this.median = median;
    }
}
