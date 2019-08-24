package com.example.calorietracker_v02;

import android.arch.persistence.room.Entity;

import java.io.Serializable;
import java.util.Date;

public class Consumption {
    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */

        private static final long serialVersionUID = 1L;
        private Integer cid;
        private Date date;
        private int servings;
        private Appuser userid;
        private Food foodid;

    public Consumption() {
    }

    public Consumption(Integer cid, Date date, int servings) {
        this.cid = cid;
        this.date = date;
        this.servings = servings;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public Appuser getUserid() {
        return userid;
    }

    public void setUserid(Appuser userid) {
        this.userid = userid;
    }

    public Food getFoodid() {
        return foodid;
    }

    public void setFoodid(Food foodid) {
        this.foodid = foodid;
    }
}


