package seniordesign.robuddyuserapp;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ArrayAdapter;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;


import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * Created by Issac on 3/1/2017.
 */


@DynamoDBTable(tableName = "RequestTable")
public class User implements Parcelable{

    private SecureRandom random = new SecureRandom();
    public String nextSessionId() {
        return new BigInteger(130, random).toString(32);
    }

    private String name;
    private String eid;
    private String requestID; //haven't quite tested yet, but supposed to be unique ID for user
    private String item;
    private String tableNum;

    private ArrayList<String> checkedout;


    @DynamoDBAttribute(attributeName = "Item")
    public String getItem() {
        return item;
    }
    public void setItem(String item) {
        this.item = item;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "TableNum")
    public String getTableNum() {
        return tableNum;
    }
    public void setTableNum(String tableNum) {
        this.tableNum = tableNum;
    }

    @DynamoDBAttribute(attributeName = "EID")
    public String getEID() {
        return eid;
    }
    public void setEID(String eid) {
        this.eid = eid;
    }

    @DynamoDBHashKey(attributeName = "RequestID")
    public String getRequestID() {
        return requestID;
    }
    public void setSignature() {
        this.requestID = this.name + "-" + this.eid + "-" + nextSessionId();
    }

    public void addCheckedoutList(String item) {
        this.checkedout.add(item);
    }

    public void removeCheckedoutList(String item) {
        if(this.checkedout.contains(item)) {
            this.checkedout.remove(item);
        }
    }

    public void newList() {
        this.checkedout = new ArrayList<String>();
    }

    public ArrayList<String> getCheckedout() {
        return this.checkedout;
    }

    public User(String name, String eid) {
        this.name = name;
        this.eid = eid;
        this.requestID = name + "-" + eid + "-" + nextSessionId();
        this.checkedout = new ArrayList<String>();
    }

    public User(Parcel input) {
        this.name = input.readString();
        this.eid = input.readString();
        this.requestID = input.readString();
        this.item = input.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.eid);
        dest.writeString(this.requestID);
        dest.writeString(this.item);
    }

    public static final Parcelable.Creator<User> CREATOR =
            new Parcelable.Creator<User>() {
                public User createFromParcel(Parcel in) {return new User(in);}

                public User[] newArray(int size) {return new User[size];}
            };


}
