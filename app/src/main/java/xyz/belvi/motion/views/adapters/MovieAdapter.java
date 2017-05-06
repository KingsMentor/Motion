package xyz.belvi.motion.views.adapters;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import xyz.belvi.motion.R;
import xyz.belvi.motion.models.enums.MoviePosterSize;
import xyz.belvi.motion.models.pojos.Movie;
import xyz.belvi.motion.viewmodels.DiffUtils.MotionDiffUtils;
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
        return new MovieHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.motion_grid_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, final int position) {
        Glide.with(holder.getContext()).load(getMovie(position).getPosterPath(MoviePosterSize.w342)).into(holder.getMovieImage());
//        holder.getVoteAverage().setText(String.valueOf(getMovie(position).getVoteAverage()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movieSelected(view, getMovie(position), position);
            }
        });
    }

    private Movie getMovie(int position) {
        return movies.get(position);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    public void resetItem(ArrayList<Movie> movies) {
        final MotionDiffUtils diffCallback = new MotionDiffUtils(this.movies, movies);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.movies.clear();
        this.movies.addAll(movies);
        diffResult.dispatchUpdatesTo(this);
    }

    protected abstract void movieSelected(View view, Movie movie, int position);
}
