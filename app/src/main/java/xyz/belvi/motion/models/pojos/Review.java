package xyz.belvi.motion.models.pojos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zone2 on 5/6/17.
 */

public class Review implements Parcelable {

    String id, author, content;

    protected Review(Parcel in) {
        id = in.readString();
        author = in.readString();
        content = in.readString();
    }

    public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public String getAuthorId() {
        return this.id;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getContent() {
        return this.content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(author);
        parcel.writeString(content);
    }
}
