package com.example.myapplication.mypage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MyPage_Post extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<ModelPost> postList;

    private ImageView currentSelectedImage;
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_user_posts);

        recyclerView = findViewById(R.id.recycler_view_posts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        postList = new ArrayList<>();
        postAdapter = new PostAdapter(this, postList);
        recyclerView.setAdapter(postAdapter);

        // Set up Toolbar as ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize image picker launcher
        initializeImagePickerLauncher();

        // Load posts
        loadPosts();
    }

    private void loadPosts() {
        postList.add(new ModelPost("첫 번째 게시물", "이것은 첫 번째 게시물의 내용입니다.", null));
        postList.add(new ModelPost("두 번째 게시물", "이것은 두 번째 게시물의 내용입니다.", null));
        postList.add(new ModelPost("세 번째 게시물", "이것은 세 번째 게시물의 내용입니다.", null));

        postAdapter.notifyDataSetChanged();
    }

    private void initializeImagePickerLauncher() {
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        if (currentSelectedImage != null) {
                            currentSelectedImage.setImageURI(imageUri);
                            currentSelectedImage.setTag(imageUri);
                        }
                    }
                }
        );
    }

    // Data model for a post
    static class ModelPost {
        private String title;
        private String description;
        private Uri imageUri;

        public ModelPost(String title, String description, Uri imageUri) {
            this.title = title;
            this.description = description;
            this.imageUri = imageUri;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Uri getImageUri() {
            return imageUri;
        }

        public void setImageUri(Uri imageUri) {
            this.imageUri = imageUri;
        }
    }

    // RecyclerView Adapter for posts
    static class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

        private final List<ModelPost> postList;
        private final Context context;

        public PostAdapter(Context context, List<ModelPost> postList) {
            this.context = context;
            this.postList = postList;
        }

        @NonNull
        @Override
        public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mypage_item_post_buttons, parent, false);
            return new PostViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
            ModelPost post = postList.get(position);
            holder.titleTextView.setText(post.getTitle());
            holder.descriptionTextView.setText(post.getDescription());

            // Load image if exists
            if (post.getImageUri() != null) {
                holder.imageView.setImageURI(post.getImageUri());
            } else {
                holder.imageView.setImageResource(android.R.drawable.ic_menu_gallery);
            }

            // Edit button functionality
            holder.editButton.setOnClickListener(v -> showCustomDialog(holder.imageView, position));

            // Delete button functionality
            holder.deleteButton.setOnClickListener(v -> showDeleteConfirmationDialog(position));

            // ImageButton functionality for changing the image
            holder.changeImageButton.setOnClickListener(v -> {
                ((MyPage_Post) context).openImageChooser(holder.imageView);
            });
        }

        private void showCustomDialog(ImageView imageView, int position) {
            ModelPost post = postList.get(position);

            // Inflate custom dialog layout
            View dialogView = LayoutInflater.from(context).inflate(R.layout.mypage_edit_inquiry, null);

            // Find views
            TextView imageLabel = dialogView.findViewById(R.id.tv_image_label);
            TextView textLabel = dialogView.findViewById(R.id.tv_text_label);
            ImageView selectedImage = dialogView.findViewById(R.id.inquiryImages);
            EditText editText = dialogView.findViewById(R.id.inquiryText);

            // Set labels
            imageLabel.setText("기존 게시물 사진");
            textLabel.setText("기존 게시물 내용");

            // Set current values
            editText.setText(post.getDescription());
            if (post.getImageUri() != null) {
                selectedImage.setImageURI(post.getImageUri());
            } else {
                selectedImage.setImageResource(android.R.drawable.ic_menu_gallery);
            }

            // Image click to select a new image
            selectedImage.setOnClickListener(v -> ((MyPage_Post) context).openImageChooser(selectedImage));

            // AlertDialog
            new AlertDialog.Builder(context)
                    .setView(dialogView)
                    .setTitle("게시물 수정")
                    .setPositiveButton("저장", (dialog, which) -> {
                        String newDescription = editText.getText().toString().trim();
                        if (!newDescription.isEmpty()) {
                            post.setDescription(newDescription);
                            post.setImageUri((Uri) selectedImage.getTag());
                            notifyItemChanged(position);
                            Toast.makeText(context, "게시물이 수정되었습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "내용을 입력하세요.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("취소", (dialog, which) -> dialog.dismiss())
                    .show();
        }


        private void showDeleteConfirmationDialog(int position) {
            new AlertDialog.Builder(context)
                    .setMessage("삭제하시겠습니까?")
                    .setPositiveButton("예", (dialog, which) -> {
                        postList.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(context, "게시물이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("아니오", (dialog, which) -> dialog.dismiss())
                    .show();
        }

        @Override
        public int getItemCount() {
            return postList.size();
        }

        static class PostViewHolder extends RecyclerView.ViewHolder {
            TextView titleTextView;
            TextView descriptionTextView;
            ImageView imageView;
            Button editButton;
            Button deleteButton;
            ImageButton changeImageButton; // New ImageButton

            public PostViewHolder(@NonNull View itemView) {
                super(itemView);
                titleTextView = itemView.findViewById(R.id.tv_post_title);
                descriptionTextView = itemView.findViewById(R.id.tv_post_description);
                imageView = itemView.findViewById(R.id.iv_post_image);
                editButton = itemView.findViewById(R.id.btn_edit_post);
                deleteButton = itemView.findViewById(R.id.btn_delete_post);
            }
        }
    }

    private void openImageChooser(ImageView imageView) {
        currentSelectedImage = imageView;
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }
}
