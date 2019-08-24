package com.example.calorietracker_v02;

class Report {
    float totalCaloriesConsumed;
    float totalCaloriesBurned;
    float totalCaloriesLeft;
    String date;

    public Report(float totalCaloriesConsumed, float totalCaloriesBurned, float totalCaloriesLeft, String date) {
        this.totalCaloriesConsumed = totalCaloriesConsumed;
        this.totalCaloriesBurned = totalCaloriesBurned;
        this.date = date;
    }

    public Report() {
        this.totalCaloriesConsumed = 0;
        this.totalCaloriesBurned = 0;
        this.totalCaloriesLeft = 0;
    }

    public float getTotalCaloriesConsumed() {
        return totalCaloriesConsumed;
    }

    public void setTotalCaloriesConsumed(float totalCaloriesConsumed) {
        this.totalCaloriesConsumed = totalCaloriesConsumed;
    }

    public float getTotalCaloriesBurned() {
        return totalCaloriesBurned;
    }

    public void setTotalCaloriesBurned(float totalCaloriesBurned) {
        this.totalCaloriesBurned = totalCaloriesBurned;
    }

    public float getTotalCaloriesLeft() {
        return totalCaloriesLeft;
    }

    public void setTotalCaloriesLeft(float totalCaloriesLeft) {
        this.totalCaloriesLeft = totalCaloriesLeft;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}