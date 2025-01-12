package com.example.myapplication.mypage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MyPage_RecentSeenProduct extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecentlyViewedAdapter adapter;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_recent_seen_product);  // XML 파일 이름

        recyclerView = findViewById(R.id.recycler_recent_seen);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();
//        productList.add(new Product("상품 1", 10000, R.drawable.product1_image));
//        productList.add(new Product("상품 2", 20000, R.drawable.product2_image));
        // 필요시 여기에 더 많은 상품 추가

        adapter = new RecentlyViewedAdapter(productList);
        recyclerView.setAdapter(adapter);
    }

    // RecyclerView 어댑터 클래스
    private class RecentlyViewedAdapter extends RecyclerView.Adapter<RecentlyViewedAdapter.ViewHolder> {

        private List<Product> productList;

        public RecentlyViewedAdapter(List<Product> productList) {
            this.productList = productList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mypage_item_recent_seen, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Product product = productList.get(position);
            holder.productName.setText(product.getName());
            holder.productPrice.setText("₩" + product.getPrice());
            holder.productImage.setImageResource(product.getImageResId());
        }

        @Override
        public int getItemCount() {
            return productList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView productName, productPrice;
            ImageView productImage;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
//                productName = itemView.findViewById(R.id.tv_product_name);
//                productPrice = itemView.findViewById(R.id.tv_product_price);
//                productImage = itemView.findViewById(R.id.iv_product_image);
            }
        }
    }

    // Product 데이터 모델 클래스
    private class Product {
        private String name;
        private int price;
        private int imageResId;

        public Product(String name, int price, int imageResId) {
            this.name = name;
            this.price = price;
            this.imageResId = imageResId;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }

        public int getImageResId() {
            return imageResId;
        }
    }
}
