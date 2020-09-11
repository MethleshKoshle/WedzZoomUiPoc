package com.example.wedzzoomuipoc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class DashboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    RoomInfo room = new RoomInfo();

    public void onClickRoom1(View view) {
        Intent intent = new Intent(this, ZoomMeetingActivity.class);

        room.setRoomName("Room-1");
        room.setRoomId("1234");
        room.setMeetingId("1234");
        room.setPassword("1234");

        intent.putExtra("roomName", room); //Put your id to your next Intent
//        intent.putExtra("roomName", "Room-1"); //Put your id to your next Intent

        startActivity(intent);
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
}
