package xyz.belvi.motion.views.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.bumptech.glide.Glide;

import xyz.belvi.motion.R;
import xyz.belvi.motion.models.enums.MoviePosterSize;
import xyz.belvi.motion.models.pojos.Movie;

public class MovieDetailedActivty extends AppCompatActivity {

    public static final String MOVIE_KEY = "xyz.belvi.motion.views.activities.MovieDetailedActivty.MOVIE_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detailed_activty);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AppCompatImageView backDropImg = (AppCompatImageView) findViewById(R.id.img_postal);
        AppCompatImageView thumbnail = (AppCompatImageView) findViewById(R.id.thumbnail);

        Glide.with(this).load(getMovie().getBackdropPath(MoviePosterSize.w500)).into(backDropImg);
        setViewText(R.id.over_view, getMovie().getOverview());
        setViewText(R.id.release_date, getMovie().getReleaseDate());
        setViewText(R.id.title, getMovie().getTitle());
        setViewText(R.id.rating_txt, String.valueOf(getMovie().getVoteAverage()));
        ((AppCompatRatingBar) findViewById(R.id.rating)).setRating(getMovie().getVoteAverage() / 2);
        Glide.with(this).load(getMovie().getPosterPath(MoviePosterSize.w185)).into(thumbnail);

    }

    public void setViewText(int viewId, String text) {
        ((AppCompatTextView) findViewById(viewId)).setText(text);
    }

    private Movie getMovie() {
        return getIntent().getParcelableExtra(MOVIE_KEY);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
