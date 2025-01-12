// MainActivity.java

package com.example.myapplication.dashboard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.R;
import com.example.myapplication.ui.dashboard.DashboardFragment;
import com.example.myapplication.ui.dashboard.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter viewPagerAdapter;
    private AppBarConfiguration appBarConfiguration;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private ArrayList<Post> postList;
    private FloatingActionButton fab;
    private PollsDBHelper dbHelper;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    private final BroadcastReceiver updatePollReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            loadPollsFromDatabase();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity_main);

        dbHelper = new PollsDBHelper(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        // Set up the TabLayout and ViewPager2
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);  // activity_main.xml에 ViewPager2 추가했다고 가정합니다.

        // Set up the ViewPager adapter with Home and Dashboard Fragment
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPagerAdapter.addFragment(new DashboardFragment(), "홈");  // Home Tab
        viewPagerAdapter.addFragment(new HomeFragment(), "팝업 정보");  // Recent Products Tab

        viewPager.setAdapter(viewPagerAdapter);

        // Set up the TabLayout with the ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(viewPagerAdapter.getFragmentTitle(position));
        }).attach();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        postList = new ArrayList<>();
        postAdapter = new PostAdapter(postList, position -> {
            if (position < postList.size()) {  // 인덱스 확인
                Intent intent = new Intent(MainActivity.this, PollDetailActivity.class);
                intent.putExtra("POLL_ID", postList.get(position).getId());
                activityResultLauncher.launch(intent);
            } else {
                Log.e("MainActivity", "Invalid index: " + position);
            }
        }, this);
        recyclerView.setAdapter(postAdapter);

        setupActivityResultLauncher();
        loadPollsFromDatabase();

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> activityResultLauncher.launch(new Intent(MainActivity.this, NewPollActivity.class)));

        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_home, R.id.navigation_chat, R.id.navigation_notifications, R.id.navigation_dashboard)
                    .build();
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(navView, navController);
        } else {
            Log.e("MainActivity", "NavHostFragment is null");
        }

        // Register BroadcastReceiver
        IntentFilter filter = new IntentFilter("com.example.myapplication2.UPDATE_POLL_LIST");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(updatePollReceiver, filter, Context.RECEIVER_NOT_EXPORTED);
        } else {
            registerReceiver(updatePollReceiver, filter);
        }
    }

    private void setupActivityResultLauncher() {
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        long pollId = data.getLongExtra("pollId", -1);
                        boolean isDeleted = data.getBooleanExtra("isDeleted", false);

                        if (!isDeleted && pollId != -1) {
                            // 새로 추가된 글이나 수정된 글을 반영
                            String action = "com.example.myapplication2.UPDATE_POLL_LIST";
                            sendBroadcast(new Intent(action));
                        }
                    }
                });
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
    protected void onDestroy() {
        // Unregister BroadcastReceiver
        unregisterReceiver(updatePollReceiver);
        dbHelper.close();
        super.onDestroy();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }
}
