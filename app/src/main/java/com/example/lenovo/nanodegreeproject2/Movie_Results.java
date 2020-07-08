package com.example.lenovo.nanodegreeproject2;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteConstraintException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.nanodegreeproject2.Favourites.MyViewModel;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Movie_Results extends AppCompatActivity {

    ImageView iv, img;
    TextView  tv2, tv3,rb, tv4,review;
    MaterialFavoriteButton mfb;
    String mid;
    String[] s;
    RecyclerView rtra,rrev;
    ArrayList<TraliorModel> trailerModel;
    ArrayList<ReviewModel> reviewModel;
    MyViewModel myViewModel;
    List<MovieModel> movieModels;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie__results);
        iv = findViewById(R.id.imageview);
        tv2 = findViewById(R.id.originaltitle);
        img = findViewById(R.id.backview);
        tv3 = findViewById(R.id.rdate);
        tv4 = findViewById(R.id.info);
        rb = findViewById(R.id.rating);
        mfb=findViewById(R.id.favbutton);
        rtra=findViewById(R.id.tralior);
        rrev=findViewById(R.id.review);
        review=findViewById(R.id.noreview);
        trailerModel=new ArrayList<>();
        reviewModel=new ArrayList<>();
        movieModels=new ArrayList<>();
        myViewModel=ViewModelProviders.of(this).get(MyViewModel.class);
        s = getIntent().getStringArrayExtra("data");
        setTitle(s[0]);
        tv2.setText(s[1]);
        tv3.setText(s[2]);
        Picasso.with(this).load("http://image.tmdb.org/t/p/w200" + s[4]).into(iv);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(Movie_Results.this);
                dialog.setContentView(R.layout.display_image);
                ImageView imageView=dialog.findViewById(R.id.poster);
                Picasso.with(Movie_Results.this).load("http:image.tmdb.org/t/p/w200"+s[4]).resize(900,900).into(imageView);
                dialog.show();

            }
        });
        Picasso.with(this).load("http://image.tmdb.org/t/p/w200" + s[5]).resize(600, 600).into(img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog1=new Dialog(Movie_Results.this);
                dialog1.setContentView(R.layout.display_image);
                ImageView view=dialog1.findViewById(R.id.back);
                Picasso.with(Movie_Results.this).load("http://image.tmdb.org/t/p/w200"+s[5]).resize(600,600).into(view);
                dialog1.show();
            }
        });
        tv4.setText(s[3]);
        mid=s[6];
        String reviewurl = "https://api.themoviedb.org/3/movie/" + mid + "/reviews?api_key=e16b36d60e6c4cefd59c1940c8fa3aeb";
        String trailerurl = "https://api.themoviedb.org/3/movie/" + mid + "/videos?api_key=e16b36d60e6c4cefd59c1940c8fa3aeb";
        rb.setText(s[7]);


        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            new MovieTralior().execute(trailerurl);
            rtra.setLayoutManager(new LinearLayoutManager(this));
            rtra.setAdapter(new TraliorAdapter(this, trailerModel));

            new ReviewTask().execute(reviewurl);
            rrev.setLayoutManager(new LinearLayoutManager(this));
            rrev.setAdapter(new ReviewAdapter(this, reviewModel));

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


            new MovieTralior().execute(trailerurl);
            rtra.setLayoutManager(new LinearLayoutManager(this));
            rtra.setAdapter(new TraliorAdapter(this, trailerModel));

            new ReviewTask().execute(reviewurl);
            rrev.setLayoutManager(new LinearLayoutManager(this));
            rrev.setAdapter(new ReviewAdapter(this, reviewModel));


        }




        String string;
        string=myViewModel.movie_view(mid);
        if(string!=null)
        {
            mfb.setFavorite(true,true);
        }
        else {
            mfb.setFavorite(false,true);
        }

        mfb.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
            @Override
            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {

                MovieModel myEntity = new MovieModel();
                if (favorite) {
                    try {


                        myEntity.setMtitle(s[0]);
                        myEntity.setMoriginal(s[1]);
                        myEntity.setMdate(s[2]);
                        myEntity.setMrating(s[7]);
                        myEntity.setMdes(s[3]);
                        myEntity.setMimage(s[4]);
                        myEntity.setMid(s[6]);
                        myEntity.setMback(s[5]);

                        mfb.setFavorite(true);
                        myViewModel.insertdata(myEntity);
                        Toast.makeText(Movie_Results.this, " movie added to favourites", Toast.LENGTH_SHORT).show();


                    }  catch (SQLiteConstraintException e) {

                    }
                } else {
                    myEntity.setMid(mid);
                    myViewModel.deletedata(myEntity);
                    Toast.makeText(Movie_Results.this, "Removed from favourites", Toast.LENGTH_SHORT).show();

                }
            }
        });





    }








class MovieTralior extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                Scanner scanner = new Scanner(inputStream);
                scanner.useDelimiter("\\A");
                if (scanner.hasNext()) {
                    return scanner.next();
                } else {
                    return null;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String traliorname= jsonObject1.getString("name");
                        String traliorkey = jsonObject1.getString("key");
                        traliorkey = "http://www.youtube.com/watch?v=" + traliorkey;
                        trailerModel.add(new TraliorModel(traliorname, traliorkey));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                rtra.setAdapter(new TraliorAdapter(Movie_Results.this, trailerModel));
            }

        }
    }

    class ReviewTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                Scanner scanner = new Scanner(inputStream);
                scanner.useDelimiter("\\A");
                if (scanner.hasNext()) {
                    return scanner.next();
                } else {
                    return null;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    if(jsonArray.length()!=0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject review = jsonArray.getJSONObject(i);
                            String reviewauthor = review.getString("author");
                            String reviewcontent = review.getString("content");
                            reviewModel.add(new ReviewModel(reviewauthor, reviewcontent));

                        }
                    }
                    else {
                        review.setText("Sorry...No Reviews Available");

                    }
                    rrev.setAdapter(new ReviewAdapter(Movie_Results.this, reviewModel));


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
    }



