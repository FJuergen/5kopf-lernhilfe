package com.kopf5.lernhilfe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/*
 * Startbildschirm
 * Darstellung des Avatars, der XP und der aktuellen Lernziele
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // toolbar.xml steht (res/layout/toolbar.xml) - ist aber noch ohne Eventlistener.
}