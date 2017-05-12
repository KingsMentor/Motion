package xyz.belvi.motion.views.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;

import xyz.belvi.motion.R;
import xyz.belvi.motion.controllers.dataRequestHandler.TrailerHandler;
import xyz.belvi.motion.controllers.presenters.TrailerPresenter;
import xyz.belvi.motion.models.pojos.Movie;
import xyz.belvi.motion.models.pojos.Trailer;
import xyz.belvi.motion.views.adapters.ReviewAdapter;
import xyz.belvi.motion.views.enchanceViews.EnhanceRecyclerView;

/**
 * Created by zone2 on 5/12/17.
 */

public class TrailerAndReviews extends Fragment implements TrailerPresenter, EnhanceRecyclerView.listenToScroll {

    private TrailerHandler trailerHandler = new TrailerHandler();
    private FlexboxLayout flexboxLayout;
    private EnhanceRecyclerView reviewList;
    public static final String MOVIE_KEY = "xyz.belvi.motion.views.fragments.TrailerAndReviews.MOVIE_KEY";

    public TrailerAndReviews newInstance(Movie movie) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(MOVIE_KEY, movie);
        setArguments(bundle);
        return this;
    }

    public Movie getMovie() {
        return getArguments().getParcelable(MOVIE_KEY);
    }

    private View getTrailerView(final Trailer trailer) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.trailer_item, null, true);
        AppCompatTextView trailerAppCompatTextView = (AppCompatTextView) view.findViewById(R.id.trailer_txt_view);
        trailerAppCompatTextView.setText(trailer.getName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey())));
            }
        });
        return view;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trailers_and_reviews, container, false);
        flexboxLayout = (FlexboxLayout) view.findViewById(R.id.trailers);
        reviewList = (EnhanceRecyclerView) view.findViewById(R.id.reviews);
        initRecyclerView();
        trailerHandler.bind(getContext(), this);
        trailerHandler.retrieveDetails(getMovie().getId());

        return view;
    }

    private void initRecyclerView() {

        reviewList.listen(this);
        reviewList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        reviewList.setHasFixedSize(false);
    }


    @Override
    public void onTrailerRetrieved(ArrayList<xyz.belvi.motion.models.pojos.Trailer> trailers) {
        for (Trailer trailer : trailers) {
            flexboxLayout.addView(getTrailerView(trailer));
        }

    }

    @Override
    public void onReviewRetrieved(ReviewAdapter reviewAdapter) {
        reviewList.setAdapter(reviewAdapter);
    }

    @Override
    public void onTrailerRetrieveFailed() {

    }

    @Override
    public void onReviewRetrieveFailed() {

    }

    @Override
    public void reachedEndOfList() {

    }
}
