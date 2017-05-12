package xyz.belvi.motion.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import xyz.belvi.motion.R;
import xyz.belvi.motion.models.pojos.Review;
import xyz.belvi.motion.viewmodels.holders.ReviewHolder;

/**
 * Created by zone2 on 5/8/17.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewHolder> {

    private ArrayList<Review> mReviews;

    public ReviewAdapter(ArrayList<Review> reviews) {
        this.mReviews = reviews;
    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReviewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
        Review review = mReviews.get(position);
        holder.getAuthorView().setText(review.getAuthor());
        holder.getContentView().setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public void setItems(ArrayList<Review> reviews) {
        mReviews = reviews;
        notifyDataSetChanged();
    }
}
