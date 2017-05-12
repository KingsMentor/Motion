package xyz.belvi.motion.controllers.dataController;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by zone2 on 5/8/17.
 */

public class FavoriteContract {

    public static final String AUTHORITY = "xyz.belvi.motion.authority";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_FAV = "favorite";
    public static final String DB_NAME = "favorite_database";
    public static final int DB_VERSION = 2;

    public static class FavoriteEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAV).build();
        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_MOVIE_DATA = "movie_json_data";
        public static final String COLUMN_MOVIE_ID = "movie_id";
    }
}
