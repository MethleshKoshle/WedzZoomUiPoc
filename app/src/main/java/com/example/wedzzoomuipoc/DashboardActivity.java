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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.content.Intent;
import android.os.Handler;

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

//public class DashboardActivity extends AppCompatActivity implements MeetingServiceListener{
public class DashboardActivity extends AppCompatActivity implements InitAuthSDKCallback,
        MeetingServiceListener, UserLoginCallback.ZoomDemoAuthenticationListener, OnClickListener{

    private static final String TAG = "ZoomSDKExample";

    private View layoutJoin;
    private View mProgressPanel;

    private ZoomSDK mZoomSDK;
    private boolean isResumed = false;

    RoomInfo room = new RoomInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mZoomSDK = ZoomSDK.getInstance();
//
        mProgressPanel = (View) findViewById(R.id.progressPanel);
        mProgressPanel.setVisibility(View.GONE);
//
        InitAuthSDKHelper.getInstance().initSDK(this, (InitAuthSDKCallback) this);
//
        if (mZoomSDK.isInitialized()) {

            ZoomSDK.getInstance().getMeetingService().addListener(this);
            ZoomSDK.getInstance().getMeetingSettingsHelper().enable720p(true);
        }
    }

    public void onClickRoom1(View view) {
//        Intent intent = new Intent(this, ZoomMeetingActivity.class);

        room.setRoomName("Room-1");
        room.setRoomId("1234");
        room.setMeetingId("94919576908");
        room.setPassword("1234");

        startMeeting(room.getMeetingId(), "ABCD");

//        intent.putExtra("roomName", room); //Put your id to your next Intent
////        intent.putExtra("roomName", "Room-1"); //Put your id to your next Intent
//
//        startActivity(intent);
    }

    public void onClickRoom2(View view) {
        Intent intent = new Intent(this, ZoomMeetingActivity.class);

        room.setRoomName("Room-2");
        room.setRoomId("1234");
        room.setMeetingId("1234");
        room.setPassword("1234");

        intent.putExtra("roomName", room); //Put your id to your next Intent
//        intent.putExtra("roomName", "Room-2"); //Put your id to your next Intent
        startActivity(intent);
    }
    InMeetingNotificationHandle handle=new InMeetingNotificationHandle() {

        @Override
        public boolean handleReturnToConfNotify(Context context, Intent intent) {
            intent = new Intent(context, MyMeetingActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            if(!(context instanceof Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            intent.setAction(InMeetingNotificationHandle.ACTION_RETURN_TO_CONF);
            context.startActivity(intent);
            return true;
        }
    };

    @Override
    public void onZoomSDKInitializeResult(int errorCode, int internalErrorCode) {
        Log.i(TAG, "onZoomSDKInitializeResult, errorCode=" + errorCode + ", internalErrorCode=" + internalErrorCode);

        if (errorCode != ZoomError.ZOOM_ERROR_SUCCESS) {
            Toast.makeText(this, "Failed to initialize Zoom SDK. Error: " + errorCode + ", internalErrorCode=" + internalErrorCode, Toast.LENGTH_LONG).show();
        } else {
            ZoomSDK.getInstance().getMeetingSettingsHelper().enable720p(false);
            ZoomSDK.getInstance().getMeetingSettingsHelper().enableShowMyMeetingElapseTime(true);
            ZoomSDK.getInstance().getMeetingService().addListener(this);
            ZoomSDK.getInstance().getMeetingSettingsHelper().setCustomizedNotificationData(null, handle);
            Toast.makeText(this, "Initialize Zoom SDK successfully.", Toast.LENGTH_LONG).show();
            if (mZoomSDK.tryAutoLoginZoom() == ZoomApiError.ZOOM_API_ERROR_SUCCESS) {
                UserLoginCallback.getInstance().addListener(this);
                showProgressPanel(true);
            } else {
                showProgressPanel(false);
            }
        }
    }
    @Override
    public void onClick(View v) {
        if(!mZoomSDK.isInitialized())
        {
            Toast.makeText(this,"4 Init SDK First", Toast.LENGTH_SHORT).show();
            InitAuthSDKHelper.getInstance().initSDK(this, this);
            return;
        }
//        if (v.getId() == R.id.btnEmailLogin) {
//            showEmailLoginActivity();
//        } else if (v.getId() == R.id.btnSSOLogin) {
//            showSSOLoginActivity();
//        } else if (v.getId() == R.id.btnWithoutLogin) {
//            showAPIUserActivity();
//        }else if(v.getId()==R.id.btn_return)
//        {
            MeetingWindowHelper.getInstance().hiddenMeetingWindow(true);
            Intent intent = new Intent(this, MyMeetingActivity.class);
            // Additional
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
//        }

    }

    public void startMeeting(String number, String name){
        // 1.
//        if(!mZoomSDK.isInitialized())
//        {
//            Toast.makeText(this,"Init SDK First", Toast.LENGTH_SHORT).show();
//            InitAuthSDKHelper.getInstance().initSDK(this, this);
//            return;
//        }
//        MeetingWindowHelper.getInstance().hiddenMeetingWindow(true);
//        Intent intent = new Intent(this, MyMeetingActivity.class);
//        // Additional
//        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//        startActivity(intent);

        // 2.
        if(!mZoomSDK.isInitialized())
        {
            Toast.makeText(this,"Init SDK First",Toast.LENGTH_SHORT).show();
            InitAuthSDKHelper.getInstance().initSDK(this, this);
            return;
        }

        if (ZoomSDK.getInstance().getMeetingSettingsHelper().isCustomizedMeetingUIEnabled()) {
            ZoomSDK.getInstance().getSmsService().enableZoomAuthRealNameMeetingUIShown(false);
        } else {
            ZoomSDK.getInstance().getSmsService().enableZoomAuthRealNameMeetingUIShown(true);
        }
        JoinMeetingParams params = new JoinMeetingParams();
        params.meetingNo = number;
        params.displayName = name;
        JoinMeetingOptions options=new JoinMeetingOptions();
        ZoomSDK.getInstance().getMeetingService().joinMeetingWithParams(this, params, ZoomMeetingUISettingHelper.getJoinMeetingOptions());
    }

    @Override
    public void onZoomSDKLoginResult(long result) {
        if ((int) result == ZoomAuthenticationError.ZOOM_AUTH_ERROR_SUCCESS) {
            finish();
        } else {
            showProgressPanel(false);
        }
    }

    @Override
    public void onZoomSDKLogoutResult(long result) {

    }

    @Override
    public void onZoomIdentityExpired() {
        if (mZoomSDK.isLoggedIn()) {
            mZoomSDK.logoutZoom();
        }
    }

    @Override
    public void onZoomAuthIdentityExpired(){
        Log.e(TAG,"onZoomAuthIdentityExpired");
    }

    private void showProgressPanel(boolean show) {
        if (show) {
            mProgressPanel.setVisibility(View.VISIBLE);
            View view = findViewById(R.id.btnSettings);
            if (null != view) {
                view.setVisibility(View.GONE);
            }
        } else {
            View view = findViewById(R.id.btnSettings);
            if (null != view) {
                view.setVisibility(View.VISIBLE);
            }
            mProgressPanel.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isResumed = true;
        refreshUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isResumed = false;
    }

    private void refreshUI() {
        if(!ZoomSDK.getInstance().isInitialized())
        {
            return;
        }
        MeetingStatus meetingStatus = ZoomSDK.getInstance().getMeetingService().getMeetingStatus();
        if (ZoomSDK.getInstance().getMeetingSettingsHelper().isCustomizedMeetingUIEnabled()) {
            if (meetingStatus == MeetingStatus.MEETING_STATUS_INMEETING && isResumed) {
                MeetingWindowHelper.getInstance().showMeetingWindow(this);
                showProgressPanel(true);
                mProgressPanel.setVisibility(View.GONE);
            } else {
                MeetingWindowHelper.getInstance().hiddenMeetingWindow(true);
                showProgressPanel(false);
            }
        }
    }

    @Override
    public void onMeetingStatusChanged(MeetingStatus meetingStatus, int errorCode, int internalErrorCode) {
        Log.d(TAG,"onMeetingStatusChanged "+ meetingStatus +":"+ errorCode);
        if(!ZoomSDK.getInstance().isInitialized())
        {
            showProgressPanel(false);
            return;
        }
        if (ZoomSDK.getInstance().getMeetingSettingsHelper().isCustomizedMeetingUIEnabled()) {
            if (meetingStatus == MeetingStatus.MEETING_STATUS_CONNECTING) {
                showMeetingUi();
            }
        }
        refreshUI();
    }

    private void showMeetingUi() {
        if (ZoomSDK.getInstance().getMeetingSettingsHelper().isCustomizedMeetingUIEnabled()) {
            SharedPreferences sharedPreferences = getSharedPreferences("UI_Setting", Context.MODE_PRIVATE);
            boolean enable = sharedPreferences.getBoolean("enable_rawdata", false);
            Intent intent = null;
            if (!enable) {
                intent = new Intent(this, MyMeetingActivity.class);
                intent.putExtra("from", MyMeetingActivity.JOIN_FROM_UNLOGIN);
            } else {
                intent = new Intent(this, RawDataMeetingActivity.class);
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            this.startActivity(intent);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MeetingWindowHelper.getInstance().onActivityResult(requestCode, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        UserLoginCallback.getInstance().removeListener(this);

        if(null!= ZoomSDK.getInstance().getMeetingService())
        {
            ZoomSDK.getInstance().getMeetingService().removeListener(this);
        }
        InitAuthSDKHelper.getInstance().reset();
    }
}
