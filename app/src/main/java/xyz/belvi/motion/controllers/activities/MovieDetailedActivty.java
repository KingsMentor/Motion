package xyz.belvi.motion.controllers.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;

import xyz.belvi.motion.R;
import xyz.belvi.motion.controllers.Utils;
import xyz.belvi.motion.models.pojos.Movie;

public class MovieDetailedActivty extends AppCompatActivity {

    public static final String MOVIE_KEY = "xyz.belvi.motion.controllers.activities.MovieDetailedActivty.MOVIE_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detailed_activty);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AppCompatImageView imgPath = (AppCompatImageView) findViewById(R.id.img_postal);

        Glide.with(this).load(Utils.getImagePath(getMovie().getBackdropPath())).into(imgPath);
        setViewText(R.id.over_view, getMovie().getOverview());
        setViewText(R.id.release_date, getMovie().getReleaseDate());
        setViewText(R.id.title, getMovie().getTitle());
        setViewText(R.id.rating_txt, String.valueOf(getMovie().getVoteAverage()));
        ((AppCompatRatingBar) findViewById(R.id.rating)).setRating(getMovie().getVoteAverage() / 2);

    }

    public void setViewText(int viewId, String text) {
        ((AppCompatTextView) findViewById(viewId)).setText(text);
    }

    private Movie getMovie() {
        return getIntent().getParcelableExtra(MOVIE_KEY);
    }
}
