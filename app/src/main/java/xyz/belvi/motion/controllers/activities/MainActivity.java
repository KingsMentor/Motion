package xyz.belvi.motion.controllers.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import xyz.belvi.motion.R;
import xyz.belvi.motion.controllers.application.MotionApplication;
import xyz.belvi.motion.controllers.presenters.DataPresenter;
import xyz.belvi.motion.models.enums.MovieSort;
import xyz.belvi.motion.models.pojos.Movie;
import xyz.belvi.motion.views.adapters.MovieAdapter;
import xyz.belvi.motion.views.enchanceViews.EnhanceRecyclerView;
import xyz.belvi.motion.views.enchanceViews.GridSpacingItemDecoration;

public class MainActivity extends AppCompatActivity implements DataPresenter, EnhanceRecyclerView.listenToScroll {

    private EnhanceRecyclerView moviesRecyclerView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();
        initToolbar();
        MotionApplication.getInstance().getMovieRequestHandler().bind(this).load();
        findViewById(R.id.retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MotionApplication.getInstance().getMovieRequestHandler().retry(getSelectedMovieSort());
            }
        });

    }

    private MovieSort getSelectedMovieSort() {
        return selectedMenu == R.id.action_filter_popular ? MovieSort.POPULAR : MovieSort.TOP_RATED;
    }

    int selectedMenu = R.id.action_filter_popular;

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // set a custom shadow that overlays the main content when the drawer opens
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

//
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };


        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                if (selectedMenu != item.getItemId()) {
                    if (item.getItemId() == R.id.action_filter_popular) {
                        MotionApplication.getInstance().getMovieRequestHandler().fetchAdapter(MovieSort.POPULAR);
                    } else {
                        MotionApplication.getInstance().getMovieRequestHandler().fetchAdapter(MovieSort.TOP_RATED);
                    }
                }
                drawerLayout.closeDrawer(Gravity.RIGHT);
                selectedMenu = item.getItemId();
                return true;
            }
        });
    }

    private void initRecyclerView() {
        moviesRecyclerView = (EnhanceRecyclerView) findViewById(R.id.movies);
        moviesRecyclerView.listen(this);
        moviesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        moviesRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 0, false));
        moviesRecyclerView.setHasFixedSize(false);
    }

    private void setRecyclerAdapter(MovieAdapter movieAdapter) {
        if (moviesRecyclerView != null)
            moviesRecyclerView.setAdapter(movieAdapter);
    }

    @Override
    public void onDataReady(MovieAdapter movieAdapter) {
        setRecyclerAdapter(movieAdapter);
    }

    @Override
    public void onLoadCompleted() {
        findViewById(R.id.loading_items).setVisibility(View.GONE);
        toggleLoadingView(false);
    }

    @Override
    public void onLoadStarted(boolean firstLoad) {
        findViewById(R.id.failed_view).setVisibility(View.GONE);
        if (firstLoad) {
            findViewById(R.id.loading_items).setVisibility(View.VISIBLE);
        } else {
            toggleLoadingView(true);
        }
    }

    @Override
    public void onLoadFailure() {
        findViewById(R.id.loading_items).setVisibility(View.GONE);
        findViewById(R.id.failed_view).setVisibility(View.VISIBLE);
    }

    @Override
    public void movieSelected(View view, Movie movie, int postion) {
        startActivity(new Intent(this, MovieDetailedActivty.class)
                .putExtra(MovieDetailedActivty.MOVIE_KEY, movie));
    }

    @Override
    public void reachedEndOfList() {
        MotionApplication.getInstance().getMovieRequestHandler().next();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        drawerLayout.openDrawer(Gravity.RIGHT);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggle
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void toggleLoadingView(boolean isLoading) {
        if (!isLoading) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    findViewById(R.id.loading_view_indicator).setVisibility(View.GONE);
                }
            }, 1500);
        } else
            findViewById(R.id.loading_view_indicator).setVisibility(View.VISIBLE);
    }


}
