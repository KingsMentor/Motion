package xyz.belvi.motion.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xyz.belvi.motion.models.pojos.Movie;
import xyz.belvi.motion.viewmodels.holders.MovieHolder;

/**
 * Created by zone2 on 4/12/17.
 */

public abstract class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {

    private ArrayList<Movie> movies;

    public MovieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public void update(ArrayList<Movie> movies, int start, int end) {
        this.movies.addAll(movies);
        notifyItemRangeInserted(start, end);
    }

    public void resetItem(ArrayList<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    protected abstract void movieSelected(View view, Movie movie, int position);
}
