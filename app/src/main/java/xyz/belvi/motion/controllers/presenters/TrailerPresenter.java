package xyz.belvi.motion.controllers.presenters;

import java.util.ArrayList;

import xyz.belvi.motion.models.pojos.Trailer;

/**
 * Created by zone2 on 4/12/17.
 */

public interface TrailerPresenter {

    void onTrailerRetrieved(ArrayList<Trailer> trailers);

    void onTrailerRetrieveFailed();


}
