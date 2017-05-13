package xyz.belvi.motion.models.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import xyz.belvi.motion.R;

/**
 * Created by zone2 on 5/12/17.
 */

public class AppUtils {

    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";


    public static String buildTrailerUri(Context context, long motionId) {
        return getUri(context, "videos", motionId, 0, true);
    }

    public static String buildReviewUri(Context context, long motionId, int page) {
        return getUri(context, "reviews", motionId, page, false);
    }

    private static String getUri(Context context, String path, long motionId, int page, boolean trailer) {
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            String url =
                    trailer ?
                            BASE_URL + motionId + "/" + path + "?api_key=" + ai.metaData.getString(context.getString(R.string.movie_db_api_key))
                            :
                            BASE_URL + motionId + "/" + path + "?page=" + page + "&api_key=" + ai.metaData.getString(context.getString(R.string.movie_db_api_key));
            return url;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";

    }
}
