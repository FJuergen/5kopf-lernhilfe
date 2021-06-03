package com.kopf5.lernhilfe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
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


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.navigation_timer:
                            getSupportFragmentManager().beginTransaction()
                                    .setReorderingAllowed(true)
                                    .add(R.id.fragment_container,Lerntimer.class,null)
                                    .commit();
                            break;
                        case R.id.navigation_happy:
                            System.out.println("happy");
                            break;
                        case R.id.navigation_shop:
                            System.out.println("shop");
                            break;
                        case R.id.navigation_clothes:
                            System.out.println("clothes");
                            break;
                    }
                    return true;
                }
        );
    }
}