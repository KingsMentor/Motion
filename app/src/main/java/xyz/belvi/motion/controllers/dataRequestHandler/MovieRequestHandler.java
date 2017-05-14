package xyz.belvi.motion.controllers.dataRequestHandler;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;

import xyz.belvi.motion.controllers.application.MotionApplication;
import xyz.belvi.motion.controllers.dataController.FavouriteDao;
import xyz.belvi.motion.controllers.presenters.DataPresenter;
import xyz.belvi.motion.controllers.volley.customVolley.MoviesRetrieval;
import xyz.belvi.motion.models.enums.MovieSort;
import xyz.belvi.motion.models.pojos.Movie;
import xyz.belvi.motion.models.pojos.MovieRequestData;
import xyz.belvi.motion.models.preferences.UIPreference;
import xyz.belvi.motion.views.adapters.MovieAdapter;

import static xyz.belvi.motion.models.enums.MovieSort.FAVORITE;
import static xyz.belvi.motion.models.enums.MovieSort.POPULAR;
import static xyz.belvi.motion.models.enums.MovieSort.TOP_RATED;

/**
 * Created by zone2 on 4/12/17.
 */

public class MovieRequestHandler {

    private DataPresenter dataPresenter;

    private Context mContext;
    private MovieSort currentMovieSort = POPULAR;
    private boolean isLoading;
    private MovieRequestData popularMovieData = new MovieRequestData();
    private MovieRequestData topRatedMovieData = new MovieRequestData();
    private ArrayList<Movie> favMovies = new ArrayList<>();
    private MovieAdapter movieAdapter;
    private UIPreference uiPreference;

    public MovieRequestHandler(Context context) {
        this.mContext = context;
        this.uiPreference = new UIPreference(mContext);
    }

    public MovieRequestHandler bind(final DataPresenter dataPresenter) {
        updateFavList();
        this.dataPresenter = dataPresenter;
        movieAdapter = new MovieAdapter(getPrefSortType() == POPULAR ? popularMovieData.getMovies() : getPrefSortType() == TOP_RATED ? topRatedMovieData.getMovies() : favMovies) {
            @Override
            protected void movieSelected(View view, Movie movie, int position) {
                dataMovieSelected(view, movie, position);
            }
        };
        movieAdapter.notifyDataSetChanged();
        return this;
    }

    private void handleDataSuccessUpdate(ArrayList<Movie> movies, MovieSort movieSort) {
        if (movieSort == POPULAR)
            popularMovieData.getMovies().addAll(movies);
        else if (movieSort == TOP_RATED)
            topRatedMovieData.getMovies().addAll(movies);
        else {
            favMovies.clear();
            favMovies.addAll(movies);
        }
        if (movieAdapter == null)//handler has not been binded
            return;
        if (currentMovieSort == movieSort) {
            int initialSize = movieSort == POPULAR ? popularMovieData.getMovies().size() - movies.size() : movieSort == MovieSort.TOP_RATED ? topRatedMovieData.getMovies().size() - movies.size() : favMovies.size() - movies.size();
            int currentSize = movieSort == POPULAR ? popularMovieData.getMovies().size() : movieSort == MovieSort.TOP_RATED ? topRatedMovieData.getMovies().size() : favMovies.size();
            movieAdapter.notifyItemRangeInserted(initialSize, currentSize);
//            movieAdapter.update(movies, initialSize, currentSize);
            if (initialSize == 0) {
                dataReady();
            }
        } else {
            movieAdapter.resetItem(movieSort == POPULAR ? popularMovieData.getMovies() : movieSort == MovieSort.TOP_RATED ? topRatedMovieData.getMovies() : favMovies);
        }
        loadComplete();
    }

    private void handleDataFailureUpdate(MovieSort movieSort) {
        if ((popularMovieData.getMovies().size() == 0 && movieSort == POPULAR) || (topRatedMovieData.getMovies().size() == 0 && movieSort == MovieSort.TOP_RATED)) {
            loadFailed();
        } else {
            loadComplete();
        }

    }

