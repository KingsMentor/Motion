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

import xyz.belvi.motion.models.pojos.Trailer;
import xyz.belvi.motion.models.utils.AppUtils;

/**
 * Created by zone2 on 4/12/17.
 */

public class TrailerRequest extends Request<ArrayList<Trailer>> {

    private final Gson gson = new Gson();
    private final Response.Listener<ArrayList<Trailer>> listener;


    public TrailerRequest(Context context, long movieId, Response.Listener<ArrayList<Trailer>> listener, Response.ErrorListener errorListener) {
        super(Method.GET, AppUtils.buildTrailerUri(context, movieId), errorListener);
        this.listener = listener;
    }


    @Override
    protected Response<ArrayList<Trailer>> parseNetworkResponse(NetworkResponse response) {
        try {

            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            try {
                JSONArray results = new JSONObject(json).optJSONArray("results");
                ArrayList<Trailer> trailers = new ArrayList<>();
                for (int responseIndex = 0; responseIndex < results.length(); responseIndex++) {
                    final Trailer movie = gson.fromJson(results.getString(responseIndex), Trailer.class);
                    trailers.add(movie);
                }
                return Response.success(trailers,
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
    protected void deliverResponse(ArrayList<Trailer> response) {
        listener.onResponse(response);
    }

}
