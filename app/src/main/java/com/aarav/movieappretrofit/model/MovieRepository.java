package com.aarav.movieappretrofit.model;

import android.app.Application;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.lifecycle.MutableLiveData;

import com.aarav.movieappretrofit.R;
import com.aarav.movieappretrofit.serviceapi.MovieApiService;
import com.aarav.movieappretrofit.serviceapi.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

public class MovieRepository {

    private ArrayList<Movie> movies = new ArrayList<>();
    private MutableLiveData<List<Movie>> mutableLiveData = new MutableLiveData<>();
    private Application application;

    public MovieRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Movie>> getMutableLiveData() {
        MovieApiService movieApiService = RetrofitInstance.getService();

        Call<Result> call = movieApiService.getPopularMovies(API_KEY); //mention your API_KEY

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result result = response.body();

                if(result != null && result.getResults() != null){
                    movies = (ArrayList<Movie>) result.getResults();
                    mutableLiveData.setValue(movies);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable throwable) {

            }
        });

        return mutableLiveData;
    }
}
