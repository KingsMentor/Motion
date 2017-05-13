package xyz.belvi.motion.controllers.dataController;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.google.gson.Gson;

import java.util.ArrayList;

import xyz.belvi.motion.models.pojos.Movie;

/**
 * Created by zone2 on 5/8/17.
 */

public class FavouriteDao {

    private Context mContext;

    public FavouriteDao(Context context) {
        mContext = context;
    }

    public boolean addToFavorites(Movie movie) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FavoriteContract.FavoriteEntry.COLUMN_MOVIE_ID, movie.getId());
        contentValues.put(FavoriteContract.FavoriteEntry.COLUMN_MOVIE_DATA, new Gson().toJson(movie, Movie.class).toString());
        return Integer.parseInt(
                mContext.getContentResolver().insert(FavoriteContract.FavoriteEntry.CONTENT_URI, contentValues).getLastPathSegment()) > 0;
    }

    public boolean removeFavorite(long id) {
        return mContext.getContentResolver().delete(ContentUris.withAppendedId(FavoriteContract.FavoriteEntry.CONTENT_URI, id)
                , FavoriteContract.FavoriteEntry.COLUMN_MOVIE_ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }

    public boolean isFavorite(long movieId) {
        return mContext.getContentResolver().query(FavoriteContract.FavoriteEntry.CONTENT_URI, null
                , FavoriteContract.FavoriteEntry.COLUMN_MOVIE_ID + "=?", new String[]{String.valueOf(movieId)}, null).getCount() > 0;
    }

    public ArrayList<Movie> getFavorites() {
        ArrayList<Movie> movies = new ArrayList<>();
        Cursor resultCursor = mContext.getContentResolver().query(FavoriteContract.FavoriteEntry.CONTENT_URI, null, null, null, null);
        if (resultCursor != null) {
            while (resultCursor.moveToNext()) {
                String movieData = resultCursor.getString(resultCursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_MOVIE_DATA));
                Movie movie = new Gson().fromJson(movieData, Movie.class);
                movies.add(movie);
            }
            resultCursor.close();
        }
        return movies;
    }
}
