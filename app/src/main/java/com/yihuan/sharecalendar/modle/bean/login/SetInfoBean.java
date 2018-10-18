package com.yihuan.sharecalendar.modle.bean.login;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ronny on 2017/9/11.
 */

public class SetInfoBean implements Parcelable{
    public String nickName;
    public String singnature;
    public String sex;
    public String year;
    public String constellationDetail;//星座全信息 摩羯座（12月22日─01月19日）
    public String constellationId;//星座id
    public String constellationName;//星座名
    public String provinceName;
    public int provinceId;
    public String cityName;
    public int cityId;

    public SetInfoBean(){}

    protected SetInfoBean(Parcel in) {
        nickName = in.readString();
        singnature = in.readString();
        sex = in.readString();
        year = in.readString();
        constellationDetail = in.readString();
        constellationId = in.readString();
        constellationName = in.readString();
        provinceName = in.readString();
        provinceId = in.readInt();
        cityName = in.readString();
        cityId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nickName);
        dest.writeString(singnature);
        dest.writeString(sex);
        dest.writeString(year);
        dest.writeString(constellationDetail);
        dest.writeString(constellationId);
        dest.writeString(constellationName);
        dest.writeString(provinceName);
        dest.writeInt(provinceId);
        dest.writeString(cityName);
        dest.writeInt(cityId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SetInfoBean> CREATOR = new Creator<SetInfoBean>() {
        @Override
        public SetInfoBean createFromParcel(Parcel in) {
            return new SetInfoBean(in);
        }

        @Override
        public SetInfoBean[] newArray(int size) {
            return new SetInfoBean[size];
        }
    };
}
