package com.example.lenovo.nanodegreeproject2;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "movieDetails")
public class MovieModel {

    String mtitle;
    String moriginal;
    String mdate;
    String mimage;
    String mdes;
    String mback;
    @PrimaryKey
    @NonNull
    String mid;
    String mrating;


    public MovieModel(){

    }



    public MovieModel(String movietitle, String movieoriginal, String movieimage, String moviedescription, String moviedate, String moviebackdrop, String movieid, String movierating) {
        this.mtitle=movietitle;
        this.moriginal=movieoriginal;
        this.mimage=movieimage;
        this.mdes=moviedescription;
        this.mdate=moviedate;
        this.mback=moviebackdrop;
        this.mid=movieid;
        this.mrating=movierating;




    }


    public String getMrating() {
        return mrating;
    }

    public void setMrating(String mrating) {
        this.mrating = mrating;
    }


    public String getMtitle() {
        return mtitle;
    }

    public void setMtitle(String mtitle) {
        this.mtitle = mtitle;
    }

    public String getMoriginal() {
        return moriginal;
    }

    public void setMoriginal(String moriginal) {
        this.moriginal = moriginal;
    }

    public String getMdate() {
        return mdate;
    }

    public void setMdate(String mdate) {
        this.mdate = mdate;
    }

    public String getMimage() {
        return mimage;
    }

    public void setMimage(String mimage) {
        this.mimage = mimage;
    }

    public String getMdes() {
        return mdes;
    }

    public void setMdes(String mdes) {
        this.mdes = mdes;
    }
    public String getMback() {
        return mback;
    }

    public void setMback(String mback) {
        this.mback = mback;
    }
    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }
}
