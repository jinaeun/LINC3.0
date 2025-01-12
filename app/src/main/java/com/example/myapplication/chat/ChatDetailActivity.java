package com.example.myapplication.chat;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class ChatDetailActivity extends AppCompatActivity {

    private ChatDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_detail);

        // DB Helper 초기화
        dbHelper = new ChatDatabaseHelper(this);

        // 차단하기 버튼 클릭 시
        LinearLayout blockUserLayout = findViewById(R.id.block_user_layout);
        blockUserLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blockUser();
            }
        });

        // 신고하기 버튼 클릭 시 report.xml로 이동
        LinearLayout reportLayout = findViewById(R.id.report_layout);
        reportLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatDetailActivity.this, ReportActivity.class);
                startActivity(intent);
            }
        });

        // 검색하기 버튼 클릭 시 ChatSearchActivity로 이동
        LinearLayout searchLayout = findViewById(R.id.search_layout);
        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchChatHistory();
            }
        });

        // 알림 켜기/끄기 버튼 클릭 시
        LinearLayout notificationsLayout = findViewById(R.id.notifications_layout);
        notificationsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleNotifications();
            }
        });

        // 채팅방 나가기 버튼 클릭 시
        Button leaveChatButton = findViewById(R.id.leave_chat_button);
        leaveChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leaveChat();
            }
        });

        // 취소 버튼 클릭 시 원래 페이지로 돌아가기
        Button cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 현재 액티비티를 종료
            }
        });
    }

    /**
     * 1. 사용자를 차단하는 기능
     */
    private void blockUser() {
        // 상대방 ID는 실제 구현에서 전달받아야 합니다 (예: Intent로 전달받음)
        String blockedUserId = "상대방_ID"; // 예시

        // 확인 팝업 표시
        new AlertDialog.Builder(this)
                .setTitle("사용자 차단")
                .setMessage("상대방을 차단하시겠습니까?")
                .setPositiveButton("예", (dialog, which) -> {
                    // 차단 처리
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("blocked_user_id", blockedUserId);
                    db.insert("blocked_users", null, values);
                    db.close();

                    Toast.makeText(this, "사용자를 차단했습니다.", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("아니요", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    /**
     * 2. 채팅 검색 기능
     */
    private void searchChatHistory() {
        Intent intent = new Intent(ChatDetailActivity.this, ChatSearchActivity.class);
        startActivity(intent);
    }

    /**
     * 3. 알림 켜기/끄기 기능
     */
    private void toggleNotifications() {
        // 알림 상태를 전환 (예: SharedPreferences 사용)
        boolean notificationsEnabled = getSharedPreferences("chat_prefs", MODE_PRIVATE)
                .getBoolean("notifications_enabled", true);

        // 알림 상태 변경
        getSharedPreferences("chat_prefs", MODE_PRIVATE)
                .edit()
                .putBoolean("notifications_enabled", !notificationsEnabled)
                .apply();

        Toast.makeText(this, notificationsEnabled ? "알림이 꺼졌습니다." : "알림이 켜졌습니다.", Toast.LENGTH_SHORT).show();
    }

    /**
     * 4. 채팅방 나가기
     */
    private void leaveChat() {
        // 현재 채팅방의 내용을 삭제
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted = db.delete(ChatDatabaseHelper.TABLE_NAME, null, null); // 모든 메시지 삭제
        db.close();

        Toast.makeText(this, "채팅방을 나갔습니다.", Toast.LENGTH_SHORT).show();

        // 현재 액티비티 종료
        finish();
    }
}
