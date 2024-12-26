package com.cookandroid.linc_sadaju.my_page;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.cookandroid.linc_sadaju.R;

public class MyPage_OnebyoneInquiry extends AppCompatActivity {

    private ImageView selectedImage;
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_onebyone_inquiry);

        // Initialize the FloatingActionButton
        FloatingActionButton fab = findViewById(R.id.displayQuestion);
        fab.setOnClickListener(v -> showCustomDialog());

        // Initialize the ActivityResultLauncher
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        if (selectedImage != null) {
                            selectedImage.setImageURI(imageUri);
                        }
                    }
                }
        );
    }

    private void showCustomDialog() {
        // Inflate the custom dialog layout
        View dialogView = getLayoutInflater().inflate(R.layout.mypage_dialog_inquiry, null);
        selectedImage = dialogView.findViewById(R.id.inquiryImages);
        EditText editText = dialogView.findViewById(R.id.inquiryText);

        // Create the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setTitle("사진 선택 및 글 작성")
                .setPositiveButton("저장", (dialog, which) -> {
                    String inputText = editText.getText().toString().trim();
                    // Save the inquiry content if needed
                    if (inputText.isEmpty()) {
                        editText.setError("내용을 입력하세요");
                    } else {
                        Toast.makeText(this, "문의가 저장되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("취소", (dialog, which) -> dialog.dismiss());

        // Set up the ImageView click listener for image selection
        selectedImage.setOnClickListener(v -> openImageChooser());

        builder.create().show();
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }
}
