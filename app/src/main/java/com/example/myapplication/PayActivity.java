package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PayActivity extends AppCompatActivity {

    private TextView amountTextView;
    private StringBuilder inputAmount = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay); // pay.xml과 연결

        amountTextView = findViewById(R.id.amount);

        GridLayout keypad = findViewById(R.id.keypad);

        // 숫자 패드 버튼 클릭 리스너 설정
        for (int i = 0; i < keypad.getChildCount(); i++) {
            View child = keypad.getChildAt(i);
            if (child instanceof Button) {
                child.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button button = (Button) v;
                        String text = button.getText().toString();
                        if (text.equals("←")) {
                            // 백스페이스 처리
                            if (inputAmount.length() > 0) {
                                inputAmount.deleteCharAt(inputAmount.length() - 1);
                            }
                        } else {
                            // "만원" 버튼 처리 또는 숫자 추가
                            inputAmount.append(text.equals("만원") ? "0000" : text);
                        }
                        // TextView에 입력한 금액 업데이트
                        amountTextView.setText(inputAmount.toString());
                    }
                });
            }
        }

        // 보내기 버튼 기능 설정
        Button sendButton = findViewById(R.id.btnSend);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputAmount.length() > 0) {
                    Toast.makeText(PayActivity.this, "예약송금 되었습니다", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PayActivity.this, "금액을 입력해 주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
