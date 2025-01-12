package com.example.myapplication.chat;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_account); // account.xml과 연결
    }
}
