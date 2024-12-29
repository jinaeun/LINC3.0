package com.cookandroid.linc_sadaju.my_page;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.cookandroid.linc_sadaju.databinding.MypageActivityMainBinding;

import com.cookandroid.linc_sadaju.R;

public class MainActivity extends AppCompatActivity {

    private MypageActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_activity_main);

        // ViewBinding 초기화
        binding = MypageActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // AppBarConfiguration 설정
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_chat, R.id.navigation_mypage
        ).build();

        // NavController 설정
        NavController navController;
        try {
            navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

            // ActionBar와 NavController 연결
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

            // BottomNavigationView와 NavController 연결
            NavigationUI.setupWithNavController(binding.navView, navController);

            // 네비게이션 그래프 확인 로그 추가
            Log.d("NavController", "현재 네비게이션 그래프 ID: " + navController.getGraph().getId());
            navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                Log.d("NavController", "이동한 Destination: " + destination.getLabel());
            });

        } catch (IllegalStateException e) {
            // NavController 설정 중 오류 처리
            Log.e("Mypage_MainActivity", "NavController 설정 중 오류 발생: " + e.getMessage());
        }
    }
}
