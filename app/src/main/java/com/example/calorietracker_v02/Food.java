package com.example.calorietracker_v02;


import android.arch.persistence.room.Entity;


import java.io.Serializable;

import java.math.BigDecimal;

    /**
     *
     * @author Nikhil V Paliath
     */
    public class Food
    {

        private static final long serialVersionUID = 1L;
        private Integer foodid;
        private String fname;
        private String servingunit;
        private String category;
        private BigDecimal servingamount;
        private double calorieamount;
        private BigDecimal fat;
        private Consumption consumption;

        public Food(Integer foodid, String fname, String servingunit, String category, BigDecimal servingamount, double calorieamount, BigDecimal fat) {
            this.foodid = foodid;
            this.fname = fname;
            this.servingunit = servingunit;
            this.category = category;
            this.servingamount = servingamount;
            this.calorieamount = calorieamount;
            this.fat = fat;
        }


    }
