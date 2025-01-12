package com.example.myapplication.chat;

<<<<<<< HEAD
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
=======
import android.content.Intent;
import android.os.Bundle;
>>>>>>> 3b92ff62f4b0f8b2ff81eb3f5afdf5e7d2c2aa9e
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class Chat extends AppCompatActivity {

    private TextView tvProductName;
    private ImageView ivProductImage;
    private LinearLayout productDetail, llChatContent;
    private EditText etMessage;
    private ImageButton btnSend, btnImage, btnRightTop;
    private ScrollView scrollView;
<<<<<<< HEAD
    private ChatDatabaseHelper dbHelper;
=======
>>>>>>> 3b92ff62f4b0f8b2ff81eb3f5afdf5e7d2c2aa9e

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        // View 연결
        tvProductName = findViewById(R.id.tv_product_name);
        ivProductImage = findViewById(R.id.iv_product_image);
        productDetail = findViewById(R.id.product_detail);
        llChatContent = findViewById(R.id.ll_chat_content);
        etMessage = findViewById(R.id.et_message);
        btnSend = findViewById(R.id.btn_send);
        btnImage = findViewById(R.id.btn_image);
        scrollView = findViewById(R.id.scrollView);
        btnRightTop = findViewById(R.id.btn_right_top);

<<<<<<< HEAD
        // DB Helper 초기화
        dbHelper = new ChatDatabaseHelper(this);

        // 채팅 기록 불러오기
        loadMessagesFromDatabase();

=======
>>>>>>> 3b92ff62f4b0f8b2ff81eb3f5afdf5e7d2c2aa9e
        // 상품 상세 정보 클릭 시 product_detail.xml로 이동
        productDetail.setOnClickListener(v -> {
            Intent intent = new Intent(Chat.this, ProductDetailActivity.class);
            startActivity(intent);
        });

        // 전송 버튼 클릭 시
        btnSend.setOnClickListener(v -> {
            String message = etMessage.getText().toString().trim();
            if (!message.isEmpty()) {
<<<<<<< HEAD
                saveMessageToDatabase("나", message);  // DB에 저장
                addMessageToChat(message, true);  // 화면에 추가
                etMessage.setText("");  // 입력 필드 초기화
=======
                addMessageToChat(message, true);  // 사용자의 메시지 추가
                etMessage.setText("");  // 입력 필드 초기화
                addMessageToChat("상대방의 응답 예시", false);  // 상대방의 응답 추가
>>>>>>> 3b92ff62f4b0f8b2ff81eb3f5afdf5e7d2c2aa9e
            } else {
                Toast.makeText(Chat.this, "메시지를 입력하세요", Toast.LENGTH_SHORT).show();
            }
        });

<<<<<<< HEAD
        // 이미지 전송 버튼 클릭 시 bottom_bar 팝업 표시
        btnImage.setOnClickListener(this::showBottomBar);

        // 오른쪽 상단 버튼 클릭 시 팝업 윈도우 표시
        btnRightTop.setOnClickListener(v -> showTopRightPopup(v));
    }

    // DB에 메시지 저장
    private void saveMessageToDatabase(String sender, String message) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ChatDatabaseHelper.COLUMN_SENDER, sender);
        values.put(ChatDatabaseHelper.COLUMN_MESSAGE, message);
        values.put(ChatDatabaseHelper.COLUMN_TIMESTAMP, System.currentTimeMillis());
        db.insert(ChatDatabaseHelper.TABLE_NAME, null, values);
        db.close();
    }

    // DB에서 메시지 불러오기
    private void loadMessagesFromDatabase() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                ChatDatabaseHelper.TABLE_NAME,
                null,
                null, null, null, null,
                ChatDatabaseHelper.COLUMN_TIMESTAMP + " ASC");

        while (cursor.moveToNext()) {
            String sender = cursor.getString(cursor.getColumnIndexOrThrow(ChatDatabaseHelper.COLUMN_SENDER));
            String message = cursor.getString(cursor.getColumnIndexOrThrow(ChatDatabaseHelper.COLUMN_MESSAGE));
            addMessageToChat(message, sender.equals("나"));
        }

        cursor.close();
        db.close();
    }

    // 채팅 메시지 말풍선 추가
=======
        // 이미지 전송 버튼 클릭 시 bottom_bar.xml을 화면에 표시
        btnImage.setOnClickListener(this::showBottomBar);

        // 오른쪽 상단 버튼 클릭 시 팝업 윈도우 표시
        btnRightTop.setOnClickListener(v -> {
            View popupView = LayoutInflater.from(Chat.this).inflate(R.layout.chatdetail, null);
            PopupWindow popupWindow = new PopupWindow(popupView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    true);

            // 팝업 윈도우 표시 위치 설정 (오른쪽 상단)
            popupWindow.showAtLocation(v, Gravity.TOP | Gravity.END, 16, 16);

            // 팝업 윈도우 안에서 신고하기 버튼 클릭 시 ReportActivity로 이동
            Button reportButton = popupView.findViewById(R.id.report_button);
            reportButton.setOnClickListener(v1 -> {
                Intent intent = new Intent(Chat.this, ReportActivity.class);
                startActivity(intent);
                popupWindow.dismiss();  // 팝업 윈도우 닫기
            });
        });
    }

    // 채팅 말풍선을 추가하는 메서드
