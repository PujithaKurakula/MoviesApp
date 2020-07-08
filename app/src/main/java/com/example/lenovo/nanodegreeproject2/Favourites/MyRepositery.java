package com.example.lenovo.nanodegreeproject2.Favourites;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import com.example.lenovo.nanodegreeproject2.MovieModel;

import java.util.List;

public class MyRepositery

{
    public static MovieDao myDao;
    LiveData<List<MovieModel>> getalldata;

    public MyRepositery(Context context){
        MovieDataBase myDataBase=MovieDataBase.getmoviedetails(context);
        myDao=myDataBase.movieDao();
        getalldata=myDao.getMovieInfoAll();


    }
    LiveData<List<MovieModel>> getallMovies(){
        return getalldata;
    }



    public void insert(MovieModel myEntity){
        new InsertTask().execute(myEntity);
    }

    public class InsertTask extends AsyncTask<MovieModel,Void,Void> {

        @Override
        protected Void doInBackground(MovieModel... myEntities) {
            myDao.movie_title(myEntities[0]);
            return null;
        }
    }
    public void update(MovieModel myEntity){
        new updateTask(myDao).execute(myEntity);
    }
    public class updateTask extends AsyncTask<MovieModel,Void,Void>{
        private MovieDao dao;
        public updateTask(MovieDao mydao){
            dao=mydao;
        }

        @Override
        protected Void doInBackground(MovieModel... myEntities) {
            myDao.getMovieDetail(myEntities[0]);
            return null;
        }
    }
    public void delete(MovieModel myEntity){
        new deleteTask().execute(myEntity);
    }
    public class deleteTask extends AsyncTask<MovieModel,Void,Void> {

        @Override
        protected Void doInBackground(MovieModel... myEntities) {
            myDao.movieDeleteInfo(myEntities[0]);
            return null;
        }
    }
    public String fav_movies(String sid)
    {
        return myDao.readData(sid);

    }

}
