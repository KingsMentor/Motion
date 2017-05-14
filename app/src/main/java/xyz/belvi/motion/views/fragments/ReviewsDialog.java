package xyz.belvi.motion.views.fragments;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import xyz.belvi.motion.R;
import xyz.belvi.motion.controllers.dataRequestHandler.TRHandler;
import xyz.belvi.motion.controllers.presenters.ReviewPresenter;
import xyz.belvi.motion.models.pojos.Movie;
import xyz.belvi.motion.views.adapters.ReviewAdapter;
import xyz.belvi.motion.views.enchanceViews.EnhanceRecyclerView;

/**
 * Created by zone2 on 5/13/17.
 */

public class ReviewsDialog extends DialogFragment implements ReviewPresenter, EnhanceRecyclerView.listenToScroll {

    private TRHandler trailerHandler = new TRHandler();
    private EnhanceRecyclerView reviewList;
    private ProgressBar loadIndicator;
    private View itemLoadingView;
    private TextView emptyReviewIndicator;
    private LinearLayout retryView;


    public static final String MOVIE_KEY = "xyz.belvi.motion.views.fragments.TrailerAndReviews.MOVIE_KEY";

    public ReviewsDialog newInstance(Movie movie) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(MOVIE_KEY, movie);
        setArguments(bundle);
        return this;
    }

    public Movie getMovie() {
        return getArguments().getParcelable(MOVIE_KEY);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;

    }


    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.reviews_layout, null, true);
        initViews(view);
        trailerHandler.bind(getContext(), this);
        trailerHandler.retrieveReviews(getMovie().getId());

        view.findViewById(R.id.retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retryView.setVisibility(View.GONE);
                loadIndicator.setVisibility(View.VISIBLE);
                trailerHandler.retrieveReviews(getMovie().getId());
            }
        });
        return view;
    }

    private void initViews(View view) {
        reviewList = (EnhanceRecyclerView) view.findViewById(R.id.reviews);
        loadIndicator = (ProgressBar) view.findViewById(R.id.pb_load_indicator);
        emptyReviewIndicator = (TextView) view.findViewById(R.id.empty_review_indicator);
        retryView = (LinearLayout) view.findViewById(R.id.retry_view);
        itemLoadingView = view.findViewById(R.id.item_loading_view);
        initRecyclerView();
    }

    @Override
    public void reachedEndOfList() {
        trailerHandler.nextReviewPage(getMovie().getId());
    }


    private void initRecyclerView() {

        reviewList.listen(this);
        reviewList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        reviewList.setHasFixedSize(false);
    }

    @Override
    public void onReviewRetrieved(ReviewAdapter reviewAdapter) {
        loadIndicator.setVisibility(View.GONE);
        reviewList.setAdapter(reviewAdapter);
    }

    @Override
    public void emptyReview() {
        loadIndicator.setVisibility(View.GONE);
        emptyReviewIndicator.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            emptyReviewIndicator.setText(Html.fromHtml(String.format(getString(R.string.empty_review), getMovie().getTitle()), Html.FROM_HTML_MODE_COMPACT));
        } else {
            emptyReviewIndicator.setText(Html.fromHtml(String.format(getString(R.string.empty_review), getMovie().getTitle())));

        }
    }

    @Override
    public void loadStart() {
        itemLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadComplete() {
        itemLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void onReviewRetrieveFailed() {
        loadIndicator.setVisibility(View.GONE);
        retryView.setVisibility(View.VISIBLE);
    }

    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        getDialog().getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation;
    }


}
