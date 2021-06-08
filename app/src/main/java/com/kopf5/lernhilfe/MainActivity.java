package com.kopf5.lernhilfe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/*
 * Startbildschirm
 * Darstellung des Avatars, der XP und der aktuellen Lernziele
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent test = new Intent(this,Lerntimer.class);
        //startActivity(test);
    }

    /*
     * wird zur Activity navigiert, wird der Skin geladen.
     */
    @Override
    protected void onStart(){
        super.onStart();
        SharedPreferences mySP = getSharedPreferences("UserInfo",0);
        loadImageFromStorage(mySP.getString("selectedSkinPath",""));
    }

    /*
     * laedt Bild vom angegebenen Path
     * setzt Image auf Standard Skin, falls File nicht gefunden
     */
    private void loadImageFromStorage(String path) {
        ImageView img = (ImageView) findViewById(R.id.imageButton5);
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

    public void launchAvatarSettings(View v){
        Intent i = new Intent(this,AvatarSettings.class);
        startActivity(i);
    }

    // toolbar.xml steht (res/layout/toolbar.xml) - ist aber noch ohne Eventlistener.
}