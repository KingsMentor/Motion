package xyz.belvi.motion.models.pojos;

import java.util.ArrayList;

/**
 * Created by zone2 on 4/12/17.
 */

public class ReviewRequestData {
    private ArrayList<Review> reviews = new ArrayList<>();
    int pageCount = 0;
    boolean reachedPageEnd = false;


    public ReviewRequestData(ArrayList<Review> reviews, int pageCount, boolean reachedPageEnd) {
        this.reviews = reviews;
        this.pageCount = pageCount;
        this.reachedPageEnd = reachedPageEnd;
    }

    public ArrayList<Review> getReviews() {
        return this.reviews;
    }

    public int getPageCount() {
        return this.pageCount;
    }

    public boolean isReachedPageEnd() {
        return this.reachedPageEnd;
    }

    public ReviewRequestData setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
        return this;
    }

    public ReviewRequestData setPageCount(int pageCount) {
        this.pageCount = pageCount;
        return this;
    }

    public ReviewRequestData setReachedPageEnd(boolean reachedPageEnd) {
        this.reachedPageEnd = reachedPageEnd;
        return this;
    }

    public void nextPage() {
        this.pageCount += 1;
    }
}
