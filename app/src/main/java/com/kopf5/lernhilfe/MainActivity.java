package com.kopf5.lernhilfe;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
/*
 * Startbildschirm
 * Darstellung des Avatars, der XP und der aktuellen Lernziele
 */
public class MainActivity extends FragmentActivity {

    public MainActivity(){
        super(R.layout.activity_main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container,new MainFragment())
                .commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    Fragment selectedFragment;
                    switch (item.getItemId()) {
                        case R.id.navigation_timer:
                            selectedFragment = new Lerntimer();
                            getSupportFragmentManager().beginTransaction()
                                    .setReorderingAllowed(true)
                                    .replace(R.id.fragment_container,selectedFragment)
                                    .commit();
                            break;
                        case R.id.navigation_home:
                            selectedFragment = new MainFragment();
                            getSupportFragmentManager().beginTransaction()
                                    .setReorderingAllowed(true)
                                    .replace(R.id.fragment_container,selectedFragment)
                                    .commit();
                            break;
                        case R.id.navigation_info:
                            System.out.println("shop");
                            break;
                        case R.id.navigation_clothes:
                            selectedFragment = new AvatarSettings();
                            getSupportFragmentManager().beginTransaction()
                                    .setReorderingAllowed(true)
                                    .replace(R.id.fragment_container,selectedFragment)
                                    .commit();
                            break;
                    }
                    return true;
                }
        );
    }
}