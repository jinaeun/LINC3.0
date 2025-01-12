package com.example.myapplication.chat;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ChatSearchActivity extends AppCompatActivity {

    private EditText etSearchQuery;
    private Button btnSearch;
    private RecyclerView rvSearchResults;
    private ChatDatabaseHelper dbHelper;
    private ChatSearchAdapter searchAdapter;
    private List<String> searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_search);

        // View 연결
        etSearchQuery = findViewById(R.id.et_search_query);
        btnSearch = findViewById(R.id.btn_search);
        rvSearchResults = findViewById(R.id.rv_search_results);

        // DB Helper 초기화
        dbHelper = new ChatDatabaseHelper(this);

        // RecyclerView 설정
        searchResults = new ArrayList<>();
        searchAdapter = new ChatSearchAdapter(searchResults);
        rvSearchResults.setLayoutManager(new LinearLayoutManager(this));
        rvSearchResults.setAdapter(searchAdapter);

        // 검색 버튼 클릭 이벤트
        btnSearch.setOnClickListener(v -> {
            String query = etSearchQuery.getText().toString().trim();
            if (!TextUtils.isEmpty(query)) {
                searchChatMessages(query);
            } else {
                Toast.makeText(ChatSearchActivity.this, "검색어를 입력하세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // SQLite에서 메시지 검색
    private void searchChatMessages(String query) {
        searchResults.clear(); // 기존 결과 초기화
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                ChatDatabaseHelper.TABLE_NAME,
                new String[]{ChatDatabaseHelper.COLUMN_MESSAGE}, // 검색할 컬럼
                ChatDatabaseHelper.COLUMN_MESSAGE + " LIKE ?", // 조건
                new String[]{"%" + query + "%"}, // 검색어
                null, null, ChatDatabaseHelper.COLUMN_TIMESTAMP + " DESC" // 최신 순 정렬
        );

        while (cursor.moveToNext()) {
            String message = cursor.getString(cursor.getColumnIndexOrThrow(ChatDatabaseHelper.COLUMN_MESSAGE));
            searchResults.add(message);
        }

        cursor.close();
        db.close();

        if (searchResults.isEmpty()) {
            Toast.makeText(this, "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show();
        }

        searchAdapter.notifyDataSetChanged(); // RecyclerView 업데이트
    }
}
