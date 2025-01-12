package com.example.myapplication.mypage;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MyPage_Trade extends AppCompatActivity {

    private TextView tvTitle;
    private RecyclerView recyclerView;
    private ListView tradeListView;
    private ArrayList<String> tradeList;
    private ArrayAdapter<String> adapter;

    private List<String> transactionList = new ArrayList<>();
    private TransactionAdapter transactionAdapter;
    private boolean isLoading = false;
    private int currentPage = 1;
    private static final int PAGE_SIZE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_trade_list);

        tvTitle = findViewById(R.id.tvTitle);
        recyclerView = findViewById(R.id.tradeList);

        tradeListView = new ListView(this);
        tradeList = new ArrayList<>();

        // 샘플 거래 목록 데이터
        tradeList.add("Item 1 - Status: Success");
        tradeList.add("Item 2 - Status: Failed");
        tradeList.add("Item 3 - Status: Pending");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tradeList);
        tradeListView.setAdapter(adapter);

//        // 항목 클릭 시 메시지 창 연동
//        tradeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String selectedItem = tradeList.get(position);
//                Intent intent = new Intent(.this, .class);
//                intent.putExtra("tradeItem", selectedItem);
//                startActivity(intent);
//            }
//        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 초기 데이터 로드
        loadTransactions(currentPage);

        transactionAdapter = new TransactionAdapter(transactionList);
        recyclerView.setAdapter(transactionAdapter);

        // 무한 스크롤 리스너 추가
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null && !isLoading) {
                    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                    if (lastVisibleItemPosition == transactionList.size() - 1) {
                        isLoading = true;
                        currentPage++;
                        loadMoreTransactions(currentPage);
                    }
                }
            }
        });
    }

    // 초기 데이터를 불러오는 메서드
    private void loadTransactions(int page) {
        for (int i = 1; i <= PAGE_SIZE; i++) {
            int transactionNumber = ((page - 1) * PAGE_SIZE) + i;
            transactionList.add("2024-12-" + transactionNumber + " - 거래 아이템 " + transactionNumber + " - " + (transactionNumber * 1_000) + "원");
        }
    }

    // 추가 데이터를 불러오는 메서드
    private void loadMoreTransactions(int page) {
        // 로딩 시 지연 효과를 주기 위해 Handler 사용
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            int startSize = transactionList.size();
            loadTransactions(page);
            transactionAdapter.notifyItemRangeInserted(startSize, PAGE_SIZE);
            isLoading = false;
        }, 2000);
    }

    // RecyclerView 어댑터 클래스
    class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

        private final List<String> transactions;

        public TransactionAdapter(List<String> transactions) {
            this.transactions = transactions;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.bind(transactions.get(position));
        }

        @Override
        public int getItemCount() {
            return transactions.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView textView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(android.R.id.text1);
            }

            public void bind(String transaction) {
                textView.setText(transaction);
            }
        }
    }
}
