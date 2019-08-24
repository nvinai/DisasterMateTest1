package com.example.calorietracker_v02;


import java.util.Date;

/**
 *
 * @author Nikhil V Paliath
 */
public class Credential
{

    private static final long serialVersionUID = 1L;
    private Integer crid;
    private String username;
    private String password;
    private Date signupdate;
    private Appuser userid;


    public Credential(Integer crid, String username, String password, Date signupdate,Appuser userid)
    {
        this.crid = crid;
        this.username = username;
        this.password = password;
        this.signupdate = signupdate;
        this.userid = userid;
    }

    public Integer getCrid()
    {
        return crid;
    }

    public void setCrid(Integer crid)
    {
        this.crid = crid;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Date getSignupdate()
    {
        return signupdate;
    }

    public void setSignupdate(Date signupdate)
    {
        this.signupdate = signupdate;
    }

    public Appuser getUserid()
    {
        return userid;
    }

    public void setUserid(Appuser userid)
    {
        this.userid = userid;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (crid != null ? crid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof Credential))
        {
            return false;
        }
        Credential other = (Credential) object;
        if ((this.crid == null && other.crid != null) || (this.crid != null && !this.crid.equals(other.crid)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "FIT5046_Package.Credential[ crid=" + crid + " ]";
    }

}
