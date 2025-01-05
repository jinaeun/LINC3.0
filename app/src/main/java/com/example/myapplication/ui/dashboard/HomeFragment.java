package com.example.myapplication.ui.dashboard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.dashboard.PollDetailActivity;
import com.example.myapplication.dashboard.Post;
import com.example.myapplication.dashboard.PostAdapter;
import com.example.myapplication.dashboard.PollsDBHelper;
import com.example.myapplication2.R;
import com.example.myapplication2.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private ArrayList<Post> postList;
    private PollsDBHelper dbHelper;

    private BroadcastReceiver updatePollReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            loadPollsFromDatabase();
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Log.e("information", root.toString() );

        dbHelper = new PollsDBHelper(getContext());

        // 수정된 부분: RecyclerView 초기화 시 올바른 ID 사용
        recyclerView = root.findViewById(R.id.recyclerView1);
        Log.e("information11", "onCreateView: "+recyclerView.toString() );
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        Log.e("information22", llm.toString() );
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        postList = new ArrayList<>();
        postAdapter = new PostAdapter(postList, new PostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position < postList.size()) {
                    Intent intent = new Intent(getContext(), PollDetailActivity.class);
                    intent.putExtra("POLL_ID", postList.get(position).getId());
                    startActivity(intent);
                } else {
                    Log.e("HomeFragment", "Invalid index: " + position);
                }
            }
        }, getContext());

        recyclerView.setAdapter(postAdapter);

        loadPollsFromDatabase();

        return root;
    }

    private void loadPollsFromDatabase() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("Polls", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            postList.clear();
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                Post post = new Post(id, title, description);
                postList.add(post);
            } while (cursor.moveToNext());
            cursor.close();
        }
        postAdapter.notifyDataSetChanged();
        db.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        // BroadcastReceiver 등록
        IntentFilter filter = new IntentFilter("com.example.myapplication2.UPDATE_POLL_LIST");
        requireContext().registerReceiver(updatePollReceiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        // BroadcastReceiver 해제
        requireContext().unregisterReceiver(updatePollReceiver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}