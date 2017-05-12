package xyz.belvi.motion.controllers.dataRequestHandler;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;

import xyz.belvi.motion.controllers.application.MotionApplication;
import xyz.belvi.motion.controllers.presenters.TrailerPresenter;
import xyz.belvi.motion.controllers.volley.customVolley.ReviewRequest;
import xyz.belvi.motion.controllers.volley.customVolley.TrailerRequest;
import xyz.belvi.motion.models.pojos.Review;
import xyz.belvi.motion.models.pojos.Trailer;
import xyz.belvi.motion.views.adapters.ReviewAdapter;

/**
 * Created by zone2 on 5/12/17.
 */

public class TrailerHandler {
    private TrailerPresenter mTrailerPresenter;
    private Context mContext;
    private ReviewAdapter mReviewAdapter;


    public void bind(Context context, TrailerPresenter trailerPresenter) {
        mContext = context;
        mTrailerPresenter = trailerPresenter;
        mReviewAdapter = new ReviewAdapter(new ArrayList<Review>());
    }

    public void retrieveDetails(long movieId) {
        retrieveTrailers(movieId);
        retrieveReviews(movieId);
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
        MotionApplication.getInstance().getVolley().addToRequestQueue(request, TrailerHandler.class.getSimpleName());


    }

    public void retrieveReviews(long movieId) {
        Request<ArrayList<Review>> request = new ReviewRequest(mContext, movieId, 1, new Response.Listener<ArrayList<Review>>() {
            @Override
            public void onResponse(ArrayList<Review> response) {
                mReviewAdapter.setItems(response);
                mTrailerPresenter.onReviewRetrieved(mReviewAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTrailerPresenter.onTrailerRetrieveFailed();
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(5000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MotionApplication.getInstance().getVolley().addToRequestQueue(request, TrailerHandler.class.getSimpleName());

    }
}
