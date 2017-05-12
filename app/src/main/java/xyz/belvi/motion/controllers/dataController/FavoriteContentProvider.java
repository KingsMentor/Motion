package xyz.belvi.motion.controllers.dataController;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by zone2 on 5/8/17.
 */

public class FavoriteContentProvider extends ContentProvider {
    public static final int FAV_WITH_ID = 1;
    public static final int FAVS = 2;

    private UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(FavoriteContract.AUTHORITY, FavoriteContract.PATH_FAV + "/#", FAV_WITH_ID);
        uriMatcher.addURI(FavoriteContract.AUTHORITY, FavoriteContract.PATH_FAV, FAVS);

        return uriMatcher;
    }

    private Favorite mFavorite;

    @Override
    public boolean onCreate() {
        mFavorite = new Favorite(getContext(), FavoriteContract.DB_NAME, null, FavoriteContract.DB_VERSION);
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        int match = sUriMatcher.match(uri);
        SQLiteDatabase db = mFavorite.getReadableDatabase();
        Uri resultUri;
        Cursor resultCursor;
        switch (match) {
            case FAVS:
                resultCursor = db.query(FavoriteContract.FavoriteEntry.TABLE_NAME, strings, s, strings1, null, null, s1);
                resultUri = ContentUris.withAppendedId(FavoriteContract.FavoriteEntry.CONTENT_URI, resultCursor.getCount());
                break;
            default:
                throw new SQLException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(resultUri, null);
        db.close();
        return resultCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int match = sUriMatcher.match(uri);

        switch (match) {
            case FAVS:
                // directory
                return "vnd.android.cursor.dir" + "/" + FavoriteContract.AUTHORITY + "/" + FavoriteContract.PATH_FAV;
            case FAV_WITH_ID:
                // single item type
                return "vnd.android.cursor.item" + "/" + FavoriteContract.AUTHORITY + "/" + FavoriteContract.PATH_FAV;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        int match = sUriMatcher.match(uri);
        SQLiteDatabase db = mFavorite.getWritableDatabase();
        Uri resultUri;
        switch (match) {
            case FAV_WITH_ID:
                throw new SQLException("row insertion not supported");
            case FAVS:
                long id = db.insert(FavoriteContract.FavoriteEntry.TABLE_NAME, null, contentValues);
                if (id > 0) {
                    resultUri = ContentUris.withAppendedId(FavoriteContract.FavoriteEntry.CONTENT_URI, id);
                    break;
                } else {
                    throw new SQLException("failed to insert row into " + uri);
                }
            default:
                throw new SQLException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(resultUri, null);
        db.close();
        return resultUri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        int match = sUriMatcher.match(uri);
        SQLiteDatabase db = mFavorite.getWritableDatabase();
        Uri resultUri;
        int rowsDeleted = 0;
        switch (match) {
            case FAV_WITH_ID:
                rowsDeleted = db.delete(FavoriteContract.FavoriteEntry.TABLE_NAME, s, strings);
                if (rowsDeleted > 0) {
                    resultUri = ContentUris.withAppendedId(FavoriteContract.FavoriteEntry.CONTENT_URI, rowsDeleted);
                    break;
                } else {
                    throw new SQLException("no row with data " + uri);
                }
            default:
                throw new SQLException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(resultUri, null);
        db.close();
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
