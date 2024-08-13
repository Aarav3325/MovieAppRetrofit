package com.aarav.movieappretrofit;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class MoviePage extends AppCompatActivity {

    TextView tvTitle,tvRating;
    ImageView moviePoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_page);

        tvTitle = findViewById(R.id.movieTitle);
        tvRating = findViewById(R.id.movieRating);
        moviePoster = findViewById(R.id.moviePoster);

        String title = getIntent().getStringExtra("title");
        Double rating = getIntent().getDoubleExtra("rating", 0);
        String poster = getIntent().getStringExtra("poster");

        //String r = rating.toString();

        if (title != null) {
            Log.i("TAG", title);

            tvTitle.setText(title);
        }

        tvRating.setText("" + rating);
        Glide.with(moviePoster.getContext()).load("https://image.tmdb.org/t/p/w500/" + poster).into(moviePoster);
    }
}