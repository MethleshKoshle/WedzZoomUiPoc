package com.example.wedzzoomuipoc;

import us.zoom.sdk.InMeetingNotificationHandle;
import us.zoom.sdk.JoinMeetingOptions;
import us.zoom.sdk.JoinMeetingParams;
import us.zoom.sdk.MeetingActivity;
import us.zoom.sdk.MeetingServiceListener;
import us.zoom.sdk.MeetingStatus;
import us.zoom.sdk.ZoomApiError;
import us.zoom.sdk.ZoomAuthenticationError;
import us.zoom.sdk.ZoomError;
import us.zoom.sdk.ZoomSDK;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Intent;
import android.os.Handler;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wedzzoomuipoc.initsdk.InitAuthSDKCallback;
import com.example.wedzzoomuipoc.initsdk.InitAuthSDKHelper;
import com.example.wedzzoomuipoc.inmeetingfunction.customizedmeetingui.MyMeetingActivity;
import com.example.wedzzoomuipoc.inmeetingfunction.customizedmeetingui.RawDataMeetingActivity;
import com.example.wedzzoomuipoc.inmeetingfunction.customizedmeetingui.view.MeetingWindowHelper;
import com.example.wedzzoomuipoc.inmeetingfunction.zoommeetingui.ZoomMeetingUISettingHelper;
import com.example.wedzzoomuipoc.startjoinmeeting.UserLoginCallback;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ZoomMeetingActivity  extends AppCompatActivity{

    private static final String TAG = "ZoomSDKExample";

    private View layoutJoin;
    private View mProgressPanel;

    private Button mReturnMeeting;


    private ZoomSDK mZoomSDK;
    private boolean isResumed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);

        RoomInfo currRoom = null;
        //in SecondActivity
        if(getIntent().getExtras() != null) {
            currRoom = getIntent().getParcelableExtra("roomName");
        }
        Toast toast = Toast.makeText(getApplicationContext(),
                currRoom.getRoomName(),
                Toast.LENGTH_SHORT);
        toast.show();

        ImageView img = (ImageView) findViewById(R.id.imageView2);
//        TextView tv = (TextView)findViewById(R.id.textView);

        String newString = currRoom.getRoomName();
        if(newString.equals("Room-1")) {
            img.setImageResource(R.drawable.wedz_2);
            setTitle(newString);
//            tv.setText("Room 1");
        }
        else {
            img.setImageResource(R.drawable.wedz_3);
            setTitle(newString);
//            tv.setText("Room 2");
        }
    }

}
