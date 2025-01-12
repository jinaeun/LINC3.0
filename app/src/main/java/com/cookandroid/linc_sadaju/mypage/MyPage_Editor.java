package com.cookandroid.linc_sadaju.mypage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.cookandroid.linc_sadaju.R;

public class MyPage_Editor extends AppCompatActivity {
    ImageView profile;
    private ActivityResultLauncher<Intent> galleryLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_editor);

        profile = findViewById(R.id.profileImageView);
        profile.setOnClickListener(v -> openGallery());

        // ActivityResultLauncher 초기화
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        if (imageUri != null) {
                            profile.setImageURI(imageUri);
                        } else {
                            // Null 값을 처리하는 예외 케이스
                            showErrorMessage("이미지를 선택하지 못했습니다. 다시 시도해 주세요.");
                        }
                    } else {
                        // 실패한 경우의 처리
                        showErrorMessage("갤러리에서 이미지를 가져오지 못했습니다.");
                    }
                });
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(gallery); // startActivityForResult 대신 사용
    }

    private void showErrorMessage(String message) {
        // 오류 메시지 표시 (Toast 등)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
