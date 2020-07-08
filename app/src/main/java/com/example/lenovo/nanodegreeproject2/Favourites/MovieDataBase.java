package com.example.lenovo.nanodegreeproject2.Favourites;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.lenovo.nanodegreeproject2.MovieModel;

@Database(entities =MovieModel.class,version = 1)
public abstract class MovieDataBase extends RoomDatabase {
    public abstract MovieDao movieDao();

    public static MovieDataBase movieDataBase;

    public static MovieDataBase getmoviedetails(Context context) {
        if (movieDataBase == null) {
            movieDataBase = Room.databaseBuilder(context, MovieDataBase.class, "movie").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return movieDataBase;

    }
}
