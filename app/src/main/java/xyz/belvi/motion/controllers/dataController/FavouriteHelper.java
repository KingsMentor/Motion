package xyz.belvi.motion.controllers.dataController;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by zone2 on 5/8/17.
 */

public class FavouriteHelper {

    public FavouriteHelper() {

    }

    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "
                + FavoriteContract.FavoriteEntry.TABLE_NAME + "( "
                + FavoriteContract.FavoriteEntry.COLUMN_MOVIE_ID + " INTEGER UNIQUE, " +
                "" + FavoriteContract.FavoriteEntry.COLUMN_MOVIE_DATA + " VARCHAR );";
        db.execSQL(sql);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
