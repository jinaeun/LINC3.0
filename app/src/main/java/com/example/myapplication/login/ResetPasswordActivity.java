package com.example.myapplication.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class ResetPasswordActivity extends AppCompatActivity {



    private EditText usernameEditText, emailEditText, verificationCodeEditText, newPasswordEditText, confirmPasswordEditText;
    private Button sendVerificationBtn, verifyBtn, resetPasswordBtn;
    private String generatedCode;  // 생성된 인증번호를 저장할 변수
    private DBHelper dbHelper;  // 사용자의 정보를 확인할 수 있는 DBHelper 객체
    boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_reset_password);

        // XML 요소 연결
        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        verificationCodeEditText = findViewById(R.id.verificationCodeEditText);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        sendVerificationBtn = findViewById(R.id.sendVerificationBtn);
        verifyBtn = findViewById(R.id.verifyBtn);
        resetPasswordBtn = findViewById(R.id.resetPasswordBtn);
        ImageView backIcon = findViewById(R.id.backIcon); // 뒤로가기 아이콘 추가

        dbHelper = new DBHelper(this);

        // 뒤로가기 아이콘 클릭 이벤트
        backIcon.setOnClickListener(v -> finish());

        // 인증번호 보내기 버튼 클릭 이벤트
        sendVerificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String email = emailEditText.getText().toString();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email)) {
                    Toast.makeText(ResetPasswordActivity.this, "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 사용자 이름과 이메일이 데이터베이스에서 일치하는지 확인
                if (dbHelper.checkUserByEmail(username, email)) {
                    // 인증번호 생성
                    generatedCode = String.valueOf((int) (Math.random() * 900000) + 100000);

                    // 이메일로 인증번호 전송
                    boolean emailSent = MailSender.sendEmail(email, "인증번호", "인증번호는 " + generatedCode + "입니다.");

                    if (emailSent) {
                        Toast.makeText(ResetPasswordActivity.this, "인증번호가 이메일로 전송되었습니다.", Toast.LENGTH_SHORT).show();
                        verificationCodeEditText.setVisibility(View.VISIBLE);  // 인증번호 입력란 표시
                        verifyBtn.setVisibility(View.VISIBLE);  // 인증 버튼 표시
                    } else {
                        Toast.makeText(ResetPasswordActivity.this, "이메일 전송에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ResetPasswordActivity.this, "사용자 이름과 이메일이 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 인증번호 확인 버튼 클릭 이벤트
        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredCode = verificationCodeEditText.getText().toString();

                if (enteredCode.equals(generatedCode)) {
                    Toast.makeText(ResetPasswordActivity.this, "인증 성공!", Toast.LENGTH_SHORT).show();
                    newPasswordEditText.setVisibility(View.VISIBLE);  // 새 비밀번호 입력란 표시
                    confirmPasswordEditText.setVisibility(View.VISIBLE);  // 새 비밀번호 확인란 표시
                    resetPasswordBtn.setVisibility(View.VISIBLE);  // 비밀번호 재설정 버튼 표시
                } else {
                    Toast.makeText(ResetPasswordActivity.this, "인증번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 비밀번호 재설정 버튼 클릭 이벤트
        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = newPasswordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                if (TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
                    Toast.makeText(ResetPasswordActivity.this, "새 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!newPassword.equals(confirmPassword)) {
                    Toast.makeText(ResetPasswordActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 새 비밀번호를 DB에 저장
                if (dbHelper.updatePassword(usernameEditText.getText().toString(), emailEditText.getText().toString(), newPassword)) {
                    Toast.makeText(ResetPasswordActivity.this, "비밀번호가 성공적으로 변경되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();  // 현재 액티비티 종료
                } else {
                    Toast.makeText(ResetPasswordActivity.this, "비밀번호 변경에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
