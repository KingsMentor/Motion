package xyz.belvi.motion.models.pojos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zone2 on 5/6/17.
 */


public class Trailer implements Parcelable {
    String key, name, id;

    protected Trailer(Parcel in) {
        key = in.readString();
        name = in.readString();
        id = in.readString();
    }

    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    public String getKey() {
        return this.key;
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(key);
        parcel.writeString(name);
        parcel.writeString(id);
    }
}
