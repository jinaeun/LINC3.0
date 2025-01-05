package com.example.myapplication.chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class HomeActivity extends AppCompatActivity {

    // 이미지 버튼 선언
    private ImageButton photoVideoButton, accountTransferButton, addressTransferButton, documentTransferButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_bottom_bar); // bottom_bar.xml과 연결

        // 버튼들 초기화
        photoVideoButton = findViewById(R.id.photoVideoButton);
        accountTransferButton = findViewById(R.id.accountTransferButton);
        addressTransferButton = findViewById(R.id.addressTransferButton);
        documentTransferButton = findViewById(R.id.documentTransferButton);

        // 사진/동영상 버튼 클릭 시
        photoVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("HomeActivity", "photoVideoButton clicked"); // 디버깅을 위한 로그 출력
                // 사진/동영상 관련 기능을 추가할 수 있습니다.
                // Intent를 사용하여 다른 Activity로 이동하려면 여기에 코드를 추가하세요.
            }
        });

        // 계좌 전송 버튼 클릭 시 AccountActivity로 이동
        accountTransferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("HomeActivity", "accountTransferButton clicked"); // 로그로 클릭 확인
                Intent intent = new Intent(HomeActivity.this, AccountActivity.class); // AccountActivity로 이동하는 인텐트
                startActivity(intent); // 인텐트를 시작하여 AccountActivity 열기
            }
        });

        // 다쥬페이 버튼 클릭 시 PayActivity로 이동
        addressTransferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("HomeActivity", "addressTransferButton clicked"); // 로그로 클릭 확인
                Intent intent = new Intent(HomeActivity.this, PayActivity.class); // PayActivity로 이동하는 인텐트
                startActivity(intent); // 인텐트를 시작하여 PayActivity 열기
            }
        });

        // 배송지 전송 버튼 클릭 시 AddressActivity로 이동
        documentTransferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("HomeActivity", "documentTransferButton clicked"); // 로그로 클릭 확인
                Intent intent = new Intent(HomeActivity.this, AddressActivity.class); // AddressActivity로 이동하는 인텐트
                startActivity(intent); // 인텐트를 시작하여 AddressActivity 열기
            }
        });
    }
}
