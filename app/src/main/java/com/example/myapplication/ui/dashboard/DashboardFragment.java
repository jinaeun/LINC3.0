package com.example.myapplication.ui.dashboard;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.dashboard.PostAdapter;
import com.example.myapplication.dashboard.PollsDBHelper;
import com.example.myapplication.dashboard.Post;
import com.example.myapplication2.R;
import com.example.myapplication2.databinding.FragmentDashboardBinding;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private ArrayList<Post> postList;
    private PollsDBHelper dbHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        dbHelper = new PollsDBHelper(getContext());

        recyclerView = binding.getRoot().findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        postList = new ArrayList<>();
        postAdapter = new PostAdapter(postList, position -> {
            // Handle item click here
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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}