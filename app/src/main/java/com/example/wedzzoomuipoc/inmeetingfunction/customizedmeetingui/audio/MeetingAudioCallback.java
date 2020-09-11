package com.example.wedzzoomuipoc.inmeetingfunction.customizedmeetingui.audio;


import com.example.wedzzoomuipoc.inmeetingfunction.customizedmeetingui.MyMeetingActivity;

import us.zoom.sdk.ZoomSDK;
import com.example.wedzzoomuipoc.inmeetingfunction.customizedmeetingui.BaseCallback;
import com.example.wedzzoomuipoc.inmeetingfunction.customizedmeetingui.BaseEvent;
import com.example.wedzzoomuipoc.inmeetingfunction.customizedmeetingui.SimpleInMeetingListener;

public class MeetingAudioCallback extends BaseCallback<MeetingAudioCallback.AudioEvent> {

    public void removeListener(MyMeetingActivity myMeetingActivity) {
    }

    public void addListener(MyMeetingActivity myMeetingActivity) {
    }

    public interface AudioEvent extends BaseEvent {

        void onUserAudioStatusChanged(long userId);

        void onUserAudioTypeChanged(long userId);

        void onMyAudioSourceTypeChanged(int type);
    }

    static MeetingAudioCallback instance;

    private MeetingAudioCallback() {
        init();
    }

    protected void init() {
        ZoomSDK.getInstance().getInMeetingService().addListener(audioListener);
    }

    public static MeetingAudioCallback getInstance() {
        if (null == instance) {
            synchronized (MeetingAudioCallback.class) {
                if (null == instance) {
                    instance = new MeetingAudioCallback();
                }
            }
        }
        return instance;
    }


    SimpleInMeetingListener audioListener = new SimpleInMeetingListener() {

        @Override
        public void onUserAudioStatusChanged(long userId) {

            for (AudioEvent event : callbacks) {
                event.onUserAudioStatusChanged(userId);
            }
        }

        @Override
        public void onUserAudioTypeChanged(long userId) {
            for (AudioEvent event : callbacks) {
                event.onUserAudioTypeChanged(userId);
            }
        }

        @Override
        public void onMyAudioSourceTypeChanged(int type) {
            for (AudioEvent event : callbacks) {
                event.onMyAudioSourceTypeChanged(type);
            }
        }
    };


}
