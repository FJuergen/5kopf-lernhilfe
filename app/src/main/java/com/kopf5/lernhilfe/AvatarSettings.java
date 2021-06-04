package com.kopf5.lernhilfe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

/*
 * Customization für den Avatar
 */

public class AvatarSettings extends AppCompatActivity {

    ImageButton ausgewaehlterSkin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar_settings);

    }

    /*
        Methode der ImageButtons:
        setzt die Priview auf das Drawable des Buttons. alter Button wird enabled und neuer disabled.
     */
    public void setPreview(View v){
        ausgewaehlterSkin = (ImageButton)v;
        Drawable skin = ausgewaehlterSkin.getDrawable();
        ((ImageView)findViewById(R.id.previewSkin)).setImageDrawable(skin);
    }

    //sendet den ausgewaehlten Skin an den Mainscreen
    /*public void sendImage(Drawable skin){
        Intent i = new Intent(this,RecieveImage.class);
        String imageUri = "drawable://" + skin;
        i.putExtra("skin",skin.toString());
    }*/
}