package com.yihuan.sharecalendar.modle.bean.active;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ronny on 2017/12/12.
 */

public class LocationBean implements Parcelable {
    public String addressStr = "";
    public String area = "";
    public Double latitude = 0.0;
    public Double longitude = 0.0;

    public LocationBean() {
    }

    protected LocationBean(Parcel in) {
        addressStr = in.readString();
        area = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Creator<LocationBean> CREATOR = new Creator<LocationBean>() {
        @Override
        public LocationBean createFromParcel(Parcel in) {
            return new LocationBean(in);
        }

        @Override
        public LocationBean[] newArray(int size) {
            return new LocationBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(addressStr);
        dest.writeString(area);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }
}
