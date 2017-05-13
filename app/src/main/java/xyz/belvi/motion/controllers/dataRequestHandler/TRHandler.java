package xyz.belvi.motion.controllers.dataRequestHandler;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;

import xyz.belvi.motion.controllers.application.MotionApplication;
import xyz.belvi.motion.controllers.presenters.ReviewPresenter;
import xyz.belvi.motion.controllers.presenters.TrailerPresenter;
import xyz.belvi.motion.controllers.volley.customVolley.ReviewRequest;
import xyz.belvi.motion.controllers.volley.customVolley.TrailerRequest;
import xyz.belvi.motion.models.pojos.Review;
import xyz.belvi.motion.models.pojos.ReviewRequestData;
import xyz.belvi.motion.models.pojos.Trailer;
import xyz.belvi.motion.views.adapters.ReviewAdapter;

/**
 * Created by zone2 on 5/12/17.
 */

public class TRHandler {
    private TrailerPresenter mTrailerPresenter;
    private ReviewPresenter mReviewPresenter;
    private Context mContext;
    private ReviewAdapter mReviewAdapter;
    private ReviewRequestData reviewRequestData;


    public void bind(Context context, TrailerPresenter trailerPresenter) {
        mContext = context;
        mTrailerPresenter = trailerPresenter;
    }

    public void bind(Context context, ReviewPresenter reviewPresenter) {
        mContext = context;
        mReviewPresenter = reviewPresenter;
        mReviewAdapter = new ReviewAdapter(new ArrayList<Review>());
        reviewRequestData = new ReviewRequestData(new ArrayList<Review>(), 0, false);
    }


    public void retrieveTrailers(long movieId) {
        Request<ArrayList<Trailer>> request = new TrailerRequest(mContext, movieId, new Response.Listener<ArrayList<Trailer>>() {
            @Override
            public void onResponse(ArrayList<Trailer> response) {
                mTrailerPresenter.onTrailerRetrieved(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTrailerPresenter.onTrailerRetrieveFailed();
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(5000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MotionApplication.getInstance().getVolley().addToRequestQueue(request, TRHandler.class.getSimpleName());


    }

    public void nextReviewPage(long movieId){
        retrieveReviews(movieId);
    }

    public void retrieveReviews(long movieId) {
        if (reviewRequestData.isReachedPageEnd()) {
            mReviewPresenter.loadComplete();
            return;
        }
        mReviewPresenter.loadStart();
        Request<ReviewRequestData> request = new ReviewRequest(mContext, movieId, reviewRequestData.getPageCount() + 1, new Response.Listener<ReviewRequestData>() {
            @Override
            public void onResponse(ReviewRequestData response) {
                mReviewPresenter.loadComplete();
                reviewRequestData.setReachedPageEnd(response.isReachedPageEnd());
                if (mReviewAdapter.getItemCount() == 0 && response.getReviews().size() == 0) {
                    mReviewPresenter.emptyReview();
                } else {
                    reviewRequestData.nextPage();
                    if (mReviewAdapter.getItemCount() == 0) {
                        mReviewAdapter.setItems(response.getReviews());
                        mReviewPresenter.onReviewRetrieved(mReviewAdapter);
                    } else {
                        mReviewAdapter.updateItems(response.getReviews());
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mReviewPresenter.loadComplete();
                if (mReviewAdapter.getItemCount() == 0)
                    mReviewPresenter.onReviewRetrieveFailed();
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(5000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MotionApplication.getInstance().getVolley().addToRequestQueue(request, TRHandler.class.getSimpleName());

    }
}
