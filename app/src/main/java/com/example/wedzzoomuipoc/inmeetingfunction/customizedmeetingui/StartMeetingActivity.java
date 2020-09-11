package com.example.wedzzoomuipoc.inmeetingfunction.customizedmeetingui;

//package com.example.wedzzoomuipoc.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import us.zoom.sdk.InMeetingNotificationHandle;
import us.zoom.sdk.JoinMeetingOptions;
import us.zoom.sdk.JoinMeetingParams;
import us.zoom.sdk.MeetingServiceListener;
import us.zoom.sdk.MeetingStatus;
import us.zoom.sdk.StartMeetingOptions;
import us.zoom.sdk.ZoomApiError;
import us.zoom.sdk.ZoomAuthenticationError;
import us.zoom.sdk.ZoomError;
import us.zoom.sdk.ZoomSDK;
import com.example.wedzzoomuipoc.R;
import com.example.wedzzoomuipoc.initsdk.InitAuthSDKCallback;
import com.example.wedzzoomuipoc.initsdk.InitAuthSDKHelper;
import com.example.wedzzoomuipoc.inmeetingfunction.customizedmeetingui.view.MeetingWindowHelper;
import com.example.wedzzoomuipoc.inmeetingfunction.zoommeetingui.ZoomMeetingUISettingHelper;
import com.example.wedzzoomuipoc.startjoinmeeting.UserLoginCallback;
import com.example.wedzzoomuipoc.ui.APIUserStartJoinMeetingActivity;
import com.example.wedzzoomuipoc.ui.EmailUserLoginActivity;
import com.example.wedzzoomuipoc.ui.LoginUserStartJoinMeetingActivity;
import com.example.wedzzoomuipoc.ui.MeetingSettingActivity;
import com.example.wedzzoomuipoc.ui.SSOUserLoginActivity;

