package com.example.lenovo.nanodegreeproject2;

import android.app.LoaderManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.lenovo.nanodegreeproject2.Favourites.MovieDataBase;
import com.example.lenovo.nanodegreeproject2.Favourites.MyViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    RecyclerView rv;
    ProgressBar progressBar;
    Button button;
    ArrayList<MovieModel> moviesearch;
    int flag = 0;
    SharedPreferences sp;
    Context context = this;
    List<MovieModel> movieModels;
    static MyViewModel viewModel;
    public static int Loader_ID1 = 10;
    public static int Loader_ID2 = 20;
    String movie_url;
    String popular_url = "https://api.themoviedb.org/3/movie/popular?api_key=e16b36d60e6c4cefd59c1940c8fa3aeb&language=en-US&page=1";
    String toprated_url = "https://api.themoviedb.org/3/movie/top_rated?api_key=e16b36d60e6c4cefd59c1940c8fa3aeb&language=en-US&page=1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.recyclerview);
        button = findViewById(R.id.button);
        movieModels = new ArrayList<>();
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        moviesearch = new ArrayList<MovieModel>();
        progressBar = findViewById(R.id.progress);

        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

           movie_url=popular_url;
           // getSupportLoaderManager().initLoader(Loader_ID1,null,this);
            getLoaderManager().initLoader(Loader_ID1,null,this);

            connected = true;
        } else {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle("Alert Box");
            builder1.setMessage("Oops!...No Internet Connection");
            builder1.setCancelable(false);

            builder1.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }

                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.toprated:

                boolean connected = false;
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                    movie_url=toprated_url;
                    button.setText(item.getTitle());
                    Toast.makeText(this, "You have selected toprated movies", Toast.LENGTH_SHORT).show();
                    //getSupportLoaderManager().restartLoader(Loader_ID2,null,this);
                    getLoaderManager().restartLoader(Loader_ID2,null,this);

                    connected = true;
                } else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    builder1.setTitle("Alert Box");
                    builder1.setMessage("Oops!...No Internet Connection");
                    builder1.setCancelable(false);

                    builder1.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }

                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();

                }
                break;
            case R.id.popular:

                 connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                    movie_url=popular_url;
                    button.setText(item.getTitle());
                    Toast.makeText(this, "you have selected popular movies", Toast.LENGTH_SHORT).show();
                   // getSupportLoaderManager().initLoader(Loader_ID1,null,this);
                    getLoaderManager().initLoader(Loader_ID1,null,this);

                    connected = true;
                } else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    builder1.setTitle("Alert Box");
                    builder1.setMessage("Oops!...No Internet Connection");
                    builder1.setCancelable(false);

                    builder1.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }

                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();

                }
                break;
            case R.id.favmov:
                button.setText(item.getTitle());
                viewModel.getAllMovieInformation().observe(this, new Observer<List<MovieModel>>() {
                    @Override
                    public void onChanged(@Nullable List<MovieModel> myEntities) {
                        rv.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                        rv.setAdapter(new MovieAdapter(MainActivity.this, (ArrayList<MovieModel>) myEntities));
                    }
                });
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<String>(this) {
            @Override
            public String loadInBackground() {
                try {
                    URL url = new URL(movie_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.connect();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    Scanner scanner = new Scanner(inputStream);
                    scanner.useDelimiter("\\A");
                    if (scanner.hasNext()) {
                        return scanner.next();
                    } else {
                        return null;
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                return null;
            }

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                forceLoad();
                progressBar.setVisibility(View.VISIBLE);
                moviesearch.clear();
                rv.setAdapter(new MovieAdapter(context,moviesearch));
            }


        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {



        progressBar.setVisibility(View.GONE);
        moviesearch.clear();

        if(data!=null){
            try {
                JSONObject jsonObject = new JSONObject(data);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject volume = jsonArray.getJSONObject(i);
                    String movietitle = volume.getString("title");
                    String moviedescription = volume.getString("overview");
                    String movieimage = volume.getString("poster_path");
                    String movieoriginal = volume.getString("original_title");
                    String moviedate = volume.getString("release_date");
                    String moviebackdrop = volume.getString("backdrop_path");
                    String movieid = volume.getString("id");
                    String movierating = volume.getString("vote_average");
                    moviesearch.add(new MovieModel(movietitle, movieoriginal, movieimage, moviedescription, moviedate, moviebackdrop, movieid, movierating));


                }
                rv.setLayoutManager(new GridLayoutManager(this,2));
                rv.setAdapter(new MovieAdapter(context, moviesearch));


            } catch (JSONException e) {
                e.printStackTrace();




            }

        }


    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

/*
    @NonNull
    @Override
    public android.support.v4.content.Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {

            return new android.support.v4.content.AsyncTaskLoader<String>(this) {
                @Nullable
                @Override
                public String loadInBackground() {
                    try {
                        URL url = new URL(movie_url);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.connect();
                        InputStream inputStream = httpURLConnection.getInputStream();
                        Scanner scanner = new Scanner(inputStream);
                        scanner.useDelimiter("\\A");
                        if (scanner.hasNext()) {
                            return scanner.next();
                        } else {
                            return null;
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    return null;
                }

                @Override
                protected void onStartLoading() {
                    super.onStartLoading();
                    forceLoad();
                    progressBar.setVisibility(View.VISIBLE);
                    moviesearch.clear();
                    rv.setAdapter(new MovieAdapter(context,moviesearch));
                }
            };

    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<String> loader, String s) {


        progressBar.setVisibility(View.GONE);
        moviesearch.clear();

        if(s!=null){
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject volume = jsonArray.getJSONObject(i);
                    String movietitle = volume.getString("title");
                    String moviedescription = volume.getString("overview");
                    String movieimage = volume.getString("poster_path");
                    String movieoriginal = volume.getString("original_title");
                    String moviedate = volume.getString("release_date");
                    String moviebackdrop = volume.getString("backdrop_path");
                    String movieid = volume.getString("id");
                    String movierating = volume.getString("vote_average");
                    moviesearch.add(new MovieModel(movietitle, movieoriginal, movieimage, moviedescription, moviedate, moviebackdrop, movieid, movierating));


                }
                rv.setLayoutManager(new GridLayoutManager(this,2));
                rv.setAdapter(new MovieAdapter(context, moviesearch));


            } catch (JSONException e) {
                e.printStackTrace();




            }

        }

    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<String> loader) {

    }


*/

}




