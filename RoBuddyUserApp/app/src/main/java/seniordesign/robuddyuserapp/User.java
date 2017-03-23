package seniordesign.robuddyuserapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Issac on 3/1/2017.
 */

public class User implements Parcelable{
    private String name;
    private String uteid;
    private String signature; //haven't quite tested yet, but supposed to be unique ID for user

    public User(String name, String uteid) {
        this.name = name;
        this.uteid = uteid;
        this.signature = name + uteid + Math.random();
    }

    public User(Parcel input) {
        this.name = input.readString();
        this.uteid = input.readString();
        this.signature = input.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.uteid);
        dest.writeString(this.signature);
    }

    public static final Parcelable.Creator<User> CREATOR =
            new Parcelable.Creator<User>() {
                public User createFromParcel(Parcel in) {return new User(in);}

                public User[] newArray(int size) {return new User[size];}
            };

    public String getName() {return this.name;}
    public String getUTEID() {return this.uteid;}
    public String getSignature() {return this.signature;}
}