public class StartMeetingActivity extends Activity implements InitAuthSDKCallback,
        MeetingServiceListener, UserLoginCallback.ZoomDemoAuthenticationListener, OnClickListener {

    private final static String TAG = "ZoomSDKExample";

    // Extra
    private static StartMeetingOptions meetingOptions = new StartMeetingOptions();


//    private Button mBtnEmailLogin;
//    private Button mBtnSSOLogin;
//    private Button mBtnWithoutLogin;
//    private View layoutJoin;
    private View mProgressPanel;
//    private EditText numberEdit;
//    private EditText nameEdit;

    private Button mBtnRoom_1;
    private Button mBtnRoom_2;

    private ZoomSDK mZoomSDK;

//    private Button mReturnMeeting;

    private boolean isResumed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mZoomSDK = ZoomSDK.getInstance();
        if (mZoomSDK.isLoggedIn()) {
            finish();
            showEmailLoginUserStartJoinActivity();
            return;
        }

        setContentView(R.layout.switch_activity);

        mBtnRoom_1 = (Button) findViewById(R.id.Room_1);
        mBtnRoom_1.setOnClickListener(this);
        mBtnRoom_2 = (Button) findViewById(R.id.Room_2);
        mBtnRoom_2.setOnClickListener(this);

//        mBtnEmailLogin = (Button) findViewById(R.id.btnEmailLogin);
//        mBtnEmailLogin.setOnClickListener(this);
//
//        mBtnSSOLogin = (Button) findViewById(R.id.btnSSOLogin);
//        mBtnSSOLogin.setOnClickListener(this);
//
//        mBtnWithoutLogin = (Button) findViewById(R.id.btnWithoutLogin);
//        mBtnWithoutLogin.setOnClickListener(this);
        mProgressPanel = (View) findViewById(R.id.progressPanelBothRooms);
//
//        mReturnMeeting = findViewById(R.id.btn_return);
//        mReturnMeeting.setOnClickListener(this);
//
//        layoutJoin = findViewById(R.id.layout_join);
//        numberEdit = findViewById(R.id.edit_join_number);
//        nameEdit = findViewById(R.id.edit_join_name);
        mProgressPanel.setVisibility(View.GONE);

        InitAuthSDKHelper.getInstance().initSDK(this, this);

        if (mZoomSDK.isInitialized()) {
//            mBtnEmailLogin.setVisibility(ZoomSDK.getInstance().isEmailLoginEnable() ? View.VISIBLE : View.GONE);
//            mBtnSSOLogin.setVisibility(View.VISIBLE);
//            mBtnWithoutLogin.setVisibility(View.VISIBLE);
//            layoutJoin.setVisibility(View.VISIBLE);
//
//            View view = findViewById(R.id.btnSettings);
//            if (null != view) {
//                view.setVisibility(View.VISIBLE);
//            }
            ZoomSDK.getInstance().getMeetingService().addListener(this);
            ZoomSDK.getInstance().getMeetingSettingsHelper().enable720p(true);
        } else {
//            mBtnEmailLogin.setVisibility(View.GONE);
//            mBtnSSOLogin.setVisibility(View.GONE);
//            mBtnWithoutLogin.setVisibility(View.GONE);
//            layoutJoin.setVisibility(View.GONE);
        }

//        if(MyMeetingActivity.cnt==1){
//        startMeeting("96125668341", "ABCD");
//            onZoomSDKInitializeResult(0, 1);
//        }
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
        MyMeetingActivity.cnt++;
        Log.i(TAG, "onZoomSDKInitializeResult, errorCode=" + errorCode + ", internalErrorCode=" + internalErrorCode);

        if (errorCode != ZoomError.ZOOM_ERROR_SUCCESS) {
            Toast.makeText(this, "Failed to initialize Zoom SDK. Error: " + errorCode + ", internalErrorCode=" + internalErrorCode, Toast.LENGTH_LONG).show();
        } else {
            ZoomSDK.getInstance().getMeetingSettingsHelper().enable720p(false);
            ZoomSDK.getInstance().getMeetingSettingsHelper().enableShowMyMeetingElapseTime(true);
            ZoomSDK.getInstance().getMeetingService().addListener(this);
            ZoomSDK.getInstance().getMeetingSettingsHelper().setCustomizedNotificationData(null, handle);
            Toast.makeText(this, "1 Initialize Zoom SDK successfully.", Toast.LENGTH_LONG).show();
            if (mZoomSDK.tryAutoLoginZoom() == ZoomApiError.ZOOM_API_ERROR_SUCCESS) {
                UserLoginCallback.getInstance().addListener(this);
                showProgressPanel(true);
            } else {
                showProgressPanel(false);
            }
        }
//        Toast.makeText(this, "Segment-1 Turn: "+String.valueOf(MyMeetingActivity.cnt), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
//        Toast.makeText(this,"4 Init SDK First",Toast.LENGTH_SHORT).show();
        if(!mZoomSDK.isInitialized())
        {
            Toast.makeText(this,"4 Init SDK First", Toast.LENGTH_SHORT).show();
            InitAuthSDKHelper.getInstance().initSDK(this, this);
            return;
        }
        if (v.getId() == R.id.Room_1) {
            MeetingWindowHelper.getInstance().hiddenMeetingWindow(true);
            Intent intent = new Intent(this, MyMeetingActivity.class);
            // Additional
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        }else if(v.getId()==R.id.Room_2)
        {
            MeetingWindowHelper.getInstance().hiddenMeetingWindow(true);
            Intent intent = new Intent(this, MyMeetingActivity.class);
            // Additional
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        }
    }

    public void onClickSettings(View view) {
        if(!mZoomSDK.isInitialized())
        {
            Toast.makeText(this,"3 Init SDK First", Toast.LENGTH_SHORT).show();
            InitAuthSDKHelper.getInstance().initSDK(this, this);
            return;
        }
        startActivity(new Intent(this, MeetingSettingActivity.class));
    }

    @Override
    public void onZoomSDKLoginResult(long result) {
        if ((int) result == ZoomAuthenticationError.ZOOM_AUTH_ERROR_SUCCESS) {
            showEmailLoginUserStartJoinActivity();
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
    public void onZoomAuthIdentityExpired() {
        Log.e(TAG,"onZoomAuthIdentityExpired");
    }

    public void onClickRoom1(View view) {
        if(!mZoomSDK.isInitialized())
        {
            Toast.makeText(this,"2 Init SDK First", Toast.LENGTH_SHORT).show();
            InitAuthSDKHelper.getInstance().initSDK(this, this);
            return;
        }

        if (ZoomSDK.getInstance().getMeetingSettingsHelper().isCustomizedMeetingUIEnabled()) {
            ZoomSDK.getInstance().getSmsService().enableZoomAuthRealNameMeetingUIShown(false);
        } else {
            ZoomSDK.getInstance().getSmsService().enableZoomAuthRealNameMeetingUIShown(true);
        }
        String number = MyMeetingActivity.mtNumber1;
        String name = MyMeetingActivity.mtName1;

        JoinMeetingParams params = new JoinMeetingParams();
        params.meetingNo = number;
        params.displayName = name;
        JoinMeetingOptions options=new JoinMeetingOptions();
        ZoomSDK.getInstance().getMeetingService().joinMeetingWithParams(this, params,ZoomMeetingUISettingHelper.getJoinMeetingOptions());
    }
    public void onClickRoom2(View view) {
        if(!mZoomSDK.isInitialized())
        {
            Toast.makeText(this,"2 Init SDK First", Toast.LENGTH_SHORT).show();
            InitAuthSDKHelper.getInstance().initSDK(this, this);
            return;
        }

        if (ZoomSDK.getInstance().getMeetingSettingsHelper().isCustomizedMeetingUIEnabled()) {
            ZoomSDK.getInstance().getSmsService().enableZoomAuthRealNameMeetingUIShown(false);
        } else {
            ZoomSDK.getInstance().getSmsService().enableZoomAuthRealNameMeetingUIShown(true);
        }
        String number = MyMeetingActivity.mtNumber2;
        String name = MyMeetingActivity.mtName2;

        JoinMeetingParams params = new JoinMeetingParams();
        params.meetingNo = number;
        params.displayName = name;
        JoinMeetingOptions options=new JoinMeetingOptions();
        ZoomSDK.getInstance().getMeetingService().joinMeetingWithParams(this, params,ZoomMeetingUISettingHelper.getJoinMeetingOptions());
    }
    private void showProgressPanel(boolean show) {
        if (show) {
//            mBtnEmailLogin.setVisibility(View.GONE);
//            mBtnSSOLogin.setVisibility(View.GONE);
//            mBtnWithoutLogin.setVisibility(View.GONE);
//            mReturnMeeting.setVisibility(View.GONE);
            mProgressPanel.setVisibility(View.VISIBLE);
//            layoutJoin.setVisibility(View.GONE);
            View view = findViewById(R.id.btnSettings);
            if (null != view) {
                view.setVisibility(View.GONE);
            }
        } else {
            View view = findViewById(R.id.btnSettings);
            if (null != view) {
                view.setVisibility(View.VISIBLE);
            }
//            mBtnWithoutLogin.setVisibility(View.VISIBLE);
//            mBtnEmailLogin.setVisibility(ZoomSDK.getInstance().isEmailLoginEnable() ? View.VISIBLE : View.GONE);
//            mBtnSSOLogin.setVisibility(View.VISIBLE);
            mProgressPanel.setVisibility(View.GONE);
//            layoutJoin.setVisibility(View.VISIBLE);
//            mReturnMeeting.setVisibility(View.GONE);
        }
    }

    private void showEmailLoginActivity() {
        Intent intent = new Intent(this, EmailUserLoginActivity.class);
        startActivity(intent);
    }

    private void showSSOLoginActivity() {
        Intent intent = new Intent(this, SSOUserLoginActivity.class);
        startActivity(intent);
    }

    private void showAPIUserActivity() {
        Intent intent = new Intent(this, APIUserStartJoinMeetingActivity.class);
        startActivity(intent);
    }

    private void showEmailLoginUserStartJoinActivity() {
        Intent intent = new Intent(this, LoginUserStartJoinMeetingActivity.class);
        startActivity(intent);
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
//                mReturnMeeting.setVisibility(View.VISIBLE);
            } else {
                MeetingWindowHelper.getInstance().hiddenMeetingWindow(true);
                showProgressPanel(false);
            }
        }
    }


    @Override
    public void onMeetingStatusChanged(MeetingStatus meetingStatus, int errorCode, int internalErrorCode) {
        Log.d(TAG,"onMeetingStatusChanged "+meetingStatus+":"+errorCode);
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
                intent.putExtra("from",MyMeetingActivity.JOIN_FROM_UNLOGIN);
            } else {
                intent = new Intent(this, RawDataMeetingActivity.class);
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            this.startActivity(intent);
        }

    }

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

//    @Override
//    public void onMeetingStatusChanged(MeetingStatus meetingStatus, int i, int i1) {
//
//    }
}