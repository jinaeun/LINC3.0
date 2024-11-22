package com.example.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText usernameEditText, nameEditText, passwordEditText, emailEditText, phoneEditText;
    private Button signUpBtn, goToLoginBtn;
    private DBHelper dbHelper; // DBHelper 추가

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);  // sign_up.xml 파일을 화면에 세팅

        // DBHelper 초기화
        dbHelper = new DBHelper(this);

        // 각 UI 요소를 XML에서 연결
        usernameEditText = findViewById(R.id.signUpUsernameEditText);
        nameEditText = findViewById(R.id.signUpNameEditText);
        passwordEditText = findViewById(R.id.signUpPasswordEditText);
        emailEditText = findViewById(R.id.signUpEmailEditText);
        phoneEditText = findViewById(R.id.signUpPhoneEditText);
        signUpBtn = findViewById(R.id.signUpButton);
        goToLoginBtn = findViewById(R.id.goToLoginButton);

        // 회원가입 버튼 클릭 이벤트 설정
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String name = nameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String phone = phoneEditText.getText().toString();

                // 입력값이 비어 있는지 확인
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(name) || TextUtils.isEmpty(password)
                        || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone)) {
                    Toast.makeText(SignUpActivity.this, "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    // DBHelper를 통해 회원정보를 저장
                    boolean isInserted = dbHelper.insertUser(username, password, email);
                    if (isInserted) {
                        Toast.makeText(SignUpActivity.this, "회원가입 성공!", Toast.LENGTH_SHORT).show();
                        // 회원가입 후 로그인 화면으로 이동
                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SignUpActivity.this, "회원가입 실패. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // 로그인 화면으로 돌아가기 버튼 클릭 이벤트 설정
        goToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 로그인 화면으로 돌아가기
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
