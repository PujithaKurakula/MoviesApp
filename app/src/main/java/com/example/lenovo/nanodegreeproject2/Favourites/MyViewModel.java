package com.example.lenovo.nanodegreeproject2.Favourites;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.lenovo.nanodegreeproject2.MovieModel;

import java.util.List;

public class MyViewModel extends AndroidViewModel {
    public MyRepositery movieRepository;
    public LiveData<List<MovieModel>> getMovieData;
    


    public MyViewModel(@NonNull Application application) {
        super(application);
        movieRepository=new MyRepositery(application);
        getMovieData=movieRepository.getallMovies();
    }


    public void insertdata(MovieModel movieModel){
        movieRepository.insert(movieModel);
    }
    public void deletedata(MovieModel movieModel){
        movieRepository.delete(movieModel);
    }
    public void updatedata(MovieModel movieModel){
        movieRepository.update(movieModel);
    }

    public String movie_view(String movies){
        return movieRepository.fav_movies(movies);
    }

    public LiveData<List<MovieModel>> getAllMovieInformation(){
        return getMovieData;
    }

}
