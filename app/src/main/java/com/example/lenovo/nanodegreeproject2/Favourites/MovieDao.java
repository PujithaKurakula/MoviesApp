package com.example.lenovo.nanodegreeproject2.Favourites;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.lenovo.nanodegreeproject2.MovieModel;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert
    public void movie_title(MovieModel myEntity);
    @Update
    public void getMovieDetail(MovieModel myEntity);
    @Query("select * from  moviedetails")
    public LiveData<List<MovieModel>> getMovieInfoAll();
    @Delete
    public void movieDeleteInfo(MovieModel myEntity);
    @Query("select mid from movieDetails where mid=:favmov")
    public String readData(String favmov);
}

