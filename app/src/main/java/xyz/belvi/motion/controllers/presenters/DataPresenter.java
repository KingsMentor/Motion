package xyz.belvi.motion.controllers.presenters;

import android.view.View;

import xyz.belvi.motion.models.pojos.Movie;
import xyz.belvi.motion.views.adapters.MovieAdapter;

/**
 * Created by zone2 on 4/12/17.
 */

public interface DataPresenter {

    public void onDataReady(MovieAdapter movieAdapter);

    public void onLoadCompleted();

    public void onLoadStarted(boolean firstLoad);

    public void onLoadFailure();

    public void movieSelected(View view, Movie movie, int postion);
}
