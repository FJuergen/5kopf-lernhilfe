package com.kopf5.lernhilfe;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*
 * Shop interface um Avatar Optionen zu kaufen
 */

public class AvatarShop extends Fragment {

    public AvatarShop() {
        super(R.layout.activity_avatar_shop);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_avatar_shop, container, false);
    }
}