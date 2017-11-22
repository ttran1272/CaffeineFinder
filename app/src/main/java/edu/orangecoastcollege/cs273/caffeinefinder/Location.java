package edu.orangecoastcollege.cs273.caffeinefinder;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The <code>Location</code> class represents a place where one can get a caffeine fix, including
 * its name, address, phone number and latitude/longitude location.
 *
 * @author Michael Paulding
 */

public class Location implements Parcelable {
    private long mId;
    private String mName;
    private String mAddress;
    private String mCity;
    private String mState;
    private String mZipCode;
    private String mPhone;
    private double mLatitude;
    private double mLongitude;

    public Location(long id, String name, String address, String city, String state, String zipCode, String phone, double latitude, double longitude) {
        mId = id;
        mName = name;
        mAddress = address;
        mCity = city;
        mState = state;
        mZipCode = zipCode;
        mPhone = phone;
        mLatitude = latitude;
        mLongitude = longitude;
    }

    public Location(String name, String address, String city, String state, String zipCode, String phone, double latitude, double longitude) {
        this(-1, name, address, city, state, zipCode, phone, latitude, longitude);
    }


    // Parcelable interface uses a private constructor to instantiate objects
    private Location(Parcel parcel){
        // ORDER MATTERS!!!
        mId = parcel.readLong();
        mName = parcel.readString();
        mAddress = parcel.readString();
        mCity = parcel.readString();
        mState = parcel.readString();
        mZipCode = parcel.readString();
        mPhone = parcel.readString();
        mLatitude = parcel.readDouble();
        mLongitude = parcel.readDouble();
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        mState = state;
    }

    public String getZipCode() {
        return mZipCode;
    }

    public void setZipCode(String zipCode) {
        mZipCode = zipCode;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public String getFullAddress()
    {
        return mAddress + "\n" + mCity + ", " + mState + "  " + mZipCode;
    }

    @Override
    public String toString() {
        return "Location{" +
                "Id=" + mId +
                ", Name='" + mName + '\'' +
                ", Address='" + mAddress + '\'' +
                ", City='" + mCity + '\'' +
                ", State='" + mState + '\'' +
                ", Zip Code='" + mZipCode + '\'' +
                ", Phone='" + mPhone + '\'' +
                ", Latitude=" + mLatitude +
                ", Longitude=" + mLongitude +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(mId);
        parcel.writeString(mName);
        parcel.writeString(mAddress);
        parcel.writeString(mCity);
        parcel.writeString(mState);
        parcel.writeString(mZipCode);
        parcel.writeString(mPhone);
        parcel.writeDouble(mLatitude);
        parcel.writeDouble(mLongitude);
    }

    // In order to read a Parcel, we need a CREATOR (STATIC FIELD)
    public static final Parcelable.Creator<Location> CREATOR = new Creator<Location>() {
        /**
         * This method is used with Intents to create new Location objects.
         * @param parcel The package with all information for the Location
         * @return The new Location object
         */
        @Override
        public Location createFromParcel(Parcel parcel) {
            //return null;
            return new Location(parcel);
        }

        /**
         * This method is used with JSON to create an array of Location objects
         * @param size  The size of the JSON array (how many Locations)
         * @return New array of Locations
         */
        @Override
        public Location[] newArray(int size) {
            //return new Game[0];
            return new Location[size];
        }
    };
}
