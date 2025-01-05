package com.example.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private TextView signUpBtn, forgetPasswordBtn;
    private Button signInBtn;
    private ImageView passwordIcon;
    private DBHelper dbHelper;  // DBHelper 선언
    boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);  // login.xml 파일을 화면에 세팅

        // DBHelper 초기화
        dbHelper = new DBHelper(this);

        // 각 UI 요소를 XML에서 연결
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signUpBtn = findViewById(R.id.signUpBtn);
        forgetPasswordBtn = findViewById(R.id.forgetPasswordBtn);
        signInBtn = findViewById(R.id.signInBtn);
        passwordIcon = findViewById(R.id.passwordIcon);

        // 로그인 버튼 클릭 이벤트 설정
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // 입력값이 비어 있는지 확인
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Username과 Password를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    // DBHelper를 통해 로그인 정보 확인
                    if (dbHelper.checkUser(username, password)) {
                        Toast.makeText(LoginActivity.this, "로그인 성공!", Toast.LENGTH_SHORT).show();
                        // 로그인 성공 후 메인 화면으로 이동
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "로그인 실패. 정보를 확인하세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // "Sign Up" 버튼 클릭 시 회원가입 화면으로 이동
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class); // SignUpActivity로 이동
                startActivity(intent);
            }
        });

        // "Forget Password" 버튼 클릭 시 ResetPasswordActivity로 이동
        forgetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
            }
        });

        // 패스워드 아이콘 클릭 시 패스워드 보기/숨기기 기능 (추가 기능 구현 가능)
        passwordIcon.setOnClickListener(v -> {
            if (isPasswordVisible) {
                passwordEditText.setInputType(129);  // 비밀번호 숨기기
                passwordIcon.setImageResource(R.drawable.password_show);
                isPasswordVisible = false;
            } else {
                passwordEditText.setInputType(144);  // 비밀번호 보이기
                passwordIcon.setImageResource(R.drawable.password_hide);
                isPasswordVisible = true;
            }
            passwordEditText.setSelection(passwordEditText.getText().length());
        });
    }
}
