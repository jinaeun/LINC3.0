package com.example.myapplication.chat;

<<<<<<< HEAD
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
=======
import android.content.Intent;
>>>>>>> 3b92ff62f4b0f8b2ff81eb3f5afdf5e7d2c2aa9e
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
<<<<<<< HEAD
import android.widget.Toast;

=======
>>>>>>> 3b92ff62f4b0f8b2ff81eb3f5afdf5e7d2c2aa9e
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class ChatDetailActivity extends AppCompatActivity {

<<<<<<< HEAD
    private ChatDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_detail);

        // DB Helper 초기화
        dbHelper = new ChatDatabaseHelper(this);
=======
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatdetail);
>>>>>>> 3b92ff62f4b0f8b2ff81eb3f5afdf5e7d2c2aa9e

        // 차단하기 버튼 클릭 시
        LinearLayout blockUserLayout = findViewById(R.id.block_user_layout);
        blockUserLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD
=======
                // 여기에 차단 기능을 구현
>>>>>>> 3b92ff62f4b0f8b2ff81eb3f5afdf5e7d2c2aa9e
                blockUser();
            }
        });

        // 신고하기 버튼 클릭 시 report.xml로 이동
        LinearLayout reportLayout = findViewById(R.id.report_layout);
        reportLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD
=======
                // 신고하기 액티비티로 이동
>>>>>>> 3b92ff62f4b0f8b2ff81eb3f5afdf5e7d2c2aa9e
                Intent intent = new Intent(ChatDetailActivity.this, ReportActivity.class);
                startActivity(intent);
            }
        });

<<<<<<< HEAD
        // 검색하기 버튼 클릭 시 ChatSearchActivity로 이동
=======
        // 검색하기 버튼 클릭 시
>>>>>>> 3b92ff62f4b0f8b2ff81eb3f5afdf5e7d2c2aa9e
        LinearLayout searchLayout = findViewById(R.id.search_layout);
        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD
=======
                // 여기에 검색 기능을 구현
>>>>>>> 3b92ff62f4b0f8b2ff81eb3f5afdf5e7d2c2aa9e
                searchChatHistory();
            }
        });

        // 알림 켜기/끄기 버튼 클릭 시
        LinearLayout notificationsLayout = findViewById(R.id.notifications_layout);
        notificationsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD
=======
                // 알림을 켜고 끄는 기능 구현
>>>>>>> 3b92ff62f4b0f8b2ff81eb3f5afdf5e7d2c2aa9e
                toggleNotifications();
            }
        });

        // 채팅방 나가기 버튼 클릭 시
        Button leaveChatButton = findViewById(R.id.leave_chat_button);
        leaveChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD
=======
                // 채팅방 나가기 기능 구현 (채팅 내용 삭제)
>>>>>>> 3b92ff62f4b0f8b2ff81eb3f5afdf5e7d2c2aa9e
                leaveChat();
            }
        });

        // 취소 버튼 클릭 시 원래 페이지로 돌아가기
        Button cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD
=======
                // ChatActivity로 돌아가기
>>>>>>> 3b92ff62f4b0f8b2ff81eb3f5afdf5e7d2c2aa9e
                finish(); // 현재 액티비티를 종료
            }
        });
    }

<<<<<<< HEAD
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
=======
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
>>>>>>> 3b92ff62f4b0f8b2ff81eb3f5afdf5e7d2c2aa9e
    }
}
