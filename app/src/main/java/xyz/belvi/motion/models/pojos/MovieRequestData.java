package xyz.belvi.motion.models.pojos;

import java.util.ArrayList;

/**
 * Created by zone2 on 4/12/17.
 */

public class MovieRequestData {
    private ArrayList<Movie> movies = new ArrayList<>();
    int pageCount = 0;


    public ArrayList<Movie> getMovies() {
        return this.movies;
    }

    public MovieRequestData setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
        return this;
    }

    public int getPageCount() {
        return this.pageCount;
    }

    public MovieRequestData setPageCount(int pageCount) {
        this.pageCount = pageCount;
        return this;
    }
}
