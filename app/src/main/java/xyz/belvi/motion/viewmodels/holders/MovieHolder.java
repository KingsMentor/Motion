package xyz.belvi.motion.viewmodels.holders;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import xyz.belvi.motion.R;

/**
 * Created by zone2 on 4/12/17.
 */

public class MovieHolder extends RecyclerView.ViewHolder {
    private AppCompatImageView movieImage;
    private Context mContext;

    public MovieHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        movieImage = (AppCompatImageView) itemView.findViewById(R.id.movie_img);
    }


    public AppCompatImageView getMovieImage() {
        return this.movieImage;
    }


    public Context getContext() {
        return this.mContext;
    }
}
