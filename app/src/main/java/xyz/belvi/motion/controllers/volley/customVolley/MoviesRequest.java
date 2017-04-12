package xyz.belvi.motion.controllers.volley.customVolley;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

import java.util.ArrayList;

import xyz.belvi.motion.models.enums.MovieSort;
import xyz.belvi.motion.models.pojos.Movie;

/**
 * Created by zone2 on 4/12/17.
 */

abstract public class MoviesRequest extends Request<ArrayList<Movie>> {


    public MoviesRequest(Context context, MovieSort movieSort, int page, Response.ErrorListener listener) {
        super(movieSort.getMethod(), movieSort.buildURl(context, page), listener);
    }

    @Override
    abstract protected Response parseNetworkResponse(NetworkResponse response);

    @Override
    abstract protected void deliverResponse(ArrayList<Movie> response);


}