>>>>>>> 3b92ff62f4b0f8b2ff81eb3f5afdf5e7d2c2aa9e
    private void addMessageToChat(String message, boolean isUser) {
        View chatBubble;
        if (isUser) {
            chatBubble = LayoutInflater.from(this).inflate(R.layout.chat_bubble_user, llChatContent, false);
        } else {
            chatBubble = LayoutInflater.from(this).inflate(R.layout.chat_bubble_other, llChatContent, false);
        }

        TextView tvMessage = chatBubble.findViewById(R.id.tv_message);
        tvMessage.setText(message);
        llChatContent.addView(chatBubble);
<<<<<<< HEAD

        scrollView.postDelayed(() -> scrollView.fullScroll(View.FOCUS_DOWN), 100);
    }

    // 하단 팝업 윈도우 표시 (bottom_bar)
    private void showBottomBar(View anchorView) {
        View bottomBarView = LayoutInflater.from(this).inflate(R.layout.chat_bottom_bar, null);

        PopupWindow popupWindow = new PopupWindow(
                bottomBarView,
                ViewGroup.LayoutParams.MATCH_PARENT, // 가로 크기 설정
                ViewGroup.LayoutParams.WRAP_CONTENT, // 세로는 내용 크기에 맞춤
                true
        );

        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.dialog_holo_light_frame));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        // 팝업 표시 위치
        popupWindow.showAtLocation(anchorView, Gravity.BOTTOM, 0, 100);
    }

    // 상단 오른쪽 팝업 윈도우 표시 (chatdetail)
    private void showTopRightPopup(View anchorView) {
        View popupView = LayoutInflater.from(Chat.this).inflate(R.layout.chat_detail, null);

        PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        popupWindow.showAtLocation(anchorView, Gravity.TOP | Gravity.END, 16, 16);

        // 팝업 내부 버튼 클릭 이벤트
        Button reportButton = popupView.findViewById(R.id.report_button);
        reportButton.setOnClickListener(v -> {
            Log.d("Chat", "신고하기 버튼 클릭됨");

            // ReportActivity로 이동
            Intent intent = new Intent(Chat.this, ReportActivity.class);
            try {
                startActivity(intent);  // 화면 전환
                Log.d("Chat", "ReportActivity 화면 전환 성공");
            } catch (Exception e) {
                Log.e("Chat", "ReportActivity 화면 전환 실패: " + e.getMessage());
            }

            // 팝업 닫기
            popupWindow.dismiss();
        });

        Button leaveChatButton = popupView.findViewById(R.id.leave_chat_button);
        leaveChatButton.setOnClickListener(v -> {
            deleteAllChatHistory();  // DB에서 기록 삭제
            popupWindow.dismiss();  // 팝업 닫기
            finish();  // 액티비티 종료
        });
    }

    // 채팅 기록 삭제
    private void deleteAllChatHistory() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int rowsDeleted = db.delete(ChatDatabaseHelper.TABLE_NAME, null, null);
        Log.d("Chat", "삭제된 행 수: " + rowsDeleted);

        db.close();
        runOnUiThread(() -> llChatContent.removeAllViews());
    }
=======
        scrollView.postDelayed(() -> scrollView.fullScroll(View.FOCUS_DOWN), 100);
    }

    // 하단 바(bottom_bar.xml)를 화면에 팝업으로 표시하는 메서드
    private void showBottomBar(View anchorView) {
        View bottomBarView = LayoutInflater.from(this).inflate(R.layout.chat_bottom_bar, null);

        // PopupWindow 설정 - 높이와 넓이, soft input mode 설정 추가
        PopupWindow popupWindow = new PopupWindow(bottomBarView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);

        // PopupWindow 배경 설정 (반투명한 회색 배경 예시)
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.dialog_holo_light_frame));
        popupWindow.setOutsideTouchable(true); // 터치하면 닫히도록 설정

        // soft input 모드 설정 - 키보드와 겹치지 않도록 조정
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        // Gravity와 yOffset을 조정하여 네비게이션 바와 겹치지 않도록 설정
        popupWindow.showAtLocation(anchorView, Gravity.BOTTOM, 0, 100); // yOffset을 적절히 조정하여 겹침 방지
    }

>>>>>>> 3b92ff62f4b0f8b2ff81eb3f5afdf5e7d2c2aa9e
}
