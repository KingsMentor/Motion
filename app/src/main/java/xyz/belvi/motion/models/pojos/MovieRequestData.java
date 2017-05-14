package xyz.belvi.motion.models.pojos;

import java.util.ArrayList;

/**
 * Created by zone2 on 4/12/17.
 */

public class MovieRequestData {
    private ArrayList<Movie> movies = new ArrayList<>();
    int pageCount;


    public ArrayList<Movie> getMovies() {
        return this.movies;
    }

    public int getPageCount() {
        return this.pageCount;
    }

    public MovieRequestData setPageCount(int pageCount) {
        this.pageCount = pageCount;
        return this;
    }
}
