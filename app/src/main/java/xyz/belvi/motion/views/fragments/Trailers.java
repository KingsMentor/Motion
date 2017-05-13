package xyz.belvi.motion.views.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;

import xyz.belvi.motion.R;
import xyz.belvi.motion.controllers.dataRequestHandler.TRHandler;
import xyz.belvi.motion.controllers.presenters.TrailerPresenter;
import xyz.belvi.motion.models.pojos.Movie;
import xyz.belvi.motion.models.pojos.Trailer;

/**
 * Created by zone2 on 5/12/17.
 */

public class Trailers extends Fragment implements TrailerPresenter {

    private TRHandler trailerHandler = new TRHandler();
    private FlexboxLayout flexboxLayout;
    public static final String MOVIE_KEY = "xyz.belvi.motion.views.fragments.TrailerAndReviews.MOVIE_KEY";

    public Trailers newInstance(Movie movie) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(MOVIE_KEY, movie);
        setArguments(bundle);
        return this;
    }

    public Movie getMovie() {
        return getArguments().getParcelable(MOVIE_KEY);
    }

    private View getTrailerView(final Trailer trailer) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.trailer_item, null, true);
        AppCompatTextView trailerAppCompatTextView = (AppCompatTextView) view.findViewById(R.id.trailer_txt_view);
        trailerAppCompatTextView.setText(trailer.getName());
        trailerAppCompatTextView.setOnClickListener(new View.OnClickListener() {
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
        View view = inflater.inflate(R.layout.trailers_layout, container, false);
        flexboxLayout = (FlexboxLayout) view.findViewById(R.id.trailers);
        trailerHandler.bind(getContext(), this);
        trailerHandler.retrieveTrailers(getMovie().getId());

        return view;
    }




    @Override
    public void onTrailerRetrieved(ArrayList<xyz.belvi.motion.models.pojos.Trailer> trailers) {
        for (Trailer trailer : trailers) {
            flexboxLayout.addView(getTrailerView(trailer));
        }

    }


    @Override
    public void onTrailerRetrieveFailed() {

    }

}
