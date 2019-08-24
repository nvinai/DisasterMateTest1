package com.example.calorietracker_v02;

import java.util.Date;

/**
 *
 * @author Nikhil V Paliath
 */

public class Appuser {

    private Integer userid;
    private String name;
    private String surname;
    private String email;
    private Date dob;
    private String gender;
    private String address;
    private Integer postcode;
    private int levelofactivity;
    private int stepspermile;
    private int height;
    private int weight;


    public Appuser(Integer userid)
    {
        this.userid = userid;
    }

    public Appuser(Integer userid, String name, String surname, String email, Date dob, String gender, int levelofactivity, int stepspermile, int height, int weight, String address,int postcode)
    {
        this.userid = userid;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.dob = dob;
        this.gender = gender;
        this.levelofactivity = levelofactivity;
        this.stepspermile = stepspermile;
        this.height = height;
        this.weight = weight;
        this.address = address;
        this.postcode = postcode;
    }

    public Integer getUserid()
    {
        return userid;
    }

    public void setUserid(Integer userid)
    {
        this.userid = userid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Date getDob()
    {
        return dob;
    }

    public void setDob(Date dob)
    {
        this.dob = dob;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public Integer getPostcode()
    {
        return postcode;
    }

    public void setPostcode(Integer postcode)
    {
        this.postcode = postcode;
    }

    public int getLevelofactivity()
    {
        return levelofactivity;
    }

    public void setLevelofactivity(int levelofactivity)
    {
        this.levelofactivity = levelofactivity;
    }

    public int getStepspermile()
    {
        return stepspermile;
    }

    public void setStepspermile(int stepspermile)
    {
        this.stepspermile = stepspermile;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public int getWeight()
    {
        return weight;
    }

    public void setWeight(int c)
    {
        this.weight = weight;
    }


    @Override
    public String toString()
    {
        return "FIT5046_Package.Appuser[ userid=" + userid + " ]";
    }

}
