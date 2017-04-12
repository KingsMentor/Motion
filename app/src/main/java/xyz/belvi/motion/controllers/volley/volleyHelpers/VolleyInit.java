package xyz.belvi.motion.controllers.volley.volleyHelpers;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by zone2 on 4/12/17.
 */

public class VolleyInit {

    public static final String TAG = VolleyInit.class
            .getSimpleName();

    private RequestQueue mRequestQueue;
    private Context mContext;

    public void initVolley(Context context) {
        this.mContext = context;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext);
        }

        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }
}
