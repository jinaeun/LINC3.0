package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class ChatDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatdetail);

        // 차단하기 버튼 클릭 시
        LinearLayout blockUserLayout = findViewById(R.id.block_user_layout);
        blockUserLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 여기에 차단 기능을 구현
                blockUser();
            }
        });

        // 신고하기 버튼 클릭 시 report.xml로 이동
        LinearLayout reportLayout = findViewById(R.id.report_layout);
        reportLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 신고하기 액티비티로 이동
                Intent intent = new Intent(ChatDetailActivity.this, ReportActivity.class);
                startActivity(intent);
            }
        });

        // 검색하기 버튼 클릭 시
        LinearLayout searchLayout = findViewById(R.id.search_layout);
        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 여기에 검색 기능을 구현
                searchChatHistory();
            }
        });

        // 알림 켜기/끄기 버튼 클릭 시
        LinearLayout notificationsLayout = findViewById(R.id.notifications_layout);
        notificationsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 알림을 켜고 끄는 기능 구현
                toggleNotifications();
            }
        });

        // 채팅방 나가기 버튼 클릭 시
        Button leaveChatButton = findViewById(R.id.leave_chat_button);
        leaveChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 채팅방 나가기 기능 구현 (채팅 내용 삭제)
                leaveChat();
            }
        });

        // 취소 버튼 클릭 시 원래 페이지로 돌아가기
        Button cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ChatActivity로 돌아가기
                finish(); // 현재 액티비티를 종료
            }
        });
    }

    private void blockUser() {
        // 사용자를 차단하는 기능 구현
        // 예시: 서버에 차단 요청 보내기 또는 로컬 차단 리스트에 추가
    }

    private void searchChatHistory() {
        // 채팅 기록을 검색하는 기능 구현
    }

    private void toggleNotifications() {
        // 알림을 켜거나 끄는 기능 구현
    }

    private void leaveChat() {
        // 채팅방 나가기 기능 구현 (채팅 내용 삭제)
        // 예시: 서버에 요청 보내기 또는 로컬 데이터 삭제
    }
}
