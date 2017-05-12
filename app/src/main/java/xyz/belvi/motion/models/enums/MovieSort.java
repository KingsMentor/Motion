package xyz.belvi.motion.models.enums;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.android.volley.Request;

import xyz.belvi.motion.R;

/**
 * Created by zone2 on 4/12/17.
 */

public enum MovieSort {
    POPULAR("movie/popular", Request.Method.GET),
    TOP_RATED("movie/top_rated", Request.Method.GET),
    FAVORITE("", Request.Method.GET);

    private String path;
    private int method;
    private String BASE_URL = "http://api.themoviedb.org/3/";

    MovieSort(String path, int method) {
        this.path = path;
        this.method = method;
    }

    public String getPath() {
        return this.path;
    }

    public int getMethod() {
        return this.method;
    }

    public String buildURl(Context context, int page) {
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Uri.Builder builder = Uri.parse(BASE_URL).buildUpon();
            builder.appendEncodedPath(getPath())
                    .appendQueryParameter("api_key", ai.metaData.getString(context.getString(R.string.movie_db_api_key)))
                    .appendQueryParameter("page", String.valueOf(page));
            return builder.build().toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";

    }
}
