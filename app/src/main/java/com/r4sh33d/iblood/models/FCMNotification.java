package com.r4sh33d.iblood.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.cottacush.fbn.android.utils.DateUtils;

/**
 * Created by rasheed on 12/19/17.
 */

public class FCMNotification implements Parcelable {
    public static final Creator<FCMNotification> CREATOR = new Creator<FCMNotification>() {
        @Override
        public FCMNotification createFromParcel(Parcel in) {
            return new FCMNotification(in);
        }

        @Override
        public FCMNotification[] newArray(int size) {
            return new FCMNotification[size];
        }
    };
    public String title;
    public String body;
    public long timeArrived; //milliseconds since epoch
    public long sentTime;

    public FCMNotification(String title, String body, long timeArrived, long sentTime) {
        this.title = title;
        this.body = body;
        this.timeArrived = timeArrived;
        this.sentTime = sentTime;
    }

    protected FCMNotification(Parcel in) {
        title = in.readString();
        body = in.readString();
        timeArrived = in.readLong();
        sentTime = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(body);
        dest.writeLong(timeArrived);
        dest.writeLong(sentTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getReadableSentTime(){
        return String.valueOf(DateUtils.getRelativeSentFromMessageWithTime(sentTime));
    }

    public String getReadableRecievedTtime(){
       return  String.valueOf(DateUtils.getRelativeSentFromMessageWithTime(timeArrived));
    }
}
