package com.example.myapplication.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView chatListView;
    private ChatAdapter chatAdapter;
    private ArrayList<ChatItem> chatItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity_main);

        // ListView 연결
        chatListView = findViewById(R.id.chat_list);

        // 데이터 리스트 생성 (예시 데이터)
        chatItems = new ArrayList<>();
        chatItems.add(new ChatItem(R.drawable.img, "물품 1", "최근 대화 내용 1"));
        chatItems.add(new ChatItem(R.drawable.img, "물품 2", "최근 대화 내용 2"));
        chatItems.add(new ChatItem(R.drawable.img, "물품 3", "최근 대화 내용 3"));
        chatItems.add(new ChatItem(R.drawable.img, "물품 4", "최근 대화 내용 4"));
        chatItems.add(new ChatItem(R.drawable.img, "물품 5", "최근 대화 내용 5"));
        chatItems.add(new ChatItem(R.drawable.img, "물품 6", "최근 대화 내용 6"));
        chatItems.add(new ChatItem(R.drawable.img, "물품 7", "최근 대화 내용 7"));
        chatItems.add(new ChatItem(R.drawable.img, "물품 8", "최근 대화 내용 8"));
        chatItems.add(new ChatItem(R.drawable.img, "물품 9", "최근 대화 내용 9"));
        chatItems.add(new ChatItem(R.drawable.img, "물품 10", "최근 대화 내용 10"));

        // 어댑터 생성 및 ListView에 연결
        chatAdapter = new ChatAdapter(chatItems);
        chatListView.setAdapter(chatAdapter);

        // ListView 클릭 이벤트
        chatListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 클릭된 아이템의 데이터 가져오기
                ChatItem clickedItem = chatItems.get(position);

                // Chat로 이동
                Intent intent = new Intent(MainActivity.this, Chat.class);
                intent.putExtra("productName", clickedItem.getProductName());
                intent.putExtra("profileImage", clickedItem.getProfileImage());
                startActivity(intent);
            }
        });
    }

    // 데이터 모델 클래스
    class ChatItem {
        private int profileImage;
        private String productName;
        private String recentMessage;

        public ChatItem(int profileImage, String productName, String recentMessage) {
            this.profileImage = profileImage;
            this.productName = productName;
            this.recentMessage = recentMessage;
        }

        public int getProfileImage() {
            return profileImage;
        }

        public String getProductName() {
            return productName;
        }

        public String getRecentMessage() {
            return recentMessage;
        }
    }

    // 어댑터 클래스
    class ChatAdapter extends BaseAdapter {
        private ArrayList<ChatItem> chatItems;

        public ChatAdapter(ArrayList<ChatItem> chatItems) {
            this.chatItems = chatItems;
        }

        @Override
        public int getCount() {
            return chatItems.size();
        }

        @Override
        public Object getItem(int position) {
            return chatItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                convertView = inflater.inflate(R.layout.chat_list_item, parent, false);
            }

            // View 연결
            ImageView ivProfile = convertView.findViewById(R.id.iv_profile);
            TextView tvProductName = convertView.findViewById(R.id.tv_product_name);
            TextView tvRecentMessage = convertView.findViewById(R.id.tv_recent_message);

            // 현재 position에 맞는 데이터 설정
            ChatItem currentItem = (ChatItem) getItem(position);
            ivProfile.setImageResource(currentItem.getProfileImage());
            tvProductName.setText(currentItem.getProductName());
            tvRecentMessage.setText(currentItem.getRecentMessage());

            return convertView;
        }
    }
}
