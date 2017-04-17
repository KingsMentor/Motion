package xyz.belvi.motion.models.enums;

/**
 * Created by zone2 on 4/17/17.
 */

public enum MoviePosterSize {
    w92(92), w154(154), w185(185), w342(342), w500(500), w780(780);

    int size;

    MoviePosterSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }
}
