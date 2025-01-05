package com.example.myapplication.chat;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_report);

        // 신고 버튼 클릭 시
        Button submitReportButton = findViewById(R.id.submit_report_button);
        submitReportButton.setOnClickListener(v -> {
            submitReport();
        });
    }

    private void submitReport() {
        // 신고 처리 로직
        Toast.makeText(this, "신고가 접수되었습니다.", Toast.LENGTH_SHORT).show();
        finish();  // 신고 완료 후 액티비티 종료
    }
}
