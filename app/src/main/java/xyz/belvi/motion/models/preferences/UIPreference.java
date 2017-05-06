package xyz.belvi.motion.models.preferences;

import android.content.Context;

import xyz.belvi.motion.models.enums.MovieSort;

/**
 * Created by zone2 on 4/18/17.
 */

public class UIPreference {
    private final String SORT_KEY = "xyz.belvi.motion.models.preferences.UIPreference.SORT_KEY";
    private Context mContext;

    public UIPreference(Context context) {
        mContext = context;
    }

    public void setSortType(MovieSort movieSort) {
        mContext.getSharedPreferences(generatePath(), Context.MODE_PRIVATE).edit().putString(SORT_KEY, movieSort.name()).commit();
    }

    public MovieSort getSortType() {
        return MovieSort.valueOf(mContext.getSharedPreferences(generatePath(), Context.MODE_PRIVATE).getString(SORT_KEY, MovieSort.POPULAR.name()));
    }

    private String generatePath() {
        return mContext.getPackageName() + "." + UIPreference.class.getPackage() + "." + UIPreference.class.getName();
    }
}
