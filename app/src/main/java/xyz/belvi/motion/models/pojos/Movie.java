package xyz.belvi.motion.models.pojos;

/**
 * Created by zone2 on 4/11/17.
 */


public class Movie {

    private String poster_path, overview, release_date, original_title, original_language, title, backdrop_path;
    private long[] genre_ids;
    private long id, video_count;
    private double popularity, vote_average;
    private boolean adult;

    public String getPosterPath() {
        return this.poster_path;
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

    public String getBackdropPath() {
        return this.backdrop_path;
    }

    public long getId() {
        return this.id;
    }

    public long getVideoCount() {
        return this.video_count;
    }

    public double getVoteAverage() {
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
}
