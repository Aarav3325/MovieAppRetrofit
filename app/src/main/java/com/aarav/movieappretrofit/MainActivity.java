package com.aarav.movieappretrofit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.aarav.movieappretrofit.databinding.ActivityMainBinding;
import com.aarav.movieappretrofit.model.Movie;
import com.aarav.movieappretrofit.view.MovieAdapter;
import com.aarav.movieappretrofit.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemClickListener {

    private ArrayList<Movie> movies;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private SwipeRefreshLayout refreshLayout;
    private ActivityMainBinding activityMainBinding;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        getPopularMovies();

        refreshLayout = activityMainBinding.swipeLayout;
        refreshLayout.setColorSchemeResources(R.color.black);


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPopularMovies();
            }
        });


    }

    private void getPopularMovies() {
        viewModel.getAllMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> moviesLiveData) {
                movies = (ArrayList<Movie>) moviesLiveData;
                displayMoviesInRecyclerView();
            }
        });
    }

    private void displayMoviesInRecyclerView() {
        recyclerView = activityMainBinding.recyclerview;

        movieAdapter = new MovieAdapter(this, movies);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(movieAdapter);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // notify an adapter associated with a RecyclerView
        // that the underlying dataset hase changed
        movieAdapter.setClickListener(this);
        movieAdapter.notifyDataSetChanged();


    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, MoviePage.class);
        intent.putExtra("title", movies.get(position).getTitle());
        intent.putExtra("poster", movies.get(position).getPosterPath());
        intent.putExtra("rating", movies.get(position).getVoteAverage());
        startActivity(intent);
    }
}