    private void loadFavorite(final Context context) {
        new AsyncTask<Void, Void, ArrayList<Movie>>() {
            @Override
            protected ArrayList<Movie> doInBackground(Void... voids) {
                return new FavouriteDao(context).getFavorites();
            }

            @Override
            protected void onPostExecute(ArrayList<Movie> movies) {
                super.onPostExecute(movies);
                handleDataSuccessUpdate(movies, MovieSort.FAVORITE);
            }
        }.execute();

    }

    private void handleDataLoad(Context context, final MovieSort movieSort, final int page) {
        if (movieSort == FAVORITE) {
            loadFavorite(context);
        } else {
            Request<ArrayList<Movie>> request = new MoviesRetrieval(context, movieSort, page, new Response.Listener<ArrayList<Movie>>() {
                @Override
                public void onResponse(ArrayList<Movie> response) {
                    isLoading = false;
                    if (movieSort == POPULAR && page > popularMovieData.getPageCount()) {
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
                    handleDataFailureUpdate(movieSort);
                }
            });
            request.setRetryPolicy(new DefaultRetryPolicy(5000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MotionApplication.getInstance().getVolley().addToRequestQueue(request, MovieRequestHandler.class.getSimpleName());
        }
    }

    public void load(MovieSort movieSort, int page) {
        if (!isLoading) {
            loadStart();
            currentMovieSort = movieSort;
            handleDataLoad(mContext, movieSort, page);
        } else {
            dataReady();
        }
        saveSortType(currentMovieSort);
    }

    public void retry(MovieSort movieSort) {
        load(movieSort, 1);
    }

    public void init() {
        if (getPrefSortType() == FAVORITE || (popularMovieData.getPageCount() == 0 && getPrefSortType() == POPULAR) || (topRatedMovieData.getPageCount() == 0 && getPrefSortType() == MovieSort.TOP_RATED))
            load(getPrefSortType(), 1);
        else
            dataReady();
    }

    public void next() {
        if (currentMovieSort == POPULAR)
            load(currentMovieSort, popularMovieData.getPageCount() + 1);
        else if (currentMovieSort == TOP_RATED)
            load(currentMovieSort, topRatedMovieData.getPageCount() + 1);
        else {
            updateFavList();
        }
    }

    public void fetchAdapter(MovieSort movieSort) {
        if (movieAdapter == null)
            return;
        if (movieSort != currentMovieSort) {
            movieAdapter.resetItem(movieSort == POPULAR ? popularMovieData.getMovies() : movieSort == MovieSort.TOP_RATED ? topRatedMovieData.getMovies() : favMovies);
            currentMovieSort = movieSort;
            next();
            dataReady();
        }
        saveSortType(currentMovieSort);
    }

    public void updateFavList() {
        new AsyncTask<Void, Void, ArrayList<Movie>>() {
            @Override
            protected ArrayList<Movie> doInBackground(Void... voids) {
                return new FavouriteDao(mContext).getFavorites();
            }

            @Override
            protected void onPostExecute(ArrayList<Movie> movies) {
                super.onPostExecute(movies);
                favMovies = movies;
                if (currentMovieSort == FAVORITE)
                    movieAdapter.resetItem(movies);
            }
        }.execute();
    }

    public MovieSort getPrefSortType() {
        return uiPreference.getSortType();
    }

    private void saveSortType(MovieSort movieSort) {
        uiPreference.setSortType(movieSort);
    }

    private void dataReady() {
        if (dataPresenter != null) {
            dataPresenter.onDataReady(movieAdapter);
            loadComplete();
        }
    }

    private void loadStart() {
        isLoading = true;
        if (dataPresenter != null)
            dataPresenter.onLoadStarted(currentMovieSort == POPULAR ? popularMovieData.getMovies().size() == 0 : topRatedMovieData.getMovies().size() == 0);
    }

    private void loadFailed() {
        isLoading = false;
        if (dataPresenter != null)
            dataPresenter.onLoadFailure();
    }

    private void loadComplete() {
        isLoading = false;
        if (dataPresenter != null)
            dataPresenter.onLoadCompleted();
    }

    private void dataMovieSelected(View view, Movie movie, int position) {
        if (dataPresenter != null)
            dataPresenter.movieSelected(view, movie, position);
    }
}
