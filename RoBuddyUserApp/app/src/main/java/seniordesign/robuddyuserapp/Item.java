package seniordesign.robuddyuserapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Issac on 3/1/2017.
 */

public class Item implements Parcelable{
    private String itemName;
    private String currentOwner;


    //some field for time checked out
    //some field for item ID (maybe)

    public Item(String itemName, String currentOwner) {
        this.itemName = itemName;
        this.currentOwner = currentOwner;
    }

    public Item(Parcel input) {
        this.itemName = input.readString();
        this.currentOwner = input.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.itemName);
        dest.writeString(this.currentOwner);
    }

    public static final Parcelable.Creator<Item> CREATOR =
            new Parcelable.Creator<Item>() {
        public Item createFromParcel(Parcel in) {return new Item(in);}

        public Item[] newArray(int size) {return new Item[size];}
    };

    public String getItemName() {return this.itemName;}
    public String getCurrentOwner() {return this.currentOwner;}


}
