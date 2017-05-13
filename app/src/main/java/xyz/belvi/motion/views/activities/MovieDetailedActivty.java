package xyz.belvi.motion.views.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import xyz.belvi.motion.R;
import xyz.belvi.motion.controllers.application.MotionApplication;
import xyz.belvi.motion.controllers.dataController.FavouriteDao;
import xyz.belvi.motion.databinding.ActivityMovieDetailedActivtyBinding;
import xyz.belvi.motion.models.enums.MoviePosterSize;
import xyz.belvi.motion.models.pojos.Movie;
import xyz.belvi.motion.views.fragments.ReviewsDialog;
import xyz.belvi.motion.views.fragments.Trailers;

public class MovieDetailedActivty extends AppCompatActivity {

    public static final String MOVIE_KEY = "xyz.belvi.motion.views.activities.MovieDetailedActivty.MOVIE_KEY";
    ActivityMovieDetailedActivtyBinding movieDetailedActivtyBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieDetailedActivtyBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detailed_activty);
        setSupportActionBar(movieDetailedActivtyBinding.toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        populateItems(getMovie());
    }

    private void populateItems(Movie movie) {

        Glide.with(this).load(movie.getBackdropPath(MoviePosterSize.w500)).into(movieDetailedActivtyBinding.imgPostal);
        Glide.with(this).load(movie.getPosterPath(MoviePosterSize.w185)).into(movieDetailedActivtyBinding.contentItems.thumbnail);

//        movieDetailedActivtyBinding.contentItems.overView.setText(movie.getOverview());
        movieDetailedActivtyBinding.contentItems.releaseDate.setText(movie.getReleaseDate());
        movieDetailedActivtyBinding.contentItems.title.setText(movie.getTitle());
        movieDetailedActivtyBinding.contentItems.ratingTxt.setText(String.valueOf(movie.getVoteAverage()));
        movieDetailedActivtyBinding.contentItems.rating.setRating(movie.getVoteAverage() / 2);
        movieDetailedActivtyBinding.contentItems.ftv.setText(movie.getOverview());
        movieDetailedActivtyBinding.contentItems.ftv.setTextColor(ContextCompat.getColor(this, R.color.white));
        movieDetailedActivtyBinding.contentItems.ftv.setTextSize(24);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.trailers_and_reviews, new Trailers().newInstance(getMovie()))
                .commitAllowingStateLoss();


    }


    private Movie getMovie() {
        return getIntent().getParcelableExtra(MOVIE_KEY);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        else
            handleFavItemClick(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie_detailed_activty, menu);
        MenuItem favItem = menu.findItem(R.id.action_fav);
        favItem.setIcon(new FavouriteDao(this).isFavorite(getMovie().getId()) ? R.drawable.ic_star_selected_24dp : R.drawable.ic_star_white_24dp);
        return super.onCreateOptionsMenu(menu);
    }

    private void handleFavItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_review) {
            new ReviewsDialog().newInstance(getMovie()).show(getSupportFragmentManager(), "");
        } else {
            FavouriteDao favouriteDao = new FavouriteDao(this);
            if (favouriteDao.isFavorite(getMovie().getId())) {
                if (favouriteDao.removeFavorite(getMovie().getId())) {
                    menuItem.setIcon(R.drawable.ic_star_white_24dp);
                    Toast.makeText(this, getMovie().getTitle() + " " + getString(R.string.fav_removed), Toast.LENGTH_SHORT).show();
                }
            } else {
                if (favouriteDao.addToFavorites(getMovie())) {
                    menuItem.setIcon(R.drawable.ic_star_selected_24dp);
                    Toast.makeText(this, getMovie().getTitle() + " " + getString(R.string.fav_success), Toast.LENGTH_SHORT).show();
                }
            }
            MotionApplication.getInstance().getMovieRequestHandler().updateFavList();
        }
    }
}
