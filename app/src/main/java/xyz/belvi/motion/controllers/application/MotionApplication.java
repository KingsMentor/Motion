package xyz.belvi.motion.controllers.application;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import xyz.belvi.motion.R;
import xyz.belvi.motion.controllers.dataRequestHandler.MovieRequestHandler;
import xyz.belvi.motion.controllers.volley.volleyHelpers.VolleyInit;

/**
 * Created by zone2 on 4/11/17.
 */

public class MotionApplication extends Application {




    private VolleyInit volleyInit;
    static MotionApplication motionApplication;

    private MovieRequestHandler movieRequestHandler;

    public synchronized static MotionApplication getInstance() {
        return motionApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        motionApplication = this;
        initVolley();
        initMovieData();
        defineCalligraphy();
    }

    private void initVolley() {
        volleyInit = new VolleyInit();
        volleyInit.initVolley(this);
    }

    private void initMovieData() {
        movieRequestHandler = new MovieRequestHandler(this);
        movieRequestHandler.init();
    }

    public VolleyInit getVolley() {
        return this.volleyInit;
    }

    private void defineCalligraphy() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/source_code.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    public MovieRequestHandler getMovieRequestHandler() {
        return this.movieRequestHandler;
    }
}
