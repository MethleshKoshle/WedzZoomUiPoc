package com.example.wedzzoomuipoc;

import android.os.Parcel;
import android.os.Parcelable;

public class RoomInfo implements Parcelable {
    private String roomName = null;
    private String roomId = null;
    private String meetingId = null;
    private String password = null;

    protected RoomInfo(Parcel in) {
        roomName = in.readString();
        roomId = in.readString();
        meetingId = in.readString();
        password = in.readString();
    }

    public static final Creator<RoomInfo> CREATOR = new Creator<RoomInfo>() {
        @Override
        public RoomInfo createFromParcel(Parcel in) {
            return new RoomInfo(in);
        }

        @Override
        public RoomInfo[] newArray(int size) {
            return new RoomInfo[size];
        }
    };

    public RoomInfo() {

    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setMeetingId(String meeting_id) {
        this.meetingId = meeting_id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(roomName);
        parcel.writeString(roomId);
        parcel.writeString(meetingId);
        parcel.writeString(password);
    }
}
