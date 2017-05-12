package xyz.belvi.motion.controllers.presenters;

import java.util.ArrayList;

import xyz.belvi.motion.models.pojos.Trailer;
import xyz.belvi.motion.views.adapters.ReviewAdapter;

/**
 * Created by zone2 on 4/12/17.
 */

public interface TrailerPresenter {

    void onTrailerRetrieved(ArrayList<Trailer> trailers);

    void onReviewRetrieved(ReviewAdapter reviewAdapter);

    void onTrailerRetrieveFailed();

    void onReviewRetrieveFailed();


}
