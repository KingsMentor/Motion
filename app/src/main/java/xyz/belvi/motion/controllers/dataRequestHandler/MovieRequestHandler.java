package xyz.belvi.motion.controllers.dataRequestHandler;

import android.content.Context;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;

import xyz.belvi.motion.controllers.application.MotionApplication;
import xyz.belvi.motion.controllers.presenters.DataPresenter;
import xyz.belvi.motion.controllers.volley.customVolley.MoviesRetrieval;
import xyz.belvi.motion.models.enums.MovieSort;
import xyz.belvi.motion.models.pojos.Movie;
import xyz.belvi.motion.models.pojos.MovieRequestData;
import xyz.belvi.motion.views.adapters.MovieAdapter;

/**
 * Created by zone2 on 4/12/17.
 */

public class MovieRequestHandler {

    private DataPresenter dataPresenter;

    private Context mContext;
    private MovieSort currentMovieSort = MovieSort.POPULAR;
    private boolean isLoading = false;
    private MovieRequestData popularMovieData = new MovieRequestData();
    private MovieRequestData topRatedMovieData = new MovieRequestData();
    private MovieAdapter movieAdapter;

    public MovieRequestHandler(Context context) {
        this.mContext = context;
    }

    public MovieRequestHandler bind(final DataPresenter dataPresenter) {
        this.dataPresenter = dataPresenter;
        movieAdapter = new MovieAdapter(popularMovieData.getMovies()) {
            @Override
            protected void movieSelected(View view, Movie movie, int position) {
                dataMovieSelected(view, movie, position);
            }
        };
        return this;
    }

    private void handleDataSuccessUpdate(ArrayList<Movie> movies, MovieSort movieSort) {
        if (movieSort == MovieSort.POPULAR)
            popularMovieData.getMovies().addAll(movies);
        else
            topRatedMovieData.getMovies().addAll(movies);

        if (currentMovieSort == movieSort) {
            int initialSize = movieSort == MovieSort.POPULAR ? popularMovieData.getMovies().size() - movies.size() : topRatedMovieData.getMovies().size() - movies.size();
            int currentSize = movieSort == MovieSort.POPULAR ? popularMovieData.getMovies().size() : topRatedMovieData.getMovies().size();
            movieAdapter.update(movies, initialSize, currentSize);
            if (initialSize == 0) {
                dataReady();
            }
        } else {
            movieAdapter.resetItem(movieSort == MovieSort.POPULAR ? popularMovieData.getMovies() : topRatedMovieData.getMovies());
        }
        loadStart();
    }

    private void handleDataFailureUpdate() {
        if (popularMovieData.getMovies().size() == 0 && topRatedMovieData.getMovies().size() == 0) {
            loadFailed();
        } else {
            loadComplete();
        }

    }

    private void handleDataLoad(Context context, final MovieSort movieSort, final int page) {
        Request<ArrayList<Movie>> request = new MoviesRetrieval(context, movieSort, page, new Response.Listener<ArrayList<Movie>>() {
            @Override
            public void onResponse(ArrayList<Movie> response) {
                isLoading = false;
                if (movieSort == MovieSort.POPULAR && page > popularMovieData.getPageCount()) {
                    popularMovieData.setPageCount(page);
                    handleDataSuccessUpdate(response, movieSort);

                } else if (page > topRatedMovieData.getPageCount() && movieSort == MovieSort.TOP_RATED) {
                    topRatedMovieData.setPageCount(page);
                    handleDataSuccessUpdate(response, movieSort);
                } else {
                    dataReady();
                    loadComplete();
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                isLoading = false;
                handleDataFailureUpdate();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(5000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MotionApplication.getInstance().getVolley().addToRequestQueue(request, MovieRequestHandler.class.getSimpleName());
    }

    public void load(MovieSort movieSort, int page) {
        if (!isLoading) {
            loadStart();
            currentMovieSort = movieSort;
            handleDataLoad(mContext, movieSort, page);
        } else {
            dataReady();
        }
    }

    public void load() {
        if (popularMovieData.getPageCount() == 0)
            load(MovieSort.POPULAR, 1);
        else
            dataReady();
    }

    public void next() {
        if (currentMovieSort == MovieSort.POPULAR)
            load(currentMovieSort, popularMovieData.getPageCount() + 1);
        else
            load(currentMovieSort, topRatedMovieData.getPageCount() + 1);
    }

    public void fetchAdapter(MovieSort movieSort) {
        if (movieSort != currentMovieSort) {
            movieAdapter.resetItem(movieSort == MovieSort.POPULAR ? popularMovieData.getMovies() : topRatedMovieData.getMovies());
            currentMovieSort = movieSort;
            next();
            dataReady();
        }
    }

    private void dataReady() {
        if (dataPresenter != null)
            dataPresenter.onDataReady(movieAdapter);
    }

    private void loadStart() {
        isLoading = true;
        if (dataPresenter != null)
            dataPresenter.onLoadStarted();
    }

    private void loadFailed() {
        if (dataPresenter != null)
            dataPresenter.onLoadFailure();
    }

    private void loadComplete() {
        if (dataPresenter != null)
            dataPresenter.onLoadCompleted();
    }

    private void dataMovieSelected(View view, Movie movie, int position) {
        if (dataPresenter != null)
            dataPresenter.movieSelected(view, movie, position);
    }
}
