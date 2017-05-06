package xyz.belvi.motion.viewmodels.DiffUtils;

import android.support.v7.util.DiffUtil;

import java.util.ArrayList;

import xyz.belvi.motion.models.pojos.Movie;

/**
 * Created by zone2 on 5/6/17.
 */

public class MotionDiffUtils extends DiffUtil.Callback {

    private ArrayList<Movie> oldMovies, newMovies;

    public MotionDiffUtils(ArrayList<Movie> oldMovies, ArrayList<Movie> newMovies) {
        this.oldMovies = oldMovies;
        this.newMovies = newMovies;
    }

    @Override
    public int getOldListSize() {
        return oldMovies == null ? 0 : oldMovies.size();
    }

    @Override
    public int getNewListSize() {
        return newMovies == null ? 0 : newMovies.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldMovies.get(oldItemPosition).getId() == newMovies.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldMovies.get(oldItemPosition).getId() == newMovies.get(newItemPosition).getId();
    }
}
