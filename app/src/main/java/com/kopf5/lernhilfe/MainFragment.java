package com.kopf5.lernhilfe;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainFragment extends Fragment {

    private SharedPreferences mySP;
    private ImageView img;
    private TextView textViewLevel;
    private TextView xpProgress;
    private ProgressBar pBar;

    public MainFragment() {
        super(R.layout.fragment_main);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        img = view.findViewById(R.id.imageView);
        textViewLevel = view.findViewById(R.id.textViewLevel);
        xpProgress = view.findViewById(R.id.xpProgress);
        pBar = view.findViewById(R.id.progressBar);
        return view;
    }



    /*
     * wird zur Activity navigiert, wird der Skin,Level und Progress (xp/levelThreshold) geladen.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mySP = getActivity().getSharedPreferences("UserInfo",0);
        loadImageFromStorage(mySP.getString("selectedSkinPath",""));
        textViewLevel.setText("Level " + mySP.getInt("level",1));
        float xp = mySP.getFloat("xp",0);
        float levelThreshold = mySP.getFloat("levelThreshold", 100);
        xpProgress.setText(xp + " XP / " + levelThreshold + " XP");
        pBar.setProgress((int)((xp/levelThreshold)*100));
    }


    /*
     * laedt Bild vom angegebenen Path
     * setzt Image auf Standard Skin, falls File nicht gefunden
     */
    private void loadImageFromStorage(String path) {
        try {
            File f = new File(path, "chibiAvatar.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            img.setImageBitmap(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //falls Datei nicht vorhanden, setzte den skin auf den Skin1
            img.setImageDrawable(getResources().getDrawable(R.drawable.skin1));
        }
    }
}