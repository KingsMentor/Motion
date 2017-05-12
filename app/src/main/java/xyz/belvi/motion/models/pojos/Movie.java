package xyz.belvi.motion.models.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import xyz.belvi.motion.models.enums.MoviePosterSize;

/**
 * Created by zone2 on 4/11/17.
 */


public class Movie implements Parcelable {

    private String poster_path, overview, release_date, original_title, original_language, title, backdrop_path;
    private long[] genre_ids;
    private long id, video_count,dbId;
    private float popularity, vote_average;
    private boolean adult,isFavorite;
    private final String IMG_PATH = "http://image.tmdb.org/t/p/w";

    protected Movie(Parcel in) {
        poster_path = in.readString();
        overview = in.readString();
        release_date = in.readString();
        original_title = in.readString();
        original_language = in.readString();
        title = in.readString();
        backdrop_path = in.readString();
        genre_ids = in.createLongArray();
        id = in.readLong();
        video_count = in.readLong();
        dbId = in.readLong();
        popularity = in.readFloat();
        vote_average = in.readFloat();
        adult = in.readByte() != 0;
        isFavorite = in.readByte() != 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getPosterPath(MoviePosterSize moviePosterSize) {
        return IMG_PATH + moviePosterSize.getSize() + "/" + this.poster_path;
    }

    public String getOverview() {
        return this.overview;
    }

    public String getReleaseDate() {
        return this.release_date;
    }

    public String getOriginalTitle() {
        return this.original_title;
    }

    public String getOriginalLanguage() {
        return this.original_language;
    }

    public String getTitle() {
        return this.title;
    }

    public String getBackdropPath(MoviePosterSize moviePosterSize) {
        return IMG_PATH + moviePosterSize.getSize() + "/" + this.backdrop_path;

    }

    public long getId() {
        return this.id;
    }

    public long getVideoCount() {
        return this.video_count;
    }

    public float getVoteAverage() {
        return this.vote_average;
    }

    public double getPopularity() {
        return this.popularity;
    }

    public boolean isAdult() {
        return this.adult;
    }

    public long[] getGenreIds() {
        return this.genre_ids;
    }

    public boolean isFavorite() {
        return this.isFavorite;
    }

    public Movie setFavorite(boolean favorite) {
        this.isFavorite = favorite;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(poster_path);
        parcel.writeString(overview);
        parcel.writeString(release_date);
        parcel.writeString(original_title);
        parcel.writeString(original_language);
        parcel.writeString(title);
        parcel.writeString(backdrop_path);
        parcel.writeLongArray(genre_ids);
        parcel.writeLong(id);
        parcel.writeLong(video_count);
        parcel.writeLong(dbId);
        parcel.writeFloat(popularity);
        parcel.writeFloat(vote_average);
        parcel.writeByte((byte) (adult ? 1 : 0));
        parcel.writeByte((byte) (isFavorite ? 1 : 0));
    }

    public long getDbId() {
        return this.dbId;
    }

    public Movie setDbId(long dbId) {
        this.dbId = dbId;
        return this;
    }
}
