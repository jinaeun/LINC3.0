package com.cookandroid.linc_sadaju.my_page;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.cookandroid.linc_sadaju.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyPage_ForCustomer extends AppCompatActivity {
    Button question, report;
    ExpandableListView expandableListView;
    List<String> listGroup;
    Map<String, List<String>> listItem;
    MyPage_FAQAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_for_customer);

        question = findViewById(R.id.question);
        report = findViewById(R.id.report);

        expandableListView = findViewById(R.id.expandableListView);
        listGroup = new ArrayList<>();
        listItem = new HashMap<>();
        adapter = new MyPage_FAQAdapter((Context) this, listGroup, (HashMap<String, List<String>>) listItem);
        expandableListView.setAdapter(adapter);
        initListData();

        // Question 버튼 클릭 리스너
        question.setOnClickListener(view -> {
            Intent intent = new Intent(MyPage_ForCustomer.this, MyPage_OnebyoneInquiry.class);
            startActivity(intent);
        });

        // Report 버튼 클릭 리스너
        report.setOnClickListener(view -> {
            EditText inputReport = new EditText(MyPage_ForCustomer.this);

            AlertDialog.Builder builderRpt = new AlertDialog.Builder(MyPage_ForCustomer.this);
            builderRpt.setTitle("신고 내용을 작성해 보세요.");
            builderRpt.setView(inputReport);
            builderRpt.setPositiveButton("OK", (dialog, which) -> {
                String userInput = inputReport.getText().toString();
                Toast.makeText(MyPage_ForCustomer.this, "당신의 신고를 확인했습니다! ", Toast.LENGTH_SHORT).show();
            });

            builderRpt.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            builderRpt.show();
        });
    }

    private void initListData() {
        listGroup.clear();
        listItem.clear();

        listGroup.add("Q. 거래는 어떻게 진행되나요?");
        listGroup.add("Q. 결제 취소는 어떻게 하나요?");
        listGroup.add("Q. 거래 중 문제가 발생했어요.");
        listGroup.add("Q. 자주 묻는 질문(FAQ)에서 해결되지 않으면 어떻게 하나요?");
        listGroup.add("Q. 배송이 지연되면 어떻게 해야 하나요?");



        List<String> problem1 = new ArrayList<>();
        problem1.add("A. 구매자가 상품을 선택하여 판매자와 채팅하거나, 결제 및 배송을 통해 거래를 진행합니다.");

        List<String> problem2 = new ArrayList<>();
        problem2.add("A. 구매 후 24시간 이내에 결제를 취소할 수 있으며, 취소는 마이페이지 → 주문 내역에서 가능합니다.");

        List<String> problem3 = new ArrayList<>();
        problem3.add("A. 마이페이지 → 고객센터를 통해 신고하거나 문의를 접수할 수 있습니다.");

        List<String> problem4 = new ArrayList<>();
        problem4.add("A. 마이페이지 → 고객센터 → 1:1문의를 통해 상세한 도움을 요청하세요.");

        List<String> problem5 = new ArrayList<>();
        problem5.add("A. 배송이 예정 시간을 초과하면 고객센터로 문의하시거나 판매자와 채팅을 통해 확인할 수 있습니다.");

        listItem.put(listGroup.get(0), problem1);
        listItem.put(listGroup.get(1), problem2);
        listItem.put(listGroup.get(2), problem3);
        listItem.put(listGroup.get(3), problem4);
        listItem.put(listGroup.get(4), problem5);

        adapter.notifyDataSetChanged();  // 데이터 변경을 어댑터에 알림
    }
}
