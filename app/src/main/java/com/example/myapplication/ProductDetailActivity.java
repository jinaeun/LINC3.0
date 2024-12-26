package com.example.myapplication;  // 패키지 경로는 당신의 프로젝트에 맞게 설정하세요.

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);  // product_detail.xml 레이아웃을 설정

        // 이곳에서 필요한 데이터 초기화나 UI 설정을 진행할 수 있습니다.
        // 예: TextView, ImageView 등을 초기화하고 해당 데이터를 설정하는 코드
    }
}
