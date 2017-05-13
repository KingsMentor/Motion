package xyz.belvi.motion.controllers.presenters;

import xyz.belvi.motion.views.adapters.ReviewAdapter;

/**
 * Created by zone2 on 4/12/17.
 */

public interface ReviewPresenter {

    void onReviewRetrieved(ReviewAdapter reviewAdapter);

    void emptyReview();

    void loadStart();

    void loadComplete();

    void onReviewRetrieveFailed();


}
