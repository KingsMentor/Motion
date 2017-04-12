package xyz.belvi.motion.controllers.volley.customVolley;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import xyz.belvi.motion.models.enums.MovieSort;
import xyz.belvi.motion.models.pojos.Movie;

/**
 * Created by zone2 on 4/12/17.
 */

public class MoviesRetrieval extends MoviesRequest {

    private final Gson gson = new Gson();
    private final Response.Listener<ArrayList<Movie>> listener;


    public MoviesRetrieval(Context context, MovieSort movieSort, int page, Response.Listener<ArrayList<Movie>> listener, Response.ErrorListener errorListener) {
        super(context, movieSort, page, errorListener);
        this.listener = listener;
    }


    @Override
    protected Response<ArrayList<Movie>> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            try {
                JSONArray results = new JSONObject(json).optJSONArray("results");
                ArrayList<Movie> movies = new ArrayList<>();
                for (int responseIndex = 0; responseIndex < results.length(); responseIndex++) {
                    final Movie movie = gson.fromJson(results.getString(responseIndex), Movie.class);
                    movies.add(movie);
                }
                return Response.success(movies,
                        HttpHeaderParser.parseCacheHeaders(response));
            } catch (JSONException e) {
                return Response.error(new ParseError(e));
            }

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(ArrayList<Movie> response) {
        listener.onResponse(response);
    }

}
