package com.cookandroid.linc_sadaju.my_page;

import android.os.Bundle;
import android.util.Log;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.os.Environment;
import java.io.File;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.cookandroid.linc_sadaju.databinding.ActivityMainBinding;
import com.cookandroid.linc_sadaju.R;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ViewBinding 초기화
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 스토리지 권한 요청
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        // 디렉터리 경로 확인 메서드 호출
        checkStorageDirectory();
        checkObbDirectory();

        // NavController 및 AppBarConfiguration 설정
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_chat, R.id.navigation_mypage
        ).build();

        try {
            // NavHostFragment를 사용하여 NavController 가져오기
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
            if (navHostFragment != null) {
                navController = navHostFragment.getNavController();

                // ActionBar와 NavController 연결
                NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

                // BottomNavigationView와 NavController 연결
                NavigationUI.setupWithNavController(binding.navView, navController);

                // 네비게이션 그래프 확인 로그 추가
                Log.d("NavController", "현재 네비게이션 그래프 ID: " + navController.getGraph().getId());
                navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                    Log.d("NavController", "이동한 Destination: " + destination.getLabel());
                });
            } else {
                Log.e("MainActivity", "NavHostFragment가 null입니다.");
            }

        } catch (Exception e) {
            Log.e("MainActivity", "NavController 설정 중 오류 발생: " + e.getMessage());
        }
    }

    // 디렉터리 경로 확인 메서드
    private void checkStorageDirectory() {
        File directory = getExternalFilesDir(null); // 앱 전용 저장소
        if (directory != null && directory.exists()) {
            Log.d("Storage", "Directory exists: " + directory.getAbsolutePath());
        } else {
            Log.d("Storage", "Directory not found or inaccessible.");
        }
    }

    private void checkObbDirectory() {
        File obbDir = new File(Environment.getExternalStorageDirectory(), "Android/obb/com.cookandroid.linc_sadaju");
        if (obbDir.exists()) {
            Log.d("Storage", "OBB Directory exists: " + obbDir.getAbsolutePath());
        } else {
            Log.d("Storage", "OBB Directory not found or inaccessible.");
        }
    }
}
