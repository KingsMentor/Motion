package xyz.belvi.motion.controllers.volley.customVolley;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import xyz.belvi.motion.models.pojos.Review;
import xyz.belvi.motion.models.utils.AppUtils;

/**
 * Created by zone2 on 4/12/17.
 */

public class ReviewRequest extends Request<ArrayList<Review>> {

    private final Gson gson = new Gson();
    private final Response.Listener<ArrayList<Review>> listener;


    public ReviewRequest(Context context, long movieId, int page, Response.Listener<ArrayList<Review>> listener, Response.ErrorListener errorListener) {
        super(Method.GET, AppUtils.buildReviewUri(context, movieId), errorListener);
        this.listener = listener;
    }


    @Override
    protected Response<ArrayList<Review>> parseNetworkResponse(NetworkResponse response) {
        try {

            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            try {
                JSONArray results = new JSONObject(json).optJSONArray("results");
                ArrayList<Review> reviews = new ArrayList<>();
                for (int responseIndex = 0; responseIndex < results.length(); responseIndex++) {
                    final Review movie = gson.fromJson(results.getString(responseIndex), Review.class);
                    reviews.add(movie);
                }
                return Response.success(reviews,
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
    protected void deliverResponse(ArrayList<Review> response) {
        listener.onResponse(response);
    }

}
