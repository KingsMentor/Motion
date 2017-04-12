package xyz.belvi.motion.controllers.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import xyz.belvi.motion.R;
import xyz.belvi.motion.controllers.application.MotionApplication;
import xyz.belvi.motion.controllers.presenters.DataPresenter;
import xyz.belvi.motion.models.pojos.Movie;
import xyz.belvi.motion.views.adapters.MovieAdapter;
import xyz.belvi.motion.views.enchanceViews.EnhanceRecyclerView;

public class MainActivity extends AppCompatActivity implements DataPresenter, EnhanceRecyclerView.listenToScroll {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MotionApplication.getInstance().getMovieRequestHandler().bind(this).load();

    }

    @Override
    public void onDataReady(MovieAdapter movieAdapter) {

    }

    @Override
    public void onLoadCompleted() {

    }

    @Override
    public void onLoadStarted() {

    }

    @Override
    public void onLoadFailure() {

    }

    @Override
    public void movieSelected(View view, Movie movie, int postion) {

    }

    @Override
    public void reachedEndOfList() {

    }
}
